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
package com.scndgen.legends.controller;

import com.scndgen.legends.Language;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.enums.CharacterState;
import com.scndgen.legends.enums.Mode;
import com.scndgen.legends.enums.Stage;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.render.RenderCharacterSelectionScreen;
import com.scndgen.legends.render.RenderGameplay;
import com.scndgen.legends.render.RenderStageSelect;
import com.scndgen.legends.render.RenderStoryMenu;
import com.scndgen.legends.state.GameState;
import com.scndgen.legends.threads.AudioPlayback;
import com.scndgen.legends.threads.GameInstance;

/**
 * @author ndana
 */
public class StoryMode implements Runnable {
    //mp3

    private static StoryMode instance;
    public boolean notAsked, doneShowingText = false;
    public String stat = "";
    public final int max = 11;
    public int time;
    private AudioPlayback storyMus;
    private String storyText;
    private int opt, tlkSpeed, currentScene;
    //thread
    private Thread thread;

    private StoryMode() {
        stat = "";
        time = 181;
        storyText = "";
        currentScene = 0;
    }

    public static synchronized StoryMode getInstance() {
        if (instance == null)
            instance = new StoryMode();
        return instance;
    }

    public synchronized void newInstance() {
        instance = new StoryMode();
    }

    public void story(int scene) {
        storyMus = new AudioPlayback(AudioPlayback.storySound(), false);
        tlkSpeed = GameState.getInstance().getLogin().getTextSpeed();
        notAsked = true;
        opt = -1;
        RenderCharacterSelectionScreen.getInstance().newInstance();
        RenderStageSelect.getInstance().newInstance();
        switch (scene) {
            case 0:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selRav(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL);
                break;
            case 1:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selLynx(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE);
                break;
            case 2:
                time = 30;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAisha(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selLynx(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.IBEX_HILL_NIGHT);
                break;
            case 3:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selSubiya(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_STREETS);
                break;
            case 4:
                time = 45;
                stat = "half way";
                RenderCharacterSelectionScreen.getInstance().selRav(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selAde(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 5:
                time = 45;
                stat = "nrml";
                RenderGameplay.getInstance().setNumOfBoards(2);
                RenderCharacterSelectionScreen.getInstance().selAdam(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selJon(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.FROZEN_WILDERNESS);
                break;
            case 6:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAza(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selNOVAAdam(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 7:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selSubiya(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selRav(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.CHELSTON_CITY_DOCKS);
                break;
            case 8:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selLynx(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selAdam(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 9:
                time = 60;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selRaila(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selSorr(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.APOCALYPTO);
                break;
            case 10:
                time = 90;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selSubiya(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selNOVAAdam(CharacterState.OPPONENT);
                RenderStageSelect.getInstance().selectStage(Stage.DISTANT_ISLE_NIGHT);
                break;
            case 11:
                time = 181;
                stat = "nrml";
                RenderCharacterSelectionScreen.getInstance().selAdam(CharacterState.CHARACTER);
                RenderCharacterSelectionScreen.getInstance().selThing(CharacterState.BOSS);
                RenderStageSelect.getInstance().selectStage(Stage.DESERT_RUINS_NIGHT);
                break;
        }
        startStoryMode(scene);
    }

    public void startStoryMode(int x) {
        RenderGameplay.getInstance().newInstance();
        currentScene = x;
        thread = new Thread(this);
        thread.setName("story scene thread");
        thread.start();
    }

    /**
     * In story scene chars and opp should generate nothin
     */
    private void storyIn() {
        storyMus.play();
        GameInstance.getInstance().storySequence = true;
        doneShowingText = false;
        GameInstance.getInstance().pauseActivityRegen();
        GameInstance.getInstance().pauseActivityRegenOpp();
    }

    private void storyOut(boolean terminateMode) {
        if (terminateMode) {
            storyMus.close();
            GameInstance.getInstance().playMusicNow();
            GameInstance.getInstance().musNotice();
        }
        RenderGameplay.getInstance().charPortBlank();
        RenderGameplay.getInstance().storyText("");
        thread.stop();
        GameInstance.getInstance().storySequence = false;
        doneShowingText = true;
        GameInstance.getInstance().resumeActivityRegen();
        GameInstance.getInstance().resumeActivityRegenOpp();
    }

    @Override
    public void run() {
        try {
            System.out.println("Stage " + RenderStoryMenu.getInstance().getStage());
            ScndGenLegends.getInstance().loadMode(Mode.STANDARD_GAMEPLAY_START);
            ScndGenLegends.getInstance().setSubMode(SubMode.STORY_MODE);
            storyIn();
            if (currentScene == 0) //scene 1
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(0);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(174));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(175));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(176));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(177));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(178));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(179));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(372));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1); //sub               
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(180));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4); //rav                
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(181));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1); //sub
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(182));
                thread.sleep(storyText.length() * tlkSpeed);
                RenderGameplay.getInstance().charPortBlank();


