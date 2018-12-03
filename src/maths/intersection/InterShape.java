package maths.intersection;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import maths.exceptions.MathsArgumentException;
import maths.exceptions.MathsLoopOverflowException;
import maths.exceptions.MathsPrecisionException;
import maths.functions.RealRootsPolySolve;

/**
 * Static class dealink with shapes intersections.
 *
 * @author flo
 */
public final class InterShape {

    private static final double ACCURACY = 0.01;

    /**
     * Non instanciable class.
     */
    private InterShape() {
    }

    /**
     * Finds the intersection of two {@code LineSegments}.
     *
     * @param line1 The first {@code Line}.
     * @param line2 The second {@code Line}.
     *
     * @return An {@code ArrayList<DetailledIntersection2D>} representing the
     * intersections of the two curves.
     *
     * @throws MathsArgumentException If the starting point is the same as the
     * ending point.
     */
    public static <T extends Line, U extends Line> ArrayList<DetailledIntersection2D<T, U>> lineSegments(final T line1, final U line2) throws MathsArgumentException {
        final double l1StX = line1.getStartX();
        final double l1StY = line1.getStartY();
        final double l1EdX = line1.getEndX();
        final double l1EdY = line1.getEndY();
        final double l2StX = line2.getStartX();
        final double l2StY = line2.getStartY();
        final double l2EdX = line2.getEndX();
        final double l2EdY = line2.getEndY();

        final double a = l1EdX - l1StX;
        final double b = l1StX - l2StX;
        final double c = l2EdX - l2StX;
        final double d = l1EdY - l1StY;
        final double e = l1StY - l2StY;
        final double f = l2EdY - l2StY;

        if ((a == 0 && d == 0) || (c == 0 && f == 0)) {
            throw new MathsArgumentException("Undefined StrtLine : the starting point is the same as the ending one.");
        }

        final ArrayList<DetailledIntersection2D<T, U>> interList = new ArrayList<>(1);
        final double dotProd = a * f - c * d;
        if (dotProd == 0) {
            return interList;
        }

        final double t = (c * e - b * f) / dotProd;
        final double u = (a * t + b) / c;
        final Point2D inter = new Point2D(a * t + l1StX, d * t + l1StY);

        if (t >= 0 && t < 1 && u >= 0 && u <= 1) {
            final Point2D line1Der = new Point2D(a, d);
            final Point2D line2Der = new Point2D(c, f);
            final DetailledIntersection2D cross = new DetailledIntersection2D(line1, line2, inter, t, u, line1Der, line2Der);
            interList.add(cross);
        }

        return interList;
    }

    /**
     * Finds the intersection of a {@code LineSegments} and a {@code QuadCurve}.
     *
     * @param segment The {@code Line}.
     * @param quad The {@code QuadCurve}.
     *
     * @return An {@code ArrayList<DetailledIntersection2D>} representing the
     * intersections of the two curves.
     *
     * @throws MathsArgumentException Thrown by
     * {@code InterShape.quadLineSement(final QuadCurve quad, final Line segment)}.
     * @throws MathsPrecisionException Thrown by
     * {@code InterShape.quadLineSement(final QuadCurve quad, final Line segment)}.
     */
    public static <T extends Line, U extends QuadCurve> ArrayList<DetailledIntersection2D<T, U>> lineSegmentQuad(final T segment, final U quad) throws MathsArgumentException, MathsPrecisionException {
        final ArrayList<DetailledIntersection2D<T, U>> interList = new ArrayList<>(1);
        quadLineSement(quad, segment)
                .forEach(inter -> interList.add(new DetailledIntersection2D(
                        inter.getCurve2(), inter.getCurve1(), inter.getIntersection(),
                        inter.getCurve2Parameter(), inter.getCurve1Parameter(),
                        inter.getCurve2Derivate(), inter.getCurve1Derivate())));
        return interList;
    }

