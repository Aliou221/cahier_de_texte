package org.cahier_de_texte.ui.chef.classe.etudiant;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.controller.chef.EtudiantController;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.cahier_de_texte.ui.chef.classe.GestionClasseUI;
import org.cahier_de_texte.ui.chef.classe.responsable.ResponsableUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class EtudiantUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper;
    Users user;
    UserController userController;
    EtudiantController etudiantController;
    private final String classe;

    public EtudiantUI(String classe){
        this.dashHelper  = new DashBordChefUI();
        this.userController = new UserController();
        this.etudiantController = new EtudiantController();
        this.user = new Users();
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

        btnTabBord = this.dashHelper.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesome.HOME , 18));
        btnTabBord.addActionListener(this);
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnBack = this.dashHelper.btnMenuSideBar("Voir la liste des classes");
        btnBack.setIcon(FontIcon.of(FontAwesome.HAND_O_LEFT , 18));
        btnBack.addActionListener(this);
        panelSideBar.add(btnBack , "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion, btnListeEtudiant , btnResponsable , btnAjouterEtudiant , btnModifierEtudiant , btnSupprimerEtudiant;
    JTable tabClasse;
    DefaultTableModel tabClasseModel;

    public JPanel homePanelClasses(String classe){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Gestion des classes");
        labelGestionClasse.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionClasse , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        btnListeEtudiant = this.dashHelper.btnMenuSideBar("Liste des étudiants");
        btnListeEtudiant.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeEtudiant.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnListeEtudiant , "split 2");

        btnResponsable = this.dashHelper.btnMenuSideBar("Définir comme responsable");
        btnResponsable.setIcon(FontIcon.of(FontAwesome.ID_BADGE , 18));
        btnResponsable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnResponsable.addActionListener(this);

        panel.add(btnResponsable , "split 2 , wrap");

        String[] columnClasse = {"ID" , "Prenom" , "Nom" , "Email" , "Profile"};

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


        btnAjouterEtudiant = this.dashHelper.btnMenuSideBar("Ajouter un Etudiant");
        btnAjouterEtudiant.setIcon(FontIcon.of(FontAwesome.PLUS_CIRCLE, 18));
        btnAjouterEtudiant.setForeground(Color.white);
        btnAjouterEtudiant.setIconTextGap(5);
        btnAjouterEtudiant.setBackground(new Color(46, 204, 113));
        btnAjouterEtudiant.addActionListener(this);

        btnModifierEtudiant = this.dashHelper.btnMenuSideBar("Modifier");
        btnModifierEtudiant.setIcon(FontIcon.of(FontAwesome.EDIT, 18));
        btnModifierEtudiant.setForeground(Color.white);
        btnModifierEtudiant.setIconTextGap(5);
        btnModifierEtudiant.setBackground(new Color(241, 196, 15));
        btnModifierEtudiant.addActionListener(this);

        btnSupprimerEtudiant = this.dashHelper.btnMenuSideBar("Supprimer");
        btnSupprimerEtudiant.setIcon(FontIcon.of(FontAwesome.TRASH, 18));
        btnSupprimerEtudiant.setForeground(Color.white);
        btnSupprimerEtudiant.setIconTextGap(5);
        btnSupprimerEtudiant.setBackground(new Color(231, 76, 60));
        btnSupprimerEtudiant.addActionListener(this);

        this.etudiantController.chargeListeEtudiant(tabClasseModel , classe);

        JPanel panBtn = new JPanel(new GridLayout(1 , 3 , 20 , 20));
        panBtn.add(btnAjouterEtudiant);
        panBtn.add(btnModifierEtudiant );
        panBtn.add(btnSupprimerEtudiant);
        panel.add(panBtn , "pushx , split 2 , growx , span , right" );

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
            new GestionClasseUI().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnAjouterEtudiant){
            AjouterEtudiantUI etudiantView = new AjouterEtudiantUI(this.classe);
            etudiantView.setVisible(true);

            etudiantView.btnAjouter.addActionListener((ActionEvent event)->{
                String prenom = etudiantView.inputFirstName.getText();
                String nom = etudiantView.inputLastName.getText();
                String email = etudiantView.inputEmail.getText();

                this.etudiantController.ajouterEtudiant(prenom , nom , email , this.classe);
                this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);

                etudiantView.inputFirstName.setText(null);
                etudiantView.inputLastName.setText(null);
                etudiantView.inputEmail.setText(null);

            });
        }

        if(e.getSource() == btnModifierEtudiant){
            int rowSelected = tabClasse.getSelectedRow();
            if(rowSelected == -1){
                JOptionPane.showMessageDialog(
                        null ,
                        "Veuillez choisir un étudiant svp !" ,
                        null ,
                        JOptionPane.WARNING_MESSAGE
                );
            }else{

                ModifierEtudiantUI etudiantView = new ModifierEtudiantUI(this.classe);
                etudiantView.setVisible(true);

                int id = (int) tabClasse.getValueAt(rowSelected , 0);
                String prenom = (String) tabClasse.getValueAt(rowSelected , 1);
                String nom = (String) tabClasse.getValueAt(rowSelected , 2);
                String email = (String) tabClasse.getValueAt(rowSelected , 3);

                etudiantView.inputFirstName.setText(prenom);
                etudiantView.inputLastName.setText(nom);
                etudiantView.inputEmail.setText(email);

                etudiantView.btnModifier.addActionListener((ActionEvent event)->{

                    String nouveauPrenom = etudiantView.inputFirstName.getText();
                    String nouveauNom = etudiantView.inputLastName.getText();
                    String nouveauEmail = etudiantView.inputEmail.getText();
                    int idRes = this.etudiantController.getRespo(email);

                    if(this.etudiantController.verifEtudiant(email)){
                        this.etudiantController.modifierEtudiant(nouveauPrenom , nouveauNom , nouveauEmail , id);
                        this.etudiantController.modifRespo(nouveauPrenom , nouveauNom , nouveauEmail , idRes);
                        this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);

                    }else{
                        this.etudiantController.modifierEtudiant(nouveauPrenom , nouveauNom , nouveauEmail , id);
                        this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);
                    }


                    etudiantView.inputFirstName.setText(null);
                    etudiantView.inputLastName.setText(null);
                    etudiantView.inputEmail.setText(null);

                });
            }
        }

        if (e.getSource() == btnSupprimerEtudiant){
            int rowSelected = tabClasse.getSelectedRow();
            if (rowSelected == -1){
                JOptionPane.showMessageDialog(
                        null ,
                        "Veuillez choisir un étudiant svp !",
                        null ,
                        JOptionPane.WARNING_MESSAGE
                );

            }else{
                int id = (int) tabClasse.getValueAt(rowSelected , 0);
                String email = (String) tabClasse.getValueAt(rowSelected , 3);
                int idRes = this.etudiantController.getRespo(email);
                System.out.println(idRes);

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Voulez vous vraiment supprimer cet étudiant ?",
                        null,
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION){
                    if(this.etudiantController.verifEtudiant(email)){
                        this.etudiantController.supprimerEtudiant(id);
                        this.etudiantController.deleteRespo(idRes);
                        this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);
                    }else{
                        this.etudiantController.supprimerEtudiant(id);
                        this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);
                    }

                }
            }
        }

        if (e.getSource() == btnResponsable){
            int rowSelected = tabClasse.getSelectedRow();

            if (rowSelected == -1){
                JOptionPane.showMessageDialog(
                        null,
                        "Veuillez choisir une étudiant svp !"
                );
            }else{
                ResponsableUI responsableView = new ResponsableUI(this.classe);
                responsableView.setVisible(true);

//            int id = (int) tabClasse.getValueAt(rowSelected , 0);
                String prenom = (String) tabClasse.getValueAt(rowSelected , 1);
                String nom = (String) tabClasse.getValueAt(rowSelected , 2);
                String email = (String) tabClasse.getValueAt(rowSelected , 3);

                responsableView.inputFirstName.setText(prenom);
                responsableView.inputLastName.setText(nom);
                responsableView.inputEmail.setText(email);
            }
        }
    }
}
