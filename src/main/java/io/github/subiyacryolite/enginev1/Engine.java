package io.github.subiyacryolite.enginev1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Engine extends Application {
    public static Class<? extends Game> gameClass;
    private Game game;
    private Canvas canvas;


    @Override
    public void start(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            game = gameClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        canvas = new Canvas(game.getWidth(), game.getHeight());
        canvas.setOnKeyReleased(keyEvent -> game.onKeyReleased(keyEvent));
        canvas.setOnKeyPressed(keyEvent -> game.onKeyPressed(keyEvent));
        canvas.setOnMouseMoved(mouseEvent -> game.onMouseMoved(mouseEvent));
        canvas.setOnMouseClicked(mouseEvent -> game.onMouseClicked(mouseEvent));
        canvas.setOnScroll(scrollEvent -> game.onScroll(scrollEvent));
        canvas.setFocusTraversable(true);
        Group group = new Group();
        group.getChildren().add(canvas);
        stage.setScene(new Scene(group));
        stage.setOnCloseRequest(closeRequest -> game.onCloseRequest(closeRequest));
        game.setStage(stage);
        stage.setTitle("The SCND Genesis: Legends RMX");
        stage.show();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (game == null) return;
                if (game.getMode() != null && !game.isSwitchingModes()) {
                    game.getMode().logic(now);
                    game.getMode().render(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
                } else
                    game.drawLoadingAnimation(canvas.getGraphicsContext2D(), canvas.getWidth(), canvas.getHeight());
            }
        };
        animationTimer.start();
    }
}