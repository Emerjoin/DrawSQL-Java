package mz.co.talkcode.drawSQL;

import mz.co.talkcode.drawSQL.impl.DefaultParser;
import mz.co.talkcode.drawSQL.impl.DefaultSQLGenerator;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Mário Júnior
 */
public class DrawSQL implements SketchContext {


    private TableInfo[] tableInfos=null;
    private SQLGenerator generator = null;
    private Parser parser = null;
    private boolean ready = false;

    private Logger logger = null;


    public String[] getTableNames(){

        List<String> names = new ArrayList<String>();
        for(TableInfo tableInfo : tableInfos){

            names.add(tableInfo.getName());

        }

        String[] array = new String[names.size()];
        names.toArray(array);
        return array;

    }

    public TableInfo[] getTableInfos(){

        return tableInfos;

    }

    private void prepare() throws ParseException{

        if(ready)
            throw new IllegalStateException();

        for(TableInfo tableInfo: tableInfos){

            tableInfo.read(parser);

        }

        ready = true;
    }


    public String getSQL(TableInfo tableInfo) throws SQLGenerationException{

        if(tableInfo==null)
            throw new NullPointerException("TableInfo instance is null");

          return generator.generateInserts(tableInfo);

    }

    public String getSQL() throws SQLGenerationException{

        String rawSql = "";
        for(TableInfo tableInfo: tableInfos){

            rawSql+=generator.generateInserts(tableInfo)+"\n";

        }

        return rawSql;

    }

    private DrawSQL(){



    }


    public void apply(Connection jdbcConnection) throws DSQLException{

        if(jdbcConnection==null)
            throw new NullPointerException("JDBC connection is null");

        try {

            if (jdbcConnection.isClosed())
                throw new IllegalStateException("Connection is closed");


            for (TableInfo tableInfo : tableInfos) {

                for (TableInfo.DataRow row : tableInfo.getRows()) {

                    Statement statement = jdbcConnection.createStatement();
                    statement.execute(generator.generateInsert(row));

                }

            }

        }catch (SQLException ex){

            throw new SQLExecutionException("Failed to execute generated SQL on supplied jdbc connection",ex);

        }

    }

    public Logger getLogger() {

        return logger;

    }

    public static class Builder {

        private SQLGenerator sqlGenerator;
        private Parser parser;
        private Scanner scanner = null;
        private DrawSQL drawSQL = null;

        public Builder(){

            sqlGenerator = new DefaultSQLGenerator();
            parser = new DefaultParser();
            drawSQL = new DrawSQL();


        }

        public Builder fromSketch(String text) throws IllegalArgumentException{
            if(text==null)
                throw new IllegalArgumentException();

            if(text.trim().length()==0)
                throw new IllegalArgumentException();

            scanner = new Scanner(new ByteArrayInputStream(text.getBytes()));
            return this;

        }


        public Builder enableLogging(){

            drawSQL.logger =  Logger.getLogger(DrawSQL.class);
            return this;

        }

        public Builder enableLogging(Logger logger){

            drawSQL.logger =  logger;
            return this;

        }

        public Builder fromSketch(File file) throws IOException{

            if(file==null)
                throw new IllegalArgumentException();

            scanner = new Scanner(new FileInputStream(file));
            return this;

        }

        public Builder fromSketch(InputStream inputStream){
            if(inputStream==null)
                throw new IllegalArgumentException();

            scanner =  new Scanner(inputStream);
            return this;

        }

        public Builder fromSketch(byte[] input){
            if(input==null)
                throw new IllegalArgumentException();

            if(input.length==0)
                throw new IllegalArgumentException();

            scanner = new Scanner(new ByteArrayInputStream(input));
            return this;

        }

        public Builder generator(SQLGenerator sqlGenerator){

            if(sqlGenerator==null)
                throw new IllegalStateException("Null SQLGenerator instance");

            this.sqlGenerator = sqlGenerator;
            this.sqlGenerator.setContext(drawSQL);
            return this;

        }

        public Builder parser(Parser parser){

            if(parser==null)
                throw new NullPointerException("Null Parser instance");

            this.parser = parser;
            this.parser.setContext(drawSQL);
            return this;

        }

        public DrawSQL build() throws DrawSQLException {

            if(scanner==null)
                throw new IllegalStateException("No sketch specified");


            parser.setContext(drawSQL);
            sqlGenerator.setContext(drawSQL);

            drawSQL.tableInfos =  parser.scan(scanner);
            drawSQL.generator = sqlGenerator;
            drawSQL.parser = this.parser;
            drawSQL.prepare();
            return drawSQL;

        }

    }

}
