package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import dg.squash.math.Vector2D;

public class VelocityComponent extends AbstractComponent<Vector2D> {

    public VelocityComponent(Entity parent) {
        super(parent, new Vector2D(0, 0));
    }

    public VelocityComponent(Entity parent, Vector2D velocity) {
        super(parent, velocity);
    }
}
