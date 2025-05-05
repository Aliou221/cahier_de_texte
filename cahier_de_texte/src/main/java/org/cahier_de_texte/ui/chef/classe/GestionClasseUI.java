package org.cahier_de_texte.ui.chef.classe;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.ClasseController;
import org.cahier_de_texte.ui.auth.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.cahier_de_texte.ui.chef.classe.etudiant.EtudiantUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GestionClasseUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper;
    ClasseController classeController;

    public GestionClasseUI() {
        this.dashHelper = new DashBordChefUI();
        this.classeController = new ClasseController();
        initUI();
    }

    public void initUI() {
        FlatLightLaf.setup();
        add(createSideBarPanel(), BorderLayout.WEST);
        add(homePanelClasses(), BorderLayout.CENTER);

        setTitle("Gestion des classes");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel createSideBarPanel() {
        JPanel getPanelSideBar, sideBarPanel;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xFFFFFFFF));
        sideBarPanel.setPreferredSize(new Dimension(250, 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png")));
        Icon logo = new ImageIcon(image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        JLabel labelUidt = new JLabel("UIDT");
        labelUidt.putClientProperty(FlatClientProperties.STYLE, "font: bold 25 Poppins");
        labelUidt.setIcon(logo);
        labelUidt.setHorizontalTextPosition(JLabel.CENTER);
        labelUidt.setVerticalTextPosition(JLabel.BOTTOM);
        labelUidt.setHorizontalAlignment(JLabel.CENTER);

        getPanelSideBar = getPanelSidebar();

        sideBarPanel.add(labelUidt, "wrap, pushx, growx");
        sideBarPanel.add(getPanelSideBar, "pushx, growx");

        return sideBarPanel;
    }

    JButton btnTabBord;

    public JPanel getPanelSidebar() {
        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = this.dashHelper.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesomeSolid.HOME, 18));
        btnTabBord.addActionListener(this);
        panelSideBar.add(btnTabBord, "wrap, pushx, growx");

        return panelSideBar;
    }

    JButton btnDeconnexion, btnPlusInfo, btnAjouterClasses, btnModifierClasses, btnSupprimerClasse;
    JTable tabClasse;
    DefaultTableModel tabClasseModel;

    public JPanel homePanelClasses() {
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20, 20, 20, 20));

        JLabel labelGestionClasse = new JLabel("Liste des classes et effectifs");
        labelGestionClasse.setBorder(this.dashHelper.emptyBorder(10, 0, 15, 0));
        labelGestionClasse.putClientProperty(FlatClientProperties.STYLE, "font: bold 23 Poppins");
        panel.add(labelGestionClasse, "pushx, growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT, 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion, "wrap, split 2");

        btnPlusInfo = this.dashHelper.btnMenuSideBar("Plus informations");
        btnPlusInfo.setIcon(FontIcon.of(FontAwesomeSolid.INFO_CIRCLE, 18));
        panel.add(btnPlusInfo, "wrap, split 2");
        btnPlusInfo.addActionListener(this);

        String[] columnClasse = {"Classe", "Responsable", "Email Reponsable", "Effectif"};
        tabClasseModel = new DefaultTableModel(columnClasse, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabClasse = new JTable(tabClasseModel);
        tabClasse.setRowHeight(30);
        tabClasse.setGridColor(Color.LIGHT_GRAY);
        tabClasse.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabClasse);
        panel.add(scrollPane, "span, push, grow");

        btnAjouterClasses = this.dashHelper.btnMenuSideBar("Ajouter un Classe");
        btnAjouterClasses.setIcon(FontIcon.of(FontAwesomeSolid.PLUS_CIRCLE, 18));
        btnAjouterClasses.setForeground(Color.white);
        btnAjouterClasses.setIconTextGap(5);
        btnAjouterClasses.setBackground(new Color(46, 204, 113));
        btnAjouterClasses.addActionListener(this);

        btnModifierClasses = this.dashHelper.btnMenuSideBar("Modifier");
        btnModifierClasses.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, 18));
        btnModifierClasses.setForeground(Color.white);
        btnModifierClasses.setIconTextGap(5);
        btnModifierClasses.setBackground(new Color(241, 196, 15));
        btnModifierClasses.addActionListener(this);

        btnSupprimerClasse = this.dashHelper.btnMenuSideBar("Supprimer");
        btnSupprimerClasse.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, 18));
        btnSupprimerClasse.setForeground(Color.white);
        btnSupprimerClasse.setIconTextGap(5);
        btnSupprimerClasse.setBackground(new Color(231, 76, 60));
        btnSupprimerClasse.addActionListener(this);

        JPanel panBtn = new JPanel(new GridLayout(1, 3, 20, 20));
        panBtn.add(btnAjouterClasses);
        panBtn.add(btnModifierClasses);
        panBtn.add(btnSupprimerClasse);
        panel.add(panBtn, "pushx, split 2, growx, span, right");

        refreshClasseTable();
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAjouterClasses) {
            AjouterClasseUI ajoutClasse = new AjouterClasseUI();
            showUI(ajoutClasse);

            ajoutClasse.btnValider.addActionListener((ActionEvent even) -> {
                String nom = ajoutClasse.inputNiveau.getText();

                if (isFieldEmpty(nom, "Veuillez remplir ce champ svp!")) return;

                classeController.ajouterClasse(nom);
                refreshClasseTable();
                ajoutClasse.dispose();
            });
        }

        if (e.getSource() == btnModifierClasses) {
            int rowSelected = tabClasse.getSelectedRow();

            if (rowSelected == -1) {
                showWarning("Veuillez choisir une classe !");
                return;
            }

            ModifierClasseUI modifClasse = new ModifierClasseUI();
            showUI(modifClasse);

            String nom = (String) tabClasse.getValueAt(rowSelected, 0);
            modifClasse.inputNiveau.setText(nom);

            modifClasse.btnValider.addActionListener((ActionEvent event) -> {
                String nouveauNom = modifClasse.inputNiveau.getText();

                if (isFieldEmpty(nouveauNom, "Veuillez remplir ce champ svp !")) return;

                classeController.modifierClasse(nom, nouveauNom);
                refreshClasseTable();
                modifClasse.dispose();
            });
        }

        if (e.getSource() == btnSupprimerClasse) {
            int rowSelected = tabClasse.getSelectedRow();

            if (rowSelected == -1) {
                showWarning("Veuillez choisir une classe !");
                return;
            }

            if (confirm("Voulez-vous vraiment supprimer cette classe ?")) {
                String nom = (String) tabClasse.getValueAt(rowSelected, 0);
                classeController.supprimerClasse(nom);
                refreshClasseTable();
            }
        }

        if (e.getSource() == btnTabBord) {
            new DashBordChefUI().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnPlusInfo) {
            int rowSelected = tabClasse.getSelectedRow();

            if (rowSelected == -1) {
                showWarning("Veuillez choisir une classe svp !");
                return;
            }

            String classeName = (String) tabClasse.getValueAt(rowSelected, 0);
            if (confirm("Voulez-vous consulter la liste de : \n" + classeName + " ?")) {
                new EtudiantUI(classeName).setVisible(true);
                dispose();
            }
        }

        if (e.getSource() == btnDeconnexion) {
            new LoginUI().setVisible(true);
            dispose();
        }
    }

    // Méthodes utilitaires pour éviter les répétitions
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(null, message, null, JOptionPane.WARNING_MESSAGE);
    }

    private boolean isFieldEmpty(String value, String message) {
        if (value.trim().isEmpty()) {
            showWarning(message);
            return true;
        }
        return false;
    }

    private boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void refreshClasseTable() {
        classeController.chargeTabClasse(tabClasseModel);
    }

    private void showUI(JFrame ui) {
        ui.setVisible(true);
    }
}