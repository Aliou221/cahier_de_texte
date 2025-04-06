package org.cahier_de_texte.vue.classe;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.model.ClassesDAO;
import org.cahier_de_texte.vue.UserLoginView;
import org.cahier_de_texte.vue.chef.DashBordChefView;
import org.cahier_de_texte.vue.chef.classe.GestionClasseView;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ClasseView extends JFrame implements ActionListener {
    DashBordChefView dash = new DashBordChefView();
    ClassesDAO classesDAO = new ClassesDAO();

    public ClasseView(String classe){
        FlatLightLaf.setup();

        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelClasses(classe) , BorderLayout.CENTER);

        setTitle("Classe : " + classe);
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JButton btnDeconnexion, btnListeEtudiant;
    JTable tabClasse;
    DefaultTableModel tabClasseModel;

    public JPanel homePanelClasses(String classe){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Gestion des classes");
        labelGestionClasse.setBorder(dash.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionClasse , "pushx , growx");

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        btnListeEtudiant = dash.btnMenuSideBar("Liste des etudiants");
        btnListeEtudiant.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeEtudiant.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnListeEtudiant , "split 2 , wrap");

        String[] columnClasse = {"ID" , "Prenom" , "Nom" , "Email" , "Action"};

        tabClasseModel = new DefaultTableModel(columnClasse , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabClasse = new JTable(tabClasseModel);
        tabClasse.setRowHeight(30);
        tabClasse.setFont(new Font("Roboto" , Font.BOLD , 13));
        tabClasse.setGridColor(Color.LIGHT_GRAY);
        tabClasse.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabClasse);
        panel.add(scrollPane , "span , push , grow");

        classesDAO.chargeListeEtudiant(tabClasseModel , classe);

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

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btnDeconnexion){
            new UserLoginView().setVisible(true);
            dispose();
        }

        if(e.getSource() == btnBack){
            new GestionClasseView().setVisible(true);
            dispose();
        }
    }

}
