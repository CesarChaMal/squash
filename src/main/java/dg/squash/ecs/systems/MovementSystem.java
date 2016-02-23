package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.ShapeComponent;
import dg.squash.ecs.components.VelocityComponent;
import dg.squash.math.Vector2D;

public class MovementSystem extends AbstractECSystem {

    public MovementSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(Entity entity) {
        Vector2D velocity = entity.getComponent(VelocityComponent.class).show();
        if (velocity.getX() == 0 && velocity.getY() == 0)
            return;
        Vector2D position = entity.getComponent(ShapeComponent.class).getPosition();
        Vector2D updatedPosition = Vector2D.add(position, velocity);
        entity.getComponent(ShapeComponent.class).setPosition(updatedPosition);
        super.belongsTo().update(entity, ShapeComponent.class);
    }
}
