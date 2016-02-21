package dg.squash.shape;

import dg.squash.math.Vector2D;

public class RectangleShape implements Shape {

    private Vector2D origin;
    private double width;
    private double height;
    private double rotation;


    public RectangleShape(Vector2D origin, double width, double height) {
        this(origin, width, height, 0);
    }

    public RectangleShape(Vector2D origin, double width, double height, double rotation) {
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Vector2D getSize() {
        Vector2D farCorner = new Vector2D(origin.getX() + width, origin.getY() + height);
        return Vector2D.subtract(farCorner, origin);
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public Vector2D getPosition() {
        return origin;
    }

    @Override
    public void setPosition(Vector2D origin) {
        this.origin = origin;
    }

    public LineShape getLeftEdge() {
        Vector2D bLeft = new Vector2D(origin.getX(), origin.getY() + height);
        return new LineShape(bLeft, origin);
    }

    public LineShape getBottomEdge() {
        Vector2D bLeft = new Vector2D(origin.getX(), origin.getY() + height);
        Vector2D bRight = new Vector2D(origin.getX() + width, origin.getY() + height);
        return new LineShape(bRight, bLeft);
    }

    public LineShape getRightEdge() {
        Vector2D tRight = new Vector2D(origin.getX() + width, origin.getY());
        Vector2D bRight = new Vector2D(origin.getX() + width, origin.getY() + height);
        return new LineShape(tRight, bRight);
    }

    public LineShape getTopEdge() {
        Vector2D tRight = new Vector2D(origin.getX() + width, origin.getY());
        return new LineShape(origin, tRight);
    }

    public Vector2D getCenter() {
        return new Vector2D(origin.getX() + width / 2, origin.getY() + height / 2);
    }

    public Vector2D getHalfExtend() {

        return Vector2D.div(getSize(), 2);
    }

    @Override
    public String toString() {
        return "Origin: " + origin + ", Width: " + width + ", Height: " + height;
    }
}
