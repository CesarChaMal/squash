package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import dg.squash.main.Input;

public class InputComponent extends AbstractComponent<Input> {

    public InputComponent(Entity parent) {
        super(parent, new Input());
    }

    public InputComponent(Entity parent, Input component) {
        super(parent, component);
    }
}
