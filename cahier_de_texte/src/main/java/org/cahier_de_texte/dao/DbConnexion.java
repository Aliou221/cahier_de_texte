package org.cahier_de_texte.dao;

import java.sql.*;

public class DbConnexion {
    Connection con ;
    String URL = "jdbc:mysql://localhost:3306/cahier_de_texte";
    String DB_USERNAME = "root";
    String DB_PASSWORD = "";

    public Connection getConnection(){
        try{
            con = DriverManager.getConnection(URL , DB_USERNAME , DB_PASSWORD);
        }catch(SQLException ex){
            System.out.println("Erreur de connexion ! " + ex.getMessage());
        }
        return con;
    }

}
