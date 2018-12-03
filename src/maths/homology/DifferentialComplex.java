package maths.homology;

import java.util.HashMap;
import maths.matrix.IntegerMatrix;

/**
 * Class representing a differential complex.
 *
 * @author flo
 */
public final class DifferentialComplex {

    private final HashMap<Integer, IntegerMatrix> differential = new HashMap<>();

    private int firstGrad = Integer.MAX_VALUE;
    private int lastGrad = Integer.MIN_VALUE;

    /**
     * Default constructor, creating an empty differential complex.
     */
    public DifferentialComplex() {
    }

    public DifferentialComplex(final int grad, final IntegerMatrix diff) {
        differential.put(grad, diff);
        firstGrad = grad;
        lastGrad = grad;
    }

    /**
     * Returns the differential at a certain graduation.
     *
     * @param grad The graduation.
     *
     * @return The matrix.
     */
    public IntegerMatrix getDiff(final int grad) {
        return differential.getOrDefault(grad, IntegerMatrix.EMPTY);
    }

    /**
     * Sets a differential at a certain graduation.
     *
     * @param grad The graduation.
     * @param diff The matrix.
     */
    public void setiDiffAt(final int grad, final IntegerMatrix diff) {
        if (diff.isEmpty()) {
            return;
        }

        differential.put(grad, diff);
        if (firstGrad > grad) {
            firstGrad = grad;
        }
        if (lastGrad < grad) {
            lastGrad = grad;
        }
    }

    /**
     * Returns the graded homology of this differential complex.
     *
     * @param calculator The {@code Calculator} used to calculate the homology.
     *
     * @return The graded homology.
     */
    public GradedHomology getHomology(final HomologyCalculator calculator) {
        return calculator.calculateHomology(this);
    }

    /**
     * Returns the first graduation with non null differential or
     * {@code Integer.MAX_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getFirstGrad() {
        return firstGrad;
    }

    /**
     * Returns the last graduation with non null differential or
     * {@code Integer.MIN_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getLastGrad() {
        return lastGrad;
    }

    /**
     * Tells if the differential complex is empty.
     *
     * @return {@code true} if the differential complex is empty, {@code false}
     * otherwise.
     */
    public boolean isEmpty() {
        return differential.isEmpty();
    }
}
