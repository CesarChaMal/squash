package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public class MassComponent extends AbstractComponent {

    private double mass;

    public MassComponent(Entity parent) {
        super(parent);
        mass = Double.POSITIVE_INFINITY;
    }

    public MassComponent(Entity parent, double mass) {
        super(parent);
        this.mass = mass;
    }

    public double showInverseMass() {
        return 1.0 / mass;
    }
}
