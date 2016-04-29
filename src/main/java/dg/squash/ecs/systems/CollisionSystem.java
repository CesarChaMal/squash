package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;
import dg.squash.math.Contact;
import dg.squash.math.ContactChecker;

/**
 * System responsible for collision handling.
 */
public class CollisionSystem extends AbstractECSystem {

    public CollisionSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    /**
     * Checks for collisions, resolves it, and notifies about that parent systemEngine
     * @param e1 first entity for collision
     * @param e2 second entity for collision
     */
    public void update(Entity e1, Entity e2) {
        Contact contact = ContactChecker.getContact(e1, e2);
        if (contact != null) {
            contact.resolve();
            belongsTo().update(e1, e2);
        }
    }
}
