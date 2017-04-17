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
package com.scndgen.legends.windows;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.OverWorld;
import com.scndgen.legends.attacks.AttackOpponent;
import com.scndgen.legends.attacks.AttackPlayer;
import com.scndgen.legends.drawing.DrawWaiting;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.executers.CharacterAttacksOnline;
import com.scndgen.legends.executers.OpponentAttacksOnline;
import com.scndgen.legends.network.NetworkClient;
import com.scndgen.legends.network.NetworkServer;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.scene.StoryMenu;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.threads.ThreadGameInstance;
import io.github.subiyacryolite.enginev1.JenesisGamePad;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

public class MainWindow extends JFrame implements KeyListener, WindowListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static final int PORT = 5555;
    public static final int serverLatency = 500;
    public static String lanHost = "lanHost";
    public static String lanClient = "lanClient";
    public static String singlePlayer = "singlePlayer";
    public static String storyMode = "StoryMenu";
    public static int tTime;
    private static MainWindow instance;
    public int hostTime;
    public boolean inStoryPane;
    public boolean gameRunning = false, withinMenuPanel, freeToSave = true, withinCharPanel = false, controller = false;
    public int item = 0, storedX = 99, storedY = 99, xyzStickDir;
    public Mode mode = Mode.EMPTY;
    public OpponentAttacksOnline playerHost2, playerClient1;
    public CharacterAttacksOnline playerHost1, playerClient2;
    public String ServerName;
    public String last, UserName;
    private AttackOpponent attackOpponent;
    private AttackPlayer attackPlayer;
    private boolean[] buttonPressed;
    //sever
    private NetworkServer server;
    private InetAddress ServerAddress;
    private int leftyXOffset, onlineClients = 0, hatDir;
    private boolean messageSent = false, isWaiting = true, isNotRepainting = true, menuLatencyElapsed = true;
    //client
    private NetworkClient client;
    private JTextField txtServerName = new JTextField(20);
    private JTextField txtUserName = new JTextField(20);
    private OverWorld world;
    private int mouseYoffset = 0;
    private String gameMode = "";
    //offline, host, client
    private String gameIp = "";
    private String userName;
    private DrawWaiting drawWait;
    private JenesisImageLoader pix;
    private ArrayList imageList;
    private Image ico16, ico22, ico24, ico32, ico48, ico72, ico96, ico128, ico256;
    private MainWindow gameWindow;
    private AudioPlayback backgroundMusic;

    private MainWindow(String nameOfUser, String mode) {
        instance = this;
        try {
            if (JenesisGamePad.getInstance().NUM_BUTTONS > 0) {
                controller = true;
                buttonPressed = new boolean[JenesisGamePad.getInstance().NUM_BUTTONS];
                pollController();
            }
        } catch (Exception e) {
        }
        backgroundMusic = new AudioPlayback(AudioPlayback.menuMus(), false);
        backgroundMusic.play();
        pix = new JenesisImageLoader();
        gameMode = mode;
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getAvailableAcceleratedMemory());
        userName = nameOfUser;

        if (getGameMode().equalsIgnoreCase(lanHost)) {
            server = new NetworkServer();
            server.start();
        }

        if (LoginScreen.getInstance().isLefty().equalsIgnoreCase("no")) {
            leftyXOffset = 548;
        } else {
            leftyXOffset = 0;
        }

        if (getGameMode().equalsIgnoreCase(lanClient)) {
            client = new NetworkClient(LoginScreen.getInstance().getIP());
        }
        setUndecorated(true);
        setTitle("The SCND Genesis: Legends" + RenderGameplay.getInstance().getVersionStr());
        ico16 = pix.loadImage("images/GameIco16.png");
        ico22 = pix.loadImage("images/GameIco22.png");
        ico24 = pix.loadImage("images/GameIco24.png");
        ico32 = pix.loadImage("images/GameIco32.png");
        ico48 = pix.loadImage("images/GameIco48.png");
        ico72 = pix.loadImage("images/GameIco72.png");
        ico96 = pix.loadImage("images/GameIco96.png");
        ico128 = pix.loadImage("images/GameIco128.png");
        ico256 = pix.loadImage("images/GameIco256.png");
        imageList = new ArrayList();
        imageList.add(ico16);
        imageList.add(ico22);
        imageList.add(ico24);
        imageList.add(ico32);
        imageList.add(ico48);
        imageList.add(ico72);
        imageList.add(ico96);
        imageList.add(ico128);
        imageList.add(ico256);
        setIconImages(imageList);
        //seti(loadIconImage("images/GameIco.ico"));

        RenderStageSelect.getInstance().newInstance();
        if (mode.equals(lanHost)) {
            isWaiting = true;
            drawWait = new DrawWaiting();
        } else if (mode.equals(storyMode)) {
            RenderStoryMenu.getInstance().newInstance();
            inStoryPane = true;
            this.mode = Mode.STORY_SELECT_SCREEN;
        } else {
            this.mode = Mode.CHAR_SELECT_SCREEN;
            RenderCharacterSelectionScreen.getInstance().animateCharSelect();
        }

        //------------ JFrame properties

        if (mode.equals(lanHost)) {
            setContentPane(drawWait);
        } else if (mode.equals(storyMode)) {
            RenderStoryMenu.getInstance().newInstance();
            setContentPane(RenderStoryMenu.getInstance());
        } else {
            RenderCharacterSelectionScreen.getInstance().newInstance();
            setContentPane(RenderCharacterSelectionScreen.getInstance());
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        requestFocusInWindow();
        setFocusable(true);
        addKeyListener(this);
        pack();
        setLocationRelativeTo(null); // Centers JFrame on screen //
        setResizable(false);
        setVisible(true);
        if (gameMode.equalsIgnoreCase(lanClient)) {
            client.sendData("player_QSLV");
        }
        superRepaintThread();
        packWindow();
    }

    public static synchronized MainWindow newInstance(String strUser, String storyMode) {
        if (instance == null)
            instance = new MainWindow(strUser, storyMode);
        return instance;
    }

    public static MainWindow getInstance() {
        return instance;
    }

    public void stopBackgroundMusic() {
        backgroundMusic.close();
    }

    public String getServerName() {
        return txtServerName.getText();
    }

    public String getServerUserName() {
        return txtUserName.getText();
    }

    public boolean isMessageSent() {
        return messageSent;
    }

    public void packWindow() {
        pack();
    }

    /**
     * Polls game pads for dataInputStream data
     */
    private void pollController() {
        new Thread() {

            @SuppressWarnings({"SleepWhileHoldingLock", "static-access"})
            public void run() {
                try {
                    do {
                        this.sleep(33);
                        if (!JenesisGamePad.getInstance().canPoll()) continue;
                        JenesisGamePad.getInstance().poll();
                        //update bottons
                        buttonPressed = JenesisGamePad.getInstance().getButtons();
                        // get compass direction for the two analog sticks
                        xyzStickDir = JenesisGamePad.getInstance().getXYStickDir();
                        if (xyzStickDir == JenesisGamePad.getInstance().NORTH) {
                            up();
                        } else if (xyzStickDir == JenesisGamePad.getInstance().SOUTH) {
                            down();
                        } else if (xyzStickDir == JenesisGamePad.getInstance().WEST) {
                            left();
                        } else if (xyzStickDir == JenesisGamePad.getInstance().EAST) {
                            right();
                        }
                        // get POV hat compass direction
                        hatDir = JenesisGamePad.getInstance().getHatDir();
                        if (hatDir == JenesisGamePad.getInstance().SOUTH) {
                            down();
                        }
                        if (hatDir == JenesisGamePad.getInstance().NORTH) {
                            up();
                        }
                        if (hatDir == JenesisGamePad.getInstance().WEST) {
                            left();
                        }
                        if (hatDir == JenesisGamePad.EAST) {
                            right();
                        }
                        //button one
                        if (buttonPressed[0]) {
                            back();
                        }
                        if (buttonPressed[1]) {
                            trigger();
                        }
                        if (buttonPressed[2]) {
                            accept();
                        }
                        if (buttonPressed[3]) {
                            escape();
                        }
                        if (buttonPressed[5]) {
                            triggerFury(CharacterState.CHARACTER);
                        }
                        hatDir = JenesisGamePad.getInstance().getXYStickDir();
                        hatDir = JenesisGamePad.getInstance().getZRZStickDir();
                        buttonPressed = JenesisGamePad.getInstance().getButtons();
                    } while (true);
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
            }
        }.start();
    }

    public StoryMenu getStory() {
        return RenderStoryMenu.getInstance();
    }

    public void main(String[] args) {
        gameWindow = new MainWindow("Punk", singlePlayer);
    }

    public void getRidOfTitleBar() {
        setUndecorated(true);
    }

    public void getBackTitleBar() {
        setUndecorated(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        focus();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
        focus();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        focus();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        focus();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        focus();
    }

    public AttackOpponent getAttackOpponent() {
        return attackOpponent;
    }


    public AttackPlayer getAttacksChar() {
        return attackPlayer;
    }

    public void newGame() {
        mode = Mode.STANDARD_GAMEPLAY;
        attackOpponent = new AttackOpponent();
        attackPlayer = new AttackPlayer();
        stopBackgroundMusic();
        RenderGameplay.getInstance().newInstance();
        setContentPane(RenderGameplay.getInstance());
        RenderGameplay.getInstance().startFight();
        reSize("game");

    }

    /**
     * Repack frame
     */
    public void reSize(String mode) {
        if (mode.equalsIgnoreCase("game")) {
            resize(LoginScreen.getInstance().getGameWidth(), LoginScreen.getInstance().getGameHeight());
        } else {
            resize(852, 480);
        }

        pack();
        setLocationRelativeTo(null);
    }

    public void storyGame() {
        //bgMusclose();
        attackOpponent = new AttackOpponent();
        attackPlayer = new AttackPlayer();
        mode = Mode.STANDARD_GAMEPLAY;
        stopBackgroundMusic();
        setContentPane(RenderGameplay.getInstance());
        RenderGameplay.getInstance().startFight();
        reSize("game");
    }

    /**
     * Goes back to main menu
     */
    public void backToMenuScreen() {
        MainMenu.getMenu().showModes();
        backgroundMusic.stop();
        backgroundMusic.close();
        dispose();
        RenderGameplay.getInstance().cleanAssets();
        focus();
    }

    /**
     * Increments to next stage in story scene
     */
    public void nextStage() {
        //bgMusclose();
        attackOpponent = new AttackOpponent();
        attackPlayer = new AttackPlayer();
        mode = Mode.STANDARD_GAMEPLAY;
        stopBackgroundMusic();
        setContentPane(RenderGameplay.getInstance());
        RenderGameplay.getInstance().startFight();
        reSize("game");

    }

    /**
     * Kinda obvious
     */
    public void stopMenuMusic() {
        //bgMusclose();
    }

    /**
     * Finds out if a match is running
     *
     * @return status of match
     */
    public boolean getIsGameRunning() {
        return mode == Mode.STANDARD_GAMEPLAY;
    }

    /**
     * Sets the game to running
     */
    public void setGameRunning() {
        gameRunning = true;
    }

    /**
     * Enables player to select a stage
     */
    public void selectStage() {
        setContentPane(RenderStageSelect.getInstance());
        mode = Mode.STAGE_SELECT_SCREEN;
        pack();
        setLocationRelativeTo(null);
        focus();
    }

    /**
     * Back to characters select screen, when match is over
     */
    public void backToCharSelect() {
        gameRunning = false;
        RenderCharacterSelectionScreen.getInstance().newInstance();
        setContentPane(RenderCharacterSelectionScreen.getInstance());
        mode = Mode.CHAR_SELECT_SCREEN;
        RenderGameplay.getInstance().cleanAssets();
        RenderCharacterSelectionScreen.getInstance().animateCharSelect();
        RenderCharacterSelectionScreen.getInstance().newInstance();
        backgroundMusic = new AudioPlayback(AudioPlayback.menuMus(), true);
        backgroundMusic.play();
        reSize("menu");
        focus();
    }

    /**
     * Back to characters select screen,, when match is cancelled
     */
    public void backToCharSelect2() {
        gameRunning = false;
        RenderGameplay.getInstance().getGameInstance().isPaused = false;
        RenderGameplay.getInstance().getGameInstance().terminateGameplay();
        RenderCharacterSelectionScreen.getInstance().animateCharSelect();
        RenderCharacterSelectionScreen.getInstance().newInstance();
        setContentPane(RenderCharacterSelectionScreen.getInstance());
        RenderGameplay.getInstance().cleanAssets();
        RenderCharacterSelectionScreen.getInstance().systemNotice("Canceled Match");
        mode = Mode.CHAR_SELECT_SCREEN;
        backgroundMusic = new AudioPlayback(AudioPlayback.menuMus(), true);
        backgroundMusic.play();
        reSize("menu");
        focus();
        RenderStageSelect.getInstance().defaultStageValues();
        if (getGameMode().equalsIgnoreCase(storyMode)) {
            RenderCharacterSelectionScreen.getInstance().backToMenu();
        }
    }

    /**
     * Closes hosting NetworkServer
     */
    public void closeTheServer() {
        server.closeServer();
        System.out.println("Closed server");
        backToMenuScreen();
    }

    /**
     * Closes client
     */
    public void closeTheClient() {
        client.closeClient();
        System.out.println("Closed client");
        backToMenuScreen();
    }

    /**
     * Display message in sys overlay via game instance
     *
     * @param mess - the message to display
     */
    public void systemNotice(String mess) {
        RenderGameplay.getInstance().systemNotice(mess);
    }

    /**
     * Display message in sys overlay via game instance
     *
     * @param mess - the message to display
     */
    public void systemNotice2(String mess) {
        RenderGameplay.getInstance().systemNotice2(mess);
    }

    /**
     * Cancels match
     */
    public void cancelMatch() {
        int u = JOptionPane.showConfirmDialog(null, "Are you sure you wanna quit?", "Dude!?", JOptionPane.YES_NO_OPTION);
        if (u == JOptionPane.YES_OPTION) {
            if (getGameMode().equalsIgnoreCase(storyMode)) {
                getStory().getStoryInstance().firstRun = true;
            }
            //unpause if match was paused
            if (RenderGameplay.getInstance().getGameInstance().isPaused) {
                pause();
            }
            RenderStageSelect.getInstance().setSelectedStage(false);
            backToCharSelect2();
        }
    }

    public String getGameMode() {
        return gameMode;
    }

    /**
     * Vibrate
     */
    private void quickVibrate(float strength, int length) {
        final float power = strength;
        final int time = length;
        new Thread() {
            public void run() {
                try {
                    JenesisGamePad.getInstance().setRumbler(true, power);
                    this.sleep(time);
                    JenesisGamePad.getInstance().setRumbler(false, 0.0f);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    /**
     * Contains universal up menu/game movements
     */
    private void up() {
        if (menuLatencyElapsed) {
            if (mode == Mode.CHAR_SELECT_SCREEN) {
                RenderCharacterSelectionScreen.getInstance().moveUp();
            } else if (mode == Mode.STORY_SELECT_SCREEN) {
                RenderStoryMenu.getInstance().moveUp();
            } else if (mode == Mode.STAGE_SELECT_SCREEN) {
                //client should be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    RenderStageSelect.getInstance().moveUp();
                }
            } else if (getIsGameRunning()) {
                RenderGameplay.getInstance().upItem();
            }
        }
    }

    /**
     * Contains universal down menu/game movements
     */
    private void down() {
        if (menuLatencyElapsed) {
            if (mode == Mode.CHAR_SELECT_SCREEN) {
                RenderCharacterSelectionScreen.getInstance().moveDown();
            } else if (mode == Mode.STORY_SELECT_SCREEN) {
                RenderStoryMenu.getInstance().moveDown();
            } else if (mode == Mode.STAGE_SELECT_SCREEN) {
                //client should not be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    RenderStageSelect.getInstance().moveDown();
                }
            } else if (getIsGameRunning()) {
                RenderGameplay.getInstance().downItem();
            }
        }
    }

    /**
     * Contains universal left menu/game movements
     */
    private void left() {
        if (menuLatencyElapsed) {
            if (mode == Mode.CHAR_SELECT_SCREEN) {
                RenderCharacterSelectionScreen.getInstance().moveLeft();
            } else if (mode == Mode.STORY_SELECT_SCREEN) {
                RenderStoryMenu.getInstance().moveLeft();
            } else if (mode == Mode.STAGE_SELECT_SCREEN) {
                //client should be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    RenderStageSelect.getInstance().moveLeft();
                }
            } else if (getIsGameRunning()) {
                RenderGameplay.getInstance().prevAnimation();
            }
        }
    }

    /**
     * Contains universal right menu/game movements
     */
    private void right() {
        if (menuLatencyElapsed) {
            if (mode == Mode.CHAR_SELECT_SCREEN) {
                RenderCharacterSelectionScreen.getInstance().moveRight();
            } else if (mode == Mode.STORY_SELECT_SCREEN) {
                RenderStoryMenu.getInstance().moveRight();
            } else if (mode == Mode.STAGE_SELECT_SCREEN) {
                RenderStageSelect.getInstance().moveRight();
            } else if (getIsGameRunning()) {
                RenderGameplay.getInstance().nextAnimation();
            }
        }
    }

    /**
     * Handles universal accept functions
     */
    private void accept() {
        if (menuLatencyElapsed) {
            if (getIsGameRunning()) {
                if (ThreadGameInstance.isPaused == false) {
                    //in game, no story sequence
                    if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.storySequence == false) {
                        RenderGameplay.getInstance().moveSelected();
                    } //in game, during story sequence
                    else if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.storySequence == true) {
                        getStory().getStoryInstance().skipDialogue();
                    } //story scene -- after text
                    else if (ThreadGameInstance.isGameOver == false && getStory().getStoryInstance().doneShowingText) {
                        getStory().getStoryInstance().skipDialogue();
                    } else {
                        //if person presses twice the stage increments twice
                        //this prevents that
                        //it only free to save when its game over
                        //one a save is used, it not free to save (i.e null)
                        if (freeToSave) {
                            freeToSave = false;
                            if (getGameMode().equalsIgnoreCase(singlePlayer)
                                    || getGameMode().equalsIgnoreCase(lanClient)
                                    || getGameMode().equalsIgnoreCase(lanHost)) {
                                RenderGameplay.getInstance().getGameInstance().closingThread(1);
                            } else {
                                RenderGameplay.getInstance().getGameInstance().closingThread(0);
                            }
                        }
                    }
                }
            } //cancel hosting
            else if (isWaiting && gameMode.equals(lanHost)) {
                closeTheServer();
            } else if (mode == Mode.CHAR_SELECT_SCREEN) {
                //if both Characters are selected
                if (RenderCharacterSelectionScreen.getInstance().getCharacterSelected() && RenderCharacterSelectionScreen.getInstance().getOpponentSelected()) {
                    if ((getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(lanHost))) {
                        quickVibrate(0.6f, 1000);
                        RenderCharacterSelectionScreen.getInstance().beginGame();
                    }
                } else {
                    quickVibrate(0.4f, 1000);
                    RenderCharacterSelectionScreen.getInstance().selectCharacter();
                }
            } else if (mode == Mode.STORY_SELECT_SCREEN) {
                RenderStoryMenu.getInstance().selectStage();
                quickVibrate(0.4f, 1000);
            } else if (mode == Mode.STAGE_SELECT_SCREEN) {
                //client should be able to meddle in stage select
                if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                    if (RenderCharacterSelectionScreen.getInstance().getCharacterSelected() && RenderCharacterSelectionScreen.getInstance().getOpponentSelected() && (getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(lanHost))) {
                        quickVibrate(0.66f, 1000);
                        RenderStageSelect.getInstance().selectStage(RenderStageSelect.getInstance().getHoveredStage());
                    }
                }
            }
        }

    }

    /**
     * Handles universal UI escape
     */
    private void escape() {
        if (mode == Mode.CHAR_SELECT_SCREEN || mode == Mode.STORY_SELECT_SCREEN) {
            if (getGameMode().equalsIgnoreCase(singlePlayer)) {
                {
                    RenderCharacterSelectionScreen.getInstance().newInstance();
                    RenderCharacterSelectionScreen.getInstance().backToMenu();
                }
            }
        }
        if (getIsGameRunning()) {
            pause();
        } else if (mode == Mode.STORY_SELECT_SCREEN && !getIsGameRunning()) {
            RenderStoryMenu.getInstance().backToMainMenu();
        }

    }

    /**
     * Global back
     */
    private void back() {
        if (menuLatencyElapsed) {
            if (mode == Mode.CHAR_SELECT_SCREEN) {
                if (getGameMode().equalsIgnoreCase(singlePlayer)) {
                    RenderCharacterSelectionScreen.getInstance().newInstance();
                }
            } else if (getIsGameRunning()) {
                RenderGameplay.getInstance().unQueMove();
            }
        }

    }

    public void triggerFury(CharacterState who) {
        RenderGameplay.getInstance().triggerFury(who);
    }

    /**
     * Universal trigger
     */
    private void trigger() {
        if (menuLatencyElapsed)
            if (getIsGameRunning())
                RenderGameplay.getInstance().attack();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            up();
        }
        if (keyCode == KeyEvent.VK_M) {
            if (isWaiting && gameMode.equals(lanHost)) {
                world = new OverWorld();
            }
        }
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            down();
        }
        if (keyCode == KeyEvent.VK_X) {
            packWindow();
        }
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            left();
        }
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            right();
        }
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            back();
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            accept();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            escape();
        }
        if (keyCode == KeyEvent.VK_F4) {
            LoginScreen.getInstance().getMenu().exit();
        }
        if (keyCode == KeyEvent.VK_F5) {
            if (getIsGameRunning()) {
                cancelMatch();
            }
        }
        if (keyCode == KeyEvent.VK_F12) {
            if (mode == Mode.CHAR_SELECT_SCREEN) {
                RenderCharacterSelectionScreen.getInstance().captureScreenShot();
            } else if (mode == Mode.STORY_SELECT_SCREEN) {
                RenderStoryMenu.getInstance().captureScreenShot();
            } else if (mode == Mode.STAGE_SELECT_SCREEN) {
                RenderStageSelect.getInstance().captureScreenShot();
            } else if (getIsGameRunning()) {
                RenderGameplay.getInstance().captureScreenShot();
            }
        } else if (getIsGameRunning()) {
            if (keyCode == KeyEvent.VK_L) {
                triggerFury(CharacterState.CHARACTER);
            }
            if (keyCode == KeyEvent.VK_SPACE) {
                trigger();
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (gameRunning) {
            if (RenderGameplay.getInstance().getGameInstance().isGameOver == false && ThreadGameInstance.storySequence == false) {
                int count = mwe.getWheelRotation();
                if (count >= 0) {
                    RenderGameplay.getInstance().prevAnimation();
                }
                if (count < 0) {
                    RenderGameplay.getInstance().nextAnimation();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent m) {
        if (mode == Mode.CHAR_SELECT_SCREEN && withinCharPanel) {
            if (RenderCharacterSelectionScreen.getInstance().getCharacterSelected() && RenderCharacterSelectionScreen.getInstance().getOpponentSelected()) {
                if ((getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(lanHost))) {
                    quickVibrate(0.6f, 1000);
                    RenderCharacterSelectionScreen.getInstance().beginGame();
                }
            } else {
                RenderCharacterSelectionScreen.getInstance().selectCharacter();
            }
        }
        if (mode == Mode.STORY_SELECT_SCREEN && withinMenuPanel) {
            RenderStoryMenu.getInstance().selectStage();
        }
        if (mode == Mode.STAGE_SELECT_SCREEN && withinCharPanel) {
            RenderStageSelect.getInstance().selectStage(RenderStageSelect.getInstance().getHoveredStage());
        } else if (getIsGameRunning()) {
            if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.storySequence == false) {
                if (m.getButton() == MouseEvent.BUTTON1) {
                    if (m.getX() > (29 + leftyXOffset) && m.getX() < (220 + leftyXOffset) && (m.getY() > 358)) {
                        RenderGameplay.getInstance().moveSelected();
                    }
                    if (m.getX() < (29 + leftyXOffset)) {
                        RenderGameplay.getInstance().prevAnimation();
                    }
                    if (m.getX() > (220 + leftyXOffset) && m.getX() < (305 + leftyXOffset)) {
                        RenderGameplay.getInstance().nextAnimation();
                    } else if ((m.getX() > 25 && m.getX() < 46) && (m.getY() > 190 && m.getY() < 270)) {
                        triggerFury(CharacterState.CHARACTER);
                    }
                }
                if (m.getButton() == MouseEvent.BUTTON2) {
                    triggerFury(CharacterState.CHARACTER);
                }
                if (m.getButton() == MouseEvent.BUTTON3) {
                    RenderGameplay.getInstance().unQueMove();
                }
            }
        }
        focus();
    }

    @Override
    public void mouseEntered(MouseEvent m) {
    }

    @Override
    public void mouseDragged(MouseEvent m) {
    }

    /**
     * To make sure the caption is animated once,
     * this method checks if the selected caption has changed
     *
     * @param x
     * @param y
     */
    public void animateCap2(int x, int y) {
        int tmpx = x;
        int tmpy = y;

        if (tmpx == storedX && tmpy == storedY) //same vals, do nothing
        {
        } else {
            storedX = tmpx;
            storedY = tmpy;
            RenderCharacterSelectionScreen.getInstance().animateCaption();
        }
    }

    /**
     * To make sure the caption is animated once,
     * this method checks if the selected caption has changed
     *
     * @param x
     * @param y
     */
    public void animateCap2x(int x, int y) {
        int tmpx = x;
        int tmpy = y;

        if (tmpx == storedX && tmpy == storedY) //same vals, do nothing
        {
        } else {
            storedX = tmpx;
            storedY = tmpy;
            RenderStoryMenu.getInstance().animateCaption();
        }
    }

    public void superRepaint() {
        repaint();
        //getGlassPane().repaint();
    }

    private void superRepaintThread() {
        if (isNotRepainting) {
            isNotRepainting = false;
            new Thread() {

                final int sleepTime = 1000 / tTime;

                @SuppressWarnings({"static-access", "SleepWhileHoldingLock", "CallToThreadDumpStack"})
                @Override
                public void run() {
                    do {
                        try {
                            superRepaint();
                            this.setName("Super awesome repaint thread");
                            this.sleep(sleepTime);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } while (1 != 0);
                }
            }.start();
        }
    }

    /**
     * Attempts to map selected characters using mouse
     */
    private void sortCharPosLoc(int mouseX, int mouseY) {
        int topY = RenderCharacterSelectionScreen.getInstance().getTopY() + mouseYoffset;
        int topX = RenderCharacterSelectionScreen.getInstance().getTopX();
        int columns = RenderCharacterSelectionScreen.getInstance().getColumns();
        int captionHeight = RenderCharacterSelectionScreen.getInstance().getCaptionHeight();
        int captionWidth = RenderCharacterSelectionScreen.getInstance().getCaptionWidth();
        int rows = RenderCharacterSelectionScreen.getInstance().getRows();
        if (mouseX > topX && mouseX < (topX + (captionWidth * columns)) && (mouseY > topY) && (mouseY < topY + (captionHeight * (rows + 1)))) {
            int vIndex = (mouseY - topY) / captionHeight;
            int hIndex = (mouseX - topX) / captionWidth;
            RenderCharacterSelectionScreen.getInstance().setHindex(hIndex);
            RenderCharacterSelectionScreen.getInstance().setVindex(vIndex);
            if (hIndex != storedX || vIndex != storedY) //same vals, do nothing
            {
                storedX = hIndex;
                storedY = vIndex;
                RenderCharacterSelectionScreen.getInstance().animateCaption();
            }
            withinCharPanel = true;
        } else {
            RenderCharacterSelectionScreen.getInstance().setHindex(99);
            RenderCharacterSelectionScreen.getInstance().setVindex(99);
            withinCharPanel = false;
        }
    }

    /**
     * Attempts to map selected characters using mouse
     */
    private void sortStagePosLoc(int mouseX, int mouseY) {
        int topY = RenderStoryMenu.getInstance().getStartY() + mouseYoffset;
        int topX = RenderStoryMenu.getInstance().getStartX();
        int columns = RenderStoryMenu.getInstance().getNumberOfCharColumns();
        int vspacer = RenderStoryMenu.getInstance().getCharHSpacer();
        int hspacer = RenderStoryMenu.getInstance().getCharVSpacer();
        int rows = RenderStoryMenu.getInstance().getCharRows();
        if (mouseX > topX && mouseX < (topX + (hspacer * columns)) && (mouseY > topY) && (mouseY < topY + (vspacer * (rows + 1)))) {
            int vIndex = (mouseY - topY) / vspacer;
            int hIndex = (((mouseX - topX) / hspacer) + 1);
            RenderStoryMenu.getInstance().setHindex(hIndex);
            RenderStoryMenu.getInstance().setVindex(vIndex);
            animateCap2x(hIndex, vIndex);
            withinMenuPanel = true;
            ;
        } else {
            RenderStoryMenu.getInstance().setHindex(99);
            RenderStoryMenu.getInstance().setVindex(99);
            withinMenuPanel = false;
        }
    }

    /**
     * Attempts to map selected characters using mouse
     */
    private void sortCharPosLoc2(int mouseX, int mouseY) {
        int topY = RenderStageSelect.getInstance().getStartY() + mouseYoffset;
        int topX = RenderStageSelect.getInstance().getStartX();
        int columns = RenderStageSelect.getInstance().getNumberOfCharColumns();
        int vspacer = RenderStageSelect.getInstance().getCharHSpacer();
        int hspacer = RenderStageSelect.getInstance().getCharVSpacer();
        int rows = RenderStageSelect.getInstance().getCharRows();
        if (mouseX > topX && mouseX < (topX + (hspacer * columns)) && (mouseY > topY) && (mouseY < topY + (vspacer * (rows + 1)))) {
            int vIndex = (mouseY - topY) / vspacer;
            int hIndex = (((mouseX - topX) / hspacer) + 1);
            //System.out.println("within char pan dog");
            //System.out.println("Row "+rowIndex);
            //System.out.println("Column "+columnIndex);
            RenderStageSelect.getInstance().setHindex(hIndex);
            RenderStageSelect.getInstance().setVindex(vIndex);
            //RenderCharacterSelectionScreen.getInstance().setItem();
            animateCap2(hIndex, vIndex);
            withinCharPanel = true;
        } else {
            //System.out.println("Outa char pan dog");
            RenderCharacterSelectionScreen.getInstance().setHindex(99);
            RenderCharacterSelectionScreen.getInstance().setVindex(99);
            withinCharPanel = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent m) {
        if (mode == Mode.CHAR_SELECT_SCREEN) {
            sortCharPosLoc(m.getX(), m.getY());
        }

        if (mode == Mode.STORY_SELECT_SCREEN) {
            sortStagePosLoc(m.getX(), m.getY());
        }

        if (mode == Mode.STAGE_SELECT_SCREEN) {
            //if you're a client stay still
            if (getGameMode().equalsIgnoreCase(lanClient) == false) {
                sortCharPosLoc2(m.getX(), m.getY());
            }
        } else if (getIsGameRunning()) {
            //when fighting
            if (ThreadGameInstance.isGameOver == false && ThreadGameInstance.storySequence == false && RenderGameplay.getInstance().isDnladng()) {
                //browse moves
                if (m.getX() > (29 + leftyXOffset) && m.getX() < (436 + leftyXOffset)) {
                    if (m.getY() > (int) (373 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset && m.getY() < (int) (390 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset) {
                        item = 0;
                    }

                    if (m.getY() > (int) (390 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset && m.getY() < (int) (407 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset) {
                        item = 1;
                    }

                    if (m.getY() > (int) (407 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset && m.getY() < (int) (420 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset) {
                        item = 2;
                    }

                    if (m.getY() > (int) (420 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset && m.getY() < (int) (435 * RenderGameplay.getInstance().getscaleY()) + mouseYoffset) {
                        item = 3;
                    }

                    RenderGameplay.getInstance().thisItem(item);
                }
            }
        }
        //System.out.println("Mouse X:"+m.getX());
        //System.out.println("Mouse y:"+m.getY());
    }

    @Override
    public void mouseExited(MouseEvent m) {
    }

    @Override
    public void mousePressed(MouseEvent m) {
    }

    @Override
    public void mouseReleased(MouseEvent m) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public String getIP() {
        return gameIp;
    }

    public void setIP(String ip) {
        gameIp = ip;
    }

    private void pause() {
        if (getGameMode().equalsIgnoreCase(singlePlayer) || getGameMode().equalsIgnoreCase(storyMode)) {
            if (ThreadGameInstance.isPaused == false) {
                RenderGameplay.getInstance().getGameInstance().pauseGame();
                RenderGameplay.getInstance().pauseThreads();
                if (ThreadGameInstance.storySequence == true) {
                    getStory().getStoryInstance().pauseDialogue();
                }
            } else {
                RenderGameplay.getInstance().start();
                RenderGameplay.getInstance().resumeThreads();
                if (ThreadGameInstance.storySequence == true) {
                    getStory().getStoryInstance().resumeDialogue();
                }
            }
        }
    }

    void savefile(String outfil, String inhalt) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfil), "UTF8"));
            out.write(inhalt);
            out.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println(">>> loadsave: no UTF-8 upport");
        } catch (IOException e) {
            System.out.println(">>> loadsave: Could not write " + outfil);
        }
    }

    public String getUserName() {
        return userName;
    }

    public void focus() {
        requestFocusInWindow();
        setFocusable(true);
        //System.out.println("Window focused");
    }

    /**
     * When a player is found
     */
    public void playerFound() {
        //System.out.println("CharacterState found");

        int ansx = JOptionPane.showConfirmDialog(null, userName + " , someone wants to fight you!!!!\nWanna waste em!?", "Heads Up", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        switch (ansx) {
            case JOptionPane.YES_OPTION: {
                sendToClient("as1wds2_" + LoginScreen.getInstance().timePref);
                isWaiting = false;
                drawWait.stopRepaint();
                RenderCharacterSelectionScreen.getInstance().newInstance();
                setContentPane(RenderCharacterSelectionScreen.getInstance());
                mode = Mode.CHAR_SELECT_SCREEN;//RenderCharacterSelectionScreen.getInstance().animCloud();
                RenderCharacterSelectionScreen.getInstance().animateCharSelect();
                pack();
                setLocationRelativeTo(null); // Centers JFrame on screen //
                break;
            }
        }
    }

    public NetworkServer getServer() {
        return server;
    }

    public NetworkClient getClient() {
        return client;
    }

    public void sendToClient(String thisTxt) {
        server.sendData(thisTxt);
    }

    public void sendToServer(String thisTxt) {
        client.sendData(thisTxt);
    }

}