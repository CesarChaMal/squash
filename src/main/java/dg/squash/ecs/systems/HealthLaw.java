package dg.squash.ecs.systems;

import dg.squash.ecs.components.DamageComponent;
import dg.squash.ecs.components.HealthComponent;
import dg.squash.ecs.Entity;
import dg.squash.events.CollisionListener;

public class HealthLaw implements CollisionListener {

    private void updateHealth(Entity h, Entity d) {
        int damage = d.getComponent(DamageComponent.class).show();
        h.getComponent(HealthComponent.class).decrease(damage);
    }

    @Override
    public void update(Entity entity1, Entity entity2) {
        if (entity1.hasComponent(HealthComponent.class) && entity2.hasComponent(DamageComponent.class))
            updateHealth(entity1, entity2);
        else if (entity2.hasComponent(HealthComponent.class) && entity1.hasComponent(DamageComponent.class))
            updateHealth(entity2, entity1);
    }
}
