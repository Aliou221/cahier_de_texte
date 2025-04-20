package org.cahier_de_texte.ui;

import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.cahier_de_texte.ui.enseignant.DashBordEnseignantUI;
import org.cahier_de_texte.ui.responsable.DashBordResponsableUI;

import net.miginfocom.swing.MigLayout;

import java.util.Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe principale représentant l'interface de connexion
public class LoginUI extends JFrame implements ActionListener {

    // Déclaration des composants de l'interface
    JLabel labelUser, labelPassword, textTitle;
    JTextField inputUser;
    JPasswordField inputPassword;
    JButton btnConnect, btnClear;

    // Constructeur de la classe
    public LoginUI() {
        initUI(); // Initialisation de l'interface utilisateur
    }

    // Méthode pour initialiser l'interface utilisateur
    public void initUI() {
        add(createPanel()); // Ajout du panneau principal

        // Configuration de la fenêtre
        setTitle("Connexion"); // Titre de la fenêtre
        setSize(400, 500); // Taille de la fenêtre
        setResizable(false); // Empêche le redimensionnement
        setMinimumSize(new Dimension(350, 450)); // Taille minimale
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ferme la fenêtre sans arrêter l'application
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
    }

    // Méthode pour créer le panneau principal
    public JPanel createPanel() {
        // Chargement et redimensionnement de l'icône utilisateur
        ImageIcon imageUser = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profile.png")));
        Icon resizeImageUser = new ImageIcon(imageUser.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        JPanel panelUser, panelPassword, mainPanelLogin;

        // Panneau principal avec un layout MigLayout
        mainPanelLogin = new JPanel(new MigLayout());
        mainPanelLogin.setBorder(BorderFactory.createEmptyBorder(15, 25, 20, 25)); // Marges autour du panneau

        // Titre de la fenêtre
        textTitle = new JLabel("Connexion");
        textTitle.setFont(new Font("Roboto", Font.BOLD, 23)); // Police et taille
        textTitle.setIcon(resizeImageUser); // Ajout de l'icône
        textTitle.setHorizontalTextPosition(JLabel.CENTER); // Position du texte
        textTitle.setVerticalTextPosition(JLabel.BOTTOM); // Position verticale
        textTitle.setVerticalAlignment(JLabel.CENTER); // Alignement vertical
        textTitle.setHorizontalAlignment(JLabel.CENTER); // Alignement horizontal
        mainPanelLogin.add(textTitle, "pushx, growx, wrap"); // Ajout au panneau principal

        // Panneau pour le champ utilisateur
        panelUser = new JPanel(new GridLayout(2, 1)); // Layout en grille

        labelUser = new JLabel("Email"); // Label pour l'email
        panelUser.add(labelUser); // Ajout du label

        inputUser = new JTextField(); // Champ de saisie pour l'email
        inputUser.setPreferredSize(new Dimension(0, 40));
        panelUser.add(inputUser); // Ajout du champ de saisie

        mainPanelLogin.add(panelUser, "pushx, growx, wrap"); // Ajout au panneau principal

        // Panneau pour le champ mot de passe
        panelPassword = new JPanel(new GridLayout(2, 1)); // Layout en grille
        panelPassword.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Marges

        labelPassword = new JLabel("Mot de passe "); // Label pour le mot de passe
        panelPassword.add(labelPassword); // Ajout du label

        inputPassword = new JPasswordField(); // Champ de saisie pour le mot de passe
        inputPassword.setPreferredSize(new Dimension(0, 40));
        panelPassword.add(inputPassword); // Ajout du champ de saisie

        mainPanelLogin.add(panelPassword, "pushx, growx, wrap"); // Ajout au panneau principal

        // Label pour le lien "Mot de passe oublié"
        JLabel passForget = new JLabel("Mot de passe oublier ?");
        passForget.setForeground(new Color(0x4d4429)); // Couleur du texte
        passForget.setFont(new Font("", Font.BOLD, 13)); // Police et taille
        passForget.setHorizontalTextPosition(JLabel.CENTER); // Position du texte
        passForget.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur en forme de main
        passForget.setBorder(BorderFactory.createEmptyBorder(0, 3, 10, 0)); // Marges
        mainPanelLogin.add(passForget, "wrap, center"); // Ajout au panneau principal

        // Bouton "Se connecter"
        btnConnect = myButton(0x4caf50, "Se connecter");
        btnConnect.addActionListener(this); // Ajout d'un écouteur d'événements
        mainPanelLogin.add(btnConnect, "split 2"); // Ajout au panneau principal

        // Bouton "Annuler"
        btnClear = myButton(0x8f5151, "Annuler");
        btnClear.addActionListener(this); // Ajout d'un écouteur d'événements
        mainPanelLogin.add(btnClear, "pushx, growx"); // Ajout au panneau principal

        return mainPanelLogin; // Retourne le panneau principal
    }

    // Méthode pour créer un bouton personnalisé
    public JButton myButton(int color, String title) {
        JButton btn = new JButton(title); // Création du bouton

        btn.setPreferredSize(new Dimension(0, 40)); // Taille préférée
        btn.setBackground(new Color(color)); // Couleur de fond
        btn.setForeground(Color.white); // Couleur du texte
        btn.setFont(new Font("Roboto", Font.PLAIN, 16)); // Police et taille
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur en forme de main

        return btn; // Retourne le bouton
    }

    // Méthode pour gérer les actions des boutons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConnect) { // Si le bouton "Se connecter" est cliqué
            UserController userController = new UserController(); // Création du contrôleur utilisateur

            String email = inputUser.getText(); // Récupération de l'email
            String password = new String(inputPassword.getPassword()); // Récupération du mot de passe

            Users user = userController.login(email, password); // Authentification de l'utilisateur
            String role = String.valueOf(user.getRole()); // Récupération du rôle de l'utilisateur

            // Redirection en fonction du rôle
            switch (role) {
                case "CHEF":
                    new DashBordChefUI().setVisible(true); // Interface pour le rôle "CHEF"
                    dispose(); // Ferme la fenêtre actuelle
                    break;

                case "ENSEIGNANT":
                    new DashBordEnseignantUI(user.getFirstName(), user.getLastName()).setVisible(true); // Interface pour "ENSEIGNANT"
                    dispose(); // Ferme la fenêtre actuelle
                    break;

                case "RESPONSABLE":
                    new DashBordResponsableUI(user.getFirstName() + " " + user.getLastName()).setVisible(true); // Interface pour "RESPONSABLE"
                    dispose(); // Ferme la fenêtre actuelle
                    break;

                default:
                    // Message d'erreur si le rôle est inconnu
                    JOptionPane.showMessageDialog(
                            null,
                            "Une erreur est survenue ! ",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                    break;
            }

            System.out.println("Le role est " + role); // Affiche le rôle dans la console
        }

        if (e.getSource() == btnClear) { // Si le bouton "Annuler" est cliqué
            inputUser.setText(""); // Efface le champ email
            inputPassword.setText(""); // Efface le champ mot de passe
        }
    }
}