    /**
     * Finds the intersection of a {@code QuadCurve} and a {@code LineSegments}.
     *
     * @param quad The {@code QuadCurve}.
     * @param segment The {@code Line}.
     *
     * @return An {@code ArrayList<DetailledIntersection2D>} representing the
     * intersections of the two curves.
     *
     * @throws MathsArgumentException If the starting point is the same as the
     * ending point in one of the two curves.
     * @throws MathsPrecisionException Thrown by
     * {@code RealRootsPolySolve.quadratic(final double start, final double end, final double a, final double b, final double c, final double accuracy)}.
     */
    public static <T extends QuadCurve, U extends Line> ArrayList<DetailledIntersection2D<T, U>> quadLineSement(final T quad, final U segment) throws MathsArgumentException, MathsPrecisionException {
        final double xShift = quad.getControlX();
        final double yShift = quad.getControlY();

        final double qStX = quad.getStartX() - xShift;
        final double qStY = quad.getStartY() - yShift;
        final double qEdX = quad.getEndX() - xShift;
        final double qEdY = quad.getEndY() - yShift;
        final double lStX = segment.getStartX() - xShift;
        final double lStY = segment.getStartY() - yShift;
        final double lEdX = segment.getEndX() - xShift;
        final double lEdY = segment.getEndY() - yShift;

        if (qStX == qEdX && qStY == qEdY) {
            throw new MathsArgumentException("Undefined Quadratic : the starting point is the same as the ending one.");
        } else if (lStX == lEdX && lStY == lEdY) {
            throw new MathsArgumentException("Undefined StrtLine : the starting point is the same as the ending one.");
        }

        final double A = lEdX - lStX;
        final double B = lEdY - lStY;

        final double a = (qEdY + qStY) * A - B * (qEdX + qStX);
        final double b = 2 * (B * qStX - A * qStY);
        final double c = -lEdX * lStY + lEdY * lStX - B * qStX + A * qStY;

        final ArrayList<Double> tSolList;
        tSolList = RealRootsPolySolve.quadratic(0, 1, a, b, c, ACCURACY);

        final double C, D, E, F;
        if (Math.abs(A) > Math.abs(B)) {
            C = qStX;
            D = qEdX;
            E = lStX;
            F = lEdX - lStX;
        } else {
            C = qStY;
            D = qEdY;
            E = lStY;
            F = lEdY - lStY;
        }

        final ArrayList<DetailledIntersection2D<T, U>> interList = new ArrayList<>(1);
        tSolList.forEach(t -> {
            final double u = ((1 - t) * (1 - t) * C + t * t * D - E) / F;
            if (u >= 0 && u <= 1) {
                final Point2D inter = new Point2D(lStX * (1 - u) + lEdX * u + xShift, lStY * (1 - u) + lEdY * u + yShift);
                final Point2D quadDiff = new Point2D(2 * (qEdX * t - qStX * (1 - t)), 2 * (qEdY * t - qStY * (1 - t)));
                final Point2D lineDiff = new Point2D(lEdX - lStX, lEdY - lStY);
                interList.add(new DetailledIntersection2D(quad, segment, inter, t, u, quadDiff, lineDiff));
            }
        });

        return interList;
    }

