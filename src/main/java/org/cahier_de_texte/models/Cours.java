package org.cahier_de_texte.models;

public class Cours {
    private int idCours;
    private String intituleCOurs;
    private int credit;
    private int idEnseignant;


    public Cours(String intituleCOurs, int credit, int idEnseignant) {
        this.intituleCOurs = intituleCOurs;
        this.credit = credit;
        this.idEnseignant = idEnseignant;
    }


    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getIntituleCOurs() {
        return intituleCOurs;
    }

    public void setIntituleCOurs(String intituleCOurs) {
        this.intituleCOurs = intituleCOurs;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }
}
