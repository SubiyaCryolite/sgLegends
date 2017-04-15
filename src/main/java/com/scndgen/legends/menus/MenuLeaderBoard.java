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
package com.scndgen.legends.menus;

import com.scndgen.legends.Language;
import com.scndgen.legends.LoginScreen;
import com.scndgen.legends.network.SqlQuery;
import com.scndgen.legends.render.RenderGameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author ifunga
 */
public class MenuLeaderBoard extends JFrame implements ActionListener {

    private JPanel line1, line2, line3;
    private JButton upload, view, close;
    private String dbName = "sql09.freemysql.net/scndrating";
    private String passWd = "user=subiyacryolite&password=dbHomie";
    private boolean notLoaded = true;
    private Box box;
    private SqlQuery viewer;
    private boolean viewerNotLoaded = true, notInitislied = true;
    private String database;
    private Connection con;
    private Statement stmt;
    private ResultSet rs;

    public MenuLeaderBoard() {
        super(Language.getInstance().getLine(98));
        if (notLoaded) {
            upload = new JButton(Language.getInstance().getLine(97));
            upload.addActionListener(this);
            view = new JButton(Language.getInstance().getLine(96));
            view.addActionListener(this);
            close = new JButton(Language.getInstance().getLine(95));
            close.addActionListener(this);
            line1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line1.add(view);
            line2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line2.add(upload);
            line3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            line3.add(close);
            box = new Box(BoxLayout.Y_AXIS);
            box.add(line1);
            box.add(line2);
            box.add(line3);
            add(box);
            pack();
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
            notLoaded = false;
        }
    }

    public static void main(String[] args) {
        new MenuLeaderBoard();
    }

    public void reappear() {
        show();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == view) {
            if (viewerNotLoaded) {
                viewer = new SqlQuery();
                viewerNotLoaded = false;
            } else {
                viewer.reappear();
            }
        }
        if (ae.getSource() == upload) {
            upload();
        }
        if (ae.getSource() == close) {
            dispose();
        }
    }

    private void upload() {
        try {
            try {
                if (notInitislied) {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    database = "jdbc:mysql://" + dbName + "?" + passWd;
                    con = DriverManager.getConnection(database);
                    System.out.println("Connected to " + database);
                    notInitislied = false;
                }
                stmt = con.createStatement();

                try {
                    //if exists error shall be thrown
                    System.out.println("Brand new");
                    stmt.executeUpdate("INSERT INTO user(id, rating, userName, userCountry, gameVersion, versionInt, userTotalMatches, userWin, userLoss, favCharacter, userPoints) VALUES ('" + LoginScreen.getInstance().usrCode + "', " + LoginScreen.getInstance().getInstance().getGameRating() + ", '" + LoginScreen.getInstance().strUser + "', '" + LoginScreen.getInstance().getInstance().getCCode() + "', '" + RenderGameplay.getInstance().getVersionStr() + "', " + RenderGameplay.getInstance().getVersionInt() + ", " + (LoginScreen.getInstance().getInstance().win + LoginScreen.getInstance().getInstance().loss) + ", " + LoginScreen.getInstance().getInstance().win + ", " + LoginScreen.getInstance().getInstance().loss + ", " + LoginScreen.getInstance().getInstance().mostPopularChar() + ", " + LoginScreen.getInstance().strPoint + ")");
                } catch (Exception e) {
                    System.out.println("Override old record");
                    stmt.executeUpdate("UPDATE user SET id='" + LoginScreen.getInstance().usrCode + "', rating=" + LoginScreen.getInstance().getInstance().getGameRating() + ", userName='" + LoginScreen.getInstance().strUser + "', userCountry='" + LoginScreen.getInstance().getInstance().getCCode() + "', gameVersion='" + RenderGameplay.getInstance().getVersionStr() + "', versionInt=" + RenderGameplay.getInstance().getVersionInt() + ", userTotalMatches=" + (LoginScreen.getInstance().getInstance().win + LoginScreen.getInstance().getInstance().loss) + ", userWin=" + LoginScreen.getInstance().getInstance().win + ", userLoss=" + LoginScreen.getInstance().getInstance().loss + ", favCharacter=" + LoginScreen.getInstance().getInstance().mostPopularChar() + ", userPoints=" + LoginScreen.getInstance().strPoint + " WHERE id='" + LoginScreen.getInstance().usrCode + "'");

                }
                LoginScreen.getInstance().getInstance().saveConfigFile();
                JOptionPane.showMessageDialog(null, Language.getInstance().getLine(99), Language.getInstance().getLine(102), JOptionPane.PLAIN_MESSAGE);
            } catch (Exception e) {
                System.err.println(e);
                notInitislied = true;
                JOptionPane.showMessageDialog(null, Language.getInstance().getLine(100), Language.getInstance().getLine(101), JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, Language.getInstance().getLine(100), Language.getInstance().getLine(101), JOptionPane.ERROR_MESSAGE);
            notInitislied = true;
        }
    }
}
