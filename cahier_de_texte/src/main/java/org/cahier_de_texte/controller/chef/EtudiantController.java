package org.cahier_de_texte.controller.chef;

import org.cahier_de_texte.dao.chef.EtudiantDAO;
import org.cahier_de_texte.model.Etudiants;
import org.cahier_de_texte.model.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EtudiantController {
    EtudiantDAO etudiantDAO;

    public EtudiantController(){
        etudiantDAO = new EtudiantDAO();
    }

    public void ajouterEtudiant(String firstName , String lastName , String email , String classe){
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez remplir tous les champs svp !" ,
                    "Erreur" ,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Etudiants etudiants = new Etudiants();
        etudiants.setFirstName(firstName);
        etudiants.setLastName(lastName);
        etudiants.setEmail(email);

        if (etudiantDAO.ajouterEtudiant(etudiants , classe)){
            JOptionPane.showMessageDialog(
                    null ,
                    etudiants.getFirstName() + " " + etudiants.getLastName() +
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

    public void modifierEtudiant(String firstName , String lastName , String email , int id){
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez remplir tous les champs svp !" ,
                    "Erreur" ,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Etudiants etudiants = new Etudiants();
        etudiants.setFirstName(firstName);
        etudiants.setLastName(lastName);
        etudiants.setEmail(email);

        if (etudiantDAO.modifierEtudiant(etudiants , id)){
            JOptionPane.showMessageDialog(
                    null ,
                            "Etudiant à été modifié avec succès !" ,
                    "Succès" ,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            JOptionPane.showMessageDialog(
                    null ,
                    "La modification a echoué" ,
                    null ,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void modifRespo(String firstName , String lastName , String email , int id ){
        Users user = new Users();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        if(etudiantDAO.modifRespo(user , id)){
            System.out.println("prenom: " + firstName);
            System.out.println("nom: " + lastName);
            System.out.println("email: " + email);
        }else{
            System.out.println("N'est pas un responsable");
        }
    }

    public boolean verifEtudiant(String email){
        return etudiantDAO.verifResponsable(email);
    }

    public int getRespo(String email){
        return etudiantDAO.getIdUser(email);
    }

    public void supprimerEtudiant(int id){

        if (etudiantDAO.supprimerEtudiant(id)){
            JOptionPane.showMessageDialog(
                    null ,
                    "Etudiant a été supprimé avec succés !",
                    null,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            JOptionPane.showMessageDialog(
                    null ,
                    "La suppression de l'etudiant a echoué !",
                    null,
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void deleteRespo(int id){
        if(etudiantDAO.deleteResponsable(id)){
           System.out.println("Responsable a été supprimé avec succés !");
        }else{
            System.out.println("La suppression du responsable a echoué !");
        }
    }

    public void defResponsable(int idResponsable){
        boolean defValide = this.etudiantDAO.defResponsable(idResponsable);
        if(!defValide){
            System.out.println("Erreur de modification du id responsable!");
        }
    }

    public void retireResponsable(int idResponsable){
        boolean retireValide = this.etudiantDAO.retireResponsable(idResponsable);
        if(!retireValide){
            System.out.println("Erreur de modification du id responsable!");
        }
    }

    public boolean verifResponsable(String email){
        return this.etudiantDAO.verifResponsable(email);
    }

    public void ajouterResponsable(String prenom , String nom , String email , String password , String classe){
        boolean ajoutValide = this.etudiantDAO.ajouterResponsableUser(prenom , nom , email , password , classe);
        if (!ajoutValide){
            System.out.println("Erreur d'ajout de responsable dans l'utilisateur !");
        }
    }

    public void deleteResponsableUser(String email){
        boolean deleteValide = this.etudiantDAO.deleteResponsableUser(email);
        if(!deleteValide){
            System.out.println("Erreur de suppression du responsable !");
        }
    }

    public void chargeListeEtudiant(DefaultTableModel model , String classe){
       etudiantDAO.chargeListeEtudiant(model , classe);
    }
}
