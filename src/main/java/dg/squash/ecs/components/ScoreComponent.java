package dg.squash.ecs.components;

import dg.squash.events.ScoreListener;
import dg.squash.ecs.Entity;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public final class ScoreComponent extends AbstractComponent {

    private final SimpleIntegerProperty scoreProperty;
    public static final List<ScoreListener> SCORE_CHANGE_LISTENERS = new ArrayList<>();

    public ScoreComponent(Entity parent) {
        super(parent);
        scoreProperty = new SimpleIntegerProperty(this, "score", 0);
    }

    public int getScore() {
        return scoreProperty.get();
    }

    public SimpleIntegerProperty getScoreProperty() {
        return scoreProperty;
    }

    public void setScore(int score) {
        scoreProperty.set(score);
        notifyListeners(SCORE_CHANGE_LISTENERS);
    }

    private void notifyListeners(List<ScoreListener> componentListeners) {
        for (ScoreListener l : componentListeners)
            l.update(this);
    }


}
