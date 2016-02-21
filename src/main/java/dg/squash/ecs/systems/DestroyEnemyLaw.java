package dg.squash.ecs.systems;

import dg.squash.ecs.components.HealthComponent;
import dg.squash.events.HealthListener;
import dg.squash.main.Game;

public class DestroyEnemyLaw implements HealthListener {

    private Game game;

    public DestroyEnemyLaw(Game game) {
        this.game = game;
    }

    @Override
    public void update(HealthComponent component) {
        if (component.show() <= 0)
            game.destroyEnemy();
    }
}
