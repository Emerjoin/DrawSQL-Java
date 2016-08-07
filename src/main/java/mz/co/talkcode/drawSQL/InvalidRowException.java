package mz.co.talkcode.drawSQL;

/**
 * @author Mário Júnior
 */
public class InvalidRowException extends InvalidSubSketchException {

    public InvalidRowException(TableInfo tableInfo,int line){

        super("Invalid table row.",tableInfo,line);

    }

    public InvalidRowException(String msg, TableInfo tableInfo,int line){

        super(msg,tableInfo,line);

    }



}
