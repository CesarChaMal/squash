package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import dg.squash.math.Vector2D;
import dg.squash.shape.RectangleShape;
import dg.squash.shape.Shape;

public class ShapeComponent extends AbstractComponent<Shape> {

    public ShapeComponent(Entity parent) {
        this(parent, new RectangleShape(new Vector2D(0, 0), 100, 100));
    }

    public ShapeComponent(Entity parent, Shape shape) {
        super(parent, shape);
    }

    public void setPosition(Vector2D position) {
        show().setPosition(position);
    }

    public void moveHorizontal(double moveBy) {
        setPosition(new Vector2D(show().getPosition().getX() + moveBy, show().getPosition().getY()));
    }

    public Vector2D getPosition() {
        return show().getPosition();
    }
}