    /**
     * Finds the intersection between two {@code QuadCurve}.
     *
     * @param quad1 The first {@code QuadCurve}.
     * @param quad2 The second {@code QuadCurve}.
     *
     * @return An {@code ArrayList<DetailledIntersection2D>} representing the
     * intersections of the two curves.
     *
     * @throws MathsArgumentException Thrown by
     * {@code RealRootsPolySolve.quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e, final double accuracy)}.
     * @throws MathsPrecisionException Thrown by
     * {@code RealRootsPolySolve.quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e, final double accuracy)}.
     * @throws MathsLoopOverflowException Thrown by
     * {@code RealRootsPolySolve.quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e, final double accuracy)}.
     */
    public static <T extends QuadCurve, U extends QuadCurve> ArrayList<DetailledIntersection2D<T, U>> quads(final T quad1, final U quad2) throws MathsArgumentException, MathsPrecisionException, MathsLoopOverflowException {
        final ArrayList<ArrayList<DetailledIntersection2D<T, U>>> intersectionsTable = new ArrayList<>(2);

        boolean exchanged = false;
        do {
            final QuadCurve firstQuad = exchanged ? quad2 : quad1;
            final QuadCurve secondQuad = exchanged ? quad1 : quad2;

            final double xShift = firstQuad.getControlX();
            final double yShift = firstQuad.getControlY();

            final double q1StX, q1EdX, q1StY, q1EdY, q2StX, q2ClX, q2EdX, q2StY, q2ClY, q2EdY;

            q1StX = firstQuad.getStartX() - xShift;
            q1EdX = firstQuad.getEndX() - xShift;
            q1StY = firstQuad.getStartY() - yShift;
            q1EdY = firstQuad.getEndY() - yShift;
            q2StX = secondQuad.getStartX() - xShift;
            q2ClX = secondQuad.getControlX() - xShift;
            q2EdX = secondQuad.getEndX() - xShift;
            q2StY = secondQuad.getStartY() - yShift;
            q2ClY = secondQuad.getControlY() - yShift;
            q2EdY = secondQuad.getEndY() - yShift;

            final double Ax = 2 * q2ClX - q2EdX - q2StX;
            final double Bx = -2 * q2ClX + 2 * q2StX;
            final double Cx = q1EdX + q1StX;
            final double Dx = -2 * q1StX;
            final double Ex = q1StX - q2StX;

            final double Ay = 2 * q2ClY - q2EdY - q2StY;
            final double By = -2 * q2ClY + 2 * q2StY;
            final double Cy = q1EdY + q1StY;
            final double Dy = -2 * q1StY;
            final double Ey = q1StY - q2StY;

            final double a = Ax * Ax * Cy * Cy - 2 * Ax * Ay * Cx * Cy + Ay * Ay * Cx * Cx;
            final double b = 2 * Ax * Ax * Cy * Dy - 2 * Ax * Ay * Cx * Dy - 2 * Ax * Ay * Cy * Dx + 2 * Ay * Ay * Cx * Dx;
            final double c = 2 * Ax * Ax * Cy * Ey + Ax * Ax * Dy * Dy - 2 * Ax * Ay * Cx * Ey - 2 * Ax * Ay * Cy * Ex - 2 * Ax * Ay * Dx * Dy - Ax * Bx * By * Cy + Ax * By * By * Cx + 2 * Ay * Ay * Cx * Ex + Ay * Ay * Dx * Dx + Ay * Bx * Bx * Cy - Ay * Bx * By * Cx;
            final double d = 2 * Ax * Ax * Dy * Ey - 2 * Ax * Ay * Dx * Ey - 2 * Ax * Ay * Dy * Ex - Ax * Bx * By * Dy + Ax * By * By * Dx + 2 * Ay * Ay * Dx * Ex + Ay * Bx * Bx * Dy - Ay * Bx * By * Dx;
            final double e = Ax * Ax * Ey * Ey - 2 * Ax * Ay * Ex * Ey - Ax * Bx * By * Ey + Ax * By * By * Ex + Ay * Ay * Ex * Ex + Ay * Bx * Bx * Ey - Ay * Bx * By * Ex;

            final ArrayList<DetailledIntersection2D<T, U>> interList = new ArrayList<>(1);
            final boolean change = exchanged;
            RealRootsPolySolve.quartic(0, 1, a, b, c, d, e, ACCURACY).forEach(t -> {
                final double x = (1 - t) * (1 - t) * q1StX + t * t * q1EdX;
                final double y = (1 - t) * (1 - t) * q1StY + t * t * q1EdY;
                final double sqRoot = Math.sqrt((-2 * q2ClX + q2EdX + q2StX) * x + q2ClX * q2ClX - q2EdX * q2StX);
                final double u1 = (q2ClX - q2StX + sqRoot) / (2 * q2ClX - q2EdX - q2StX);
                final double u2 = (q2ClX - q2StX - sqRoot) / (2 * q2ClX - q2EdX - q2StX);
                final double x1 = (1 - u1) * (1 - u1) * q2StX + 2 * u1 * (1 - u1) * q2ClX + u1 * u1 * q2EdX;
                final double y1 = (1 - u1) * (1 - u1) * q2StY + 2 * u1 * (1 - u1) * q2ClY + u1 * u1 * q2EdY;
                final double x2 = (1 - u2) * (1 - u2) * q2StX + 2 * u2 * (1 - u2) * q2ClX + u2 * u2 * q2EdX;
                final double y2 = (1 - u2) * (1 - u2) * q2StY + 2 * u2 * (1 - u2) * q2ClY + u2 * u2 * q2EdY;
                final double u1diff = Math.hypot(x - x1, y - y1);
                final double u2diff = Math.hypot(x - x2, y - y2);
                final double u = u1diff < u2diff ? u1 : u2;
                final double udiff = u1diff < u2diff ? u1diff : u2diff;
                if (0 <= u && u <= 1 && udiff < 5) {
                    final double c1xDiff = -(2 * (1 - t)) * q1StX + 2 * t * q1EdX;
                    final double c1yDiff = -(2 * (1 - t)) * q1StY + 2 * t * q1EdY;
                    final double c2xDiff = -(2 * (1 - u)) * q2StX + (2 * (1 - u)) * q2ClX - 2 * u * q2ClX + 2 * u * q2EdX;
                    final double c2yDiff = -(2 * (1 - u)) * q2StY + (2 * (1 - u)) * q2ClY - 2 * u * q2ClY + 2 * u * q2EdY;
                    if (change) {
                        interList.add(new DetailledIntersection2D(
                                secondQuad, firstQuad,
                                new Point2D(x + xShift, y + yShift),
                                u, t,
                                new Point2D(c2xDiff, c2yDiff),
                                new Point2D(c1xDiff, c1yDiff)
                        ));
                    } else {
                        interList.add(new DetailledIntersection2D(
                                firstQuad, secondQuad,
                                new Point2D(x + xShift, y + yShift),
                                t, u,
                                new Point2D(c1xDiff, c1yDiff),
                                new Point2D(c2xDiff, c2yDiff)
                        ));
                    }

                }
            });

            intersectionsTable.add(interList);

            exchanged = !exchanged;
        } while (exchanged);

        if (intersectionsTable.get(0).size() < intersectionsTable.get(1).size()) {
            return intersectionsTable.get(1);
        } else {
            return intersectionsTable.get(0);
        }
    }

