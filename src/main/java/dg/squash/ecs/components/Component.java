package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public interface Component<T> {
    Entity belongsTo();
    T show();
    void modify(T component);
}
