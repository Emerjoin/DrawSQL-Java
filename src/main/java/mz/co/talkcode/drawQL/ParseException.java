package mz.co.talkcode.drawQL;

/**
 * @author Mário Júnior
 */
public class ParseException extends DrawQLException {


    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
