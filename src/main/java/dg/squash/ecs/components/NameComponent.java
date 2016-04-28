package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public class NameComponent extends AbstractComponent<String> {

    public NameComponent(Entity parent, String component) {
        super(parent, component);
    }

    public NameComponent(Entity parent) {
        super(parent, "ENTITY");
    }
}
