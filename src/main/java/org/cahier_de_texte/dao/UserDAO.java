package org.cahier_de_texte.dao;
import org.cahier_de_texte.models.DbConnexion;
import org.cahier_de_texte.models.Users;

import javax.swing.table.DefaultTableModel;
import java.sql.*;


// Classe de gestion des opérations BDD pour les utilisateurs
public class UserDAO {

    DbConnexion db = new DbConnexion(); // Connexion à la base de données
    Connection con = db.getConnection();
    PreparedStatement pst;
    ResultSet res;

    String role , nom; // Informations récupérées à la connexion

    // Vérifie les informations de connexion de l'utilisateur
    public boolean verifeInfo(Users user){

        String sql = "SELECT * FROM Utilisateurs WHERE email = ? AND password = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1 , user.getEmail());
            pst.setString(2 , user.getPassword());

            res = pst.executeQuery();

            if(res.next()){
                role = res.getString("role");
                nom = res.getString("nom");
                return true;
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    // Retourne le rôle de l'utilisateur connecté
    public String getUserRole(){
       return role;
    }

    // Retourne le nom de l'utilisateur connecté
    public String getUserName(){
       return nom;
    }

    // Modifie les informations d’un utilisateur existant
    public boolean modiferUser(Users user){

        String sql = "UPDATE Utilisateurs SET prenom = ? , nom = ? , email = ? WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);

            pst.setString(1 , user.getFirstName());
            pst.setString(2 , user.getLastName());
            pst.setString(3 , user.getEmail());
            pst.setInt(4 , user.getId());

            pst.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    // Supprime un utilisateur par ID
    public boolean supprimerUser(Users user){
        String sql = "DELETE FROM Utilisateurs WHERE id = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setInt(1 , user.getId());
            pst.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Erreur ! " + e.getMessage());
        }

        return false;
    }

    public int nbEnseignant(){
        int nbreEnseignant = 0 ;
        String sql = "SELECT COUNT(*) FROM Utilisateurs WHERE role LIKE 'Enseignant'";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if(res.next()){
                nbreEnseignant = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreEnseignant;
    }

    public int nbResponsable(){
        int nbreResponsable = 0 ;
        String sql = "SELECT COUNT(*) FROM Utilisateurs WHERE role LIKE 'Responsable'";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if(res.next()){
                nbreResponsable = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreResponsable;
    }

    public int nbEtudiant(){
        int nbreEtudiant = 0 ;
        String sql = "SELECT COUNT(*) FROM Etudiants";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if(res.next()){
                nbreEtudiant = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreEtudiant;
    }

    public int nbSeanceValide(){
        int nbreSeanceValide = 0 ;
        String sql = "SELECT COUNT(*) FROM Validations";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if(res.next()){
                nbreSeanceValide = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreSeanceValide;
    }

    public int nbCours(){
        int nbreCours = 0 ;
        String sql = "SELECT COUNT(*) FROM Cours";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if(res.next()){
                nbreCours = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreCours;
    }

    public int nbClasse(){
        int nbreClasse = 0 ;
        String sql = "SELECT COUNT(*) FROM Classes";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if(res.next()){
                nbreClasse = res.getInt(1);
            }

        }catch (Exception e){
            System.out.println("Erreur ! " + e.getMessage());
        }

        return nbreClasse;
    }


    // Charge tous les responsable et leurs classes dans une tableau
    public void chargeTabResponsableClasse(DefaultTableModel modelTabEnseignant){

        String sql = "SELECT CONCAT(Utilisateurs.prenom, ' ', Utilisateurs.nom) AS Responsable, " +
                "Utilisateurs.email AS Email, Classes.nom AS Classe " +
                "FROM Utilisateurs " +
                "INNER JOIN Responsables ON Responsables.id_utilisateur = Utilisateurs.id " +
                "INNER JOIN Classes ON Classes.id_responsable = Responsables.id";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            modelTabEnseignant.setRowCount(0);

            while (res.next()){
                String responsable = res.getString("Responsable");
                String email = res.getString("Email");
                String classe = res.getString("Classe");

                modelTabEnseignant.addRow(new Object[]{responsable, email ,classe,});
            }

        } catch (Exception exp) {
            System.out.println("Erreur ! " + exp.getMessage());
        }
    }

    // Charge tous les enseignants et leurs cours asigner dans une tableau
    public void chargeTabEnseignantCours(DefaultTableModel modelTabEnseignant){

        String sql = "SELECT CONCAT(Utilisateurs.prenom , ' ' , Utilisateurs.nom) AS Enseignant, " +
                "Utilisateurs.email AS Email , Cours.intitule AS Cours , Classes.nom AS Classe " +
                "FROM Enseignants " +
                "INNER JOIN Utilisateurs ON Enseignants.id_utilisateur = Utilisateurs.id " +
                "INNER JOIN Cours ON Cours.enseignant_id = Enseignants.id " +
                "INNER JOIN ClasseCours ON ClasseCours.id_cours = Cours.id " +
                "INNER JOIN Classes ON ClasseCours.id_classe = Classes.id";

        try{
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            modelTabEnseignant.setRowCount(0);

            while (res.next()){
                String Enseignant = res.getString("Enseignant");
                String email = res.getString("Email");
                String cours = res.getString("Cours");
                String classe = res.getString("classe");

                modelTabEnseignant.addRow(new Object[]{Enseignant, email , cours,classe,});
            }

        } catch (Exception exp) {
            System.out.println("Erreur ! " + exp.getMessage());
        }
    }


}
