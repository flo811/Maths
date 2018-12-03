package maths.intersection;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/**
 *
 * @author flo
 *
 * @param <T> The class of the first shape wich must extend {@code Shape}.
 * @param <U> The class of the second shape wich must extend {@code Shape}.
 */
public class SimpleIntersection2D<T extends Shape, U extends Shape> {

    protected final T curve1;
    protected final U curve2;

    protected final Point2D intersection;

    /**
     * Creates a new {@code SimpleIntersection2D}.
     *
     * @param curve1 The first curve.
     * @param curve2 The second curve.
     * @param intersection A {@code Point2D} representing the location of the
     * intersection.
     */
    public SimpleIntersection2D(final T curve1, final U curve2, final Point2D intersection) {
        this.curve1 = curve1;
        this.curve2 = curve2;
        this.intersection = intersection;
    }

    /**
     * Returns the first curve.
     *
     * @return The first curve.
     */
    public final T getCurve1() {
        return curve1;
    }

    /**
     * Returns the second curve.
     *
     * @return The second curve.
     */
    public final U getCurve2() {
        return curve2;
    }

    /**
     * Returns the intersection point.
     *
     * @return A {@code Point2D} representing the intersection point.
     */
    public final Point2D getIntersection() {
        return intersection;
    }
}
