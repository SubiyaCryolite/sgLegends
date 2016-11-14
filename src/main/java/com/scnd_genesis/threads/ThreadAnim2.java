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
package com.scnd_genesis.threads;

import com.scnd_genesis.drawing.DrawGame;
import com.scnd_genesis.windows.WindowOptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ndana
 */
public class ThreadAnim2 implements Runnable {

    private static Thread t;

    public ThreadAnim2() {
        if (t != null) {
            t.resume();
        } else {
            t = new Thread(this);
            t.setPriority(1);
            t.setName("Animator thread 2 - Foreground");
            t.start();
        }
    }

    @Override
    public void run() {
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            do {
                //if rotating
                if (DrawGame.animDirection.equalsIgnoreCase("rot")) {
                    try {
                        //loop1
                        for (int o = 0; o <= DrawGame.animLoops; o++) {
                            DrawGame.fgx = DrawGame.fgx + DrawGame.fgxInc;
                            DrawGame.fgy = DrawGame.fgy + DrawGame.fgyInc;
                            t.sleep(DrawGame.delay);
                        }

                        //loop2
                        for (int o = 0; o <= DrawGame.animLoops; o++) {
                            DrawGame.fgx = DrawGame.fgx - DrawGame.fgxInc;
                            DrawGame.fgy = DrawGame.fgy + DrawGame.fgyInc;
                            t.sleep(DrawGame.delay);
                        }

                        //loop3
                        for (int o = 0; o <= DrawGame.animLoops; o++) {
                            DrawGame.fgx = DrawGame.fgx - DrawGame.fgxInc;
                            DrawGame.fgy = DrawGame.fgy - DrawGame.fgyInc;
                            t.sleep(DrawGame.delay);
                        }

                        //loop4
                        for (int o = 0; o <= DrawGame.animLoops; o++) {
                            DrawGame.fgx = DrawGame.fgx + DrawGame.fgxInc;
                            DrawGame.fgy = DrawGame.fgy - DrawGame.fgyInc;
                            t.sleep(DrawGame.delay);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DrawGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } //if NOT rotating
                else {
                    DrawGame.fgx = 0;
                    DrawGame.fgy = 0;

                    try {
                        for (int o = 0; o <= DrawGame.animLoops; o++) {
                            if (DrawGame.animDirection.equalsIgnoreCase("both")) {
                                DrawGame.fgx = DrawGame.fgx + DrawGame.fgxInc;
                                DrawGame.fgy = DrawGame.fgy + DrawGame.fgyInc;
                            } else if (DrawGame.animDirection.equalsIgnoreCase("horiz")) {
                                DrawGame.fgx = DrawGame.fgx + DrawGame.fgxInc;
                            } else if (DrawGame.animDirection.equalsIgnoreCase("vert")) {
                                DrawGame.fgy = DrawGame.fgy + DrawGame.fgyInc;
                            }

                            t.sleep(DrawGame.delay);
                        }

                        for (int o = 0; o <= DrawGame.animLoops; o++) {
                            if (DrawGame.animDirection.equalsIgnoreCase("both")) {
                                DrawGame.fgx = DrawGame.fgx - DrawGame.fgxInc;
                                DrawGame.fgy = DrawGame.fgy - DrawGame.fgyInc;
                            } else if (DrawGame.animDirection.equalsIgnoreCase("horiz")) {
                                DrawGame.fgx = DrawGame.fgx - DrawGame.fgxInc;
                            } else if (DrawGame.animDirection.equalsIgnoreCase("vert")) {
                                DrawGame.fgy = DrawGame.fgy - DrawGame.fgyInc;
                            }

                            t.sleep(DrawGame.delay);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DrawGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (1 != 0);
        }
    }

    public void stop() {
        t = null;
        //t.destroy();
    }

    public void pauseThread() {
        t.suspend();
    }

    public void resumeThread() {
        t.resume();
    }
}
