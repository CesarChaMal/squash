package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import dg.squash.math.Vector2D;

public class VelocityComponent extends AbstractComponent {

    private Vector2D velocity;

    public VelocityComponent(Entity parent) {
        super(parent);
        velocity = new Vector2D(0, 0);
    }

    public VelocityComponent(Entity parent, Vector2D velocity) {
        super(parent);
        this.velocity = velocity;
    }

    public void modify(Vector2D velocity) {
        this.velocity = velocity;
    }

    public Vector2D show() {
        return velocity;
    }

}
