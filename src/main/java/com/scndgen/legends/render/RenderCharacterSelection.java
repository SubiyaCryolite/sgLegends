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
package com.scndgen.legends.render;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.ScndGenLegends;
import com.scndgen.legends.characters.Characters;
import com.scndgen.legends.characters.Raila;
import com.scndgen.legends.enums.CharacterEnum;
import com.scndgen.legends.enums.ModeEnum;
import com.scndgen.legends.enums.Player;
import com.scndgen.legends.enums.SubMode;
import com.scndgen.legends.mode.CharacterSelection;
import com.scndgen.legends.ui.Event;
import com.scndgen.legends.ui.UiItem;
import io.github.subiyacryolite.enginev1.Loader;
import io.github.subiyacryolite.enginev1.Overlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;

import static com.sun.javafx.tk.Toolkit.getToolkit;

/**
 * @author: Ifunga Ndana
 * @class: drawPrevChar
 * This class creates a graphical preview of the characterEnum and opponent
 */
public class RenderCharacterSelection extends CharacterSelection {

    private static RenderCharacterSelection instance;
    private final String[] charDesc = new String[numOfCharacters];
    private final Image[] thumbnailNormal = new Image[numOfCharacters];
    private final Image[] thumbnailBlurred = new Image[numOfCharacters];
    private final Image[] portrait = new Image[numOfCharacters];
    private final Image[] portraitFlipped = new Image[numOfCharacters];
    private final Image[] caption = new Image[numOfCharacters];
    private final HashMap<Integer, UiItem> uiElements = new HashMap<>();
    private final UiItem subiya;
    private final UiItem raila;
    private final UiItem lynx;
    private final UiItem aisha;
    private final UiItem ade;
    private final UiItem ravage;
    private final UiItem jonah;
    private final UiItem adam;
    private final UiItem novaAdam;
    private final UiItem azaria;
    private final UiItem sorrowe;
    private final UiItem thing;
    private Font bigFont, normalFont;
    private Image fg1, fg2, fg3, bg3;
    private Image charBack, oppBack, charHold, p1, p2, fight, charDescPic, oppDescPic;
    private CharacterEnum hoveredCharacter;

    public RenderCharacterSelection() {
        opacInc = 0.025f;
        loadAssets = true;
        uiElements.clear();
        Event commonEvent = new Event() {
            public void onHover() {
                animatePortratit();
            }

            public void onAccept() {
                if (!selectedCharacter || !selectedOpponent) {
                    switch (hoveredCharacter) {
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
                } else {
                    //if both character and opponent selected move on to stage select after second accept
                    ScndGenLegends.getInstance().loadMode(ModeEnum.STAGE_SELECT_SCREEN);
                }
            }

            public void onBackCancel() {
                if (selectedOpponent) {
                    selectedOpponent = false;
                } else if (selectedCharacter) {
                    selectedCharacter = false;
                } else {
                    ScndGenLegends.getInstance().loadMode(ModeEnum.MAIN_MENU);
                }
            }

            public void onRight() {
                setActiveItem(activeItem.getRight());
            }

            public void onLeft() {
                setActiveItem(activeItem.getLeft());
            }

            public void onUp() {
                setActiveItem(activeItem.getUp());
            }

            public void onDown() {
                setActiveItem(activeItem.getDown());
            }
        };

        (subiya = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.SUBIYA;
            }
        });
        subiya.addJenesisEvent(commonEvent);

