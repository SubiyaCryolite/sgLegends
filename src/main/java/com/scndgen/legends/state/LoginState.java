package com.scndgen.legends.state;

import com.scndgen.legends.enums.Achievements;
import com.scndgen.legends.enums.CharacterEnum;
import io.github.subiyacryolite.jds.JdsEntity;
import io.github.subiyacryolite.jds.annotations.JdsEntityAnnotation;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import static com.scndgen.legends.state.GameState.MAX_TIME;

/**
 * Created by ifunga on 22/04/2017.
 */
@JdsEntityAnnotation(entityName = "Game Saves", entityId = 2)
public class LoginState extends JdsEntity {
    private final int diff0 = 0,
            diff1 = 1000,
            diff2 = 2500,
            diff3 = 3500,
            diff4 = 4500,
            diff5 = 6000;
    private final int[] txtSpeedArr = new int[]{50, 100, 200, 250};
    private final int[] difficultyArray = {diff0, diff1, diff2, diff3, diff4, diff5};
    //
    private final SimpleStringProperty userName = new SimpleStringProperty("");
    private final SimpleIntegerProperty points = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty playTime = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty numberOfMatches = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach0 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach1 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach2 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach3 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach4 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach5 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach6 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach7 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach8 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach9 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach10 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty ach11 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty wins = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty losses = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty frames = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty textSpeed = new SimpleIntegerProperty(200);
    private final SimpleBooleanProperty isAudioOn = new SimpleBooleanProperty(true);
    private final SimpleIntegerProperty difficulty = new SimpleIntegerProperty(diff3);
    private final SimpleIntegerProperty difficultyDynamic = new SimpleIntegerProperty(diff3);
    private final SimpleIntegerProperty lastStoryScene = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty timeLimit = new SimpleIntegerProperty(MAX_TIME);
    private final SimpleStringProperty graphicsSetting = new SimpleStringProperty("");
    private final SimpleIntegerProperty char0 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char1 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char2 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char3 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char4 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char5 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char6 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char7 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char8 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char9 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char10 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty char11 = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty comicEffectOccurence = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty gameRating = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty usingController = new SimpleBooleanProperty(true);
    private final SimpleIntegerProperty currentLanguage = new SimpleIntegerProperty(0);
    private final SimpleBooleanProperty leftHanded = new SimpleBooleanProperty(false);
    private final SimpleIntegerProperty consecutiveWins = new SimpleIntegerProperty(0);
    private final SimpleFloatProperty musicVolume = new SimpleFloatProperty(100);
    private final SimpleFloatProperty voiceVolume = new SimpleFloatProperty(100);
    private final SimpleFloatProperty soundVolume = new SimpleFloatProperty(100);

    public LoginState() {
        map(LoginFields.USER_NAME, userName);
        map(LoginFields.POINTS, points);
        map(LoginFields.PLAY_TIME, playTime);
        map(LoginFields.NUMBER_OF_MATCHES, numberOfMatches);
        map(LoginFields.D_005, ach0);
        map(LoginFields.D_006, ach1);
        map(LoginFields.D_007, ach2);
        map(LoginFields.D_008, ach3);
        map(LoginFields.D_009, ach4);
        map(LoginFields.D_010, ach5);
        map(LoginFields.D_011, ach6);
        map(LoginFields.D_012, ach7);
        map(LoginFields.D_013, ach8);
        map(LoginFields.D_014, ach9);
        map(LoginFields.D_015, ach10);
        map(LoginFields.D_016, ach11);
        map(LoginFields.WINS, wins);
        map(LoginFields.LOSSES, losses);
        map(LoginFields.FRAMES_PER_SECOND, frames);
        map(LoginFields.TEXT_SPEED, textSpeed);
        map(LoginFields.AUDIO_ON, isAudioOn);
        map(LoginFields.DIFFICULTY, difficulty);
        map(LoginFields.DIFFICULTY_DYNAMIC, difficultyDynamic);
        map(LoginFields.LAST_STORY_SCENE, lastStoryScene);
        map(LoginFields.TIME_LIMIT, timeLimit);
        map(LoginFields.GRAPHICS_SETTING, graphicsSetting);
        map(LoginFields.D_027, char0);
        map(LoginFields.D_028, char1);
        map(LoginFields.D_029, char2);
        map(LoginFields.D_030, char3);
        map(LoginFields.D_031, char4);
        map(LoginFields.D_032, char5);
        map(LoginFields.D_033, char6);
        map(LoginFields.D_034, char7);
        map(LoginFields.D_035, char8);
        map(LoginFields.D_036, char9);
        map(LoginFields.D_037, char10);
        map(LoginFields.D_038, char11);
        map(LoginFields.COMIC_EFFECT_TEXT_OCCURENCE, comicEffectOccurence);
        map(LoginFields.GAME_RATING, gameRating);
        map(LoginFields.USING_CONTROLLER, usingController);
        map(LoginFields.CURRENT_LANGUAGE, currentLanguage);
        map(LoginFields.LEFT_HANDED, leftHanded);
        map(LoginFields.CONSECUTIVE_WINS, consecutiveWins);
        map(LoginFields.MUSIC_VOLUME, musicVolume);
        map(LoginFields.VOICE_VOLUME, voiceVolume);
        map(LoginFields.SOUND_VOLUME, soundVolume);
    }

