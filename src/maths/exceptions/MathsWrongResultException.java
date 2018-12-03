package maths.exceptions;

/**
 * Class defining an exception used when the result doens't fit the expected
 * one.
 *
 * @author flo
 */
public class MathsWrongResultException extends MathsException {

    private static final long serialVersionUID = -2384503085425721283L;

    /**
     * Creates a {@code MathsWrongResultException} exception with no parameters.
     */
    public MathsWrongResultException() {
        super();
    }

    /**
     * Creates a {@code MathsWrongResultException} with a specified message.
     *
     * @param message The message.
     */
    public MathsWrongResultException(final String message) {
        super(message);
    }

    /**
     * Creates a {@code MathsWrongResultException} with a specified cause.
     *
     * @param cause The cause.
     */
    public MathsWrongResultException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@code MathsWrongResultException} with a specified message and
     * cause.
     *
     * @param message The message.
     * @param cause The cause.
     */
    public MathsWrongResultException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
