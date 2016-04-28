package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public abstract class AbstractComponent<T> implements Component<T> {

    private T component;
    private final Entity parent;

    public AbstractComponent(Entity parent, T component) {
        this.parent = parent;
        this.component = component;
    }

    @Override
    public T show() {
        return component;
    }

    @Override
    public void modify(T component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return component.toString();
    }

    @Override
    public Entity belongsTo() {
        return this.parent;
    }
}
