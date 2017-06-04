/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([http://www.scndgen.com]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

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

import com.scndgen.legends.enums.Player;
import com.scndgen.legends.mode.GamePlay;

import static com.scndgen.legends.enums.CharacterEnum.JONAH;

/**
 * @author ndana
 */
public class Jonah extends Character {

    public Jonah() {
        descSmall = "Jonah - a fighter specialised in Force combat";
        name = "Jonah";
        characterEnum = JONAH;
        bragRights = new String[]{"I don't like fighting weakling, this'll be over soon", "Do achievements a favour.....die", "I'm not my brother, I'll destroy you", "You're no match for achievements", "Time for some tough love brother", "Sorry Ade, it can't be helped", "Let's do this", "NovaAdam, prove you're more than just talk", "So this is your true form.....I'LL DESTROY IT!!!!", "Out of my way!!! I've got stronger opponents to destroy", "You talk big for a tiny girl", "Terrible, just terrible"};
        physical = new String[]{"One!", "Two!!", "Ou!!!", "Jaw Breaker"};
        celestia = new String[]{"Force Rush", "Force Beam", "Force Slice", "Force Unleashed"};
        status = new String[]{"Heal Plus", "Heal EX", "Pain Killer", "Wound Spray"};
        behaviours1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        behaviours2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11};
        behaviours3 = new int[]{0, 1, 7, 8, 10, 11};
        behaviours4 = new int[]{0, 1, 9, 12, 10, 11};
        behaviours5 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        life = 29760;
        limit = new int[]{0, 0, 0, 0, 0};
        atbRecoveryRate = 2.08f;//2.10;
    }

    @Override
    public void attack(String attack, Player player, GamePlay gamePlay) {
        switch (attack) {
            case "01":
                attackStr = physical[0];
                damage = 95;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "02":
                attackStr = physical[1];
                damage = 95;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "03":
                attackStr = physical[2];
                damage = 96;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "04":
                attackStr = physical[3];
                damage = 98;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "05":
                attackStr = celestia[0];
                damage = 94;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "06":
                attackStr = celestia[1];
                damage = 96;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "07":
                attackStr = celestia[2];
                damage = 101;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "08":
                attackStr = celestia[3];
                damage = 98;
                gamePlay.lifePhysUpdateSimple(player, damage);
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "09":
                play();
                attackStr = status[0];
                damage = 73;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "10":
                play();
                attackStr = status[1];
                damage = 75;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "11":
                play();
                attackStr = status[2];
                damage = 79;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
            case "12":
                play();
                attackStr = status[3];
                damage = 71;
                gamePlay.setStatIndex(1);
                if (player == Player.OPPONENT) {
                    gamePlay.updatePlayerLife(damage);
                    gamePlay.setStatusPic(Player.CHARACTER);
                } else {
                    gamePlay.updateOpponentLife(damage);
                    gamePlay.setStatusPic(Player.OPPONENT);
                }
                gamePlay.showBattleMessage(name + " used " + attackStr);
                break;
        }
    }
}
