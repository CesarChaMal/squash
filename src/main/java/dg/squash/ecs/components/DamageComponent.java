package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public class DamageComponent extends AbstractComponent<Integer> {

    public DamageComponent(Entity parent, int damage) {
        super(parent, damage);
    }

    public DamageComponent(Entity parent) {
        super(parent, 0);
    }
}
