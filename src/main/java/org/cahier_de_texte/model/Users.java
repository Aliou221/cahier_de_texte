package org.cahier_de_texte.model;

public class Users {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Constructeur pour l'authentification
    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Constructeur pour l'ajout d'un nouvel utilisateur
    public Users(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Constructeur utilisé pour la suppression par ID
    public Users(int id) {
        this.id = id;
    }

    //Constructeur utilisé pour la modification d’un utilisateur
    public Users(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //Getteurs
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
