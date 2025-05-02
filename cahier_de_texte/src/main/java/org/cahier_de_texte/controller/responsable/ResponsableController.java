package org.cahier_de_texte.controller.responsable;

import org.cahier_de_texte.dao.responsable.ResponsableDAO;

import javax.swing.table.DefaultTableModel;

public class ResponsableController {
    ResponsableDAO responsableDAO;

    public ResponsableController(){
        responsableDAO = new ResponsableDAO();
    }

    public void chargeSeance(DefaultTableModel model, int idResponsable){
        boolean valide = responsableDAO.chargeListeSeances(model , idResponsable);
        if(!valide){
            System.out.println("Erreur dans chargement des Séances");
        }
    }

    public void insertSeanceValide(int idResponsable , int idSeance){
        boolean valide = this.responsableDAO.valideSeance(idResponsable , idSeance);

        if (!valide){
            System.out.println("Erreur de validation du Séance");
        }
    }

    public void valide(int idSeance){
        boolean valide = this.responsableDAO.valide(idSeance);
        if (!valide){
            System.out.println("Erreur de valide la seance");
        }
    }

    public boolean verifIdSeance(int id){
        return this.responsableDAO.verifIdSeance(id);
    }
}
