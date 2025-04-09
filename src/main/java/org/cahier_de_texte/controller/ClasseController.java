package org.cahier_de_texte.controller;

import org.cahier_de_texte.dao.ClassesDAO;

import javax.swing.*;

public class ClasseController {
    ClassesDAO classesDAO;

    public ClasseController(){
        classesDAO = new ClassesDAO();
    }

    public void ajouterClasse(String nom){

        if(nom.isEmpty()){
            JOptionPane.showMessageDialog(null , "Le champs est obligatoire !" , null , JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean verif = classesDAO.ajouterClasse(nom);

        if (verif){
            JOptionPane.showMessageDialog(
                    null ,
                    "La classe " + nom +" a été ajouté avec succé !" ,
                    null ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void modifierClasse(String nom , String nouveauNom){
        if(nom.isEmpty()){
            JOptionPane.showMessageDialog(null , "Le champs est obligatoire !"  , null , JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean verif = classesDAO.modifierClasse(nom , nouveauNom);

        if (verif){
            JOptionPane.showMessageDialog(
                    null ,
                    "La classe " + nom + " a été modifié avec succé !" ,
                    null ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void supprimeClasse(String nom){
        if(nom.isEmpty()){
            JOptionPane.showMessageDialog(null , "Le champs est obligatoire !" , null , JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean verif = classesDAO.supprimerClasse(nom);

        if (verif){
            JOptionPane.showMessageDialog(
                    null ,
                    "La classe " + nom +" a été supprimé avec succé !" ,
                    null ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


}