    public void incrementAchievement(Achievements achievement) {
        switch (achievement) {
            case UPPER_HAND:
                setAch0(getAch0() + 1);
                break;
            case BEAT_THE_ODDS:
                setAch1(getAch1() + 1);
                break;
            case OWNAGE:
                setAch2(getAch2() + 1);
                break;
            case HEARTLESS:
                setAch3(getAch3() + 1);
                break;
            case MEANIE:
                setAch4(getAch4() + 1);
                break;
            case RAGE:
                setAch5(getAch5() + 1);
                break;
            case WINNER:
                setAch6(getAch6() + 1);
                break;
            case BUZZ_KILL:
                setAch7(getAch7() + 1);
                break;
            case CLOSE_CALL:
                setAch8(getAch8() + 1);
                break;
            case ON_A_ROLL:
                setAch9(getAch9() + 1);
                break;
            case HALF_WAY_THROUGH:
                setAch10(getAch10() + 1);
                break;
            case Ach12:
                setAch11(getAch11() + 1);
                break;
        }
    }

    public int getAchievementTriggers(Achievements achievement) {
        switch (achievement) {
            case UPPER_HAND:
                return getAch0();
            case BEAT_THE_ODDS:
                return getAch1();
            case OWNAGE:
                return getAch2();
            case HEARTLESS:
                return getAch3();
            case MEANIE:
                return getAch4();
            case RAGE:
                return getAch5();
            case WINNER:
                return getAch6();
            case BUZZ_KILL:
                return getAch7();
            case CLOSE_CALL:
                return getAch8();
            case ON_A_ROLL:
                return getAch9();
            case HALF_WAY_THROUGH:
                return getAch10();
            case Ach12:
                return getAch11();
        }
        return 0;
    }

    public int getNumberOfTimesAchivementTriggered() {
        int count = 0;
        for (Achievements achievement : Achievements.values()) {
            count += getAchievementTriggers(achievement);
        }
        return count;
    }

    public int getUnlockedAch() {
        int counter = 0;
        for (Achievements achievement : Achievements.values()) {
            if (getAchievementTriggers(achievement) > 0) {
                counter = counter + 1;
            }
        }
        return counter;
    }

    public int getCharacterUsage(CharacterEnum characterEnum) {
        switch (characterEnum) {
            case SUBIYA:
                return getChar0();
            case RAILA:
                return getChar1();
            case LYNX:
                return getChar2();
            case AISHA:
                return getChar3();
            case ADE:
                return getChar4();
            case RAVAGE:
                return getChar5();
            case JONAH:
                return getChar6();
            case ADAM:
                return getChar7();
            case NOVA_ADAM:
                return getChar8();
            case AZARIA:
                return getChar9();
            case SORROWE:
                return getChar10();
            case THING:
                return getChar11();
            default:
                return 0;
        }
    }

