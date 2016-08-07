package mz.co.talkcode.drawSQL;

/**
 * @author Mário Júnior
 */
public class ParseException extends DrawSQLException {


    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
