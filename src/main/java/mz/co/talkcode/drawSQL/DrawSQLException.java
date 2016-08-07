package mz.co.talkcode.drawSQL;

/**
 * @author Mário Júnior
 */
public class DrawSQLException extends Exception {


    public DrawSQLException(String msg){

        super(msg);

    }

    public DrawSQLException(String msg, Throwable ex){

        super(msg,ex);

    }

}
