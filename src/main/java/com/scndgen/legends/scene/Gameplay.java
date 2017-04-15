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
package com.scndgen.legends.scene;

import com.scndgen.legends.Achievements;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.Character;
import com.scndgen.legends.network.NetworkClient;
import com.scndgen.legends.network.NetworkServer;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.threads.ClashSystem;
import com.scndgen.legends.threads.ClashingOpponent;
import com.scndgen.legends.threads.ThreadGameInstance;
import com.scndgen.legends.windows.MainWindow;
import com.scndgen.legends.windows.WindowOptions;
import io.github.subiyacryolite.enginev1.JenesisGamePad;
import io.github.subiyacryolite.enginev1.JenesisMode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class draws and manipulates all sprites, images and effects used in the game
 *
 * @Author: Ifunga Ndana
 * @Class: Gameplay
 */
public abstract class Gameplay extends JenesisMode {
    protected String ActivePerson; // person who performed an attack, name shall show in battle info status area
    protected int perCent = 100, perCent2 = 100, perCent2a = 100, perCent3a = 100;
    protected Characters selectedChar, selectedOpp;
    protected int done = 0; // if gameover
    protected String[] attackArray = new String[8];//up to 8 moves can be qued
    protected int comboCounter = 0; //must be negative one to reach index 0, app wide counter, enable you to que attacks of different kinds
    protected boolean threadsNotRunningYet = true, playATBFile = false;
    protected StringBuilder StatusText = new StringBuilder();
    protected int startDrawing = 0, menuBarY;
    protected float angRot = 20;
    protected int charXcord = 10, charYcord = 10, oppYcord = 10, statIndex = 0;
    protected int amb1x = 0, amb1y = 0, amb2x = 0, amb2y = 0;
    protected int activeStage, numOfComicPics = 9;
    protected int fgx, fgy, fgxInc, fgyInc, animLoops, delay;
    protected String animDirection = "vert", verticalMove = "no";
    protected int oppXcord = 10;
    protected int playerDamageXLoc, opponentDamageXLoc, numOfAttacks = 0;
    protected String fgLocation;
    protected String bgLocation;
    protected String scenePic = "images/bgBG2.png";
    protected String attackPicSrc = "images/trans.png";
    protected String[] storyPicArrStr;
    protected Character[] charNames = LoginScreen.charNames;
    protected String attackPicOppSrc = "images/trans.png";
    protected int ambSpeed1, ambSpeed2, paneCord;
    protected StringBuilder battleInf = new StringBuilder("");
    protected int count = 0, fpsInt = 0, fpsIntStat;
    protected String[] physical, celestia, item, special, current;
    protected String[] currentColumn;
    protected int currentCols = 0;
    protected boolean safeToSelect = true;
    protected String animLayer = "";
    protected boolean clasherRunnign = false, dnladng;
    protected boolean loadedUpdaters;
    protected float daNum, daNum2, daNum2a, daNum3a;
    protected long lifePlain, lifeTotalPlain, lifePlain2, lifePlain2a, lifePlain3a, lifeTotalPlain2, lifeTotalPlain2a, lifeTotalPlain3a;
    protected int fancyBWAnimeEffect = 0;     //toggle fancy effect when HP low
    protected boolean fancyBWAnimeEffectEnabled;
    protected ThreadGameInstance fpsGen;
    protected boolean isMoveQued, gameOver;
    protected int thisInt; //max damage that can be dealt by Celestia Physics
    protected int damageC, damageO;
    protected int life, maXlife, oppLife, oppMaxLife, oppLife2, oppMaxLife2, charLife3, charMaxLife3;
    protected int damageChar, damageOpp, damageChar2, damageOpp2;
    protected int limitTop = 1000;
    protected String versionString = " 2K17 RMX";
    protected int versioInt = 20120630; // yyyy-mm-dd
    protected float finalOppLife, finalCharLife;
    protected Object source;
    protected int shakingChar = 0033, loop1 = 3, loop2 = 4;
    protected int x2 = 560, comX = 380, comY = 100;
    protected int xLocal = 470;
    protected int y2 = 435;
    protected int statIndexOpp, statIndexChar, statsPosYChar, statsPosYOpp, shakeyOffsetChar = 1, shakeyOffsetOpp = 1, basicX = 0, basicY = 0;
    protected boolean shaky1 = true;
    protected ClashingOpponent oppAttack = null;
    protected int animTime = 400, itemX = 0, itemY = 0;
    protected boolean runNew = true, effectChar = false;
    protected int bgX = 0;
    protected int numberOfStoryPix, lbx2 = 500;
    protected int lby2 = 420;
    protected String attackType = "normal", attackTYpeOpp = "normal", statusChar = "", statusOpp = "";
    protected int charMeleeSpriteStatus = 9, oppMeleeSpriteStatus = 9, charCelestiaSpriteStatus = 11, oppCelestiaSpriteStatus = 11;
    protected int oppAssSpriteStatus, charAssSpriteStatus;
    protected float statusOpChar, statusOpOpp;
    protected int itemindex = 0, furyBarY = 0;
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    protected int[] fontSizes = {LoginScreen.bigTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize};
    protected int attackInt = 0;
    protected String attackStr;
    protected boolean lagFactor = true;
    protected float currentXShear = 0, currentYShear = 0;
    protected boolean isFree = true, isFree2 = true;
    protected char dude;
    protected String sysNot = "";
    protected float sysNotOpac = 0, sysNotOpacInc = (float) 0.1;
    protected String ach1 = "", ach2 = "", ach3 = "", ach4 = "";
    protected int move = 0;
    protected NetworkServer server;
    protected NetworkClient client;
    protected boolean specialEffect;
    protected int InfoBarYPose, spacer = 27, randSoundIntChar, randSoundIntOpp, randSoundIntOppHurt, randSoundIntCharHurt, YOffset = 15;
    protected boolean imagesNumChached = false, imagesCharChached = false;
    protected int x = 2;
    protected int oppBarYOffset, leftyXOffset, y = 0;
    protected float opacityTxt = 10, opacityPic = 0.0f;
    protected boolean limitRunning = true, animCharFree = true;
    protected float angleRaw, charPointInc;
    protected int result;
    protected float opponentDamageOpacity, playerDamageOpacity, comicBookTextOpacity, furyComboOpacity;
    protected int comboPicArrayPosOpp = 8;
    protected String manipulateThis;
    protected int one, two, three, four, oneO, twoO, threeO, fourO;
    protected int comicY, opponentDamageYLoc, playerDamageYLoc, yTEST = 25, yTESTinit = 25;
    protected float opac = 1.0f;
    protected float damOpInc;
    protected boolean nextEnabled = true, backEnabled = true;
    protected int charOp = 10, comicPicArrayPos = 0;
    protected int limitBreak;

