package maths.functions;

import java.util.function.Function;
import maths.exceptions.MathsArgumentException;
import maths.exceptions.MathsLoopOverflowException;

/**
 * Static class dealing with approximative roots finding.
 *
 * @author flo
 */
public final class RootFinding {

    private static final double ACCURACY = 1e-10;
    private static final int MAX_ITERATIONS = 1_000;

    /**
     * Non instanciable class.
     */
    private RootFinding() {
    }

    /**
     * Seek for one root in an interval for a continuous function that has
     * opposite signs values on the interval bounds.
     *
     * @param start The starting point of the interval.
     * @param end The ending point of the interval.
     * @param function The function with wich to seek for a root.
     *
     * @return A root in the interval with accurcy equals to {@code ACCURACY}.
     *
     * @throws MathsLoopOverflowException If the number of iterations is greater
     * than {@code MAX_ITERATIONS}.
     * @throws MathsArgumentException If {@code start} or {@code end} isn't
     * finite or if values of the function at the bounds are both non zero and
     * have same sign.
     */
    public static double brent(final double start, final double end, final Function<Double, Double> function) throws MathsLoopOverflowException, MathsArgumentException {
        if (!Double.isFinite(start) || !Double.isFinite(end)) {
            throw new MathsArgumentException("Start and end values must be finite.");
        }

        double fa = function.apply(start);
        double fb = function.apply(end);
        if (fa * fb > 0) {
            throw new MathsArgumentException("Values of the function on the bounds must be zero or have different signs.");
        }

        double a, b, c, fc, s, fs, d = 0;
        boolean flag = true;

        if (Math.abs(fa) < Math.abs(fb)) {
            final double tmp = fa;
            a = end;
            b = start;
            fa = fb;
            fb = tmp;
        } else {
            a = start;
            b = end;
        }
        c = a;
        fc = fa;

        int stop = 0;
        while (Math.abs(a - b) > ACCURACY && fb != 0) {
            if (fa != fc && fb != fc) {
                s = ((c * fb - b * fc) * fa * fa + (b * fc * fc - c * fb * fb) * fa + a * fb * fc * (fb - fc)) / ((fa - fb) * (fa - fc) * (fb - fc));
            } else {
                s = b - fb * (b - a) / (fb - fa);
            }

            boolean isin = (3 * a + b) / 4 <= b ? (3 * a + b) / 4 <= s && s <= b : b <= s && s <= (3 * a + b) / 4;
            if (!isin || (flag && Math.abs(s - b) >= Math.abs(b - c) / 2) || (!flag && Math.abs(s - b) >= Math.abs(c - d) / 2)) {
                s = (a + b) / 2;
                flag = true;
            } else {
                flag = false;
            }

            d = c;
            c = b;
            fs = function.apply(s);

            if (fa * fs < 0) {
                b = s;
                fb = fs;
            } else {
                a = s;
                fa = fs;
            }

            if (Math.abs(fa) < Math.abs(fb)) {
                double tmp = a;
                a = b;
                b = tmp;
                tmp = fa;
                fa = fb;
                fb = tmp;
            }

            if (++stop >= MAX_ITERATIONS) {
                throw new MathsLoopOverflowException("Loop iterated more than " + MAX_ITERATIONS + " times.");
            }
        }

        return b;
    }
}
