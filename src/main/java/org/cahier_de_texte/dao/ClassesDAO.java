package org.cahier_de_texte.dao;

import org.cahier_de_texte.models.DbConnexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class ClassesDAO {
    Connection con ;
    DbConnexion db = new DbConnexion();
    PreparedStatement pst;

    public void chargeTabClasse(DefaultTableModel model){
        String sql = "SELECT Classes.nom AS classe, " +
                "COALESCE(CONCAT(Utilisateurs.prenom, ' ', Utilisateurs.nom), 'Aucun responsable') AS responsable, " +
                "COALESCE(Utilisateurs.email, 'Neant') AS email, " +
                "COUNT(Etudiants.id) AS effectif " +
                "FROM Classes " +
                "LEFT JOIN Etudiants ON Etudiants.classe_id = Classes.id " +
                "LEFT JOIN Utilisateurs ON Utilisateurs.id = Classes.id_responsable " +
                "GROUP BY Classes.id";


        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            model.setRowCount(0);

            while (res.next()){
                String classe = res.getString("classe");
                String responsable = res.getString("responsable");
                String email = res.getString("email");
                int effectif = res.getInt("effectif");

                model.addRow(new Object[] {classe , responsable , email , effectif});
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }


    public void chargeListeEtudiant(DefaultTableModel model , String classe){
        String sql = "SELECT Etudiants.id AS ID , Etudiants.prenom AS prenom, Etudiants.nom AS nom , Etudiants.email AS email , Classes.nom " +
                "FROM Etudiants " +
                "INNER JOIN Classes ON Etudiants.classe_id = Classes.id " +
                "WHERE Classes.nom = ?";
        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , classe);
            res = pst.executeQuery();
            model.setRowCount(0);

            while (res.next()){

                int id = res.getInt("ID");
                String prenom = res.getString("prenom");
                String nom = res.getString("nom");
                String email = res.getString("email");

                if (verifResponsable(email)) {
                    model.addRow(new Object[]{id, prenom, nom, email, "Responsable"});
                }
                else {
                    model.addRow(new Object[]{id, prenom, nom, email, ""});
                }
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public boolean verifResponsable(String email){
        String sql = "SELECT * FROM Responsables " +
                "INNER JOIN Utilisateurs ON Responsables.id_utilisateur = Utilisateurs.id " +
                "WHERE Utilisateurs.email = ?";
        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            res = pst.executeQuery();

            if(res.next()){
                return true;
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean ajouterClasse(String nomClasse){
        String sql = "INSERT INTO Classes(nom) VALUES (?)";

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1 , nomClasse);
            pst.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean modifierClasse(String nom , String nouveauNom){
        String sql = "UPDATE Classes SET nom = ? WHERE nom = ?";

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , nouveauNom);
            pst.setString(2 , nom);
            pst.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return false;
    }

    public boolean supprimerClasse(String nom){
        String sql = "DELETE FROM Classes WHERE nom = ?";
        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , nom);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erreur ! " + e.getMessage());
        }
        return false;
    }

    public void chargeClasse(JComboBox<String> combo){
        String sql = "SELECT * FROM Classes";
        ResultSet res;
        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            combo.removeAllItems();
            while (res.next()){
                String classe = res.getString("nom");
                combo.addItem(classe);
            }

        }catch(Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }
}
