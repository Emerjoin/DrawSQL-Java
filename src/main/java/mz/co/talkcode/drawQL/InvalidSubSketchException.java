package mz.co.talkcode.drawQL;

/**
 * @author Mário Júnior
 */
public class InvalidSubSketchException extends InvalidSketchException {

    private TableInfo tableInfo;

    public InvalidSubSketchException(String msg,TableInfo tableInfo, int line) {
        super(msg, tableInfo.getStartLine()+line);
        this.tableInfo = tableInfo;
    }

    public InvalidSubSketchException(TableInfo tableInfo, int line) {
        super("Invalid table declaration.",tableInfo.getStartLine()+line);
        this.tableInfo = tableInfo;
    }

    public InvalidSubSketchException(String msg, TableInfo tableInfo, int line, Throwable throwable) {
        super(msg,tableInfo.getStartLine()+line,throwable);
        this.tableInfo = tableInfo;
    }

    public TableInfo getTableInfo() {
        return tableInfo;
    }

}
