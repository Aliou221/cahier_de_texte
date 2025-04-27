package org.cahier_de_texte.controller.chef;

import org.cahier_de_texte.dao.chef.SeanceDAO;

import javax.swing.table.DefaultTableModel;

public class SeanceController {
    SeanceDAO seanceDAO;

    public SeanceController(){
        this.seanceDAO = new SeanceDAO();
    }

    public void chargeTableClasseSeance(DefaultTableModel model){
        seanceDAO.chargeTabSeanceClasse(model);
    }

    public void chargeListeSeancesClasse(DefaultTableModel model , String classe){
        seanceDAO.chargeListeSeancesClasse(model , classe);
    }
}
