package org.cahier_de_texte.model;

public class Cours {
    private int idCours;
    private String codeCours;
    private String intituler;
    private int credit;


    public Cours(String codeCours, String intituler, int credit) {
        this.codeCours = codeCours;
        this.intituler = intituler;
        this.credit = credit;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getCodeCours() {
        return codeCours;
    }

    public void setCodeCours(String codeCours) {
        this.codeCours = codeCours;
    }

    public String getIntituler() {
        return intituler;
    }

    public void setIntituler(String intituler) {
        this.intituler = intituler;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
