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
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;

public class ExecuterMovesCharOnline implements Runnable {

    public static int taskComplete;
    public static int taskRun = 0;
    public static boolean isRunning = false;
    private static Thread timer;
    private int i1, i2, i3, i4;
    private char well;

    public ExecuterMovesCharOnline(int m1, int m2, int m3, int m4, char type) {
        well = type;
        i1 = m1;
        i2 = m2;
        i3 = m3;
        i4 = m4;

        timer = new Thread(this);
        timer.start();
    }

    public static void pause() {
        timer.suspend();
    }

    public static void resume() {
        timer.resume();
    }

    public void run() {
        //normal attack
        if (well == 'n') {

            RenderStandardGameplay.getInstance().comboCounter = 0;
            //clear active combos

            RenderStandardGameplay.getInstance().setSprites('c', 9, 11);
            RenderStandardGameplay.getInstance().setSprites('o', 9, 11);
            //RenderStandardGameplay.getInstance().DisableMenus(); disable issueing of more attacksCombatMage during execution
            // each Mattack will check if they are in the battle que.... if they are they execute

            executingTheCommands();
            RenderStandardGameplay.getInstance().getGameInstance().setRecoveryUnitsChar(0);
        }

        //limit break
        if (well == 'l') {
            RenderStandardGameplay.getInstance().Clash(1, 'c');
        }
    }

    private void executingTheCommands() {
        int[] moveBuff = {i1, i2, i3, i4};

        for (int o = 0; o < 4; o++) {
            LoginScreen.getInstance().getMenu().getMain().getAttacksChar().CharacterOverlayEnabled();
            LoginScreen.getInstance().getMenu().getMain().getAttacksChar().attack(moveBuff[o], 2, 'c', 'o');
            RenderStandardGameplay.getInstance().shakeOppCharLB();
            RenderStandardGameplay.getInstance().AnimatePhyAttax('c');
            LoginScreen.getInstance().getMenu().getMain().getAttacksChar().CharacterOverlayDisabled();
        }
    }
}