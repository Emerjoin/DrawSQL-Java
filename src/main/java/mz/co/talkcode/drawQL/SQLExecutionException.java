package mz.co.talkcode.drawQL;

/**
 * @author Mário Júnior
 */
public class SQLExecutionException extends DSQLException {


    public SQLExecutionException(String msg) {
        super(msg);
    }

    public SQLExecutionException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
