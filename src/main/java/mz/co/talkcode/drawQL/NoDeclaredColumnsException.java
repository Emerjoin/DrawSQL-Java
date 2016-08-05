package mz.co.talkcode.drawQL;

/**
 * @author Mário Júnior
 */
public class NoDeclaredColumnsException extends InvalidSubSketchException {

    public NoDeclaredColumnsException(TableInfo tableInfo, int line) {
        super("The table "+tableInfo.getName()+" has no declared columns.",tableInfo, line);
    }

}
