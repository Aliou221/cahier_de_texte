package org.cahier_de_texte.controller.chef;

import org.cahier_de_texte.dao.chef.ChefDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ChefController {
    ChefDAO chefDAO;

    public ChefController(){
        chefDAO = new ChefDAO();
    }

    public int getNbEnseignant(){
        return chefDAO.nbEnseignant();
    }

    public int getNbResponsable(){
        return chefDAO.nbResponsable();
    }

    public int getNbEtudiant(){
        return chefDAO.nbEtudiant();
    }

    public int getNbSeanceValide(){
        return chefDAO.nbSeanceValide();
    }

    public int getNbCours(){
        return chefDAO.nbCours() ;
    }

    public int getNbClasse(){
        return chefDAO.nbClasse() ;
    }

    // Charge tous les responsable et leurs classes dans une tableau
    public void chargeTabResponsable(DefaultTableModel model){
        chefDAO.chargeTabResponsableClasse(model);
    }

    // Charge tous les enseignats et leurs cours asigner dans une tableau
    public void chargeTabEnseignantCours(DefaultTableModel model){
        chefDAO.chargeTabEnseignantCours(model);
    }

    public void enseignant(JComboBox<String> combo){
        chefDAO.listeEnseignant(combo);
    }

    public void cours(JComboBox<String> combo){
        chefDAO.listeCours(combo);
    }

    public void classe(JComboBox<String> combo){
        chefDAO.listeClasse(combo);
    }
}