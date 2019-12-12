package gr.aueb.mscis.sample.exceptions;

@SuppressWarnings("serial")
public class EpoptisException extends RuntimeException {

    public EpoptisException() { }
    
    public EpoptisException(String message) {
        super(message);
    }

    public EpoptisException(String message, Throwable cause) {
        super(message, cause);
    }

    public EpoptisException(Throwable cause) {
        super(cause);
    }
}
