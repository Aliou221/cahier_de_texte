package org.cahier_de_texte.controller;

import org.cahier_de_texte.vue.DashBordChefView;
import org.cahier_de_texte.model.UserDAO;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.vue.LoginView;

import javax.swing.*;

public class UserController {
    UserDAO userDAO;
    LoginView log ;

    public UserController(LoginView log) {
        this.log = log;
        userDAO = new UserDAO();
    }


    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(null , "Veuillez remplir tous les champs svp !");
            return;
        }

        Users user = new Users(email , password);
        boolean verifInfo = userDAO.verifeInfo(user);

        if(verifInfo){
            JOptionPane.showMessageDialog(null , "Connexion Reussi !" , "Succée" , JOptionPane.INFORMATION_MESSAGE);
            dashbord();

        }else{
            JOptionPane.showMessageDialog(null , "Email ou Mot de passe incorrecte !" , "Erreur" , JOptionPane.ERROR_MESSAGE);
        }
    }

    public void dashbord(){
        String role = userDAO.getUserRole();

        switch (role){
            case "Enseignant" :
                JOptionPane.showMessageDialog(null , "Vous ete enseignant");
                break;
            case "Chef" :
                new DashBordChefView().setVisible(true);
                log.dispose();
                break;
            case "Responsable" : JOptionPane.showMessageDialog(null , "Vous ete responsable");
                break;
            default: JOptionPane.showMessageDialog(null , "Roles inconnue");
                break;

        }

    }

}