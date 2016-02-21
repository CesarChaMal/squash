package dg.squash.main;

import dg.squash.ecs.components.*;
import dg.squash.events.InputListener;
import dg.squash.math.Vector2D;
import dg.squash.shape.CircleShape;
import dg.squash.shape.RectangleShape;
import dg.squash.ecs.Entity;
import javafx.animation.Animation;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public final class EntityCreator {

    private EntityCreator() {

    }

    public static Entity createBall(Vector2D position, double radius, Vector2D velocity, Entity board) {
        Entity ball = new Entity();
        CircleShape circleShape = new CircleShape(position, radius);
        ImageView circle = new ImageView(AssetManager.BALL);
        circle.setFitHeight(2 * radius);
        circle.setFitWidth(2 * radius);

        ShapeComponent shapeComponent = new ShapeComponent(ball, circleShape);
        ShapeComponent node = board.getComponent(ShapeComponent.class);

        shapeComponent.getPositionXProperty().bind(node.getPositionXProperty().add(((RectangleShape) node.show()).getWidth() / 2));
        shapeComponent.getPositionYProperty().bind(node.getPositionYProperty().subtract(radius - 5));
        circle.layoutXProperty().bind(shapeComponent.getPositionXProperty().subtract(radius));
        circle.layoutYProperty().bind(shapeComponent.getPositionYProperty().subtract(radius));

        ball.addComponent(new VelocityComponent(ball, velocity));
        ball.addComponent(shapeComponent);
        ball.addComponent(new NodeComponent(ball, circle));
        ball.addComponent(new MassComponent(ball, 1));
        ball.addComponent(new DamageComponent(ball, 1));


        return ball;
    }

    public static Entity createBoard(Vector2D position, double width, double height, Game game) {
        Entity board = new Entity();
        RectangleShape rectangleShape = new RectangleShape(position, width, height);

        Scene scene = game.getScene();
        ShapeComponent shapeComponent = new ShapeComponent(board, rectangleShape);
        board.addComponent(shapeComponent);
        board.addComponent(new MassComponent(board));
        board.addComponent(new VelocityComponent(board));

        InputComponent inputComponent = new InputComponent(board);
        inputComponent.setLeft(new InputListener() {
            @Override
            public void update() {
                if (shapeComponent.getPosition().getX() - 25 < 0)
                    shapeComponent.setPosition(new Vector2D(0, shapeComponent.getPosition().getY()));
                else
                    shapeComponent.moveHorizontal(-25);
            }
        });
        ImageView rectangle = new ImageView(AssetManager.BOARD);
        inputComponent.setRight(new InputListener() {
            @Override
            public void update() {
                if (shapeComponent.getPosition().getX() + rectangle.getFitWidth() + 25 > scene.getWidth())
                    shapeComponent.setPosition(new Vector2D(scene.getWidth() - rectangle.getFitWidth(), shapeComponent.getPosition().getY()));
                else
                    shapeComponent.moveHorizontal(25);
            }
        });


        board.addComponent(inputComponent);
        rectangle.setFitHeight(height);
        rectangle.setFitWidth(width);
        rectangle.xProperty().bind(shapeComponent.getPositionXProperty());
        rectangle.yProperty().bind(shapeComponent.getPositionYProperty());

        board.addComponent(new NodeComponent(board, rectangle));

        return board;
    }

    public static Entity createWall(final Vector2D position, final double width, final double height) {
        Entity wall = new Entity();
        RectangleShape rectangle = new RectangleShape(position, width, height);
        wall.addComponent(new ShapeComponent(wall, rectangle));
        wall.addComponent(new VelocityComponent(wall));
        wall.addComponent(new MassComponent(wall));
        return wall;
    }

    public static Entity createTomato(Vector2D position, double width, double height, int health, Game game) {
        Entity tomato = new Entity();
        Rectangle rectangle = new Rectangle(width, height);

        switch (health) {
            case 1:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_CRYING));
                break;
            case 2:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_SAD));
                break;
            case 3:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_STUPID));
                break;
            case 4:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_ARE_YOU_JOKING));
                break;
            case 5:
                rectangle.setFill(new ImagePattern(AssetManager.TOMATO_LAUGHING));
                break;
        }

        NodeComponent node = new NodeComponent(tomato, rectangle);

        tomato.addComponent(node);
        RectangleShape rectangleShape = new RectangleShape(position, width, height);
        tomato.addComponent(new MassComponent(tomato));
        tomato.addComponent(new VelocityComponent(tomato));

        ShapeComponent shapeComponent = new ShapeComponent(tomato, rectangleShape);
        rectangle.xProperty().bind(shapeComponent.getPositionXProperty());
        rectangle.yProperty().bind(shapeComponent.getPositionYProperty());

        tomato.addComponent(shapeComponent);
        tomato.addComponent(new ScoreComponent(tomato));
        tomato.addComponent(new HealthComponent(tomato, health));

        return tomato;
    }

    public static Entity createMiddleRectangle(Vector2D position, double width, double height) {
        Entity middleRectangle = new Entity();
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setX(position.getX());
        rectangle.setY(position.getY());
        rectangle.setFill(Color.rgb(125, 125, 125, 0.5));

        middleRectangle.addComponent(new NodeComponent(middleRectangle, rectangle));
        return middleRectangle;
    }

    public static Entity createBackground(double width, double height) {
        Entity bg = new Entity();
        ImageView imageView = new ImageView(AssetManager.SPLASH_SCREEN_BACKGROUND);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        bg.addComponent(new NodeComponent(bg, imageView));
        return bg;
    }

    public static Entity createTotalScoreContainer(Vector2D position, double width, double height) {
        Entity totalScore = new Entity();

        ImageView scoreImage = new ImageView(AssetManager.SCORE);
        scoreImage.setLayoutX(position.getX());
        scoreImage.setLayoutY(position.getY());
        scoreImage.setFitWidth(width);
        scoreImage.setFitHeight(height);

        totalScore.addComponent(new NodeComponent(totalScore, scoreImage));
        return totalScore;

    }

    public static Entity totalScoreText(Vector2D position, int fontSize, SimpleIntegerProperty property) {
        Entity totalScore = new Entity();
        Text score = new Text();
        score.setX(position.getX());
        score.setY(position.getY());
        score.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, fontSize));
        score.setFill(Color.WHITE);
        score.textProperty().bind(property.asString());
        totalScore.addComponent(new NodeComponent(totalScore, score));
        return totalScore;
    }

    public static Entity createPauseButton(Vector2D position, double width, double height, Game game, Entity entity) {
        Entity pauseButton = new Entity();

        ImageView pause = new ImageView(AssetManager.PAUSE_BUTTON);
        pause.setLayoutX(position.getX());
        pause.setLayoutY(position.getY());
        pause.setFitWidth(width);
        pause.setFitHeight(height);
        pause.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pause.setImage(AssetManager.PAUSE_BUTTON_HOVER);
                game.getVisualDimension().setCursor(Cursor.CLOSED_HAND);
            }
        });
        pause.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pause.setImage(AssetManager.PAUSE_BUTTON);
                game.getVisualDimension().setCursor(Cursor.DEFAULT);
            }
        });

        Animation animation = AnimationCreator.pauseOnAnimation(entity.getComponent(NodeComponent.class).show());
        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (game.isRunning()) {
                    game.stopTime();
                    animation.play();
                }
            }
        });

        pauseButton.addComponent(new NodeComponent(pauseButton, pause));
        return pauseButton;
    }

    public static Entity createResumeButton(double width, double height, Game game, Entity entity) {
        Entity pauseButton = new Entity();

        ImageView container = (ImageView) entity.getComponent(NodeComponent.class).show();
        ImageView resume = new ImageView(AssetManager.RESUME_BUTTON);
        resume.setFitWidth(width);
        resume.setFitHeight(height);
        resume.layoutXProperty().bind(container.layoutXProperty().add(container.getFitWidth() / 2 - resume.getFitWidth() / 2));
        resume.layoutYProperty().bind(container.translateYProperty().subtract(300));
        resume.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resume.setImage(AssetManager.RESUME_BUTTON_HOVER);
                game.getVisualDimension().setCursor(Cursor.CLOSED_HAND);
            }
        });
        resume.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resume.setImage(AssetManager.RESUME_BUTTON);
                game.getVisualDimension().setCursor(Cursor.DEFAULT);
            }
        });

        Animation animation = AnimationCreator.pauseOffAnimation(entity.getComponent(NodeComponent.class).show());
        resume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!game.isRunning()) {
                    game.resumeTime();
                    animation.play();
                }
            }
        });

        pauseButton.addComponent(new NodeComponent(pauseButton, resume));
        return pauseButton;
    }

    public static Entity createRestartButton(double width, double height, Game game, Entity entity) {
        Entity pauseButton = new Entity();

        ImageView container = (ImageView) entity.getComponent(NodeComponent.class).show();
        ImageView restart = new ImageView(AssetManager.RESTART_BUTTON);
        restart.setFitWidth(width);
        restart.setFitHeight(height);
        restart.layoutXProperty().bind(container.layoutXProperty().add(container.getFitWidth() / 2 - restart.getFitWidth() / 2));
        restart.layoutYProperty().bind(container.translateYProperty().subtract(215));
        restart.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restart.setImage(AssetManager.RESTART_BUTTON_HOVER);
                game.getVisualDimension().setCursor(Cursor.CLOSED_HAND);
            }
        });
        restart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restart.setImage(AssetManager.RESTART_BUTTON);
                game.getVisualDimension().setCursor(Cursor.DEFAULT);
            }
        });

        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                game.reset();
            }
        });

        pauseButton.addComponent(new NodeComponent(pauseButton, restart));
        return pauseButton;
    }
    public static Entity createOptionsButton(double width, double height, Game game, Entity entity) {
        Entity pauseButton = new Entity();

        ImageView container = (ImageView) entity.getComponent(NodeComponent.class).show();
        ImageView restart = new ImageView(AssetManager.OPTIONS_BUTTON);
        restart.setFitWidth(width);
        restart.setFitHeight(height);
        restart.layoutXProperty().bind(container.layoutXProperty().add(container.getFitWidth() / 2 - restart.getFitWidth() / 2));
        restart.layoutYProperty().bind(container.translateYProperty().subtract(130));
        restart.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restart.setImage(AssetManager.OPTIONS_BUTTON_HOVER);
                game.getVisualDimension().setCursor(Cursor.CLOSED_HAND);
            }
        });
        restart.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restart.setImage(AssetManager.OPTIONS_BUTTON);
                game.getVisualDimension().setCursor(Cursor.DEFAULT);
            }
        });

        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            }
        });

        pauseButton.addComponent(new NodeComponent(pauseButton, restart));
        return pauseButton;
    }


    public static Entity createProgressBarContainer(Vector2D position, double width, double height) {
        Entity progressBar = new Entity();
        ImageView progress = new ImageView(AssetManager.PROGRESS);
        progress.setLayoutX(position.getX());
        progress.setLayoutY(position.getY());
        progress.setFitHeight(height);
        progress.setFitWidth(width);

        progressBar.addComponent(new NodeComponent(progressBar, progress));
        return progressBar;

    }

    public static Entity createProgressBar(Vector2D position, double width, double height, SimpleDoubleProperty property) {
        Entity progress = new Entity();
        ProgressBar completed = new ProgressBar();
        completed.setLayoutX(position.getX());
        completed.setLayoutY(position.getY());
        completed.setMinWidth(width);
        completed.setMinHeight(height);

        completed.progressProperty().bind(property);
        progress.addComponent(new NodeComponent(progress, completed));
        return progress;
    }

    public static Entity createPauseContainer(Vector2D position, double width, double height) {
        Entity pause = new Entity();
        ImageView pauseContainer = new ImageView(AssetManager.PAUSED_CONTAINER);
        pauseContainer.setFitWidth(width);
        pauseContainer.setFitHeight(height);
        pauseContainer.setLayoutX(position.getX());
        pauseContainer.setLayoutY(position.getY());
        pause.addComponent(new NodeComponent(pause, pauseContainer));
        return pause;
    }

}
