package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;

/**
 * Interface which {@link dg.squash.ecs.systems.AbstractECSystem} implements. It contains only one
 * method which gives reference to to the {@link dg.squash.ecs.SystemEngine} which is responsible for managing
 * this system. By having a reference system can call {@link dg.squash.ecs.SystemEngine#update(Entity, Entity)}
 * methods to notify about changes which it have done to the component.
 */
public interface ECSystem {

    /**
     * Returns a reference of system engine (parent) for any systems (child).
     * @return system engine
     */
    SystemEngine belongsTo();
}
