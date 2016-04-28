package dg.squash.ecs.systems;

import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.InputComponent;

public class InputSystem extends AbstractECSystem {

    public InputSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(InputComponent component) {
        if (component.show().isLeftPressed()) {
            component.show().getLeft().update();
        }

        if (component.show().isRightPressed()) {
            component.show().getRight().update();
        }
    }
}
