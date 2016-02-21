package dg.squash.events;

import dg.squash.ecs.components.HealthComponent;

public interface HealthListener {

    void update(HealthComponent component);
}