    public void setCharacterUsage(CharacterEnum characterEnum) {
        switch (characterEnum) {
            case SUBIYA:
                setChar0(getChar0() + 1);
                break;
            case RAILA:
                setChar1(getChar1() + 1);
                break;
            case LYNX:
                setChar2(getChar2() + 1);
                break;
            case AISHA:
                setChar3(getChar3() + 1);
                break;
            case ADE:
                setChar4(getChar4() + 1);
                break;
            case RAVAGE:
                setChar5(getChar5() + 1);
                break;
            case JONAH:
                setChar6(getChar6() + 1);
                break;
            case ADAM:
                setChar7(getChar7() + 1);
                break;
            case NOVA_ADAM:
                setChar8(getChar8() + 1);
                break;
            case AZARIA:
                setChar9(getChar9() + 1);
                break;
            case SORROWE:
                setChar10(getChar10() + 1);
                break;
            case THING:
                setChar11(getChar11() + 1);
                break;
        }
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(final String userName) {
        this.userName.set(userName);
    }

    public int getPoints() {
        return points.get();
    }

    public void setPoints(final int points) {
        this.points.set(points);
    }

    public int getPlayTime() {
        return playTime.get();
    }

    public void setPlayTime(final int playTime) {
        this.playTime.set(playTime);
    }

    public int getNumberOfMatches() {
        return numberOfMatches.get();
    }

    public void setNumberOfMatches(final int numberOfMatches) {
        this.numberOfMatches.set(numberOfMatches);
    }

    public int getAch0() {
        return ach0.get();
    }

    public void setAch0(final int ach0) {
        this.ach0.set(ach0);
    }

    public int getAch1() {
        return ach1.get();
    }

    public void setAch1(final int ach1) {
        this.ach1.set(ach1);
    }

    public int getAch2() {
        return ach2.get();
    }

    public void setAch2(final int ach2) {
        this.ach2.set(ach2);
    }

    public int getAch3() {
        return ach3.get();
    }

    public void setAch3(final int ach3) {
        this.ach3.set(ach3);
    }

    public int getAch4() {
        return ach4.get();
    }

    public void setAch4(final int ach4) {
        this.ach4.set(ach4);
    }

    public int getAch5() {
        return ach5.get();
    }

    public void setAch5(final int ach5) {
        this.ach5.set(ach5);
    }

    public int getAch6() {
        return ach6.get();
    }

    public void setAch6(final int ach6) {
        this.ach6.set(ach6);
    }

    public int getAch7() {
        return ach7.get();
    }

    public void setAch7(final int ach7) {
        this.ach7.set(ach7);
    }

    public int getAch8() {
        return ach8.get();
    }

    public void setAch8(final int ach8) {
        this.ach8.set(ach8);
    }

    public int getAch9() {
        return ach9.get();
    }

    public void setAch9(final int ach9) {
        this.ach9.set(ach9);
    }

    public int getAch10() {
        return ach10.get();
    }

    public void setAch10(final int ach10) {
        this.ach10.set(ach10);
    }

    public int getAch11() {
        return ach11.get();
    }

    public void setAch11(final int ach11) {
        this.ach11.set(ach11);
    }

    public int getWins() {
        return wins.get();
    }

    public void setWins(final int wins) {
        this.wins.set(wins);
    }

    public int getLosses() {
        return losses.get();
    }

    public void setLosses(final int losses) {
        this.losses.set(losses);
    }

    public int getFrames() {
        return frames.get();
    }

    public void setFrames(int frames) {
        this.frames.set(frames);
    }

    public int getTextSpeed() {
        return textSpeed.get();
    }

    public void setTextSpeed(int textSpeed) {
        this.textSpeed.set(textSpeed);
    }

    public SimpleIntegerProperty framesProperty() {
        return frames;
    }

    public boolean isAudioOn() {
        return isAudioOn.get();
    }

    public void setIsAudioOn(final boolean isAudioOn) {
        this.isAudioOn.set(isAudioOn);
    }

    public int getDifficulty() {
        return difficulty.get();
    }

    public void setDifficulty(final int difficulty) {
        this.difficulty.set(difficulty);
    }

    public int getDifficultyDynamic() {
        return difficultyDynamic.get();
    }

    public void setDifficultyDynamic(final int difficultyDynamic) {
        this.difficultyDynamic.set(difficultyDynamic);
    }

    public int getLastStoryScene() {
        return lastStoryScene.get();
    }

    public void setLastStoryScene(final int lastStoryScene) {
        this.lastStoryScene.set(lastStoryScene);
    }

    public int getTimeLimit() {
        return timeLimit.get();
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit.set(timeLimit);
    }

    public SimpleIntegerProperty timeLimitProperty() {
        return timeLimit;
    }

    public String getGraphicsSetting() {
        return graphicsSetting.get();
    }

    public void setGraphicsSetting(final String graphicsSetting) {
        this.graphicsSetting.set(graphicsSetting);
    }

    public int getChar0() {
        return char0.get();
    }

    public void setChar0(final int char0) {
        this.char0.set(char0);
    }

    public int getChar1() {
        return char1.get();
    }

    public void setChar1(final int char1) {
        this.char1.set(char1);
    }

    public int getChar2() {
        return char2.get();
    }

    public void setChar2(final int char2) {
        this.char2.set(char2);
    }

    public int getChar3() {
        return char3.get();
    }

    public void setChar3(final int char3) {
        this.char3.set(char3);
    }

    public int getChar4() {
        return char4.get();
    }

    public void setChar4(final int char4) {
        this.char4.set(char4);
    }

    public int getChar5() {
        return char5.get();
    }

    public void setChar5(final int char5) {
        this.char5.set(char5);
    }

    public int getChar6() {
        return char6.get();
    }

    public void setChar6(final int char6) {
        this.char6.set(char6);
    }

    public int getChar7() {
        return char7.get();
    }

    public void setChar7(final int char7) {
        this.char7.set(char7);
    }

    public int getChar8() {
        return char8.get();
    }

    public void setChar8(final int char8) {
        this.char8.set(char8);
    }

    public int getChar9() {
        return char9.get();
    }

    public void setChar9(final int char9) {
        this.char9.set(char9);
    }

    public int getChar10() {
        return char10.get();
    }

    public void setChar10(final int char10) {
        this.char10.set(char10);
    }

    public int getChar11() {
        return char11.get();
    }

    public void setChar11(final int char11) {
        this.char11.set(char11);
    }

    public int getComicEffectOccurence() {
        return comicEffectOccurence.get();
    }

    public void setComicEffectOccurence(final int comicEffectOccurence) {
        this.comicEffectOccurence.set(comicEffectOccurence);
    }

    public int getGameRating() {
        return gameRating.get();
    }

    public void setGameRating(final int gameRating) {
        this.gameRating.set(gameRating);
    }

    public boolean getUsingController() {
        return usingController.get();
    }

    public void setUsingController(final boolean usingController) {
        this.usingController.set(usingController);
    }

    public int getCurrentLanguage() {
        return currentLanguage.get();
    }

    public void setCurrentLanguage(final int currentLanguage) {
        this.currentLanguage.set(currentLanguage);
    }

    public boolean isLeftHanded() {
        return leftHanded.get();
    }

    public void setLeftHanded(final boolean leftHanded) {
        this.leftHanded.set(leftHanded);
    }

    public void setConsecutiveWins(int consecutiveWins) {
        this.consecutiveWins.set(consecutiveWins);
    }

    public int getConsecutiveWins() {
        return this.consecutiveWins.get();
    }

    /**
     * Sorts difficulty
     *
     * @return difficulty array index
     */
    public int resolveDifficulty() {
        if (GameState.getInstance().getLogin().getDifficulty() == diff0)
            return 0;
        if (GameState.getInstance().getLogin().getDifficulty() == diff1)
            return 1;
        if (GameState.getInstance().getLogin().getDifficulty() == diff2)
            return 2;
        if (GameState.getInstance().getLogin().getDifficulty() == diff3)
            return 3;
        if (GameState.getInstance().getLogin().getDifficulty() == diff4)
            return 4;
        if (GameState.getInstance().getLogin().getDifficulty() == diff5)
            return 5;
        return -1;
    }

    public int getTxtSpeedConstant(int dex) {
        return txtSpeedArr[dex];
    }

    public int getDifficultyConstant(int dex) {
        return difficultyArray[dex];
    }

    public int mostPopularChar() {
        int highest = 0;
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            if (getCharacterUsage(characterEnum) > highest) {
                highest = getCharacterUsage(characterEnum);
            }
        }
        return highest;
    }

