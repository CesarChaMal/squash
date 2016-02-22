package dg.squash.main;

import dg.squash.ecs.Entity;
import dg.squash.ecs.components.*;
import dg.squash.math.Vector2D;
import dg.squash.shape.CircleShape;
import dg.squash.shape.RectangleShape;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public final class EntityCreator {

    private EntityCreator() {

    }

    public static Entity createBall(Vector2D position, double radius) {

        Entity ball = new Entity();
        CircleShape circleShape = new CircleShape(position, radius);
        ImageView circle = NodeCreator.createBoundView(AssetManager.BALL, circleShape.centerXProperty().subtract(radius),
                circleShape.centerYProperty().subtract(radius), circleShape.radiusProperty().multiply(2),
                circleShape.radiusProperty().multiply(2));
        ball.addComponent(new ShapeComponent(ball, circleShape));
        ball.addComponent(new NodeComponent(ball, circle));
        ball.addComponent(new VelocityComponent(ball, new Vector2D(0, 0)));
        ball.addComponent(new MassComponent(ball, 1));
        ball.addComponent(new DamageComponent(ball, 1));
        return ball;
    }

    public static Entity createBoard(Vector2D position, double width, double height) {
        Entity board = new Entity();
        RectangleShape rectangleShape = new RectangleShape(position, width, height);
        ImageView rectangle = NodeCreator.createBoundView(AssetManager.BOARD, rectangleShape);
        board.addComponent(new ShapeComponent(board, rectangleShape));
        board.addComponent(new MassComponent(board));
        board.addComponent(new VelocityComponent(board));
        board.addComponent(new InputComponent(board));
        board.addComponent(new NodeComponent(board, rectangle));

        return board;
    }

    public static Entity createWall(Vector2D position, double width, double height) {
        Entity wall = new Entity();
        RectangleShape rectangle = new RectangleShape(position, width, height);
        wall.addComponent(new ShapeComponent(wall, rectangle));
        wall.addComponent(new VelocityComponent(wall));
        wall.addComponent(new MassComponent(wall));
        return wall;
    }

    public static Entity createTomato(Vector2D position, double width, double height, int health) {
        Entity tomato = new Entity();
        RectangleShape rectangleShape = new RectangleShape(position, width, height);
        ImageView rectangle = NodeCreator.createBoundView(AssetManager.TOMATO_CRYING, rectangleShape);
        switch (health) {
            case 1:
                rectangle.setImage(AssetManager.TOMATO_CRYING);
                break;
            case 2:
                rectangle.setImage(AssetManager.TOMATO_SAD);
                break;
            case 3:
                rectangle.setImage(AssetManager.TOMATO_STUPID);
                break;
            case 4:
                rectangle.setImage(AssetManager.TOMATO_ARE_YOU_JOKING);
                break;
            case 5:
                rectangle.setImage(AssetManager.TOMATO_LAUGHING);
                break;
        }
        tomato.addComponent(new VelocityComponent(tomato, new Vector2D(0, 0)));
        tomato.addComponent(new MassComponent(tomato));
        tomato.addComponent(new ShapeComponent(tomato, rectangleShape));
        tomato.addComponent(new NodeComponent(tomato, rectangle));
        tomato.addComponent(new ScoreComponent(tomato));
        tomato.addComponent(new HealthComponent(tomato, health));
        return tomato;
    }

    public static Entity totalScoreText(Vector2D position, int fontSize) {
        Entity totalScore = new Entity();
        Font font = Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, fontSize);
        Text score = NodeCreator.createText(position, font, Color.WHITE);
        totalScore.addComponent(new NodeComponent(totalScore, score));
        return totalScore;
    }

    public static Entity createPauseButton(Vector2D position, double width, double height) {
        Entity pauseButton = new Entity();
        ImageView pause = NodeCreator.createView(position, width, height, AssetManager.PAUSE_BUTTON);

        pause.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pause.setImage(AssetManager.PAUSE_BUTTON_HOVER);
            }
        });
        pause.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pause.setImage(AssetManager.PAUSE_BUTTON);
            }
        });
        pauseButton.addComponent(new NodeComponent(pauseButton, pause));
        return pauseButton;
    }

    public static Entity createResumeButton(double width, double height) {
        Entity pauseButton = new Entity();
        ImageView resume = NodeCreator.createView(new Vector2D(0, 0), width, height, AssetManager.RESUME_BUTTON);
        resume.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resume.setImage(AssetManager.RESUME_BUTTON_HOVER);
            }
        });
        resume.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resume.setImage(AssetManager.RESUME_BUTTON);
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

    public static Entity createProgressBar(ImageView barContainer) {
        Entity progress = new Entity();
        ProgressBar completed = NodeCreator.createBoundProgressBar(
                barContainer.layoutXProperty().add(60), barContainer.layoutYProperty().add(22),
                barContainer.fitWidthProperty().subtract(67), barContainer.fitHeightProperty().subtract(265));

        progress.addComponent(new NodeComponent(progress, completed));
        return progress;
    }

    public static Entity createMiddleRectangle(Vector2D position, double width, double height) {
        Entity entity = new Entity();
        entity.addComponent(new NodeComponent(entity, NodeCreator.createRectangle(position, width, height, Color.rgb(125, 125, 125, 0.5))));
        return entity;
    }

    public static Entity createTotalScoreContainer(Vector2D position, double width, double height) {
        return entityWithViewNode(position, width, height, AssetManager.SCORE);
    }

    public static Entity createProgressBarContainer(Vector2D position, double width, double height) {
        return entityWithViewNode(position, width, height, AssetManager.PROGRESS);
    }

    public static Entity createPauseContainer(Vector2D position, double width, double height) {
        return entityWithViewNode(position, width, height, AssetManager.PAUSED_CONTAINER);
    }

    public static Entity createBackground(double width, double height) {
        return entityWithViewNode(new Vector2D(0, 0), width, height, AssetManager.SPLASH_SCREEN_BACKGROUND);
    }

    private static Entity entityWithViewNode(Vector2D position, double width, double height, Image image) {
        Entity entity = new Entity();
        entity.addComponent(new NodeComponent(entity, NodeCreator.createView(position, width, height, image)));
        return entity;
    }
}
