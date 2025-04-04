package org.cahier_de_texte.controller;

import org.cahier_de_texte.vue.DashBordChefView;
import org.cahier_de_texte.model.UserDAO;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.vue.DashBordEnseignantView;
import org.cahier_de_texte.vue.UserLoginView;

import javax.swing.*;

public class UserController {
    UserDAO userDAO;
    UserLoginView log ;

    public UserController(UserLoginView log) {
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
        String nom = userDAO.getUserName();

        switch (role){
            case "Enseignant" :
                new DashBordEnseignantView(nom).setVisible(true);
                log.dispose();
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

    public void enregistreUser(String firstName , String lastName , String email , String password){

    }

}