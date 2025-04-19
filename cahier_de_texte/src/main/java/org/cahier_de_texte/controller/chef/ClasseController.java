package org.cahier_de_texte.controller.chef;

import org.cahier_de_texte.dao.chef.ClasseDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClasseController {
    ClasseDAO classeDAO;

    public ClasseController(){
        classeDAO = new ClasseDAO();
    }

    public void ajouterClasse(String nomClasse){
        if (nomClasse.isEmpty()){
            JOptionPane.showMessageDialog(
                    null,
                    "Veuillez remplir ce champs svp!",
                    null,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (classeDAO.ajouterClasse(nomClasse)){
            JOptionPane.showMessageDialog(
                    null,
                    nomClasse + " a été ajouté avec succés !",
                    null,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            JOptionPane.showMessageDialog(
                    null,
                    "Erreur !\n" +nomClasse + " a été ajouté !",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void modifierClasse(String nom , String autreNom){
        if (autreNom.isEmpty()){
            JOptionPane.showMessageDialog(
                    null,
                    "Veuillez remplir ce champs svp !",
                    null,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (classeDAO.modifierClasse(nom , autreNom)){
            JOptionPane.showMessageDialog(
                    null,
                    nom + " a été remplacé par : " + autreNom,
                    null,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            JOptionPane.showMessageDialog(
                    null,
                    "La modification a échoué !",
                    null,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void supprimerClasse(String nom){
        if (classeDAO.supprimerClasse(nom)){
            JOptionPane.showMessageDialog(
                    null,
                    "La classe " + nom + " a été supprimé avec succé !",
                    null,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            JOptionPane.showMessageDialog(
                    null,
                    "La suppression de la classe " + nom + " echoué !",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void chargeTabClasse(DefaultTableModel model){
        classeDAO.chargeTabClasse(model);
    }
}
