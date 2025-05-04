package org.cahier_de_texte.dao.responsable;

import org.cahier_de_texte.dao.DbConnexion;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponsableDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public boolean chargeListeSeancesNoValide(DefaultTableModel model , int idResponsable){
        String sql = "SELECT s.id AS idSeance, s.Valide AS etat , c.code AS codeCours , " +
                " c.intitule AS intitule , s.contenu, " +
                " s.duree , s.date_seance, CONCAT(u.prenom, ' ', u.nom) AS enseignant " +
                "FROM Seances s " +
                "LEFT JOIN Cours c ON s.cours_id = c.id " +
                "LEFT JOIN Utilisateurs u ON c.enseignant_id = u.id " +
                "LEFT JOIN ClasseCours cc ON cc.id_cours = c.id " +
                "LEFT JOIN Classes cl ON cl.id = cc.id_classe " +
                "WHERE cl.id = (" +
                "    SELECT id FROM `Classes` WHERE id_responsable = ? AND s.Valide = false " +
                ")  " +
                "ORDER BY s.date_seance ";
        ResultSet res;

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idResponsable);
            res = pst.executeQuery();
            model.setRowCount(0);
            while (res.next()){
                int idSeance = res.getInt("idSeance");
                String codeCours = res.getString("codeCours");
                String intitule = res.getString("intitule");
                String contenu = res.getString("contenu");
                int duree = res.getInt("duree");
                String dateSeance = res.getString("date_seance");
                String enseignant = res.getString("enseignant");
                boolean valide = res.getBoolean("etat");

                model.addRow(new Object[]{idSeance , codeCours , intitule , contenu , duree + " Heurs" , dateSeance , enseignant , valide});
            }
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean chargeListeSeancesValide(DefaultTableModel model , int idResponsable){
        String sql = "SELECT s.id AS idSeance, s.Valide AS etat , c.code AS codeCours , " +
                " c.intitule AS intitule , s.contenu, " +
                " s.duree , s.date_seance, CONCAT(u.prenom, ' ', u.nom) AS enseignant " +
                "FROM Seances s " +
                "LEFT JOIN Cours c ON s.cours_id = c.id " +
                "LEFT JOIN Utilisateurs u ON c.enseignant_id = u.id " +
                "LEFT JOIN ClasseCours cc ON cc.id_cours = c.id " +
                "LEFT JOIN Classes cl ON cl.id = cc.id_classe " +
                "WHERE cl.id = (" +
                "    SELECT id FROM `Classes` WHERE id_responsable = ? AND s.Valide = true " +
                ")  " +
                "ORDER BY s.date_seance";
        ResultSet res;

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idResponsable);
            res = pst.executeQuery();
            model.setRowCount(0);
            while (res.next()){
                int idSeance = res.getInt("idSeance");
                String codeCours = res.getString("codeCours");
                String intitule = res.getString("intitule");
                String contenu = res.getString("contenu");
                int duree = res.getInt("duree");
                String dateSeance = res.getString("date_seance");
                String enseignant = res.getString("enseignant");
                boolean valide = res.getBoolean("etat");

                model.addRow(new Object[]{idSeance , codeCours , intitule , contenu , duree + " Heurs" , dateSeance , enseignant , valide});
            }
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean valideSeance(int idResponsable , int idSeance){
        String sql = "INSERT INTO Validations (responsable_id , seance_id) VALUES" +
                "(? , ?)";

        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idResponsable);
            pst.setInt(2 , idSeance);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public boolean valide(int idSeance){
        String sql = "UPDATE Seances SET Valide = ? WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setBoolean(1 , true);
            pst.setInt(2 , idSeance);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur de valider la seance " + e.getMessage());
        }

        return false;
    }

    public boolean verifIdSeance(int idSeance){
        String sql = "SELECT * FROM Validations WHERE seance_id = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1 , idSeance);
            ResultSet res = pst.executeQuery();

            if (res.next()){
                return true;
            }

        }catch (SQLException e){
            System.out.println("Erreur de recherche ! " + e.getMessage());
        }

        return false;
    }
}