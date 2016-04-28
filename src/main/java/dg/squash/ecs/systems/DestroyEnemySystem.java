package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.GameComponent;
import dg.squash.ecs.components.HealthComponent;

public class DestroyEnemySystem extends AbstractECSystem {

    public DestroyEnemySystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(Entity game, HealthComponent healthComponent) {
        int health = healthComponent.show();
        if (health <= 0){
            game.getComponent(GameComponent.class).show().destroyEnemy();
        }
        belongsTo().update(game, GameComponent.class);
    }
}
