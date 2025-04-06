package org.cahier_de_texte.controller;

import org.cahier_de_texte.model.UserDAO;
import org.cahier_de_texte.model.UserEnseignantDAO;
import org.cahier_de_texte.model.Users;

import javax.swing.*;

public class UserEnseignantController {
    UserDAO userDAO;
    UserEnseignantDAO enseignantDAO;

    public UserEnseignantController(){
        userDAO = new UserDAO();
        enseignantDAO = new UserEnseignantDAO();
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

        if(enseignantDAO.addEnseignant(user)){
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
