package maths.exceptions;

/**
 * Class defining an exception used when a wrong type argument is set.
 *
 * @author flo
 */
public final class MathsArgumentException extends MathsException {

    private static final long serialVersionUID = 7844802516182070034L;

    /**
     * Creates a {@code MathsArgumentException} exception with no parameters.
     */
    public MathsArgumentException() {
        super();
    }

    /**
     * Creates a {@code MathsArgumentException} with a specified message.
     *
     * @param message The message.
     */
    public MathsArgumentException(final String message) {
        super(message);
    }

    /**
     * Creates a {@code MathsArgumentException} with a specified cause.
     *
     * @param cause The cause.
     */
    public MathsArgumentException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@code MathsArgumentException} with a specified message and
     * cause.
     *
     * @param message The message.
     * @param cause The cause.
     */
    public MathsArgumentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
