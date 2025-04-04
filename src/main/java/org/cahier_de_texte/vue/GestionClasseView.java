package org.cahier_de_texte.vue;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class GestionClasseView extends JFrame {
    DashBordChefView dash = new DashBordChefView();

    public GestionClasseView(){

        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelClasses() , BorderLayout.CENTER);

        setTitle("Gestion des classes");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    JButton btnDeconnexion;
    public JPanel homePanelClasses(){

        JTable tabClasse;
        DefaultTableModel modelTab;

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Gestion des classes");
        labelGestionClasse.setBorder(dash.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionClasse , "pushx , growx");

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion", "/img/logout.png");
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new UserLoginView().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2");

        JButton btnAjouterClasse = dash.btnMenuSideBar("Ajouter une Classe" , "");
        btnAjouterClasse.setBackground(new Color(46, 204, 113));

        JButton btnListeClasses = dash.btnMenuSideBar("Charger la liste des Classes" , "");
        btnListeClasses.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnModifierClasse = dash.btnMenuSideBar("Modifier" , "");
        btnModifierClasse.setBackground(new Color(241, 196, 15));

        JButton btnSupprimerClasse = dash.btnMenuSideBar("Supprimer" , "");
        btnSupprimerClasse.setBackground(new Color(231, 76, 60));

        String[] columnClasse = {"Niveau", "Nombre d'étudiant" , "Responsable" , "Date de création"};

        modelTab = new DefaultTableModel(columnClasse , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            };
        };

        tabClasse = new JTable(modelTab);
        tabClasse.setRowHeight(30);
        tabClasse.setFont(new Font("Roboto" , Font.BOLD , 13));

        tabClasse.setGridColor(Color.LIGHT_GRAY);
        tabClasse.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabClasse);

        btnAjouterClasse.addActionListener((ActionEvent e)->{
            modelTab.addRow(new Object[]{"Licene 1" , "21", "Papa cisse", "2025-03-30 03:40:07"});

            new AjouterClasseView().setVisible(true);
        });


        panel.add(btnListeClasses , "wrap , split 2");
        panel.add(scrollPane , "span , push , grow");
        panel.add(btnAjouterClasse , "pushx , growx");
        panel.add(btnModifierClasse , "pushx , growx");
        panel.add(btnSupprimerClasse , "pushx , growx");

        return panel;
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

    public static void main(String[] args) {
        new GestionClasseView().setVisible(true);
    }

}
