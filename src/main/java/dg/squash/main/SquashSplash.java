package dg.squash.main;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
public class SquashSplash {

    private Scene scene;
    private Group root;

    public SquashSplash() {

        root = new Group();
        this.scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        init();
    }

    public Scene getScene() {
        return scene;
    }

    private void initBackground() {

        ImageView iv = new ImageView(AssetManager.SPLASH_SCREEN_BACKGROUND);
        iv.fitWidthProperty().bind(scene.widthProperty());
        iv.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(iv);
    }

    private void initTitle() {
        ImageView title = new ImageView(AssetManager.TITLE);
        title.setFitHeight(150);
        title.setFitWidth(450);
        title.setX(scene.getWidth() / 2 - title.getFitWidth() / 2);
        title.setY(100);
        root.getChildren().add(title);
    }

    public void initPlayButton(EventHandler<MouseEvent> onClick) {

        ImageView playButton = new ImageView(AssetManager.PLAY_BUTTON);
        playButton.setFitHeight(80);
        playButton.setFitWidth(230);
        playButton.setX(scene.getWidth() / 2 - playButton.getFitWidth() / 2);
        playButton.setY(350);
        root.getChildren().add(playButton);

        playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playButton.setImage(AssetManager.PLAY_BUTTON_HOVER);
                root.setCursor(Cursor.CLOSED_HAND);
            }
        });

        playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playButton.setImage(AssetManager.PLAY_BUTTON);
                root.setCursor(Cursor.DEFAULT);
            }
        });
        playButton.setOnMouseClicked(onClick);
    }

    public void initExitButton() {

        ImageView playButton = new ImageView(AssetManager.EXIT_BUTTON);
        playButton.setFitHeight(60);
        playButton.setFitWidth(200);
        playButton.setX(scene.getWidth() / 2 - playButton.getFitWidth() / 5);
        playButton.setY(500);
        root.getChildren().add(playButton);

        playButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playButton.setImage(AssetManager.EXIT_BUTTON_HOVER);
                root.setCursor(Cursor.CLOSED_HAND);
            }
        });

        playButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playButton.setImage(AssetManager.EXIT_BUTTON);
                root.setCursor(Cursor.DEFAULT);
            }
        });
        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(0);
            }
        });
    }

    private void init() {
        initBackground();
        initTitle();
        initExitButton();
    }
}
