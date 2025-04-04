package org.cahier_de_texte.vue;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DashBordChefView extends JFrame implements ActionListener {

    public DashBordChefView(){
        initUI();
    }

    public void initUI() {
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo.png")));
        add(dashboardPanel());

        setTitle("CHEF DE DEPARTEMENT");
        setIconImage(logo.getImage());
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
        sideBarPanel.setBackground(new Color(0xE2E9C0));
        sideBarPanel.setPreferredSize(new Dimension(250 , 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/log1.png")));
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

    JButton btnTabBord , btnGestionEnseignant , btnGestionResponsable , btnGestionCours , btnGestionClasses , btnAttribuerComptes, btnGestionSeance;

    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = btnMenuSideBar("Tableau de bord", "/img/home1.png");
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnGestionEnseignant = btnMenuSideBar("Gestion des Ensignants", "/img/teacher.png");
        panelSideBar.add(btnGestionEnseignant , "wrap , pushx , growx");
        btnGestionEnseignant.addActionListener(this);

        btnGestionResponsable = btnMenuSideBar("Gestion des Responsables", "/img/respos.png");
        panelSideBar.add(btnGestionResponsable , "wrap , pushx , growx");
        btnGestionResponsable.addActionListener(this);

        btnGestionClasses = btnMenuSideBar("Gestion des Classes", "/img/classe.png");
        panelSideBar.add(btnGestionClasses , "wrap , pushx , growx");
        btnGestionClasses.addActionListener(this);

        btnGestionCours = btnMenuSideBar("Attribution des Cours", "/img/cours.png");
        panelSideBar.add(btnGestionCours , "wrap , pushx , growx");

        btnGestionSeance  = btnMenuSideBar("Gestion des Séances", "/img/seance.png");
        panelSideBar.add(btnGestionSeance , "wrap , pushx , growx");

//        btnAttribuerComptes = btnMenuSideBar("Attribution des Comptes", "/img/compte.png");
//        panelSideBar.add(btnAttribuerComptes , "wrap , pushx , growx");


        return panelSideBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnGestionEnseignant) {
            new GestionEnseignantView().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnGestionResponsable) {
            new GestionResponsableView().setVisible(true);
            dispose();
        }

        if(e.getSource() == btnDeconnexion){
            new UserLoginView().setVisible(true);
            dispose();
        }

    }

    public JButton btnMenuSideBar(String title , String urlIcon){
        JButton btn;

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource(urlIcon)));
        Icon resizeImage = new ImageIcon(image.getImage().getScaledInstance(15 , 15 , Image.SCALE_SMOOTH));

        btn = new JButton(title);
        btn.setPreferredSize(new Dimension(0 , 45));
        btn.setIcon(resizeImage);
        btn.setHorizontalTextPosition(JButton.RIGHT);
        btn.setAlignmentX(JButton.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(JButton.CENTER);
        btn.setFont(new Font("Roboto" , Font.BOLD , 14));

        return btn;
    }

    JButton btnDeconnexion;
    public JPanel homePanel(){
        JPanel panEnsignants , panResponsables , panEtudiants , panSeanceValider,panCours , panClasses , licence1 , licence2 , licence3;

        JPanel myHomePanel = new JPanel(new MigLayout());
        myHomePanel.setBorder(emptyBorder(20 , 20 , 0 , 20));

        JLabel labelTabBord = new JLabel("Tableau de bord ");
        labelTabBord.setBorder(emptyBorder(5 ,0 , 15 , 0));
        labelTabBord.setFont(new Font("Roboto" , Font.BOLD , 20));

        btnDeconnexion = btnMenuSideBar("Deconnexion", "/img/logout.png");
        btnDeconnexion.addActionListener(this);


        panEnsignants = panelStat("Nombre d’enseignants" , 30 , new Color(0, 132, 209) , "/img/teachers.png");
        panResponsables = panelStat("Nombre de responsables" , 30 , new Color(137, 69, 69) , "/img/responsables.png");
        panEtudiants = panelStat("Nombre d’étudiants" , 30 , new Color(0, 153, 102) , "/img/student.png");
        panSeanceValider = panelStat("Nombre de séances validées" , 30 ,new Color(241, 196, 15) , "/img/seance.png");
        panCours = panelStat("Nombre de cours total" , 30 , new Color(0, 194, 219) , "/img/cours.png");
        panClasses = panelStat("Nombre de classes" , 30 , new Color(200, 100, 0) , "/img/classe.png");

        JPanel panelThree = new JPanel(new MigLayout());
        panelThree.add(panEnsignants , "pushx , growx");
        panelThree.add(panResponsables , "pushx , growx");
        panelThree.add(panEtudiants , "pushx , growx");

        JPanel panSeancePanCours = new JPanel(new MigLayout());
        panSeancePanCours.add(panSeanceValider , "pushx , growx");
        panSeancePanCours.add(panCours , "pushx , growx");
        panSeancePanCours.add(panClasses , "pushx , growx");

        JPanel panelText = new JPanel(new MigLayout());
        JLabel labelResponsableTab = new JLabel("Responsables & leur classe");
        labelResponsableTab.setFont(new Font("Roboto", Font.BOLD , 20));
        labelResponsableTab.setBorder(emptyBorder(15 , 0 , 20 , 0));

        JLabel labelProf = new JLabel("Enseignants & Cours Assignés");
        labelProf.setFont(new Font("Roboto", Font.BOLD , 20));
        labelProf.setBorder(emptyBorder(15 , 0 , 20 , 0));

        JTable tableRespo =  new JTable(), tabEnseignant = new JTable();
        DefaultTableModel tableModelResponsable = new DefaultTableModel(), tabModelEnseignant = new DefaultTableModel();

        String[] columns = {"Responsable" , "Email" , "Classe"};
        String[] columnEnseignats = {"Enseignant" , "Email" , "Cours" , "Classe"};


        panelText.add(labelResponsableTab);
        panelText.add(labelProf , "wrap");
        panelText.add(tableauScrollPane(tableRespo , tableModelResponsable , columns) , "push , grow");
        panelText.add(tableauScrollPane(tabEnseignant , tabModelEnseignant, columnEnseignats) , "push , grow");

        myHomePanel.add(labelTabBord ,"pushx , growx");
        myHomePanel.add(btnDeconnexion , "split 2 , wrap");
        myHomePanel.add(panelThree , "pushx , growx , span , wrap");
        myHomePanel.add(panSeancePanCours , "span , pushx , grow");
        myHomePanel.add(panelText , "pushx , growx , wrap , span");

        return myHomePanel;
    }
    
    public JScrollPane tableauScrollPane( JTable table , DefaultTableModel model , String[] columns){
        JScrollPane scrollPane;

        model = new DefaultTableModel(columns , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            };
        };
        
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        
        return scrollPane;
    }

    public JPanel panelStat(String title , int numbre , Color color , String iconeUrl){

        ImageIcon icone = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconeUrl)));
        Icon resizeImage = new ImageIcon(icone.getImage().getScaledInstance(30 , 30 , Image.SCALE_SMOOTH));

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
        labelStat.setIcon(resizeImage);
        labelStat.setFont(new Font("Roboto" , Font.BOLD , 20));
        labelStat.setForeground(Color.white);
        labelStat.setIconTextGap(10);
        labelStat.setBorder(emptyBorder(0 , 20 , 15 , 10));

        panel.add(labelName , BorderLayout.NORTH);
        panel.add(labelStat , BorderLayout.CENTER);

        return panel;
    }

    //Creation d'une bordure vide
    public Border emptyBorder(int top , int left , int bottom , int right){
        return BorderFactory.createEmptyBorder(top , left , bottom ,right);
    }

    public static void main(String[] args) {
        new DashBordChefView().setVisible(true);
    }
}
