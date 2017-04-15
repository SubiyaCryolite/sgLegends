package com.scndgen.legends.render;

import com.scndgen.legends.enums.Overlay;
import com.scndgen.legends.scene.MainMenu;
import com.scndgen.legends.windows.MainWindow;
import com.scndgen.legends.windows.WindowAbout;
import io.github.subiyacryolite.enginev1.JenesisGlassPane;
import io.github.subiyacryolite.enginev1.JenesisImageLoader;
import io.github.subiyacryolite.enginev1.JenesisRender;

import java.awt.*;

/**
 * Created by ifunga on 15/04/2017.
 */
public class RenderMainMenu extends MainMenu implements JenesisRender {

    private JenesisImageLoader pix = new JenesisImageLoader();
    private Image sgLogo, ndanaSol;
    private Image pointer;
    private Image fg, foreGroundA, pic1, foreGroundB;
    private static RenderMainMenu instance;

    public static synchronized RenderMainMenu getInstance() {
        if (instance == null)
            instance = new RenderMainMenu();
        return instance;
    }

    @Override
    public void newInstance() {

    }

    @Override
    public void loadAssets() {
        if (!loadAssets) return;
        ndanaSol = pix.loadImage("logo/ndana_sol.png");
        sgLogo = pix.loadImage("images/sglogo.png");
        pointer = pix.loadImage("images/pointer.png");
        //0 to 8hrs :: Morning
        if (time >= 0 && time <= 9) {
            pic1 = pix.loadImage("images/blur/bgBG1.png");
            fg = pix.loadImage("images/blur/bgBG1fg.png");
            foreGroundA = pix.loadImage("images/blur/bgBG1a.png");
            foreGroundB = pix.loadImage("images/blur/bgBG1b.png");
        } //9hrs to 16hrs :: Afternoon
        else if (time > 9 && time <= 16) {
            pic1 = pix.loadImage("images/blur/bgBG6.png");
            fg = pix.loadImage("images/blur/bgBG6fg.png");
            foreGroundA = pix.loadImage("images/blur/bgBG6a.png");
            foreGroundB = pix.loadImage("images/blur/bgBG6b.png");
        } //17 to 24hrs :: Evening
        else if (time > 16 && time <= 24) {
            pic1 = pix.loadImage("images/blur/bgBG5.png");
            fg = pix.loadImage("images/blur/bgBG5fg.png");
            foreGroundA = pix.loadImage("images/blur/bgBG5a.png");
            foreGroundB = pix.loadImage("images/blur/bgBG5b.png");
        }
        loadAssets = false;
    }

