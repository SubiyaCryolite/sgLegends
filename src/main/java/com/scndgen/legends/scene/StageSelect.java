/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana (http://www.scndgen.sf.net).

 The SCND Genesis: Legends  © 2011 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <http://www.gnu.org/licenses/>.

 **************************************************************************/
package com.scndgen.legends.scene;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.JenesisMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Hashtable;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public abstract class StageSelect extends JenesisMode {

    protected Stage hoveredStage = Stage.IBEX_HILL;
    protected int charYcap = 0, charXcap = 0, column, x = 0, y = 0, row = 0, vSpacer = 52, hSpacer = 92, hPos = 288, firstLine = 105;
    protected StageSelection mode;
    protected int ambientMusicIndex = 0;
    protected final int numberOfStages = Stage.values().length;
    protected final int columns = 3;
    protected final int rows = numberOfStages / columns;
    protected boolean selectedStage;
    protected final Hashtable<Integer, Stage> stageLookup = new Hashtable<>();
    protected final Hashtable<Stage, String> lookupStageNames = new Hashtable<>();
    protected final String[] stagePreviews = new String[Stage.values().length];
    protected int storedX;
    protected int storedY;
    protected String fgLocation;
    protected String bgLocation;

    public void newInstance() {
        loadAssets = true;
        hoveredStage = Stage.IBEX_HILL;
        mode = StageSelection.NORMAL;
        selectedStage = false;
        stageLookup.clear();
        for (Stage stage : Stage.values()) {
            stageLookup.put(stage.index(), stage);
        }
        //===============================================================
        stagePreviews[Stage.IBEX_HILL.index()] = "bgBG1";
        stagePreviews[Stage.CHELSTON_CITY_DOCKS.index()] = "bgBG2";
        stagePreviews[Stage.DESERT_RUINS.index()] = "bgBG3";
        stagePreviews[Stage.CHELSTON_CITY_STREETS.index()] = "bgBG4";
        stagePreviews[Stage.IBEX_HILL_NIGHT.index()] = "bgBG5";
        stagePreviews[Stage.SCORCHED_RUINS.index()] = "bgBG6";
        stagePreviews[Stage.FROZEN_WILDERNESS.index()] = "bgBG7";
        stagePreviews[Stage.DISTANT_ISLE.index()] = "bgBG100";
        stagePreviews[Stage.HIDDEN_CAVE.index()] = "bgBG8";
        stagePreviews[Stage.AFRICAN_VILLAGE.index()] = "bgBG9";
        stagePreviews[Stage.APOCALYPTO.index()] = "bgBG10";
        stagePreviews[Stage.DISTANT_ISLE_NIGHT.index()] = "bgBG11";
        stagePreviews[Stage.DESERT_RUINS_NIGHT.index()] = "bgBG13";
        stagePreviews[Stage.SCORCHED_RUINS_NIGHT.index()] = "bgBG14";
        stagePreviews[Stage.RANDOM.index()] = "bgBG12";
        stagePreviews[Stage.HIDDEN_CAVE_NIGHT.index()] = "bgBG15";
        //===============================================================
        Language language = Language.getInstance();
        lookupStageNames.clear();
        lookupStageNames.put(Stage.IBEX_HILL, language.get(152));
        lookupStageNames.put(Stage.CHELSTON_CITY_DOCKS, language.get(153));
        lookupStageNames.put(Stage.DESERT_RUINS, language.get(154));
        lookupStageNames.put(Stage.CHELSTON_CITY_STREETS, language.get(155));
        lookupStageNames.put(Stage.IBEX_HILL_NIGHT, language.get(156));
        lookupStageNames.put(Stage.SCORCHED_RUINS, language.get(157));
        lookupStageNames.put(Stage.FROZEN_WILDERNESS, language.get(158));
        lookupStageNames.put(Stage.DISTANT_ISLE, language.get(162));
        lookupStageNames.put(Stage.HIDDEN_CAVE, language.get(159));
        lookupStageNames.put(Stage.AFRICAN_VILLAGE, language.get(160));
        lookupStageNames.put(Stage.APOCALYPTO, language.get(161));
        lookupStageNames.put(Stage.DISTANT_ISLE_NIGHT, language.get(163));
        lookupStageNames.put(Stage.DESERT_RUINS_NIGHT, language.get(369));
        lookupStageNames.put(Stage.SCORCHED_RUINS_NIGHT, language.get(370));
        lookupStageNames.put(Stage.RANDOM, language.get(164));
        lookupStageNames.put(Stage.HIDDEN_CAVE_NIGHT, language.get(371));
    }

    public StageSelect() {

    }

    /**
     * SHows loading screen
     */
    public void nowLoading() {
        selectedStage = true;
    }

    public void selectStage(Stage stage) {
        hoveredStage = stage;
        if (mode == StageSelection.NORMAL) {
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                nowLoading();
                if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                    JenesisPanel.getInstance().sendToClient("loadingGVSHA");
                }
            }
        } else {
            hoveredStage = stageLookup.get((int) (Math.random() * (numberOfStages - 1)));
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                nowLoading();
                if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                    JenesisPanel.getInstance().sendToClient("loadingGVSHA");
                }
            }
        }
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE || ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
            switch (hoveredStage) {
                case IBEX_HILL:
                    selectIbexHill();
                    break;
                case CHELSTON_CITY_DOCKS:
                    selectChelsonCityDocks();
                    break;
                case DESERT_RUINS:
                    selectDesertRuins();
                    break;
                case CHELSTON_CITY_STREETS:
                    selectChelstonCityStreets();
                    break;
                case IBEX_HILL_NIGHT:
                    selectIbexHillNight();
                    break;
                case SCORCHED_RUINS:
                    selectScorchedRuins();
                    break;
                case FROZEN_WILDERNESS:
                    selectDistantSnowField();
                    break;
                case DISTANT_ISLE:
                    selectDistantIsle();
                    break;
                case HIDDEN_CAVE:
                    selectHiddenCave();
                    break;
                case HIDDEN_CAVE_NIGHT:
                    selectHiddenCaveNight();
                    break;
                case AFRICAN_VILLAGE:
                    selectAfricanVillage();
                    break;
                case APOCALYPTO:
                    selectApocalypto();
                    break;
                case DISTANT_ISLE_NIGHT:
                    selectDistantIsleNight();
                    break;
                case RANDOM:
                    selectRandomStage();
                    break;
                case DESERT_RUINS_NIGHT:
                    selectDesertRuinsNight();
                    break;
                case SCORCHED_RUINS_NIGHT:
                    selectScorchedRuinsNight();
                    break;
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient(hoveredStage.shortCode());
            }
        }
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE || ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST || ScndGenLegends.getInstance().getSubMode() == SubMode.WATCH) {
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("gameStart7%^&");
            }
            start();
        }
    }

    public void defaultStageValues() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 10;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 10;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.VERTICAL;
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 4;
        RenderGameplay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 0;
    }

    /**
     * Ibex Hill- day
     */
    private void selectIbexHill() //
    {
        defaultStageValues();
    }

    /**
     * Chelston City docks
     */
    private void selectChelsonCityDocks() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 1;
    }

    /**
     * The Ruined Hall
     */
    private void selectDesertRuins() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 5;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 4;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 2;
    }

    /**
     * Chelston City - Streets
     */
    private void selectChelstonCityStreets() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
    }

    /**
     * Ibex Hill - Night
     */
    private void selectIbexHillNight() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 10;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 10;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.VERTICAL;
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 4;
        RenderGameplay.getInstance().ambSpeed2 = 3;
        ambientMusicIndex = 4;
    }

    /**
     * Scorched Ruins
     */
    private void selectScorchedRuins() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 5;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 4;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 5;
    }

    /**
     * Frozen Wilderness
     */
    private void selectDistantSnowField() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 10;
        RenderGameplay.getInstance().foreGroundXIncrement = 5;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 4;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 6;
    }

    /**
     * Distant Isle
     */
    private void selectDistantIsle() {
        RenderGameplay.getInstance().foreGroundPositionX = -40;
        RenderGameplay.getInstance().foreGroundPositionY = 20;
        RenderGameplay.getInstance().foreGroundXIncrement = 2;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.ROTATION;
        RenderGameplay.getInstance().animLayer = "onBackCancel";
        RenderGameplay.getInstance().delay = 25;
        RenderGameplay.getInstance().ambSpeed1 = 1;
        RenderGameplay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 0;
    }

    private void selectRandomStage() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
    }

    /**
     * Distant Isle night
     */
    private void selectDistantIsleNight() {
        RenderGameplay.getInstance().foreGroundPositionX = -40;
        RenderGameplay.getInstance().foreGroundPositionY = 20;
        RenderGameplay.getInstance().foreGroundXIncrement = 2;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.ROTATION;
        RenderGameplay.getInstance().animLayer = "onBackCancel";
        RenderGameplay.getInstance().delay = 25;
        RenderGameplay.getInstance().ambSpeed1 = 1;
        RenderGameplay.getInstance().ambSpeed2 = 2;
        ambientMusicIndex = 1;
    }

    /**
     * Hidden Cave
     */
    private void selectHiddenCave() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 2;
    }

    private void selectHiddenCaveNight() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 2;
    }

    /**
     * African Village
     */
    private void selectAfricanVillage() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "onBackCancel";
        RenderGameplay.getInstance().delay = 122;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 3;
    }

    /**
     * The Apocalypse
     */
    private void selectApocalypto() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 5;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 4;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "both";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 4;
    }

    private void selectDesertRuinsNight() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 5;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 4;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "forg";
        RenderGameplay.getInstance().delay = 33;
        RenderGameplay.getInstance().ambSpeed1 = 2;
        RenderGameplay.getInstance().ambSpeed2 = 1;
        ambientMusicIndex = 1;
    }

    private void selectScorchedRuinsNight() {
        RenderGameplay.getInstance().foreGroundPositionX = 0;
        RenderGameplay.getInstance().foreGroundPositionY = 0;
        RenderGameplay.getInstance().foreGroundXIncrement = 1;
        RenderGameplay.getInstance().foreGroundYIncrement = 1;
        RenderGameplay.getInstance().animationLoops = 20;
        RenderGameplay.getInstance().animationDirection = AnimationDirection.NONE;
        RenderGameplay.getInstance().animLayer = "none";
        RenderGameplay.getInstance().delay = 66;
        RenderGameplay.getInstance().ambSpeed1 = 0;
        RenderGameplay.getInstance().ambSpeed2 = 0;
        ambientMusicIndex = 3;
    }


    /**
     * @return lastStoryScene
     */
    public String getStage() {
        return stagePreviews[hoveredStage.index()];
    }

    public void start() {
        bgLocation = "images/bgBG" + hoveredStage.filePrefix() + ".png";
        fgLocation = "images/bgBG" + hoveredStage.filePrefix() + "fg.png";
        ScndGenLegends.getInstance().loadMode(Mode.STANDARD_GAMEPLAY_START);

    }

    public String getBgLocation() {
        return bgLocation;
    }

    public String getFgLocation() {
        return fgLocation;
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both CharacterEnum have been selected, true if only one is selected
     */
    public boolean bothArentSelected() {
        boolean answer = true;

        if (RenderCharacterSelectionScreen.getInstance().characterSelected && RenderCharacterSelectionScreen.getInstance().opponentSelected) {
            answer = false;
        }

        return answer;
    }

    /**
     * Animates captions
     */
    public void capAnim() {
        opacity = 0.0f;
    }

    /**
     * Checks if within number of CharacterEnum
     */
    public boolean isHoveredOverStage() {
        boolean ans = false;
        int computedStage = (row * columns) + column;
        if (computedStage <= numberOfStages) {
            ans = true;
            hoveredStage = stageLookup.get(computedStage);
        }
        return ans;
    }

    public Stage getHoveredStage() {
        return hoveredStage;
    }

    /**
     * Horizontal index
     *
     * @return columnIndex
     */
    public int getHindex() {
        return column;
    }

    /**
     * Set horizontal index
     */
    public void setRow(int value) {
        column = value;
    }

    /**
     * Vertical index
     *
     * @return rowIndex
     */
    public int getVindex() {
        return row;
    }

    /**
     * Set vertical index
     */
    public void setColumn(int value) {
        row = value;
    }

    /**
     * To make sure the caption is animated once,
     * this method checks if the selected caption has changed
     *
     * @param x
     * @param y
     */
    private void animateCaption(int x, int y) {
        int tmpx = x;
        int tmpy = y;

        if (tmpx == storedX && tmpy == storedY) //same vals, do nothing
        {
        } else {
            storedX = tmpx;
            storedY = tmpy;
            RenderCharacterSelectionScreen.getInstance().animateCaption();
        }
    }


    /**
     * Move up
     */
    public void onUp() {
        if (row > 0)
            row -= 1;
        else
            row = rows;
        capAnim();
    }

    /**
     * Move down
     */
    public void onDown() {
        if (row < rows - 1)
            row = row + 1;
        else
            row = 0;
        capAnim();
    }

    /**
     * Move right
     */
    public void onRight() {
        if (column < columns - 1)
            column += 1;
        else
            column = 0;
        capAnim();
    }

    /**
     * Move left
     */
    public void onLeft() {
        if (column > 0)
            column -= 1;
        else
            column = columns - 1;
        capAnim();
    }


    /**
     * Gets the number of columns in the characterEnum select screen
     *
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharHSpacer() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharVSpacer() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getStartX() {
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getStartY() {
        return firstLine;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                if (validHover)
                    onAccept();
                break;
            case MIDDLE:
                break;
            case SECONDARY:
                break;
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        switch (keyCode) {
            case ENTER:
                onAccept();
                break;
            case ESCAPE:
            case BACK_SPACE:
                onBackCancel();
                break;
            case UP:
            case W:
                onUp();
                break;
            case DOWN:
            case S:
                onDown();
                break;
            case LEFT:
            case A:
                onLeft();
                break;
            case RIGHT:
            case D:
                onRight();
                break;
        }
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        if (ScndGenLegends.getInstance().getSubMode() != SubMode.LAN_CLIENT) {
            int topY = getStartY();
            int topX = getStartX();
            int columns = getColumns();
            int vSpacer = getCharHSpacer();
            int hSpacer = getCharVSpacer();
            int rows = getRows();
            if (mouseEvent.getX() > topX && mouseEvent.getX() < (topX + (hSpacer * columns)) && (mouseEvent.getY() > topY) && (mouseEvent.getY() < topY + (vSpacer * (rows)))) {
                int row = Math.round(Math.round((mouseEvent.getY() - topY) / vSpacer));
                int column = Math.round(Math.round((mouseEvent.getX() - topX) / hSpacer));
                System.out.printf("Row %s :: Column %s\n", row, column);
                setRow(row);
                setColumn(column);
                animateCaption(row, column);
                validHover = true;
            } else {
                RenderCharacterSelectionScreen.getInstance().setHindex(99);
                RenderCharacterSelectionScreen.getInstance().setVindex(99);
                validHover = false;
            }
        }
    }

    public void onAccept() {
        selectStage(getHoveredStage());
    }

    public void onBackCancel() {
        //ScndGenLegends.getInstance().loadMode(Mode.CHAR_SELECT_SCREEN);
    }
}
