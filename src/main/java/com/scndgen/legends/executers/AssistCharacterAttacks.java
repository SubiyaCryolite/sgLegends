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
package com.scndgen.legends.executers;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.MainWindow;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AssistCharacterAttacks implements Runnable {

    public static int taskRun = 0, range;
    private Thread timer;
    private int[] aiMoves;
    private int whoToAttack = 4;

    public AssistCharacterAttacks() {
        timer = new Thread(this);
        timer.setName("Opponent attacking thread");
    }

    public void attack() {
        if (timer.isAlive()) {
            timer.resume();
        } else {
            timer.start();
        }
    }

    @Override
    public void run() {
        do {
            try {
                int time = LoginScreen.getInstance().difficultyDyn;
                Thread.sleep((int) (time + (Math.random() * time)));
            } catch (InterruptedException ex) {
                Logger.getLogger(AssistCharacterAttacks.class.getName()).log(Level.SEVERE, null, ex);
            }
            executingTheCommandsAI();
            RenderGameplay.getInstance().getGameInstance().setRecoveryUnitsChar2(0);
            RenderGameplay.getInstance().getGameInstance().aiRunning3 = false;
            timer.suspend();
        } while (1 != 0);
    }

    private void executingTheCommandsAI() {
        aiMoves = RenderCharacterSelectionScreen.getInstance().getAISlot3();
        range = aiMoves.length - 1;
        if (RenderGameplay.getInstance().getGameInstance().isGameOver == false) {

            int randomNumber = (int) (Math.random() * 12);
            if (randomNumber <= 6) {
                if (RenderGameplay.getInstance().getPercent2a() >= 0) {
                    whoToAttack = 4;
                } // normally CPU player 1 attacks CPU opponent 2
                else {
                    whoToAttack = 2;
                }
            } else if (randomNumber >= 7) {
                if (RenderGameplay.getInstance().getPercent2() >= 0) {
                    whoToAttack = 2;
                } //attack CPU opponent 1
                else {
                    whoToAttack = 4;
                }
            }
            for (int o = 0; o < 4; o++) {
                MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
                //fix story scene bug
                if (RenderGameplay.getInstance().getGameInstance().storySequence == false) {
                    MainWindow.getInstance().getAttacksOpp2().attack(aiMoves[Integer.parseInt("" + Math.round(Math.random() * range))], whoToAttack, 'a', 'b');
                    RenderGameplay.getInstance().shakeCharLB();
                    RenderGameplay.getInstance().AnimatePhyAttax('a');
                }
                MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
            }
        }
    }

    public void pause() {
        timer.suspend();
    }

    public void resume() {
        timer.resume();
    }
}
