package org.cahier_de_texte.controller;

import org.cahier_de_texte.dao.EtudiantDAO;
import org.cahier_de_texte.models.Etudiants;
import org.cahier_de_texte.models.Users;

import javax.swing.*;

public class EtudiantController {
    EtudiantDAO etudiantDAO;
    public EtudiantController(){
        etudiantDAO = new EtudiantDAO();
    }

    public void insereEtudiant(String prenom , String nom , String email , String classe){
        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez remplir tous les champs svp !" ,
                    null ,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Etudiants etudiants = new Etudiants(prenom , nom , email);
        boolean verif = etudiantDAO.ajouterEtudiant(etudiants , classe);

        if (verif){
            JOptionPane.showMessageDialog(
                    null ,
                    prenom + " " + nom +
                            " a été ajouté avec succés !" ,
                    null ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void modifEtudiant(String prenom , String nom , String email , int id){

        if(prenom.isEmpty() || nom.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez remplir tous les champs svp !" ,
                    null ,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Etudiants etudiant = new Etudiants(prenom , nom , email);
        boolean modifValide = etudiantDAO.modifierEtudiant(etudiant , id);

        if (modifValide){
            JOptionPane.showMessageDialog(
                    null ,
                    prenom + " " + nom + " a été modifié avec succés !" ,
                    null ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void supprimerEtudiant(int id){
        boolean supValide = etudiantDAO.supprimerEtudiant(id);

        if (supValide){
            JOptionPane.showMessageDialog(null , "Etudiant a été supprimé avec succés !");
        }
    }

    public void defResponsable(String prenom , String nom , String email , String password , String classe){
        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez remplir tous les champs svp !",
                    null ,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Users user = new Users(prenom , nom , email , password);
        boolean ajoutValide = etudiantDAO.defResponsable(user , classe);

        if (ajoutValide){
            JOptionPane.showMessageDialog(
                    null,
                    prenom + " " +nom +" a été définit comme reponsable de " + classe ,
                    null ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
