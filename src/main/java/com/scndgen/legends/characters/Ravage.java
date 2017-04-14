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
import com.scndgen.legends.threads.ThreadMP3;

/**
 * @author ndana
 */
public class Ravage extends JenesisCharacter {

    public Ravage() {
        //strings
        descSmall = "Ravage - a fighter specialised in brute force via the Earth element";
        name = "Ravage";
        characterEnum = CharacterEnum.RAVAGE;
        //ints
        life = 32000;
        hitPoints = 60;
        limit = new int[]{0, 0, 0, 0, 0};
        bragRights = new String[]{"Pathetic weakling", "Prepare to be owned", "I owe you one....a beating that is", "Lowly Saint, be gone!!!", "Let's do this", "Lets see if you're strong enough", "Lets do this bro", "I won't hold back NovaAdam", "Goodie, no holding back, HA HA HA!!!", "I'll destroy you!!", "Don't get all high and mighty brat!!!", "?????"};
        physical = new String[]{"Strike", "Impale", "Stone Summon", "Deadly Snare"};
        celestia = new String[]{"Siezmic Slam", "Fist-Full", "Quake", "Boulder Rush"};
        status = new String[]{"Heal Plus", "Heal EX", "Energy Juice", "Weaken Opponent"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        //doubles
        actionRecoverRate = 1.70f;//1.5;
        hpRecovRate = 0.000150f;
        sound3 = new ThreadMP3(ThreadMP3.itemSound1(), false);
    }

    @Override
    public void attack(String attack, int forWho) {
        if (attack.equalsIgnoreCase("01")) {
            attackStr = physical[0];
            damage = 108;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("02")) {
            attackStr = physical[1];
            damage = 102;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
            //StandardGameplay.specialEffect(2,true);
        }

        if (attack.equalsIgnoreCase("03")) {
            attackStr = physical[2];
            damage = 103;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("04")) {
            attackStr = physical[3];
            damage = 103;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("05")) {
            attackStr = celestia[0];
            damage = 101;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("06")) {
            attackStr = celestia[1];
            damage = 107;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("07")) {
            attackStr = celestia[2];
            damage = 103;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("08")) {
            attackStr = celestia[3];
            damage = 102;
            RenderStandardGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("09")) {
            sound3.play();
            attackStr = status[0];
            damage = 82;
            StandardGameplay.setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updateLife(damage);
                StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOppLife(damage);
                StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("10")) {
            sound3.play();
            attackStr = status[1];
            damage = 99;
            StandardGameplay.setStatIndex(1);
            if (forWho == 2) {
                RenderStandardGameplay.getInstance().updateLife(damage);
                StandardGameplay.setStatusPic('c', "+" + damage + "0 HP", Colors.getColor("green"));
            } else {
                RenderStandardGameplay.getInstance().updateOppLife(damage);
                StandardGameplay.setStatusPic('o', "+" + damage + "0 HP", Colors.getColor("green"));
            }
            RenderStandardGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
        }

        if (attack.equalsIgnoreCase("11")) {

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
                // disble this move
                RenderStandardGameplay.getInstance().showBattleMessage(name + " cant use his attack!!!!");
            }
        }

        if (attack.equalsIgnoreCase("12")) {

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
                // disble this move
                RenderStandardGameplay.getInstance().showBattleMessage(name + " cant use his attack!!!!");
            }
        }

        //dummy, do nothing
        if (attack.equalsIgnoreCase("99")) {
        }
    }
}