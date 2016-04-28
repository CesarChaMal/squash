package dg.squash.math;


import dg.squash.shape.CircleShape;
import dg.squash.shape.LineShape;
import dg.squash.shape.RectangleShape;

public class Intersector {

    private class Range {
        private double min;
        private double max;

        public Range(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }

    public boolean rectangleXLine(RectangleShape r, LineShape l) {
        Vector2D n = Vector2D.rotate90(l.getDirection());
        double dp1, dp2, dp3, dp4;
        Vector2D c1 = r.getPosition();
        Vector2D c2 = Vector2D.add(c1, r.getSize());
        Vector2D c3 = new Vector2D(c2.getX(), c1.getY());
        Vector2D c4 = new Vector2D(c1.getX(), c2.getY());

        c1 = Vector2D.subtract(c1, l.getPosition());
        c2 = Vector2D.subtract(c2, l.getPosition());
        c3 = Vector2D.subtract(c3, l.getPosition());
        c4 = Vector2D.subtract(c4, l.getPosition());

        dp1 = Vector2D.dotProduct(n, c1);
        dp2 = Vector2D.dotProduct(n, c2);
        dp3 = Vector2D.dotProduct(n, c3);
        dp4 = Vector2D.dotProduct(n, c4);

        return (dp1 * dp2 <= 0) || (dp2 * dp3 <= 0) || (dp3 * dp4 <= 0);

    }

    public boolean rectangleXLineSegment(RectangleShape r, LineShape s) {
        if (!rectangleXLine(r, s))
            return false;

        Range rRange = new Range(r.getPosition().getX(), r.getPosition().getX() + r.getSize().getX());
        Range sRange = new Range(s.getPosition().getX(), s.getEnd().getX());
        sRange = sortRange(sRange);
        if (!overlappingRanges(rRange, sRange))
            return false;

        rRange = new Range(r.getPosition().getY(), r.getPosition().getY() + r.getSize().getY());
        sRange = new Range(s.getPosition().getY(), s.getEnd().getY());
        sRange = sortRange(sRange);

        return overlappingRanges(rRange, sRange);
    }

    public boolean circleXOrientedRectangle(CircleShape c, RectangleShape r) {
        RectangleShape lr = new RectangleShape(new Vector2D(0, 0), r.getWidth(), r.getHeight());
        CircleShape lc = new CircleShape(new Vector2D(0, 0), c.getRadius());

        Vector2D distance = Vector2D.subtract(c.getPosition(), r.getCenter());
        distance = Vector2D.rotate(distance, r.getRotation() * -1);
        lc.setPosition(Vector2D.add(distance, r.getHalfExtend()));
        return circleXRectangle(lc, lr);
    }

    private boolean equivalent_Lines(LineShape a, LineShape b) {
        if (!Vector2D.isParallel(a.getDirection(), b.getDirection()))
            return false;
        Vector2D d = Vector2D.subtract(a.getPosition(), b.getPosition());
        return Vector2D.isParallel(d, a.getDirection());
    }

    public boolean lineXLine(LineShape a, LineShape b) {
        if (Vector2D.isParallel(a.getDirection(), b.getDirection()))
            return equivalent_Lines(a, b);
        return true;
    }

    public boolean circleXRectangle(CircleShape c, RectangleShape r) {
        Vector2D clamped = clampOnRectangle(c.getPosition(), r);
        return pointXCircle(c, clamped);
    }

    public boolean pointXRectangle(RectangleShape r, Vector2D p) {
        double left = r.getPosition().getX();
        double right = left + r.getSize().getX();
        double bottom = r.getPosition().getY();
        double top = bottom + r.getSize().getY();
        return left <= p.getX() && bottom <= p.getY() && p.getX() <= right && p.getY() <= top;
    }

    private double clampOnRange(double x, double min, double max) {
        if (x < min)
            return min;
        else if (max < x)
            return max;
        return x;
    }

    private Vector2D clampOnRectangle(Vector2D p, RectangleShape r) {
        Vector2D o = r.getPosition();
        Vector2D s = r.getSize();

        double clampX = clampOnRange(p.getX(), o.getX(), o.getX() + s.getX());
        double clampY = clampOnRange(p.getY(), o.getY(), o.getY() + s.getY());
        return new Vector2D(clampX, clampY);
    }

