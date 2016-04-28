package dg.squash.ecs;

import dg.squash.ecs.components.*;
import dg.squash.ecs.systems.*;
import dg.squash.utils.Pair;

/**
 * Manages all the systems and their interactions with each other.
 */
public class SystemEngine {

    // a system which controls movement of the entities
    private final MovementSystem movementSystem = new MovementSystem(this);

    // a system which controls collisions between entities
    private final CollisionSystem collisionSystem = new CollisionSystem(this);

    // a system which controls health (resistance) of the entities
    private final HealthSystem healthSystem = new HealthSystem(this);

    // a system which manages individual entity's score for the game
    private final ScoreSystem scoreSystem = new ScoreSystem(this);

    // a system which manages total score for the game
    private final TotalScoreSystem totalScoreSystem = new TotalScoreSystem(this);

    // a system responsible for rendering entities
    private final RenderSystem renderSystem = new RenderSystem(this);

    // a system responsible for destroying entities
    private final DestroyEnemySystem destroyEnemySystem = new DestroyEnemySystem(this);

    // a system which manages input from the user
    private final InputSystem inputSystem = new InputSystem(this);

    // a system responsible for restarting and repositioning some entities
    private final RepositionSystem repositionSystem = new RepositionSystem(this);

    // all entities and collisions for system engine to manipulate on
    private EntityEngine entityEngine;

    /**
     * Creates default system engine with supplied entity engine.
     * @param entityEngine all the entities on which systems works on
     */
    public SystemEngine(EntityEngine entityEngine) {
        this.entityEngine = entityEngine;
    }

    /**
     * Updates an entity. This method should be called only from system classes which updates (change component state)
     * only of one entity.
     * @param entity to be update
     * @param <T> type of the component to be updated
     * @param component of the entity to be updated
     */
    public <T extends Component> void update(Entity entity, Class<T> component) {

         /*
          * checks if component which was updated has health component if it does
          * then updates score, checks if entity may have to be destroyed and plays some
          * through render system.
          */
        if (component.equals(HealthComponent.class)) {
            scoreSystem.update(entity, entity.getComponent(HealthComponent.class));
            destroyEnemySystem.update(entityEngine.getEntity("GAME"), entity.getComponent(HealthComponent.class));
            renderSystem.update(entityEngine.getEntity("BIN"), entity);
        }

        /*
         * Checks if entity with score has been updated and adds to the total (totalScoreSystem),
         * also plays some animations (renderSystem).
         */
        if (component.equals(ScoreComponent.class)) {
            totalScoreSystem.update(entityEngine.getEntity("GAME"), entity.getComponent(ScoreComponent.class));
            renderSystem.playScoreAnimation(entity);
        }
    }

    /**
     * Update entities. This method should be called only from system classes which updates (change component state)
     * of two entities.
     * @param e1 first entity for the systems
     * @param e2 second entity for the systems
     */
    public void update(Entity e1, Entity e2) {
        healthSystem.update(e1, e2);
        repositionSystem.update(e1, e2, entityEngine);
    }

    /**
     * Starts system engine. First it goes through all the entities which can listen
     * input from the users and updates them. Next it moves all the entities and finally
     * resolves all the collisions.
     */
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

        for (Pair<Entity, Entity> pair : entityEngine.getCollisions()) {
            collisionSystem.update(pair.getT1(), pair.getT2());
        }
    }
}
