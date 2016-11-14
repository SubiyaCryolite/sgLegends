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
package com.scnd_genesis.executers;

import com.scnd_genesis.LoginScreen;

public class ExecuterMovesOppOnline implements Runnable {

    public static int taskComplete;
    public static int taskRun = 0;
    public static char feeCol;
    public static boolean isRunning = false;
    private static Thread timer;
    private static int f1, f2, f3, f4;
    private static int randomMattackInt;
    private char well;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public ExecuterMovesOppOnline(int mel1, int mel2, int mel3, int mel4, char type) {
        well = type;
        f1 = mel1;
        f2 = mel2;
        f3 = mel3;
        f4 = mel4;

        timer = new Thread(this);
        timer.start();
    }

    public static void pause() {
        timer.suspend();
    }

    public static void resume() {
        timer.resume();
    }

    @Override
    public void run() {
        try {
            //normal attack
            if (well == 'n') {
                int interator = 0;
                do {
                    LoginScreen.getLoginScreen().getMenu().getMain().getGame().setSprites('c', 9, 11);
                    LoginScreen.getLoginScreen().getMenu().getMain().getGame().setSprites('o', 9, 11);

                    executingTheCommandsAI(f1, f2, f3, f4);

                    LoginScreen.getLoginScreen().getMenu().getMain().getGame().getGameInstance().setRecoveryUnitsOpp(0);
                    interator = interator + 1;
                } while (interator != 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executingTheCommandsAI(int m1, int m2, int m3, int m4) {
        try {
            int[] aiMoves = {m1, m2, m3, m4};
            System.out.println("AI Opponent>>>>>>>>");

            for (int o = 0; o < 4; o++) {
                LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
                LoginScreen.getLoginScreen().getMenu().getMain().getAttacksOpp().attack(aiMoves[o], 1, 'o', 'c');
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().shakeCharLB();
                LoginScreen.getLoginScreen().getMenu().getMain().getGame().AnimatePhyAttax('o');
                LoginScreen.getLoginScreen().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
