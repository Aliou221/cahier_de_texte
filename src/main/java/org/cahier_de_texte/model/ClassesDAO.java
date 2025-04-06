package org.cahier_de_texte.model;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClassesDAO {
    Connection con ;
    DbConnexion db = new DbConnexion();
    PreparedStatement pst;

    public void chargeTabClasse(DefaultTableModel model){
        String sql = "SELECT Classes.nom AS classe, " +
                "CONCAT(Utilisateurs.prenom, ' ', Utilisateurs.nom) AS responsable, Utilisateurs.email AS email, " +
                "COUNT(Etudiants.id) AS effectif " +
                "FROM Classes " +
                "INNER JOIN Utilisateurs ON Utilisateurs.id = Classes.id_responsable " +
                "INNER JOIN Etudiants ON Etudiants.classe_id = Classes.id " +
                "GROUP BY Classes.id, Utilisateurs.prenom, Utilisateurs.nom ";
        ResultSet res;

        try{
            con = db.getConnection();
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

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public void chargeListeEtudiant(DefaultTableModel model , String classe){
        String sql = "SELECT Etudiants.id AS ID , Etudiants.prenom AS prenom, Etudiants.nom AS nom , Etudiants.email AS email , Classes.nom " +
                "FROM Etudiants " +
                "INNER JOIN Classes ON Etudiants.classe_id = Classes.id " +
                "WHERE Classes.nom = ?";
        ResultSet res;

        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1 , classe);
            res = pst.executeQuery();
            model.setRowCount(0);

            while (res.next()){

                int id = res.getInt("ID");
                String prenom = res.getString("prenom");
                String nom = res.getString("nom");
                String email = res.getString("email");

                if (verifResponsable(email)) {
                    model.addRow(new Object[]{id, prenom, nom, email, "Responsable"});
                } else {
                    model.addRow(new Object[]{id, prenom, nom, email, ""});
                }
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }
    }

    public boolean verifResponsable(String email){
        String sql = "SELECT * FROM Utilisateurs WHERE email = ? AND role = 'Responsable' ";
        ResultSet res;
        try{
            con = db.getConnection();
            pst = con.prepareStatement(sql);
            pst = con.prepareStatement(sql);
            pst.setString(1, email);
            res = pst.executeQuery();

            if(res.next()){
                return true;
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }
}
