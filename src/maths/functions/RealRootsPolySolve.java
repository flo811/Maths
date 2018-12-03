package maths.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import maths.exceptions.MathsArgumentException;
import maths.exceptions.MathsLoopOverflowException;
import maths.exceptions.MathsPrecisionException;
import static maths.functions.RootFinding.brent;
import maths.numbers.Numbers;

/**
 * Static class dealing with real roots resolution of real polynomials;
 *
 * @author flo
 */
public final class RealRootsPolySolve {

    private static final double DEFAULT_ACCURACY = 1e-7;

    /**
     * Non instanciable class.
     */
    private RealRootsPolySolve() {
    }

    /**
     * Find a real solution of the equation {@code a*x + b = 0} if any in an
     * interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     * @param accuracy Accuracy.
     *
     * @return The root of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a} is zero or if a coefficient
     * is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code accuracy} when the solution found is inserted in.
     */
    public static ArrayList<Double> linear(final double start, final double end, final double a, final double b, final double accuracy) throws MathsArgumentException, MathsPrecisionException {
        if (a == 0) {
            throw new MathsArgumentException("First argument must be non zero.");
        }
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final ArrayList<Double> solList = new ArrayList<>(1);
        final double sol = -b / a;

        if (start <= sol && sol <= end) {
            if (Math.abs(a * sol + b) > accuracy) {
                throw new MathsPrecisionException("The solution found is not an accurate root of the equation.");
            }
            solList.add(sol);
        }

        return solList;
    }

    /**
     * Find a real solution of the equation {@code a*x + b = 0} if any in an
     * interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     *
     * @return The root of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a} is zero or if a coefficient
     * is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code DEFAULT_ACCURACY} when the solution found is inserted
     * in.
     */
    public static ArrayList<Double> linear(final double start, final double end, final double a, final double b) throws MathsArgumentException, MathsPrecisionException {
        return linear(start, end, a, b, DEFAULT_ACCURACY);
    }

    /**
     * Find real solutions of the equation {@code a*x^2 + b*x + c = 0} in an
     * interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     * @param c Third coefficient.
     * @param accuracy Accuracy.
     *
     * @return Roots of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a} and {@code b} are both zero
     * or if a coefficient is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code accuracy} when a solution found is inserted in.
     */
    public static ArrayList<Double> quadratic(final double start, final double end, final double a, final double b, final double c, final double accuracy) throws MathsArgumentException, MathsPrecisionException {
        if (a == 0) {
            return linear(start, end, b, c, accuracy);
        }
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final ArrayList<Double> solList = new ArrayList<>(0);
        final double sol1, sol2;

        final double delta = b * b - 4 * a * c;
        if (delta == 0) {
            sol1 = -0.5 * b / a;
            if (start <= sol1 && sol1 <= end) {
                solList.add(sol1);
            }
        } else if (delta > 0) {
            final double sqrt = Math.sqrt(delta);
            sol1 = 0.5 * (-b + sqrt) / a;
            sol2 = 0.5 * (-b - sqrt) / a;
            if (start <= sol1 && sol1 <= end) {
                solList.add(sol1);
            }
            if (start <= sol2 && sol2 <= end) {
                solList.add(sol2);
            }
        }

        for (final double sol : solList) {
            if (Math.abs((a * sol + b) * sol + c) > accuracy) {
                throw new MathsPrecisionException("A solution found is not an accurate root of the equation.");
            }
        }

        return solList;
    }

    /**
     * Find real solutions of the equation {@code a*x^2 + b*x + c = 0} in an
     * interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     * @param c Third coefficient.
     *
     * @return Roots of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a} and {@code b} are both zero
     * or if a coefficient is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code DEFAULT_ACCURACY} when a solution found is inserted
     * in.
     */
    public static ArrayList<Double> quadratic(final double start, final double end, final double a, final double b, final double c) throws MathsArgumentException, MathsPrecisionException {
        return quadratic(start, end, a, b, c, DEFAULT_ACCURACY);
    }

    /**
     * Find real solutions of the equation {@code a*x^3 + b*x^2 + c*x + d = 0}
     * in an interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     * @param c Third coefficient.
     * @param d Fourth coefficient.
     * @param accuracy Accuracy.
     *
     * @return Roots of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a}, {@code b} and {@code c} are
     * all zero or if a coefficient is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code accuracy} when a solution found is inserted in.
     * @throws MathsLoopOverflowException If Brent's method loops too many
     * times.
     */
    public static ArrayList<Double> cubic(final double start, final double end, final double a, final double b, final double c, final double d, final double accuracy) throws MathsArgumentException, MathsPrecisionException, MathsLoopOverflowException {
        if (a == 0) {
            return quadratic(start, end, b, c, d, accuracy);
        }
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c) || !Double.isFinite(d)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final double inf, sup;
        if (start == Double.NEGATIVE_INFINITY) {
            inf = -1 - Numbers.maxAbs(b, c, d) / Math.abs(a);
        } else {
            inf = start;
        }
        if (end == Double.POSITIVE_INFINITY) {
            sup = 1 + Numbers.maxAbs(b, c, d) / Math.abs(a);
        } else {
            sup = end;
        }

