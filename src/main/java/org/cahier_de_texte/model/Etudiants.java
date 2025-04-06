package org.cahier_de_texte.model;

public class Etudiants {
    private int id;
    private String prenom ;
    private String nom ;
    private String email ;

    public Etudiants(int id, String prenom, String nom, String email) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
