package maths.homology;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class representing the homology associated with a graded differential
 * complex.
 *
 * @author flo
 */
public class GradedHomology {

    private final HashMap<Integer, Homology> gradedHomology = new HashMap<>();

    private int firstGrad = Integer.MAX_VALUE;
    private int lastGrad = Integer.MIN_VALUE;

    /**
     * Default constructor, creating an empty graded homology.
     */
    public GradedHomology() {
    }

    /**
     * Constructor creating a garded homology.
     *
     * @param grad The homology group graduation.
     * @param homology The homology group.
     */
    public GradedHomology(final int grad, final Homology homology) {
        setHomologyAt(grad, homology);
    }

    /**
     * Adds an homology group.
     *
     * @param grad The homology group graduation.
     * @param homology The homology group.
     */
    public void setHomologyAt(final int grad, final Homology homology) {
        if (homology.isEmpty()) {
            return;
        }

        gradedHomology.put(grad, homology);
        if (firstGrad > grad) {
            firstGrad = grad;
        }
        if (lastGrad < grad) {
            lastGrad = grad;
        }
    }

    /**
     * Tells if homology groups are all null.
     *
     * @return {@code True} if all the homology groups are null, {@code False}
     * otherwise.
     */
    public boolean isEmpty() {
        return gradedHomology.isEmpty();
    }

    /**
     * Tells if one homology group is null.
     *
     * @param grad The group graduation.
     * @return {@code True} if the homology group is null, {@code False}
     * otherwise.
     */
    public boolean isEmptyAt(final int grad) {
        return gradedHomology.containsKey(grad);
    }

    /**
     * Gives the first graduation where the homology is non null or
     * {@code Integer.MAX_VALUE} if none.
     *
     * @return The first non null graduation.
     */
    public int getFirstGrad() {
        return firstGrad;
    }

    /**
     * Gives the last graduation where the homology is non null or
     * {@code Integer.MIN_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getLastGrad() {
        return lastGrad;
    }

    /**
     * Gives the homology at some graduation.
     *
     * @param grad The graduation.
     *
     * @return The homology group.
     */
    public Homology getHomologyAt(final int grad) {
        return gradedHomology.getOrDefault(grad, Homology.NULL_HOMOLOGY);
    }

    /**
     * All the graduations where the homology is not null.
     *
     * @return A {@code Set<Integer>} of the graduations.
     */
    public Set<Integer> getNotNullGrads() {
        return gradedHomology.keySet();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "0";
        }

        final StringBuilder text = new StringBuilder().append('i');
        final TreeSet<Integer> grads = new TreeSet<>(getNotNullGrads());

        grads.forEach(key -> text.append("\t\t").append(key));
        text.append('\n');
        grads.forEach(key -> text.append("\t\t").append(gradedHomology.get(key)));

        return text.toString();
    }
}
