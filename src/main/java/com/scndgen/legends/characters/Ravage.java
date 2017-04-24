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
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.threads.AudioPlayback;

import static com.scndgen.legends.enums.CharacterEnum.RAVAGE;

/**
 * @author ndana
 */
public class Ravage extends Character {

    public Ravage() {
        descSmall = "Ravage - a fighter specialised in brute force via the Earth element";
        name = "Ravage";
        characterEnum = RAVAGE;
        life = 32000;
        limit = new int[]{0, 0, 0, 0, 0};
        bragRights = new String[]{"Pathetic weakling", "Prepare to be owned", "I owe you one....a beating that is", "Lowly Saint, be gone!!!", "Let's do this", "Lets see if you're strong enough", "Lets do this bro", "I won't hold onBackCancel NovaAdam", "Goodie, no holding onBackCancel, HA HA HA!!!", "I'll destroy you!!", "Don't get all high and mighty brat!!!", "?????"};
        physical = new String[]{"Strike", "Impale", "Stone Summon", "Deadly Snare"};
        celestia = new String[]{"Siezmic Slam", "Fist-Full", "Quake", "Boulder Rush"};
        status = new String[]{"Heal Plus", "Heal EX", "Energy Juice", "Weaken Opponent"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        atbRecoveryRate = 1.70f;//1.5;
        sound3 = new AudioPlayback(AudioPlayback.itemSound1(), false);
    }

    @Override
    public void attack(String attack, CharacterState forWho) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 108;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 102;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                //Gameplay.specialEffect(2,true);
                break;
            case "03":
                attackStr = physical[2];
                damage = 103;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "04":
                attackStr = physical[3];
                damage = 103;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 101;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 107;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 103;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 102;
                RenderGameplay.getInstance().lifePhysUpdateSimple(forWho, damage, name);
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                sound3.play();
                attackStr = status[0];
                damage = 82;
                RenderGameplay.getInstance().setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    RenderGameplay.getInstance().updatePlayerLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderGameplay.getInstance().updateOpponentLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "10":
                sound3.play();
                attackStr = status[1];
                damage = 99;
                RenderGameplay.getInstance().setStatIndex(1);
                if (forWho == CharacterState.OPPONENT) {
                    RenderGameplay.getInstance().updatePlayerLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "+" + damage + "0 HP", Colors.getColor("green"));
                } else {
                    RenderGameplay.getInstance().updateOpponentLife(damage);
                    RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "+" + damage + "0 HP", Colors.getColor("green"));
                }
                RenderGameplay.getInstance().showBattleMessage(name + " used " + attackStr);
                break;
            case "11":
                limit[1] = limit[1] + 1;
                if (limit[1] <= 4) {
                    sound3.play();
                    attackStr = status[2];
                    RenderGameplay.getInstance().setStatIndex(3);
                    if (forWho == CharacterState.OPPONENT) {
                        RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "STRENGTHENED", Colors.getColor("blue"));
                        RenderGameplay.getInstance().alterDamageCounter(CharacterState.OPPONENT, +1);
                    } else {
                        RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "STRENGTHENED", Colors.getColor("blue"));
                        RenderGameplay.getInstance().alterDamageCounter(CharacterState.CHARACTER, +1);
                    }
                    RenderGameplay.getInstance().showBattleMessage(name + " strengthened himself!!!");
                } else {
                    // disble this move
                    RenderGameplay.getInstance().showBattleMessage(name + " cant use his attack!!!!");
                }
                break;
            case "12":
                limit[0] = limit[0] + 1;
                if (limit[0] <= 4) {
                    sound3.play();
                    attackStr = status[3];
                    RenderGameplay.getInstance().setStatIndex(4);
                    if (forWho == CharacterState.OPPONENT) {
                        //as a player(2) yo8u attack the opponent(1)
                        RenderGameplay.getInstance().setStatusPic(CharacterState.OPPONENT, "WEAKENED!!!", Colors.getColor("red"));
                        RenderGameplay.getInstance().alterDamageCounter(CharacterState.CHARACTER, -1);
                    } else {
                        RenderGameplay.getInstance().setStatusPic(CharacterState.CHARACTER, "WEAKENED!!!", Colors.getColor("red"));
                        RenderGameplay.getInstance().alterDamageCounter(CharacterState.OPPONENT, -1);
                    }
                    RenderGameplay.getInstance().showBattleMessage(name + " weakened his opponent!!!");
                } else {
                    // disble this move
                    RenderGameplay.getInstance().showBattleMessage(name + " cant use his attack!!!!");
                }
                break;
        }
    }
}
