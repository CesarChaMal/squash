package dg.squash.shape;


import dg.squash.math.Vector2D;

public class LineShape implements Shape {

    private Vector2D start;
    private Vector2D end;

    public LineShape(Vector2D start, Vector2D end) {
        this.start = start;
        this.end = end;
    }

    public Vector2D getPosition() {
        return start;
    }

    public void setPosition(Vector2D start) {
        this.start = start;
    }

    public Vector2D getEnd() {
        return end;
    }

    public void setEnd(Vector2D end) {
        this.end = end;
    }

    public Vector2D getDirection() {
        return Vector2D.subtract(getEnd(), getPosition());
    }

    @Override
    public String toString() {
        return "Start: " + start + ", End: " + end;
    }
}
