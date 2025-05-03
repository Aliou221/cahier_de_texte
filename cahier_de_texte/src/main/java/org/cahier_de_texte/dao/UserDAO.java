package org.cahier_de_texte.dao;

import org.cahier_de_texte.model.Role;
import org.cahier_de_texte.model.Users;

import java.sql.*;

public class UserDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public boolean verifeUser(Users user){
        String sql = "SELECT * FROM Utilisateurs WHERE email = ? AND password = ?";
        ResultSet res;

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , user.getEmail());
            pst.setString(2 , user.getPassword());
            res = pst.executeQuery();

            if (res.next()){
                user.setId(res.getInt("id"));
                user.setFirstName(res.getString("prenom"));
                user.setLastName(res.getString("nom"));
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("password"));
                user.setRole(Role.valueOf(res.getString("role")));
                return true;
            }

        }catch(SQLException e){
            System.out.println("Erreur de connexion ! " + e.getMessage());
        }
        return false;
    }

    public boolean modifierUser(Users user){
        String sql = "UPDATE Utilisateurs SET prenom = ? , nom = ? , email = ? WHERE id = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , user.getFirstName());
            pst.setString(2 , user.getLastName());
            pst.setString(3 , user.getEmail());
            pst.setInt(4 , user.getId());
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
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

    public boolean updatePassword(String email , String password){
        String sql = "UPDATE Utilisateurs SET password = ? WHERE email = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , password);
            pst.setString(2 , email);
            pst.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println("Erreur de modification de mot de passe ! " + e.getMessage() + " pour l'email " + email + " et le mot de passe " + password);
        }
        return false;
    }

    public boolean verifEmail(String email){
        String sql = "SELECT * FROM Utilisateurs WHERE email = ?";
        ResultSet res;

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , email);
            res = pst.executeQuery();

            if (res.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Erreur de connexion ! " + e.getMessage());
        }

        return false;
    }

}
