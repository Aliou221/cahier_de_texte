package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;
import org.cahier_de_texte.model.Etudiants;
import org.cahier_de_texte.model.Users;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EtudiantDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public boolean ajouterEtudiant(Etudiants etudiant , String classe){
        String sql = "INSERT INTO Etudiants (prenom, nom, email, classe_id)" +
                "VALUES (? , ? , ? , ?)";
        int id = getIdClasse(classe);

        try{
            pst = con.prepareStatement(sql);

            pst.setString(1 , etudiant.getFirstName());
            pst.setString(2 , etudiant.getLastName());
            pst.setString(3 , etudiant.getEmail());
            pst.setInt(4 , id);

            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean modifierEtudiant(Etudiants etudiant , int id){
        String sql = "UPDATE Etudiants SET prenom = ? , nom = ? , email = ? WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , etudiant.getFirstName());
            pst.setString(2 , etudiant.getLastName());
            pst.setString(3 , etudiant.getEmail());
            pst.setInt(4 , id);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean modifRespo(Users user, int etudiantId) {
//        if (verifResponsable(user.getEmail())) {
            int utilisateurId = getIdResponsable(user.getEmail());

            try {
                // Mise à jour dans la table Etudiants
                String sqlEtudiant = "UPDATE Etudiants SET prenom = ?, nom = ?, email = ? WHERE id = ?";
                pst = con.prepareStatement(sqlEtudiant);
                pst.setString(1, user.getFirstName());
                pst.setString(2, user.getLastName());
                pst.setString(3, user.getEmail());
                pst.setInt(4, etudiantId);
                pst.executeUpdate();

                // Mise à jour dans la table Utilisateurs
                String sqlUtilisateur = "UPDATE Utilisateurs SET prenom = ?, nom = ?, email = ? WHERE id = ?";
                pst = con.prepareStatement(sqlUtilisateur);
                pst.setString(1, user.getFirstName());
                pst.setString(2, user.getLastName());
                pst.setString(3, user.getEmail());
                pst.setInt(4, utilisateurId);
                pst.executeUpdate();

                return true;
            } catch (SQLException ex) {
                System.out.println("Erreur ! " + ex.getMessage());
            }
//        }
        return false;
    }

    public boolean supprimerEtudiant(int id){
        String sql = "DELETE FROM Etudiants WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);

            pst.setInt(1 , id);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public int getIdClasse(String classe){
        int id = 0;
        String sql = "SELECT * FROM Classes WHERE nom = ?";
        ResultSet res;

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , classe);

            res = pst.executeQuery();

            if (res.next()){
                id = res.getInt("id");
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return id;
    }

    public void chargeListeEtudiant(DefaultTableModel model , String classe){
        String sql = "SELECT Etudiants.id AS ID , Etudiants.prenom AS prenom, Etudiants.nom AS nom , Etudiants.email AS email , Classes.nom " +
                "FROM Etudiants " +
                "INNER JOIN Classes ON Etudiants.classe_id = Classes.id " +
                "WHERE Classes.nom = ?";
        ResultSet res;

        try{
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

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public boolean verifResponsable(String email){
        String sql = "SELECT * FROM Utilisateurs " +
                "WHERE email = ?";
        ResultSet res;
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            res = pst.executeQuery();

            if(res.next()){
                return true;
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return false;
    }

    public int getIdResponsable(String email){
        int idRespo = 0;
        String sql = "SELECT * FROM Utilisateurs " +
                "WHERE email = ?";
        ResultSet res;
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            res = pst.executeQuery();

            if(res.next()){
                idRespo = res.getInt("id");
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return idRespo;
    }
}