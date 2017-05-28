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
package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.render.RenderGamePlay;
import com.scndgen.legends.state.GameState;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Mode;

import javax.swing.*;

public abstract class StoryMenu extends Mode {

    protected int charYcap = 0, charXcap = 0, row = 1, x = 0, y = 0, column = 0, vSpacer = 52, hSpacer = 92, commonXCoord = 299, commonYCoord = 105;
    protected final int columns = 3;
    protected final int numberOfScenes = StoryMode.getInstance().max;
    protected final int rows = numberOfScenes / columns;
    protected final int rowsCiel = Math.round(Math.round(Math.ceil(numberOfScenes / (double) columns)));
    protected final int columnsCiel = numberOfScenes % columns;
    protected int oldId = -1;
    protected boolean[] unlockedStage;
    protected boolean loadingNow;
    protected int currentScene = GameState.getInstance().getLogin().getLastStoryScene();
    protected int storedX = 99, storedY = 99;

    public void newInstance() {
        loadAssets = true;
        StoryMode.getInstance().newInstance();
        opacity = 1.0f;
    }

    /**
     * Animates captions
     */
    public void animateCaption() {
        opacity = 0.0f;
    }

    /**
     * Find out the lastStoryScene the player is on
     */
    public void resetCurrentStage() {
        currentScene = GameState.getInstance().getLogin().getLastStoryScene();
    }

    /**
     * When in playStory currentScene, we go to the current level
     *
     * @return current level
     */
    public int getStage() {
        return currentScene;
    }

    protected void showstoryName(int id) {
        if (id != oldId) {
            primaryNotice("Scene " + (id + 1));
            oldId = id;
        }
    }

    /**
     * Are there more stages?????
     *
     * @return
     */
    public boolean moreStages() {
        boolean answer = false;
        resetCurrentStage();

        //check if more stages
        if (currentScene < StoryMode.getInstance().max) {
            answer = true;
        } //if won last 'final' match
        else if (RenderGamePlay.getInstance().hasWon()) {
            //incrementMode();
            //go onBackCancel to user difficulty
            GameState.getInstance().getLogin().setDifficultyDynamic(GameState.getInstance().getLogin().getDifficulty());
            AudioPlayback victorySound = new AudioPlayback(AudioConstants.soundGameOver(), AudioType.MUSIC, false);
            victorySound.play();
            JOptionPane.showMessageDialog(null, Language.getInstance().get(115), "Sweetness!!!", JOptionPane.INFORMATION_MESSAGE);
            answer = false;
        }
        return answer;
    }

    protected boolean validIndex(int hoveredStoryIndex) {
        return unlockedStage[hoveredStoryIndex];
    }
}
