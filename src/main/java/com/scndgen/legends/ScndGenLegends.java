package com.scndgen.legends;

import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.network.NetworkManager;
import com.scndgen.legends.render.*;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Engine;
import io.github.subiyacryolite.enginev1.FxDialogs;
import io.github.subiyacryolite.enginev1.Game;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.WindowEvent;

/**
 * Created by ifunga on 15/04/2017.
 */
public class ScndGenLegends extends Game {

    private static ScndGenLegends instance;
    private SubMode subMode;
    private ModeEnum modeEnum;
    private double mouseX;
    private double mouseY;
    private String targetIp = "192.168.1.103";

    public static ScndGenLegends getInstance() {
        return instance;
    }

    public static void main(String[] main) {
        Engine.applicationStage = ScndGenLegends.class;
        Application.launch(Engine.class);
    }

    public ScndGenLegends() {
        instance = this;
        setSize(852, 480);
        loadMode(ModeEnum.MAIN_MENU);
    }

    public void loadMode(ModeEnum modeEnum) {
        this.modeEnum = modeEnum;
        setSwitchingModes(true);
        try {
            switch (modeEnum) {
                case MAIN_MENU:
                    RenderMainMenu.getInstance().newInstance();
                    setMode(RenderMainMenu.getInstance());
                    break;
                case STORY_SELECT_SCREEN:
                    RenderStoryMenu.getInstance().newInstance();
                    setMode(RenderStoryMenu.getInstance());
                    break;
                case CHAR_SELECT_SCREEN:
                    RenderCharacterSelection.getInstance().newInstance();
                    switch (getSubMode()) {
                        case LAN_HOST:
                            NetworkManager.getInstance().asHost();
                            break;
                        case LAN_CLIENT:
                            targetIp = FxDialogs.input("title", "header", "content", targetIp);
                            NetworkManager.getInstance().asClient(targetIp);
                            break;
                    }
                    setMode(RenderCharacterSelection.getInstance());
                    break;
                case STAGE_SELECT_SCREEN:
                    RenderStageSelect.getInstance().newInstance();
                    setMode(RenderStageSelect.getInstance());
                    break;
                case STANDARD_GAMEPLAY_START:
                    RenderGamePlay.getInstance().newInstance();
                    setMode(RenderGamePlay.getInstance());
                    RenderGamePlay.getInstance().startFight();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        } finally {
            setSwitchingModes(false);
        }
    }

    public SubMode getSubMode() {
        return this.subMode;
    }


    public void setSubMode(SubMode subMode) {
        this.subMode = subMode;
    }

    @Override
    public void onKeyReleased(KeyEvent keyEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().keyReleased(keyEvent);
    }

    @Override
    public void onKeyPressed(KeyEvent keyEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().keyPressed(keyEvent);
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        setMouseX(mouseEvent.getX());
        setMouseY(mouseEvent.getY());
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseMoved(mouseEvent);
    }

    @Override
    public void onMouseClicked(MouseEvent mouseEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseClicked(mouseEvent);
    }

    public void onScroll(ScrollEvent scrollEvent) {
        if (this.getMode() != null && !isSwitchingModes())
            this.getMode().mouseScrolled(scrollEvent);
    }

    public void shutDown() {
        NetworkManager.getInstance().close();
        AudioPlayback.closeAll();
    }

    public void onCloseRequest(WindowEvent closeRequest) {
        shutDown();
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }
}
