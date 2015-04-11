package lt.cybergear.stateable;

/**
 * Created by marius on 15.4.11.
 */
public class StateableException extends RuntimeException {

    public StateableException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
