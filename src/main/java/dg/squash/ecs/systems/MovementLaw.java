package dg.squash.ecs.systems;

import dg.squash.ecs.components.ShapeComponent;
import dg.squash.ecs.components.VelocityComponent;
import dg.squash.math.Vector2D;

public class MovementLaw {

    public void update(VelocityComponent component) {
        Vector2D velocity = component.show();
        if (velocity.getX() == 0 && velocity.getY() == 0)
            return;
        Vector2D position = component.belongsTo().getComponent(ShapeComponent.class).getPosition();
        Vector2D updatedPosition = Vector2D.add(position, velocity);
        component.belongsTo().getComponent(ShapeComponent.class).setPosition(updatedPosition);
    }
}
