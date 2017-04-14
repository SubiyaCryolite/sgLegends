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
package com.scndgen.legends.drawing;

import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.arefactored.mode.CharacterSelectionScreen;
import com.scndgen.legends.characters.Raila;
import com.scndgen.legends.engine.JenesisLanguage;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.windows.WindowMain;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImage;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the character and opponent
 */
public class RenderCharacterSelectionScreen extends CharacterSelectionScreen {

    private Font bigFont, normalFont;
    private String[] charDesc = new String[numOfCharacters];
    private Image[] charNorm = new Image[numOfCharacters];
    private Image[] charBlur = new Image[numOfCharacters];
    private Image[] charPrev = new Image[numOfCharacters];
    private Image[] oppPrev = new Image[numOfCharacters];
    private Image[] charNames = new Image[numOfCharacters];
    private Image fg1, fg2, fg3, bg3;
    private boolean loadResources;
    private Image charBack, oppBack, charHold, p1, p2, fight, charDescPic, oppDescPic;
    private static RenderCharacterSelectionScreen instance;

    public static synchronized RenderCharacterSelectionScreen getInstance() {
        if (instance == null) {
            instance = new RenderCharacterSelectionScreen();
        }
        return instance;
    }

    private RenderCharacterSelectionScreen() {
        opacInc = 0.025f;
        loadResources = true;
        setBorder(BorderFactory.createEmptyBorder());
    }

