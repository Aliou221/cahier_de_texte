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

    String role;

    public boolean verifeInfo(Users user){

        String sql = "SELECT role FROM Utilisateurs WHERE email = ? AND password = ?";

        try{
            pst = con.prepareStatement(sql);

            pst.setString(1 , user.getEmail());
            pst.setString(2 , user.getPassword());

            res = pst.executeQuery();

            if(res.next()){
                role = res.getString("role");
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


}
