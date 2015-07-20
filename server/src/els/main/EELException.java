package els.main;

/**
 * Created by filip on 15.07.15.
 */
public class EELException extends Exception{
    public EELException(String s){
        super(s);
    }
}

class EELIOException extends EELException{

    public EELIOException(String s, boolean exit) {
        super(s);
        if(exit){
            System.exit(0);
        }
    }
}