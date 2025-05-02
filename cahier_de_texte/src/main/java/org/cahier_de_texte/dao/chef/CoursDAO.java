package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public boolean ajouterCours(String codeCours , String intituler , int credit){
        String sql = "INSERT INTO Cours (code, intitule, credits) VALUES" +
                "(? , ? , ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1 , codeCours);
            pst.setString(2 , intituler);
            pst.setInt(3 , credit);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur lors de l'insertion du cours ! " + e.getMessage());
        }

        return false;
    }

    public void listeCours(DefaultTableModel model){
        String sql = "SELECT * FROM Cours";

        try{
            pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery();
            model.setRowCount(0);

            while (res.next()){
                model.addRow(new String[]{
                        res.getString("code"),
                        res.getString("intitule"),
                        String.valueOf(res.getInt("credits"))
                });
            }

        }catch (SQLException e){
            System.out.println("Erreur de charger la table des cours ! " + e.getMessage());
        }
    }

    public boolean asignerCours(int idEnseignant , String code , String intitule){
        String sqlCours = "UPDATE Cours SET enseignant_id = ? WHERE code = ? AND intitule = ?";

        try {
            pst = con.prepareStatement(sqlCours);
            pst.setInt(1 , idEnseignant);
            pst.setString(2 , code);
            pst.setString(3 , intitule);
            pst.executeUpdate();

            return true;
        }catch (SQLException e){
            System.out.println("Erreur de modifier l'ID de enseignant dans cours ! " + e.getMessage());
        }
        return false;
    }

    public boolean asignerCoursAjouter(String code , String intitule , int credit , int idEnseignant){
        String sqlCours = "INSERT INTO Cours (code , intitule , credits , enseignant_id) VALUES" +
                "(? , ? , ? , ?)";

        try {
            pst = con.prepareStatement(sqlCours);
            pst.setString(1 , code);
            pst.setString(2 , intitule);
            pst.setInt(3 , credit);
            pst.setInt(4 , idEnseignant);
            pst.executeUpdate();

            return true;

        }catch (SQLException e){
            System.out.println("Erreur lors de l'isertion dans la table cours " + e.getMessage());
        }

        return false;
    }

    public boolean verifEnseignantNull(String code){
        String sql = "SELECT * FROM Cours WHERE enseignant_id IS NULL AND code = ? ";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , code);

            ResultSet res = pst.executeQuery();

            if (res.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return false;
    }


    public void ajouterClasseCours(int idCours , int idClasse){
        String sql = "INSERT INTO ClasseCours(id_cours , id_classe) VALUES" +
                "(? , ?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idCours);
            pst.setInt(2 , idClasse);
            pst.executeUpdate();

        }catch (SQLException e){
            System.out.println("Erreur lor de l'insertion des id cours et id classe " + e.getMessage());
        }
    }

    public void modifierCours(String code , String intitule , int credit , String codeFirst){
        String sql = "UPDATE Cours SET code = ? , intitule = ? , credits = ? WHERE code = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , code);
            pst.setString(2 , intitule);
            pst.setInt(3 , credit);
            pst.setString(4 , codeFirst);
            pst.executeUpdate();
        }catch (SQLException e){
            System.out.println("Erreur lor de modification du cours ! " + e.getMessage());
        }
    }

    public void supprimerCours(String code) {
        String sql = "DELETE FROM Cours WHERE code = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , code);
            pst.executeUpdate();
            System.out.println("Sppression valide");

        }catch (SQLException e){
            System.out.println("Erreur lor de suppression du cours ! " + e.getMessage());
        }
    }

    public int getIDCours(String code){
        int id = 0;

        String sql = "SELECT id FROM Cours WHERE code = ?";

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1 , code);
            ResultSet res = pst.executeQuery();

            if (res.next()){
                id = res.getInt("id");
            }

        }catch (SQLException e){
            System.out.println("Erreur de recuperation de l'id du cours ! " + e.getMessage());
        }
        return id ;
    }

}