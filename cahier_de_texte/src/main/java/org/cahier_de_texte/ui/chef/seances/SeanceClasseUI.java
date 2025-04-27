package org.cahier_de_texte.ui.chef.seances;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.EtudiantController;
import org.cahier_de_texte.controller.chef.SeanceController;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SeanceClasseUI extends JFrame implements ActionListener {
    DashBordChefUI dash ;
    SeanceController seanceController;
    private final String classe;

    public SeanceClasseUI(String classe){
        this.seanceController = new SeanceController();
        this.dash = new DashBordChefUI();
        this.classe = classe;
        initUI();
    }

    public void initUI(){
        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelClasses(this.classe) , BorderLayout.CENTER);

        setTitle("Classe : " + this.classe);
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

    JButton btnTabBord , btnBack;

    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = dash.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesome.HOME , 18));
        btnTabBord.addActionListener(this);
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnBack = dash.btnMenuSideBar("Voir la liste des classes");
        btnBack.setIcon(FontIcon.of(FontAwesome.HAND_O_LEFT , 18));
        btnBack.addActionListener(this);
        panelSideBar.add(btnBack , "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion, btnListeSeances;
    JTable tabClasse;
    DefaultTableModel tabClasseModel;

    public JPanel homePanelClasses(String classe){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Gestion des séances");
        labelGestionClasse.setBorder(dash.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionClasse , "pushx , growx");

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        String[] columnClasse = {"Date Séance" , "Code" , "Cours" , "Contenue" , "Duree" , "Enseignant"};

        tabClasseModel = new DefaultTableModel(columnClasse , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabClasse = new JTable(tabClasseModel);
        TableColumnModel columnModel = tabClasse.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120);
        columnModel.getColumn(1).setPreferredWidth(90);
        columnModel.getColumn(2).setPreferredWidth(130);
        columnModel.getColumn(3).setPreferredWidth(300);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(200);

        tabClasse.setRowHeight(30);
        tabClasse.setFont(new Font("Roboto" , Font.BOLD , 13));
        tabClasse.setGridColor(Color.LIGHT_GRAY);
        tabClasse.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabClasse);
        panel.add(scrollPane , "span , push , grow");
        
        this.seanceController.chargeListeSeancesClasse(tabClasseModel , classe);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTabBord){
            new DashBordChefUI().setVisible(true);
            dispose();
        }

        if(e.getSource() == btnDeconnexion){
            new LoginUI().setVisible(true);
            dispose();
        }

        if(e.getSource() == btnBack){
            new GestionSeancesUI().setVisible(true);
            dispose();
        }
    }
        
}