package cc.isotopestudio.Crack;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mars on 5/30/2016.
 * Copyright ISOTOPE Studio
 */
public class InfoGUI extends JFrame {
    public static JTextArea text = new JTextArea();

    public void run() {
        JFrame fm = new JFrame("Crack Debug");
        JPanel fp = new JPanel();
        Container con = fm.getContentPane();
        fp.add(text);
        con.add(fp);
        fm.setSize(500, 400);
        fm.setVisible(true);
    }

}
