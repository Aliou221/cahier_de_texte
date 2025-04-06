package org.cahier_de_texte.vue.chef.responsable;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.vue.UserLoginView;
import org.cahier_de_texte.vue.chef.DashBordChefView;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class GestionResponsableView extends JFrame {
    DashBordChefView dash = new DashBordChefView();

    public GestionResponsableView(){
        FlatLightLaf.setup();

        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelResponsable() , BorderLayout.CENTER);

        setTitle("Gestion des Responsables");
        setSize(1300 , 650);
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

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new UserLoginView().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"split 2 ,wrap ");

        JButton btnAjouterResponsable = dash.btnMenuSideBar("Ajouter un Responsable");
        btnAjouterResponsable.setIcon(FontIcon.of(FontAwesome.PLUS_CIRCLE , 18));
        btnAjouterResponsable.setForeground(Color.WHITE);
        btnAjouterResponsable.setIconTextGap(10);
        btnAjouterResponsable.setBackground(new Color(46, 204, 113));

        JButton btnListeResponsable = dash.btnMenuSideBar("Liste des Responsables");
        btnListeResponsable.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeResponsable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnListeResponsable.setPreferredSize(new Dimension(getWidth() , 45));


        JButton btnModifierResponsable = dash.btnMenuSideBar("Modifier");
        btnModifierResponsable.setIcon(FontIcon.of(FontAwesome.PENCIL , 18));
        btnModifierResponsable.setForeground(Color.WHITE);
        btnModifierResponsable.setIconTextGap(10);
        btnModifierResponsable.setBackground(new Color(241, 196, 15));

        JButton btnSupprimerResponsable = dash.btnMenuSideBar("Supprimer");
        btnSupprimerResponsable.setIcon(FontIcon.of(FontAwesome.TRASH , 18));
        btnSupprimerResponsable.setForeground(Color.WHITE);
        btnSupprimerResponsable.setIconTextGap(10);
        btnSupprimerResponsable.setBackground(new Color(231, 76, 60));

        String[] columns = {"Prénom", "Nom", "Email" , "Classe" , "Date de création"};

        modelTab = new DefaultTableModel(columns , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabResponsable = new JTable(modelTab);
        tabResponsable.setRowHeight(30);
        tabResponsable.setFont(new Font("Roboto" , Font.BOLD , 13));

        tabResponsable.setGridColor(Color.LIGHT_GRAY);
        tabResponsable.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabResponsable);

        btnAjouterResponsable.addActionListener((ActionEvent e)->{
            modelTab.addRow(new Object[]{"Aliou", "CISSE", "aliou@gmail.com" , "Lgi-1", "2025-03-30 03:40:07"});
//            frameAjoutResponsable();
        });


        panel.add(btnListeResponsable , "wrap");
        panel.add(scrollPane , "span , push , grow");
        panel.add(btnAjouterResponsable , "pushx , growx");
        panel.add(btnModifierResponsable , "pushx , growx");
        panel.add(btnSupprimerResponsable , "pushx , growx");

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

    public static void main(String[] args) {
        new GestionResponsableView().setVisible(true);
    }
}
