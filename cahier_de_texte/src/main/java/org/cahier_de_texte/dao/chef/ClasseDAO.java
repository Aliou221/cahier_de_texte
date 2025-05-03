package org.cahier_de_texte.dao.chef;

import org.cahier_de_texte.dao.DbConnexion;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClasseDAO {
    DbConnexion db = new DbConnexion();
    Connection con = db.getConnection();
    PreparedStatement pst;

    public boolean ajouterClasse(String nomClasse){
        String sql = "INSERT INTO Classes(nom) VALUES (?)";

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1 , nomClasse);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur !  dans class dao ajouter classe\"" + e.getMessage());
        }

        return false;
    }

    public boolean modifierClasse(String nom , String nouveauNom){
        String sql = "UPDATE Classes SET nom = ? WHERE nom = ?";

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , nouveauNom);
            pst.setString(2 , nom);
            pst.executeUpdate();
            return true;

        }catch (SQLException e){
            System.out.println("Erreur ! dans classe dao modifier" + e.getMessage());
        }
        return false;
    }

    public boolean supprimerClasse(String nom){
        String sql = "DELETE FROM Classes WHERE nom = ?";
        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , nom);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur ! dans classe dao supprimer " + e.getMessage());
        }
        return false;
    }

    public void chargeTabClasse(DefaultTableModel model){
        String sql = "SELECT Classes.nom AS classe, " +
                "COALESCE(CONCAT(Utilisateurs.prenom, ' ', Utilisateurs.nom), 'Aucun responsable') AS responsable, " +
                "COALESCE(Utilisateurs.email, 'Neant') AS email, " +
                "COUNT(Etudiants.id) AS effectif " +
                "FROM Classes " +
                "LEFT JOIN Etudiants ON Etudiants.classe_id = Classes.id " +
                "LEFT JOIN Utilisateurs ON Utilisateurs.id = Classes.id_responsable " +
                "GROUP BY Classes.id";
        ResultSet res;

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            model.setRowCount(0);
            while (res.next()){
                String classe = res.getString("classe");
                String responsable = res.getString("responsable");
                String email = res.getString("email");
                int effectif = res.getInt("effectif");

                model.addRow(new Object[] {classe , responsable , email , effectif});
            }

        }catch (SQLException e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }
}