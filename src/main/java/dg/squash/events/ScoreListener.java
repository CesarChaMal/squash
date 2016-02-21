package dg.squash.events;

import dg.squash.ecs.components.ScoreComponent;

public interface ScoreListener {

    void update(ScoreComponent component);
}
