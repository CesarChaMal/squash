package dg.squash.ecs.components;

import dg.squash.math.Vector2D;
import dg.squash.ecs.Entity;
import javafx.beans.property.SimpleDoubleProperty;
import dg.squash.shape.RectangleShape;
import dg.squash.shape.Shape;

public class ShapeComponent extends AbstractComponent {

    private Shape shape;
    private SimpleDoubleProperty positionXProperty;
    private SimpleDoubleProperty positionYProperty;

    public ShapeComponent(Entity parent) {
        this(parent, new RectangleShape(new Vector2D(0, 0), 100, 100));
    }

    public ShapeComponent(Entity parent, Shape shape) {
        super(parent);
        this.shape = shape;
        this.positionXProperty = new SimpleDoubleProperty(this, "xPosition", shape.getPosition().getX());
        this.positionYProperty = new SimpleDoubleProperty(this, "yPosition", shape.getPosition().getY());
    }

    public SimpleDoubleProperty getPositionXProperty() {
        return positionXProperty;
    }

    public SimpleDoubleProperty getPositionYProperty() {
        return positionYProperty;
    }

    public void setPosition(Vector2D position) {
        shape.setPosition(position);
        positionXProperty.set(position.getX());
        positionYProperty.set(position.getY());
    }
    public void moveHorizontal(double moveBy){
        setPosition(new Vector2D(show().getPosition().getX() + moveBy, shape.getPosition().getY()));

    }
    public Vector2D getPosition() {
        return shape.getPosition();
    }

    public Shape show() {
        return shape;
    }
}
