package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.GameComponent;
import dg.squash.ecs.components.ScoreComponent;

public class TotalScoreSystem extends AbstractECSystem {

    public TotalScoreSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(Entity total, ScoreComponent scoreComponent) {
        int totalScore = total.getComponent(GameComponent.class).show().getTotalScore();
        total.getComponent(GameComponent.class).show().setTotalScore(totalScore + scoreComponent.show());
        belongsTo().update(total, GameComponent.class);
    }
}
