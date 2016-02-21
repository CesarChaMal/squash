package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public abstract class AbstractComponent implements Component {

    private final Entity parent;

    protected AbstractComponent(Entity parent) {
        this.parent = parent;
    }

    @Override
    public Entity belongsTo() {
        return this.parent;
    }

}
