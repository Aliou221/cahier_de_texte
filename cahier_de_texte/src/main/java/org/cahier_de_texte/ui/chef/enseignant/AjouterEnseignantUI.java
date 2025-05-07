package org.cahier_de_texte.ui.chef.enseignant;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AjouterEnseignantUI extends JFrame {
    DashBordChefUI dashHelper;

    JLabel labelFirstName, labelLastName, labelEmail, labelPassword;
    public JTextField inputFirstName, inputLastName, inputEmail;
    public JPasswordField inputPassword;
    public JButton btnValider;

    private static final Font DEFAULT_FONT = new Font("Poppins", Font.PLAIN, 15);

    public AjouterEnseignantUI() {
        this.dashHelper = new DashBordChefUI();
        initUI();
    }

    private void initUI() {
        add(formPanel());
        setTitle("Ajouter un enseignant");
        setSize(350, 550);
        setMinimumSize(new Dimension(350, 550));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel formPanel() {
        JPanel panel = new JPanel(new MigLayout("wrap 1, gap 8"));
        panel.setBorder(dashHelper.emptyBorder(20, 20, 20, 20));

        JLabel avatarLabel = new JLabel();
        avatarLabel.setHorizontalAlignment(JLabel.CENTER);
        avatarLabel.setIcon(loadIcon());
        panel.add(avatarLabel, "span, wrap, pushx, growx");

        labelFirstName = createLabel("Prénom");
        inputFirstName = createInputField();
        panel.add(labelFirstName);
        panel.add(inputFirstName, "pushx, growx");

        labelLastName = createLabel("Nom");
        inputLastName = createInputField();
        panel.add(labelLastName);
        panel.add(inputLastName, "pushx, growx");

        labelEmail = createLabel("Email");
        inputEmail = createInputField();
        panel.add(labelEmail);
        panel.add(inputEmail, "pushx, growx");

        labelPassword = createLabel("Mot de passe");
        inputPassword = new JPasswordField();
        inputPassword.setPreferredSize(new Dimension(0, 40));
        inputPassword.setFont(DEFAULT_FONT);
        panel.add(labelPassword);
        panel.add(inputPassword, "pushx, growx");

        // Espacement
        JLabel spacer = new JLabel();
        spacer.setBorder(dashHelper.emptyBorder(10, 0, 0, 0));
        panel.add(spacer);

        btnValider = dashHelper.btnMenuSideBar("Enregistrer");
        btnValider.setFont(DEFAULT_FONT);
        btnValider.setBackground(new Color(46, 204, 113));
        btnValider.setForeground(Color.white);
        panel.add(btnValider, "pushx, growx");

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        return label;
    }

    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(0, 40));
        field.setFont(DEFAULT_FONT);
        return field;
    }

    private Icon loadIcon() {
        ImageIcon img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profil.png")));
        return new ImageIcon(img.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
    }
}