        final ArrayList<Double> solList = new ArrayList<>(0);
        final Function<Double, Double> function = x -> ((a * x + b) * x + c) * x + d;
        double fStart = function.apply(inf);
        double fEnd = function.apply(sup);
        if (Math.abs(fStart) < accuracy) {
            solList.add(inf);
            fStart = 0;
        }
        if (Math.abs(fEnd) < accuracy) {
            solList.add(sup);
            fEnd = 0;
        }

        final ArrayList<Double> nullDerivate = quadratic(inf, sup, 3 * a, 2 * b, c, accuracy);
        Collections.sort(nullDerivate);
        switch (nullDerivate.size()) {
            case 0:
                if (fStart * fEnd < 0) {
                    solList.add(brent(inf, sup, function));
                }
                break;
            case 1:
                double fDer = function.apply(nullDerivate.get(0));
                if (Math.abs(fDer) < accuracy) {
                    solList.add(nullDerivate.get(0));
                    fDer = 0;
                }
                if (fStart * fDer < 0) {
                    solList.add(brent(inf, nullDerivate.get(0), function));
                }
                if (fDer * fEnd < 0) {
                    solList.add(brent(nullDerivate.get(0), sup, function));
                }
                break;
            case 2:
                double fDer1 = function.apply(nullDerivate.get(0));
                double fDer2 = function.apply(nullDerivate.get(1));
                if (Math.abs(fDer1) < accuracy) {
                    solList.add(nullDerivate.get(0));
                    fDer1 = 0;
                }
                if (Math.abs(fDer2) < accuracy) {
                    solList.add(nullDerivate.get(1));
                    fDer2 = 0;
                }
                if (fStart * fDer1 < 0) {
                    solList.add(brent(inf, nullDerivate.get(0), function));
                }
                if (fDer1 * fDer2 < 0) {
                    solList.add(brent(nullDerivate.get(0), nullDerivate.get(1), function));
                }
                if (fDer2 * fEnd < 0) {
                    solList.add(brent(nullDerivate.get(1), sup, function));
                }
                break;
        }

        for (final double sol : solList) {
            if (Math.abs(((a * sol + b) * sol + c) * sol + d) > accuracy) {
                throw new MathsPrecisionException("A solution found is not an accurate root of the equation.");
            }
        }

