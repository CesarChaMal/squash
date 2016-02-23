package dg.squash.ecs;

import dg.squash.ecs.components.*;
import dg.squash.ecs.systems.*;
import dg.squash.utils.TwoTuple;

public class SystemEngine {

    private final MovementSystem movementSystem = new MovementSystem(this);
    private final CollisionSystem collisionSystem = new CollisionSystem(this);
    private final HealthSystem healthSystem = new HealthSystem(this);
    private final ScoreSystem scoreSystem = new ScoreSystem(this);
    private final TotalScoreSystem totalScoreSystem = new TotalScoreSystem(this);
    private final RenderSystem renderSystem = new RenderSystem(this);
    private final DestroyEnemySystem destroyEnemySystem = new DestroyEnemySystem(this);
    private final InputSystem inputSystem = new InputSystem(this);
    private EntityEngine entityEngine;

    public SystemEngine(EntityEngine entityEngine) {
        this.entityEngine = entityEngine;
    }

    public void resetEntities(EntityEngine entityEngine){
        this.entityEngine = entityEngine;
    }
    public <T extends Component> void update(Entity entity, Class<T> component) {
        if (component.equals(HealthComponent.class)) {
            scoreSystem.update(entity, entity.getComponent(HealthComponent.class));
            destroyEnemySystem.update(entityEngine.getEntity("GAME"), entity.getComponent(HealthComponent.class));
            renderSystem.update(entityEngine.getEntity("BIN"), entity);
        }
        if (component.equals(ScoreComponent.class)) {
            totalScoreSystem.update(entityEngine.getEntity("GAME"), entity.getComponent(ScoreComponent.class));
            renderSystem.playScoreAnimation(entity);
        }
    }

    public void update(Entity e1, Entity e2) {
        healthSystem.update(e1, e2);
    }

    public void start() {
        for (Entity entity : entityEngine.getEntities()) {
            if (entity.hasComponent(InputComponent.class))
                inputSystem.update(entity.getComponent(InputComponent.class));
        }
        for (Entity entity : entityEngine.getEntities()) {
            if (entity.hasComponent(VelocityComponent.class)) {
                movementSystem.update(entity);
            }
        }

        for (TwoTuple<Entity, Entity> t : entityEngine.getCollisions()) {
            collisionSystem.update(t.getT1(), t.getT2());
        }
    }
}
