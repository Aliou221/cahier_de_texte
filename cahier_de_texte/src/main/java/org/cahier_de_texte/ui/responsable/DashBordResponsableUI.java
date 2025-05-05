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
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DashBordResponsableUI extends JFrame implements ActionListener {
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

    JButton btnListeSeancesValide;
    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnListeSeancesValide = this.dashHelper.btnMenuSideBar("Séances Valides");
        btnListeSeancesValide.setIcon(FontIcon.of(FontAwesomeSolid.LIST , 18));
        btnListeSeancesValide.addActionListener(this);
        panelSideBar.add(btnListeSeancesValide, "wrap , pushx , growx");

        return panelSideBar;
    }

    JTable tabSeances;
    DefaultTableModel modelTabSeances;
    JButton btnDeconnexion , btnValiderSeances ;
    public JPanel homePanel(){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelListeSeances = new JLabel("Liste des séances non validees".toUpperCase());
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

        modelTabSeances = new DefaultTableModel(null, columnCours) {
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

        btnValiderSeances = this.dashHelper.btnMenuSideBar("Valider la séance");
        btnValiderSeances.setIcon(FontIcon.of(FontAwesomeSolid.CHECK, 18));
        btnValiderSeances.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Roboto");
        btnValiderSeances.setPreferredSize(new Dimension(getWidth() , 45));
        btnValiderSeances.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnValiderSeances, "wrap");

        btnValiderSeances.addActionListener(this);

        tabSeances = new JTable(modelTabSeances);
        tabSeances.setRowHeight(30);
        tabSeances.setGridColor(Color.LIGHT_GRAY);
        tabSeances.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabSeances);

        panel.add(scrollPane , "span , push , grow");

        this.responsableController.chargeSeanceNonValide(modelTabSeances, this.idResponsable);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnListeSeancesValide){
            new DashBordSeanceValideUI(this.responsable , this.idResponsable).setVisible(true);
            dispose();
        }

        if (e.getSource() == btnValiderSeances){
            validerSeance();
        }
    }

    private void validerSeance(){
        int row = tabSeances.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(null, "Veuillez sélectioner une seance svp !", null, JOptionPane.WARNING_MESSAGE);
        }else{
            int idSeance = (int) tabSeances.getValueAt(row , 0);
            boolean isValide = (boolean) modelTabSeances.getValueAt(row, 7);
            if (isValide) {
                if (this.responsableController.verifIdSeance(idSeance)){
                    JOptionPane.showMessageDialog(null, "Cette séance a été déja validé", null, JOptionPane.ERROR_MESSAGE);
                }else{
                    this.responsableController.insertSeanceValide(this.idResponsable , idSeance);
                    this.responsableController.valide(idSeance);
                    JOptionPane.showMessageDialog(null , "La Séance a été validée avec succée !" , null , JOptionPane.INFORMATION_MESSAGE);
                }
                this.responsableController.chargeSeanceNonValide(modelTabSeances, this.idResponsable);
            }else{
                JOptionPane.showMessageDialog(null, "Veuillez cocher le case pour valider ", null, JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}