package maths.exceptions;

/**
 * Class defining an exception used when desired precision in calculus isn't
 * reached.
 *
 * @author flo
 */
public class MathsPrecisionException extends MathsException {

    private static final long serialVersionUID = 1930674194868341753L;

    /**
     * Creates a {@code MathsPrecisionException} exception with no parameters.
     */
    public MathsPrecisionException() {
        super();
    }

    /**
     * Creates a {@code MathsPrecisionException} with a specified message.
     *
     * @param message The message.
     */
    public MathsPrecisionException(final String message) {
        super(message);
    }

    /**
     * Creates a {@code MathsPrecisionException} with a specified cause.
     *
     * @param cause The cause.
     */
    public MathsPrecisionException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@code MathsPrecisionException} with a specified message and
     * cause.
     *
     * @param message The message.
     * @param cause The cause.
     */
    public MathsPrecisionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
