package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import dg.squash.events.InputListener;

public class InputComponent extends AbstractComponent {

    private InputListener left;
    private InputListener right;
    private InputListener mouseClick;

    public InputListener getMouseClick() {
        return mouseClick;
    }

    public void setMouseClick(InputListener mouseClick) {
        this.mouseClick = mouseClick;
    }


    public InputListener getRight() {
        return right;
    }

    public void setRight(InputListener right) {
        this.right = right;
    }

    public InputListener getLeft() {
        return left;
    }

    public void setLeft(InputListener left) {
        this.left = left;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean mouseClicked = false;

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public void setMouseClicked(boolean mouseClicked) {
        this.mouseClicked = mouseClicked;
    }


    public InputComponent(Entity parent) {
        super(parent);
    }
}
