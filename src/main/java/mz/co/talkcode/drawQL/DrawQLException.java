package mz.co.talkcode.drawQL;

/**
 * @author Mário Júnior
 */
public class DrawQLException extends Exception {


    public DrawQLException(String msg){

        super(msg);

    }

    public DrawQLException(String msg,Throwable ex){

        super(msg,ex);

    }

}
