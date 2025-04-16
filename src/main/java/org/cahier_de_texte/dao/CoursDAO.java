package org.cahier_de_texte.dao;

import org.cahier_de_texte.models.DbConnexion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CoursDAO {
    DbConnexion db = new DbConnexion(); // Connexion à la base de données
    Connection con = db.getConnection();
    PreparedStatement pst;

    public void chargeCours(JComboBox<String> combo){
        String sql = "SELECT * FROM Cours";
        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            combo.removeAllItems();
            while (res.next()){
                String cours = res.getString("code") + " - " + res.getString("intitule");
                combo.addItem(cours);
            }

        }catch(Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public boolean insertCours(String code , String nom , int credit , int idEnseignant){
        String sql = "INSERT INTO Cours(code , intitule , credits , enseignant_id)" +
                "VALUES(?,?,?,?)";
        try{
            pst = con.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return false;
    }

    public void classCoursIdCours(int idCours){

    }
}
