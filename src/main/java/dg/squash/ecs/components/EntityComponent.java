package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityComponent extends AbstractComponent<List<Entity>> {

    public EntityComponent(Entity parent, List<Entity> component) {
        super(parent, component);
    }

    public EntityComponent(Entity parent) {
        super(parent, new ArrayList<>());
    }
}
