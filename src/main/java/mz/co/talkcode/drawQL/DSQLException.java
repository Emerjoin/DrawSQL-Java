package mz.co.talkcode.drawQL;

/**
 * @author Mário Júnior
 */
public class DSQLException extends  DrawQLException{


    public DSQLException(String msg) {
        super(msg);
    }

    public DSQLException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
