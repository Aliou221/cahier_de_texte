package org.cahier_de_texte.dao;

import org.cahier_de_texte.models.DbConnexion;
import org.cahier_de_texte.models.Etudiants;
import org.cahier_de_texte.models.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class EtudiantDAO {

    Connection con;
    DbConnexion db = new DbConnexion();
    PreparedStatement pst;


    public boolean ajouterEtudiant(Etudiants etudiant , String classe){
        String sql = "INSERT INTO Etudiants (prenom, nom, email, classe_id)" +
                "VALUES (? , ? , ? , ?)";
        int id = idClasse(classe);

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1 , etudiant.getPrenom());
            pst.setString(2 , etudiant.getNom());
            pst.setString(3 , etudiant.getEmail());
            pst.setInt(4 , id);

            pst.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean modifierEtudiant(Etudiants etudiant , int id){
        String sql = "UPDATE Etudiants SET prenom = ? , nom = ? , email = ? WHERE id = ?";

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1 , etudiant.getPrenom());
            pst.setString(2 , etudiant.getNom());
            pst.setString(3 , etudiant.getEmail());
            pst.setInt(4 , id);
            pst.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean supprimerEtudiant(int id){
        String sql = "DELETE FROM Etudiants WHERE id = ?";

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);

            pst.setInt(1 , id);
            pst.executeUpdate();

            return true;

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }


    public int idClasse(String classe){
        int id = 0;
        String sql = "SELECT * FROM Classes WHERE nom = ?";
        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , classe);

            res = pst.executeQuery();

            if (res.next()){
                id = res.getInt("id");
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return id;
    }

    //Responsable & classe
    String nomClasse;
    int idU = 0;
    public boolean defResponsable(Users user , String classe){
        ResultSet res;
        nomClasse = classe;

        String sql = "INSERT INTO Utilisateurs (prenom , nom , email , password , role) VALUES" +
                "(? , ? , ? , ? , 'Responsable')";

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);

            pst.setString(1 , user.getFirstName());
            pst.setString(2 , user.getLastName());
            pst.setString(3 , user.getEmail());
            pst.setString(4 , user.getPassword());
            pst.executeUpdate();

            res = pst.getGeneratedKeys();

            if (res.next()){
                int idUser = res.getInt(1);
                idU = idUser;
                inserResponsable(idUser);
            }

            return true;
        }catch (Exception e){
            System.out.println("Erreur ! "+ e.getMessage());
        }
        return false;
    }

    public void inserResponsable(int idUser){
        String sql = "INSERT INTO Responsables(id_utilisateur) VALUES" +
                "(?)";
        ResultSet res;
        try{
            pst = con.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1 , idUser);
            pst.executeUpdate();

            res = pst.getGeneratedKeys();

            if (res.next()){
                int idRespo = res.getInt(1);
                insertClasse(idRespo);
                System.out.println("Id ajouter dans la classe");
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public void insertClasse(int idResponsable){
        int id = idClasse(nomClasse);
        String sql;

        try{
            con = db.getConnection();
            if (id != 0) {

                // Classe déjà existante → on met à jour le responsable
                sql = "UPDATE Classes SET id_responsable = ? WHERE id = ?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, idU);
                pst.setInt(2, id);
                pst.executeUpdate();
                System.out.println("Responsable mis à jour pour la classe existante");

            } else {

                // Classe inexistante → on l'insère avec responsable
                sql = "INSERT INTO Classes (nom, id_responsable) VALUES (?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, nomClasse);
                pst.setInt(2, idResponsable);
                pst.executeUpdate();
                System.out.println("Nouvelle classe insérée avec responsable");
            }

        } catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

}
