package com.scndgen.legends.scene;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.enums.Character;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.Language;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.windows.MainWindow;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ifunga on 14/04/2017.
 */
public abstract class CharacterSelectionScreen extends JenesisMode implements ActionListener {
    protected String[] statsChar = new String[LoginScreen.getInstance().charNames.length];
    protected int numOfCharacters = Character.values().length;
    protected int col, currentSlot = 0, lastRow, xCordCloud = 0, xCordCloud2 = 0, charYcap = 0, charXcap = 0, charPrevLoicIndex = 0, hIndex = 1, x = 0, y = 0, vIndex = 0, hSpacer = 48, vSpacer = 48, hPos = 354, firstLine = 105, horizColumns = 3, verticalRows = 3;
    protected JenesisImageLoader imageLoader;
    protected int charDescIndex = 0;
    protected float opacInc, p1Opac, opacChar;
    protected static int characterSel, opponentSel;
    protected static String charDesc = "";
    protected Character opponent, character;
    protected int[] allPlayers = new int[LoginScreen.getInstance().charNames.length];
    protected int oppPrevLoc, charPrevLoc;
    protected static int[] arr1, arr2, arr3, arr4, arr5;
    protected static int[] arr1a, arr2a, arr3a, arr4a, arr5a;
    protected static int[] arr1b, arr2b, arr3b, arr4b, arr5b;
    protected static int[] attacks;
    protected static int storyBoards;
    protected boolean characterSelected = false, opponentSelected = false, isAnimatorNotRuning = true;
    protected int selectedCharIndex = 0, selectedOppIndex = 0;
    protected Object source;
    //private static StageSelect charVisual;
    protected AudioPlayback sound, sound2, error;
    protected final Characters players = new Characters();


