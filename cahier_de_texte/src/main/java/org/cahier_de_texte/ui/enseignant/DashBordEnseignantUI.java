package org.cahier_de_texte.ui.enseignant;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.CoursController;
import org.cahier_de_texte.controller.enseignant.EnseignantController;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class DashBordEnseignantUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper;
    EnseignantController enseignantController;
    CoursController coursController;
    
    private final String nom;
    private final String prenom;
    private final int idEnseignant;

    public DashBordEnseignantUI(String prenom , String nom , int idEnseignant){
        this.dashHelper = new DashBordChefUI();
        this.enseignantController = new EnseignantController();
        this.coursController = new CoursController();
        this.idEnseignant = idEnseignant;
        this.prenom = prenom;
        this.nom = nom;
        initUI();
    }

    public void initUI(){
        FlatLightLaf.setup();
        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanel() , BorderLayout.CENTER);

        setTitle("Bienvenue " + this.prenom + " " + this.nom);
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

    JButton btnAjouterSeance;
    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnAjouterSeance = this.dashHelper.btnMenuSideBar("Ajouter une séance");
        btnAjouterSeance.setIcon(FontIcon.of(FontAwesome.BOOK, 18));
        btnAjouterSeance.addActionListener(this);
        panelSideBar.add(btnAjouterSeance , "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion;
    JTable tabCours;
    DefaultTableModel modelTabCours;
    public JPanel homePanel(){
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelListeCours = new JLabel("Liste de mes cours");
        labelListeCours.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelListeCours.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelListeCours , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new LoginUI().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2");


        String[] columnCours = {"Code", "Cours", "Crédits" , "Classe concernée"};

        modelTabCours = new DefaultTableModel(columnCours , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabCours = new JTable(modelTabCours);
        tabCours.setRowHeight(30);
        tabCours.setFont(new Font("Roboto" , Font.BOLD , 13));

        tabCours.setGridColor(Color.LIGHT_GRAY);
        tabCours.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabCours);
        panel.add(scrollPane , "span , push , grow");
        this.enseignantController.chargeListeCoursAssigner(modelTabCours , this.idEnseignant);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAjouterSeance){
            int selectedRow = tabCours.getSelectedRow();
            if (selectedRow == -1){
                JOptionPane.showMessageDialog(
                        null,
                        "Veuillez choisir une classe svp !",
                        null,
                        JOptionPane.WARNING_MESSAGE
                );
            }else{
                String nomClasse = tabCours.getValueAt(selectedRow , 3).toString();
                String coursIntitule = tabCours.getValueAt(selectedRow , 1).toString();
                String codeCours = tabCours.getValueAt(selectedRow , 0).toString();
                int idCours = this.coursController.getIdCours(codeCours);

                System.out.println("Classe : " + nomClasse);
                System.out.println("Cours : " + codeCours);
                System.out.println("Id cours : " + idCours);

                new AjouterSeanceClasseUI(nomClasse , this.prenom ,  this.nom , this.idEnseignant , coursIntitule , idCours).setVisible(true);
                dispose();
            }
        }
    }
}