package dg.squash.ecs.components;

import dg.squash.ecs.Entity;
import dg.squash.main.GameLogic;

public class GameComponent extends AbstractComponent<GameLogic> {

    public GameComponent(Entity parent, GameLogic component) {
        super(parent, component);
    }
}
