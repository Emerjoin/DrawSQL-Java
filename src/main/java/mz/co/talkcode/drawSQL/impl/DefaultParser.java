package mz.co.talkcode.drawSQL.impl;

import mz.co.talkcode.drawSQL.*;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Mário Júnior
 */
public class DefaultParser implements Parser {

    private SketchContext sketchContext;

    public TableInfo[] scan(Scanner document) throws ParseException {

        ArrayList<TableInfo> tableInfos = new ArrayList<TableInfo>();

        boolean tableDetected = false;
        TableInfo tableInfo = null;
        int totalDashesFound = 0;
        String textAccumulator = "";

        int lineNumber = 1;
        while(document.hasNextLine()){

            String line = document.nextLine();
            if(line.length()==0)
                continue;

            if(!tableDetected){

                if(line.charAt(0)=='@'){

                    String name = line.substring(1,line.length());
                    tableDetected = true;
                    tableInfo = new TableInfo(name);

                }


            }else{

                if(textAccumulator.equals(""))
                    textAccumulator = line;
                else
                    textAccumulator+="\n"+line;

                if(line.startsWith("--")){

                    if(totalDashesFound<3) {
                        totalDashesFound++;

                        //Table is complete
                        if(totalDashesFound==3){

                            tableInfo.setSubSketch(textAccumulator);
                            tableInfos.add(tableInfo);

                            textAccumulator="";
                            tableInfo = null;
                            tableDetected = false;
                            totalDashesFound=0;

                        }

                    }else if(totalDashesFound>=3){

                        throw new InvalidSubSketchException(tableInfo,lineNumber);

                    }

                }

            }


            lineNumber++;


        }

        if(tableDetected)
            throw new InvalidSubSketchException(tableInfo,lineNumber);

        TableInfo[] infos = new TableInfo[tableInfos.size()];
        tableInfos.toArray(infos);
        return infos;

    }


    private void readRow(String line, TableInfo tableInfo, int lineNumber) throws ParseException{

        if(line.length()==0)
            throw new InvalidRowException(tableInfo,lineNumber);

        String regularized = tableInfo.regularizeLength(line);

        TableInfo.DataRow row = tableInfo.new DataRow();

        for(TableInfo.ColumnInfo col: tableInfo.getColumnInfos()){

            String colValue = normalizeColVal(col.getValue(regularized));

            //Check asterisk (escaped or not)
            char[] valueChars = colValue.toCharArray();

            Character prevChar = null;
            int splitIndex=-1;
            int charIndex = 0;
            for(char ch: valueChars){

                if(ch=='*'){

                    //Not escaped
                    if(prevChar!='\\'){

                        splitIndex = charIndex;
                        break;

                    }

                }

                prevChar = ch;
                ++charIndex;

            }

            //A non escaped asterisk was found : split the value
            if(splitIndex!=-1){

                colValue=colValue.substring(0,splitIndex);
                colValue=colValue.trim();

            }

            row.setValue(col,colValue);


        }

        row.add();

    }

    private String normalizeColVal(String colValue){

        //Avoid Trimming
        int semiColonIndex = colValue.lastIndexOf(';');
        if(colValue.length() > semiColonIndex
                && colValue.charAt(semiColonIndex+1)==' '
                && semiColonIndex!=-1&&semiColonIndex!=0){

            colValue = colValue.substring(0,semiColonIndex);

        } else {
            colValue = colValue.trim();
        }

        return colValue;

    }

    public void readRows(TableInfo tableInfo) throws ParseException {

        Logger logger = sketchContext.getLogger();

        Scanner scanner = new Scanner(new ByteArrayInputStream(tableInfo.getSubSketch().getBytes()));
        int curLine = 0;
        while (scanner.hasNextLine()){

            String line = scanner.nextLine();

            if(logger!=null){

                logger.debug("Row at line ["+curLine+1+"] ROW : "+line);

            }



            //From line number 2 up to the last
            if(curLine>2){

                //Its not the last line
                if(scanner.hasNextLine()){

                    readRow(line,tableInfo,curLine+1);

                }


            }

            curLine++;

        }

    }

    public void readColumns(TableInfo tableInfo) throws ParseException {


        ByteArrayInputStream bin = new ByteArrayInputStream(tableInfo.getSubSketch().getBytes());
        Scanner table = new Scanner(bin);
        int lineCount = 0;
        String line = null;

        while (lineCount<2&&table.hasNextLine()){

            line = table.nextLine();
            ++lineCount;

        }

        if(lineCount==2&&line!=null){

            if(line.length()==0)
                 throw new NoDeclaredColumnsException(tableInfo,1);

            char[] chars = line.toCharArray();

            int currentPos = -1;
            int startPos=-1;

            StringBuilder colNameBuilder = new StringBuilder();

            for(char ch : chars){

                currentPos++;

                if(ch!='|') {

                    if(ch==' ')
                        continue;

                    if(startPos==-1)
                        startPos = currentPos;

                    colNameBuilder.append(ch);


                }else{

                    TableInfo.ColumnInfo columnInfo = tableInfo.new ColumnInfo();
                    columnInfo.setStartPos(startPos);
                    columnInfo.setEndPos(currentPos);
                    columnInfo.setName(colNameBuilder.toString().trim());
                    columnInfo.add();

                    startPos = -1;

                    colNameBuilder = new StringBuilder();

                }

            }

            if(startPos!=-1){

                TableInfo.ColumnInfo columnInfo = tableInfo.new ColumnInfo();
                columnInfo.setStartPos(startPos);
                columnInfo.setEndPos(currentPos);
                columnInfo.setName(colNameBuilder.toString());
                columnInfo.add();

            }

        }else
            throw new NoDeclaredColumnsException(tableInfo,1);


    }

    public void setContext(SketchContext context) {

        this.sketchContext = context;

    }
}
