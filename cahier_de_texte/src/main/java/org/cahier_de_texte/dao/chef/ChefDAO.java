package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChefDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public int nbEnseignant(){
        String sql = "SELECT COUNT(*) FROM Utilisateurs WHERE role = ?";
        int nbre = 0;

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1 , "ENSEIGNANT");
            ResultSet res = pst.executeQuery();

            if (res.next()){
                nbre = res.getInt(1);
            }
        }catch(SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbre;
    }

    public int nbResponsable(){
        int nbreResponsable = 0 ;
        String sql = "SELECT COUNT(*) FROM Utilisateurs WHERE role LIKE 'RESPONSABLE'";

        try{
            pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery();

            if(res.next()){
                nbreResponsable = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreResponsable;
    }

    public int nbCours(){
        int nbreCours = 0 ;
        String sql = "SELECT COUNT(*) FROM Cours";

        try{
            pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery();

            if(res.next()){
                nbreCours = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreCours;
    }

    public int nbEtudiant(){
        int nbreEtudiant = 0 ;
        String sql = "SELECT COUNT(*) FROM Etudiants";

        try{
            pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery();

            if(res.next()){
                nbreEtudiant = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return nbreEtudiant;
    }

    public int nbClasse(){
        int nbreClasse = 0 ;
        String sql = "SELECT COUNT(*) FROM Classes";

        try{
            pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery();

            if(res.next()){
                nbreClasse = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreClasse;
    }

    public int nbSeanceValide(){
        int nbreSeanceValide = 0 ;
        String sql = "SELECT COUNT(*) FROM Validations";

        try{
            pst = con.prepareStatement(sql);
            ResultSet res = pst.executeQuery();

            if(res.next()){
                nbreSeanceValide = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreSeanceValide;
    }

    // Charge tous les responsable et leurs classes dans une tableau
    public void chargeTabResponsableClasse(DefaultTableModel model){

        String sql = "SELECT CONCAT(Utilisateurs.prenom, ' ', Utilisateurs.nom) AS Responsable, " +
                "Utilisateurs.email AS Email, Classes.nom AS Classe " +
                "FROM Utilisateurs " +
                "INNER JOIN Classes ON Classes.id_responsable = Utilisateurs.id " +
                "WHERE Utilisateurs.role = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,"RESPONSABLE");

            ResultSet res = pst.executeQuery();
            model.setRowCount(0);

            while (res.next()){
                String responsable = res.getString("Responsable");
                String email = res.getString("Email");
                String classe = res.getString("Classe");

                model.addRow(new Object[]{responsable, email ,classe,});
            }

        } catch (Exception exp) {
            System.out.println("Erreur ! " + exp.getMessage());
        }
    }

    // Charge tous les enseignants et leurs cours asigner dans une tableau
    public void chargeTabEnseignantCours(DefaultTableModel model){

        String sql = "SELECT Utilisateurs.id, CONCAT(Utilisateurs.prenom , ' ' , Utilisateurs.nom) AS Enseignant, " +
                "Utilisateurs.email AS Email , Cours.intitule AS Cours , Classes.nom AS Classe " +
                "FROM Utilisateurs " +
                "INNER JOIN Cours ON Cours.enseignant_id = Utilisateurs.id " +
                "INNER JOIN ClasseCours ON ClasseCours.id_cours = Cours.id " +
                "INNER JOIN Classes ON ClasseCours.id_classe = Classes.id " +
                "WHERE Utilisateurs.role = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , "ENSEIGNANT");
            ResultSet res = pst.executeQuery();

            model.setRowCount(0);

            while (res.next()){
                int id = res.getInt("id");
                String Enseignant = res.getString("Enseignant");
                String email = res.getString("Email");
                String cours = res.getString("Cours");
                String classe = res.getString("classe");

                model.addRow(new Object[]{id ,Enseignant, email , cours,classe});
            }

        } catch (Exception exp) {
            System.out.println("Erreur ! " + exp.getMessage());
        }
    }

    public void listeEnseignant(JComboBox<String> combo){
        String sql = "SELECT * FROM Utilisateurs WHERE role = ?";
        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , "ENSEIGNANT");

            ResultSet res = pst.executeQuery();
            combo.removeAllItems();
            combo.addItem("");
            while (res.next()){
                String enseignant = res.getInt("id")        + " - " +
                                    res.getString("prenom") + " " +
                                    res.getString("nom")    + " - " +
                                    res.getString("email");

                combo.addItem(enseignant);
            }

        }catch(Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public void listeCours(JComboBox<String> combo){
        String sql = "SELECT * FROM Cours";
        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            combo.removeAllItems();
            combo.addItem("");
            while (res.next()){
                String cours = res.getInt("id") + " - " + res.getString("code") + " - " + res.getString("intitule");
                combo.addItem(cours);
            }

        }catch(Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public void listeClasse(JComboBox<String> combo){
        String sql = "SELECT * FROM Classes";
        ResultSet res;
        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            combo.removeAllItems();
            combo.addItem("");

            while (res.next()){
                String classe = res.getInt("id") + " - " + res.getString("nom");
                combo.addItem(classe);
            }

        }catch(Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }
}