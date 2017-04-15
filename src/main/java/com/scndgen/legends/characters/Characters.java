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


import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;

/**
 * This class should be self explainatory -_-
 *
 * @author Ifunga Ndana
 */
public class Characters {

    public static String[] moveMusicOpp = new String[8];
    public static String[] moveMusicChar = new String[8];
    public static int[] pointsArr = new int[12];
    public static String[] typeArray = new String[4];
    private static float activityRecoveryRateChar2, activityRecoverRateChar, activityRecoveryRateOpp, activityRecoveryRateOpp2;
    private static float healthRecoveryRateChar2, healthRecoveryRateChar, healthRecoveryRateOpp, healthRecoveryRateOpp2;
    //AIRCON 12 GLOWING HOT GIMP 2.6.8
    private static int damageMultiplierOpp, damageMultiplierChar, minCharlife, minOppLife2, currCharLife3, minOppLife, currCharLife, currOppLife2, currOppLife, points, maxPoints;
    private String characterName, nameOpp, assistCharacterName, opponentAssistantName;
    private com.scndgen.legends.characters.Character character, assistCharacter, opponent, opponentAssistant;

    public static float getCharRecoverySpeed() {
        return activityRecoverRateChar;
    }

    public static float getOppRecoverySpeed() {
        return activityRecoveryRateOpp;
    }

    public static float getOppRecoverySpeed2() {
        return activityRecoveryRateOpp2;
    }

    public static float getCharRecoverySpeed2() {
        return activityRecoveryRateChar2;
    }

    public static float getCharRecoveryRate() {
        return healthRecoveryRateChar;
    }

    public static float getOppRecoveryRate() {
        return healthRecoveryRateOpp;
    }

    public static void incrementSpeedRate(char who, float thisMuch) {
        if (who == 'c') {
            activityRecoverRateChar = activityRecoverRateChar + thisMuch;
        }

        if (who == 'o') {
            activityRecoveryRateOpp = activityRecoveryRateOpp + thisMuch;
        }
    }

    //called when character damaged
    public static void setCurrLifeChar(int life) {
        currCharLife = life;
        //percentages
        if (life < minCharlife) {
            minCharlife = life;
            //System.out.println("min char life "+minCharlife);
        }
    }

    //called when opp damaged
    public static void setCurrLifeOpp(int life) {
        currOppLife = life;

        //percentages
        if (life < minOppLife) {
            minOppLife = life;
        }
    }

    //called when opp damaged
    public static void setCurrLifeOpp2(int life) {
        currOppLife2 = life;

        //percentages
        if (life < minOppLife2) {
            minOppLife2 = life;
        }
    }

    //called when opp damaged
    public static void setCurrLifeChar2(int life) {
        currCharLife3 = life;

        //percentages
        if (life < currCharLife3) {
            currCharLife3 = life;
        }
    }

    public static float getCharMinLife() {
        return (float) minCharlife;
    }

    public static float getCharCurrLife() {
        return (float) currCharLife;
    }

    public static float getOppMinLife() {
        return (float) minOppLife;
    }

    public static float getOppCurrLife() {
        return (float) currOppLife;
    }

    public static float getPoints() {
        return (float) points / maxPoints;
    }

    public static void setPoints(int amount) {
        points = amount;
        maxPoints = amount;
    }

    public static void alterPoints(int thisMuch) {
        points = points - thisMuch;
    }

    /*
     *
     */
    public static void alterPoints2(int index) {
        if (RenderGameplay.getInstance().getNumOfAttacks() > 1) {
            RenderGameplay.getInstance().setNumOfAttacks(1);
            points = points + pointsArr[index];
        }
    }

    /**
     * SET damage multipliers, used to strengthen/weaken attacks
     *
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public static void setDamageCounter(char per, int thisMuch) {
        if (per == 'c') {
            damageMultiplierOpp = thisMuch;
        }

        if (per == 'o') {
            damageMultiplierChar = thisMuch;
        }
    }

    public static int getDamageMultiplier(char per) {
        int myInt = 0;

        if (per == 'c') {
            myInt = damageMultiplierOpp;
        } else if (per == 'o') {
            myInt = damageMultiplierChar;
        }

        return myInt;
    }

    //--------public accessor methods-----------------
    public String getCharName() {
        return characterName;
    }

    /**
     * Get the Character assist partner
     *
     * @return character assist partner
     */
    public String getCharAssName() {
        return assistCharacterName;
    }

    /**
     * Get the opponents assist partner
     *
     * @return opponent assist partner
     */
    public String getOppAssName() {
        return opponentAssistantName;
    }

    public String getOppName() {
        return nameOpp;
    }

    public void setOppName(String thisName) {
        minOppLife = 100;
        currOppLife = 100;
        nameOpp = thisName;
    }

    public com.scndgen.legends.characters.Character getCharacter() {
        return character;
    }

    public com.scndgen.legends.characters.Character getAssistCharacter() {
        return assistCharacter;
    }

    public com.scndgen.legends.characters.Character getOpponent() {
        return opponent;
    }

    public com.scndgen.legends.characters.Character getAssistOpponent() {
        return opponentAssistant;
    }

