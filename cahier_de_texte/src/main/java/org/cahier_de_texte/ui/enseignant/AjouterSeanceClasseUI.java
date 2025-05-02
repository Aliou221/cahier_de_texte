package org.cahier_de_texte.ui.enseignant;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.controller.chef.EtudiantController;
import org.cahier_de_texte.controller.chef.SeanceController;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AjouterSeanceClasseUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper;
    Users user;
    UserController userController;
    EtudiantController etudiantController;
    SeanceController seanceController;
    private final String classe;
    private final String nom;
    private final String prenom;
    private final String coursIntitule;

    private final int idEnseignant;
    private final int idCours;


    public AjouterSeanceClasseUI(String classe , String prenom , String nom , int idEnseignant ,String coursIntitule , int idCours){
        this.dashHelper  = new DashBordChefUI();
        this.userController = new UserController();
        this.etudiantController = new EtudiantController();
        this.seanceController = new SeanceController();
        this.user = new Users();
        this.classe = classe;
        this.coursIntitule = coursIntitule;
        this.idEnseignant = idEnseignant;
        this.idCours = idCours;
        this.prenom = prenom;
        this.nom = nom;
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

    JButton btnBack;

    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnBack = this.dashHelper.btnMenuSideBar("Retour");
        btnBack.setIcon(FontIcon.of(FontAwesome.HAND_O_LEFT , 18));
        btnBack.addActionListener(this);
        panelSideBar.add(btnBack , "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion, btnListeSeances, btnAjouterSeance , btnModifierSeances, btnSupprimerSeances;
    JTable tabSeance;
    DefaultTableModel tabSeanceModel;

    public JPanel homePanelClasses(String classe){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Classe : " + classe);
        labelGestionClasse.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionClasse , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        btnListeSeances = this.dashHelper.btnMenuSideBar("Liste des séances");
        btnListeSeances.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeSeances.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnListeSeances.setBackground(new Color(33, 150, 243));
        panel.add(btnListeSeances, "split 4");

        btnAjouterSeance = this.dashHelper.btnMenuSideBar("Ajouter une séance");
        btnAjouterSeance.setIcon(FontIcon.of(FontAwesome.PLUS_CIRCLE, 18));
        btnAjouterSeance.setForeground(Color.white);
        btnAjouterSeance.setIconTextGap(5);
//        btnAjouterSeance.setBackground(new Color(46, 204, 113));
        btnAjouterSeance.setBackground(new Color(76, 175, 80));
        btnAjouterSeance.addActionListener(this);
        panel.add(btnAjouterSeance);

        btnModifierSeances = this.dashHelper.btnMenuSideBar("Modifier");
        btnModifierSeances.setIcon(FontIcon.of(FontAwesome.EDIT, 18));
        btnModifierSeances.setForeground(Color.white);
        btnModifierSeances.setIconTextGap(5);
        btnModifierSeances.setBackground(new Color(241, 196, 15));
//        btnModifierSeances.setBackground(new  Color(33, 150, 243));
        btnModifierSeances.addActionListener(this);
        panel.add(btnModifierSeances);

        btnSupprimerSeances = this.dashHelper.btnMenuSideBar("Supprimer");
        btnSupprimerSeances.setIcon(FontIcon.of(FontAwesome.TRASH, 18));
        btnSupprimerSeances.setForeground(Color.white);
        btnSupprimerSeances.setIconTextGap(5);
//        btnSupprimerSeances.setBackground(new Color(231, 76, 60));
        btnSupprimerSeances.setBackground(new Color(244, 67, 54));
        btnSupprimerSeances.addActionListener(this);
        panel.add(btnSupprimerSeances , "wrap");

        String[] columnSeances = {"ID" , "Code" , "Intitule" , "Contenue" , "Durée" , "Date"};

        tabSeanceModel = new DefaultTableModel(columnSeances, 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabSeance = new JTable(tabSeanceModel);
        tabSeance.setRowHeight(30);
        tabSeance.setFont(new Font("Roboto" , Font.BOLD , 13));
        tabSeance.setGridColor(Color.LIGHT_GRAY);
        tabSeance.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabSeance);
        panel.add(scrollPane , "span , push , grow");

        this.seanceController.chargeListeSeances(tabSeanceModel, this.classe , this.idEnseignant , this.coursIntitule);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnDeconnexion){
            new LoginUI().setVisible(true);
            dispose();
        }
        if(e.getSource() == btnBack){
            new DashBordEnseignantUI(this.prenom , this.nom , this.idEnseignant).setVisible(true);
            dispose();
        }

        if (e.getSource() == btnAjouterSeance){
            ajouterSeance();
        }

        if (e.getSource() == btnModifierSeances){
            modifierSeance();
        }

        if (e.getSource() == btnSupprimerSeances){
            supprimerSeance();
        }
    }

    private void ajouterSeance(){
        AjouterSeanceUI ajouterSeanceUI = new AjouterSeanceUI(this.classe);
        ajouterSeanceUI.setVisible(true);

        ajouterSeanceUI.btnAjouter.addActionListener((ActionEvent even) -> {
            String contenue = ajouterSeanceUI.inputContenue.getText();
            String dateSeance = ajouterSeanceUI.inputDate.getText();
            String dureeSeance = ajouterSeanceUI.inputDuree.getText();

            if (contenue.isEmpty() || dateSeance.isEmpty() || dureeSeance.isEmpty()){
                JOptionPane.showMessageDialog(
                        null,
                        "Veuillez remplir tous les champs svp !",
                        null,
                        JOptionPane.WARNING_MESSAGE
                );
            }else{
                int duree = Integer.parseInt(dureeSeance);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(dateSeance, formatter);

                this.seanceController.ajouterSeance(this.idCours , Timestamp.valueOf(dateTime), contenue , duree);
                JOptionPane.showMessageDialog(
                        null,
                        "La séance a été ajouté avec succée !",
                        null,
                        JOptionPane.INFORMATION_MESSAGE
                );

                this.seanceController.chargeListeSeances(tabSeanceModel, this.classe , this.idEnseignant , this.coursIntitule);

                ajouterSeanceUI.inputContenue.setText(null);
                ajouterSeanceUI.inputDate.setText(null);
                ajouterSeanceUI.inputDuree.setText(null);
            }
        });
    }

    private void modifierSeance(){
        int row = tabSeance.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(
                    null,
                    "Veuillez selectioner une séance svp !",
                    null,
                    JOptionPane.WARNING_MESSAGE
            );
        }else{
            int idSeance = (int) tabSeanceModel.getValueAt(row , 0);
            String contenueSeance = (String) tabSeanceModel.getValueAt(row , 3);
            String dureeSeance = (String) tabSeanceModel.getValueAt(row , 4);
            String dateSeance = (String) tabSeanceModel.getValueAt(row , 5);

            String[] tabDateHeur = dateSeance.split(" ");
            String[] tabDate = tabDateHeur[0].split("/");
            String[] tabHeur = tabDateHeur[1].split(":");

            ModifierSeanceUI modifierSeanceUI = new ModifierSeanceUI(this.classe);
            modifierSeanceUI.setVisible(true);

            modifierSeanceUI.inputContenue.setText(contenueSeance);
            modifierSeanceUI.inputDate.setText(
                    tabDate[2] + "-" + tabDate[1] + "-" + tabDate[0] + " " +
                    tabHeur[0] + ":" + tabHeur[1] + ":" + tabHeur[2]
            );
            modifierSeanceUI.inputDuree.setText(dureeSeance);

            modifierSeanceUI.btnModifier.addActionListener((ActionEvent even) -> {
                String contenue = modifierSeanceUI.inputContenue.getText();
                String date = modifierSeanceUI.inputDate.getText();
                String duree = modifierSeanceUI.inputDuree.getText();

                if (contenue.isEmpty() || date.isEmpty() || duree.isEmpty()){
                    JOptionPane.showMessageDialog(
                            null,
                            "Veuillez remplir tous les champs svp !",
                            null,
                            JOptionPane.WARNING_MESSAGE
                    );
                }else{
                    int dureeCours = Integer.parseInt(duree);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dateTimeSeance = LocalDateTime.parse(date , formatter);

                    this.seanceController.modifierSeance(idSeance , Timestamp.valueOf(dateTimeSeance),  contenue , dureeCours);

                    JOptionPane.showMessageDialog(
                            null,
                            "La séance a été modifié avec succée !",
                            null,
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    this.seanceController.chargeListeSeances(tabSeanceModel, this.classe , this.idEnseignant , this.coursIntitule);

                    modifierSeanceUI.inputContenue.setText(null);
                    modifierSeanceUI.inputDate.setText(null);
                    modifierSeanceUI.inputDuree.setText(null);
                }

            });
        }
    }

    public void supprimerSeance(){
        int row = tabSeance.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(
                    null,
                    "Veuillez selectionez une seance svp !",
                    null,
                    JOptionPane.WARNING_MESSAGE
            );
        }else{
            int idSeance = (int) tabSeanceModel.getValueAt(row , 0);

            int opt = JOptionPane.showConfirmDialog(
                    null,
                    "Voulez vous vraiment supprimer cette séance ?",
                    null,
                    JOptionPane.YES_NO_OPTION
            );

            if (opt == JOptionPane.YES_OPTION){
                this.seanceController.supprimerSeance(idSeance);
                JOptionPane.showMessageDialog(
                  null,
                  "La séance a été supprimé avec succée !",
                  null,
                  JOptionPane.INFORMATION_MESSAGE
                );
                this.seanceController.chargeListeSeances(tabSeanceModel, this.classe , this.idEnseignant , this.coursIntitule);
            }
        }
    }
}