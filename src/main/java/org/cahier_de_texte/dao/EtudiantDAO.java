package org.cahier_de_texte.dao;

import org.cahier_de_texte.models.DbConnexion;
import org.cahier_de_texte.models.Etudiants;
import org.cahier_de_texte.models.Users;

import javax.swing.*;
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
    public boolean defResponsable(Users user , String classe){
        String sql = "INSERT INTO Utilisateurs (prenom , nom , email , password , role) VALUES" +
                "(? , ? , ? , ? , 'Responsable')";
        ResultSet res;
        nomClasse = classe;

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
                inserResponsable(idUser);
                insertClasse(idUser);
            }

            return true;
        }catch (Exception e){
            System.out.println("Erreur ! "+ e.getMessage());
        }
        return false;
    }

    public void inserResponsable(int idResponsable){
        String sql = "INSERT INTO Responsables(id_utilisateur) VALUES" +
                "(?)";
        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idResponsable);
            pst.executeUpdate();
            System.out.println("Id ajouter responsable");

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public void insertClasse(int idResponsable){
        String sql = "INSERT INTO Classes (nom , id_responsable) VALUES " +
                "(? , ?)";
        String sqlClasseName = "SELECT nom FROM Classes WHERE id = ?";
        int id = idClasse(nomClasse);
        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sqlClasseName);
            pst.setInt(1 , id);
            res = pst.executeQuery();

            if (res.next()){
                try{
                    PreparedStatement pst1 = con.prepareStatement(sql);
                    pst1.setString(1 , res.getString("nom"));
                    pst1.setInt(2 , idResponsable);
                    pst1.executeUpdate();
                    System.out.println("Id ajouter dans la classe");

                }catch (Exception e){
                    System.out.println("Erreur ! " + e.getMessage());
                }
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }





}
