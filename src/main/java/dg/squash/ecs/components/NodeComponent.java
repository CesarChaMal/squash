package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class NodeComponent extends AbstractComponent<Node> {

    public NodeComponent(Entity parent) {
        super(parent, new Rectangle());
    }

    public NodeComponent(Entity parent, Node node) {
        super(parent, node);
    }
}