                storyOut(false);
            }

            if (currentScene == 1) //scene 2
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(1);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(183));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(184));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(185));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(186));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(187));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(146));

                storyOut(false);
            }

            if (currentScene == 2) //scene 3
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(2);

                RenderGameplay.getInstance().setCharacterPortrait(2); //lynx
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(188));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0); //raila                
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(189));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2); //lynx
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(190) + " .......");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3); //aisha
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(191));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(192));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(193));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(194));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(195));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(196));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(197));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(198));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(3);
                RenderGameplay.getInstance().charPortBlank();
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(199));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(200));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(201));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(202));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(203));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(3);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(204));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(205));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (currentScene == 3) //scene 4
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(3);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(206));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(207));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(208));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(5);
                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(209));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(210));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(211));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(212));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(213));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(214));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(215));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(216));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(203));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(217));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (currentScene == 4) //scene 5
            {
                thread.sleep(5000);

                RenderGameplay.getInstance().changeStoryBoard(5);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(218) + " .......");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(219));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(220));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10); //sorrowe
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(221));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(222));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4); //ravage
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(223));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(224));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(225));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5); //ade
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(226));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6); //jonah
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(227));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(228));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(229));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);//ade
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(230));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (currentScene == 5) //scene 6
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(5);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(231));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);//ade
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(232));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(233));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(234));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(235));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(236));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(237));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(238));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(239));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(240));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(241));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(242));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(243));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(244));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(245));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(246));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(247));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(248));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(249));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(250));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (currentScene == 6) //scene 7
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(4);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(251));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(252));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(253));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().changeStoryBoard(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(254));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(255));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(256));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(257));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(258));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(259));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(260));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(261));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(262));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }


            if (currentScene == 7) //scene 8
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(6);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(263));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(264));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(265));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(266));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(267));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(268));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = ".......... " + Language.getInstance().get(269));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (currentScene == 8) //scene 9
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(8);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(270));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(271));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(272));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(273));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(274));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(275));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(276));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(277));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(231) + " !!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(278));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(279) + " !!!");
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            //new stage they arrive

            if (currentScene == 9) //scene 10
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(9);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(280) + " !!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(281));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(282));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(283));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(284));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(285));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(286));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(287) + " ...");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(288));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(289) + " !!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(290));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(291));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(292));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(293));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (currentScene == 10) //scene 11
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(11);

                //set difficulty - hard
                Characters.getInstance().setDamageCounter(CharacterState.OPPONENT, 18);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(294) + " !!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(231) + " !!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(295));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(296));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(297));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(0);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(298));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(299) + "!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(300) + " !!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(301) + " ?");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(302) + " !!!!!!!!!!!!!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(303) + " !!!");
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(2);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(304));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(305));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(7);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(306));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }

            if (currentScene == 11) //scene 12
            {
                thread.sleep(5000);
                RenderGameplay.getInstance().changeStoryBoard(10);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(373));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(374));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(375));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(376));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(1);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(377));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().charPortBlank();
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(378));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(4);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(379));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(5);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(380));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(381));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(383));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(11);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(384));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(385));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(9);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(386));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(387));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(388));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(10);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(389));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(390));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(8);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(391));
                thread.sleep(storyText.length() * tlkSpeed);

                RenderGameplay.getInstance().setCharacterPortrait(6);
                RenderGameplay.getInstance().storyText(storyText = Language.getInstance().get(392));
                thread.sleep(storyText.length() * tlkSpeed);

                storyOut(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void pauseDialogue() {
        thread.suspend();
    }

    public void resumeDialogue() {
        thread.resume();
    }

    public void onBackCancel() {
        storyOut(false);
    }

    public void onAccept() {
        if (GameInstance.getInstance().gameOver && RenderGameplay.getInstance().hasWon()) {
            incrementMode();
        }
        startGame();
    }

    /**
     * When you wins a match, move to the next level
     */
    public void incrementMode() {
        if (currentScene < max)
            currentScene = currentScene + 1;
        GameState.getInstance().getLogin().setLastStoryScene(currentScene);
    }

    public void setCurrentScene(int currentScene) {
        this.currentScene = currentScene;
    }

    public void startGame() {
        story(currentScene);
    }
}
