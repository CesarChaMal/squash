package dg.squash.shape;

import dg.squash.math.Vector2D;

public class CircleShape implements Shape {

    private Vector2D center;
    private double radius;

    public CircleShape(Vector2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void setPosition(Vector2D center) {
        this.center = center;
    }

    @Override
    public Vector2D getPosition() {
        return center;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Center: + " + center + ", Radius:" + radius;
    }
}
