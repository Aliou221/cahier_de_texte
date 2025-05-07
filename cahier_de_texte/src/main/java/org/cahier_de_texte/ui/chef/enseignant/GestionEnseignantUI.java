package org.cahier_de_texte.ui.chef.enseignant;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.controller.chef.EnseignantController;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.ui.auth.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GestionEnseignantUI extends JFrame implements ActionListener {
    private final DashBordChefUI dashHelper;
    private final EnseignantController enseignantController;
    private final UserController userController;
    Users enseignant;

    public GestionEnseignantUI() {
        this.enseignant = new Users();
        this.dashHelper = new DashBordChefUI();
        this.enseignantController = new EnseignantController();
        this.userController = new UserController();

        initUI();
    }

    public void initUI() {
        add(createSideBarPanel(), BorderLayout.WEST);
        add(homePanelEnseignants(), BorderLayout.CENTER);

        setTitle("Gestion des Enseignants");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel createSideBarPanel() {
        JPanel sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(Color.WHITE);
        sideBarPanel.setPreferredSize(new Dimension(250, 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png")));
        Icon logo = new ImageIcon(image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        JLabel labelUidt = new JLabel("UIDT");
        labelUidt.setIcon(logo);
        labelUidt.setHorizontalTextPosition(JLabel.CENTER);
        labelUidt.setVerticalTextPosition(JLabel.BOTTOM);
        labelUidt.setHorizontalAlignment(JLabel.CENTER);
        labelUidt.putClientProperty(FlatClientProperties.STYLE, "font: bold 25 Poppins");

        JPanel getPanelSideBar = getPanelSidebar();

        sideBarPanel.add(labelUidt, "wrap , pushx , growx");
        sideBarPanel.add(getPanelSideBar, "pushx , growx");

        return sideBarPanel;
    }

    JButton btnTabBord;
    public JPanel getPanelSidebar() {
        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(null);

        btnTabBord = createStyledButton("Tableau de bord", FontAwesomeSolid.HOME);
        btnTabBord.addActionListener(e -> {
            new DashBordChefUI().setVisible(true);
            dispose();
        });
        panelSideBar.add(btnTabBord, "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion, btnAjouterEnseignants, btnModifierEnseignants, btnSupprimerEnseignants;
    JTable tabEnseignant;
    DefaultTableModel modelTabEnseignant;

    public JPanel homePanelEnseignants() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20, 20, 20, 20));

        JLabel labelGestionEnseignant = new JLabel("Liste des enseignants".toUpperCase());
        labelGestionEnseignant.putClientProperty(FlatClientProperties.STYLE, "font: bold 23 Poppins");
        panel.add(labelGestionEnseignant, "pushx , growx");

        btnDeconnexion = createStyledButton("Deconnexion", FontAwesomeSolid.SIGN_OUT_ALT);
        btnDeconnexion.addActionListener(e -> {
            new LoginUI().setVisible(true);
            dispose();
        });
        panel.add(btnDeconnexion, "wrap , split 2");

        btnAjouterEnseignants = createColoredButton("Ajouter un Enseignant", FontAwesomeSolid.PLUS_CIRCLE, new Color(46, 204, 113));
        btnModifierEnseignants = createColoredButton("Modifier", FontAwesomeSolid.EDIT, new Color(241, 196, 15));
        btnSupprimerEnseignants = createColoredButton("Supprimer", FontAwesomeSolid.TRASH, new Color(231, 76, 60));

        btnAjouterEnseignants.addActionListener(this);
        btnModifierEnseignants.addActionListener(this);
        btnSupprimerEnseignants.addActionListener(this);

        String[] columnEnseignant = {"ID", "Prénom", "Nom", "Email", "Date de création"};

        modelTabEnseignant = new DefaultTableModel(columnEnseignant, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabEnseignant = new JTable(modelTabEnseignant);
        tabEnseignant.setRowHeight(30);
        tabEnseignant.setGridColor(Color.LIGHT_GRAY);
        tabEnseignant.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(tabEnseignant);
        enseignantController.chargeTabEnseignant(modelTabEnseignant);

        JPanel panBtn = new JPanel(new GridLayout(1, 3, 20, 20));
        panBtn.add(btnAjouterEnseignants);
        panBtn.add(btnModifierEnseignants);
        panBtn.add(btnSupprimerEnseignants);

        panel.add(scrollPane, "span , push , grow");
        panel.add(panBtn, "pushx , split 2 , growx , span , right");

        return panel;
    }

    public JButton createStyledButton(String title, FontAwesomeSolid icon) {
        JButton btn = this.dashHelper.btnMenuSideBar(title);
        btn.setIcon(FontIcon.of(icon, 18));
        btn.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        return btn;
    }

    public JButton createColoredButton(String title, FontAwesomeSolid icon, Color bgColor) {
        JButton btn = createStyledButton(title, icon);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setIconTextGap(5);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAjouterEnseignants) ajouterEnseignant();
        if (e.getSource() == btnModifierEnseignants) modifierEnseignants();
        if (e.getSource() == btnSupprimerEnseignants) supprimerEnseignants();
    }

    public void ajouterEnseignant() {
        AjouterEnseignantUI fenetre = new AjouterEnseignantUI();
        fenetre.setVisible(true);

        fenetre.btnValider.addActionListener(event -> {
            String firstname = fenetre.inputFirstName.getText();
            String lastname = fenetre.inputLastName.getText();
            String email = fenetre.inputEmail.getText();
            String password = new String(fenetre.inputPassword.getPassword());

            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showMessage("Veuillez remplir tous les champs svp !", "Erreur", JOptionPane.WARNING_MESSAGE);
            } else if (userController.verifUser(email)) {
                showMessage("Cette email est deja utiliser par un autre utilisateur !", null, JOptionPane.ERROR_MESSAGE);
            } else {
                enseignantController.enregistreUser(firstname, lastname, email, password);
                enseignantController.chargeTabEnseignant(modelTabEnseignant);
                fenetre.dispose();
            }
        });
    }

    public void modifierEnseignants() {
        int rowSelected = tabEnseignant.getSelectedRow();
        if (rowSelected == -1) {
            showMessage("Veuillez selectioner la ligne\n que vous voulez modifier !", null, JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modelTabEnseignant.getValueAt(rowSelected, 0);
        String prenom = (String) modelTabEnseignant.getValueAt(rowSelected, 1);
        String nom = (String) modelTabEnseignant.getValueAt(rowSelected, 2);
        String email = (String) modelTabEnseignant.getValueAt(rowSelected, 3);

        ModifierEnseignantUI fenetre = new ModifierEnseignantUI();
        fenetre.setVisible(true);

        fenetre.inputFirstName.setText(prenom);
        fenetre.inputLastName.setText(nom);
        fenetre.inputEmail.setText(email);

        fenetre.btnModifier.addActionListener(e -> {
            String newFirstName = fenetre.inputFirstName.getText();
            String newLastname = fenetre.inputLastName.getText();
            String newEmail = fenetre.inputEmail.getText();

            if (newFirstName.isEmpty() || newLastname.isEmpty() || newEmail.isEmpty()) {
                showMessage("Veuillez remplir tous les champs svp !", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (newEmail.equals(email)){
                enseignant = userController.modifierUser(newFirstName, newLastname, newEmail, id);
                enseignantController.chargeTabEnseignant(modelTabEnseignant);
                fenetre.dispose();
                return;
            }

            if (userController.verifUser(newEmail)) {
                showMessage("Cette email est deja utiliser par un autre utilisateur !", null, JOptionPane.ERROR_MESSAGE);
                return;
            }
            enseignant = userController.modifierUser(newFirstName, newLastname, newEmail, id);
            enseignantController.chargeTabEnseignant(modelTabEnseignant);
            fenetre.dispose();
        });
    }

    public void supprimerEnseignants() {
        int rowSelected = tabEnseignant.getSelectedRow();
        if (rowSelected == -1) {
            showMessage("Veuillez selectionez la ligne\n que vous voulez supprimer !", "Conseil", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int reponse = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer l'utilisateur ?", null, JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            int id = (int) modelTabEnseignant.getValueAt(rowSelected, 0);
            enseignant = userController.deleteUser(id);
            enseignantController.chargeTabEnseignant(modelTabEnseignant);
        }
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }
}
