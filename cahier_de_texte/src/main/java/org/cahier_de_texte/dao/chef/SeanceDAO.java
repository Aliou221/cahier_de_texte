package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;

public class SeanceDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public void chargeTabSeanceClasse(DefaultTableModel model){
        String sql = "SELECT Classes.nom AS classe , CONCAT(Responsable.prenom , \" \", Responsable.nom) AS responsable ,  " +
                "        Responsable.email, " +
                "        COUNT(Classes.nom) AS nb_seance_valide " +
                "FROM Validations " +
                "INNER JOIN Seances ON Seances.id = Validations.seance_id " +
                "INNER JOIN Utilisateurs AS Responsable ON Responsable.id = Validations.responsable_id " +
                "INNER JOIN Cours ON Cours.id = Seances.cours_id " +
                "INNER JOIN Utilisateurs AS Enseignant ON Enseignant.id = Cours.enseignant_id " +
                "INNER JOIN Classes ON Classes.id_responsable = Responsable.id " +
                "GROUP BY(Classes.nom)";
        ResultSet res;

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            model.setRowCount(0);
            while (res.next()){
                String classe = res.getString("classe");
                String responsable = res.getString("responsable");
                String email = res.getString("email");
                int nbSeanceValide = res.getInt("nb_seance_valide");

                model.addRow(new Object[] {classe , responsable , email , nbSeanceValide});
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public void chargeListeSeancesClasse(DefaultTableModel model , String classe){
        String sql = "SELECT Seances.date_seance, Cours.code AS code_cours, Cours.intitule AS intitule_cours, Seances.contenu, " +
                "        Seances.duree, " +
                "        CONCAT(Enseignant.prenom , \" \", Enseignant.nom) AS enseignant " +
                "FROM Validations " +
                "INNER JOIN Seances ON Seances.id = Validations.seance_id " +
                "INNER JOIN Utilisateurs AS Responsable ON Responsable.id = Validations.responsable_id " +
                "INNER JOIN Cours ON Cours.id = Seances.cours_id " +
                "INNER JOIN Utilisateurs AS Enseignant ON Enseignant.id = Cours.enseignant_id " +
                "INNER JOIN Classes ON Classes.id_responsable = Responsable.id " +
                "WHERE Classes.nom = ?";
        ResultSet res;

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , classe);
            res = pst.executeQuery();
            model.setRowCount(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            while (res.next()){
                Timestamp dateCreation = res.getTimestamp("date_seance");
                String formattedDate = dateFormat.format(dateCreation);
                String code_cours = res.getString("code_cours");
                String cours = res.getString("intitule_cours");
                String contenu = res.getString("contenu");
                int duree = res.getInt("duree");
                String enseignant = res.getString("enseignant");

                
                model.addRow(new Object[]{formattedDate, code_cours, cours, contenu, duree , enseignant});
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }
}
