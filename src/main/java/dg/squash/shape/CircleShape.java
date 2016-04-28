package dg.squash.shape;

import dg.squash.math.Vector2D;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Circle object.
 */
public class CircleShape implements Shape {

    private SimpleDoubleProperty centerXProperty;
    private SimpleDoubleProperty centerYProperty;
    private SimpleDoubleProperty radiusProperty;

    /**
     * Constructor of circleShape.
     * @param center center of circle
     * @param radius radius of circle
     */
    public CircleShape(Vector2D center, double radius) {
        centerXProperty = new SimpleDoubleProperty(this, "centerX", center.getX());
        centerYProperty = new SimpleDoubleProperty(this, "centerY", center.getX());
        radiusProperty = new SimpleDoubleProperty(this, "radius", radius);
    }

    public SimpleDoubleProperty centerXProperty() {
        return centerXProperty;
    }

    public SimpleDoubleProperty radiusProperty() {
        return radiusProperty;
    }

    public SimpleDoubleProperty centerYProperty() {
        return centerYProperty;
    }

    @Override
    public void setPosition(Vector2D center) {
        centerXProperty.set(center.getX());
        centerYProperty.set(center.getY());
    }

    @Override
    public Vector2D getPosition() {
        return new Vector2D(centerXProperty.get(), centerYProperty.get());
    }

    public void setRadius(double radius) {
        radiusProperty.set(radius);
    }

    public double getRadius() {
        return radiusProperty.get();
    }

    @Override
    public String toString() {
        return "Center: + " + new Vector2D(centerXProperty.get(), centerYProperty.get()) + ", Radius:" + radiusProperty.get();
    }
}
