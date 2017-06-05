package com.scndgen.legends.mode;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.constants.NetworkConstants;
import com.scndgen.legends.enums.AudioType;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.network.NetworkManager;
import io.github.subiyacryolite.enginev1.AudioPlayback;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Mode;
import javafx.scene.input.MouseEvent;

import java.util.Hashtable;

/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class CharacterSelection extends Mode {
    protected static String charDesc = "";
    protected int[] attacks;
    protected String[] characterDescription;
    protected final int numOfCharacters = CharacterEnum.values().length;
    protected int xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, column = 1, x = 0, y = 0, row = 0, hSpacer = 48, vSpacer = 48, hPos = 354, firstLine = 105;
    protected Loader loader;
    protected float opacInc, p1Opac, opacChar;
    protected CharacterEnum opponentEnum, characterEnum;
    protected int oppPrevLoc, charPrevLoc;
    protected boolean selectedCharacter, selectedOpponent, animatorThreadRunning;
    protected int selectedCharIndex = 0, selectedOppIndex = 0;
    protected final int columns = 3;
    protected boolean canSelectCharacter;
    protected final Hashtable<Integer, CharacterEnum> characterLookup = new Hashtable<>();

    public void newInstance() {
        loadAssets = true;
        canSelectCharacter = true;
        selectedCharacter = false;
        selectedOpponent = false;
        refreshSelections();
        characterLookup.clear();
        for (CharacterEnum character : CharacterEnum.values()) {
            characterLookup.put(character.index(), character);
        }
    }

    /**
     * Initialises the characterEnum select panel
     */
    public CharacterSelection() {

        attacks = new int[4];
    }

    protected void playSelectSound() {
        AudioPlayback sound = new AudioPlayback(AudioConstants.charSelectSound(), AudioType.SOUND, false);
        sound.play();
    }

    public void setAttacks(int attackNUm, int frames) {
        attacks[attackNUm] = frames;
    }

    /**
     * Load attacks
     *
     * @return
     */
    public int getAttacks() {
        return attacks.length;
    }

    /**
     * Get number of frames
     *
     * @param index - the attack
     * @return - the number of frames
     */
    public int getAttack(int index) {
        return attacks[index];
    }

    /**
     * Animates cloud
     */
    public static void animCloud() {
    }

    /**
     * Depending on scene, sets
     */
    private static void sortMode(Player who) {
        /**
         * If you choose a characterEnum in lan, you can't choose your opponent
         */
        if (who == Player.CHARACTER) {
        }
    }

    /**
     * Disables all buttons, used in online and playStory modes
     */
    public void preventCharacterSelection() {
        canSelectCharacter = false;
    }


    /**
     * Refresh selections
     */
    private void refreshSelections() {
        charPrevLoc = 0;
        oppPrevLoc = 0;
        selectedCharacter = false;
        selectedOpponent = false;
        //selectedCharIndex=0;
        //selectedOppIndex=0;
    }

    /**
     * Get the CharacterEnum getInfo
     *
     * @return characterEnum getInfo
     */
    public CharacterEnum getCharName() {
        return characterEnum;
    }

    /**
     * Get the opponents getInfo
     *
     * @return opponent getInfo
     */
    public CharacterEnum getOppName() {
        return opponentEnum;
    }

    /**
     * Selecting Raila
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selRaila(Player type) {
        primaryNotice(Language.get().get(84));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.RAILA;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_RAILA);
                preventCharacterSelection();
            }
        } else if (type == Player.OPPONENT && !selectedOpponent) {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.RAILA;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Subiya
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selSubiya(Player type) {
        if (type == Player.CHARACTER) //when selecting char
        {
            primaryNotice(Language.get().get(85));
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.SUBIYA;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_SUBIYA);
                preventCharacterSelection();
            }
        }
        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.SUBIYA;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Lynx
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selLynx(Player type) {
        primaryNotice(Language.get().get(86));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.LYNX;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_LYNX);
                preventCharacterSelection();
            }
        }
        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.LYNX;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selAisha(Player type) {
        primaryNotice(Language.get().get(87));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.AISHA;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_ALEX);
                preventCharacterSelection();
            }
        }
        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.AISHA;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Ade
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selAde(Player type) {
        primaryNotice(Language.get().get(88));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.ADE;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_ADE);
                preventCharacterSelection();
            }
        }
        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.ADE;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selRav(Player type) {
        primaryNotice(Language.get().get(89));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.RAVAGE;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_RAVAGE);
                preventCharacterSelection();
            }
        }

        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.RAVAGE;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Jonah
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selJon(Player type) {
        primaryNotice(Language.get().get(90));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.JONAH;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_JOHN);
                preventCharacterSelection();
            }
        }

        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.JONAH;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting NovaAdam
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selAdam(Player type) {
        primaryNotice(Language.get().get(91));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.ADAM;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_ADAM);
                preventCharacterSelection();
            }
        }

        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.NOVA_ADAM;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting NOVA NovaAdam
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selNOVAAdam(Player type) {
        primaryNotice(Language.get().get(92));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.NOVA_ADAM;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_NOVA_ADAM);
                preventCharacterSelection();
            }
        }

        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.NOVA_ADAM;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Azaria
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selAza(Player type) {
        primaryNotice(Language.get().get(93));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.AZARIA;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_AZARIA);
                preventCharacterSelection();
            }
        }

        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.AZARIA;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selSorr(Player type) {
        primaryNotice(Language.get().get(94));
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.SORROWE;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_SORROWE);
                preventCharacterSelection();
            }
        }

        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.SORROWE;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (Player.OPPONENT) or characterEnum (Player.CHARACTER)
     */
    public void selThing(Player type) {
        primaryNotice("..........");
        if (type == Player.CHARACTER) //when selecting char
        {
            playSelectSound();
            selectedCharacter = true;
            characterEnum = CharacterEnum.THING;
            Characters.get().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.get().getCharacter().getDescSmall();
            if (NetworkManager.get().isOnline()) {
                NetworkManager.get().send(NetworkConstants.SEL_THING);
                preventCharacterSelection();
            }
        }

        if (type == Player.OPPONENT && !selectedOpponent) // when selecting opponent
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.THING;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        } else if (type == Player.BOSS && !selectedOpponent) // when selecting opponent as boss
        {
            playSelectSound();
            selectedOpponent = true;
            opponentEnum = CharacterEnum.THING;
            Characters.get().prepareO(opponentEnum);
            selectedOppIndex = oppPrevLoc = opponentEnum.index();
        }
    }

    public void proceed2False() {
        selectedOpponent = false;
    }

    public void animateCharSelect() {
        if (animatorThreadRunning) return;
        new Thread() {
            @Override
            public void run() {
                this.setName("CharacterEnum select bg animation thread");
                do {
                    animatorThreadRunning = true;
                    try {
                        for (int x = 0; x > (-1440 + 852); x++) {
                            this.sleep(0033);
                            xCordCloud = xCordCloud - 3;
                            xCordCloud2 = xCordCloud2 - 5;
                            if (xCordCloud < -960) {
                                xCordCloud = 852;
                            }
                            if (xCordCloud2 < -960) {
                                xCordCloud2 = 852;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                }
                while (ScndGenLegends.get().getSubMode() == SubMode.MAIN_MENU);
                animatorThreadRunning = false;
            }
        }.start();
    }

    /**
     * Selects characterEnum
     */
    public void selectCharacter() {
        if (!canSelectCharacter) {
            errorSound();
            return;
        }
        int row = getHindex();
        int column = getVindex();
        int computedPosition = (columns * column) + row;
        CharacterEnum character = characterLookup.get(computedPosition);
        switch (character) {
            case SUBIYA:
                selSubiya(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case RAILA:
                selRaila(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case LYNX:
                selLynx(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case AISHA:
                selAisha(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case ADE:
                selAde(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case RAVAGE:
                selRav(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case JONAH:
                selJon(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case ADAM:
                selAdam(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case NOVA_ADAM:
                selNOVAAdam(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case AZARIA:
                selAza(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case SORROWE:
                selSorr(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
            case THING:
                selThing(selectedCharacter ? Player.OPPONENT : Player.CHARACTER);
                break;
        }
    }

    /**
     * Plays error sound
     */
    public void errorSound() {
        AudioPlayback error = new AudioPlayback("audio/error.ogg", AudioType.SOUND, false);
        error.play();
    }

    /**
     * Horizontal index
     *
     * @return column
     */
    public int getHindex() {
        return column;
    }

    /**
     * Vertical index
     *
     * @return row
     */
    public int getVindex() {
        return row;
    }

    /**
     * Animates captions
     */
    protected void animatePortratit() {
        x = -100;
        p1Opac = 0.0f;
        opacChar = 0.0f;
    }

    /**
     * Checks if within number of CharacterEnum
     */
    public boolean isWithinRange() {
        boolean ans = false;
        int whichChar = ((row * columns) + column) - 1;
        if (whichChar <= numOfCharacters) {
            ans = true;
        }
        return ans;
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both CharacterEnum have been selected, true if only one is selected
     */
    public boolean bothArentSelected() {
        boolean answer = true;
        if (selectedCharacter && selectedOpponent) {
            answer = false;
        }
        return answer;
    }

    public int getSelectedCharIndex() {
        return selectedCharIndex;
    }

    public void setSelectedCharIndex(int selectedCharIndex) {
        this.selectedCharIndex = selectedCharIndex;
    }

    public void setSelectedCharacter(boolean selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public void setSelectedOpponent(boolean selectedOpponent) {
        this.selectedOpponent = selectedOpponent;
    }

    public void setSelectedOppIndex(int selectedOppIndex) {
        this.selectedOppIndex = selectedOppIndex;
    }

    public void mouseMoved(MouseEvent m) {
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                onAccept();
                break;
            case SECONDARY:
                onBackCancel();
                break;
            case MIDDLE:
                break;
        }
    }
}
