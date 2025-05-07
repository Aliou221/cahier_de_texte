package org.cahier_de_texte;

import com.formdev.flatlaf.FlatLightLaf;
import org.cahier_de_texte.ui.auth.LoginUI;
import org.cahier_de_texte.utils.FontLoader;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception{
        FontLoader.loadCustomFonts();

        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("Button.arc", 8);
        UIManager.put("TextComponent.arc", 8);

        Font poppinsFont = new Font("Poppins", Font.PLAIN, 15);
        UIManager.put("Label.font", poppinsFont);
        UIManager.put("Button.font", poppinsFont);
        UIManager.put("TextField.font", poppinsFont);
        UIManager.put("TextArea.font", poppinsFont);
        UIManager.put("Table.font", poppinsFont);
        UIManager.put("TabbedPane.font", poppinsFont);
        UIManager.put("ComboBox.font", poppinsFont);

        new LoginUI().setVisible(true);
    }
}