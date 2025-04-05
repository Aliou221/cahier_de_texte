package org.cahier_de_texte.controller;

import org.cahier_de_texte.vue.DashBordChefView;
import org.cahier_de_texte.model.UserDAO;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.vue.DashBordEnseignantView;
import org.cahier_de_texte.vue.UserLoginView;

import javax.swing.*;

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

    // Enregistre un utilisateur avec le rôle Enseignant
    public void enregistreUser(String firstName , String lastName , String email , String password){

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez remplir tous les champs svp !" ,
                    "Erreur" ,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Users user = new Users(firstName , lastName , email , password);

        if(userDAO.addEnseignant(user)){
            JOptionPane.showMessageDialog(
                    null ,
                    user.getFirstName() + " " + user.getLastName() +
                            " à été ajouté avec succès !" ,
                    "Succès" ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Modifie les informations d’un utilisateur
    public void modifierUser(int id , String firstName , String lastName , String email){

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(null , "Veuillez remplir tous les champs svp !" , null , JOptionPane.WARNING_MESSAGE);
            return;
        }

        Users user = new Users(id , firstName , lastName , email);
        boolean modifValide = userDAO.modiferUser(user);

        if(modifValide){
            JOptionPane.showMessageDialog(null , "Ensignant a été modifié avec succés !" , "Succés" , JOptionPane.INFORMATION_MESSAGE);
        }

    }


    // Supprime un utilisateur avec boîte de dialogue de confirmation
    public void supprimerUser(int id) {

        int confirmation = JOptionPane.showConfirmDialog(
                null,
                "Voulez-vous vraiment supprimer cet utilisateur ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            Users user = new Users(id);
            boolean supValide = userDAO.supprimerUser(user);

            if (supValide) {

                JOptionPane.showMessageDialog(
                        null,
                        "Enseignant supprimé avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Échec de la suppression de l'utilisateur.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

}