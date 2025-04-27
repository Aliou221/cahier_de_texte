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
}
