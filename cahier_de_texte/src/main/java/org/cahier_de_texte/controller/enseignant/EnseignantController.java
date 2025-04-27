package org.cahier_de_texte.controller.enseignant;

import org.cahier_de_texte.dao.enseignant.EnseignantDAO;
import org.cahier_de_texte.dao.responsable.ResponsableDAO;

import javax.swing.table.DefaultTableModel;

public class EnseignantController {
    EnseignantDAO enseignantDAO;

    public EnseignantController(){
        enseignantDAO = new EnseignantDAO();
    }

    public void chargeListeCoursAssigner(DefaultTableModel model, int idResponsable){
        boolean valide = enseignantDAO.chargeListeCoursAssigner(model , idResponsable);
        if(!valide){
            System.out.println("Erreur dans chargement des Séances");
        }
    }
}