    /**
     * Finds the intersection between a {@code Circle} and a {@code Line}.
     *
     * @param circle The {@code Circle}.
     * @param segment The {@code Line}.
     *
     * @return An {@code ArrayList<DetailledIntersection2D>} representing the
     * intersections of the two curves.
     *
     * @throws MathsArgumentException If the starting point of the line is the
     * same as the ending one or thrown by
     * {@code RealRootsPolySolve.quadratic(final double start, final double end, final double a, final double b, final double c, final double accuracy)}.
     * @throws MathsPrecisionException Thrown by
     * {@code RealRootsPolySolve.quadratic(final double start, final double end, final double a, final double b, final double c, final double accuracy)}.
     */
    public static <T extends Circle, U extends Line> ArrayList<SimpleIntersection2D<T, U>> circleLineSegment(final T circle, final U segment) throws MathsArgumentException, MathsPrecisionException {
        final double xShift = circle.getCenterX();
        final double yShift = circle.getCenterY();

        final double x1 = segment.getStartX() - xShift;
        final double y1 = segment.getStartY() - yShift;
        final double x2 = segment.getEndX() - xShift;
        final double y2 = segment.getEndY() - yShift;

        final double a = y2 - y1;
        final double b = x1 - x2;
        final double c = -b * y1 - a * x1;
        final double radius = circle.getRadius();

        final ArrayList<SimpleIntersection2D<T, U>> interList = new ArrayList<>(2);
        if (a == 0) {
            if (b == 0) {
                throw new MathsArgumentException("Undefined StrtLine : the starting point is the same as the ending one.");
            } else {
                final double y = -c / b;
                final double x = radius * radius - y * y;
                if (x < 0) {
                    return interList;
                } else {
                    final double xRoot = Math.sqrt(x);
                    interList.add(new SimpleIntersection2D(circle, segment, new Point2D(xRoot + xShift, y + yShift)));
                    interList.add(new SimpleIntersection2D(circle, segment, new Point2D(-xRoot + xShift, y + yShift)));
                    return interList;
                }
            }
        }

        final ArrayList<Double> ySol;
        ySol = RealRootsPolySolve.quadratic(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, a * a + b * b, 2 * b * c, c * c - a * radius * a * radius, ACCURACY);
        ySol.stream()
                .filter(y -> y >= Math.min(y1, y2) && y <= Math.max(y1, y2))
                .forEach(sol -> {
                    final double x = -(c + b * sol) / a;
                    if (x >= Math.min(x1, x2) && x <= Math.max(x1, x2)) {
                        interList.add(new SimpleIntersection2D(circle, segment, new Point2D(x + xShift, sol + yShift)));
                    }
                });

        return interList;
    }

