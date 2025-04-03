package org.cahier_de_texte.vue;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class GestionResponsableView extends JFrame {
    DashBordChefView dash = new DashBordChefView();

    public GestionResponsableView(){

        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelResponsable() , BorderLayout.CENTER);

        setTitle("Gestion des Responsables");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel homePanelResponsable(){
        JButton btnDeconnexion;
        JTable tabResponsable;
        DefaultTableModel modelTab;

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionResponsable = new JLabel("Gestion des Responsables");
        labelGestionResponsable.setBorder(dash.emptyBorder(10 , 0 ,15 , 0));
        labelGestionResponsable.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionResponsable , "pushx , growx");

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion", "/img/logout.png");
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new LoginView().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2");

        JButton btnAjouterResponsable = dash.btnMenuSideBar("Ajouter un Responsable" , "");
        btnAjouterResponsable.setBackground(new Color(46, 204, 113));

        JButton btnListeResponsable = new JButton("Charger la liste des Responsables");
        btnListeResponsable.setCursor(new Cursor(Cursor.HAND_CURSOR));


        JButton btnModifierResponsable = dash.btnMenuSideBar("Modifier" , "");
        btnModifierResponsable.setBackground(new Color(241, 196, 15));

        JButton btnSupprimerResponsable = dash.btnMenuSideBar("Supprimer" , "");
        btnSupprimerResponsable.setBackground(new Color(231, 76, 60));

        String[] columns = {"Prénom", "Nom", "Email" , "Classe" , "Date de création"};

        modelTab = new DefaultTableModel(columns , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            };
        };


        tabResponsable = new JTable(modelTab);
        tabResponsable.setRowHeight(30);
        tabResponsable.setFont(new Font("Dialog" , Font.BOLD , 13));

        tabResponsable.setGridColor(Color.LIGHT_GRAY);
        tabResponsable.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabResponsable);

        btnAjouterResponsable.addActionListener((ActionEvent e)->{
            modelTab.addRow(new Object[]{"Aliou", "CISSE", "aliou@gmail.com" , "Lgi-1", "2025-03-30 03:40:07"});
            frameAjoutResponsable();
        });



        panel.add(btnListeResponsable , "wrap");
        panel.add(scrollPane , "span , push , grow");
        panel.add(btnAjouterResponsable , "pushx , growx");
        panel.add(btnModifierResponsable , "pushx , growx");
        panel.add(btnSupprimerResponsable , "pushx , growx");

        return panel;
    }

    public void frameAjoutResponsable(){

        JLabel labelFirstName , labelLastName , labelEmail , labelPassword;
        JTextField inputFirstName , inputLastName , inputEmail;
        JPasswordField inputPassword;
        JButton btnValider;

        JFrame frame = new JFrame();

        JPanel formPanel = new JPanel(new MigLayout("wrap 1 , gap 8"));
        formPanel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profil.png")));
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(90 , 90 , Image.SCALE_SMOOTH));

        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        formPanel.add(label , "span , wrap, pushx , growx");

        labelFirstName = new JLabel("Prénom");
        labelFirstName.setFont(new Font("Dialog", Font.PLAIN , 15));

        inputFirstName = new JTextField();
        inputFirstName.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(labelFirstName);
        formPanel.add(inputFirstName , "pushx , growx");

        labelLastName = new JLabel("Nom");
        labelLastName.setFont(new Font("Dialog", Font.PLAIN , 15));
        inputLastName = new JTextField();
        inputLastName.setPreferredSize(new Dimension(0 , 40));

        formPanel.add(labelLastName);
        formPanel.add(inputLastName , "pushx , growx");

        labelEmail = new JLabel("Email");
        labelEmail.setFont(new Font("Dialog", Font.PLAIN , 15));
        inputEmail = new JTextField();
        inputEmail.setPreferredSize(new Dimension(0 , 40));

        JLabel labelClasse = new JLabel("Classe");
        formPanel.add(labelClasse , "pushx , growx");

        String[] listeClasse = {"LI 1" , "LI 2" , "LI 3"};
        JComboBox<String> classes = new JComboBox<>(listeClasse);
        formPanel.add(classes , "pushx , growx");

        formPanel.add(labelEmail);
        formPanel.add(inputEmail , "pushx , growx");

        labelPassword = new JLabel("Mot de passe");
        labelPassword.setFont(new Font("Dialog", Font.PLAIN , 15));
        inputPassword = new JPasswordField();
        inputPassword.setPreferredSize(new Dimension(0 , 40));

        formPanel.add(labelPassword);
        formPanel.add(inputPassword , "pushx , growx");

        JLabel l = new JLabel("");
        l.setBorder(dash.emptyBorder(10 , 0 , 0 , 0));
        formPanel.add(l);

        btnValider = new JButton("Valider");
        btnValider.setPreferredSize(new Dimension(0 , 45));
        btnValider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnValider.setBackground(new Color(46, 204, 113));
        btnValider.setFont(new Font("Dialog" , Font.BOLD , 13));
        btnValider.setForeground(Color.white);
        formPanel.add(btnValider , "pushx , growx");

        frame.add(formPanel);

        frame.setTitle("Ajouter un responsable");
        frame.setSize(350 , 560);
        frame.setMinimumSize(new Dimension(350 , 560));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocation(90 , 90);

        frame.setVisible(true);
    }

    //Creation de notre Sidebar
    public  JPanel createSideBarPanel(){

        JPanel getPanelSideBar ,  sideBarPanel ;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xE2E9C0));
        sideBarPanel.setPreferredSize(new Dimension(250 , 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/log1.png")));
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

        btnTabBord = dash.btnMenuSideBar("Tableau de bord", "/img/home1.png");
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnTabBord.addActionListener((ActionEvent e)->{
            new DashBordChefView().setVisible(true);
            dispose();
        });

        return panelSideBar;
    }


}
