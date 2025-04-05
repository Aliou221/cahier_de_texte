package org.cahier_de_texte.model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;

// Classe de gestion des opérations BDD pour les utilisateurs
public class UserDAO {

    DbConnexion db = new DbConnexion(); // Connexion à la base de données
    Connection con = db.getConnection();
    PreparedStatement pst;
    ResultSet res;

    String role , nom; // Informations récupérées à la connexion

    // Vérifie les informations de connexion de l'utilisateur
    public boolean verifeInfo(Users user){

        String sql = "SELECT nom , role FROM Utilisateurs WHERE email = ? AND password = ?";

        try{
            pst = con.prepareStatement(sql);

            pst.setString(1 , user.getEmail());
            pst.setString(2 , user.getPassword());

            res = pst.executeQuery();

            if(res.next()){
                role = res.getString("role");
                nom = res.getString("nom");
                return true;
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    // Retourne le rôle de l'utilisateur connecté
    public String getUserRole(){
       return role;
    }
    // Retourne le nom de l'utilisateur connecté
    public String getUserName(){
       return nom;
    }

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

    // Modifie les informations d’un utilisateur existant
    public boolean modiferUser(Users user){

        String sql = "UPDATE Utilisateurs SET prenom = ? , nom = ? , email = ? WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);

            pst.setString(1 , user.getFirstName());
            pst.setString(2 , user.getLastName());
            pst.setString(3 , user.getEmail());
            pst.setInt(4 , user.getId());

            pst.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    // Supprime un utilisateur par ID
    public boolean supprimerUser(Users user){
        String sql = "DELETE FROM Utilisateurs WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1 , user.getId());
            pst.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

}