    public boolean rectangleXRectangle(RectangleShape a, RectangleShape b) {

        double aLeft = a.getPosition().getX();
        double aRight = aLeft + a.getSize().getX();
        double bLeft = b.getPosition().getX();
        double bRight = bLeft + b.getSize().getX();

        double aBottom = a.getPosition().getY();
        double aTop = aBottom + a.getSize().getY();
        double bBottom = b.getPosition().getY();
        double bTop = bBottom + b.getSize().getY();

        return overlapping(aLeft, aRight, bLeft, bRight) && overlapping(aBottom, aTop, bBottom, bTop);
    }

    public boolean pointXCircle(CircleShape c, Vector2D p) {
        Vector2D distance = Vector2D.subtract(c.getPosition(), p);
        return distance.mag() <= c.getRadius();
    }

    public boolean circleXLineSegment(CircleShape c, LineShape s) {
        if (pointXCircle(c, s.getPosition()) || pointXCircle(c, s.getEnd()))
            return true;
        Vector2D d = s.getDirection();
        Vector2D lc = Vector2D.subtract(c.getPosition(), s.getPosition());
        Vector2D p = Vector2D.projection(lc, d);
        Vector2D nearest = Vector2D.add(s.getPosition(), p);
        return pointXCircle(c, nearest) && p.mag() <= d.mag() && 0 <= Vector2D.dotProduct(p, d);
    }

    public double getNearsetPointToLine(LineShape s, Vector2D c){
        Vector2D d = s.getDirection();
        Vector2D lc = Vector2D.subtract(c, s.getPosition());
        Vector2D p = Vector2D.projection(lc, d);
        Vector2D nearest = Vector2D.add(s.getPosition(), p);
        return nearest.mag();
    }
    private boolean overlapping(double minA, double maxA, double minB, double maxB) {
        return minB <= maxA && minA <= maxB;
    }

    public boolean circleXCircle(CircleShape a, CircleShape b) {
        double radiusSum = a.getRadius() + b.getRadius();
        Vector2D distance = Vector2D.subtract(a.getPosition(), b.getPosition());
        return distance.mag() <= radiusSum;
    }

    public boolean circleXLine(CircleShape c, LineShape l) {
        Vector2D lc = Vector2D.subtract(c.getPosition(), l.getPosition());
        Vector2D p = Vector2D.projection(lc, l.getDirection());
        Vector2D nearest = Vector2D.add(l.getPosition(), p);
        return pointXCircle(c, nearest);
    }

    private boolean onOneSide(LineShape axis, LineShape s) {
        Vector2D d1 = Vector2D.subtract(s.getPosition(), axis.getPosition());
        Vector2D d2 = Vector2D.subtract(s.getEnd(), axis.getPosition());
        Vector2D n = Vector2D.rotate90(axis.getDirection());
        return Vector2D.dotProduct(n, d1) * Vector2D.dotProduct(n, d2) > 0;
    }

    private Range sortRange(Range r) {
        Range sorted = r;
        if (r.min > r.max) {
            sorted.min = r.max;
            sorted.max = r.min;
        }
        return sorted;
    }

    private Range projectSegment(LineShape s, Vector2D onto) {
        Vector2D ontoUnit = Vector2D.norm(onto);
        double min = Vector2D.dotProduct(ontoUnit, s.getPosition());
        double max = Vector2D.dotProduct(ontoUnit, s.getEnd());

        Range r = new Range(min, max);
        r = sortRange(r);
        return r;
    }

    private boolean overlappingRanges(Range a, Range b) {
        return overlapping(a.min, a.max, b.min, b.max);
    }

    public boolean lineSegmentXLineSegment(LineShape a, LineShape b) {
        LineShape axisA = new LineShape(a.getPosition(), a.getEnd());

        if (onOneSide(axisA, b))
            return false;
        LineShape axisB = new LineShape(b.getPosition(), b.getEnd());

        if (onOneSide(axisB, a))
            return false;

        if (Vector2D.isParallel(axisA.getDirection(), axisB.getDirection())) {
            Range rangeA = projectSegment(a, axisA.getDirection());
            Range rangeB = projectSegment(b, axisA.getDirection());

            return overlappingRanges(rangeA, rangeB);
        }
        return true;
    }

    public boolean lineSegmentXOrientedRectangle(LineShape s, RectangleShape r) {

        RectangleShape lr = new RectangleShape(Vector2D.ZERO, r.getWidth(), r.getHeight());

        Vector2D lsStart = Vector2D.subtract(s.getPosition(), r.getCenter());
        lsStart = Vector2D.rotate(lsStart, -r.getRotation());
        lsStart = Vector2D.add(lsStart, r.getHalfExtend());

        Vector2D lsEnd = Vector2D.subtract(s.getEnd(), r.getCenter());
        lsEnd = Vector2D.rotate(lsEnd, -r.getRotation());
        lsEnd = Vector2D.add(lsEnd, r.getHalfExtend());

        LineShape ls = new LineShape(lsStart, lsEnd);
        return rectangleXLineSegment(lr, ls);
    }

