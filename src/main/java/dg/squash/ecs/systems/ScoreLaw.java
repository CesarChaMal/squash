package dg.squash.ecs.systems;

import dg.squash.ecs.components.HealthComponent;
import dg.squash.ecs.components.ScoreComponent;
import dg.squash.events.HealthListener;

public class ScoreLaw implements HealthListener {

    @Override
    public void update(HealthComponent component) {
        if (!component.belongsTo().hasComponent(ScoreComponent.class))
            return;
        int health = component.show();
        if (health > 0) {
            component.belongsTo().getComponent(ScoreComponent.class).setScore(10);
        } else {
            component.belongsTo().getComponent(ScoreComponent.class).setScore(100);
        }

    }
}
