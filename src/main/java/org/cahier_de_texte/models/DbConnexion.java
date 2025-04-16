package org.cahier_de_texte.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnexion {
    Connection con;
    String url = "jdbc:mysql://localhost/cahier_de_texte";
    String dbUser = "root";
    String dbPassword = "";

    public Connection getConnection() {

        try{
            con = DriverManager.getConnection(url , dbUser , dbPassword);
            System.out.println("Connexion reussi !");
        }catch (Exception e){
            System.out.println("Erreur de connexion ! " + e.getMessage());
        }
        return con;
    }
}
