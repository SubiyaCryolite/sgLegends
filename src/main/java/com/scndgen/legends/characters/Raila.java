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
package com.scndgen.legends.characters;

import com.scndgen.legends.Colors;
import com.scndgen.legends.arefactored.mode.StandardGameplay;
import com.scndgen.legends.arefactored.render.RenderStandardGameplay;
import com.scndgen.legends.engine.JenesisCharacter;
import com.scndgen.legends.enums.CharacterEnum;

/**
 * @author ndana
 */
public class Raila extends JenesisCharacter {

    public Raila() {
        //strings
        descSmall = "Raila - a fighter specialised in celestia combat";
        name = "Raila";
        characterEnum = CharacterEnum.RAILA;
        //ints
        points = 2500;
        life = 25600;
        hitPoints = 60;
        limit = new int[]{0, 0, 0, 0, 0};
        //string arrays
        bragRights = new String[]{"Let's do this", "I won't go easy on you bro", "Let's do this cuz", "This'll be fun", "You scum, prepare to be owned", "You're powers are intersting. Lets see how you do against my speed", "You're definately the better looking twin, though, you won't look so good afterwards", "NovaAdam, the stiuff of legend, show me your power", "An awakened being? Lets do this!!!!", "One of te original Saints, show me your power!!", "Genius  vs Genius , this'll be fun", "What in the world?"};
        physical = new String[]{"Strike", "Chant", "Blue Embrace", "Blue Blitz"};
        celestia = new String[]{"Nova Storm", "Eternal Flame", "Frozen Breeze", "Dark Cloud"};
        status = new String[]{"Heal Plus", "Heal EX", "Energy Juice", "Weaken Opponent"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        weakness = new float[8];
        weakness[0] = 0.66f;    //light
        weakness[1] = 0.66f;    //dark
        weakness[2] = 0.66f;    //nonElemental (lighting , wind)
        weakness[3] = 0.90f;    //earth ( wood/ rock)
        weakness[4] = 0.80f;    //water
        weakness[5] = 0.30f;    //ice
        weakness[6] = 0.70f;    //physical
        weakness[7] = 0.30f;    //fire
        //doubles
        actionRecoverRate = 2.5f;//2.5;
        hpRecovRate = 0.000150f;
    }

    @Override
    public void attack(String attack, int forWho) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 83;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 82;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                //StandardGameplay.specialEffect(2,true);
                break;
            case "03":
                attackStr = physical[2];
                damage = 82;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                //StandardGameplay.specialEffect(2,true);
                break;
            case "04":
                attackStr = physical[3];
                damage = 81;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 88;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 85;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 85;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 83;
                RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                sound3.play();
                attackStr = status[0];
                damage = 72;
                StandardGameplay.setStatIndex(1);
                if (forWho == 2) {
                    RenderStandardGameplay.getInstance().updateLife(damage);
                    StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderStandardGameplay.getInstance().updateOppLife(damage);
                    StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "10":
                sound3.play();
                attackStr = status[1];
                damage = 79;
                StandardGameplay.setStatIndex(1);
                if (forWho == 2) {
                    RenderStandardGameplay.getInstance().updateLife(damage);
                    StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderStandardGameplay.getInstance().updateOppLife(damage);
                    StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "11":
                limit[1] = limit[1] + 1;
                if (limit[1] <= 4) {
                    sound3.play();
                    attackStr = status[2];
                    StandardGameplay.setStatIndex(3);
                    if (forWho == 2) {
                        StandardGameplay.setStatusPic('c', "STRENGTHENED", Colors.getColor("blue"));
                        RenderStandardGameplay.getInstance().alterDamageCounter('o', +1);
                    } else {
                        StandardGameplay.setStatusPic('o', "STRENGTHENED", Colors.getColor("blue"));
                        RenderStandardGameplay.getInstance().alterDamageCounter('c', +1);
                    }
                    RenderStandardGameplay.getInstance().showBattleMessage(name + " strengthened himself!!!");
                } else {
                    RenderStandardGameplay.getInstance().showBattleMessage(name + " cant use his attack!!!!");
                }
                break;
            case "12":
                limit[0] = limit[0] + 1;
                if (limit[0] <= 4) {
                    sound3.play();
                    attackStr = status[3];
                    StandardGameplay.setStatIndex(4);
                    if (forWho == 2) {
                        //as a player(2) yo8u attack the opponent(1)
                        StandardGameplay.setStatusPic('o', "WEAKENED!!!", Colors.getColor("red"));
                        RenderStandardGameplay.getInstance().alterDamageCounter('c', -1);
                    } else {
                        StandardGameplay.setStatusPic('c', "WEAKENED!!!", Colors.getColor("red"));
                        RenderStandardGameplay.getInstance().alterDamageCounter('o', -1);
                    }
                    RenderStandardGameplay.getInstance().showBattleMessage(name + " weakened his opponent!!!");
                } else {
                    RenderStandardGameplay.getInstance().showBattleMessage(name + " cant use his attack!!!!");
                }
                break;
        }
    }
}