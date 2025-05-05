package org.cahier_de_texte.ui.chef;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.ChefController;
import org.cahier_de_texte.ui.auth.LoginUI;
import org.cahier_de_texte.ui.chef.classe.GestionClasseUI;
import org.cahier_de_texte.ui.chef.cours.GestionCoursUI;
import org.cahier_de_texte.ui.chef.enseignant.GestionEnseignantUI;
import org.cahier_de_texte.ui.chef.seances.GestionSeancesUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DashBordChefUI extends JFrame implements ActionListener {
    ChefController chefController;

    public DashBordChefUI(){
        this.chefController = new ChefController();
        initUI();
    }

    public void initUI() {
        FlatLightLaf.setup();
        add(dashboardPanel());

        setTitle("CHEF DE DEPARTEMENT");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel dashboardPanel(){
        JPanel mainPanel = new JPanel(new BorderLayout());
        JScrollPane HomeScrollPane = new JScrollPane(homePanel());

        mainPanel.add(createSideBarPanel() , BorderLayout.WEST);
        mainPanel.add(HomeScrollPane , BorderLayout.CENTER);

        return mainPanel;
    }

    public  JPanel createSideBarPanel(){
        JPanel getPanelSideBar ,  sideBarPanel ;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xFFFFFFFF));
        sideBarPanel.setPreferredSize(new Dimension( 300 , 0));

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

    JButton btnTabBord , btnGestionEnseignant , btnAttCours , btnGestionClasses , btnGestionSeance;

    public JPanel getPanelSidebar(){
        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesomeSolid.HOME, 18));
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnGestionEnseignant = btnMenuSideBar("Gestion des Ensignants");
        btnGestionEnseignant.setIcon(FontIcon.of(FontAwesomeSolid.USERS, 18));
        panelSideBar.add(btnGestionEnseignant , "wrap , pushx , growx");
        btnGestionEnseignant.addActionListener(this);

        btnGestionClasses = btnMenuSideBar("Gestion des Classes");
        btnGestionClasses.setIcon(FontIcon.of(FontAwesomeSolid.GRADUATION_CAP, 18));
        panelSideBar.add(btnGestionClasses , "wrap , pushx , growx");
        btnGestionClasses.addActionListener(this);

        btnAttCours = btnMenuSideBar("Attribution des Cours");
        btnAttCours.setIcon(FontIcon.of(FontAwesomeSolid.BOOK, 18));
        panelSideBar.add(btnAttCours , "wrap , pushx , growx");
        btnAttCours.addActionListener(this);

        btnGestionSeance  = btnMenuSideBar("Gestion des Séances");
        btnGestionSeance.setIcon(FontIcon.of(FontAwesomeSolid.CALENDAR, 18));
        btnGestionSeance.addActionListener(this);
        panelSideBar.add(btnGestionSeance , "wrap , pushx , growx");

        return panelSideBar;
    }

    public JButton btnMenuSideBar(String title){
        JButton btn;

        btn = new JButton(title);
        btn.setPreferredSize(new Dimension(0 , 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(JButton.CENTER);
        btn.putClientProperty(FlatClientProperties.STYLE, "font: bold 14 Roboto");

        return btn;
    }

    JButton btnDeconnexion;
    public JPanel homePanel(){
        JPanel panEnsignants , panResponsables ,
                panEtudiants , panSeanceValider,
                panCours , panClasses;

        JPanel myHomePanel = new JPanel(new MigLayout());
        myHomePanel.setBorder(emptyBorder(20 , 20 , 0 , 20));

        JLabel labelTabBord = new JLabel("Tableau de bord ");
        labelTabBord.setBorder(emptyBorder(5 ,0 , 15 , 0));
        labelTabBord.putClientProperty(FlatClientProperties.STYLE, "font: bold 20 Poppins");

        btnDeconnexion = btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT, 18));
        btnDeconnexion.addActionListener(this);

        panEnsignants = panelStat("Nombre d’enseignants" , this.chefController.getNbEnseignant() , new Color(63, 81, 181) , FontAwesomeSolid.USER);//Color(63, 81, 181)
        panResponsables = panelStat("Nombre de responsables" , this.chefController.getNbResponsable() , new Color(255, 152, 0) , FontAwesomeSolid.USER_CIRCLE);//Color(255, 152, 0)
        panEtudiants = panelStat("Nombre d’étudiants" , this.chefController.getNbEtudiant() , new Color(251, 192, 45) , FontAwesomeSolid.GRADUATION_CAP);//Color(251, 192, 45)
        panSeanceValider = panelStat("Nombre de séances validées" , this.chefController.getNbSeanceValide() ,new Color(56, 142, 60) , FontAwesomeSolid.CHECK_CIRCLE);//Color(56, 142, 60)
        panCours = panelStat("Nombre de cours total" , this.chefController.getNbCours() , new Color(33, 150, 243) , FontAwesomeSolid.BOOK); //Color(33, 150, 243)
        panClasses = panelStat("Nombre de classes" , this.chefController.getNbClasse() , new Color(76, 175, 80) , FontAwesomeSolid.BUILDING);//Color(76, 175, 80)

        JPanel panelThree = new JPanel(new MigLayout("wrap 3"));
        panelThree.add(panEnsignants , "pushx , growx");
        panelThree.add(panResponsables , "pushx , growx");
        panelThree.add(panEtudiants , "pushx , growx");

        panelThree.add(panSeanceValider , "pushx , growx");
        panelThree.add(panCours , "pushx , growx");
        panelThree.add(panClasses , "pushx , growx");

        JLabel labelResponsableTab = new JLabel("Responsables et leur classe");
        labelResponsableTab.putClientProperty(FlatClientProperties.STYLE, "font: bold 23 Poppins");
        labelResponsableTab.setBorder(emptyBorder(15 , 0 , 20 , 0));

        JLabel labelProf = new JLabel("Enseignants et Cours Assignés");
        labelProf.putClientProperty(FlatClientProperties.STYLE, "font: bold 20 Poppins");
        labelProf.setBorder(emptyBorder(15 , 0 , 20 , 0));

        String[] columns = {"Responsable" , "Email" , "Classe"};
        String[] columnEnseignats = {"ID" ,"Enseignant" , "Email" , "Cours" , "Classe"};

        myHomePanel.add(labelTabBord ,"pushx , growx");
        myHomePanel.add(btnDeconnexion , "split 2 , wrap");
        myHomePanel.add(panelThree , "pushx , growx , span , wrap");
        myHomePanel.add(labelProf , "wrap");
        myHomePanel.add(tableauScrollPaneEnseignants(columnEnseignats) , "pushx , growx , span");
        myHomePanel.add(labelResponsableTab , "wrap");
        myHomePanel.add(tableauScrollPaneResponsable(columns) , "pushx , growx , span");

        return myHomePanel;
    }

    public JScrollPane tableauScrollPaneResponsable( String[] columns){
        JScrollPane scrollPane;

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0 , 400));

        JTableHeader header = table.getTableHeader();
        header.putClientProperty(FlatClientProperties.STYLE, "font: bold 14 Poppins");

        this.chefController.chargeTabResponsable(model);
        return scrollPane;
    }

    public JScrollPane tableauScrollPaneEnseignants(String[] columns){
        JScrollPane scrollPane;

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0 , 400));

        JTableHeader header = table.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));

        this.chefController.chargeTabEnseignantCours(model);

        return scrollPane;
    }

    public JPanel panelStat(String title , int numbre , Color color , FontAwesomeSolid icofont){

        Border shadow = BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 4, true);

        JPanel panel = new JPanel(new BorderLayout());
        panel. setBorder(shadow);
        panel.setBackground(color);

        JLabel labelName = new JLabel(title);
        labelName.setBorder(emptyBorder(15 , 20 , 15 , 10));
        labelName.putClientProperty(FlatClientProperties.STYLE, "font: bold 16 Roboto");
        labelName.setForeground(Color.white);

        numbre = (char) numbre;
        JLabel labelStat = new JLabel(String.valueOf(numbre));
        labelStat.setIcon(FontIcon.of(icofont , 30));
        labelStat.putClientProperty(FlatClientProperties.STYLE, "font: bold 18 Poppins");
        labelStat.setForeground(Color.white);
        labelStat.setIconTextGap(10);
        labelStat.setBorder(emptyBorder(0 , 20 , 15 , 10));

        panel.add(labelName , BorderLayout.NORTH);
        panel.add(labelStat , BorderLayout.CENTER);

        return panel;
    }

    public Border emptyBorder(int top , int left , int bottom , int right){
        return BorderFactory.createEmptyBorder(top , left , bottom ,right);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnGestionEnseignant) {
            new GestionEnseignantUI().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnGestionClasses){
            new GestionClasseUI().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnGestionSeance){
            new GestionSeancesUI().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnAttCours){
            new GestionCoursUI().setVisible(true);
            dispose();
        }

        if(e.getSource() == btnDeconnexion){
            new LoginUI().setVisible(true);
            dispose();
        }
    }
}