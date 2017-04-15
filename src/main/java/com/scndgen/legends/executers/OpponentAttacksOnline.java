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

import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.windows.MainWindow;

public class OpponentAttacksOnline implements Runnable {
    private Thread timer;
    private int command1, command2, command3, command4;
    private char executionType;

    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public OpponentAttacksOnline(int command1, int command2, int command3, int command4, char type) {
        this.executionType = type;
        this.command1 = command1;
        this.command2 = command2;
        this.command3 = command3;
        this.command4 = command4;
        timer = new Thread(this);
        timer.start();
    }

    public void pause() {
        timer.suspend();
    }

    public void resume() {
        timer.resume();
    }

    @Override
    public void run() {
        try {
            if (executionType == 'n') {
                RenderGameplay.getInstance().setSprites('c', 9, 11);
                RenderGameplay.getInstance().setSprites('o', 9, 11);
                executingTheCommandsAI(command1, command2, command3, command4);
                RenderGameplay.getInstance().getGameInstance().setRecoveryUnitsOpp(0);
            } else if (executionType == 'l') {//limit break
                RenderGameplay.getInstance().clash(1, 'c');
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void executingTheCommandsAI(int command1, int command2, int command3, int command4) {
        try {
            int[] commands = {command1, command2, command3, command4};
            for (int index = 0; index < commands.length; index++) {
                MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                MainWindow.getInstance().getAttackOpponent().attack(commands[index], 1, 'o', 'c');
                RenderGameplay.getInstance().shakeCharLB();
                RenderGameplay.getInstance().AnimatePhyAttax('o');
                MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}