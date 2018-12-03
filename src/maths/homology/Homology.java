package maths.homology;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class representing an homology group.
 *
 * @author flo
 */
public final class Homology {

    /**
     * Constant representing a null homology group.
     */
    public static final Homology NULL_HOMOLOGY = new Homology();

    private final int rank;
    private final HashMap<Integer, Integer> torsion = new HashMap<>(0);

    /**
     * Default constructor, creating a null homology group.
     */
    public Homology() {
        rank = 0;
    }

    /**
     * Constructor creating a free abelian homology group.
     *
     * @param rank The rank of the group.
     */
    public Homology(final int rank) {
        this.rank = rank;
    }

    /**
     * Constructor creating an abelian homology group with one torsion
     * generator.
     *
     * @param rank The rank of the group.
     * @param torsion The torsion generator of the group.
     * @param torsionPower The torsion generator power of the group.
     */
    public Homology(final int rank, final int torsion, final int torsionPower) {
        this.rank = rank;
        addTorsion(torsion, torsionPower);
    }

    /**
     * Constructor creating an abelian homology group with torsion elements.
     *
     * @param rank The rank of the group.
     * @param torsions The torsion generators of the group.
     */
    public Homology(final int rank, final int... torsions) {
        this.rank = rank;

        final HashMap<Integer, Integer> torMap = new HashMap<>();
        for (final int i : torsions) {
            torMap.put(i, torMap.containsKey(i) ? torMap.get(i) + 1 : 1);
        }
        torMap.forEach(this::addTorsion);
    }

    /**
     * Constructor creating an abelian homology group with torsion elements.
     *
     * @param rank The rank of the group.
     * @param torsion The torsion generators of the group, mapped to their
     * power.
     */
    public Homology(final int rank, final HashMap<Integer, Integer> torsion) {
        this.rank = rank;
        torsion.forEach(this::addTorsion);
    }

    /**
     * Adds torsion.
     *
     * @param torsion A torsion generator of the group.
     * @param power The power of the torsion generator.
     */
    private void addTorsion(final int torsion, final int power) {
        if (power != 0) {
            this.torsion.put(torsion, power);
        }
    }

    /**
     * Tells if the homology group has rank equals to zero.
     *
     * @return {@code True} if the rank is zero, {@code False} otherwise.
     */
    public boolean isNullRank() {
        return rank == 0;
    }

    /**
     * Tells if the torsion homology subgroup is zero.
     *
     * @return {@code True} if the torsion subgroup is null, {@code False}
     * otherwise.
     */
    public boolean isTorsionFree() {
        return torsion.isEmpty();
    }

    /**
     * Tells if the homology group is null.
     *
     * @return {@code True} if the homology group is null, {@code False}
     * otherwise.
     */
    public boolean isEmpty() {
        return isNullRank() && isTorsionFree();
    }

    /**
     * Gives the rank of the homology group.
     *
     * @return The homology group rank.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gives the generators of the homology torsion subgroup.
     *
     * @return The homology torsion generators.
     */
    public Set<Integer> getTorsionGenerators() {
        return torsion.keySet();
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "0";
        }

        final StringBuilder description = new StringBuilder(1);

        if (!isNullRank()) {
            description.append("Z").append(rank == 1 ? "" : "^" + rank);
            description.append(isTorsionFree() ? "" : '+');
        }

        if (!isTorsionFree()) {
            new TreeSet<>(torsion.keySet())
                    .forEach(key -> description.append(key.intValue())
                    .append('Z').append(torsion.get(key) != 1 ? "^" + torsion.get(key) : "").append('+'));
            description.deleteCharAt(description.length() - 1);
        }

        return description.toString();
    }
}
