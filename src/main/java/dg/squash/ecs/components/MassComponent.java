package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public class MassComponent extends AbstractComponent<Double> {

    public MassComponent(Entity parent) {
        super(parent, Double.POSITIVE_INFINITY);
    }

    public MassComponent(Entity parent, double mass) {
        super(parent, mass);
    }

    public double showInverseMass() {
        return 1.0 / show();
    }
}