    protected Gameplay() {
    }

    /**
     * Set stat index
     *
     * @param dex
     */
    public void setStatIndex(int dex) {
        statIndex = dex;
    }

    public void setStatusPic(char who, String stat, Color c) {

        if (who == 'c' || who == 'a') {
            statusOpChar = 1.0f;
            statsPosYChar = 0;
            statIndexChar = statIndex;
        }
        if (who == 'o' || who == 'b') {
            statusOpOpp = 1.0f;
            statsPosYOpp = 0;
            statIndexOpp = statIndex;
        }
    }

    public void setNumOfBoards(int i) {
        /*
        numberOfStoryPix = i;
        storyPicArr = new VolatileImage[numberOfStoryPix];
        storyPicArrStr = new String[numberOfStoryPix];*/
    }

    public void setPicAt(int i, String s) {
        storyPicArrStr[i] = s;
    }

    /**
     * Draws Achievements
     */
    @SuppressWarnings("SleepWhileHoldingLock")
    public void drawAchievements() {
        try {
            int howMany = Achievements.getInstance().getAcievementsTriggered();
            RenderGameplay.getInstance().getGameInstance().timeOut(howMany);
            do {
                for (int u = 0; u < howMany; u++) {
                    String[] thisOne = Achievements.getInstance().getName(u);
                    ach1 = thisOne[0]; //name
                    ach2 = thisOne[1]; //desc
                    ach3 = thisOne[2]; //class
                    ach4 = thisOne[3]; //points

                    System.out.println("Triggered " + ach1 + "\n" + ach2 + "\n" + ach3 + "\n" + ach4);

                    try {
                        Thread.sleep(2300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (RenderGameplay.getInstance().getGameInstance().instance);
        } catch (Exception e) {
            //nothin
        }
    }

    /**
     * Navigate down in the menu
     */
    public void downItem() {
        if (itemindex < 3) {
            itemindex = itemindex + 1;
        } else {
            itemindex = 0;
        }

        //default size
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }

        //increase font size
        fontSizes[itemindex] = LoginScreen.bigTxtSize;
    }

    /**
     * Get the Character
     */
    protected void setCharMoveset() {
        MainWindow.getInstance().getAttacksChar().getOpponent().setCharMoveset();
    }

    /**
     * Set stats
     *
     * @param physicalS
     * @param celestiaS
     * @param itemS
     */
    public void setStats(String[] physicalS, String[] celestiaS, String[] itemS) {
        physical = physicalS;
        celestia = celestiaS;
        item = itemS;
        currentColumn = physical;
        currentCols = 0;
    }

    /**
     * Resolves column names
     */
    protected void resolveText() {
        if (currentCols == 0) {
            currentColumn = physical;
        } else if (currentCols == 1) {
            currentColumn = celestia;
        } else if (currentCols == 2) {
            currentColumn = item;
        }
    }

    /**
     * Generates strings used to execute moves
     *
     * @param input move
     */
    public String genStr(int input) {
        String thisTxt = "";

        if (input < 10) {
            thisTxt = "0" + move;
        } else {
            thisTxt = "" + move;
        }

        return thisTxt;
    }

    /**
     * Get the move selected by the player
     */
    protected String getSelMove(int move) {
        String txt = MainWindow.getInstance().getAttacksChar().getOpponent().getMoveQued(move);

        return txt;
    }

    /**
     * Get hurtChar type
     *
     * @return hurtChar type
     */
    public String getAttackType(char who) {
        String result = "";
        if (who == 'c') {
            result = attackType;
        } else if (who == 'o') {
            result = attackTYpeOpp;
        }

        return result;
    }

    /**
     * set hurtChar type, normal or fury
     */
    public void setAttackType(String type, char who) {
        if (who == 'c') {
            attackType = type;
        }

        if (who == 'o') {
            attackTYpeOpp = type;
            System.out.println("Opponent is Pissed");
        }
    }


    public ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];
        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }
        Kernel kernel = null;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    /**
     * Change the notification pic
     *
     * @param index - the index
     */
    public void setNotifiationPic(int index) {
    }


    public void clash(int dude, char homie) {
        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
            MainWindow.getInstance().sendToServer("clashing^T&T^&T&^");
        } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
            MainWindow.getInstance().sendToClient("clashing^T&T^&T&^");
        }
        if (RenderGameplay.getInstance().getBreak() == 1000 && safeToSelect && clasherRunnign == false) {
            new ClashSystem(dude, homie);
            clasherRunnign = true;
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode)) {
                oppAttack = new ClashingOpponent();
                System.out.println("clash ai");
            }
        }
    }

    public void opponetClashing() {
        ClashSystem.getInstance().oppClashing();
    }

    public void sendToClient(String message) {
        MainWindow.getInstance().sendToClient(message);
        //protected MainWindow.getInstance().NetworkClient client;
    }

    public void sendToServer(String message) {
        MainWindow.getInstance().sendToServer(message);
    }


    public String getFavChar(int here) {
        return charNames[here].name();
    }

    @Override
    public Dimension getSize() {
        return new Dimension(getGameWidth(), getGameHeight()); //16:9
    }

    /**
     * Get screen height
     *
     * @return height
     */
    public int getGameHeight() {
        return LoginScreen.getInstance().getGameHeight();
    }

    /**
     * Get screen width
     *
     * @return width
     */
    public int getGameWidth() {
        return LoginScreen.getInstance().getGameWidth();
    }

    public void shakeCharLB() {
        try {
            JenesisGamePad.getInstance().setRumbler(true, 0.4f);
        } catch (Exception e) {
        }

        for (int h = 0; h < loop1; h++) // shakes opponents LifeBar in a cool way as well as Black n White flashy Anime effect
        {
            for (int i = 0; i < loop2; i++) {
                shakeyOffsetChar = shakeyOffsetChar + 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }

            for (int i = 0; i < loop2; i++) {
                shakeyOffsetChar = shakeyOffsetChar - 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }
        }
        try {
            JenesisGamePad.getInstance().setRumbler(false, 0.0f);
        } catch (Exception e) {
        }
    }

    public void shakeOppCharLB() {
        for (int h = 0; h < loop1; h++) // shakes opponents LifeBar in a cool way as well as Black n White flashy Anime effect
        {
            for (int i = 0; i < loop2; i++) {
                shakeyOffsetOpp = shakeyOffsetOpp + 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }

            for (int i = 0; i < loop2; i++) {
                shakeyOffsetOpp = shakeyOffsetOpp - 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }
        }
    }


    /**
     * Navigate to specific location in the menu. Used by the mouse
     */
    public void thisItem(int item) {
        {
            itemindex = item;
        }
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }
        fontSizes[itemindex] = LoginScreen.bigTxtSize;
    }

    /**
     * Navigate up in the menu
     */
    public void upItem() {
        if (itemindex > 0) {
            itemindex = itemindex - 1;
        } else {
            itemindex = 3;
        }

        //default size
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }

        //increase font size
        fontSizes[itemindex] = LoginScreen.bigTxtSize;
    }


    /**
     * Checks if opponent has more slots
     * TRIGGERS ATTACKS
     */
    protected void checkStatus() {
        if (RenderGameplay.getInstance().comboCounter == 4) {
            attack();
        }
    }


    /**
     * assigns pic to array index
     */
    public void setSprites(char who, int oneA, int Magic) {
        if (who == 'c') {
            charMeleeSpriteStatus = oneA;
            charCelestiaSpriteStatus = Magic;
        }

        if (who == 'o') {
            oppMeleeSpriteStatus = oneA;
            oppCelestiaSpriteStatus = Magic;
        }

        if (who == 'a') {
            charAssSpriteStatus = oneA;
        }

        if (who == 'b') {
            oppAssSpriteStatus = oneA;
        }
    }

    /**
     * Attacks the opponent
     */
    public void attack() {
        if (numOfAttacks > 0) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.storyMode) || MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer2)) {
                RenderGameplay.getInstance().disableSelection();
                RenderGameplay.getInstance().getGameInstance().triggerCharAttack();
            } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                RenderGameplay.getInstance().comboCounter = 0;
                //clear active combos

                setSprites('c', 9, 11);
                setSprites('o', 9, 11);
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer2)) {
                    setSprites('a', 9, 11);
                    setSprites('b', 9, 11);
                }
                //RenderGameplay.getInstance().DisableMenus(); disable issueing of more attacksCombatMage during execution
                // each Mattack will check if they are in the battle que.... if they are they execute

                //broadcast hurtChar on net
                attackStr = "" + RenderGameplay.getInstance().attackArray[0] + RenderGameplay.getInstance().attackArray[1] + RenderGameplay.getInstance().attackArray[2] + RenderGameplay.getInstance().attackArray[3] + " attack";
                System.out.println(attackStr);
                client.sendData(attackStr);

                //attack on local
                RenderGameplay.getInstance().disableSelection();
                RenderGameplay.getInstance().getGameInstance().triggerCharAttack();

                if (RenderGameplay.getInstance().done != 1)// if game still running enable menus
                {
                    MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                }
            } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                RenderGameplay.getInstance().comboCounter = 0;
                //clear active combos

                setSprites('c', 9, 11);
                setSprites('o', 9, 11);
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer2)) {
                    setSprites('a', 9, 11);
                    setSprites('b', 9, 11);
                }
                //RenderGameplay.getInstance().DisableMenus(); disable issueing of more attacksCombatMage during execution
                // each Mattack will check if they are in the battle que.... if they are they execute

                //broadcast hurtChar on net
                attackStr = "" + RenderGameplay.getInstance().attackArray[0] + RenderGameplay.getInstance().attackArray[1] + RenderGameplay.getInstance().attackArray[2] + RenderGameplay.getInstance().attackArray[3] + " attack";
                System.out.println(attackStr);
                server.sendData(attackStr);

                //attack on local
                RenderGameplay.getInstance().disableSelection();
                RenderGameplay.getInstance().getGameInstance().triggerCharAttack();

                RenderGameplay.getInstance().getGameInstance().setRecoveryUnitsChar(0);
                if (RenderGameplay.getInstance().done != 1)// if game still running enable menus
                {
                    MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                }
            }
        }
    }


    public void newInstance() {
        dnladng = false;
        opponentDamageXLoc = 150;
        playerDamageXLoc = 575;
        statusOpOpp = 0.0f;
        statusOpChar = 0.0f;
        statIndexChar = 0;
        statIndexOpp = 0;
        oppBarYOffset = 435;
        paneCord = (int) (306);
        menuBarY = (int) (360);

        threadsNotRunningYet = false;
        if (WindowOptions.graphics.equalsIgnoreCase("High")) {
            specialEffect = true;
        } else {
            specialEffect = false;
        }
        currentColumn = physical;
        itemindex = 0;
        furyBarY = (int) (130);
        itemX = 215;
        itemY = (int) (360);
        dnladng = true;
    }


    /**
     * get scale of Y
     *
     * @return y scale
     */
    public float scaleY() {
        return LoginScreen.getInstance().getGameYScale();
    }

    /**
     * get scale of X
     *
     * @return X scale
     */
    public float scaleX() {
        return LoginScreen.getInstance().getGameXScale();
    }

    /**
     * Legacy code
     *
     * @param message to display
     * @return integer
     */
    public int showConfirmMessage(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Hey There", JOptionPane.YES_NO_CANCEL_OPTION);
    }

    /**
     * Gets the damage multiplier
     *
     * @param who - which character
     * @return damage multiplier
     */
    public int getDamageDealt(char who) {

        if (who == 'c') {
            thisInt = damageC;
        }

        if (who == 'o') {
            thisInt = damageO;
        }

        return thisInt;
    }

    /**
     * Set player 1 life
     *
     * @param Life - value
     */
    public void setLife(int Life) {
        life = Life;
    }

    /**
     * Set player 1 maximum life
     *
     * @param Life - value
     */
    public void setMaxLife(int Life) {
        maXlife = Life;
    }

    public boolean isGameRunning() {
        return fpsGen != null;
    }
    //------------- end action listers -------------
    //------------- start methods ------------------

    /**
     * Gets the games current version
     *
     * @return version
     */
    public String getVersionStr() {
        return versionString;
    }

    /**
     * Gets the games current version
     *
     * @return version
     */
    public int getVersionInt() {
        return versioInt;
    }

    /**
     * Legacy awesomeness
     *
     * @return is effect on?
     */
    public boolean isFancyEffect() {
        {
            if (fancyBWAnimeEffect == 1) {
                fancyBWAnimeEffectEnabled = true;
            }

            if (fancyBWAnimeEffect != 1) {
                fancyBWAnimeEffectEnabled = false;
            }
        }

        return fancyBWAnimeEffectEnabled;
    }

    /**
     * Get the character multiplier
     *
     * @return the damage multiplier
     */
    public int getDamageMultiplierChar() {
        return RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getDamageMultiplier();
    }

    /**
     * Get the opponent multiplier
     *
     * @return the damage multiplier
     */
    public int getDamageMultiplierOpp() {
        return RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getDamageMultiplier();
    }


    /**
     * 13 / Sept /2010
     * 16:24
     * <p>
     * be strong
     * love what you do
     * passion
     * expertice
     * ============Ultra High Definition=====================
     * 1980x1080 standard HD
     * UHD developed by NHK
     * 8000x4360, 24GiB/s compressed to 1.GiBs, encoded 350MiB/s
     */
    public void killGameInstance() {
        //fpsGen=null;
    }


    /**
     * Get scale Y
     *
     * @return Y's scale
     */
    public float getscaleY() {
        return LoginScreen.getInstance().getGameYScale();
    }

    /**
     * Get scale Y
     *
     * @return Y's scale
     */
    public float getscaleX() {
        return LoginScreen.getInstance().getGameXScale();
    }

    /**
     * Determines if match has reached game over state
     */
    public void matchStatus() {
        if (gameOver == false) {
            if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer2)) {
                if ((oppLife + oppLife2) < 0 || (life + charLife3) < 0 || (getGameInstance().time <= 0 && getGameInstance().time <= 180)) {
                    if ((float) (oppLife + oppLife2) / (float) (oppMaxLife + oppMaxLife2) > (float) (life + charLife3) / (float) (maXlife + charMaxLife3) || (float) (oppLife + oppLife2) / (float) (oppMaxLife + oppMaxLife2) < (float) (life + charLife3) / (float) (charMaxLife3 + maXlife)) {
                        getGameInstance().gameOver();
                    }
                }
            } else if (oppLife < 0 || life < 0 || (ThreadGameInstance.time <= 0 && ThreadGameInstance.time <= 180)) {
                if ((float) oppLife / (float) oppMaxLife > (float) life / (float) maXlife || (float) oppLife / (float) oppMaxLife < (float) life / (float) maXlife) {
                    getGameInstance().gameOver();

                }
            }
            //save life at gameover
            finalOppLife = (float) oppLife / (float) oppMaxLife;
            finalCharLife = (float) life / (float) maXlife;
        }
    }


    /**
     * Get opponent 1 life
     *
     * @return value
     */
    public float getOppLife() {
        return (float) oppLife;
    }

    /**
     * Set opponent 1 life
     *
     * @param Life - value
     */
    public void setOppLife(int Life) {
        oppLife = Life;
    }

    /**
     * Get opponent 1's maximum life
     *
     * @return value
     */
    public float getOppMaxLife() {
        return (float) oppMaxLife;
    }

    /**
     * Set opponent 1's maximum life
     *
     * @param Life
     */
    public void setOppMaxLife(int Life) {
        oppMaxLife = Life;
    }

    /**
     * Get opponent 2's current life
     *
     * @return value
     */
    public float getOppLife2() {
        return (float) oppLife2;
    }

    /**
     * Set opponent 2's current life
     *
     * @param Life
     */
    public void setOppLife2(int Life) {
        oppLife2 = Life;
    }

    /**
     * Get opponent 2's maximum life
     *
     * @return value
     */
    public float getOppMaxLife2() {
        return (float) oppMaxLife2;
    }

    /**
     * Set opponent 2's maximum life
     *
     * @param Life
     */
    public void setOppMaxLife2(int Life) {
        oppMaxLife2 = Life;
    }

    /**
     * Get player 2's current life
     *
     * @return value
     */
    public float getCharLife3() {
        return (float) charLife3;
    }

    /**
     * Set player 2's current life
     *
     * @param Life
     */
    public void setCharLife3(int Life) {
        charLife3 = Life;
    }

    /**
     * Get player 2's maximum life
     *
     * @return value
     */
    public float getCharMaxLife3() {
        return (float) charMaxLife3;
    }

    /**
     * Set player 2's maximum life
     *
     * @param Life
     */
    public void setCharMaxLife3(int Life) {
        charMaxLife3 = Life;
    }


    /**
     * Resets the game after a match is done or cancelled
     */
    public void resetGame() {
        life = maXlife;
        oppLife = oppMaxLife;
        limitBreak = 5;
        RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().setDamageMultiplier(Characters.getDamageMultiplier('c'));
        RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().setCelestiaMultiplier(10);
        RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().setCelestiaMultiplier(10);
        RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().setDamageMultiplier(Characters.getDamageMultiplier('o'));
    }

    /**
     * Slow down game
     *
     * @param amount - time duration
     */
    public void slowDown(int amount) {
        getGameInstance().sleepy(amount);
    }

    /**
     * Starts an actual fight
     */
    public void startFight() {
        resetGame();
        fpsGen = new ThreadGameInstance(1, this);
        startDrawing = 1;
        comboCounter = 0;
        MainWindow.getInstance().setGameRunning();
        perCent = 100;
        perCent2 = 100;
        MainWindow.getInstance().reSize("game");
    }

    public ThreadGameInstance getGameInstance() {
        return fpsGen;
    }

    /**
     * Increment combo count
     */
    public void incrimentComboCounter() {
        comboCounter = comboCounter + 1;
    }

    /**
     * Remove move from que
     */
    public void unQueMove() {
        if (comboCounter >= 1 && safeToSelect) {
            //change curent index
            comboCounter = comboCounter - 1;
            int moi = Integer.parseInt(attackArray[comboCounter]);
            Characters.alterPoints2(moi);
            System.out.println("UNQUED " + moi);
        }
    }

    /**
     * Scan que for desired attacks
     *
     * @param desiredAttack - id
     * @return status
     */
    public boolean scanPhyQue(int desiredAttack) {
        isMoveQued = false;
        for (int x = 0; x <= 3; x++) //loops 4 times
        {
            if (Integer.parseInt(attackArray[x]) == desiredAttack) {
                isMoveQued = true;
            }
        }
        return isMoveQued;
    }

    /**
     * update Player 1 life
     *
     * @param thisMuch - value
     */
    public void updatePlayerLife(int thisMuch) {
        int thisMuch2 = RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getCelestiaMultiplier() * thisMuch;
        life = life + thisMuch2;
        daNum = ((getCharLife() / getCharMaxLife()) * 100); //perc life x life bar length
        lifePlain = Math.round(daNum); // round off
        lifeTotalPlain = Math.round(getCharLife()); // for text
        perCent = Math.round(lifePlain);
        Characters.setCurrLifeOpp(perCent2);
        Characters.setCurrLifeChar(perCent);
    }

    /**
     * update opponent 1 life
     *
     * @param thisMuch - value
     */
    public void updateOpponentLife(int thisMuch) {
        int thisMuch2 = RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getCelestiaMultiplier() * thisMuch;
        oppLife = oppLife + thisMuch2;
        daNum2 = ((getOppLife() / getOppMaxLife()) * 100); //perc life x life bar length
        lifePlain2 = Math.round(daNum2); // round off
        lifeTotalPlain2 = Math.round(getOppLife()); // for text
        perCent2 = Math.round(lifePlain2);
        Characters.setCurrLifeOpp(perCent2);
        Characters.setCurrLifeChar(perCent);
    }

    /**
     * update opponent 2 life
     *
     * @param thisMuch - value
     */
    public void updateAssistantOpponentLife(int thisMuch) {
        int thisMuch2 = RenderCharacterSelectionScreen.getInstance().getPlayers().getAssistOpponent().getCelestiaMultiplier() * thisMuch;
        oppLife2 = oppLife2 + thisMuch2;
        daNum2a = ((getOppLife2() / getOppMaxLife2()) * 100); //perc life x life bar length
        lifePlain2a = Math.round(daNum2a); // round off
        lifeTotalPlain2a = Math.round(getOppLife2()); // for text
        Characters.setCurrLifeChar2(perCent3a);
        Characters.setCurrLifeChar(perCent);
        Characters.setCurrLifeOpp2(perCent2a);
        Characters.setCurrLifeOpp(perCent2);
    }

    /**
     * update Player 2 life
     *
     * @param thisMuch - value
     */
    public void updateAssistantCharacterLife(int thisMuch) {
        int thisMuch2 = RenderCharacterSelectionScreen.getInstance().getPlayers().getAssistCharacter().getCelestiaMultiplier() * thisMuch;
        charLife3 = charLife3 + thisMuch2;
        daNum3a = ((getCharLife3() / getCharMaxLife3()) * 100); //perc life x life bar length
        lifePlain2a = Math.round(daNum3a); // round off
        lifeTotalPlain2a = Math.round(getCharLife3()); // for text
        perCent3a = Math.round(lifePlain3a);
        Characters.setCurrLifeChar2(perCent3a);
        Characters.setCurrLifeChar(perCent);
        Characters.setCurrLifeOpp2(perCent2a);
        Characters.setCurrLifeOpp(perCent2);
    }

    /**
     * Get the Character life, these methods should be float as they are used in divisions
     *
     * @return Character life
     */
    public float getCharLife() {
        return (float) life;
    }

    /**
     * Get the Character max life, these methods should be float as they are used in divisions
     *
     * @return Character maximum life
     */
    public float getCharMaxLife() {
        return (float) maXlife;
    }

    /**
     * Resume paused game
     */
    public void start() {
        fpsGen.resumeGame();
    }

    /**
     * Alter damage multipliers, used to strengthen/weaken attacks
     *
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public void alterDamageCounter(char per, int thisMuch) {
        if (per == 'c' && RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getDamageMultiplier() > 0 && RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getDamageMultiplier() < 20) {
            RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().setDamageMultiplier(RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getDamageMultiplier() + thisMuch);
        }

        if (per == 'o' && RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getDamageMultiplier() > 0 && RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getDamageMultiplier() < 20) {
            RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().setDamageMultiplier(RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getDamageMultiplier() + thisMuch);
        }
    }

    /**
     * Alter celestia multipliers, used to strengthen/weaken celestia attacks
     *
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public void alterCelestiaCounter(char per, int thisMuch) {
        if (per == 'c' && RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getCelestiaMultiplier() > 0 && RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getCelestiaMultiplier() < 16) {
            RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().setCelestiaMultiplier(RenderCharacterSelectionScreen.getInstance().getPlayers().getOpponent().getCelestiaMultiplier() + thisMuch);
        }

        if (per == 'o' && RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getCelestiaMultiplier() > 0 && RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getCelestiaMultiplier() < 16) {
            RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().setCelestiaMultiplier(RenderCharacterSelectionScreen.getInstance().getPlayers().getCharacter().getCelestiaMultiplier() + thisMuch);
        }
    }

    /**
     * Determines if the player has won
     *
     * @return match status
     */
    public boolean hasWon() {
        return finalCharLife > finalOppLife;
    }


    /**
     * Increment limit
     */
    private void incLImit(int ThisMuch) {
        final int inc = ThisMuch;
        new Thread() {
            //add one, make sure we dont go over 2000

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                int icrement = inc;
                if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.singlePlayer2)) {
                    icrement = icrement / 2;
                }
                this.setName("Fury bar increment stage");
                for (int o = 0; o < icrement; o++) {
                    if (limitBreak < limitTop) {
                        try {
                            limitBreak = limitBreak + 1;
                            this.sleep(15);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(RenderGameplay.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }.start();
    }

    public void triggerFury(char who) {
        limitBreak(who);
    }


    /**
     * Sets limit back to initial value
     */
    public void resetBreak() {
        limitBreak = 5;
    }

    /**
     * limit break, wee!!!
     */
    public void limitBreak(char who) {
        dude = who;
        new Thread() {

            @Override
            public void run() {
                if (getBreak() == 1000) {
                    //&& getGameInstance().getRecoveryUnitsChar()>289
                    //runs on local
                    if (dude == 'c' && limitRunning && getGameInstance().getRecoveryUnitsChar() > 289) {
                        limitRunning = false;

                        //broadcast on net
                        if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanClient)) {
                            MainWindow.getInstance().sendToServer("limt_Break_Oxodia_Ownz");
                        } else if (MainWindow.getInstance().getGameMode().equalsIgnoreCase(MainWindow.lanHost)) {
                            MainWindow.getInstance().sendToClient("limt_Break_Oxodia_Ownz");
                        }
                        setAttackType("fury", 'c');
                        comboCounter = 0;
                        getGameInstance().pauseActivityRegen();
                        getGameInstance().setRecoveryUnitsChar(0);
                        try {
                            JenesisGamePad.getInstance().setRumbler(true, 0.8f);
                        } catch (Exception e) {
                        }
                        for (int i = 1; i < 9; i++) {
                            //stop attacking when game over
                            if (getGameInstance().isGameOver == false) {
                                furySound();
                                hurtSoundOpp();
                                MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                                setSprites('c', i, 11);
                                setSprites('a', i, 11);
                                setSprites('o', 0, 11);
                                shakeOppCharLB();
                                comboPicArrayPosOpp = i;
                                furyComboOpacity = 1.0f;
                                lifePhysUpdateSimple(2, 100, "");
                            }
                        }
                        MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
                        try {
                            JenesisGamePad.getInstance().setRumbler(false, 0.0f);
                        } catch (Exception e) {
                        }
                        comboPicArrayPosOpp = 8;
                        getGameInstance().resumeActivityRegen();
                        setSprites('c', 9, 11);
                        setSprites('o', 9, 11);
                        setSprites('a', 11, 11);
                        limitRunning = true;
                        resetBreak();
                        setAttackType("normal", 'c');
                    } else if (dude == 'o' && limitRunning && getGameInstance().getRecoveryUnitsOpp() > 289) {
                        setAttackType("fury", 'o');
                        limitRunning = false;
                        try {
                            JenesisGamePad.getInstance().setRumbler(true, 0.8f);
                        } catch (Exception e) {
                        }
                        for (int i = 1; i < 9; i++) {
                            if (getGameInstance().isGameOver == false) {
                                MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
                                furySound();
                                hurtSoundChar();
                                getGameInstance().setRecoveryUnitsOpp(0);
                                setSprites('o', i, 11);
                                setSprites('b', i, 11);
                                setSprites('c', 0, 11);
                                shakeCharLB();
                                comboPicArrayPosOpp = i;
                                lifePhysUpdateSimple(1, 100, "");
                            }
                        }
                        MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                        try {
                            JenesisGamePad.getInstance().setRumbler(false, 0.0f);
                        } catch (Exception e) {
                        }
                        comboPicArrayPosOpp = 8;
                        setSprites('o', 9, 11);
                        setSprites('c', 9, 11);
                        setSprites('b', 11, 11);
                        limitRunning = true;
                        resetBreak();
                        setAttackType("normal", 'o');
                    }
                }
            }
        }.start();
    }

    /**
     * Updates the life of Character
     *
     * @param forWho   - the person affected
     * @param ThisMuch - the life to add/subtract
     * @param attacker - who inflicted damage
     */
    public void lifePhysUpdateSimple(int forWho, int ThisMuch, String attacker) {

        if (forWho == 1) //Attack from player
        {
            damageChar = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageChar);
            guiScreenChaos(ThisMuch * getDamageMultiplierOpp(), 'o');
            for (int m = 0; m < damageChar; m++) {
                if (life >= 0) {
                    life = life - (1 * getDamageMultiplierOpp());
                }
            }
            daNum = ((getCharLife() / getCharMaxLife()) * 100); //perc life x life bar length
            lifePlain = Math.round(daNum); // round off
            lifeTotalPlain = Math.round(getCharLife()); // for text
            perCent = Math.round(lifePlain);
        }

        if (forWho == 2) //Attack from CPU pponent 1
        {
            damageOpp = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageOpp);
            guiScreenChaos(ThisMuch * getDamageMultiplierChar(), 'c');
            for (int m = 0; m < damageOpp; m++) {
                if (oppLife >= 0) {
                    oppLife = oppLife - (1 * getDamageMultiplierOpp());
                }
            }
            daNum2 = ((getOppLife() / getOppMaxLife()) * 100); //perc life x life bar length
            lifePlain2 = Math.round(daNum2); // round off
            lifeTotalPlain2 = Math.round(getOppLife()); // for text
            perCent2 = Math.round(lifePlain2);
        }

        if (forWho == 4) //Attack from CPU opponent 2
        {
            damageOpp2 = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageOpp2);
            guiScreenChaos(ThisMuch * getDamageMultiplierChar(), 'a');
            for (int m = 0; m < damageOpp2; m++) {
                if (oppLife2 >= 0) {
                    oppLife2 = oppLife2 - (1 * getDamageMultiplierOpp());
                }
            }
            daNum2a = ((getOppLife2() / getOppMaxLife2()) * 100); //perc life x life bar length
            lifePlain2a = Math.round(daNum2a); // round off
            lifeTotalPlain2a = Math.round(getOppLife2()); // for text
            perCent2a = Math.round(lifePlain2a);
        }

        if (forWho == 3) //Attack from CPU player 2
        {
            damageChar2 = ThisMuch;
            ActivePerson = attacker;
            incLImit(damageChar2);
            guiScreenChaos(ThisMuch * getDamageMultiplierOpp(), 'b');
            for (int m = 0; m < damageChar2; m++) {
                if (charLife3 >= 0) {
                    charLife3 = charLife3 - (1 * getDamageMultiplierOpp());
                }
            }
            daNum3a = ((getCharLife3() / getCharMaxLife3()) * 100); //perc life x life bar length
            lifePlain3a = Math.round(daNum3a); // round off
            lifeTotalPlain3a = Math.round(getCharLife3()); // for text
            perCent3a = Math.round(lifePlain3a);
        }
    }

    public int getPerCent3a() {
        return perCent3a;
    }

    /**
     * Get the break status
     *
     * @return break status
     */
    public int getBreak() {
        return limitBreak;
    }

    protected abstract void guiScreenChaos(float damageAmount, char who);

    protected abstract void furySound();

    protected abstract void hurtSoundChar();

    protected abstract void hurtSoundOpp();

    protected abstract void attackSoundOpp();

    public String getAnimDirection() {
        return animDirection;
    }

    public void setBgLocation(String location) {
        bgLocation = location;
    }

    public boolean isDnladng() {
        return dnladng;
    }

    public int getPercent() {
        return perCent;
    }

    public int getPercent2() {
        return perCent2;
    }

    public void setClasherRunnign(boolean value) {
        clasherRunnign = value;
    }

    public int getCharYcord() {
        return charYcord;
    }

    public int getOppYcord() {
        return oppYcord;
    }

    public void setCharYcord(int value) {
        charYcord = value;
    }

    public void setOppYcord(int value) {
        oppYcord = value;
    }

    public int getPercent2a() {
        return perCent2a;
    }

    public int getAnimLoops() {
        return animLoops;
    }

    public long getDelay() {
        return delay;
    }

    public void setFgx(int fgx) {
        this.fgx = fgx;
    }

    public void setFgy(int fgy) {
        this.fgy = fgy;
    }

    public int getFgx() {
        return fgx;
    }

    public int getFgy() {
        return fgy;
    }

    public int getFgxInc() {
        return fgxInc;
    }

    public int getFgyInc() {
        return fgyInc;
    }

    public void setComboCounter(int comboCounter) {
        this.comboCounter = comboCounter;
    }

    public int getComboCounter() {
        return comboCounter;
    }

    public String[] getAttackArray() {
        return attackArray;
    }

    public int getNumOfAttacks() {
        return numOfAttacks;
    }

    public void setNumOfAttacks(int numOfAttacks) {
        this.numOfAttacks = numOfAttacks;
    }

    public int getDone() {
        return done;
    }

    public boolean getClasherRunnign() {
        return clasherRunnign;
    }

    public Character[] getCharNames() {
        return charNames;
    }

    public String getVerticalMove() {
        return verticalMove;
    }

    public int getAmbSpeed1() {
        return ambSpeed1;
    }

    public int getAmbSpeed2() {
        return ambSpeed2;
    }

    public int getAmb1x() {
        return amb1x;
    }

    public int getAmb2x() {
        return amb2x;
    }

    public int getAmb1y() {
        return amb1y;
    }

    public int getAmb2y() {
        return amb2y;
    }

    public void setAmb1x(int amb1x) {
        this.amb1x = amb1x;
    }

    public void setAmb2x(int amb2x) {
        this.amb2x = amb2x;
    }

    public void setAmb1y(int amb1y) {
        this.amb1y = amb1y;
    }

    public void setAmb2y(int amb2y) {
        this.amb2y = amb2y;
    }
}