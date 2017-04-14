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
import com.scndgen.legends.menus.CanvasCharSelect;
import com.scndgen.legends.threads.ThreadGameInstance;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecuterMovesOpp2 implements Runnable {

    public static int taskComplete;
    public static int taskRun = 0, range;
    public static char feeCol;
    public static boolean isRunning = false;
    private static Thread timer;
    private int[] aiMoves;
    private int whoToAttack = 3;

    public ExecuterMovesOpp2() {
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
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().setSprites('c', 9, 11);
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().setSprites('o', 9, 11);
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().setSprites('b', 9, 11);

            try {
                int time = LoginScreen.getLoginScreen().difficultyDyn;
                Thread.sleep((int) (time + (Math.random() * time)));
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecuterMovesOpp2.class.getName()).log(Level.SEVERE, null, ex);
            }

            executingTheCommandsAI();

            LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsOpp2(0);
            LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().aiRunning2 = false;

            timer.suspend();
        } while (1 != 0);
    }

    private void executingTheCommandsAI() {
        aiMoves = CanvasCharSelect.getAISlot2();
        range = aiMoves.length - 1;

        int randomNumber = (int) (Math.random() * 12);
        if (randomNumber >= 7) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent3a >= 0) {
                whoToAttack = 3;
            } else {
                whoToAttack = 1;
            }
        } else if (randomNumber <= 6) {
            if (LoginScreen.getLoginScreen().getMenu().getMain().getGame().perCent >= 0) {
                whoToAttack = 1;
            } else {
                whoToAttack = 3;
            }
        }

        if (ThreadGameInstance.isGameOver == false) {
            for (int o = 0; o < ((LoginScreen.difficultyBase - LoginScreen.getLoginScreen().difficultyDyn) / LoginScreen.difficultyScale); o++) {
                //fix story mode bug
                if (ThreadGameInstance.story == false && ThreadGameInstance.isGameOver == false) {
                    LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
                    LoginScreen.getLoginScreen().getMenu().getMain().getAttacksOpp().attack(aiMoves[Integer.parseInt("" + Math.round(Math.random() * range))], 1, 'o', 'c');
                    LoginScreen.getLoginScreen().getMenu().getMain().getGame().shakeCharLB();
                    LoginScreen.getLoginScreen().getMenu().getMain().getGame().AnimatePhyAttax('o');
                    LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
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
