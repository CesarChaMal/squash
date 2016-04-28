package dg.squash.ecs;

import dg.squash.ecs.components.NameComponent;
import dg.squash.utils.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages all the entities as well as collisions in which those entities participate.
 * Collision consist of pair of two entities. Every collision should be defined and put in
 * collision list explicitly otherwise entities will not collide with each other.
 */
public class EntityEngine {

    // list to store all the entities
    private final Set<Entity> entities;

    // list to store all the collisions
    private final Set<Pair<Entity, Entity>> collisions;

    /**
     * Creates default entity engine with empty collision and
     * entity lists.
     */
    public EntityEngine() {
        this.entities = new HashSet<>();
        this.collisions = new HashSet<>();
    }

    /**
     * Returns all the entities in entity engine.
     * @return list of entities
     */
    public Set<Entity> getEntities() {
        return entities;
    }

    /**
     * Returns all collisions in entity engine.
     * @return list of collisions
     */
    public Set<Pair<Entity, Entity>> getCollisions() {
        return collisions;
    }

    /**
     * Adds collision to this engine. If one or both entities doesn't exists in the
     * entity engine, entities will be added in entity list as well.
     * @param collision to be added
     */
    public void addCollision(Pair<Entity, Entity> collision) {
        if (!entities.contains(collision.getT1()))
            entities.add(collision.getT1());
        if (!entities.contains(collision.getT2()))
            entities.add(collision.getT2());
        collisions.add(collision);
    }

    /**
     * Returns entity with particular name. If there is no entity with that name or an entity doesn't have
     * name component return null.
     * @param name string to look for entity
     * @return entity with supplied name.
     */
    public Entity getEntity(String name) {
        for (Entity e : entities)
            if (e.hasComponent(NameComponent.class) && e.getComponent(NameComponent.class).show().equals(name))
                return e;
        return null;
    }

    /**
     * Checks if entity engine contains an entity with particular name component.
     * @param name string to check for entity
     * @return true if this engine contains entity with supplied name, otherwise return false
     */
    public boolean hasEntity(String name) {
        for (Entity e : entities)
            if (e.hasComponent(NameComponent.class) && e.getComponent(NameComponent.class).show().equals(name))
                return true;
        return false;
    }

    /**
     * Removes collision from this entity engine.
     * @param collision to be removed
     */
    public void removeCollision(Pair<Entity, Entity> collision) {
        collisions.remove(collision);
    }

    /**
     * Removes entity from this engine. If the entity is part of collision, then that collision will
     * be removed as well.
     * @param entity to be removed
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
        collisions.removeAll(findPairs(entity));
    }

    /*
     * Checks if entity collides with another entity
     * @param entity to check if it collides with any other entity
     * @return list of collisions where entity is one of participant
     */
    private List<Pair<Entity, Entity>> findPairs(Entity entity) {
        List<Pair<Entity, Entity>> removals = new ArrayList<>();
        for (Pair pair : collisions)
            if ((pair.getT1() == entity) || (pair.getT2() == entity))
                removals.add(pair);
        return removals;
    }

}
