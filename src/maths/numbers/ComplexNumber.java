package maths.numbers;

import java.io.Serializable;
import maths.exceptions.MathsArgumentException;

/**
 * Class representing a complex number.
 *
 * @author flo
 */
public final class ComplexNumber implements Serializable {

    private static final long serialVersionUID = 280214115214486859L;

    public static final ComplexNumber ZERO = new ComplexNumber(0.0, 0.0);
    public static final ComplexNumber ONE = new ComplexNumber(1.0, 0.0);
    public static final ComplexNumber I = new ComplexNumber(0.0, 1.0);
    public static final ComplexNumber J = new ComplexNumber(-0.5, Math.sqrt(3.0) / 2.0);
    public static final ComplexNumber J2 = new ComplexNumber(-0.5, -Math.sqrt(3.0) / 2.0);
    public static final ComplexNumber E = new ComplexNumber(Math.E, 0.0);
    public static final ComplexNumber PI = new ComplexNumber(Math.PI, 0.0);
    public static final ComplexNumber REAL_POSITIVE_INFINITY = new ComplexNumber(1.0 / 0.0, 0.0);
    public static final ComplexNumber REAL_NEGATIVE_INFINITY = new ComplexNumber(-1.0 / 0.0, 0.0);
    public static final ComplexNumber IMAGINARY_POSITIVE_INFINITY = new ComplexNumber(0.0, 1.0 / 0.0);
    public static final ComplexNumber IMAGINARY_NEGATIVE_INFINITY = new ComplexNumber(0.0, -1.0 / 0.0);
    public static final ComplexNumber NaN = new ComplexNumber(0.0 / 0.0, 0.0 / 0.0);

    private final double re;
    private final double im;

    /**
     * Default initialization as zero.
     */
    public ComplexNumber() {
        re = 0.0;
        im = 0.0;
    }

    /**
     * Initialization with a {@code Number} as real part.
     *
     * @param <T> A class that extends {@code Number}.
     * @param re The real part.
     */
    public <T extends Number> ComplexNumber(final T re) {
        this.re = re.doubleValue();
        im = 0.0;
    }

    /**
     * Initialization with two {@code Number}, the first as the real part and
     * the second as the imaginary one.
     *
     * @param <T> A class that extends {@code Number}.
     * @param <U> A class that extends {@code Number}.
     * @param re The real part.
     * @param im The complex part.
     */
    public <T extends Number, U extends Number> ComplexNumber(final T re, final U im) {
        this.re = re.doubleValue();
        this.im = im.doubleValue();
    }

    /**
     * Initialization with a {@code ComplexNumber}.
     *
     * @param c The {@code ComplexNumber} with which to initialize.
     */
    public ComplexNumber(final ComplexNumber c) {
        re = c.getReal();
        im = c.getImaginary();
    }

    /**
     * Returns the real part.
     *
     * @return The real part.
     */
    public double getReal() {
        return re;
    }

    /**
     * Returns the imaginary part.
     *
     * @return The imaginary part.
     */
    public double getImaginary() {
        return im;
    }

    /**
     * Add a {@code Number}.
     *
     * @param <T> A class that extends {@code Number}.
     * @param d The {@code double} to add.
     *
     * @return The sum of this {@code ComplexeNumber} with {@code d}.
     */
    public <T extends Number> ComplexNumber add(final T d) {
        return new ComplexNumber(re + d.doubleValue(), im);
    }

    /**
     * Add a {@code ComplexeNumber}.
     *
     * @param c The {@code ComplexeNumber} to add.
     *
     * @return The sum of this {@code ComplexeNumber} with {@code c}.
     */
    public ComplexNumber add(final ComplexNumber c) {
        return new ComplexNumber(re + c.getReal(), im + c.getImaginary());
    }

    /**
     * Subract a {@code Number}.
     *
     * @param <T> A class that extends {@code Number}.
     * @param d The {@code double} to subtract.
     *
     * @return The difference between this {@code ComplexeNumber} and {@code d}.
     */
    public <T extends Number> ComplexNumber minus(final T d) {
        return new ComplexNumber(re - d.doubleValue(), im);
    }

    /**
     * Subract a {@code ComplexeNumber}.
     *
     * @param c The {@code ComplexeNumber} to subtract.
     *
     * @return The difference between this {@code ComplexeNumber} and {@code c}.
     */
    public ComplexNumber minus(final ComplexNumber c) {
        return new ComplexNumber(re - c.getReal(), im - c.getImaginary());
    }

    /**
     * Multiply by a {@code Number}.
     *
     * @param <T> A class that extends {@code Number}.
     * @param d The {@code double} to multiply with.
     *
     * @return The product of this {@code ComplexeNumber} with {@code d}.
     */
    public <T extends Number> ComplexNumber times(final T d) {
        return new ComplexNumber(re * d.doubleValue(), im * d.doubleValue());
    }

