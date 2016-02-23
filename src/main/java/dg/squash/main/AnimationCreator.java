package dg.squash.main;

public class AnimationCreator {

    private AnimationCreator() {

    }







/*    public static Animation timerAnimation(Game game) {

        Text createTimerText = new Text();
        createTimerText.setX(game.getScene().getWidth() / 2 - 50);
        createTimerText.setY(game.getScene().getHeight() / 2 - createTimerText.getWrappingWidth() / 2);
        createTimerText.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 150));
        createTimerText.setFill(Color.WHITE);
        createTimerText.setText(String.valueOf(3));
        createTimerText.setStroke(Color.YELLOW);
        createTimerText.setStrokeWidth(4);

        if (!game.getVisualDimension().getChildren().contains(createTimerText))
            game.getVisualDimension().getChildren().add(createTimerText);

        Timeline startAnimation = new Timeline();
        int dur = 0;
        for (int i = 3; i >= 0; i--) {
            KeyFrame keyFrame;
            keyFrame = new KeyFrame(Duration.millis(dur), new KeyValue(createTimerText.textProperty(), String.valueOf(i)));
            startAnimation.getKeyFrames().add(keyFrame);
            dur += 1000;
        }


        startAnimation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        return startAnimation;
    }*/
}
