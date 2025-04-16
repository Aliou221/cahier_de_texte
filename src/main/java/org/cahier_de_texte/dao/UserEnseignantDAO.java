package org.cahier_de_texte.dao;

import org.cahier_de_texte.models.DbConnexion;
import org.cahier_de_texte.models.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class UserEnseignantDAO {
    DbConnexion db = new DbConnexion(); // Connexion à la base de données
    Connection con = db.getConnection();
    PreparedStatement pst;
    ResultSet res;

    // Ajoute un nouvel enseignant dans la table Utilisateurs
    public boolean addEnseignant(Users user ){

        String sql = "INSERT INTO Utilisateurs (prenom , nom , email , password , role) VALUES" +
                "(?,?,?,?,'Enseignant')";
        try{
            pst = con.prepareStatement(sql ,  Statement.RETURN_GENERATED_KEYS);

            pst.setString(1 , user.getFirstName());
            pst.setString(2 , user.getLastName());
            pst.setString(3 , user.getEmail());
            pst.setString(4 , user.getPassword());
            pst.executeUpdate();

            res = pst.getGeneratedKeys();

            if(res.next()){
                int idUser = res.getInt(1);
                insertEnseignant(idUser);
            }

            return true;

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    // Insère l'enseignant dans la table Enseignants (liée à Utilisateurs)
    public void insertEnseignant(int idEnseignant){
        String sql = "INSERT INTO Enseignants(id_utilisateur) VALUES" +
                "(?)";
        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idEnseignant);
            pst.executeUpdate();

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    // Charge tous les enseignants dans une tableau
    public void chargerTabEnseignant(DefaultTableModel modelTabEnseignant){
        String sql = "SELECT * FROM Utilisateurs WHERE role LIKE 'Enseignant'";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            modelTabEnseignant.setRowCount(0);

            while (res.next()){
                int id = res.getInt(1);
                String prenom = res.getString("prenom");
                String nom = res.getString("nom");
                String mail = res.getString("email");
                Timestamp dateCreation = res.getTimestamp("date_creation");
                String formattedDate = dateFormat.format(dateCreation);

                modelTabEnseignant.addRow(new Object[]{id, prenom, nom ,mail, formattedDate});
            }

        } catch (Exception exp) {
            System.out.println("Erreur ! " + exp.getMessage());
        }
    }

    public void Enseignants(JComboBox<String> combo){
        String sql = "SELECT * FROM Utilisateurs WHERE role = ?";
        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , "Enseignant");

            res = pst.executeQuery();
            combo.removeAllItems();

            while (res.next()){
                String enseignant = res.getInt("id") + " - " +
                        res.getString("prenom") + " " +
                        res.getString("nom") + " - " +
                        res.getString("email");

                combo.addItem(enseignant);
            }

        }catch(Exception e){
           System.out.println("Erreur ! " + e.getMessage());
        }
    }
}
