package org.cahier_de_texte.model;

import java.time.LocalDateTime;

public class Seance {
    private int id;
    private String coursCode;
    private String coursIntitule;
    private LocalDateTime dateSeance;
    private String contenu;
    private int duree;

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCoursCode() {
        return coursCode;
    }
    public void setCoursCode(String coursCode) {
        this.coursCode = coursCode;
    }

    public String getCoursIntitule() {
        return coursIntitule;
    }
    public void setCoursIntitule(String coursIntitule) {
        this.coursIntitule = coursIntitule;
    }

    public LocalDateTime getDateSeance() {
        return dateSeance;
    }
    public void setDateSeance(LocalDateTime dateSeance) {
        this.dateSeance = dateSeance;
    }

    public String getContenu() {
        return contenu;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getDuree() {
        return duree;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }
}