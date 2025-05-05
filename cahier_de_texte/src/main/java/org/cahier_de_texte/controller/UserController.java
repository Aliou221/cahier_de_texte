package org.cahier_de_texte.controller;

import org.cahier_de_texte.dao.UserDAO;
import org.cahier_de_texte.model.Users;

import javax.swing.*;

public class UserController {
    UserDAO userDAO;

    public UserController(){
        userDAO = new UserDAO();
    }

    public Users login(String email , String password){
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(password);
        boolean verifUser = userDAO.verifeUser(user);

        if (verifUser){
            return user;
        }
        return null;
    }

    public Users modifierUser(String firstName , String lastName , String email , int id){
        Users user = new Users();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setId(id);

        if (userDAO.modifierUser(user)){
            JOptionPane.showMessageDialog(null, "Enseignant a été modifié avec succés !", null, JOptionPane.INFORMATION_MESSAGE);
            return user;
        }else{
            JOptionPane.showMessageDialog(null, "Erreur de modification !", null, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Users deleteUser(int id){
        Users user = new Users();
        user.setId(id);

        if(userDAO.supprimerUser(user)){
            JOptionPane.showMessageDialog(null, "Enseignant a été supprimé avec succés !", null, JOptionPane.INFORMATION_MESSAGE);
            return user;
        }else{
            JOptionPane.showMessageDialog(null, "Erreur de suppression !", null, JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void updatePassword(String email , String password){
            boolean updateValide = this.userDAO.updatePassword(email , password);
            if (!updateValide){
                System.out.println("Modification de mot de passe impossible !");
            }else{
                JOptionPane.showMessageDialog(null, "Votre mot de passe à été modifié avec succée", null, JOptionPane.INFORMATION_MESSAGE);
            }
    }

    public boolean verifUser(String email){
        return this.userDAO.verifEmail(email);
    }
}