    /**
     * Multiply by a {@code ComplexeNumber}.
     *
     * @param c The {@code ComplexeNumber} to multiply with.
     *
     * @return The product of this {@code ComplexeNumber} with {@code c}.
     */
    public ComplexNumber times(final ComplexNumber c) {
        return new ComplexNumber(re * c.getReal() - im * c.getImaginary(), re * c.getImaginary() + im * c.getReal());
    }

    /**
     * Divide by a {@code Number}.
     *
     * @param <T> A class that extends {@code Number}.
     * @param d The {@code double} to divide by.
     *
     * @return The division of this {@code ComplexeNumber} with {@code d}.
     */
    public <T extends Number> ComplexNumber divide(final T d) {
        return new ComplexNumber(re / d.doubleValue(), im / d.doubleValue());
    }

    /**
     * Divide by a {@code ComplexeNumber}.
     *
     * @param c The {@code ComplexeNumber} to divide by.
     *
     * @return The division of this {@code ComplexeNumber} with {@code c}.
     */
    public ComplexNumber divide(final ComplexNumber c) {
        return times(c.invert());
    }

    /**
     * Gives the opposite.
     *
     * @return The opposite of this {@code ComplexeNumber}.
     */
    public ComplexNumber opposite() {
        return new ComplexNumber(-re, -im);
    }

    /**
     * Gives the multiplicative inverse.
     *
     * @return The multiplicative inverse of this {@code ComplexeNumber}.
     */
    public ComplexNumber invert() {
        return new ComplexNumber(re / squareModule(), -im / squareModule());
    }

    /**
     * Gives the conjugate.
     *
     * @return The conjugate of this {@code ComplexeNumber}.
     */
    public ComplexNumber conjugate() {
        return new ComplexNumber(re, -im);
    }

    /**
     * Gives the module.
     *
     * @return The module of this {@code ComplexeNumber}.
     */
    public double module() {
        return Math.sqrt(squareModule());
    }

    /**
     * Gives the argument.
     *
     * @return The argument of this {@code ComplexeNumber}.
     */
    public double arg() {
        return Math.atan2(im, re);
    }

    /**
     * Gives the squarred module.
     *
     * @return The squarred module of this {@code ComplexeNumber}.
     */
    public double squareModule() {
        return re * re + im * im;
    }

    /**
     * Gives the square.
     *
     * @return The square of this {@code ComplexeNumber}.
     */
    public ComplexNumber square() {
        return new ComplexNumber(re * re - im * im, 2 * re * im);
    }

    /**
     * Gives the square root.
     *
     * @return The square root of this {@code ComplexeNumber}.
     */
    public ComplexNumber sqrt() {
        if (isReal()) {
            return new ComplexNumber(Math.sqrt(re >= 0 ? re : -re));
        }

        final double moduleSqrt = Math.sqrt(module());
        final double halfArg = arg() / 2;
        return new ComplexNumber(moduleSqrt * Math.cos(halfArg), moduleSqrt * Math.sin(halfArg));
    }

    /**
     * Gives the cube root.
     *
     * @return The cube root of this {@code ComplexeNumber}.
     */
    public ComplexNumber cbrt() {
        final double moduleCbrt = Math.cbrt(module());
        final double thirdOfArg = arg() / 3;

        return new ComplexNumber(moduleCbrt * Math.cos(thirdOfArg), moduleCbrt * Math.sin(thirdOfArg));
    }

    /**
     * Gives the nth root.
     *
     * @param n The {@code int} parameter for the root.
     *
     * @return The {@code n}th root of this {@code ComplexeNumber}.
     * @throws MathsArgumentException If n is less or equal to zero.
     */
    public ComplexNumber nRoot(final int n) throws MathsArgumentException {
        if (n < 1) {
            throw new MathsArgumentException("n must be a strictly positive integer.");
        }

        switch (n) {
            case 1:
                return new ComplexNumber(this);
            case 2:
                return sqrt();
            case 3:
                return cbrt();
            default:
                final double moduleRoot = Math.pow(module(), 1.0 / n);
                final double newArg = arg() / n;
                return new ComplexNumber(moduleRoot * Math.cos(newArg), moduleRoot * Math.sin(newArg));
        }
    }

    /**
     * Gives the exponential.
     *
     * @return The exponential of this {@code ComplexeNumber}.
     */
    public ComplexNumber exp() {
        return (new ComplexNumber(Math.cos(im), Math.sin(im))).times(Math.exp(re));
    }

    /**
     * Gives the logarithm.
     *
     * @return The logarithm of this {@code ComplexeNumber}.
     */
    public ComplexNumber ln() {
        return new ComplexNumber(Math.log(module()), arg());
    }

    /**
     * Gives a power of this complex number.
     *
     * @param <T> A class that extends {@code Number}.
     * @param power The {@code double} representing the power.
     *
     * @return This {@code ComplexeNumber} rised to the {@code n}th power.
     */
    public <T extends Number> ComplexNumber pow(final T power) {
        return ln().times(power.doubleValue()).exp();
    }

    /**
     * Gives the complex power.
     *
     * @param power The {@code ComplexNumber} representing the power.
     *
     * @return This {@code ComplexeNumber} raised to the power {@code power}.
     */
    public ComplexNumber pow(final ComplexNumber power) {
        return ln().times(power).exp();
    }

