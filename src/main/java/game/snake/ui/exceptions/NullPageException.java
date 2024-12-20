package game.snake.ui.exceptions;

/**
 * A exception that occurs when attempting to reference a page that doesn't exist (Error 404)
 * @author Aleksandr Stinchcomb
 * @version 1.0
 */
public class NullPageException extends Exception {
    private static final long serialVersionUID = -993775511278708854L;

	public NullPageException() {
        super();
    }

    public NullPageException(Throwable cause) {
        super(cause);
    }
    
    public NullPageException(String message) {
        super(message);
    }

    public NullPageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
