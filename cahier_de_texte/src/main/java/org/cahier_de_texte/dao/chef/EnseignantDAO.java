package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;
import org.cahier_de_texte.model.Users;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;

public class EnseignantDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;
    ResultSet res;

    // Ajoute un nouvel enseignant dans la table Utilisateurs
    public boolean addEnseignant(Users user ){
        String sql = "INSERT INTO Utilisateurs (prenom , nom , email , password , role) VALUES" +
                "(?,?,?,?,'ENSEIGNANT')";
        try{
            pst = con.prepareStatement(sql ,  Statement.RETURN_GENERATED_KEYS);

            pst.setString(1 , user.getFirstName());
            pst.setString(2 , user.getLastName());
            pst.setString(3 , user.getEmail());
            pst.setString(4 , user.getPassword());
            pst.executeUpdate();

            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    // Charge tous les enseignants dans une tableau
    public void chargerTabEnseignant(DefaultTableModel model){
        String sql = "SELECT * FROM Utilisateurs WHERE role LIKE 'ENSEIGNANT'";
        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            model.setRowCount(0);

            while (res.next()){
                int id = res.getInt(1);
                String prenom = res.getString("prenom");
                String nom = res.getString("nom");
                String mail = res.getString("email");
                Timestamp dateCreation = res.getTimestamp("date_creation");
                String formattedDate = dateFormat.format(dateCreation);

                model.addRow(new Object[]{id, prenom, nom ,mail, formattedDate});
            }

        } catch (SQLException exp) {
            System.out.println("Erreur ! dans EnseignantDao" + exp.getMessage());
        }
    }
}