    /**
     * Gives the cosine.
     *
     * @return The cosine of this {@code ComplexeNumber}.
     */
    public ComplexNumber cos() {
        return times(I).cosh();
    }

    /**
     * Gives the sine.
     *
     * @return The sine of this {@code ComplexeNumber}.
     */
    public ComplexNumber sin() {
        return times(I).sinh().times(I.opposite());
    }

    /**
     * Gives the tangent.
     *
     * @return The tangent of this {@code ComplexeNumber}.
     */
    public ComplexNumber tan() {
        return times(I).tanh().opposite().times(I);
    }

    /**
     * Gives the hyperbolic cosine.
     *
     * @return The hyperbolic cosine of this {@code ComplexeNumber}.
     */
    public ComplexNumber cosh() {
        return exp().add(opposite().exp()).times(0.5);
    }

    /**
     * Gives the hyperbolic sine.
     *
     * @return The hyperbolic sine of this {@code ComplexeNumber}.
     */
    public ComplexNumber sinh() {
        return exp().minus(opposite().exp()).times(0.5);
    }

    /**
     * Gives the hyperbolic tangent.
     *
     * @return The hyperbolic tangent of this {@code ComplexeNumber}.
     */
    public ComplexNumber tanh() {
        return times(2).exp().add(1).invert().times(-2).add(1);
    }

    /**
     * Test nullity.
     *
     * @return {@code True} if real and imaginary part are zero, {@code False}
     * otherwise.
     */
    public boolean isNull() {
        return re == 0 && im == 0;
    }

    /**
     * Test nullity with accuracy defined.
     *
     * @param <T> A class that extends {@code Number}.
     * @param accuracy Accuracy.
     *
     * @return {@code True} if its module is less or equal than
     * {@code accuracy}, {@code False} otherwise.
     */
    public <T extends Number> boolean isAboutNull(final T accuracy) {
        return module() <= accuracy.doubleValue();
    }

    /**
     * Test equality.
     *
     * @param <T> A class that extends {@code Number}.
     * @param d The {@code double} with with to test equality.
     *
     * @return {@code True} if it equals {@code d}, {@code False} otherwise.
     */
    public <T extends Number> boolean equals(final T d) {
        return re == d.doubleValue() && im == 0;
    }

    /**
     * Test equality with a {@code ComplexNumber}.
     *
     * @param c The {@code ComplexNumber} with with to test equality.
     *
     * @return {@code True} if it equals {@code c}, {@code False} otherwise.
     */
    public boolean equals(final ComplexNumber c) {
        return re == c.getReal() && im == c.getImaginary();
    }

    /**
     * Test equality with a {@code ComplexNumber} with determined accuracy.
     *
     * @param <T> A class that extends {@code Number}.
     * @param c The {@code ComplexNumber} with with to test equality.
     * @param accuracy Accuracy for equality.
     *
     * @return {@code True} if the modulus of its difference with {@code c} is
     * less or equal to {@code accuracy}, {@code False} otherwise.
     */
    public <T extends Number> boolean aboutEquals(final ComplexNumber c, final T accuracy) {
        return minus(c).module() <= accuracy.doubleValue();
    }

    /**
     * Test if the {@code ComplexNumber} is finite.
     *
     * @return {@code True} if the real part and the imaginary part are both
     * finite, {@code False} otherwise.
     */
    public boolean isFinite() {
        return Double.isFinite(re) && Double.isFinite(im);
    }

    /**
     * Test if the {@code ComplexNumber} has zero as real part and a finite
     * imaginary part.
     *
     * @return {@code True} if the real part is zero and if the imaginary part
     * is finite, {@code False} otherwise.
     */
    public boolean isImaginary() {
        return re == 0 && Double.isFinite(im);
    }

    /**
     * Test if the {@code ComplexNumber} has zero as imaginary part and a finite
     * real part.
     *
     * @return {@code True} if the imaginary part is zero and if the real part
     * is finite, {@code False} otherwise.
     */
    public boolean isReal() {
        return im == 0 && Double.isFinite(re);
    }

    /**
     * Test if the {@code ComplexNumber} is a Not-a-Number value.
     *
     * @return {@code True} if the real part or imaginary the part is
     * {@code NaN}, {@code False} otherwise.
     */
    public boolean isNaN() {
        return re != re || im != im;
    }

    /**
     * A [@code String} representation of the {@code ComplexNumber}.
     *
     * @return {@code NaN} if the real or imaginary part is {@code NaN},
     * otherwise a numeric representation of the {@code ComplexNumber}.
     */
    @Override
    public String toString() {
        if (isNaN()) {
            return "NaN";
        } else if (im == 0) {
            return String.valueOf(re);
        } else if (re == 0) {
            return String.valueOf(im) + "i";
        } else {
            return String.valueOf(re) + (im < 0 ? "-" : "+") + String.valueOf(Math.abs(im)) + "i";
        }
    }
}