        (raila = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.RAILA;
            }
        });
        raila.addJenesisEvent(commonEvent);

        (lynx = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.LYNX;
            }
        });
        lynx.addJenesisEvent(commonEvent);

        (aisha = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.AISHA;
            }
        });
        aisha.addJenesisEvent(commonEvent);

        (ade = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.ADE;
            }
        });
        ade.addJenesisEvent(commonEvent);

        (ravage = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.RAVAGE;
            }
        });
        ravage.addJenesisEvent(commonEvent);

        (jonah = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.JONAH;
            }
        });
        jonah.addJenesisEvent(commonEvent);

        (adam = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.ADAM;
            }
        });
        adam.addJenesisEvent(commonEvent);

        (novaAdam = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.NOVA_ADAM;
            }
        });
        novaAdam.addJenesisEvent(commonEvent);

        (azaria = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.AZARIA;
            }
        });
        azaria.addJenesisEvent(commonEvent);

        (sorrowe = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.SORROWE;
            }
        });
        sorrowe.addJenesisEvent(commonEvent);

        (thing = new UiItem()).addJenesisEvent(new Event() {
            public void onHover() {
                hoveredCharacter = CharacterEnum.THING;
            }
        });
        thing.addJenesisEvent(commonEvent);

        uiElements.put(CharacterEnum.SUBIYA.index(), subiya);
        uiElements.put(CharacterEnum.RAILA.index(), raila);
        uiElements.put(CharacterEnum.LYNX.index(), lynx);
        uiElements.put(CharacterEnum.AISHA.index(), aisha);
        uiElements.put(CharacterEnum.ADE.index(), ade);
        uiElements.put(CharacterEnum.RAVAGE.index(), ravage);
        uiElements.put(CharacterEnum.JONAH.index(), jonah);
        uiElements.put(CharacterEnum.ADAM.index(), adam);
        uiElements.put(CharacterEnum.NOVA_ADAM.index(), novaAdam);
        uiElements.put(CharacterEnum.AZARIA.index(), azaria);
        uiElements.put(CharacterEnum.SORROWE.index(), sorrowe);
        uiElements.put(CharacterEnum.THING.index(), thing);

        //set up down, left right
        int total = uiElements.size();
        for (int index = 0; index < total; index++) {
            if (index > 0)
                uiElements.get(index).setLeft(uiElements.get(index - 1));
            if ((index + columns) < total)
                uiElements.get(index).setDown(uiElements.get(index + columns));
        }
    }

    public static synchronized RenderCharacterSelection getInstance() {
        if (instance == null) {
            instance = new RenderCharacterSelection();
        }
        return instance;
    }

    @Override
    public void newInstance() {
        super.newInstance();
        Characters.getInstance().resetCharacters();
        setActiveItem(uiElements.get(0));
    }

    public void loadAssetsIml() {
        loader = new Loader();
        loadCaps();
        loadDesc();
        loadAssets = false;
    }

    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void render(final GraphicsContext gc, final double w, final double h) {
        loadAssets();
        gc.setFont(normalFont);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 852, 480);
        gc.drawImage(bg3, 0, 0);
        gc.drawImage(fg1, xCordCloud, 0);
        gc.drawImage(fg2, xCordCloud2, 0);
        gc.drawImage(fg3, 0, 0);
        if (p1Opac < (1.0f - opacInc)) {
            p1Opac = p1Opac + opacInc;
        }
        if (opacChar < (1.0f - (opacInc * 2))) {
            opacChar = opacChar + (opacInc * 2);
        }
        gc.setFill(Color.BLACK);
        gc.setGlobalAlpha(0.70f);
        gc.fillRect(0, 0, 853, 480);
        gc.setGlobalAlpha(1.0f);
        //characterEnum preview DYNAMIC change
        if (selectedCharacter != true) {
            gc.setGlobalAlpha((p1Opac));
            gc.drawImage(portrait[hoveredCharacter.index()], charXcap + x, charYcap);
            gc.setGlobalAlpha((1.0f));
            gc.drawImage(caption[hoveredCharacter.index()], 40 - x, 400);
        }
        //opponent preview DYNAMIC change, only show if quick match, should change sprites
        if (selectedCharacter && selectedOpponent != true && ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER) {
            gc.setGlobalAlpha((p1Opac));
            gc.drawImage(portraitFlipped[hoveredCharacter.index()], 512 - x, charYcap);
            gc.setGlobalAlpha((1.0f));
            gc.drawImage(caption[hoveredCharacter.index()], 553 + x, 400);
        }
        //if characterEnum selected draw FIXED prev
        if (selectedCharacter) {
            gc.drawImage(portrait[charPrevLoc], charXcap, charYcap);
            gc.drawImage(caption[selectedCharIndex], 40, 380);
        }
        //if opp selected, draw FIXED prev
        if (selectedOpponent) {
            gc.drawImage(portraitFlipped[oppPrevLoc], 512, charYcap);
            gc.drawImage(caption[selectedOppIndex], 553, 380);
        }
        gc.drawImage(charHold, 311, 0);
        for (int row = 0; row <= (thumbnailNormal.length / columns); row++) {
            for (int column = 0; column < columns; column++) {
                int computedPosition = (columns * row) + column;
                if (computedPosition >= numOfCharacters) continue;
                boolean characterOpenToSelection = (selectedCharIndex != computedPosition || selectedOppIndex != computedPosition);
                boolean notAllCharactersSelect = bothArentSelected();
                if (notAllCharactersSelect && uiElements.get(computedPosition).isHovered() && characterOpenToSelection)//clear
                {
                    if (!selectedCharacter) {
                        gc.drawImage(charBack, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                    }
                    if (selectedCharacter && !selectedOpponent && ScndGenLegends.getInstance().getSubMode() == SubMode.SINGLE_PLAYER) {
                        gc.drawImage(oppBack, hPos + (hSpacer * column), firstLine + (vSpacer * row));
                    }
                }
                gc.setGlobalAlpha(opacChar);
                drawImage(gc, thumbnailNormal[computedPosition], hPos + (hSpacer * column), firstLine + (vSpacer * row), uiElements.get(computedPosition));
                gc.setGlobalAlpha((1.0f));
            }
        }

        if (selectedCharacter && selectedOpponent) {
            gc.drawImage(fight, 0, 0);
            gc.setFont(bigFont);
            gc.setFill(Color.WHITE);
            gc.fillText("<< " + Language.getInstance().get(146) + " >>", (852 - getToolkit().getFontLoader().computeStringWidth("<< " + Language.getInstance().get(146) + " >>", gc.getFont())) / 2, 360);
            gc.fillText("<< " + Language.getInstance().get(147) + " >>", (852 - getToolkit().getFontLoader().computeStringWidth("<< " + Language.getInstance().get(147) + " >>", gc.getFont())) / 2, 390);
        }
        gc.setFont(normalFont);
        gc.setFill(Color.WHITE);
        if (!selectedCharacter) {
            //select character
            gc.drawImage(charDescPic, 0, 0);
            gc.fillText(statsChar[hoveredCharacter.index()], 4 + x, 18);
        }
        if (selectedCharacter && !selectedOpponent) {
            //select opponent
            gc.drawImage(oppDescPic, 452, 450);
            gc.fillText(statsChar[hoveredCharacter.index()], 852 - getToolkit().getFontLoader().computeStringWidth(statsChar[hoveredCharacter.index()], gc.getFont()) + x, 468);
        }
        gc.drawImage(p1, 0, 180);
        gc.drawImage(p2, 812, 180);
        if (x < 0)

        {
            x = x + 2;
        }
        Overlay.getInstance().

                overlay(gc, x, y);

    }

    private void loadCaps() {
        bigFont = getMyFont(LoginScreen.extraTxtSize);
        normalFont = getMyFont(LoginScreen.normalTxtSize);
        oppDescPic = loader.load("images/charInfoO.png");
        charDescPic = loader.load("images/charInfoC.png");
        loadUiContent(CharacterEnum.RAILA);
        loadUiContent(CharacterEnum.SUBIYA);
        loadUiContent(CharacterEnum.LYNX);
        loadUiContent(CharacterEnum.AISHA);
        loadUiContent(CharacterEnum.RAVAGE);
        loadUiContent(CharacterEnum.ADE);
        loadUiContent(CharacterEnum.JONAH);
        loadUiContent(CharacterEnum.NOVA_ADAM);
        loadUiContent(CharacterEnum.ADAM);
        loadUiContent(CharacterEnum.AZARIA);
        loadUiContent(CharacterEnum.SORROWE);
        loadUiContent(CharacterEnum.THING);
        charBack = loader.load("images/selChar.png");
        oppBack = loader.load("images/selOpp.png");
        charHold = loader.load("images/charHold.png");
        Image[] tmp = RenderMainMenu.getInstance().getPics();
        bg3 = tmp[0];
        fg1 = tmp[1];
        fg2 = tmp[2];
        fg3 = tmp[3];
        p1 = loader.load("images/player1.png");
        p2 = loader.load("images/player2.png");
        fight = loader.load("images/fight.png");
        charDesc[0] = Raila.class.getName();
    }

    public void loadUiContent(CharacterEnum characterEnum) {
        thumbnailNormal[characterEnum.index()] = loader.load("images/" + characterEnum.data() + "/cap.png");
        thumbnailBlurred[characterEnum.index()] = loader.load("images/" + characterEnum.data() + "/capB.png");
        caption[characterEnum.index()] = loader.load("images/" + characterEnum.data() + "/name.png");
        portrait[characterEnum.index()] = loader.load("images/" + characterEnum.data() + "/Prev.png");
        portraitFlipped[characterEnum.index()] = loader.load("images/" + characterEnum.data() + "/PrevO.png");
    }


    private void loadDesc() {
        statsChar[0] = Language.getInstance().get(134);
        statsChar[1] = Language.getInstance().get(135);
        statsChar[2] = Language.getInstance().get(136);
        statsChar[3] = Language.getInstance().get(137);
        statsChar[4] = Language.getInstance().get(138);
        statsChar[5] = Language.getInstance().get(139);
        statsChar[6] = Language.getInstance().get(140);
        statsChar[7] = Language.getInstance().get(141);
        statsChar[8] = Language.getInstance().get(142);
        statsChar[9] = Language.getInstance().get(143);
        statsChar[10] = Language.getInstance().get(144);
        statsChar[11] = Language.getInstance().get(145);
    }
}
