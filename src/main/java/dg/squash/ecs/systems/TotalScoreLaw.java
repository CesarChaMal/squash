package dg.squash.ecs.systems;

import dg.squash.ecs.components.ScoreComponent;
import dg.squash.events.ScoreListener;
import dg.squash.main.Game;

public class TotalScoreLaw implements ScoreListener {

    private Game game;

    public TotalScoreLaw(Game game) {
        this.game = game;
    }

    @Override
    public void update(ScoreComponent component) {
        int score = component.getScore();
        game.increaseScore(score);
    }
}
