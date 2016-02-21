package dg.squash.ecs.systems;

import dg.squash.ecs.components.InputComponent;

public class InputLaw {

    public void update(InputComponent component) {
        if (component.isLeftPressed()) {
            component.getLeft().update();
        }

        if (component.isRightPressed()) {
            component.getRight().update();
        }
    }
}
