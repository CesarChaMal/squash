package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class NodeComponent extends AbstractComponent {

    private Node node;

    public NodeComponent(Entity parent) {
        super(parent);
        node = new Rectangle();
    }

    public NodeComponent(Entity parent, Node node) {
        super(parent);
        this.node = node;
    }

    public Node show() {
        return node;
    }
}
