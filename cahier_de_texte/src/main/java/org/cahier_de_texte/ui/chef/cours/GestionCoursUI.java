package org.cahier_de_texte.ui.chef.cours;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.ChefController;
import org.cahier_de_texte.controller.chef.CoursController;
import org.cahier_de_texte.ui.auth.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
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
        labelUidt.putClientProperty(FlatClientProperties.STYLE, "font: bold 25 Poppins");
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
        btnTabBord.setIcon(FontIcon.of(FontAwesomeSolid.HOME , 18));
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
        labelAttCours.putClientProperty(FlatClientProperties.STYLE, "font: bold 23 Poppins");
        panel.add(labelAttCours , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT , 18));
        btnDeconnexion.addActionListener(this);

        panel.add(btnDeconnexion ,"wrap , split 2 , right");

        btnListeEnseignants = myBtn("Enseignant et cours assigner", (FontAwesomeSolid.USERS));
        panel.add(btnListeEnseignants , "split 2");

        btnAjouterCours = myBtn("Ajouter un cours", FontAwesomeSolid.BOOK);
        btnAjouterCours.setBackground(new Color(46, 204, 113));
        btnAjouterCours.setForeground(Color.WHITE);
        btnAjouterCours.addActionListener(this);

        btnListeCours = myBtn("Liste des cours", FontAwesomeSolid.BOOKMARK);
        btnListeCours.addActionListener(this);
        panel.add(btnListeCours , "wrap");

        btnAssignerCours = myBtn("Assigner cours" , FontAwesomeSolid.LINK);
        btnAssignerCours.setBackground(new Color(241, 196, 15));
        btnAssignerCours.setForeground(Color.WHITE);
        btnAssignerCours.addActionListener(this);

        btnCharger = myBtn("Charger la table" , FontAwesomeSolid.RECYCLE);
        btnCharger.setForeground(Color.WHITE);
        btnCharger.setBackground(new Color(235, 111, 60));
        btnCharger.addActionListener(this);

        String[] columnEnseignant = {"ID","Enseignant", "Email" , "Cours" , "Classe"};

        modelTabEnseignant = new DefaultTableModel(columnEnseignant , 0){
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
        panel.add(scrollPane , "span , push , grow");

        JPanel panBtn = new JPanel(new GridLayout(1 , 3 , 20,20));
        panBtn.add(btnAjouterCours);
        panBtn.add(btnAssignerCours);
        panBtn.add(btnCharger);

        panel.add(panBtn , "span , pushx , growx");

         this.chefController.chargeTabEnseignantCours(modelTabEnseignant);

        return panel;
    }

    public JButton myBtn(String title , FontAwesomeSolid icon){
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
}