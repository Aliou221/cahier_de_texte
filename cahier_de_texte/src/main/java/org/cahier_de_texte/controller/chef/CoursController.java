package org.cahier_de_texte.controller.chef;

import org.cahier_de_texte.dao.chef.CoursDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CoursController {
    CoursDAO coursDAO;

    public CoursController(){
        this.coursDAO = new CoursDAO();
    }

    public void ajouterCours(String code , String intituler , int credit){
            boolean ajoutValide = this.coursDAO.ajouterCours(code , intituler , credit);
            if(ajoutValide){
                JOptionPane.showMessageDialog(
                        null,
                        "Le cours " + code + "_" + intituler + " a été ajoutè avec succès !",
                        null,
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
    }

    public void assignerCours(int id , String code , String intitule , int idCours , int idClasse){
        boolean assignationVade = this.coursDAO.asignerCours(id , code , intitule);
        if (assignationVade){
            this.coursDAO.ajouterClasseCours(idCours,idClasse);
            System.out.println("Good JOb !");
        }
    }

    public int getIdCours(String code){
        return this.coursDAO.getIDCours(code);
    }

    public void modifierCours(String code , String intitule , int credit , String codeFirst){
        this.coursDAO.modifierCours(code , intitule , credit , codeFirst);
    }

    public void supprimerCours(String code){
        this.coursDAO.supprimerCours(code);
    }

    public void listeDesCours(DefaultTableModel model){
        this.coursDAO.listeCours(model);
    }
}
