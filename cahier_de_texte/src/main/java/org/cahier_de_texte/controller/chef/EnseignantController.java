package org.cahier_de_texte.controller.chef;

import org.cahier_de_texte.dao.chef.EnseignantDAO;
import org.cahier_de_texte.model.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EnseignantController {
    EnseignantDAO enseignantDAO ;

    public EnseignantController(){
        enseignantDAO = new EnseignantDAO();
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

        Users user = new Users();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        if(enseignantDAO.addEnseignant(user)){
            JOptionPane.showMessageDialog(
                    null ,
                    user.getFirstName() + " " + user.getLastName() +
                            " à été ajouté avec succès !" ,
                    "Succès" ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            JOptionPane.showMessageDialog(
                    null ,
                            "Erreur !" ,
                    null ,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void chargeTabEnseignant(DefaultTableModel model){
        enseignantDAO.chargerTabEnseignant(model);
    }
}
