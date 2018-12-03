package maths.exceptions;

/**
 * Class defining an exception used when a a loop is iterated too many times.
 *
 * @author flo
 */
public class MathsLoopOverflowException extends MathsException {

    private static final long serialVersionUID = 2738015770673623271L;

    /**
     * Creates a {@code MathsLoopOverflowException} exception with no
     * parameters.
     */
    public MathsLoopOverflowException() {
        super();
    }

    /**
     * Creates a {@code MathsLoopOverflowException} with a specified message.
     *
     * @param message The message.
     */
    public MathsLoopOverflowException(final String message) {
        super(message);
    }

    /**
     * Creates a {@code MathsLoopOverflowException} with a specified cause.
     *
     * @param cause The cause.
     */
    public MathsLoopOverflowException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a {@code MathsLoopOverflowException} with a specified message and
     * cause.
     *
     * @param message The message.
     * @param cause The cause.
     */
    public MathsLoopOverflowException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
