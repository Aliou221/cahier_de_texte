package org.cahier_de_texte.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class FontLoader {
    public static void loadCustomFonts() {
        try {
            InputStream is = FontLoader.class.getResourceAsStream("/fonts/Poppins-Regular.ttf");
            assert is != null;
            Font poppins = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(poppins);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la police : " + e.getMessage());
        }
    }
}


