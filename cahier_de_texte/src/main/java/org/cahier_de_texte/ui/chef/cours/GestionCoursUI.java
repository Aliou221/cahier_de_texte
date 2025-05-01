package org.cahier_de_texte.ui.chef.cours;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.ChefController;
import org.cahier_de_texte.controller.chef.CoursController;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GestionCoursUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper ;
    ChefController chefController ;
    CoursController coursController;
    
    public GestionCoursUI(){
        this.dashHelper = new DashBordChefUI();
        this.chefController = new ChefController();
        this.coursController = new CoursController();
        
        initUI();
    }

    public void initUI(){
        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelEnseignants() , BorderLayout.CENTER);

        setTitle("Attribution des cours");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Creation de notre Sidebar
    public  JPanel createSideBarPanel(){

        JPanel getPanelSideBar ,  sideBarPanel ;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xFFFFFFFF));
        sideBarPanel.setPreferredSize(new Dimension(250 , 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png")));
        Icon logo = new ImageIcon(image.getImage().getScaledInstance(100 , 100 , Image.SCALE_SMOOTH));

        JLabel labelUidt = new JLabel("UIDT");
        labelUidt.setFont(new Font("Arial",Font.BOLD , 25));
        labelUidt.setIcon(logo);
        labelUidt.setHorizontalTextPosition(JLabel.CENTER);
        labelUidt.setVerticalTextPosition(JLabel.BOTTOM);
        labelUidt.setHorizontalAlignment(JLabel.CENTER);

        getPanelSideBar = getPanelSidebar();

        sideBarPanel.add(labelUidt , "wrap , pushx , growx");
        sideBarPanel.add(getPanelSideBar , "pushx , growx");

        return sideBarPanel;
    }

    JButton btnTabBord;
    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = this.dashHelper.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesome.HOME , 18));
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnTabBord.addActionListener((ActionEvent e)->{
            new DashBordChefUI().setVisible(true);
            dispose();
        });

        return panelSideBar;
    }

    JButton btnDeconnexion ,btnAssignerCours,
            btnListeEnseignants, btnAjouterCours ,
            btnListeCours , btnCharger;
    JTable tabEnseignant;
    DefaultTableModel modelTabEnseignant;

    public JPanel homePanelEnseignants(){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelAttCours = new JLabel("Attribution des cours");
        labelAttCours.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelAttCours.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelAttCours , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener(this);

        panel.add(btnDeconnexion ,"wrap , split 2 , right");

        btnListeEnseignants = myBtn("Enseignant et cours assigner", (FontAwesome.USERS));
        panel.add(btnListeEnseignants , "split 4");

        btnAjouterCours = myBtn("Ajouter un cours", FontAwesome.BOOK);
        btnAjouterCours.addActionListener(this);
        panel.add(btnAjouterCours);

        btnListeCours = myBtn("Liste des cours", FontAwesome.BOOKMARK);
        btnListeCours.addActionListener(this);
        panel.add(btnListeCours);

        btnAssignerCours = myBtn("Assigner cours" , FontAwesome.LINK);
        btnAssignerCours.addActionListener(this);
        panel.add(btnAssignerCours);

        btnCharger = myBtn("Charger la table" , FontAwesome.REFRESH);
        btnCharger.addActionListener(this);
        panel.add(btnCharger , "wrap");

        String[] columnEnseignant = {"ID","Enseignant", "Email" , "Cours" , "Classe"};

        modelTabEnseignant = new DefaultTableModel(columnEnseignant , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabEnseignant = new JTable(modelTabEnseignant);
        tabEnseignant.setRowHeight(30);
        tabEnseignant.setFont(new Font("Roboto" , Font.BOLD , 13));
        tabEnseignant.setGridColor(Color.LIGHT_GRAY);
        tabEnseignant.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(tabEnseignant);
        panel.add(scrollPane , "span , push , grow");

         this.chefController.chargeTabEnseignantCours(modelTabEnseignant);

        return panel;
    }

    public JButton myBtn(String title , FontAwesome icon){
        JButton btn;

        btn = this.dashHelper.btnMenuSideBar(title);
        btn.setIcon(FontIcon.of(icon, 18));
        btn.setIconTextGap(5);
        btn.setPreferredSize(new Dimension(getWidth() , 45));

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDeconnexion){
            new LoginUI().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnAssignerCours){
            new AssignerCoursUI().setVisible(true);
        }

        if (e.getSource() == btnAjouterCours){
            AjouterCoursUI ajouterCoursUI = new AjouterCoursUI();
            ajouterCoursUI.setVisible(true);

            ajouterCoursUI.btnValider.addActionListener((ActionEvent even)->{
                String code = ajouterCoursUI.inputCode.getText().trim();
                String intituler = ajouterCoursUI.inputNomCours.getText().trim();
                String credit = ajouterCoursUI.inputCredit.getText().trim();

                if (code.isEmpty() || intituler.isEmpty() || credit.isEmpty()){
                    JOptionPane.showMessageDialog(
                            null,
                            "Veuillez remplir tous les champs svp!",
                            null,
                            JOptionPane.WARNING_MESSAGE
                    );
                }else{
                    int creditCours = Integer.parseInt(credit);
                    this.coursController.ajouterCours(code , intituler , creditCours);

                    ajouterCoursUI.inputCode.setText(null);
                    ajouterCoursUI.inputNomCours.setText(null);
                    ajouterCoursUI.inputCredit.setText(null);
                }
            });
        }

        if(e.getSource() == btnListeCours){
            new ListeCoursUI().setVisible(true);
        }

        if (e.getSource() == btnCharger){
            this.chefController.chargeTabEnseignantCours(modelTabEnseignant);
        }
    }

    public static void main(String[] args) {
        new GestionCoursUI().setVisible(true);
    }
}