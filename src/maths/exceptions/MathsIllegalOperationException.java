package maths.exceptions;

/**
 * Class defining an exception used when an illegal operation is tryed.
 *
 * @author flo
 */
public final class MathsIllegalOperationException extends MathsException {

    private static final long serialVersionUID = 3927128590591110927L;

    /**
     * Creates a {@code MathsIllegalOperationException} exception with no
     * parameters.
     */
    public MathsIllegalOperationException() {
        super();
    }

    /**
     * Creates a {@code MathsIllegalOperationException} with a specified
     * message.
     *
     * @param message The message.
     */
    public MathsIllegalOperationException(final String message) {
        super(message);
    }

    /**
     * Creates a {@code MathsIllegalOperationException} with a specified cause.
     *
     * @param cause The cause.
     */
    public MathsIllegalOperationException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@code MathsIllegalOperationException} with a specified message
     * and cause.
     *
     * @param message The message.
     * @param cause The cause.
     */
    public MathsIllegalOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
