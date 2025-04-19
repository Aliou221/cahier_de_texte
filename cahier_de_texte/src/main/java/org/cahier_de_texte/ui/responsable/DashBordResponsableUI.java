package org.cahier_de_texte.ui.responsable;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class DashBordResponsableUI extends JFrame {
    DashBordChefUI dash = new DashBordChefUI();
    private final String responsable;

    public DashBordResponsableUI(String responsable){
        this.responsable = responsable;
        initUI();
    }

    public void initUI(){
        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanel() , BorderLayout.CENTER);

        setTitle("Bienvenue " + this.responsable);
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
        labelUidt.setFont(new Font("Roboto",Font.BOLD , 25));
        labelUidt.setIcon(logo);
        labelUidt.setHorizontalTextPosition(JLabel.CENTER);
        labelUidt.setVerticalTextPosition(JLabel.BOTTOM);
        labelUidt.setHorizontalAlignment(JLabel.CENTER);

        getPanelSideBar = getPanelSidebar();

        sideBarPanel.add(labelUidt , "wrap , pushx , growx");
        sideBarPanel.add(getPanelSideBar , "pushx , growx");

        return sideBarPanel;
    }

    JButton btnAjouterSeance , btnListeCours;
    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnListeCours = dash.btnMenuSideBar("Liste des cours");
        btnListeCours.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        panelSideBar.add(btnListeCours , "wrap , pushx , growx");

        btnAjouterSeance = dash.btnMenuSideBar("Ajouter une séance");
        btnAjouterSeance.setIcon(FontIcon.of(FontAwesome.BOOK, 18));
        panelSideBar.add(btnAjouterSeance , "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion;
    public JPanel homePanel(){

        JTable tabCours;
        DefaultTableModel modelTabCours;

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelListeCours = new JLabel("Liste de mes cours");
        labelListeCours.setBorder(dash.emptyBorder(10 , 0 ,15 , 0));
        labelListeCours.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelListeCours , "pushx , growx");

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new LoginUI().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2");

        JButton btnListeCours = new JButton("Liste des cours");
        btnListeCours.setFont(new Font("Roboto" , Font.BOLD , 16));
        btnListeCours.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeCours.setPreferredSize(new Dimension(getWidth() , 45));
        btnListeCours.setCursor(new Cursor(Cursor.HAND_CURSOR));

        String[] columnCours = {"Code", "Cours", "Classe concernée", "Crédits"};

        modelTabCours = new DefaultTableModel(columnCours , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabCours = new JTable(modelTabCours);
        tabCours.setRowHeight(30);
        tabCours.setFont(new Font("Roboto" , Font.BOLD , 13));

        tabCours.setGridColor(Color.LIGHT_GRAY);
        tabCours.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabCours);

        panel.add(btnListeCours , "wrap");
        panel.add(scrollPane , "span , push , grow");

        return panel;
    }
}