    /**
     * Finds the intersection between a {@code Circle} and a {@code QuadCurve}.
     *
     * @param circle The {@code Circle}.
     * @param quad The {@code QuadCurve}.
     *
     * @return An {@code ArrayList<DetailledIntersection2D>} representing the
     * intersections of the two curves.
     *
     * @throws MathsArgumentException If the starting point of the quadcurve is
     * the same as the ending one or thrown by
     * {@code RealRootsPolySolve.quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e, final double accuracy)}.
     * @throws MathsPrecisionException Thrown by
     * {@code RealRootsPolySolve.quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e, final double accuracy)}.
     * @throws MathsLoopOverflowException Thrown by
     * {@code RealRootsPolySolve.quartic(final double start, final double end, final double a, final double b, final double c, final double d, final double e, final double accuracy)}.
     */
    public static <T extends Circle, U extends QuadCurve> ArrayList<SimpleIntersection2D<T, U>> circleQuad(final T circle, final U quad) throws MathsArgumentException, MathsPrecisionException, MathsLoopOverflowException {
        final double xShift = circle.getCenterX();
        final double yShift = circle.getCenterY();

        final double qStX = quad.getStartX() - xShift;
        final double qClX = quad.getControlX() - xShift;
        final double qEdX = quad.getEndX() - xShift;
        final double qStY = quad.getStartY() - yShift;
        final double qClY = quad.getControlY() - yShift;
        final double qEdY = quad.getEndY() - yShift;
        final double radius = circle.getRadius();

        if (qStX == qEdX && qStY == qEdY) {
            throw new MathsArgumentException("Undefined Quadratic : the starting point is the same as the ending point.");
        }

        final double A = qStX - 2 * qClX + qEdX;
        final double B = 2 * (qClX - qStX);
        final double C = qStY - 2 * qClY + qEdY;
        final double D = 2 * (qClY - qStY);

        final double a = A * A + C * C;
        final double b = 2 * (A * B + C * D);
        final double c = 2 * (A * qStX + C * qStY) + B * B + D * D;
        final double d = 2 * (B * qStX + D * qStY);
        final double e = qStX * qStX + qStY * qStY - radius * radius;

        final ArrayList<SimpleIntersection2D<T, U>> interList = new ArrayList<>(0);
        RealRootsPolySolve.quartic(0, 1, a, b, c, d, e, ACCURACY).forEach(t -> {
            final double x = (1 - t) * (1 - t) * qStX + 2 * qClX * (1 - t) * t + qEdX * t * t + xShift;
            final double y = (1 - t) * (1 - t) * qStY + 2 * qClY * (1 - t) * t + qEdY * t * t + yShift;
            interList.add(new SimpleIntersection2D(circle, quad, new Point2D(x, y)));
        });

        return interList;
    }
}
