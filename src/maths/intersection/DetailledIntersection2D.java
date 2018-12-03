package maths.intersection;

import java.io.Serializable;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/**
 * Class representing an detailled intersection point of two curves, with
 * parameters and derivates.
 *
 * @author flo
 *
 * @param <T> The class of the first shape wich must extend {@code Shape}.
 * @param <U> The class of the second shape wich must extend {@code Shape}.
 */
public class DetailledIntersection2D<T extends Shape, U extends Shape> extends SimpleIntersection2D implements Serializable{

    protected final Point2D curve1Derivate, curve2Derivate;
    protected final double curve1Parameter, curve2Parameter;

    /**
     * Creates a new {@code DetailledIntersection2D}.
     *
     * @param curve1 The first curve.
     * @param curve2 The second curve.
     * @param intersection A {@code Point2D} representing the location of the
     * intersection.
     * @param curve1Parameter The parameter of the first curve.
     * @param curve2Parameter The parameter of the second curve.
     * @param curve1Derivate A {@code Point2D} representing the derivate of the
     * first curve at the intersection.
     * @param curve2Derivate A {@code Point2D} representing the derivate of the
     * second curve at the intersection.
     */
    public DetailledIntersection2D(final T curve1, final U curve2, final Point2D intersection, final double curve1Parameter, final double curve2Parameter, final Point2D curve1Derivate, final Point2D curve2Derivate) {
        super(curve1, curve2, intersection);

        this.curve1Parameter = curve1Parameter;
        this.curve2Parameter = curve2Parameter;
        this.curve1Derivate = curve1Derivate;
        this.curve2Derivate = curve2Derivate;
    }

    /**
     * Returns the first parameter.
     *
     * @return The first parameter.
     */
    public final double getCurve1Parameter() {
        return curve1Parameter;
    }

    /**
     * Returns the second parameter.
     *
     * @return The second parameter.
     */
    public final double getCurve2Parameter() {
        return curve2Parameter;
    }

    /**
     * Returns the first curve derivate.
     *
     * @return A {@code Point2D} representing the derivate of the first curve at
     * the intersection.
     */
    public final Point2D getCurve1Derivate() {
        return curve1Derivate;
    }

    /**
     * Returns the second curve derivate.
     *
     * @return A {@code Point2D} representing the derivate of the second curve
     * at the intersection.
     */
    public final Point2D getCurve2Derivate() {
        return curve2Derivate;
    }
}
