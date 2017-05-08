package com.scndgen.legends.scene;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.constants.AudioConstants;
import com.scndgen.legends.controller.StoryMode;
import com.scndgen.legends.enums.*;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.windows.JenesisPanel;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Hashtable;

/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class CharacterSelectionScreen extends JenesisMode {
    protected static String charDesc = "";
    protected int[] arr1, arr2, arr3, arr4, arr5;
    protected int[] arr1a, arr2a, arr3a, arr4a, arr5a;
    protected int[] arr1b, arr2b, arr3b, arr4b, arr5b;
    protected int[] attacks;
    protected String[] statsChar = new String[LoginScreen.getInstance().charNames.length];
    protected final int numOfCharacters = CharacterEnum.values().length;
    protected int currentSlot = 0, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, charPrevLoicIndex = 0, column = 1, x = 0, y = 0, row = 0, hSpacer = 48, vSpacer = 48, hPos = 354, firstLine = 105;
    protected JenesisImageLoader imageLoader;
    protected int charDescIndex = 0;
    protected float opacInc, p1Opac, opacChar;
    protected CharacterEnum opponent, characterEnum;
    protected int oppPrevLoc, charPrevLoc;
    protected boolean characterSelected, opponentSelected, animatorThreadRunning;
    protected int selectedCharIndex = 0, selectedOppIndex = 0;
    protected final AudioPlayback sound, error;
    protected final int columns = 3;
    protected final int rows = numOfCharacters / columns;
    protected final int rowsCiel = Math.round(Math.round(Math.ceil(numOfCharacters / (double) columns)));
    protected final int columnsCiel = numOfCharacters % columns;
    protected boolean canSelectCharacter;
    protected final Hashtable<Integer, CharacterEnum> characterLookup = new Hashtable<>();
    protected int storedX;
    protected int storedY;

    public void newInstance() {
        loadAssets = true;
        canSelectCharacter = true;
        characterSelected = false;
        opponentSelected = false;
        refreshSelections();
        characterLookup.clear();
        for (CharacterEnum character : CharacterEnum.values()) {
            characterLookup.put(character.index(), character);
        }
    }

    /**
     * Initialises the characterEnum select panel
     */
    public CharacterSelectionScreen() {
        error = new AudioPlayback("audio/error.ogg", AudioType.SOUND, false);
        sound = new AudioPlayback(AudioConstants.charSelectSound(), AudioType.SOUND, false);
        attacks = new int[4];
    }

    /**
     * Set 5 AI personalities
     *
     * @param array - the movws
     * @param num   - the number
     */
    public void setAISlot(int[] array, int num) {
        if (num == 1) {
            arr1 = array;
        }

        if (num == 2) {
            arr2 = array;
        }

        if (num == 3) {
            arr3 = array;
        }

        if (num == 4) {
            arr4 = array;
        }

        if (num == 5) {
            arr5 = array;
        }
    }

    /**
     * Set 5 AI personalities
     *
     * @param array - the movws
     * @param num   - the number
     */
    public void setAISlot2(int[] array, int num) {
        if (num == 1) {
            arr1a = array;
        }

        if (num == 2) {
            arr2a = array;
        }

        if (num == 3) {
            arr3a = array;
        }

        if (num == 4) {
            arr4a = array;
        }

        if (num == 5) {
            arr5a = array;
        }
    }

    /**
     * Set 5 AI personalities
     *
     * @param array - the movws
     * @param num   - the number
     */
    public void setAISlot3(int[] array, int num) {
        if (num == 1) {
            arr1b = array;
        }

        if (num == 2) {
            arr2b = array;
        }

        if (num == 3) {
            arr3b = array;
        }

        if (num == 4) {
            arr4b = array;
        }

        if (num == 5) {
            arr5b = array;
        }
    }

    /**
     * Get char AI
     *
     * @return AI - Personality
     */
    public int[] getAISlot() {
        int[] array = {};
        //when doing isWithinRange, all attacks
        if (RenderGameplay.getInstance().getOpponentHp() / RenderGameplay.getInstance().getOpponentMaximumHp() >= 1.00) {
            array = arr1;
        } //when doing isWithinRange, all attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOpponentHp() / RenderGameplay.getInstance().getOpponentMaximumHp() >= 0.75 && RenderGameplay.getInstance().getOpponentHp() / RenderGameplay.getInstance().getOpponentMaximumHp() < 1.00) {
            array = arr2;
        } //when doing isWithinRange, 4 attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOpponentHp() / RenderGameplay.getInstance().getOpponentMaximumHp() >= 0.50 && RenderGameplay.getInstance().getOpponentHp() / RenderGameplay.getInstance().getOpponentMaximumHp() < 0.75) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                RenderGameplay.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3;
            }
        } //when doing isWithinRange, 4 buffs + 2 moves
        else if (RenderGameplay.getInstance().getOpponentHp() / RenderGameplay.getInstance().getOpponentMaximumHp() >= 0.25 && RenderGameplay.getInstance().getOpponentHp() / RenderGameplay.getInstance().getOpponentMaximumHp() < 0.50) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                RenderGameplay.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4;
            }
        } //first fury, when doing isWithinRange, 4 buffs + 2 moves
        else {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                RenderGameplay.getInstance().triggerFury(CharacterState.OPPONENT);
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5;
            }
        }

        return array;
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
    private static void sortMode(CharacterState who) {
        /**
         * If you choose a characterEnum in lan, you can't choose your opponent
         */
        if (who == CharacterState.CHARACTER) {
        }
    }

    /**
     * Disables all buttons, used in online and story modes
     */
    public void preventCharacterSelection() {
        canSelectCharacter = false;
    }

    /**
     * Goes onBackCancel to main menu
     */
    public void backToMenu() {
        newInstance();
        if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
            JenesisPanel.getInstance().closeTheServer();
        } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            JenesisPanel.getInstance().closeTheClient();
        } else if (ScndGenLegends.getInstance().getSubMode() == SubMode.STORY_MODE) {
            StoryMode.getInstance().onAccept();
        }
        ScndGenLegends.getInstance().loadMode(Mode.MAIN_MENU);
        RenderGameplay.getInstance().closeAudio();
    }

    /**
     * Refresh selections
     */
    private void refreshSelections() {
        charPrevLoc = 0;
        oppPrevLoc = 0;
        characterSelected = false;
        opponentSelected = false;
        //selectedCharIndex=0;
        //selectedOppIndex=0;
        storedX = 99;
        storedY = 99;
    }

    /**
     * Get the CharacterEnum name
     *
     * @return characterEnum name
     */
    public CharacterEnum getCharName() {
        return characterEnum;
    }

    /**
     * Get the opponents name
     *
     * @return opponent name
     */
    public CharacterEnum getOppName() {
        return opponent;
    }

    /**
     * Selecting Raila
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selRaila(CharacterState type) {
        primaryNotice(Language.getInstance().get(84));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.RAILA;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selRai_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selRai_jkxc");
                preventCharacterSelection();
            }

        } else if (type == CharacterState.OPPONENT && opponentSelected == false) {
            sound.play();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
            }
            opponentSelected = true;
            opponent = CharacterEnum.RAILA;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Subiya
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selSubiya(CharacterState type) {
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            primaryNotice(Language.getInstance().get(85));
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.SUBIYA;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selSub_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selSub_jkxc");
                preventCharacterSelection();
            }
        }
        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.SUBIYA;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Lynx
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selLynx(CharacterState type) {
        primaryNotice(Language.getInstance().get(86));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.LYNX;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selLyn_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selLyn_jkxc");
                preventCharacterSelection();
            }
        }
        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.LYNX;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selAisha(CharacterState type) {
        primaryNotice(Language.getInstance().get(87));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.AISHA;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selAlx_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selAlx_jkxc");
                preventCharacterSelection();
            }
        }
        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.AISHA;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Ade
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selAde(CharacterState type) {
        primaryNotice(Language.getInstance().get(88));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.ADE;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selAde_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selAde_jkxc");
                preventCharacterSelection();
            }
        }
        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.ADE;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selRav(CharacterState type) {
        primaryNotice(Language.getInstance().get(89));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.RAVAGE;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selRav_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selRav_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.RAVAGE;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Jonah
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selJon(CharacterState type) {
        primaryNotice(Language.getInstance().get(90));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.JONAH;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selJon_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selJon_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.JONAH;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting NovaAdam
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selAdam(CharacterState type) {
        primaryNotice(Language.getInstance().get(91));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.ADAM;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selAdam_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selAdam_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.NOVA_ADAM;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting NOVA NovaAdam
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selNOVAAdam(CharacterState type) {
        primaryNotice(Language.getInstance().get(92));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.NOVA_ADAM;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selNOVAAdam_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selNOVAAdam_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.NOVA_ADAM;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Azaria
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selAza(CharacterState type) {
        primaryNotice(Language.getInstance().get(93));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.AZARIA;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selAzaria_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selAzaria_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.AZARIA;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selSorr(CharacterState type) {
        primaryNotice(Language.getInstance().get(94));
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.SORROWE;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selSorr_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selSorr_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent (CharacterState.OPPONENT) or characterEnum (CharacterState.CHARACTER)
     */
    public void selThing(CharacterState type) {
        primaryNotice("..........");
        if (type == CharacterState.CHARACTER) //when selecting char
        {
            sound.play();
            characterSelected = true;
            characterEnum = CharacterEnum.THING;
            Characters.getInstance().prepare(characterEnum);
            charPrevLoc = selectedCharIndex = characterEnum.index();
            charDesc = Characters.getInstance().getCharacter().getDescSmall();
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                JenesisPanel.getInstance().sendToClient("selThi_jkxc");
                preventCharacterSelection();
            }
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_CLIENT) {
                JenesisPanel.getInstance().sendToServer("selThi_jkxc");
                preventCharacterSelection();
            }
        }

        if (type == CharacterState.OPPONENT && opponentSelected == false) // when selecting opponent
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.THING;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }

        if (type == CharacterState.BOSS && opponentSelected == false) // when selecting opponent as boss
        {
            sound.play();
            opponentSelected = true;
            opponent = CharacterEnum.THING;
            Characters.getInstance().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = opponent.index();
        }
    }

    public void proceed2False() {
        opponentSelected = false;
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
                while (ScndGenLegends.getInstance().getSubMode() == SubMode.MAIN_MENU);
                animatorThreadRunning = false;
            }
        }.start();
    }

    /**
     * Move up
     */
    public void onUp() {
        if (row > 0)
            row -= 1;
        else {
            int upperLimit = column < columnsCiel ? rowsCiel - 1 : rows - 1;
            row = upperLimit;
        }
        capAnim();
    }

    /**
     * Move down
     */
    public void onDown() {
        int limit = column < columnsCiel ? rowsCiel - 1 : rows - 1;
        if (row < limit)
            row++;
        else
            row = 0;
        capAnim();
    }

    /**
     * Move right
     */
    public void onRight() {
        int limit = (row < rowsCiel) ? columns - 1 : columnsCiel - 1;
        if (column < limit)
            column++;
        else
            column = 0;
        capAnim();
    }

    /**
     * Move left
     */
    public void onLeft() {
        int limit = (row < rowsCiel - 1) ? columns - 1 : columnsCiel - 1;
        if (column > 0)
            column -= 1;
        else
            column = limit;
        capAnim();
    }

    /**
     * Gets the number of columns in the characterEnum select screen
     *
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCaptionHeight() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCaptionWidth() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getTopX() {
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getTopY() {
        return firstLine;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Animates caption
     */
    public void animateCaption() {
        capAnim();
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
                selSubiya(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case RAILA:
                selRaila(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case LYNX:
                selLynx(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case AISHA:
                selAisha(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case ADE:
                selAde(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case RAVAGE:
                selRav(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case JONAH:
                selJon(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case ADAM:
                selAdam(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case NOVA_ADAM:
                selNOVAAdam(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case AZARIA:
                selAza(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case SORROWE:
                selSorr(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
            case THING:
                selThing(characterSelected ? CharacterState.OPPONENT : CharacterState.CHARACTER);
                break;
        }
    }

    /**
     * Plays error sound
     */
    public void errorSound() {
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
     * Set horizontal index
     */
    public void setHindex(int value) {
        column = value;
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
     * Set vertical index
     */
    public void setVindex(int value) {
        row = value;
    }

    /**
     * Animates captions
     */
    public void capAnim() {
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
        if (characterSelected && opponentSelected) {
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

    public boolean getCharacterSelected() {
        return characterSelected;
    }

    public void setCharacterSelected(boolean characterSelected) {
        this.characterSelected = characterSelected;
    }

    public boolean getOpponentSelected() {
        return opponentSelected;
    }

    public void setOpponentSelected(boolean opponentSelected) {
        this.opponentSelected = opponentSelected;
    }

    public void setSelectedOppIndex(int selectedOppIndex) {
        this.selectedOppIndex = selectedOppIndex;
    }

    public void keyPressed(KeyEvent ke) {
        KeyCode keyCode = ke.getCode();
        switch (keyCode) {
            case ENTER:
                onAccept();
                break;
            case ESCAPE:
            case BACK_SPACE:
                onBackCancel();
                break;
            case UP:
            case W:
                onUp();
                break;
            case DOWN:
            case S:
                onDown();
                break;
            case LEFT:
            case A:
                onLeft();
                break;
            case RIGHT:
            case D:
                onRight();
                break;
        }
    }

    public void mouseMoved(MouseEvent m) {
        int topY = getTopY();
        int topX = getTopX();
        int columns = getColumns();
        int captionHeight = getCaptionHeight();
        int captionWidth = getCaptionWidth();
        int rows = getRows();
        if (m.getX() > topX && m.getX() < (topX + (captionWidth * columns)) && (m.getY() > topY) && (m.getY() < topY + (captionHeight * rows))) {
            int vIndex = Math.round(Math.round((m.getY() - topY) / captionHeight));
            int hIndex = Math.round(Math.round((m.getX() - topX) / captionWidth));
            setHindex(hIndex);
            setVindex(vIndex);
            if (hIndex != storedX || vIndex != storedY) //same vals, do nothing
            {
                storedX = hIndex;
                storedY = vIndex;
                animateCaption();
            }
            validHover = true;
        } else {
            setHindex(99);
            setVindex(99);
            validHover = false;
        }
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                if (validHover)
                    onAccept();
                break;
            case SECONDARY:
                break;
            case MIDDLE:
                break;
        }
    }

    public void onAccept() {
        if (getCharacterSelected() && getOpponentSelected()) {
            if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                if (ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER || ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST || ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER_TAG) {
                    ScndGenLegends.getInstance().loadMode(Mode.STAGE_SELECT_SCREEN);
                    if (ScndGenLegends.getInstance().getSubMode() == SubMode.LAN_HOST) {
                        //JenesisPanel.getInstance().sendToClient("watchStageSel_xcbD");
                    }
                }
            }
        } else {
            selectCharacter();
        }
    }
}
