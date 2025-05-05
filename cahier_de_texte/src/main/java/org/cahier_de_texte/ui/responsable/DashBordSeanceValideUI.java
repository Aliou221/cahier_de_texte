package org.cahier_de_texte.ui.responsable;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.responsable.ResponsableController;
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

public class DashBordSeanceValideUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper;
    ResponsableController responsableController;

    private final String responsable;
    private final int idResponsable;

    public DashBordSeanceValideUI(String responsable , int idResponsable){
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

    public JPanel getPanelSidebar(){
        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnListeSeancesNonValide = this.dashHelper.btnMenuSideBar("Séances non validées");
        btnListeSeancesNonValide.setIcon(FontIcon.of(FontAwesomeSolid.LIST , 18));
        btnListeSeancesNonValide.addActionListener(this);
        panelSideBar.add(btnListeSeancesNonValide, "wrap , pushx , growx");

        return panelSideBar;
    }

    JTable tabSeancesValide;
    DefaultTableModel modelTabSeancesValide;
    JButton btnDeconnexion , btnListeSeancesNonValide;
    public JPanel homePanel(){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelListeSeances = new JLabel("Liste des séances validées");
        labelListeSeances.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelListeSeances.putClientProperty(FlatClientProperties.STYLE, "font: bold 23 Poppins");
        panel.add(labelListeSeances , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT , 18));
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new LoginUI().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2");

        String[] columnCours = {"ID Seance", "Code", "Intitulé", "Contenu", "Duree", "Date", "Enseignant",  "Validé"};

        modelTabSeancesValide = new DefaultTableModel(null, columnCours) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 7;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Boolean.class;
                return String.class;
            }
        };

        tabSeancesValide = new JTable(modelTabSeancesValide);
        tabSeancesValide.setRowHeight(30);
        tabSeancesValide.setGridColor(Color.LIGHT_GRAY);
        tabSeancesValide.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabSeancesValide);

        panel.add(scrollPane , "span , push , grow");

        this.responsableController.chargeSeanceValide(modelTabSeancesValide, this.idResponsable);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnListeSeancesNonValide){
            new DashBordResponsableUI(this.responsable , this.idResponsable).setVisible(true);
            dispose();
        }
    }
}