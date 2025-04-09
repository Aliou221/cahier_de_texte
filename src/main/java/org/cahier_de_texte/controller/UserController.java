package org.cahier_de_texte.controller;

import org.cahier_de_texte.vue.chef.DashBordChefView;
import org.cahier_de_texte.dao.UserDAO;
import org.cahier_de_texte.models.Users;
import org.cahier_de_texte.vue.enseignant.DashBordEnseignantView;
import org.cahier_de_texte.vue.UserLoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Contrôleur de gestion des actions de l'utilisateur (MVC)
public class UserController {
    UserDAO userDAO;
    UserLoginView log ;

    // Constructeur du contrôleur avec vue login pour que je puisse fermer la fenetre de login apres connexion
    public UserController(UserLoginView log) {
        this.log = log;
        userDAO = new UserDAO();
    }

    // Gère la connexion utilisateur
    public void login(String email, String password) {

        if (email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez remplir tous les champs svp !",
                    null,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Users user = new Users(email , password);
        boolean verifInfo = userDAO.verifeInfo(user);

        if(verifInfo){
            JOptionPane.showMessageDialog(
                    null ,
                    "Connexion Reussi !" ,
                    "Succée" ,
                    JOptionPane.INFORMATION_MESSAGE
            );
            dashbord();
        }else{

            JOptionPane.showMessageDialog(
                    null ,
                    "Email ou Mot de passe incorrecte !" ,
                    "Erreur" ,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Redirige vers la bonne interface selon le rôle de l'utilisateur
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
            case "Responsable" : JOptionPane.showMessageDialog(
                    null ,
                    "Vous ete responsable"
            );
                break;
            default: JOptionPane.showMessageDialog(null ,
                    "Roles inconnue"
            );
                break;

        }

    }

    public int getNbEnseignant(){
        return userDAO.nbEnseignant();
    }

    public int getNbResponsable(){
        return userDAO.nbResponsable();
    }

    public int getNbEtudiant(){
        return userDAO.nbEtudiant();
    }

    public int getNbSeanceValide(){
        return userDAO.nbSeanceValide();
    }

    public int getNbCours(){
        return userDAO.nbCours() ;
    }

    public int getNbClasse(){
        return userDAO.nbClasse() ;
    }

    // Charge tous les responsable et leurs classes dans une tableau
    public void chargeTabResponsable(DefaultTableModel model){
        userDAO.chargeTabResponsableClasse(model);
    }

    // Charge tous les enseignats et leurs cours asigner dans une tableau
    public void chargeTabEnseignantCours(DefaultTableModel model){
        userDAO.chargeTabEnseignantCours(model);
    }

}