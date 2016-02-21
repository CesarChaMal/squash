package dg.squash.main;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class GameView {

    private Scene scene;
    private Group root;
    private ProgressBar completed;
    private Text score;
    private Timeline startAnimation;
    private Text timerText;

    public GameView() {
        root = new Group();
        this.scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
    }

    public Scene getScene() {
        return scene;
    }

    public void updateProgress(int totalEnemies, int destroyedEnemies) {
        if (totalEnemies != 0)
            completed.setProgress((double) destroyedEnemies / totalEnemies);
    }

}
