package dg.squash.main;


import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class AssetManager {

     public static final AudioClip BE_HAPPY = new AudioClip(AssetManager.class.getResource("/assets/audio/be_happy.mp3").toExternalForm());
    public static final AudioClip SPLAT = new AudioClip(AssetManager.class.getResource("/assets/audio/splat.mp3").toExternalForm());
    public static final AudioClip TOMATO_HIT = new AudioClip(AssetManager.class.getResource("/assets/audio/tomato_hit.mp3").toExternalForm());

    public static final Image SPLASH_SCREEN_BACKGROUND = new Image("/assets/images/bg.jpg");
    public static final Image PAUSED_CONTAINER = new Image("/assets/images/paused_container.png");
    public static final Image BALL = new Image("/assets/images/ball.png");
    public static final Image PAUSE_BUTTON = new Image("/assets/images/pause.png");
    public static final Image PAUSE_BUTTON_HOVER = new Image("/assets/images/pause_hover.png");
    public static final Image RESUME_BUTTON = new Image("/assets/images/resume.png");
    public static final Image RESUME_BUTTON_HOVER = new Image("/assets/images/resume_hover.png");
    public static final Image RESTART_BUTTON = new Image("/assets/images/restart.png");
    public static final Image RESTART_BUTTON_HOVER = new Image("/assets/images/restart_hover.png");
    public static final Image OPTIONS_BUTTON = new Image("/assets/images/options.png");
    public static final Image OPTIONS_BUTTON_HOVER = new Image("/assets/images/options_hover.png");
    public static final Image EXIT_BUTTON = new Image("/assets/images/exit.png");
    public static final Image EXIT_BUTTON_HOVER = new Image("/assets/images/exit_hover.png");
    public static final Image PLAY_BUTTON = new Image("/assets/images/play.png");
    public static final Image PLAY_BUTTON_HOVER = new Image("/assets/images/play_hover.png");
    public static final Image PROGRESS = new Image("/assets/images/progress.png");
    public static final Image SCORE = new Image("/assets/images/score.png");

    public static final Image TOMATO_SPLAT_1 = new Image("/assets/images/splat_004.png");
    public static final Image TOMATO_SPLAT_2 = new Image("/assets/images/splat_005.png");
    public static final Image TOMATO_SPLAT_3 = new Image("/assets/images/splat_006.png");
    public static final Image TOMATO_SPLAT_4 = new Image("/assets/images/splat_007.png");
    public static final Image TOMATO_SPLAT_5 = new Image("/assets/images/splat_008.png");

    public static final Image TOMATO_ARE_YOU_JOKING = new Image("/assets/images/t_05.png");
    public static final Image TOMATO_SAD = new Image("/assets/images/t_08.png");
    public static final Image TOMATO_CRYING = new Image("/assets/images/t_10.png");
    public static final Image TOMATO_LAUGHING = new Image("/assets/images/t_11.png");
    public static final Image TOMATO_STUPID = new Image("/assets/images/t_15.png");

    public static final Image TITLE = new Image("/assets/images/title.png");
    public static final Image BOARD = new Image("/assets/images/wood.png");


    private AssetManager() {
    }

    static {


    }
}
