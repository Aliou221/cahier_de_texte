package org.cahier_de_texte;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.awt.*;

public class TabPane extends JFrame {
    public TabPane(){
        setSize(800 , 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        setLayout(new FlowLayout());

        JTabbedPane tab = new JTabbedPane();
        tab.setPreferredSize(new Dimension(700 , 500));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        panel1.add(new JLabel("Panel 1"));
        panel2.add(new JLabel("Panel 2"));
        panel3.add(new JLabel("Panel 3"));

        tab.addTab("panel 1" , panel1);
        tab.addTab("panel 2" , panel2);
        tab.addTab("panel 3" , panel3);

        add(tab);
    }

    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(new FlatDarculaLaf());
        new TabPane();
    }
}