    @Override
    public void cleanAssets() {
        loadAssets = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        createBackBuffer();
        loadAssets();
        g2d.setComposite(makeComposite(1));
        if (fadeOutFeedback && (feedBackOpac > 0.0f)) {
            feedBackOpac = feedBackOpac - 0.025f;
        }
        g2d.drawImage(pic1, 0, 0, this);
        g2d.drawImage(fg, 0, 0, this);
        g2d.drawImage(foreGroundB, xCordCloud, yCordCloud, this);
        g2d.drawImage(foreGroundA, xCordCloud2, yCordCloud2, this);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(makeComposite(0.50f));
        g2d.fillRect(0, 0, screenWidth, screenHeight);
        g2d.setComposite(makeComposite(1.0f));
        g2d.drawImage(sgLogo, 0, 0, this);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font1);
        if (overlay == Overlay.PRIMARY) {
            menuItem = 0;
            if (menuIndex == menuItem) {
                menuItmStr = MainWindow.storyMode;
                g2d.drawImage(pointer, xMenu - 18, yMenu - 15, this);
                g2d.drawString(itemz[1], xMenu, yMenu);
            } else {
                g2d.drawString(itemz[0], xMenu, yMenu);
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = "vs1";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[3], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[2], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

                /*
                if (menuIndex == menuItem) {
                menuItmStr="vs2";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[5], xMenu, yMenu + (fontSize * menuItem));
                } else {
                g2d.drawString(itemz[4], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;
                 */

            if (menuIndex == menuItem) {
                menuItmStr = MainWindow.lanHost;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[7], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[6], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = MainWindow.lanClient;
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[9], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[8], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

                /*
                if (menuIndex == menuItem) {
                menuItmStr = "leaders";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[23], xMenu, yMenu + (fontSize * menuItem));
                } else {
                g2d.drawString(itemz[22], xMenu, yMenu + (fontSize * menuItem));
                }
                menuItem++;
                 */

            if (menuIndex == menuItem) {
                menuItmStr = "stats";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[11], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[10], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = "ach";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[21], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[20], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = "tutorial";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[25], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[24], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = "options";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[13], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[12], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;


            if (menuIndex == menuItem) {
                menuItmStr = "controls";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[15], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[14], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = "logout";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[23], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[22], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = "about";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[17], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[16], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;

            if (menuIndex == menuItem) {
                menuItmStr = "exit";
                g2d.drawImage(pointer, xMenu - 18, yMenu + (fontSize * menuItem) - 15, this);
                g2d.drawString(itemz[19], xMenu, yMenu + (fontSize * menuItem));
            } else {
                g2d.drawString(itemz[18], xMenu, yMenu + (fontSize * menuItem));
            }
            menuItem++;
        }

        JenesisGlassPane.getInstance().overlay(g2d, this);
        g2d.drawString("The SCND Genesis: Legends " + RenderGameplay.getInstance().getVersionStr() + " | copyright © " + WindowAbout.year() + " Ifunga Ndana.", 10, screenHeight - 10);
        g2d.setComposite(makeComposite(feedBackOpac));
        mess = "Press 'F' to provide Feedback";
        g2d.drawString(mess, 590, 14);
        mess = "Press 'B' to visit our Blog";
        g2d.drawString(mess, 590, 30);
        mess = "Press 'L' to like us on Facebook";
        g2d.drawString(mess, 590, 46);
        g2d.drawLine(590 - 5, 0, 590 - 5, 46);
        g2d.setComposite(makeComposite(1.0f));
        g2d.setColor(Color.WHITE);
        if (overlay == Overlay.STATISTICS) {
            achachievementLocker.drawStats(g2d, this);
        }
        if (overlay == Overlay.ACHIEVEMENTS) {
            achachievementLocker.drawAch(g2d, this);
        }
        if (overlay == Overlay.TUTORIAL) {
            tutorial.draw(g2d, this);
        }
        if (xCordCloud < -960) {
            xCordCloud = screenWidth;
        } else {
            xCordCloud = xCordCloud - 1;
        }
        if (xCordCloud2 < -960) {
            xCordCloud2 = screenWidth;
        } else {
            xCordCloud2 = xCordCloud2 - 2;
        }
        if (xCordCloud3 < -960) {
            xCordCloud3 = screenWidth;
        } else {
            xCordCloud3 = xCordCloud3 - 3;
        }
        if (openOpac > 0.0f) {
            if (openOpac <= 1.0f) {
                g2d.setComposite(makeComposite(openOpac));
            }
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, 852, 480);
            if (openOpac > 2.0f) {
                g2d.setComposite(makeComposite(1.0f));
            } else if (openOpac <= 2.0f && openOpac > 1.0f) {
                g2d.setComposite(makeComposite(openOpac - 1.0f));
            } else {
                g2d.setComposite(makeComposite(0f));
            }
            g2d.drawImage(ndanaSol, 0, 0, this);
            openOpac = openOpac - 0.0125f;
        }
        g.drawImage(volatileImg, 0, 0, this);
    }


    /**
     * Get menu images for use in character select screen
     *
     * @return pictures
     */
    public Image[] getPics() {
        return new Image[]{pic1, foreGroundA, foreGroundB, fg};
    }
}