    public boolean lineXOrientedRectangle(LineShape l, RectangleShape r) {
        RectangleShape lr = new RectangleShape(Vector2D.ZERO, r.getWidth(), r.getHeight());

        Vector2D base = Vector2D.subtract(l.getPosition(), r.getCenter());
        base = Vector2D.rotate(base, -r.getRotation());
        base = Vector2D.add(base, r.getHalfExtend());
        Vector2D dir = Vector2D.rotate(l.getDirection(), -r.getRotation());
        LineShape ll = new LineShape(base, dir);
        return rectangleXLine(lr, ll);
    }

    public boolean lineXLineSegment(LineShape l, LineShape s) {
        return !onOneSide(l, s);
    }

    public boolean pointXOrienteRectangle(RectangleShape r, Vector2D p) {
        RectangleShape lr = new RectangleShape(Vector2D.ZERO, r.getWidth(), r.getHeight());
        Vector2D lp = Vector2D.subtract(p, r.getCenter());
        lp = Vector2D.rotate(lp, -r.getRotation());
        lp = Vector2D.add(lp, r.getHalfExtend());
        return pointXRectangle(lr, lp);
    }

    public boolean pointXLineSegment(Vector2D p, LineShape s) {
        Vector2D d = s.getDirection();
        Vector2D lp = Vector2D.subtract(p, s.getPosition());
        Vector2D pr = Vector2D.projection(lp, d);
        return Vector2D.isEqual(lp, pr) && pr.mag() <= d.mag() && 0 <= Vector2D.dotProduct(pr, d);
    }

    public boolean pointXPoint(Vector2D a, Vector2D b) {
        return Vector2D.equalDouble(a.getX(), b.getX()) && Vector2D.equalDouble(a.getY(), b.getY());
    }

    public boolean pointXLine(LineShape l, Vector2D p) {
        if (pointXPoint(l.getPosition(), p))
            return true;
        Vector2D lp = Vector2D.subtract(p, l.getPosition());
        return Vector2D.isParallel(lp, l.getDirection());
    }

    private Range rangeHull(Range a, Range b) {
        double min = a.min < b.min ? a.min : b.min;
        double max = a.max > b.max ? a.max : b.max;
        return new Range(min, max);
    }

    private LineShape orientedRectangleEdge(RectangleShape r, int nr) {
        Vector2D a = r.getHalfExtend();
        Vector2D b = r.getHalfExtend();

        switch (nr % 4) {
            case 0:
                a.invertX();
                break;
            case 1:
                b.invertY();
                break;
            case 2:
                a.invertY();
                b.invert();
                break;
            default:
                a.invert();
                b.invertX();
                break;
        }
        a = Vector2D.rotate(a, r.getRotation());
        a = Vector2D.add(a, r.getCenter());

        b = Vector2D.rotate(b, r.getRotation());
        b = Vector2D.add(b, r.getCenter());

        return new LineShape(a, b);
    }

    private boolean SATforOrientedRectangle(LineShape axis, RectangleShape r) {
        Range axisRange, r0Range, r2Range, rProjection;

        LineShape rEdge0 = orientedRectangleEdge(r, 0);
        LineShape rEdge2 = orientedRectangleEdge(r, 2);
        Vector2D n = axis.getDirection();

        axisRange = projectSegment(axis, n);
        r0Range = projectSegment(rEdge0, n);
        r2Range = projectSegment(rEdge2, n);
        rProjection = rangeHull(r0Range, r2Range);
        return !overlappingRanges(axisRange, rProjection);
    }

    public boolean orientedRectangleXOrientedRectangle(RectangleShape a, RectangleShape b) {
        LineShape edge = orientedRectangleEdge(a, 0);
        if (SATforOrientedRectangle(edge, b))
            return false;

        edge = orientedRectangleEdge(a, 1);
        if (SATforOrientedRectangle(edge, b))
            return false;

        edge = orientedRectangleEdge(b, 0);
        if (SATforOrientedRectangle(edge, a))
            return false;

        edge = orientedRectangleEdge(b, 1);
        return !SATforOrientedRectangle(edge, a);
    }
}
