package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public final class HealthComponent extends AbstractComponent<Integer> {

    public HealthComponent(Entity parent) {
        super(parent, 0);
    }

    public HealthComponent(Entity parent, int health) {
        super(parent, health);
    }
}
