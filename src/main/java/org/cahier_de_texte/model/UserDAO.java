package org.cahier_de_texte.model;


import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;
    ResultSet res;

    String role , nom;

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

    public String getUserRole(){
       return role;
    }
    public String getUserName(){
       return nom;
    }


    public boolean addUser(Users user , String role){
        String sql = "INSERT INTO Utilisateurs (prenom , nom , email , password , role) VALUES" +
                "(?,?,?,?,?)";
        try{
            pst = con.prepareStatement(sql);

            pst.setString(1 , user.getFirstName());
            pst.setString(2 , user.getLastName());
            pst.setString(3 , user.getEmail());
            pst.setString(4 , user.getPassword());
            pst.setString(5 , role);

            pst.executeUpdate();
            return true;

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }


}