    public CharacterEnum mostPopularCharEnum() {
        int h = 0;
        CharacterEnum highest = CharacterEnum.ADAM;
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            if (getCharacterUsage(characterEnum) > h) {
                h = getCharacterUsage(characterEnum);
                highest = characterEnum;
            }
        }
        return highest;
    }

    public int mostPopularCharPercentage() {
        float ans;
        float count = 0;
        for (CharacterEnum characterEnum : CharacterEnum.values()) {
            count += getCharacterUsage(characterEnum);
        }
        ans = (mostPopularChar() / count) * 100;
        return Math.round(ans);
    }

    public int userAwesomeness() {
        int total = 0;
        int returnThis;
        try {
            for (Achievements achievement : Achievements.values())
                total += (getAchievementTriggers(achievement) * achievement.achievementCategory().points());
            System.out.println("Style points: " + total);
            returnThis = total / getUnlockedAch();
        } catch (Exception e) {
            System.out.println("new user, awesomeness is newbie");
            returnThis = 0;
        }
        return returnThis;
    }

    public boolean isTimeLimited() {
        return getTimeLimit() != MAX_TIME;
    }

    public float getMusicVolume() {
        return musicVolume.get();
    }

    public float getVoiceVolume() {
        return voiceVolume.get();
    }

    public float getSoundVolume() {
        return soundVolume.get();
    }

    public void setMusicVolume(float value) {
        musicVolume.set(value);
    }

    public void setVoiceVolume(float value) {
        voiceVolume.set(value);
    }

    public void setSoundVolume(float value) {
        soundVolume.set(value);
    }
}
