package org.cahier_de_texte.ui.chef.cours;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.ChefController;
import org.cahier_de_texte.controller.chef.CoursController;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AssignerCoursUI extends JFrame {
    private final DashBordChefUI dashHelper;
    private final ChefController chefController;
    private final CoursController coursController;

    private JComboBox<String> comboBoxEnseignant, comboBoxCours, comboBoxClasse;
    JButton btnValider;

    public AssignerCoursUI() {
        this.chefController = new ChefController();
        this.coursController = new CoursController();
        this.dashHelper = new DashBordChefUI();
        initUI();
    }

    private void initUI() {
        setTitle("Attribution des cours");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(formePanel());
    }

    private JPanel formePanel() {
        JPanel formPanel = new JPanel(new MigLayout("wrap 1, gap 10"));
        formPanel.setBorder(dashHelper.emptyBorder(20, 20, 20, 20));

        // Logo
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profil.png")));
        Image scaledImage = image.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage), JLabel.CENTER);
        formPanel.add(imageLabel, "span, growx, center");

        // Enseignant
        JLabel labelEnseignant = new JLabel("Enseignant");
        labelEnseignant.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        formPanel.add(labelEnseignant, "growx");
        comboBoxEnseignant = createComboBox();
        chefController.enseignant(comboBoxEnseignant);
        formPanel.add(comboBoxEnseignant, "growx");

        // Cours
        JLabel labelCours = new JLabel("Cours");
        labelCours.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        formPanel.add(labelCours , "growx");
        comboBoxCours = createComboBox();
        chefController.cours(comboBoxCours);
        formPanel.add(comboBoxCours, "growx");

        // Classe
        JLabel labelClasse = new JLabel("Classe");
        labelClasse.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        formPanel.add(labelClasse, "growx");
        comboBoxClasse = createComboBox();
        chefController.classe(comboBoxClasse);
        formPanel.add(comboBoxClasse, "growx");

        formPanel.add(Box.createVerticalStrut(10));

        btnValider = dashHelper.btnMenuSideBar("Assigner");
        btnValider.setBackground(new Color(46, 204, 113));
        btnValider.setForeground(Color.WHITE);
        btnValider.addActionListener(e -> assignerCours());
        formPanel.add(btnValider, "growx");

        return formPanel;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(getWidth(), 40));
        comboBox.setMaximumRowCount(10);
        return comboBox;
    }

    private void assignerCours() {
        String enseignant = (String) comboBoxEnseignant.getSelectedItem();
        String cours = (String) comboBoxCours.getSelectedItem();
        String classe = (String) comboBoxClasse.getSelectedItem();

        if (enseignant == null || cours == null || classe == null || enseignant.isEmpty() || cours.isEmpty() || classe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String[] partEnseignant = enseignant.split(" - ");
            String[] partCours = cours.split(" - ");
            String[] partClasse = classe.split(" - ");

            int idEnseignant = Integer.parseInt(partEnseignant[0]);
            int idCours = Integer.parseInt(partCours[0]);
            String codeCours = partCours[1];
            String intituleCours = partCours[2];
            int idClasse = Integer.parseInt(partClasse[0]);

            coursController.assignerCours(idEnseignant, codeCours, intituleCours, idCours, idClasse);

            JOptionPane.showMessageDialog(this, "Cours assigné avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Nettoyage des champs
            comboBoxEnseignant.setSelectedIndex(-1);
            comboBoxCours.setSelectedIndex(-1);
            comboBoxClasse.setSelectedIndex(-1);
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Une erreur s'est produite : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}