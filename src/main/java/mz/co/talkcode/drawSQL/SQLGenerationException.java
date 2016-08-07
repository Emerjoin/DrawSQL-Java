package mz.co.talkcode.drawSQL;

/**
 * Exception thrown while generating INSERT SQL statements.
 * @author Mário Júnior
 */
public class SQLGenerationException extends DSQLException {

    public SQLGenerationException(String msg){

        super(msg);

    }

    public SQLGenerationException(String msg, Throwable throwable){

        super(msg,throwable);

    }

}
