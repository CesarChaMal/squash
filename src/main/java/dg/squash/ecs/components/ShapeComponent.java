package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import dg.squash.math.Vector2D;
import dg.squash.shape.RectangleShape;
import dg.squash.shape.Shape;

public class ShapeComponent extends AbstractComponent {

    private Shape shape;

    public ShapeComponent(Entity parent) {
        this(parent, new RectangleShape(new Vector2D(0, 0), 100, 100));
    }

    public ShapeComponent(Entity parent, Shape shape) {
        super(parent);
        this.shape = shape;
    }

    public void setPosition(Vector2D position) {
        shape.setPosition(position);
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
