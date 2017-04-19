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
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.network.NetworkClient;
import com.scndgen.legends.network.NetworkServer;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.threads.ClashSystem;
import com.scndgen.legends.threads.ClashingOpponent;
import com.scndgen.legends.threads.GameInstance;
import com.scndgen.legends.windows.MainWindow;
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
    protected String activePerson; // person who performed an attack, name shall show in battle info status area
    protected int perCent = 100, perCent2 = 100;
    protected com.scndgen.legends.characters.Characters selectedChar, selectedOpp;
    protected int done = 0; // if gameover
    protected String[] attackArray = new String[8];//up to 8 moves can be qued
    protected int comboCounter = 0; //must be negative one to reach index 0, app wide counter, enable you to que attacks of different kinds
    protected boolean threadsNotRunningYet = true, playATBFile = false;
    protected StringBuilder StatusText = new StringBuilder();
    protected int startDrawing = 0, menuBarY;
    protected float angRot = 20;
    protected int charXcord = 10, charYcord = 10, oppYcord = 10, statIndex = 0;
    protected int particlesLayer1PositionX = 0, particlesLayer1PositionY = 0, particlesLayer2PositionX = 0, particlesLayer2PositionY = 0;
    protected int numOfComicPics = 9;
    protected int foreGroundPositionX, foreGroundPositionY, fgxInc, fgyInc, animLoops, delay;
    protected String animDirection = "vert", verticalMove = "no";
    protected int oppXcord = 10;
    protected int playerDamageXLoc, opponentDamageXLoc, numOfAttacks = 0;
    protected String scenePic = "images/bgBG2.png";
    protected String attackPicSrc = "images/trans.png";
    protected String[] storyPicArrStr;
    protected CharacterEnum[] charNames = LoginScreen.charNames;
    protected String attackPicOppSrc = "images/trans.png";
    protected int ambSpeed1, ambSpeed2, paneCord;
    protected StringBuilder battleInformation = new StringBuilder("");
    protected int count = 0, fpsInt = 0, fpsIntStat;
    protected String[] physical, celestia, item, special, current;
    protected String[] currentColumn;
    protected int currentColumnIndex = 0;
    protected boolean safeToSelect = true;
    protected String animLayer = "";
    protected boolean clasherRunnign = false, dnladng;
    protected boolean loadedUpdaters;
    protected float daNum, daNum2;
    protected long lifePlain, lifeTotalPlain, lifePlain2, lifeTotalPlain2;
    protected int fancyBWAnimeEffect = 0;     //toggle fancy effect when HP low
    protected boolean fancyBWAnimeEffectEnabled;
    protected boolean isMoveQued, gameOver;
    protected int thisInt; //max damage that can be dealt by Celestia Physics
    protected int damageC, damageO;
    protected int life, maXlife, oppLife, oppMaxLife;
    protected int damageChar, damageOpp;
    protected int limitTop = 1000;
    protected String versionString = " 2K17 RMX";
    protected int versioInt = 20120630; // yyyy-mm-dd
    protected float finalOppLife, finalCharLife;
    protected Object source;
    protected int shakingChar = 0033, loop1 = 3, loop2 = 4;
    protected int x2 = 560, comX = 380, comY = 100;
    protected int xLocal = 470;
    protected int y2 = 435;
    protected int statIndexOpp, statIndexChar, statusEffectCharacterYCoord, statusEffectOpponentYCoord, uiShakeEffectOffsetCharacter = 1, uiShakeEffectOffsetOpponent = 1, basicY = 0;
    protected boolean shaky1 = true;
    protected ClashingOpponent oppAttack = null;
    protected int animTime = 400, itemX = 0, itemY = 0;
    protected boolean runNew = true, effectChar = false;
    protected int bgX = 0;
    protected int numberOfStoryPix, lbx2 = 500;
    protected int lby2 = 420;
    protected String characterAttackType = "normal", opponentAttackType = "normal", statusChar = "", statusOpp = "";
    protected int charMeleeSpriteStatus = 9, oppMeleeSpriteStatus = 9, charCelestiaSpriteStatus = 11, oppCelestiaSpriteStatus = 11;
    protected float statusEffectCharacterOpacity, statusEffectOpponentOpacity;
    protected int itemIndex = 0, furyBarY = 0;
    @SuppressWarnings("StaticNonFinalUsedInInitialization")
    protected int[] fontSizes = {LoginScreen.bigTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize, LoginScreen.normalTxtSize};
    protected int attackInt = 0;
    protected String attackStr;
    protected boolean lagFactor = true;
    protected float currentXShear = 0, currentYShear = 0;
    protected boolean isFree = true, isFree2 = true;
    protected CharacterState characterState;
    protected String sysNot = "";
    protected float sysNotOpac = 0, sysNotOpacInc = (float) 0.1;
    protected String achievementName = "", achievementDescription = "", achievementClass = "", achievementPoints = "";
    protected int move = 0;
    protected NetworkServer server;
    protected NetworkClient client;
    protected int InfoBarYPose, spacer = 27, randSoundIntChar, randSoundIntOpp, randSoundIntOppHurt, randSoundIntCharHurt, YOffset = 15;
    protected int x = 2;
    protected int oppBarYOffset, leftHandXAxisOffset, y = 0;
    protected float opacityTxt = 10, opacityPic = 0.0f;
    protected boolean limitRunning = true, animCharFree = true;
    protected float angleRaw, charPointInc;
    protected int result;
    protected float opponentDamageOpacity, playerDamageOpacity, comicBookTextOpacity, furyComboOpacity;
    protected int comboPicArrayPosOpp = 8;
    protected String manipulateThis;
    protected int one, two, three, four, oneO, twoO, threeO, fourO;
    protected int comicBookTextPositionY, opponentDamageYLoc, playerDamageYCoord, yTEST = 25, yTESTinit = 25;
    protected float opac = 1.0f;
    protected float damageLayerOpacity;
    protected boolean nextEnabled = true, backEnabled = true;
    protected int charOp = 10, comicBookTextIndex = 0;
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

    public void setStatusPic(CharacterState who, String stat, Color c) {

        if (who == CharacterState.CHARACTER) {
            statusEffectCharacterOpacity = 1.0f;
            statusEffectCharacterYCoord = 0;
            statIndexChar = statIndex;
        }
        if (who == CharacterState.OPPONENT) {
            statusEffectOpponentOpacity = 1.0f;
            statusEffectOpponentYCoord = 0;
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
            GameInstance.getInstance().setTimeOut(howMany);
            do {
                for (int u = 0; u < howMany; u++) {
                    String[] achievementInfo = Achievements.getInstance().getName(u);
                    achievementName = achievementInfo[0]; //name
                    achievementDescription = achievementInfo[1]; //desc
                    achievementClass = achievementInfo[2]; //class
                    achievementPoints = achievementInfo[3]; //points
                    System.out.println("Triggered " + achievementName + "\n" + achievementDescription + "\n" + achievementClass + "\n" + achievementPoints);

                    try {
                        Thread.sleep(2300);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } while (GameInstance.getInstance().isRunning);
        } catch (Exception e) {
            //nothin
        }
    }

    /**
     * Navigate down in the menu
     */
    public void downItem() {
        if (itemIndex < 3) {
            itemIndex = itemIndex + 1;
        } else {
            itemIndex = 0;
        }

        //default size
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }

        //increase font size
        fontSizes[itemIndex] = LoginScreen.bigTxtSize;
    }

    /**
     * Get the CharacterEnum
     */
    protected void setCharMoveset() {
        MainWindow.getInstance().getAttacksChar().getOpponent().setCharMoveset();
    }

    /**
     * Set STATS
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
        currentColumnIndex = 0;
    }

    /**
     * Resolves column names
     */
    protected void resolveText() {
        if (currentColumnIndex == 0) {
            currentColumn = physical;
        } else if (currentColumnIndex == 1) {
            currentColumn = celestia;
        } else if (currentColumnIndex == 2) {
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
    public String getAttackType(CharacterState who) {
        String result = "";
        if (who == CharacterState.CHARACTER) {
            result = characterAttackType;
        } else if (who == CharacterState.OPPONENT) {
            result = opponentAttackType;
        }

        return result;
    }

    /**
     * set hurtChar type, normal or fury
     */
    public void setAttackType(String type, CharacterState who) {
        if (who == CharacterState.CHARACTER) {
            characterAttackType = type;
        }
        if (who == CharacterState.OPPONENT) {
            opponentAttackType = type;
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


    public void clash(int dude, CharacterState homie) {
        if (MainWindow.getInstance().getGameMode() == SubMode.LAN_CLIENT) {
            MainWindow.getInstance().sendToServer("clashing^T&T^&T&^");
        } else if (MainWindow.getInstance().getGameMode() == SubMode.LAN_HOST) {
            MainWindow.getInstance().sendToClient("clashing^T&T^&T&^");
        }
        if (RenderGameplay.getInstance().getBreak() == 1000 && safeToSelect && clasherRunnign == false) {
            new ClashSystem(dude, homie);
            clasherRunnign = true;
            if (MainWindow.getInstance().getGameMode() == SubMode.SINGLE_PLAYER || MainWindow.getInstance().getGameMode() == SubMode.STORY_MODE) {
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

        for (int h = 0; h < loop1; h++) // shakes opponents LifeBar in a cool way as isWithinRange as Black n White flashy Anime effect
        {
            for (int i = 0; i < loop2; i++) {
                uiShakeEffectOffsetCharacter = uiShakeEffectOffsetCharacter + 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }

            for (int i = 0; i < loop2; i++) {
                uiShakeEffectOffsetCharacter = uiShakeEffectOffsetCharacter - 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }
        }
        try {
            JenesisGamePad.getInstance().setRumbler(false, 0.0f);
        } catch (Exception e) {
        }
    }

    public void shakeOppCharLB() {
        for (int h = 0; h < loop1; h++) // shakes opponents LifeBar in a cool way as isWithinRange as Black n White flashy Anime effect
        {
            for (int i = 0; i < loop2; i++) {
                uiShakeEffectOffsetOpponent = uiShakeEffectOffsetOpponent + 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }

            for (int i = 0; i < loop2; i++) {
                uiShakeEffectOffsetOpponent = uiShakeEffectOffsetOpponent - 1;
                RenderGameplay.getInstance().slowDown(shakingChar);
            }
        }
    }


    /**
     * Navigate to specific location in the menu. Used by the mouse
     */
    public void thisItem(int item) {
        {
            itemIndex = item;
        }
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }
        fontSizes[itemIndex] = LoginScreen.bigTxtSize;
    }

    /**
     * Navigate up in the menu
     */
    public void upItem() {
        if (itemIndex > 0) {
            itemIndex = itemIndex - 1;
        } else {
            itemIndex = 3;
        }

        //default size
        for (int u = 0; u < fontSizes.length; u++) {
            fontSizes[u] = LoginScreen.normalTxtSize;
        }

        //increase font size
        fontSizes[itemIndex] = LoginScreen.bigTxtSize;
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
    public void setSprites(CharacterState characterState, int oneA, int Magic) {
        if (characterState == CharacterState.CHARACTER) {
            charMeleeSpriteStatus = oneA;
            charCelestiaSpriteStatus = Magic;
        }
        if (characterState == CharacterState.OPPONENT) {
            oppMeleeSpriteStatus = oneA;
            oppCelestiaSpriteStatus = Magic;
        }
    }

    /**
     * Attacks the opponent
     */
    public void attack() {
        if (numOfAttacks > 0) {
            if (MainWindow.getInstance().getGameMode() == SubMode.SINGLE_PLAYER || MainWindow.getInstance().getGameMode() == SubMode.STORY_MODE) {
                RenderGameplay.getInstance().disableSelection();
                GameInstance.getInstance().triggerCharAttack();
            } else if (MainWindow.getInstance().getGameMode() == SubMode.LAN_CLIENT) {
                RenderGameplay.getInstance().comboCounter = 0;
                //clear active combos
                setSprites(CharacterState.CHARACTER, 9, 11);
                setSprites(CharacterState.OPPONENT, 9, 11);
                //broadcast hurtChar on net
                attackStr = "" + RenderGameplay.getInstance().attackArray[0] + RenderGameplay.getInstance().attackArray[1] + RenderGameplay.getInstance().attackArray[2] + RenderGameplay.getInstance().attackArray[3] + " attack";
                System.out.println(attackStr);
                client.sendData(attackStr);
                //attack on local
                RenderGameplay.getInstance().disableSelection();
                GameInstance.getInstance().triggerCharAttack();
                if (RenderGameplay.getInstance().done != 1)// if game still running enable menus
                    MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
            } else if (MainWindow.getInstance().getGameMode() == SubMode.LAN_HOST) {
                RenderGameplay.getInstance().comboCounter = 0;
                //clear active combos
                setSprites(CharacterState.CHARACTER, 9, 11);
                setSprites(CharacterState.OPPONENT, 9, 11);
                //broadcast hurtChar on net
                attackStr = "" + RenderGameplay.getInstance().attackArray[0] + RenderGameplay.getInstance().attackArray[1] + RenderGameplay.getInstance().attackArray[2] + RenderGameplay.getInstance().attackArray[3] + " attack";
                System.out.println(attackStr);
                server.sendData(attackStr);
                //attack on local
                RenderGameplay.getInstance().disableSelection();
                GameInstance.getInstance().triggerCharAttack();
                GameInstance.getInstance().setRecoveryUnitsChar(0);
                if (RenderGameplay.getInstance().done != 1)// if game still running enable menus
                    MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
            }
        }
    }


    public void newInstance() {
        loadAssets = true;
        dnladng = false;
        opponentDamageXLoc = 150;
        playerDamageXLoc = 575;
        statusEffectOpponentOpacity = 0.0f;
        statusEffectCharacterOpacity = 0.0f;
        statIndexChar = 0;
        statIndexOpp = 0;
        oppBarYOffset = 435;
        paneCord = 306;
        menuBarY = 360;
        threadsNotRunningYet = false;
        currentColumn = physical;
        itemIndex = 0;
        furyBarY = 130;
        itemX = 215;
        itemY = 360;
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
     * @param who - which characterEnum
     * @return damage multiplier
     */
    public int getDamageDealt(CharacterState who) {
        if (who == CharacterState.CHARACTER) {
            thisInt = damageC;
        }
        if (who == CharacterState.OPPONENT) {
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
     * Get the characterEnum multiplier
     *
     * @return the damage multiplier
     */
    public int getDamageMultiplierChar() {
        return Characters.getInstance().getCharacter().getDamageMultiplier();
    }

    /**
     * Get the opponent multiplier
     *
     * @return the damage multiplier
     */
    public int getDamageMultiplierOpp() {
        return Characters.getInstance().getOpponent().getDamageMultiplier();
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
            if (oppLife < 0 || life < 0 || (GameInstance.getInstance().time <= 0 && GameInstance.getInstance().time <= 180)) {
                if ((float) oppLife / (float) oppMaxLife > (float) life / (float) maXlife || (float) oppLife / (float) oppMaxLife < (float) life / (float) maXlife) {
                    GameInstance.getInstance().gameOver();
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
     * Resets the game after a match is done or cancelled
     */
    public void resetGame() {
        life = maXlife;
        oppLife = oppMaxLife;
        limitBreak = 5;
        Characters.getInstance().getCharacter().setDamageMultiplier(Characters.getInstance().getDamageMultiplier(CharacterState.CHARACTER));
        Characters.getInstance().getOpponent().setDamageMultiplier(Characters.getInstance().getDamageMultiplier(CharacterState.OPPONENT));
    }

    /**
     * Slow down game
     *
     * @param amount - time duration
     */
    public void slowDown(int amount) {
        GameInstance.getInstance().sleepy(amount);
    }

    /**
     * Starts an actual fight
     */
    public void startFight() {
        resetGame();
        GameInstance.getInstance().newInstance();
        startDrawing = 1;
        comboCounter = 0;
        MainWindow.getInstance().setGameRunning();
        perCent = 100;
        perCent2 = 100;
        MainWindow.getInstance().reSize("game");
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
            Characters.getInstance().alterPoints2(moi);
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
     * update CharacterState 1 life
     *
     * @param thisMuch - value
     */
    public void updatePlayerLife(int thisMuch) {
        int thisMuch2 = Characters.getInstance().getCharacter().getCelestiaMultiplier() * thisMuch;
        life = life + thisMuch2;
        daNum = ((getCharLife() / getCharMaxLife()) * 100); //perc life x life bar length
        lifePlain = Math.round(daNum); // round off
        lifeTotalPlain = Math.round(getCharLife()); // for text
        perCent = Math.round(lifePlain);
        Characters.getInstance().setCurrLifeOpp(perCent2);
        Characters.getInstance().setCurrLifeChar(perCent);
    }

    /**
     * update opponent 1 life
     *
     * @param thisMuch - value
     */
    public void updateOpponentLife(int thisMuch) {
        int thisMuch2 = Characters.getInstance().getOpponent().getCelestiaMultiplier() * thisMuch;
        oppLife = oppLife + thisMuch2;
        daNum2 = ((getOppLife() / getOppMaxLife()) * 100); //perc life x life bar length
        lifePlain2 = Math.round(daNum2); // round off
        lifeTotalPlain2 = Math.round(getOppLife()); // for text
        perCent2 = Math.round(lifePlain2);
        Characters.getInstance().setCurrLifeOpp(perCent2);
        Characters.getInstance().setCurrLifeChar(perCent);
    }

    /**
     * Get the CharacterEnum life, these methods should be float as they are used in divisions
     *
     * @return CharacterEnum life
     */
    public float getCharLife() {
        return (float) life;
    }

    /**
     * Get the CharacterEnum max life, these methods should be float as they are used in divisions
     *
     * @return CharacterEnum maximum life
     */
    public float getCharMaxLife() {
        return (float) maXlife;
    }

    /**
     * Resume paused game
     */
    public void start() {
        GameInstance.getInstance().resumeGame();
    }

    /**
     * Alter damage multipliers, used to strengthen/weaken attacks
     *
     * @param per      the person calling the method
     * @param thisMuch the number to alter by
     */
    public void alterDamageCounter(CharacterState per, int thisMuch) {
        if (per == CharacterState.CHARACTER && Characters.getInstance().getOpponent().getDamageMultiplier() > 0 && Characters.getInstance().getOpponent().getDamageMultiplier() < 20) {
            Characters.getInstance().getOpponent().setDamageMultiplier(Characters.getInstance().getOpponent().getDamageMultiplier() + thisMuch);
        }

        if (per == CharacterState.OPPONENT && Characters.getInstance().getCharacter().getDamageMultiplier() > 0 && Characters.getInstance().getCharacter().getDamageMultiplier() < 20) {
            Characters.getInstance().getCharacter().setDamageMultiplier(Characters.getInstance().getCharacter().getDamageMultiplier() + thisMuch);
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
                setName("Fury bar increment stage");
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

    public void triggerFury(CharacterState who) {
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
    public void limitBreak(CharacterState who) {
        characterState = who;
        new Thread() {

            @Override
            public void run() {
                if (getBreak() == 1000) {
                    //&& GameInstance.getInstance().getRecoveryUnitsChar()>289
                    //runs on local
                    if (characterState == CharacterState.CHARACTER && limitRunning && GameInstance.getInstance().getRecoveryUnitsChar() > 289) {
                        limitRunning = false;
                        if (MainWindow.getInstance().getGameMode() == SubMode.LAN_CLIENT) {
                            MainWindow.getInstance().sendToServer("limt_Break_Oxodia_Ownz");
                        } else if (MainWindow.getInstance().getGameMode() == SubMode.LAN_HOST) {
                            MainWindow.getInstance().sendToClient("limt_Break_Oxodia_Ownz");
                        }
                        setAttackType("fury", CharacterState.CHARACTER);
                        comboCounter = 0;
                        GameInstance.getInstance().pauseActivityRegen();
                        GameInstance.getInstance().setRecoveryUnitsChar(0);
                        try {
                            JenesisGamePad.getInstance().setRumbler(true, 0.8f);
                        } catch (Exception ex) {
                            ex.printStackTrace(System.err);
                        }
                        for (int i = 1; i < 9; i++) {
                            //stop attacking when game over
                            if (GameInstance.getInstance().gameOver == false) {
                                furySound();
                                hurtSoundOpp();
                                MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                                setSprites(CharacterState.CHARACTER, i, 11);
                                setSprites(CharacterState.OPPONENT, 0, 11);
                                shakeOppCharLB();
                                comboPicArrayPosOpp = i;
                                furyComboOpacity = 1.0f;
                                lifePhysUpdateSimple(CharacterState.OPPONENT, 100, "");
                            }
                        }
                        MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
                        try {
                            JenesisGamePad.getInstance().setRumbler(false, 0.0f);
                        } catch (Exception e) {
                        }
                        comboPicArrayPosOpp = 8;
                        GameInstance.getInstance().resumeActivityRegen();
                        setSprites(CharacterState.CHARACTER, 9, 11);
                        setSprites(CharacterState.OPPONENT, 9, 11);
                        limitRunning = true;
                        resetBreak();
                        setAttackType("normal", CharacterState.CHARACTER);
                    } else if (characterState == CharacterState.OPPONENT && limitRunning && GameInstance.getInstance().getRecoveryUnitsOpp() > 289) {
                        setAttackType("fury", CharacterState.OPPONENT);
                        limitRunning = false;
                        try {
                            JenesisGamePad.getInstance().setRumbler(true, 0.8f);
                        } catch (Exception e) {
                        }
                        for (int i = 1; i < 9; i++) {
                            if (GameInstance.getInstance().gameOver == false) {
                                MainWindow.getInstance().getAttacksChar().CharacterOverlayEnabled();
                                furySound();
                                hurtSoundChar();
                                GameInstance.getInstance().setRecoveryUnitsOpp(0);
                                setSprites(CharacterState.OPPONENT, i, 11);
                                setSprites(CharacterState.CHARACTER, 0, 11);
                                shakeCharLB();
                                comboPicArrayPosOpp = i;
                                lifePhysUpdateSimple(CharacterState.CHARACTER, 100, "");
                            }
                        }
                        MainWindow.getInstance().getAttacksChar().CharacterOverlayDisabled();
                        try {
                            JenesisGamePad.getInstance().setRumbler(false, 0.0f);
                        } catch (Exception e) {
                        }
                        comboPicArrayPosOpp = 8;
                        setSprites(CharacterState.OPPONENT, 9, 11);
                        setSprites(CharacterState.CHARACTER, 9, 11);
                        limitRunning = true;
                        resetBreak();
                        setAttackType("normal", CharacterState.OPPONENT);
                    }
                }
            }
        }.start();
    }

    /**
     * Updates the life of CharacterEnum
     *
     * @param forWho   - the person affected
     * @param ThisMuch - the life to add/subtract
     * @param attacker - who inflicted damage
     */
    public void lifePhysUpdateSimple(CharacterState forWho, int ThisMuch, String attacker) {

        if (forWho == CharacterState.CHARACTER) //Attack from player
        {
            damageChar = ThisMuch;
            activePerson = attacker;
            incLImit(damageChar);
            guiScreenChaos(ThisMuch * getDamageMultiplierOpp(), CharacterState.OPPONENT);
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

        if (forWho == CharacterState.OPPONENT || forWho == CharacterState.BOSS) //Attack from CPU pponent 1
        {
            damageOpp = ThisMuch;
            activePerson = attacker;
            incLImit(damageOpp);
            guiScreenChaos(ThisMuch * getDamageMultiplierChar(), CharacterState.CHARACTER);
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
    }

    /**
     * Get the break status
     *
     * @return break status
     */
    public int getBreak() {
        return limitBreak;
    }

    protected abstract void guiScreenChaos(float damageAmount, CharacterState who);

    protected abstract void furySound();

    protected abstract void hurtSoundChar();

    protected abstract void hurtSoundOpp();

    protected abstract void attackSoundOpp();

    public String getAnimDirection() {
        return animDirection;
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

    public int getCharYcord() {
        return charYcord;
    }

    public void setCharYcord(int value) {
        charYcord = value;
    }

    public int getOppYcord() {
        return oppYcord;
    }

    public void setOppYcord(int value) {
        oppYcord = value;
    }

    public int getAnimLoops() {
        return animLoops;
    }

    public long getDelay() {
        return delay;
    }

    public int getForeGroundPositionX() {
        return foreGroundPositionX;
    }

    public void setForeGroundPositionX(int foreGroundPositionX) {
        this.foreGroundPositionX = foreGroundPositionX;
    }

    public int getForeGroundPositionY() {
        return foreGroundPositionY;
    }

    public void setForeGroundPositionY(int foreGroundPositionY) {
        this.foreGroundPositionY = foreGroundPositionY;
    }

    public int getFgxInc() {
        return fgxInc;
    }

    public int getFgyInc() {
        return fgyInc;
    }

    public int getComboCounter() {
        return comboCounter;
    }

    public void setComboCounter(int comboCounter) {
        this.comboCounter = comboCounter;
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

    public void setClasherRunnign(boolean value) {
        clasherRunnign = value;
    }

    public CharacterEnum[] getCharNames() {
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

    public int getParticlesLayer1PositionX() {
        return particlesLayer1PositionX;
    }

    public void setParticlesLayer1PositionX(int particlesLayer1PositionX) {
        this.particlesLayer1PositionX = particlesLayer1PositionX;
    }

    public int getParticlesLayer2PositionX() {
        return particlesLayer2PositionX;
    }

    public void setParticlesLayer2PositionX(int particlesLayer2PositionX) {
        this.particlesLayer2PositionX = particlesLayer2PositionX;
    }

    public int getParticlesLayer1PositionY() {
        return particlesLayer1PositionY;
    }

    public void setParticlesLayer1PositionY(int particlesLayer1PositionY) {
        this.particlesLayer1PositionY = particlesLayer1PositionY;
    }

    public int getParticlesLayer2PositionY() {
        return particlesLayer2PositionY;
    }

    public void setParticlesLayer2PositionY(int particlesLayer2PositionY) {
        this.particlesLayer2PositionY = particlesLayer2PositionY;
    }

    public void mouseMoved(int mouseX, int mouseY) {
        //when fighting
        if (GameInstance.getInstance().gameOver == false && GameInstance.getInstance().storySequence == false && isDnladng()) {
            //browse moves
            if (mouseX > (29 + (leftHandXAxisOffset * getscaleX())) && mouseX < (436 + (leftHandXAxisOffset * getscaleX()))) {
                if (mouseY > (int) (373 * getscaleY()) && mouseY < (int) (390 * getscaleY())) {
                    RenderGameplay.getInstance().thisItem(0);
                }
                if (mouseY > (int) (390 * getscaleY()) && mouseY < (int) (407 * getscaleY())) {
                    RenderGameplay.getInstance().thisItem(1);
                }
                if (mouseY > (int) (407 * getscaleY()) && mouseY < (int) (420 * getscaleY())) {
                    RenderGameplay.getInstance().thisItem(2);
                }
                if (mouseY > (int) (420 * getscaleY()) && mouseY < (int) (435 * getscaleY())) {
                    RenderGameplay.getInstance().thisItem(3);
                }
            }
        }
    }
}
