package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public class DamageComponent extends AbstractComponent {

    private int damage = 0;

    public DamageComponent(Entity parent) {
        super(parent);
    }

    public DamageComponent(Entity parent, int damage) {
        super(parent);
        this.damage = damage;
    }

    public int show() {
        return damage;
    }

    public void modify(int damage) {
        this.damage = damage;
    }

}
