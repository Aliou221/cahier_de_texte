package org.cahier_de_texte;

import com.formdev.flatlaf.FlatLightLaf;
import org.cahier_de_texte.ui.LoginUI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception{
        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("Button.arc" , 8);
        UIManager.put("TextComponent.arc" , 8);

        Font robotoFont = new Font("Roboto", Font.PLAIN, 14);
        UIManager.put("Label.font", robotoFont);
        UIManager.put("Button.font", robotoFont);
        UIManager.put("TextField.font", robotoFont);

        new LoginUI().setVisible(true);
    }
}