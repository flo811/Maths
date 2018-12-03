package maths.homology;

import java.util.Comparator;
import java.util.HashMap;
import maths.matrix.IntegerMatrix;

/**
 * Class representing a bigraded differential complex.
 *
 * @author flo
 */
public final class DifferentialBiComplex {

    private final HashMap<Integer, DifferentialComplex> biComplex = new HashMap<>();

    private int firstjGrad = Integer.MAX_VALUE;
    private int lastjGrad = Integer.MIN_VALUE;

    /**
     * Default constructor, creating an empty bigraded differential complex.
     */
    public DifferentialBiComplex() {
    }

    /**
     * Creates a new {@code DifferentialBiComplex} with an initial value at j
     * graduation.
     *
     * @param jGrad The graduation.
     * @param complex The differential complex.
     */
    public DifferentialBiComplex(final int jGrad, final DifferentialComplex complex) {
        setDifferentialComplex(jGrad, complex);
    }

    /**
     * Creates a new {@code DifferentialBiComplex} with an initial value at
     * (i,j) bigraduation.
     *
     * @param iGrad The i graduation.
     * @param jGrad The j graduation.
     * @param matrix The matrix.
     */
    public DifferentialBiComplex(final int iGrad, final int jGrad, final IntegerMatrix matrix) {
        setijDiff(iGrad, jGrad, matrix);
    }

    /**
     * Returns the differential at j graduation.
     *
     * @param jGrad The j graduation.
     *
     * @return The diffrerntial complex with j graduation.
     */
    public DifferentialComplex getDiff(final int jGrad) {
        return biComplex.getOrDefault(jGrad, new DifferentialComplex());
    }

    /**
     * Returns the differential at (i,j) bigraduation.
     *
     * @param iGrad The i graduation.
     * @param jGrad The j graduation.
     *
     * @return The matrix.
     */
    public IntegerMatrix getijDiff(final int iGrad, final int jGrad) {
        return biComplex.getOrDefault(iGrad, new DifferentialComplex()).getDiff(iGrad);
    }

    /**
     * Sets a differential complex at j graduation.
     *
     * @param jGrad The graduation.
     * @param complex The differential complex.
     */
    public void setDifferentialComplex(final int jGrad, final DifferentialComplex complex) {
        if (complex.isEmpty()) {
            return;
        }

        biComplex.put(jGrad, complex);
        if (firstjGrad > jGrad) {
            firstjGrad = jGrad;
        }
        if (lastjGrad < jGrad) {
            lastjGrad = jGrad;
        }
    }

    /**
     * Sets a differential at (i,j) bigraduation.
     *
     * @param iGrad The i graduation.
     * @param jGrad The j graduation.
     * @param diff The matrix.
     */
    public synchronized void setijDiff(final int iGrad, final int jGrad, final IntegerMatrix diff) {
        if (diff.isEmpty()) {
            return;
        }

        if (biComplex.containsKey(jGrad)) {
            biComplex.get(jGrad).setiDiffAt(iGrad, diff);
        } else {
            setDifferentialComplex(jGrad, new DifferentialComplex(iGrad, diff));
            if (firstjGrad > jGrad) {
                firstjGrad = jGrad;
            }
            if (lastjGrad < jGrad) {
                lastjGrad = jGrad;
            }
        }
    }

    /**
     * Returns the bigraded homology of this bigraded differential complex.
     *
     * @param calculator The {@code Calculator} used to calculate the homology.
     *
     * @return The bigraded homology.
     */
    public BiGradedHomology getHomology(final HomologyCalculator calculator) {
        final BiGradedHomology homology = new BiGradedHomology();
        biComplex.keySet().parallelStream().forEach(jGrad -> {
            final GradedHomology jHomology = biComplex.get(jGrad).getHomology(calculator);
            jHomology.getNotNullGrads()
                    .forEach(iGrad -> homology.setHomologyAt(iGrad, jGrad, jHomology.getHomologyAt(iGrad)));
        });

        return homology;
    }

    /**
     * Returns the first i graduation with non null differential or
     * {@code Integer.MAX_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getFirstiGrad() {
        return biComplex.values().stream()
                .map(DifferentialComplex::getFirstGrad)
                .min(Comparator.naturalOrder()).get();
    }

    /**
     * Returns the last i graduation with non null differential or
     * {@code Integer.MIN_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getLastiGrad() {
        return biComplex.values().stream()
                .map(DifferentialComplex::getLastGrad)
                .max(Comparator.naturalOrder()).get();
    }

    /**
     * Returns the first j graduation with non null differential or
     * {@code Integer.MAX_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getFirstjGrad() {
        return firstjGrad;
    }

    /**
     * Returns the last j graduation with non null differential or
     * {@code Integer.MIN_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getLastjGrad() {
        return lastjGrad;
    }

    /**
     * Tells if the differential bicomplex is empty.
     *
     * @return {@code true} if the differential bicomplex is empty,
     * {@code false} otherwise.
     */
    public boolean isEmpty() {
        return biComplex.isEmpty();
    }
}
