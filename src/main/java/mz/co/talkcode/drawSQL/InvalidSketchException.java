package mz.co.talkcode.drawSQL;

/**
 * @author Mário Júnior
 */
public class InvalidSketchException extends ParseException {

    private int line=-1;


    public InvalidSketchException(int line)
    {
        super("The supplied sketch is not valid. Check the line number "+line);
        this.line = line;
    }


    public InvalidSketchException(String msg, int line)
    {
        super(msg+". Check line number "+line);
        this.line = line;
    }


    public InvalidSketchException(String msg, int line, Throwable ex) {

        super(msg+". Check line number "+line, ex);
        this.line = line;

    }

    public int getLine() {
        return line;
    }


}
