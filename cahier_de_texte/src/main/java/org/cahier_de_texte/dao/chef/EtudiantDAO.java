package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;
import org.cahier_de_texte.model.Etudiants;
import org.cahier_de_texte.model.Users;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class EtudiantDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    private int idUser = 0;
    public EtudiantDAO() {
    }
    public EtudiantDAO(int idUser) {
        this.idUser = idUser;
    }

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

    public boolean modifRespo(Users user, String emailEtudiant) {
            try {
                // Mise à jour dans la table Utilisateurs
                String sqlUtilisateur = "UPDATE Utilisateurs SET prenom = ?, nom = ?, email = ? WHERE email = ?";
                pst = con.prepareStatement(sqlUtilisateur);
                pst.setString(1, user.getFirstName());
                pst.setString(2, user.getLastName());
                pst.setString(3, user.getEmail());
                pst.setString(4, emailEtudiant);
                pst.executeUpdate();
                System.out.println("Utilisateur a ete modifier : " + emailEtudiant);
                return true;
            } catch (SQLException ex) {
                System.out.println("Erreur ! " + ex.getMessage());
            }
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

    public boolean deleteResponsable(String email){
        String sql = "DELETE FROM Utilisateurs WHERE email = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , email);
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
        String sql = "SELECT Etudiants.id AS ID , Etudiants.responsable , Etudiants.prenom AS prenom, Etudiants.nom AS nom , Etudiants.email AS email , Classes.nom " +
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
                boolean responsable = res.getBoolean("responsable");

                model.addRow(new Object[]{id, prenom, nom, email, responsable});
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public boolean defResponsable(int idEtudiant){
        String sql = "UPDATE Etudiants SET responsable = ? WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setBoolean(1 , true);
            pst.setInt(2 , idEtudiant);
            pst.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Erreur de definiton du responsable " + e.getMessage());
        }
        return false;
    }

    public boolean retireResponsable(int idEtudiant){
        String sql = "UPDATE Etudiants SET responsable = ? WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setBoolean(1 , false);
            pst.setInt(2 , idEtudiant);
            pst.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Erreur de definiton du responsable " + e.getMessage());
        }
        return false;
    }

    public boolean ajouterResponsableUser(String prenom , String nom , String email , String password , String classe){
        String sql = "INSERT INTO Utilisateurs (prenom , nom , email , password , role) VALUES" +
                "(? , ? , ? , ? , ?)";

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1 , prenom);
            pst.setString(2 , nom);
            pst.setString(3 , email);
            pst.setString(4 , password);
            pst.setString(5 , "RESPONSABLE");
            pst.executeUpdate();

            String sql2 = "SELECT * FROM Utilisateurs WHERE email = ?";
            pst = con.prepareStatement(sql2);
            pst.setString(1 , email);
            ResultSet res = pst.executeQuery();

            if(res.next()){
                idUser = res.getInt("id");
                return updateClasse(classe, idUser);
            }else{
                return false;
            }

        }catch (SQLException e){
            System.out.println("Erreur d'insertion de responsable dans la table Utilisateurs " + e.getMessage());
        }
        return false;
    }

    public boolean deleteResponsableUser(String email){
        String sql = "DELETE FROM Utilisateurs WHERE email = ?";

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1 , email);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur de suppression du responsable " + e.getMessage());
        }

        return false;
    }

    public boolean updateClasse(String classe , int idUserResponsable){
        String sql = "UPDATE Classes SET id_responsable = ? WHERE nom = ?";

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idUserResponsable);
            pst.setString(2 , classe);
            pst.executeUpdate();

            System.out.println("Responsable de la classe " + classe + " a ete mis a jour avec l'id " + idUserResponsable);
            return true;

        }catch (SQLException e){
            System.out.println("Erreur de modifier le responsable de la classe " + e.getMessage());
        }

        return false;
    }

    public boolean verifMailEtudiant(String email){
        String sql = "SELECT * FROM Etudiants " +
                "WHERE email = ?";
        ResultSet res;
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            res = pst.executeQuery();

            if(res.next()){
                idUser = res.getInt("id");
                return true;
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return false;
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
                idUser = res.getInt("id");
                return true;
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
        return false;
    }

    public int getIdUser(String email){
        return idUser;
    }
}