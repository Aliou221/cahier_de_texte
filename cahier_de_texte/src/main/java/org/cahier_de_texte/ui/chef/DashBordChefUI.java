package org.cahier_de_texte.ui.chef;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.ChefController;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.classe.GestionClasseUI;
import org.cahier_de_texte.ui.chef.cours.GestionCoursUI;
import org.cahier_de_texte.ui.chef.enseignant.GestionEnseignantUI;
import org.cahier_de_texte.ui.chef.seances.GestionSeancesUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
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

    JButton btnTabBord , btnGestionEnseignant , btnAttCours , btnGestionClasses , btnGestionSeance;

    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesome.HOME, 18));
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnGestionEnseignant = btnMenuSideBar("Gestion des Ensignants");
        btnGestionEnseignant.setIcon(FontIcon.of(FontAwesome.USERS, 18));
        panelSideBar.add(btnGestionEnseignant , "wrap , pushx , growx");
        btnGestionEnseignant.addActionListener(this);

        btnGestionClasses = btnMenuSideBar("Gestion des Classes");
        btnGestionClasses.setIcon(FontIcon.of(FontAwesome.GRADUATION_CAP, 18));
        panelSideBar.add(btnGestionClasses , "wrap , pushx , growx");
        btnGestionClasses.addActionListener(this);

        btnAttCours = btnMenuSideBar("Attribution des Cours");
        btnAttCours.setIcon(FontIcon.of(FontAwesome.BOOK, 18));
        panelSideBar.add(btnAttCours , "wrap , pushx , growx");
        btnAttCours.addActionListener(this);

        btnGestionSeance  = btnMenuSideBar("Gestion des Séances");
        btnGestionSeance.setIcon(FontIcon.of(FontAwesome.CALENDAR, 18));
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
        btn.setFont(new Font("Roboto" , Font.BOLD , 14));

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
        labelTabBord.setFont(new Font("Roboto" , Font.BOLD , 20));

        btnDeconnexion = btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener(this);

        panEnsignants = panelStat("Nombre d’enseignants" , this.chefController.getNbEnseignant() , new Color(0, 132, 209) , FontAwesome.USER);
        panResponsables = panelStat("Nombre de responsables" , this.chefController.getNbResponsable() , new Color(137, 69, 69) , FontAwesome.USER_CIRCLE);
        panEtudiants = panelStat("Nombre d’étudiants" , this.chefController.getNbEtudiant() , new Color(0, 153, 102) , FontAwesome.GRADUATION_CAP);
        panSeanceValider = panelStat("Nombre de séances validées" , this.chefController.getNbSeanceValide() ,new Color(241, 196, 15) , FontAwesome.CHECK_CIRCLE);
        panCours = panelStat("Nombre de cours total" , this.chefController.getNbCours() , new Color(0, 194, 219) , FontAwesome.BOOK);
        panClasses = panelStat("Nombre de classes" , this.chefController.getNbClasse() , new Color(200, 100, 0) , FontAwesome.BUILDING);

        JPanel panelThree = new JPanel(new MigLayout("wrap 3"));
        panelThree.add(panEnsignants , "pushx , growx");
        panelThree.add(panResponsables , "pushx , growx");
        panelThree.add(panEtudiants , "pushx , growx");

        panelThree.add(panSeanceValider , "pushx , growx");
        panelThree.add(panCours , "pushx , growx");
        panelThree.add(panClasses , "pushx , growx");

        JLabel labelResponsableTab = new JLabel("Responsables et leur classe");
        labelResponsableTab.setFont(new Font("Roboto", Font.BOLD , 20));
        labelResponsableTab.setBorder(emptyBorder(15 , 0 , 20 , 0));

        JLabel labelProf = new JLabel("Enseignants et Cours Assignés");
        labelProf.setFont(new Font("Roboto", Font.BOLD , 20));
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
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));

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

        this.chefController.chargeTabEnseignantCours(model);

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0 , 400));

        JTableHeader header = table.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));

        return scrollPane;
    }

    public JPanel panelStat(String title , int numbre , Color color , FontAwesome icofont){

        Border shadow = BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 4, true);

        JPanel panel = new JPanel(new BorderLayout());
        panel. setBorder(shadow);
        panel.setBackground(color);

        JLabel labelName = new JLabel(title);
        labelName.setBorder(emptyBorder(15 , 20 , 15 , 10));
        labelName.setFont(new Font("Roboto" , Font.BOLD , 16));
        labelName.setForeground(Color.white);

        numbre = (char) numbre;
        JLabel labelStat = new JLabel(String.valueOf(numbre));
        labelStat.setIcon(FontIcon.of(icofont , 30));
        labelStat.setFont(new Font("Roboto" , Font.BOLD , 20));
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