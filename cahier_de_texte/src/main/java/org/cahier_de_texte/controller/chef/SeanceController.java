package org.cahier_de_texte.controller.chef;

import org.cahier_de_texte.dao.chef.SeanceDAO;

import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;

public class SeanceController {
    SeanceDAO seanceDAO;

    public SeanceController(){
        this.seanceDAO = new SeanceDAO();
    }

    public void chargeTableClasseSeance(DefaultTableModel model){
        seanceDAO.chargeTabSeanceClasse(model);
    }

    public void chargeListeSeances(DefaultTableModel model , String classe , int idEnsegnant , String cours){
        this.seanceDAO.chargeListeSeances(model,classe , idEnsegnant , cours);
    }

    public void ajouterSeance(int idCours , Timestamp dateSeance , String contenu , int duree){
        boolean seanceAjoute = this.seanceDAO.ajouterSeance( idCours ,  dateSeance ,  contenu , duree);
        if (seanceAjoute){
            System.out.println("Seance ajoutee avec succee !");
        }
    }

    public void modifierSeance(int idSeance, Timestamp dateSeance , String contenu , int duree){
        boolean seanceModifier = this.seanceDAO.modifierSeance( idSeance ,  dateSeance ,  contenu , duree);
        if (seanceModifier){
            System.out.println("Seance modifié avec succee !");
        }
    }

    public void supprimerSeance(int idSeance){
        boolean supressionValide = this.seanceDAO.supprimerSeance(idSeance);

        if (supressionValide){
            System.out.println("La séance a été supprimé avec succé !");
        }
    }

    public void chargeListeSeancesClasse(DefaultTableModel model , String classe){
        seanceDAO.chargeListeSeancesClasse(model , classe);
    }

    public void chargeListeSeancesClasseNonValide(DefaultTableModel model , String classe){
        seanceDAO.chargeListeSeancesClasseNonValide(model , classe);
    }
}
