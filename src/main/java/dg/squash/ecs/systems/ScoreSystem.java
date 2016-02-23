package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.HealthComponent;
import dg.squash.ecs.components.ScoreComponent;

public class ScoreSystem extends AbstractECSystem  {

    public ScoreSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(Entity entity, HealthComponent healthComponent) {
        int health = healthComponent.show();
        if (health > 0) {
            entity.getComponent(ScoreComponent.class).modify(10);
        } else {
            entity.getComponent(ScoreComponent.class).modify(100);
        }
        belongsTo().update(entity, ScoreComponent.class);
    }
}
