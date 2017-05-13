package io.github.subiyacryolite.enginev1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Engine extends Application {
    public static Class<? extends Game> applicationStage;
    private Game game;
    private Canvas canvas;


    @Override
    public void start(Stage stage) {
        try {
            game = applicationStage.newInstance();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        canvas = new Canvas(game.getWidth(), game.getHeight());
        canvas.setOnKeyReleased(keyEvent -> game.keyReleased(keyEvent));
        canvas.setOnKeyPressed(keyEvent -> game.keyPressed(keyEvent));
        canvas.setOnMouseMoved(mouseEvent -> game.mouseMoved(mouseEvent));
        canvas.setOnMouseClicked(mouseEvent -> game.mouseClicked(mouseEvent));
        canvas.setFocusTraversable(true);
        Group group = new Group();
        group.getChildren().add(canvas);
        stage.setScene(new Scene(group));
        stage.show();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //canvas.getGraphicsContext2D().setGlobalBlendMode(BlendMode.ADD);
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