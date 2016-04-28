package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.SystemEngine;
import dg.squash.math.Contact;
import dg.squash.math.ContactChecker;

public class CollisionSystem extends AbstractECSystem {

    public CollisionSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(Entity e1, Entity e2) {
        Contact contact = ContactChecker.getContact(e1, e2);
        if (contact != null) {
            contact.resolve();
            belongsTo().update(e1, e2);
        }
    }
}
