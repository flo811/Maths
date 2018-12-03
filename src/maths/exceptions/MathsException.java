package maths.exceptions;

/**
 * Abstract class defining a generic exception for the {@code maths} package.
 *
 * @author flo
 */
public abstract class MathsException extends Exception {

    private static final long serialVersionUID = 7948727083241722127L;

    /**
     * Creates a {@code MathsException} exception with no parameters.
     */
    protected MathsException() {
        super();
    }

    /**
     * Creates a {@code MathsException} with a specified message.
     *
     * @param message The message.
     */
    protected MathsException(final String message) {
        super(message);
    }

    /**
     * Creates a {@code MathsException} with a specified cause.
     *
     * @param cause The cause.
     */
    protected MathsException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@code MathsException} with a specified message and cause.
     *
     * @param message The message.
     * @param cause The cause.
     */
    protected MathsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
