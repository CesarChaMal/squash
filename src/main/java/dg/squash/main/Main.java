package dg.squash.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final double WIDTH = 1024;
    public static final double HEIGHT = 768;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage = primaryStage;
        stage.setTitle("Squash");
        stage.sizeToScene();
        stage.setResizable(false);

       // Game game = new Game(WIDTH, HEIGHT);

    }

    public static void main(String[] args) {

    }

}
