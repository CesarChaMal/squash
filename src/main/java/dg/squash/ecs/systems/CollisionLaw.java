package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.events.CollisionListener;
import dg.squash.math.Contact;
import dg.squash.math.ContactChecker;

import java.util.ArrayList;
import java.util.List;

public class CollisionLaw {

    public static final List<CollisionListener> COLLISION_LISTENERS = new ArrayList<>();

    public void apply(Entity entity1, Entity entity2) {

        Contact contact = ContactChecker.getContact(entity1, entity2);
        if (contact != null) {
            contact.resolve();
            for (CollisionListener l : COLLISION_LISTENERS)
                l.update(entity1, entity2);
        }
    }
}
