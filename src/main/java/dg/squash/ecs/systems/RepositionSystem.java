package dg.squash.ecs.systems;

import dg.squash.ecs.Entity;
import dg.squash.ecs.EntityEngine;
import dg.squash.ecs.SystemEngine;
import dg.squash.ecs.components.EntityComponent;
import dg.squash.ecs.components.NameComponent;
import dg.squash.ecs.components.ShapeComponent;
import dg.squash.main.Squash;
import dg.squash.shape.CircleShape;
import dg.squash.shape.RectangleShape;

public class RepositionSystem extends AbstractECSystem {

    public RepositionSystem(SystemEngine systemEngine) {
        super(systemEngine);
    }

    public void update(Entity e1, Entity e2, EntityEngine entityEngine) {

        if (e2.getComponent(NameComponent.class).show().equals("BALL")) {
            CircleShape circle = ((CircleShape) e2.getComponent(ShapeComponent.class).show());

            if (circle.getPosition().getY() + circle.getRadius() >= Squash.HEIGHT) {
                RectangleShape board = (RectangleShape) entityEngine.getEntity("BOARD").getComponent(ShapeComponent.class).show();
                circle.centerXProperty().bind(board.positionXProperty().add(board.getWidth() / 2));
                circle.centerYProperty().bind(board.positionYProperty().subtract(circle.getRadius()));

                Entity live = null;
                if (entityEngine.getEntity("LIVE_2") != null)
                    live = entityEngine.getEntity("LIVE_2");
                else if (entityEngine.getEntity("LIVE_1") != null)
                    live = entityEngine.getEntity("LIVE_1");
                else if (entityEngine.getEntity("LIVE_0") != null)
                    live = entityEngine.getEntity("LIVE_0");
                if (live != null)
                    entityEngine.getEntity("BIN").getComponent(EntityComponent.class).show().add(live);
                else {
                    Entity restart = new Entity();
                    restart.addComponent(new NameComponent(restart, "RESTART"));
                    entityEngine.getEntities().add(restart);
                }
            }
        }
    }
}
