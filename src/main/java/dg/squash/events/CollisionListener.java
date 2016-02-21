package dg.squash.events;

import dg.squash.ecs.Entity;

public interface CollisionListener {

    void update(Entity e1, Entity e2);
}
