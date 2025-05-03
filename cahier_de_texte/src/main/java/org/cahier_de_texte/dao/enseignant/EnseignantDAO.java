package org.cahier_de_texte.dao.enseignant;

import org.cahier_de_texte.dao.DbConnexion;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnseignantDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public boolean chargeListeCoursAssigner(DefaultTableModel model , int idEnseignant){
        String sql = "SELECT Cours.code , Cours.intitule , Classes.nom , Cours.credits " +
                "FROM Cours " +
                "INNER JOIN ClasseCours ON ClasseCours.id_cours = Cours.id " +
                "INNER JOIN Classes ON Classes.id = ClasseCours.id_classe " +
                "INNER JOIN Utilisateurs ON Utilisateurs.id = Cours.enseignant_id " +
                "WHERE Utilisateurs.id = ?";
        ResultSet res;

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idEnseignant);
            res = pst.executeQuery();

            model.setRowCount(0);
            while (res.next()){
                String codeCours = res.getString("code");
                String intitule = res.getString("intitule");
                String classe = res.getString("nom");
                int credit = res.getInt("credits");

                model.addRow(new Object[]{codeCours , intitule , credit , classe});
            }
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }
}