    /**
     * Initialises the character select panel
     */
    public CharacterSelectionScreen() {
        setLayout(new BorderLayout());
        attacks = new int[4];
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    /**
     * Disables all buttons, used in online and story modes
     */
    public void disableAll() {
        for (int u = 0; u < allPlayers.length; u++) {
            allPlayers[u] = 1;
        }
    }

    /**
     * Get the storyboard size for the character
     *
     * @return the number of levels
     */
    public static int getStorySize() {
        return storyBoards;
    }

    /**
     * Set the number of setpieces
     *
     * @param size
     */
    public static void setStoryBoards(int size) {
        storyBoards = size;
    }

    /**
     * Set 5 AI personalities
     *
     * @param array - the movws
     * @param num   - the number
     */
    public static void setAISlot(int[] array, int num) {
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
    public static void setAISlot2(int[] array, int num) {
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
    public static void setAISlot3(int[] array, int num) {
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
    public static int[] getAISlot() {
        int[] array = {};
        //when doing well, all attacks
        if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 1.00) {
            array = arr1;
        } //when doing well, all attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.75 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 1.00) {
            array = arr2;
        } //when doing well, 4 attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.50 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 0.75) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('o');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3;
            }
        } //when doing well, 4 buffs + 2 moves
        else if (RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() >= 0.25 && RenderGameplay.getInstance().getOppLife() / RenderGameplay.getInstance().getOppMaxLife() < 0.50) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('o');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4;
            }
        } //first fury, when doing well, 4 buffs + 2 moves
        else {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('o');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5;
            }
        }

        return array;
    }

    /**
     * Get char AI
     *
     * @return AI - Personality
     */
    public static int[] getAISlot2() {
        int[] array = {};

        //when doing well, all attacks
        if (RenderGameplay.getInstance().getOppLife2() / RenderGameplay.getInstance().getOppMaxLife2() >= 1.00) {
            array = arr1a;
        } //when doing well, all attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife2() / RenderGameplay.getInstance().getOppMaxLife2() >= 0.75 && RenderGameplay.getInstance().getOppLife2() / RenderGameplay.getInstance().getOppMaxLife2() < 1.00) {
            array = arr2a;
        } //when doing well, 4 attacks + 2 buffs
        else if (RenderGameplay.getInstance().getOppLife2() / RenderGameplay.getInstance().getOppMaxLife2() >= 0.50 && RenderGameplay.getInstance().getOppLife2() / RenderGameplay.getInstance().getOppMaxLife2() < 0.75) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('b');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3a;
            }
        } //when doing well, 4 buffs + 2 moves
        else if (RenderGameplay.getInstance().getOppLife2() / RenderGameplay.getInstance().getOppMaxLife2() >= 0.25 && RenderGameplay.getInstance().getOppLife2() / RenderGameplay.getInstance().getOppMaxLife2() < 0.50) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('b');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4a;
            }
        } //first fury, when doing well, 4 buffs + 2 moves
        else {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('b');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5a;
            }
        }

        return array;
    }

    /**
     * Get char AI
     *
     * @return AI - Personality
     */
    public static int[] getAISlot3() {
        int[] array = {};

        //when doing well, all attacks
        if (RenderGameplay.getInstance().getCharLife3() / RenderGameplay.getInstance().getCharMaxLife3() >= 1.00) {
            array = arr1a;
        } //when doing well, all attacks + 2 buffs
        else if (RenderGameplay.getInstance().getCharLife3() / RenderGameplay.getInstance().getCharMaxLife3() >= 0.75 && RenderGameplay.getInstance().getCharLife3() / RenderGameplay.getInstance().getCharMaxLife3() < 1.00) {
            array = arr2a;
        } //when doing well, 4 attacks + 2 buffs
        else if (RenderGameplay.getInstance().getCharLife3() / RenderGameplay.getInstance().getCharMaxLife3() >= 0.50 && RenderGameplay.getInstance().getCharLife3() / RenderGameplay.getInstance().getCharMaxLife3() < 0.75) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('a');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr3a;
            }
        } //when doing well, 4 buffs + 2 moves
        else if (RenderGameplay.getInstance().getCharLife3() / RenderGameplay.getInstance().getCharMaxLife3() >= 0.25 && RenderGameplay.getInstance().getCharLife3() / RenderGameplay.getInstance().getCharMaxLife3() < 0.50) {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('a');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr4a;
            }
        } //first fury, when doing well, 4 buffs + 2 moves
        else {
            if (RenderGameplay.getInstance().getBreak() == 1000 && RenderGameplay.getInstance().limitRunning) {
                MainWindow.getInstance().triggerFury('a');
                array = new int[]{0, 0, 0, 0};
            } else {
                array = arr5a;
            }
        }

        return array;
    }

    public static void setAttacks(int attackNUm, int frames) {
        attacks[attackNUm] = frames;
    }

    /**
     * Load attacks
     *
     * @return
     */
    public static int getAttacks() {
        return attacks.length;
    }

    /**
     * Get number of frames
     *
     * @param index - the attack
     * @return - the number of frames
     */
    public static int getAttack(int index) {
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
    private static void sortMode(char who) {
        /**
         * If you choose a character in lan, you can't choose your opponent
         */
        if (who == 'c') {
        }
    }

    public Characters getPlayers() {
        return players;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        source = ae.getSource();
    }

    /**
     * Goes back to main menu
     */
    public void backToMenu() {
        refreshSelections();
        //cancel hosting
        if (MainWindow.getInstance().getGameMode().equals(MainWindow.lanHost)) {
            MainWindow.getInstance().closeTheServer();
        } else if (MainWindow.getInstance().getGameMode().equals(MainWindow.lanClient)) {
            MainWindow.getInstance().closeTheClient();
        } else if (MainWindow.getInstance().getGameMode().equals(MainWindow.storyMode)) {
            MainWindow.getInstance().getStory().getStoryInstance().skipDialogue();
        }
        MainWindow.getInstance().backToMenuScreen();
        RenderGameplay.getInstance().closeAudio();
    }

    /**
     * Starts a new game
     */
    public void beginGame() {
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost) || MainWindow.getInstance().getGameMode().equalsIgnoreCase("offline2")) {
            MainWindow.getInstance().selectStage();

            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("watchStageSel_xcbD");
            }
        }

    }

    /**
     * Refresh selections
     */
    public void refreshSelections() {
        charPrevLoc = 0;
        oppPrevLoc = 0;
        //MainWindow.getInstance().repaintCharSel();
        for (int u = 0; u < allPlayers.length; u++) {
            allPlayers[u] = 0; // 0 means free, 1 means selected
        }
        characterSelected = false;
        opponentSelected = false;
        //selectedCharIndex=0;
        //selectedOppIndex=0;
        MainWindow.getInstance().storedX = 99;
        MainWindow.getInstance().storedY = 99;
    }

    /**
     * Get the Character name
     *
     * @return character name
     */
    public Character getCharName() {
        return character;
    }

    /**
     * Get the opponents name
     *
     * @return opponent name
     */
    public Character getOppName() {
        return opponent;
    }

    /**
     * Selecting Raila
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selRaila(char type) {
        systemNotice(Language.getInstance().getLine(84));
        if (type == 'c') //when selecting char
        {
            {
                sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
                sound.play();
                characterSelected = true;
                character = Character.RAILA;
                getPlayers().prepare(character);
                allPlayers[character.index()] = charPrevLoc = selectedCharIndex = character.index();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("selRai_jkxc");
                    disableAll();
                }
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                    MainWindow.getInstance().sendToServer("selRai_jkxc");
                    disableAll();
                }
            }
        } else if (type == 'o' && opponentSelected == false) {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
            }
            opponentSelected = true;
            opponent = Character.RAILA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Subiya
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selSubiya(char type) {
        if (type == 'c') //when selecting char
        {
            systemNotice(Language.getInstance().getLine(85));
            {
                sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
                sound.play();
                characterSelected = true;
                character = Character.SUBIYA;
                getPlayers().prepare(character);
                allPlayers[character.index()] = charPrevLoc = selectedCharIndex = character.index();
                charDesc = getPlayers().getCharacter().getDescSmall();
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                    MainWindow.getInstance().sendToClient("selSub_jkxc");
                    disableAll();
                }
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                    MainWindow.getInstance().sendToServer("selSub_jkxc");
                    disableAll();
                }
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.SUBIYA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Lynx
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selLynx(char type) {
        systemNotice(Language.getInstance().getLine(86));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.LYNX;
            getPlayers().prepare(character);
            allPlayers[character.index()] = charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selLyn_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selLyn_jkxc");
                disableAll();
            }
        }
        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.LYNX;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAisha(char type) {
        systemNotice(Language.getInstance().getLine(87));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.AISHA;
            getPlayers().prepare(character);
            allPlayers[character.index()] = charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAlx_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAlx_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.AISHA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Ade
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAde(char type) {
        systemNotice(Language.getInstance().getLine(88));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.ADE;
            getPlayers().prepare(character);
            allPlayers[character.index()] = charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAde_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAde_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.ADE;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Aisha
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selRav(char type) {
        systemNotice(Language.getInstance().getLine(89));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.RAVAGE;
            getPlayers().prepare(character);
            allPlayers[character.index()] = charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selRav_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selRav_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.RAVAGE;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Jonah
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selJon(char type) {
        systemNotice(Language.getInstance().getLine(90));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.JONAH;
            getPlayers().prepare(character);
            charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            allPlayers[6] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selJon_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selJon_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.JONAH;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting NovaAdam
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAdam(char type) {
        systemNotice(Language.getInstance().getLine(91));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.ADAM;
            getPlayers().prepare(character);
            allPlayers[character.index()] = charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAdam_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAdam_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.NOVA_ADAM;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting NOVA NovaAdam
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selNOVAAdam(char type) {
        systemNotice(Language.getInstance().getLine(92));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.NOVA_ADAM;
            getPlayers().prepare(character);
            charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[8] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selNOVAAdam_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selNOVAAdam_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.NOVA_ADAM;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Azaria
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selAza(char type) {
        systemNotice(Language.getInstance().getLine(93));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.AZARIA;
            getPlayers().prepare(character);
            charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[9] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selAzaria_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selAzaria_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.AZARIA;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selSorr(char type) {
        systemNotice(Language.getInstance().getLine(94));
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.SORROWE;
            getPlayers().prepare(character);
            charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[10] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selSorr_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selSorr_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    /**
     * Selecting Sorrowe
     *
     * @param type - opponent ('o') or character ('c')
     */
    public void selThing(char type) {
        systemNotice("..........");
        if (type == 'c') //when selecting char
        {
            sound = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound.play();
            characterSelected = true;
            character = Character.THING;
            getPlayers().prepare(character);
            charPrevLoc = selectedCharIndex = character.index();
            charDesc = getPlayers().getCharacter().getDescSmall();
            //MainWindow.getInstance().repaintCharSel();
            allPlayers[11] = 1;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                MainWindow.getInstance().sendToClient("selThi_jkxc");
                disableAll();
            }
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                MainWindow.getInstance().sendToServer("selThi_jkxc");
                disableAll();
            }
        }

        if (type == 'o' && opponentSelected == false) // when selecting opponent
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.THING;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }

        if (type == 'x' && opponentSelected == false) // when selecting opponent as boss
        {
            sound2 = new AudioPlayback(AudioPlayback.charSelectSound(), false);
            sound2.play();
            opponentSelected = true;
            opponent = Character.THING;
            getPlayers().prepareO(opponent);
            selectedOppIndex = oppPrevLoc = allPlayers[2] = opponent.index();
        }
    }

    public void proceed2False() {
        opponentSelected = false;
    }

    public void animateCharSelect() {
        new Thread() {

            @Override
            @SuppressWarnings("static-access")
            public void run() {
                this.setName("Characters select bg animation thread");
                if (isAnimatorNotRuning) {
                    do {
                        isAnimatorNotRuning = false;
                        try {
                            for (int x = 0; x > (-1440 + 852); x++) {
                                {
                                    this.sleep(0033);
                                    this.setName("What am I doing");
                                    xCordCloud = xCordCloud - 3;
                                    xCordCloud2 = xCordCloud2 - 5;
                                    if (xCordCloud < -960) {
                                        xCordCloud = 852;
                                    }
                                    if (xCordCloud2 < -960) {
                                        xCordCloud2 = 852;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    while (MainWindow.getInstance().mode == Mode.CHAR_SELECT_SCREEN);
                }

            }
        }.start();
    }

    public void setItem() {
        currentSlot = (verticalRows * vIndex) + hIndex;
        System.out.println("Current Slot: " + currentSlot);
    }

    /**
     * Character select screen, move up
     */
    public void moveUp() {
        upMove();
    }

    /**
     * Character select screen, move down
     */
    public void moveDown() {
        downMove();
    }

    /**
     * Character select screen, move right
     */
    public void moveRight() {
        rightMove();
    }

    /**
     * Character select screen, move left
     */
    public void moveLeft() {
        leftMove();
    }

    /**
     * Gets the number of columns in the character select screen
     *
     * @return number of columns
     */
    public int getNumberOfCharColumns() {
        return horizColumns;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharHSpacer() {
        return vSpacer;
    }

    /**
     * Gets the char caption spacer
     *
     * @return spacer
     */
    public int getCharVSpacer() {
        return hSpacer;
    }

    /**
     * Get starting x coordinate
     *
     * @return starting x coordinate
     */
    public int getStartX() {
        return hPos;
    }

    /**
     * Returns the starting Y coordinate
     *
     * @return starting y
     */
    public int getStartY() {
        return firstLine;
    }

    /**
     * Get number of char rows
     *
     * @return number of rows
     */
    public int getCharRows() {
        return verticalRows;
    }

    /**
     * Animates caption
     */
    public void animateCaption() {
        capAnim();
    }

    /**
     * Selects character
     */
    public void selectChar() {
        int horz = getHindex();
        int vert = getVindex();

        if (horz == 1 && vert == 0) {
            if (allPlayers[0] == 0) {
                if (characterSelected == false) {
                    selRaila('c');
                } else {
                    selRaila('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 0) {
            if (allPlayers[1] == 0) {
                if (characterSelected == false) {
                    selSubiya('c');
                } else {
                    selSubiya('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 0) {
            if (allPlayers[2] == 0) {
                if (characterSelected == false) {
                    selLynx('c');
                } else {
                    selLynx('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 1) {
            if (allPlayers[3] == 0) {
                if (characterSelected == false) {
                    selAisha('c');
                } else {
                    selAisha('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 1) {
            if (allPlayers[4] == 0) {
                if (characterSelected == false) {
                    selRav('c');
                } else {
                    selRav('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 1) {
            if (allPlayers[5] == 0) {
                if (characterSelected == false) {
                    selAde('c');
                } else {
                    selAde('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 2) {
            if (allPlayers[6] == 0) {
                if (characterSelected == false) {
                    selJon('c');
                } else {
                    selJon('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 2) {
            if (allPlayers[7] == 0) {
                if (characterSelected == false) {
                    selAdam('c');
                } else {
                    selAdam('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 2) {
            if (allPlayers[8] == 0) {
                if (characterSelected == false) {
                    selNOVAAdam('c');
                } else {
                    selNOVAAdam('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 1 && vert == 3) {
            if (allPlayers[9] == 0) {
                if (characterSelected == false) {
                    selAza('c');
                } else {
                    selAza('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 2 && vert == 3) {
            if (allPlayers[10] == 0) {
                if (characterSelected == false) {
                    selSorr('c');
                } else {
                    selSorr('o');
                }
            } else {
                errorSound();
            }
        }

        if (horz == 3 && vert == 3) {
            if (allPlayers[11] == 0) {
                if (characterSelected == false) {
                    selThing('c');
                } else {
                    selThing('o');
                }
            } else {
                errorSound();
            }
        }
    }

    /**
     * Plays error sound
     */
    public void errorSound() {
        error = new AudioPlayback("audio/error.mp3", false);
        error.play();
        System.out.println("Error sound");
    }


    /**
     * Move up
     */
    public void upMove() {
        if (vIndex > 0) {
            vIndex = vIndex - 1;
        } else {
            vIndex = verticalRows;
        }

        capAnim();
    }

    /**
     * Move down
     */
    public void downMove() {
        if (vIndex < verticalRows) {
            vIndex = vIndex + 1;
        } else {
            vIndex = 0;
        }

        capAnim();
    }

    /**
     * Move right
     */
    public void rightMove() {
        if (hIndex < horizColumns) {
            hIndex = hIndex + 1;
        } else {
            hIndex = 1;
        }

        capAnim();
    }

    /**
     * Move left
     */
    public void leftMove() {
        if (hIndex > 1) {
            hIndex = hIndex - 1;
        } else {
            hIndex = horizColumns;
        }

        capAnim();
    }

    /**
     * Horizontal index
     *
     * @return hIndex
     */
    public int getHindex() {
        return hIndex;
    }

    /**
     * Set horizontal index
     */
    public void setHindex(int value) {
        hIndex = value;
    }

    /**
     * Vertical index
     *
     * @return vIndex
     */
    public int getVindex() {
        return vIndex;
    }

    /**
     * Set vertical index
     */
    public void setVindex(int value) {
        vIndex = value;
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
     * Checks if within number of Character
     */
    public boolean well() {
        boolean ans = false;
        int whichChar = ((vIndex * 3) + hIndex) - 1;
        if (whichChar <= numOfCharacters) {
            ans = true;
            //System.out.println("Dude: "+whichChar);
        }

        return ans;
    }

    /**
     * When both playes are selected, this prevents movement.
     *
     * @return false if both Character have been selected, true if only one is selected
     */
    public boolean bothArentSelected() {
        boolean answer = true;
        if (characterSelected && opponentSelected) {
            answer = false;
        }
        return answer;
    }

    public void setOpponentSelected(boolean opponentSelected) {
        this.opponentSelected = opponentSelected;
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

    public boolean getOpponentSelected() {
        return opponentSelected;
    }

    public void setSelectedOppIndex(int selectedOppIndex) {
        this.selectedOppIndex = selectedOppIndex;
    }

    public void setCharacterSelected(boolean characterSelected) {
        this.characterSelected = characterSelected;
    }
}