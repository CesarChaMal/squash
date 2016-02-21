package dg.squash.main;

import dg.squash.ecs.systems.*;
import dg.squash.ecs.Entity;
import dg.squash.ecs.components.HealthComponent;
import dg.squash.ecs.components.InputComponent;
import dg.squash.ecs.components.ScoreComponent;
import dg.squash.ecs.components.VelocityComponent;
import dg.squash.utils.TwoTuple;

public class GameWorldLaws {
    private final MovementLaw movementLaw;
    private final CollisionLaw collisionLaw;
    private final InputLaw inputLaw;

    public GameWorldLaws(final Game game) {
        movementLaw = new MovementLaw();
        collisionLaw = new CollisionLaw();
        inputLaw = new InputLaw();
        RenderLaw renderLaw = new RenderLaw(game);
        HealthLaw healthLaw = new HealthLaw();
        ScoreLaw scoreLaw = new ScoreLaw();
        SoundLaw soundLaw = new SoundLaw();
        TotalScoreLaw totalScoreLaw = new TotalScoreLaw(game);
        DestroyEnemyLaw destroyEnemyLaw = new DestroyEnemyLaw(game);

        ScoreComponent.SCORE_CHANGE_LISTENERS.add(totalScoreLaw);
        ScoreComponent.SCORE_CHANGE_LISTENERS.add(renderLaw);
        HealthComponent.HEALTH_CHANGE_LISTENERS.add(scoreLaw);
        HealthComponent.HEALTH_CHANGE_LISTENERS.add(renderLaw);
        HealthComponent.HEALTH_CHANGE_LISTENERS.add(soundLaw);
        HealthComponent.HEALTH_CHANGE_LISTENERS.add(destroyEnemyLaw);
        CollisionLaw.COLLISION_LISTENERS.add(healthLaw);

    }

    public void applyRules(final GameWorld gameWorld) {
        for(Entity entity: gameWorld.getEntities()){
            if(entity.hasComponent(InputComponent.class))
                inputLaw.update(entity.getComponent(InputComponent.class));

        }
        for (Entity entity : gameWorld.getEntities()) {
            if (entity.hasComponent(VelocityComponent.class)) {
                movementLaw.update(entity.getComponent(VelocityComponent.class));
            }
        }

        for (TwoTuple<Entity, Entity> t : gameWorld.getCollisions()) {
            collisionLaw.apply(t.getT1(), t.getT2());
        }
    }
}
