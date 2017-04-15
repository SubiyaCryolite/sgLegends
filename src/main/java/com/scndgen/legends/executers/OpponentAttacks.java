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
import com.scndgen.legends.threads.ThreadGameInstance;
import com.scndgen.legends.windows.MainWindow;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OpponentAttacks implements Runnable {

    public int taskRun = 0, range;
    public char feeCol;
    public boolean isRunning = false;
    private Thread timer;
    private int[] aiMoves;

    public OpponentAttacks() {
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
                Thread.sleep(LoginScreen.getInstance().difficultyDyn);
            } catch (InterruptedException ex) {
                Logger.getLogger(OpponentAttacks.class.getName()).log(Level.SEVERE, null, ex);
            }

            executingTheCommandsAI();

            RenderGameplay.getInstance().getGameInstance().setRecoveryUnitsOpp(0);
            RenderGameplay.getInstance().getGameInstance().aiRunning = false;

            timer.suspend();
        } while (1 != 0);
    }

    private void executingTheCommandsAI() {
        aiMoves = RenderCharacterSelectionScreen.getInstance().getAISlot();
        range = aiMoves.length - 1;

        if (ThreadGameInstance.isGameOver == false) {
            for (int o = 0; o < ((LoginScreen.difficultyBase - LoginScreen.getInstance().difficultyDyn) / LoginScreen.difficultyScale); o++) {
                //fix story scene bug
                if (ThreadGameInstance.storySequence == false && ThreadGameInstance.isGameOver == false) {
                    MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                    MainWindow.getInstance().getAttackOpponent().attack(aiMoves[Integer.parseInt("" + Math.round(Math.random() * range))], 1, 'o', 'c');
                    RenderGameplay.getInstance().shakeCharLB();
                    RenderGameplay.getInstance().AnimatePhyAttax('o');
                    MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
                }
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