    public void prepare(com.scndgen.legends.enums.Character character) {

        minOppLife = 100;
        currOppLife = 100;
        minCharlife = 100;
        currCharLife = 100;
        setDamageCounter('c', 12);

        switch (character) {
            case SUBIYA:
                assistCharacter = new Raila();
                this.character = new Subiya();
                break;
            case RAILA:
                assistCharacter = new Subiya();
                this.character = new Raila();
                break;
            case LYNX:
                assistCharacter = new Aisha();
                this.character = new Lynx();
                break;
            case AISHA:
                assistCharacter = new Lynx();
                this.character = new Aisha();
                break;
            case RAVAGE:
                assistCharacter = new Jonah();
                this.character = new Ravage();
                break;
            case ADE:
                assistCharacter = new Adam();
                this.character = new Ade();
                break;
            case JONAH:
                assistCharacter = new Ravage();
                this.character = new Jonah();
                break;
            case ADAM:
                assistCharacter = new Ade();
                this.character = new Adam();
                break;
            case NOVA_ADAM:
                assistCharacter = new Ade();
                this.character = new NovaAdam();
                break;
            case AZARIA:
                assistCharacter = new Lynx();
                this.character = new Azaria();
                break;
            case SORROWE:
                assistCharacter = new Ade();
                this.character = new Sorrowe();
                break;
            case THING:
                assistCharacter = new NovaAdam();
                this.character = new Thing(0);
                break;
        }

        characterName = character.name();
        RenderCharacterSelectionScreen.getInstance().setSelectedCharIndex(character.index());
        assistCharacterName = assistCharacter.getEnum().name();

        activityRecoverRateChar = this.character.getRecovSpeed();
        healthRecoveryRateChar = this.character.getHPRecovRate();
        setPoints(this.character.getPoints());
        RenderGameplay.getInstance().setLife(this.character.getLife());
        RenderGameplay.getInstance().setMaxLife(this.character.getLife());


        activityRecoveryRateChar2 = assistCharacter.getRecovSpeed();
        healthRecoveryRateChar2 = assistCharacter.getHPRecovRate();
        RenderGameplay.getInstance().setCharLife3(assistCharacter.getLife());
        RenderGameplay.getInstance().setCharMaxLife3(assistCharacter.getLife());
        assistCharacter.setAiProf3();
    }

    public void prepareO(com.scndgen.legends.enums.Character character) {
        minOppLife = 100;
        currOppLife = 100;
        minCharlife = 100;
        currCharLife = 100;
        setDamageCounter('o', 12);
        switch (character) {
            case SUBIYA:
                opponentAssistant = new Raila();
                opponent = new Subiya();
                break;
            case RAILA:
                opponentAssistant = new Subiya();
                opponent = new Raila();
                break;
            case LYNX:
                opponentAssistant = new Aisha();
                opponent = new Lynx();
                break;
            case AISHA:
                opponentAssistant = new Lynx();
                opponent = new Aisha();
                break;
            case RAVAGE:
                opponentAssistant = new Jonah();
                opponent = new Ravage();
                break;
            case ADE:
                opponentAssistant = new Adam();
                opponent = new Ade();
                break;
            case JONAH:
                opponentAssistant = new Ravage();
                opponent = new Jonah();
                break;
            case ADAM:
                opponentAssistant = new Ade();
                opponent = new Adam();
                break;
            case NOVA_ADAM:
                opponentAssistant = new Ade();
                opponent = new NovaAdam();
                break;
            case AZARIA:
                opponentAssistant = new Lynx();
                opponent = new Azaria();
                break;
            case SORROWE:
                opponentAssistant = new Ade();
                opponent = new Sorrowe();
                break;
            case THING:
                opponentAssistant = new NovaAdam();
                opponent = new Thing(0);
                break;
        }
        nameOpp = character.name();
        RenderCharacterSelectionScreen.getInstance().setSelectedOppIndex(character.index());
        opponentAssistantName = opponentAssistant.getEnum().name();

        activityRecoveryRateOpp = opponent.getRecovSpeed();
        healthRecoveryRateOpp = opponent.getHPRecovRate();
        RenderGameplay.getInstance().setOppLife(opponent.getLife());
        RenderGameplay.getInstance().setOppMaxLife(opponent.getLife());
        opponent.setAiProf();

        activityRecoveryRateOpp2 = opponentAssistant.getRecovSpeed();
        healthRecoveryRateOpp2 = opponentAssistant.getHPRecovRate();
        RenderGameplay.getInstance().setOppLife2(opponentAssistant.getLife());
        RenderGameplay.getInstance().setOppMaxLife2(opponentAssistant.getLife());
        opponentAssistant.setAiProf2();
    }

    /**
     * Added 19/jan/2011 by SubiyaCryolite -
     * resets every character
     */
    public void resetCharacters() {
        opponent.resetLimits();
        character.resetLimits();
        RenderCharacterSelectionScreen.getInstance().setCharacterSelected(false);
        RenderCharacterSelectionScreen.getInstance().setOpponentSelected(false);
    }
}