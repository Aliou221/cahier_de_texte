package org.cahier_de_texte.vue.chef.classe;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.model.ClassesDAO;
import org.cahier_de_texte.vue.UserLoginView;
import org.cahier_de_texte.vue.chef.DashBordChefView;
import org.cahier_de_texte.vue.classe.ClasseView;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GestionClasseView extends JFrame implements ActionListener {
    DashBordChefView dash = new DashBordChefView();
    ClassesDAO classesDAO = new ClassesDAO();

    public GestionClasseView(){
        FlatLightLaf.setup();

        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelClasses() , BorderLayout.CENTER);

        setTitle("Gestion des classes");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JButton btnDeconnexion, btnPlusInfo , btnListeClasses;
    JTable tabClasse;
    DefaultTableModel tabClasseModel;

    public JPanel homePanelClasses(){

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

        btnListeClasses = dash.btnMenuSideBar("Liste des Classes");
        btnListeClasses.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeClasses.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnListeClasses , "split 2");

        btnPlusInfo = dash.btnMenuSideBar("Plus informations");
        panel.add(btnPlusInfo , "wrap , split 2");
        btnPlusInfo.addActionListener(this);

        String[] columnClasse = {"Classe", "Responsable" , "Email Reponsable" , "Effectif"};

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

        classesDAO.chargeTabClasse(tabClasseModel);
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
        btnTabBord.addActionListener(this);
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        return panelSideBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnTabBord){
            new DashBordChefView().setVisible(true);
            dispose();
        }

        if(e.getSource() == btnPlusInfo){
            int rowSelected = tabClasse.getSelectedRow();

            if(rowSelected == -1){
                JOptionPane.showMessageDialog(
                        null ,
                        "Veuillez choisir une classe svp !",
                        null,
                        JOptionPane.WARNING_MESSAGE
                );
            }
            else{

                String classeName = (String) tabClasse.getValueAt(rowSelected , 0);

                JOptionPane.showMessageDialog(
                        null,
                        "Classe " + classeName,
                        null ,
                        JOptionPane.INFORMATION_MESSAGE
                );

                new ClasseView(classeName).setVisible(true);
                dispose();
            }



        }

        if(e.getSource() == btnDeconnexion){
            new UserLoginView().setVisible(true);
            dispose();
        }
    }

}