    private void loadResources() {
        if (loadResources) {
            pix = new JenesisImage();
            loadCaps();
            loadDesc();
            loadResources = false;
        }
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

    private void loadDesc() {
        statsChar[0] = JenesisLanguage.getInstance().getLine(134);
        statsChar[1] = JenesisLanguage.getInstance().getLine(135);
        statsChar[2] = JenesisLanguage.getInstance().getLine(136);
        statsChar[3] = JenesisLanguage.getInstance().getLine(137);
        statsChar[4] = JenesisLanguage.getInstance().getLine(138);
        statsChar[5] = JenesisLanguage.getInstance().getLine(139);
        statsChar[6] = JenesisLanguage.getInstance().getLine(140);
        statsChar[7] = JenesisLanguage.getInstance().getLine(141);
        statsChar[8] = JenesisLanguage.getInstance().getLine(142);
        statsChar[9] = JenesisLanguage.getInstance().getLine(143);
        statsChar[10] = JenesisLanguage.getInstance().getLine(144);
        statsChar[11] = JenesisLanguage.getInstance().getLine(145);
    }

    public Dimension setPreferredSize() {
        return new Dimension(852, 480);
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        loadResources();
        g2d.setFont(normalFont);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, 852, 480);


        g2d.drawImage(bg3, 0, 0, this);

        g2d.drawImage(fg1, xCordCloud, 0, this);
        g2d.drawImage(fg2, xCordCloud2, 0, this);
        g2d.drawImage(fg3, 0, 0, this);

        if (p1Opac < (1.0f - opacInc)) {
            p1Opac = p1Opac + opacInc;
        }

        if (opacChar < (1.0f - (opacInc * 2))) {
            opacChar = opacChar + (opacInc * 2);
        }

        g2d.setColor(Color.BLACK);
        g2d.setComposite(makeComposite(0.70f));
        g2d.fillRect(0, 0, 853, 480);
        g2d.setComposite(makeComposite(1.0f));

        //character preview DYNAMIC change
        if (characterSelected != true) {
            g2d.setComposite(makeComposite(p1Opac));
            g2d.drawImage(charPrev[charPrevLoicIndex], charXcap + x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(charNames[charPrevLoicIndex], 40 - x, 400, this);
        }

        //opponent preview DYNAMIC change, only show if quick match, should change sprites
        if (characterSelected && opponentSelected != true && LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer)) {
            g2d.setComposite(makeComposite(p1Opac));
            g2d.drawImage(oppPrev[charPrevLoicIndex], 512 - x, charYcap, this);
            g2d.setComposite(makeComposite(1.0f));
            g2d.drawImage(charNames[charPrevLoicIndex], 553 + x, 400, this);
        }


        //if character selected draw FIXED prev
        if (characterSelected) {
            g2d.drawImage(charPrev[charPrevLoc], charXcap, charYcap, this);
            g2d.drawImage(charNames[selectedCharIndex], 40, 380, this);
        }

        //if opp selected, draw FIXED prev
        if (opponentSelected) {
            g2d.drawImage(oppPrev[oppPrevLoc], 512, charYcap, this);
            g2d.drawImage(charNames[selectedOppIndex], 553, 380, this);
        }

        //all char caps in this segment
        {

            g2d.drawImage(charHold, 311, 0, this);

            if (well()) {
                if (characterSelected != true) {
                    g2d.drawImage(charBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                }

                if (characterSelected && opponentSelected != true && LoginScreen.getInstance().getMenu().getMain().getGameMode().equalsIgnoreCase(WindowMain.singlePlayer)) {
                    g2d.drawImage(oppBack, (hPos - hSpacer) + (hSpacer * hIndex), firstLine + (vSpacer * vIndex), this);
                }
            }

            col = 0;
            for (int i = 0; i < (charNorm.length / 3); i++) {
                {
                    g2d.drawImage(charBlur[col], hPos, firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 1 && vIndex == i && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos, firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(charBlur[col], hPos + (hSpacer * 1), firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 2 && vIndex == i && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos + (hSpacer * 1), firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(charBlur[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                    //normal
                    if (bothArentSelected() && hIndex == 3 && vIndex == i && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos + (hSpacer * 2), firstLine + (vSpacer * i), this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }
                lastRow = i;
            }

            int rem = charBlur.length % 3;

            if (rem == 1) {
                g2d.drawImage(charBlur[col], hPos, firstLine, this);
                //normal
                if (bothArentSelected() && hIndex == 1 && vIndex == lastRow && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                {
                    g2d.setComposite(makeComposite(opacChar));
                    g2d.drawImage(charNorm[col], hPos, firstLine, this);
                    charPrevLoicIndex = col;
                    g2d.setComposite(makeComposite(1.0f));
                }
            } else if (rem == 2) {
                {
                    g2d.drawImage(charBlur[col], hPos, firstLine, this);
                    //normal
                    if (bothArentSelected() && hIndex == 1 && vIndex == lastRow && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col], hPos, firstLine, this);
                        charPrevLoicIndex = col;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }

                {
                    g2d.drawImage(charBlur[col + 1], hPos + hSpacer, firstLine + vSpacer, this);
                    //normal
                    if (bothArentSelected() && hIndex == 2 && vIndex == lastRow && allPlayers[((vIndex * 3) + hIndex) - 1] == 0)//clear
                    {
                        g2d.setComposite(makeComposite(opacChar));
                        g2d.drawImage(charNorm[col + 1], hPos + hSpacer, firstLine + vSpacer, this);
                        charPrevLoicIndex = col + 1;
                        g2d.setComposite(makeComposite(1.0f));
                    }
                    col++;
                }
            }

            if (characterSelected && opponentSelected) {
                g2d.drawImage(fight, 0, 0, this);
                g2d.setFont(bigFont);
                g2d.setColor(Color.WHITE);
                g2d.drawString("<< " + JenesisLanguage.getInstance().getLine(146) + " >>", (852 - g2d.getFontMetrics(bigFont).stringWidth("<< " + JenesisLanguage.getInstance().getLine(146) + " >>")) / 2, 360);
                g2d.drawString("<< " + JenesisLanguage.getInstance().getLine(147) + " >>", (852 - g2d.getFontMetrics(bigFont).stringWidth("<< " + JenesisLanguage.getInstance().getLine(147) + " >>")) / 2, 390);
            }
        }
        g2d.setFont(normalFont);
        g2d.setColor(Color.white);
        if (characterSelected == false) {
            g2d.drawImage(charDescPic, 0, 0, this);
            g2d.drawString(statsChar[charPrevLoicIndex], 4 + x, 18);
        }
        if (characterSelected && opponentSelected == false) {
            g2d.drawImage(oppDescPic, 452, 450, this);
            g2d.drawString(statsChar[charPrevLoicIndex], 852 - g2d.getFontMetrics(normalFont).stringWidth(statsChar[charPrevLoicIndex]) + x, 468);
        }
        g2d.drawImage(p1, 0, 180, this);
        g2d.drawImage(p2, 812, 180, this);
        if (x < 0) {
            x = x + 2;
        }
        JenesisGlassPane.getInstance().overlay(g2d, this);
        g.drawImage(volatileImg, 0, 0, this);
    }

    private void loadCaps() {
        bigFont = LoginScreen.getInstance().getMyFont(LoginScreen.extraTxtSize);
        normalFont = LoginScreen.getInstance().getMyFont(LoginScreen.normalTxtSize);
        oppDescPic = pix.loadImageFromToolkitNoScale("images/charInfoO.png");
        charDescPic = pix.loadImageFromToolkitNoScale("images/charInfoC.png");
        setMenuContent(CharacterEnum.RAILA);
        setMenuContent(CharacterEnum.SUBIYA);
        setMenuContent(CharacterEnum.LYNX);
        setMenuContent(CharacterEnum.AISHA);
        setMenuContent(CharacterEnum.RAVAGE);
        setMenuContent(CharacterEnum.ADE);
        setMenuContent(CharacterEnum.JONAH);
        setMenuContent(CharacterEnum.NOVA_ADAM);
        setMenuContent(CharacterEnum.ADAM);
        setMenuContent(CharacterEnum.AZARIA);
        setMenuContent(CharacterEnum.SORROWE);
        setMenuContent(CharacterEnum.THING);
        charBack = pix.loadImageFromToolkitNoScale("images/selChar.png");
        oppBack = pix.loadImageFromToolkitNoScale("images/selOpp.png");
        charHold = pix.loadImageFromToolkitNoScale("images/charHold.png");
        Image[] tmp = SpecialDrawModeRender.getPics();
        bg3 = tmp[0];
        fg1 = tmp[1];
        fg2 = tmp[2];
        fg3 = tmp[3];
        p1 = pix.loadImageFromToolkitNoScale("images/player1.png");
        p2 = pix.loadImageFromToolkitNoScale("images/player2.png");
        fight = pix.loadImageFromToolkitNoScale("images/fight.png");
        charDesc[0] = Raila.class.getName();
    }

    public void setMenuContent(CharacterEnum characterEnum) {
        charNorm[characterEnum.index()] = pix.loadImageFromToolkitNoScale("images/" + characterEnum.data() + "/cap.png");
        charBlur[characterEnum.index()] = pix.loadImageFromToolkitNoScale("images/" + characterEnum.data() + "/capB.png");
        charNames[characterEnum.index()] = pix.loadImageFromToolkitNoScale("images/" + characterEnum.data() + "/name.png");
        charPrev[characterEnum.index()]  = pix.loadImageFromToolkitNoScale("images/" + characterEnum.data() + "/Prev.png");
        oppPrev[characterEnum.index()]  = pix.loadImageFromToolkitNoScale("images/" + characterEnum.data() + "/PrevO.png");
    }
}