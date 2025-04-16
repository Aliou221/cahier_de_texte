package org.cahier_de_texte.vue.chef.cours;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.vue.UserLoginView;
import org.cahier_de_texte.vue.chef.DashBordChefView;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GestionCoursView extends JFrame implements ActionListener {
    UserController userController = new UserController(null);
    DashBordChefView dash = new DashBordChefView();

    public GestionCoursView(){
        FlatLightLaf.setup();

        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelEnseignants() , BorderLayout.CENTER);

        setTitle("Attribution des cours");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JButton btnDeconnexion ,btnAssignerCours,
            btnListeEnseignants, btnAjouterCours , btnListeCours , btnModifierEnseignants,
            btnSupprimerEnseignants;
    JTable tabEnseignant;
    DefaultTableModel modelTabEnseignant;

    public JPanel homePanelEnseignants(){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelAttCours = new JLabel("Attribution des cours");
        labelAttCours.setBorder(dash.emptyBorder(10 , 0 ,15 , 0));
        labelAttCours.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelAttCours , "pushx , growx");

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new UserLoginView().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2 , right");

        btnAssignerCours = dash.btnMenuSideBar("Assigner cours");
        btnAssignerCours.setIcon(FontIcon.of(FontAwesome.LINK, 18));
        btnAssignerCours.setForeground(Color.white);
        btnAssignerCours.setIconTextGap(5);
        btnAssignerCours.setBackground(new Color(46, 204, 113));
        btnAssignerCours.addActionListener(this);

        btnListeEnseignants = dash.btnMenuSideBar("Liste des enseignants et leur cours");
        btnListeEnseignants.setIcon(FontIcon.of(FontAwesome.USERS , 18));
        btnListeEnseignants.setPreferredSize(new Dimension(getWidth() , 45));
        btnListeEnseignants.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAjouterCours = dash.btnMenuSideBar("Ajouter un cours");
        btnAjouterCours.setIcon(FontIcon.of(FontAwesome.BOOK , 18));
        btnAjouterCours.setPreferredSize(new Dimension(getWidth() , 45));
        btnAjouterCours.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAjouterCours.addActionListener(this);

        btnListeCours = dash.btnMenuSideBar("Liste des cours");
        btnListeCours.setIcon(FontIcon.of(FontAwesome.BOOKMARK , 18));
        btnListeCours.setPreferredSize(new Dimension(getWidth() , 45));
        btnListeCours.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnModifierEnseignants = dash.btnMenuSideBar("Modifier");
        btnModifierEnseignants.setIcon(FontIcon.of(FontAwesome.EDIT, 18));
        btnModifierEnseignants.setForeground(Color.white);
        btnModifierEnseignants.setIconTextGap(5);
        btnModifierEnseignants.setBackground(new Color(241, 196, 15));
        btnModifierEnseignants.addActionListener(this);

        btnSupprimerEnseignants = dash.btnMenuSideBar("Supprimer");
        btnSupprimerEnseignants.setIcon(FontIcon.of(FontAwesome.TRASH, 18));
        btnSupprimerEnseignants.setForeground(Color.white);
        btnSupprimerEnseignants.setIconTextGap(5);
        btnSupprimerEnseignants.setBackground(new Color(231, 76, 60));
        btnSupprimerEnseignants.addActionListener(this);

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

        JPanel panBtn = new JPanel(new GridLayout(1 , 3 , 20 , 20));
        panBtn.add(btnAssignerCours);
        panBtn.add(btnModifierEnseignants );
        panBtn.add(btnSupprimerEnseignants);


        panel.add(btnListeEnseignants , "split 3");
        panel.add(btnAjouterCours);
        panel.add(btnListeCours , "wrap");
        panel.add(scrollPane , "span , push , grow");
        panel.add(panBtn , "pushx , split 2 , growx , span , right" );

        userController.chargeTabEnseignantCours(modelTabEnseignant);

        return panel;
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

        btnTabBord = dash.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesome.HOME , 18));
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnTabBord.addActionListener((ActionEvent e)->{
            new DashBordChefView().setVisible(true);
            dispose();
        });

        return panelSideBar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAssignerCours){
            new AssignerCoursView().setVisible(true);
        }

        if (e.getSource() == btnAjouterCours){
            new AjouterCoursView().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new GestionCoursView().setVisible(true);
    }

}
