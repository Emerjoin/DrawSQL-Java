package mz.co.talkcode.drawSQL.impl;

import mz.co.talkcode.drawSQL.SQLGenerationException;
import mz.co.talkcode.drawSQL.SQLGenerator;
import mz.co.talkcode.drawSQL.SketchContext;
import mz.co.talkcode.drawSQL.TableInfo;
import org.apache.log4j.Logger;

/**
 * @author Mário Júnior
 */
public class DefaultSQLGenerator implements SQLGenerator {

    private SketchContext sketchContext;

    public String generateInserts(TableInfo tableInfo) throws SQLGenerationException {

        String inserts = "";

        for(TableInfo.DataRow row : tableInfo.getRows()){

            inserts+=generateInsert(row)+";\n";

        }

        return inserts;

    }

    public String generateInsert(TableInfo.DataRow rowData) throws SQLGenerationException {

        Logger logger = sketchContext.getLogger();

        String[] columns = new String[rowData.size()];
        rowData.keySet().toArray(columns);

        String[] values = new String[rowData.values().size()];
        rowData.values().toArray(values);

        for(int i=0;i<values.length;i++){

            String value = values[i];
            if(!value.equals("NULL")) {
                value = "'" + value + "'";
                values[i] = value;
            }

        }

        String glue = ",";
        String sql = "INSERT INTO "+rowData.getTable ().getName()+"("+implode(columns,glue)+") VALUES ("+implode(values,glue)+")";

        if(logger!=null){

            logger.debug("Row : "+rowData.toString());
            logger.debug("Generated INSERT SQL : "+sql);

        }



        return sql;

    }


    private String implode(String[] items,String glue){

        String imploded = "";

        int itemIndex = 0;
        int lastIndex = items.length-1;

        for(String item : items){

            imploded+=item;

            if(itemIndex<lastIndex)
                imploded+=glue;

            itemIndex++;

        }

        return imploded;

    }

    public void setContext(SketchContext context) {

        this.sketchContext = context;

    }
}
