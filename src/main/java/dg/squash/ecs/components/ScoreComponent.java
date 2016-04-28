package dg.squash.ecs.components;

import dg.squash.ecs.Entity;

public final class ScoreComponent extends AbstractComponent<Integer> {

    public ScoreComponent(Entity parent) {
        super(parent, 0);
    }

    public ScoreComponent(Entity parent, int score){
       super(parent, score);
    }
}
