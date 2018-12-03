package maths.functions;

import java.util.ArrayList;
import maths.exceptions.MathsArgumentException;
import maths.exceptions.MathsPrecisionException;
import maths.numbers.ComplexNumber;

/**
 *
 * @author flo
 */
public final class ComplexPolySolve {

    /**
     * Non instanciable class.
     */
    private ComplexPolySolve() {
    }

    /**
     * Solves the equation {@code a*x + b = 0}.
     *
     * @param a The first coefficient.
     * @param b The second coefficient.
     * @param accuracy Accuracy.
     *
     * @return An {@code ArrayList<ComplexNumber>} containing the solution.
     *
     * @throws MathsArgumentException If {@code a} is zero or if a coefficient
     * is not finite.
     * @throws MathsPrecisionException If the module of the polynom is greater
     * than {@code accuracy} when the solution found is inserted in.
     */
    public static ArrayList<ComplexNumber> linear(final double a, final double b, final double accuracy) throws MathsArgumentException, MathsPrecisionException {
        if (a == 0) {
            throw new MathsArgumentException("First argument must be non zero.");
        }
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final ArrayList<ComplexNumber> solList = new ArrayList<>(1);
        final ComplexNumber sol = new ComplexNumber(-b / a);

        if (sol.times(a).add(b).isAboutNull(accuracy)) {
            solList.add(sol);
        } else {
            throw new MathsPrecisionException("The solution found is not an accurate root of the equation.");
        }

        return solList;
    }

    /**
     * Solves the equation {@code a*x^2 + b*x + c = 0}.
     *
     * @param a The first coefficient.
     * @param b The second coefficient.
     * @param c The third coefficient.
     * @param accuracy Accuracy.
     *
     * @return An {@code ArrayList<ComplexNumber>} containing the solutions.
     *
     * @throws MathsArgumentException If {@code a} and {@code b} are both zero
     * or if a coefficient is not finite.
     * @throws MathsPrecisionException If the module of the polynom is greater
     * than {@code accuracy} when a solution found is inserted in.
     */
    public static ArrayList<ComplexNumber> quadratic(final double a, final double b, final double c, final double accuracy) throws MathsArgumentException, MathsPrecisionException {
        if (a == 0) {
            return linear(b, c, accuracy);
        }
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final ArrayList<ComplexNumber> solList = new ArrayList<>(2);
        final ComplexNumber sol1, sol2;

        if (c == 0) {
            solList.add(new ComplexNumber());
            solList.addAll(linear(a, b, accuracy));
            return solList;
        }

        final double delta = b * b - 4 * a * c;
        if (delta >= 0) {
            final double sqrt = Math.sqrt(delta);
            sol1 = new ComplexNumber((-b + sqrt) / (2 * a));
            sol2 = new ComplexNumber((-b - sqrt) / (2 * a));
        } else {
            final double real = -b / (2 * a);
            final double imaginary = Math.sqrt(-delta) / (2 * a);
            sol1 = new ComplexNumber(real, imaginary);
            sol2 = new ComplexNumber(real, -imaginary);
        }

        solList.add(new ComplexNumber(sol1));
        solList.add(new ComplexNumber(sol2));

        for (final ComplexNumber sol : solList) {
            if (sol.times(a).add(b).times(sol).add(c).module() > accuracy) {
                throw new MathsPrecisionException("A solution found is not an accurate root of the equation.");
            }
        }

        return solList;
    }

    /**
     * Solves the equation {@code a * x ^ 3 + b * x ^ 2 + c * x + d = 0}.
     *
     * @param a The first coefficient.
     * @param b The second coefficient.
     * @param c The third coefficient.
     * @param d The fourth coefficient.
     * @param accuracy Accuracy.
     *
     * @return An {@code ArrayList<ComplexNumber>} containing the solutions.
     *
     * @throws MathsArgumentException If {@code a}, {@code b} and {@code c} are
     * all zero or if a coefficient is not finite.
     * @throws MathsPrecisionException If the module of the polynom is greater
     * than {@code accuracy} when a solution found is inserted in.
     */
    public static ArrayList<ComplexNumber> cubic(final double a, final double b, final double c, final double d, final double accuracy) throws MathsArgumentException, MathsPrecisionException {
        if (a == 0) {
            return quadratic(b, c, d, accuracy);
        }
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c) || !Double.isFinite(d)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final ArrayList<ComplexNumber> solList = new ArrayList<>(3);
        final ComplexNumber sol1, sol2, sol3;

        if (d == 0) {
            solList.add(new ComplexNumber());
            solList.addAll(quadratic(a, b, c, accuracy));
            return solList;
        }

        final double q = (2 * b * b * b - 9 * a * b * c + 27 * a * a * d) / (27 * a * a * a);
        final double delta = (-27 * a * a * d * d + 18 * a * b * c * d - 4 * a * c * c * c - 4 * b * b * b * d + b * b * c * c) / (a * a * a * a);
        final double varChange = b / (-3 * a);

