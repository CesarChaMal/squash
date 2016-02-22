package dg.squash.shape;

import dg.squash.math.Vector2D;
import javafx.beans.property.SimpleDoubleProperty;

public class RectangleShape implements Shape {

    public SimpleDoubleProperty positionYProperty() {
        return positionYProperty;
    }

    public SimpleDoubleProperty positionXProperty() {
        return positionXProperty;
    }

    public SimpleDoubleProperty widthProperty() {
        return widthProperty;
    }

    public SimpleDoubleProperty rotationProperty() {
        return rotationProperty;
    }

    public SimpleDoubleProperty heightProperty() {
        return heightProperty;
    }

    private SimpleDoubleProperty positionYProperty;
    private SimpleDoubleProperty widthProperty;
    private SimpleDoubleProperty heightProperty;
    private SimpleDoubleProperty rotationProperty;
    private SimpleDoubleProperty positionXProperty;

    public RectangleShape(Vector2D origin, double width, double height) {
        this(origin, width, height, 0);
    }

    public RectangleShape(Vector2D origin, double width, double height, double rotation) {
        positionXProperty = new SimpleDoubleProperty(this, "xPosition", origin.getX());
        positionYProperty = new SimpleDoubleProperty(this, "yPosition", origin.getY());
        widthProperty = new SimpleDoubleProperty(this, "width", width);
        heightProperty = new SimpleDoubleProperty(this, "height", height);
        rotationProperty = new SimpleDoubleProperty(this, "rotation", rotation);
    }

    public double getRotation() {
        return rotationProperty.get();
    }

    public void setRotation(double rotation) {
        rotationProperty.set(rotation);
    }

    public Vector2D getSize() {
        Vector2D farCorner = new Vector2D(positionXProperty.get() + widthProperty.get(),
                positionYProperty.get() + heightProperty.get());
        return Vector2D.subtract(farCorner, new Vector2D(positionXProperty.get(), positionYProperty.get()));
    }

    public double getHeight() {
        return heightProperty.get();
    }

    public double getWidth() {
        return widthProperty.get();
    }

    public void setHeight(double height) {
        heightProperty.set(height);
    }

    public void setWidth(double width) {
        widthProperty.set(width);
    }

    @Override
    public Vector2D getPosition() {
       return new Vector2D(positionXProperty.get(), positionYProperty.get());
    }

    @Override
    public void setPosition(Vector2D origin) {
        positionXProperty.set(origin.getX());
        positionYProperty.set(origin.getY());
    }

    public LineShape getLeftEdge() {
        Vector2D bLeft = new Vector2D(positionXProperty.get(), positionYProperty.get() + heightProperty.get());
        return new LineShape(bLeft, new Vector2D(positionXProperty.get(), positionYProperty.get()));
    }

    public LineShape getBottomEdge() {
        Vector2D bLeft = new Vector2D(positionXProperty.get(), positionYProperty.get() + heightProperty.get());
        Vector2D bRight = new Vector2D(positionXProperty.get() + widthProperty.get(), positionYProperty.get() + heightProperty.get());
        return new LineShape(bRight, bLeft);
    }

    public LineShape getRightEdge() {
        Vector2D tRight = new Vector2D(positionXProperty.get() + widthProperty.get(), positionYProperty.get());
        Vector2D bRight = new Vector2D(positionXProperty.get() + widthProperty.get(), positionYProperty.get() + heightProperty.get());
        return new LineShape(tRight, bRight);
    }

    public LineShape getTopEdge() {
        Vector2D tRight = new Vector2D(positionXProperty.get() + widthProperty.get(), positionYProperty.get());
        return new LineShape(new Vector2D(positionXProperty.get(), positionYProperty.get()), tRight);
    }

    public Vector2D getCenter() {
        return new Vector2D(positionXProperty.get() + widthProperty.get() / 2,
                positionYProperty.get() + heightProperty.get() / 2);
    }

    public Vector2D getHalfExtend() {

        return Vector2D.div(getSize(), 2);
    }

    @Override
    public String toString() {
        return "Origin: " + new Vector2D(positionXProperty.get(), positionYProperty.get()) +
                ", Width: " + widthProperty.get() + ", Height: " + heightProperty.get();
    }
}
