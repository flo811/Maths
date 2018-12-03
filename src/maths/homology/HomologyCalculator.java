package maths.homology;

/**
 *
 * @author flo
 */
@FunctionalInterface
public interface HomologyCalculator {

    abstract GradedHomology calculateHomology(final DifferentialComplex complex);
}
