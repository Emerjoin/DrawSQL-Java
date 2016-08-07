package mz.co.talkcode.drawSQL;

/**
 * @author Mário Júnior
 */
public class DSQLException extends DrawSQLException {


    public DSQLException(String msg) {
        super(msg);
    }

    public DSQLException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