        return solList;
    }

    /**
     * Find real solutions of the equation {@code a*x^3 + b*x^2 + c*x + d = 0}
     * in an interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     * @param c Third coefficient.
     * @param d Fourth coefficient.
     *
     * @return Roots of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a}, {@code b} and {@code c} are
     * all zero or if a coefficient is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code DEFAULT_ACCURACY} when a solution found is inserted
     * in.
     * @throws MathsLoopOverflowException If Brent's method loops too many
     * times.
     */
    public static ArrayList<Double> cubic(final double start, final double end, final double a, final double b, final double c, final double d) throws MathsArgumentException, MathsPrecisionException, MathsLoopOverflowException {
        return cubic(start, end, a, b, c, d, DEFAULT_ACCURACY);
    }

    /**
     * Find real solutions of the equation
     * {@code a*x^4 + b*x^3 + c*x^2 + d*x + e = 0} in an interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     * @param c Third coefficient.
     * @param d Fourth coefficient.
     * @param e Fifth coefficient.
     * @param accuracy Accuracy.
     *
     * @return Roots of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a}, {@code b}, {@code c} and
     * {@code d} are all zero or if a coefficient is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code accuracy} when a solution found is inserted in.
     * @throws MathsLoopOverflowException If Brent's method loops too many
     * times.
     */
    public static ArrayList<Double> quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e, final double accuracy) throws MathsArgumentException, MathsPrecisionException, MathsLoopOverflowException {
        if (a == 0) {
            return cubic(start, end, b, c, d, e, accuracy);
        }
        if (!Double.isFinite(a) || !Double.isFinite(b) || !Double.isFinite(c) || !Double.isFinite(d) || !Double.isFinite(e)) {
            throw new MathsArgumentException("All coefficients must be finite.");
        }

        final double inf, sup;
        if (start == Double.NEGATIVE_INFINITY) {
            inf = -1 - Numbers.maxAbs(b, c, d, e) / Math.abs(a);
        } else {
            inf = start;
        }
        if (end == Double.POSITIVE_INFINITY) {
            sup = 1 + Numbers.maxAbs(b, c, d, e) / Math.abs(a);
        } else {
            sup = end;
        }

        final ArrayList<Double> solList = new ArrayList<>(0);
        final Function<Double, Double> function = x -> (((a * x + b) * x + c) * x + d) * x + e;
        double fStart = function.apply(inf);
        double fEnd = function.apply(sup);
        if (Math.abs(fStart) < accuracy) {
            solList.add(inf);
            fStart = 0;
        }
        if (Math.abs(fEnd) < accuracy) {
            solList.add(sup);
            fEnd = 0;
        }

        final ArrayList<Double> nullDerivate = cubic(inf, sup, 4 * a, 3 * b, 2 * c, d, accuracy);
        Collections.sort(nullDerivate);
        switch (nullDerivate.size()) {
            case 0:
                if (fStart * fEnd < 0) {
                    solList.add(brent(inf, sup, function));
                }
                break;
            case 1:
                double fDer = function.apply(nullDerivate.get(0));
                if (Math.abs(fDer) < accuracy) {
                    solList.add(nullDerivate.get(0));
                    fDer = 0;
                }
                if (fStart * fDer < 0) {
                    solList.add(brent(inf, nullDerivate.get(0), function));
                }
                if (fDer * fEnd < 0) {
                    solList.add(brent(nullDerivate.get(0), sup, function));
                }
                break;
            case 2: {
                double fDer1 = function.apply(nullDerivate.get(0));
                double fDer2 = function.apply(nullDerivate.get(1));
                if (Math.abs(fDer1) < accuracy) {
                    solList.add(nullDerivate.get(0));
                    fDer1 = 0;
                }
                if (Math.abs(fDer2) < accuracy) {
                    solList.add(nullDerivate.get(1));
                    fDer2 = 0;
                }
                if (fStart * fDer1 < 0) {
                    solList.add(brent(inf, nullDerivate.get(0), function));
                }
                if (fDer1 * fDer2 < 0) {
                    solList.add(brent(nullDerivate.get(0), nullDerivate.get(1), function));
                }
                if (fDer2 * fEnd < 0) {
                    solList.add(brent(nullDerivate.get(1), sup, function));
                }
                break;
            }
            case 3: {
                double fDer1 = function.apply(nullDerivate.get(0));
                double fDer2 = function.apply(nullDerivate.get(1));
                double fDer3 = function.apply(nullDerivate.get(2));
                if (Math.abs(fDer1) < accuracy) {
                    solList.add(nullDerivate.get(0));
                    fDer1 = 0;
                }
                if (Math.abs(fDer2) < accuracy) {
                    solList.add(nullDerivate.get(1));
                    fDer2 = 0;
                }
                if (Math.abs(fDer3) < accuracy) {
                    solList.add(nullDerivate.get(2));
                    fDer3 = 0;
                }
                if (fStart * fDer1 < 0) {
                    solList.add(brent(inf, nullDerivate.get(0), function));
                }
                if (fDer1 * fDer2 < 0) {
                    solList.add(brent(nullDerivate.get(0), nullDerivate.get(1), function));
                }
                if (fDer2 * fDer3 < 0) {
                    solList.add(brent(nullDerivate.get(1), nullDerivate.get(2), function));
                }
                if (fDer3 * fEnd < 0) {
                    solList.add(brent(nullDerivate.get(2), sup, function));
                }
                break;
            }
        }

        for (final double sol : solList) {
            if (Math.abs((((a * sol + b) * sol + c) * sol + d) * sol + e) > accuracy) {
                throw new MathsPrecisionException("A solution found is not an accurate root of the equation.");
            }
        }

        return solList;
    }

    /**
     * Find real solutions of the equation
     * {@code a*x^4 + b*x^3 + c*x^2 + d*x + e = 0} in an interval.
     *
     * @param start Start point of the interval.
     * @param end End point of the interval.
     * @param a First coefficient.
     * @param b Second coefficient.
     * @param c Third coefficient.
     * @param d Fourth coefficient.
     * @param e Fifth coefficient.
     *
     * @return Roots of the equation in the interval if any.
     *
     * @throws MathsArgumentException If {@code a}, {@code b}, {@code c} and
     * {@code d} are all zero or if a coefficient is not finite.
     * @throws MathsPrecisionException If the absolute value of the polynom is
     * greater than {@code DEFAULT_ACCURACY} when a solution found is inserted
     * in.
     * @throws MathsLoopOverflowException If Brent's method loops too many
     * times.
     */
    public static ArrayList<Double> quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e) throws MathsArgumentException, MathsPrecisionException, MathsLoopOverflowException {
        return quartic(start, end, a, b, c, d, e, DEFAULT_ACCURACY);
    }
}
