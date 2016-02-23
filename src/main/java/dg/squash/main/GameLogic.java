package dg.squash.main;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameLogic {

    private SimpleIntegerProperty totalScoreProperty;
    private SimpleIntegerProperty destroyedEnemiesProperty;
    private SimpleDoubleProperty progressProperty;
    private int totalEnemies;


    public GameLogic() {
        totalScoreProperty = new SimpleIntegerProperty(this, "totalScore", 0);
        destroyedEnemiesProperty = new SimpleIntegerProperty(this, "destroyedEnemies", 0);
        progressProperty = new SimpleDoubleProperty(this, "progress", 0);
        this.totalEnemies = 0;
    }

    public void destroyEnemy() {
        destroyedEnemiesProperty.set(destroyedEnemiesProperty.get() + 1);
        progressProperty.set((double) destroyedEnemiesProperty.get() / totalEnemies);

    }

    public int getTotalEnemies() {
        return totalEnemies;
    }

    public void setTotalEnemies(int totalEnemies) {
        this.totalEnemies = totalEnemies;
    }

    public int getTotalScore() {
        return totalScoreProperty.get();
    }

    public void setTotalScore(int totalScore) {
        this.totalScoreProperty.set(totalScore);
    }

    public SimpleIntegerProperty totalScoreProperty() {
        return totalScoreProperty;
    }

    public int getDestroyedEnemies() {
        return destroyedEnemiesProperty.get();
    }

    public SimpleIntegerProperty destroyedEnemiesProperty() {
        return destroyedEnemiesProperty;
    }

    public SimpleDoubleProperty progressProperty(){
        return progressProperty;
    }
}
