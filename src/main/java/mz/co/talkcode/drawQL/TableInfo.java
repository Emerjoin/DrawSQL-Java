package mz.co.talkcode.drawQL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Mário Júnior
 */
public class TableInfo {

    private String name;
    private int startLine;
    private int endLine;

    private int firstRowLine;
    private int lastRowLine;

    private String subSketch;

    private ColumnInfo lastColumn;

    public String getSubSketch() {
        return subSketch;
    }

    public void setSubSketch(String subSketch) {
        this.subSketch = subSketch;
    }

    public int getFirstRowLine() {
        return firstRowLine;
    }

    public void setFirstRowLine(int firstRowLine) {
        this.firstRowLine = firstRowLine;
    }

    public int getLastRowLine() {
        return lastRowLine;
    }

    public void setLastRowLine(int lastRowLine) {
        this.lastRowLine = lastRowLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public ColumnInfo getLastColumn(){

        return lastColumn;

    }

    public String regularizeLength(String line){

        if(columnInfos.size()==0)
            return line;

        ColumnInfo lastCol = getLastColumn();
        int idealLength = lastCol.getEndPos()+1;

        if(line.length()<idealLength){

            int diff = idealLength-line.length();
            StringBuilder stringBuilder = new StringBuilder(line);
            while (diff>0){

                stringBuilder.append(' ');
                --diff;

            }

            return stringBuilder.toString();

        }

        return line;


    }

    private Collection<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();
    private Collection<DataRow> rows = new ArrayList<DataRow>();

    public TableInfo(String name){

        this.name = name;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public DataRow[] getRows() {
        DataRow[] dataRows = new DataRow[rows.size()];
        rows.toArray(dataRows);
        return dataRows;
    }

    public ColumnInfo[] getColumns(){

        ColumnInfo[] array = new ColumnInfo[columnInfos.size()];
        columnInfos.toArray(array);
        return array;

    }

    public int totalRows(){

        return rows.size();

    }

    public int totalColumns(){

        return columnInfos.size();

    }

    public void read(Parser parser) throws ParseException{

        parser.readColumns(this);
        parser.readRows(this);

    }

    public class ColumnInfo{

        private String name;
        private int startPos;
        private int endPos;

        public String getValue(String regularized){

            return regularized.substring(startPos,endPos+1);

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStartPos() {
            return startPos;
        }

        public void setStartPos(int startPos) {
            this.startPos = startPos;
        }

        public int getEndPos() {
            return endPos;
        }

        public void setEndPos(int endPos) {
            this.endPos = endPos;
        }

        public void add(){

            TableInfo.this.columnInfos.add(this);
            lastColumn = this;

        }

    }


    public class DataRow extends HashMap<String,String> {


        public int getInt(String name){

            if(containsKey(name))
                return Integer.parseInt(get(name));

            return 0;

        }

        public long getLong(String name){

            if(containsKey(name))
                return Long.parseLong(get(name));

            return 0;

        }

        public double getDouble(String name){

            if(containsKey(name))
                return Double.parseDouble(get(name));

            return 0.0;

        }

        public float getFloat(String name){

            if(containsKey(name))
                return Float.parseFloat(get(name));

            return 0.0f;

        }

        public boolean getBoolean(String name){

            if(containsKey(name))
                return Boolean.parseBoolean(get(name));

            return false;

        }

        public String getString(String name){

            if(containsKey(name))
                return get(name);

            return null;

        }

        public boolean isNULL(String name){

            if(containsKey(name))
                return get(name).equals("NULL");

            return false;

        }

        public void setValue(TableInfo.ColumnInfo columnInfo, String value){

            this.put(columnInfo.getName(),value);

        }

        public String toSQLInsert(){

            return null;

        }

        public void add(){

            TableInfo.this.rows.add(this);

        }

        public TableInfo getTable(){

            return TableInfo.this;

        }

        public int calculateSketchLine(int subSketchLine){

            return startLine+subSketchLine;

        }


    }




}
