package org.cahier_de_texte.ui.responsable;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.responsable.ResponsableController;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class DashBordResponsableUI extends JFrame {
    DashBordChefUI dashHelper;
    ResponsableController responsableController;
    
    private final String responsable;
    private final int idResponsable;

    public DashBordResponsableUI(String responsable , int idResponsable){
        this.idResponsable = idResponsable;
        this.dashHelper = new DashBordChefUI();
        this.responsable = responsable;
        this.responsableController = new ResponsableController();
        initUI();
    }

    public void initUI(){
        FlatLightLaf.setup();
        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanel() , BorderLayout.CENTER);

        setTitle("Bienvenue " + this.responsable);
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Creation de notre Sidebar
    public JPanel createSideBarPanel(){

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

    JButton btnListeSeances;
    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnListeSeances = dashHelper.btnMenuSideBar("Liste des séances");
        btnListeSeances.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        panelSideBar.add(btnListeSeances , "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion;
    public JPanel homePanel(){

        JTable tabSeances;
        DefaultTableModel modelTabSeances;

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelListeSeances = new JLabel("Liste des séances");
        labelListeSeances.setBorder(dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelListeSeances.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelListeSeances , "pushx , growx");

        btnDeconnexion = dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new LoginUI().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2");

        JButton btnListeSeances = dashHelper.btnMenuSideBar("Liste des séances");
        btnListeSeances.setFont(new Font("Roboto" , Font.BOLD , 16));
        btnListeSeances.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeSeances.setPreferredSize(new Dimension(getWidth() , 45));
        btnListeSeances.setCursor(new Cursor(Cursor.HAND_CURSOR));

        String[] columnCours = {"Code", "Intitulé", "Contenu", "Duree", "Date", "Enseignant",  "Validé"};

        modelTabSeances = new DefaultTableModel(null, columnCours) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 6;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) return Boolean.class;
                return String.class;
            }
        };

        JButton btnValiderSeances = dashHelper.btnMenuSideBar("Valider la séance");
        btnValiderSeances.setIcon(FontIcon.of(FontAwesome.CHECK, 18));
        btnValiderSeances.setFont(new Font("Roboto", Font.BOLD, 16));
        btnValiderSeances.setPreferredSize(new Dimension(getWidth() , 45));
        btnValiderSeances.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnValiderSeances.addActionListener(e -> {
            for (int i = 0; i < modelTabSeances.getRowCount(); i++) {
                boolean isValide = (boolean) modelTabSeances.getValueAt(i, 6);
                if (isValide) {
                    // Appelle ton DAO ou affiche dans la console pour simuler
                    System.out.println("Séance validée : " + modelTabSeances.getValueAt(i, 1));
                    JOptionPane.showMessageDialog(
                            null ,
                            "Séance validée" ,
                            null ,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        tabSeances = new JTable(modelTabSeances);
        TableColumnModel columnModel = tabSeances.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(200);
        columnModel.getColumn(6).setPreferredWidth(80);

        tabSeances.setRowHeight(30);
        tabSeances.setFont(new Font("Roboto" , Font.BOLD , 13));

        tabSeances.setGridColor(Color.LIGHT_GRAY);
        tabSeances.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabSeances);

        panel.add(btnListeSeances , "split 2");
        panel.add(btnValiderSeances, "wrap");
        panel.add(scrollPane , "span , push , grow");

        this.responsableController.chargeSeance(modelTabSeances, this.idResponsable);

        return panel;
    }

    public static void main(String[] args) {
        new DashBordResponsableUI("" , 8).setVisible(true);
    }
}