package maths.homology;

import java.util.Comparator;
import java.util.HashMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class representing the homology associated with a bigraded differential
 * complex.
 *
 * @author flo
 */
public class BiGradedHomology {

    private final HashMap<Integer, GradedHomology> biGradedHomology = new HashMap<>();

    private int firstjGrad = Integer.MAX_VALUE;
    private int lastjGrad = Integer.MIN_VALUE;

    /**
     * Default constructor, creating an empty bigraded homology.
     */
    public BiGradedHomology() {
    }

    /**
     * Constructor creating a garded homology.
     *
     * @param iGrad The i's homology group graduation.
     * @param jGrad The j's homology group graduation.
     * @param homology The homology group.
     */
    public BiGradedHomology(final int iGrad, final int jGrad, final Homology homology) {
        setHomologyAt(iGrad, jGrad, homology);
    }

    /**
     * Adds a graded homology group.
     *
     * @param jGrad The j's graded homology group graduation.
     * @param homology The graded homology group.
     */
    public void setHomologyAt(final int jGrad, final GradedHomology homology) {
        if (homology.isEmpty()) {
            return;
        }

        biGradedHomology.put(jGrad, homology);
        if (firstjGrad > jGrad) {
            firstjGrad = jGrad;
        }
        if (lastjGrad < jGrad) {
            lastjGrad = jGrad;
        }
    }

    /**
     * Adds an homology group.
     *
     * @param iGrad The i's homology group graduation.
     * @param jGrad The j's homology group graduation.
     * @param homology The homology group.
     */
    public void setHomologyAt(final int iGrad, final int jGrad, final Homology homology) {
        if (homology.isEmpty()) {
            return;
        }

        if (biGradedHomology.containsKey(jGrad)) {
            biGradedHomology.get(jGrad).setHomologyAt(iGrad, homology);
        } else {
            biGradedHomology.put(jGrad, new GradedHomology(iGrad, homology));
        }

        if (firstjGrad > jGrad) {
            firstjGrad = jGrad;
        }
        if (lastjGrad < jGrad) {
            lastjGrad = jGrad;
        }
    }

    /**
     * Tells if the homology group is null.
     *
     * @return {@code True} if the homology group is null, {@code False}
     * otherwise.
     */
    public boolean isEmpty() {
        return biGradedHomology.isEmpty();
    }

    /**
     * Tells if one homology group is null.
     *
     * @param jGrad The graduation.
     *
     * @return {@code True} if the homology group is null, {@code False}
     * otherwise.
     */
    public boolean isEmptyAt(final int jGrad) {
        return biGradedHomology.containsKey(jGrad);
    }

    /**
     * Gives the first i graduation where the homology is non null or
     * {@code Integer.MAX_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getFirstiGrad() {
        return biGradedHomology.values().stream()
                .map(GradedHomology::getFirstGrad)
                .min(Comparator.naturalOrder()).get();
    }

    /**
     * Gives the last i graduation where the homology is non null or
     * {@code Integer.MIN_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getLastiGrad() {
        return biGradedHomology.values().stream()
                .map(GradedHomology::getLastGrad)
                .max(Comparator.naturalOrder()).get();
    }

    /**
     * Gives the first j graduation where the homology is non null or
     * {@code Integer.MAX_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getFirstjGrad() {
        return firstjGrad;
    }

    /**
     * Gives the last j graduation where the homology is non null or
     * {@code Integer.MIN_VALUE} if none.
     *
     * @return The graduation.
     */
    public int getLastjGrad() {
        return lastjGrad;
    }

    /**
     * Gives one graded homology group.
     *
     * @param jGrad The j's group graduation.
     *
     * @return The homology group.
     */
    public GradedHomology getjGradedHomology(final int jGrad) {
        return biGradedHomology.getOrDefault(jGrad, new GradedHomology());
    }

    /**
     * Gives one bigraded homology group.
     *
     * @param iGrad The i's group graduation.
     * @param jGrad The j's group graduation.
     *
     * @return The homology group.
     */
    public Homology getHomologyAt(final int iGrad, final int jGrad) {
        return getjGradedHomology(jGrad).getHomologyAt(iGrad);
    }

    /**
     * All the i graduations where the homology is not null.
     *
     * @return A {@code Set<Integer>} of the graduations.
     */
    public Set<Integer> getNotNulliGrads() {
        return biGradedHomology.values().stream()
                .map(GradedHomology::getNotNullGrads)
                .reduce(new TreeSet<>(), (s, t) -> {
                    s.addAll(t);
                    return new TreeSet<>(s);
                });
    }

    /**
     * All the j graduations where the homology is not null.
     *
     * @return A {@code Set<Integer>} of the graduations.
     */
    public Set<Integer> getNotNulljGrads() {
        return biGradedHomology.keySet();
    }

    /**
     * Tells if one bigraded homology group is null.
     *
     * @param iGrad The i graduation.
     * @param jGrad The j graduation.
     *
     * @return {@code True} if the homology group is null, {@code False}
     * otherwise.
     */
    public boolean isEmptyAt(final int iGrad, int jGrad) {
        return getHomologyAt(iGrad, jGrad).isEmpty();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "0";
        }

        final TreeSet<Integer> sortedi = new TreeSet<>(getNotNulliGrads());
        final NavigableSet<Integer> sortedj = new TreeSet<>(getNotNulljGrads()).descendingSet();
        final StringBuilder text = new StringBuilder("j\\i");

        sortedi.stream().map(i -> "\t\t" + i).forEach(txt -> text.append(txt));
        sortedj.stream().peek(j -> text.append('\n').append(j))
                .forEach(j -> sortedi.forEach(i
                -> text.append("\t\t").append(isEmptyAt(i, j) ? "" : getHomologyAt(i, j))));

        return text.toString();
    }
}