        if (delta == 0) {
            final double p = (3 * a * c - b * b) / (3 * a * a);
            if (p == 0) {
                sol1 = new ComplexNumber(varChange);
                sol2 = new ComplexNumber(varChange);
                sol3 = new ComplexNumber(varChange);
            } else {
                sol1 = new ComplexNumber(3 * q / p + varChange);
                sol2 = new ComplexNumber((-3 * q) / (2 * p) + varChange);
                sol3 = new ComplexNumber((-3 * q) / (2 * p) + varChange);
            }
        } else if (delta < 0) {
            final double val = Math.sqrt(-delta / 27);
            final ComplexNumber u = new ComplexNumber((-q + val) / 2).cbrt();
            final ComplexNumber v = new ComplexNumber((-q - val) / 2).cbrt();
            final ComplexNumber w = ComplexNumber.J.times(u).add(ComplexNumber.J.conjugate().times(v));
            sol1 = u.add(v).add(varChange);
            sol2 = w.add(varChange);
            sol3 = w.conjugate().add(varChange);
        } else {
            final ComplexNumber u = (new ComplexNumber(-q / 2, Math.sqrt(delta / 27) / 2)).cbrt();
            sol1 = new ComplexNumber(u.getReal() * 2 + varChange);
            sol2 = new ComplexNumber(u.times(ComplexNumber.J).getReal() * 2 + varChange);
            sol3 = new ComplexNumber(u.times(ComplexNumber.J2).getReal() * 2 + varChange);
        }

        solList.add(sol1);
        solList.add(sol2);
        solList.add(sol3);

        for (final ComplexNumber sol : solList) {
            if (sol.times(a).add(b).times(sol).add(c).times(sol).add(d).module() > accuracy) {
                throw new MathsPrecisionException("A solution found is not an accurate root of the equation.");
            }
        }

        return solList;
    }

    /**
     * Solves the equation
     * {@code a * x ^ 4 + b * x ^ 3 + c * x ^ 2 + d * x + e = 0}.
     *
     * @param a The first coefficient.
     * @param b The second coefficient.
     * @param c The third coefficient.
     * @param d The fourth coefficient.
     * @param e The fifth coefficient.
     * @param accuracy Accuracy.
     *
     * @return An {@code ArrayList<ComplexNumber>} containing the solutions.
     *
     * @throws MathsArgumentException If {@code a}, {@code b}, {@code c} and
     * {@code d} are all zero or if a coefficient is not finite.
     * @throws MathsPrecisionException If the module of the polynom is greater
     * than {@code accuracy} when a solution found is inserted in.
     */
    public static ArrayList<ComplexNumber> quartic(final double a, final double b, final double c, final double d, final double e, final double accuracy) throws MathsArgumentException, MathsPrecisionException {
        if (a == 0) {
            return cubic(b, c, d, e, accuracy);
        }
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c) || !Double.isFinite(d) || !Double.isFinite(e)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final ArrayList<ComplexNumber> solList = new ArrayList<>(4);

        if (e == 0) {
            solList.add(new ComplexNumber());
            solList.addAll(cubic(a, b, c, d, accuracy));
            return solList;
        }

        final double p = (8 * a * c - 3 * b * b) / (8 * a * a);
        final double q = (b * b * b - 4 * a * b * c + 8 * a * a * d) / (8 * a * a * a);
        final double r = (256 * a * a * a * e - 64 * a * a * b * d + 16 * a * b * b * c - 3 * b * b * b * b) / (256 * a * a * a * a);
        final ArrayList<ComplexNumber> cubicRoots = cubic(1, 2 * p, p * p - 4 * r, -q * q, accuracy);
        final double varchange = b / (-4 * a);

        final ComplexNumber val1 = cubicRoots.get(0).sqrt();
        final ComplexNumber val2 = cubicRoots.get(1).sqrt();
        final ComplexNumber val3;

        if (val1.times(val2).times(cubicRoots.get(2).sqrt()).add(q).squareModule() < val1.times(val2).times(cubicRoots.get(2).sqrt().opposite()).add(q).squareModule()) {
            val3 = cubicRoots.get(2).sqrt();
        } else {
            val3 = cubicRoots.get(2).sqrt().opposite();
        }

        solList.add(val1.add(val2).add(val3).times(0.5).add(varchange));
        solList.add(val1.minus(val2).minus(val3).times(0.5).add(varchange));
        solList.add(val2.minus(val1).minus(val3).times(0.5).add(varchange));
        solList.add(val3.minus(val1).minus(val2).times(0.5).add(varchange));

        for (final ComplexNumber sol : solList) {
            if (sol.times(a).add(b).times(sol).add(c).times(sol).add(d).times(sol).add(e).module() > accuracy) {
                throw new MathsPrecisionException("A solution found is not an accurate root of the equation.");
            }
        }

        return solList;
    }
}
