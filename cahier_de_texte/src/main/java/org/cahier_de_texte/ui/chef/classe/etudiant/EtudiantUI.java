package org.cahier_de_texte.ui.chef.classe.etudiant;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.controller.chef.EtudiantController;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.cahier_de_texte.ui.chef.classe.GestionClasseUI;
import org.cahier_de_texte.ui.chef.classe.responsable.ResponsableUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class EtudiantUI extends JFrame implements ActionListener {

    private final DashBordChefUI dashHelper;
    private final UserController userController;
    private final EtudiantController etudiantController;
    private final AjouterEtudiantUI ajouterEtudiantUI ;
    private final ModifierEtudiantUI modifierEtudiantUI;
    private final ResponsableUI responsableUI;
    Users user;

    private final String classe;

    // Composants UI - Boutons de navigation
    private JButton btnTabBord;
    private JButton btnBack;
    private JButton btnDeconnexion;

    private JButton btnResponsable;
    private JButton btnRetireResponsable;
    private JButton btnAjouterEtudiant;
    private JButton btnModifierEtudiant;
    private JButton btnSupprimerEtudiant;

    // Composants UI - Table
    private JTable tabClasse;
    private DefaultTableModel tabClasseModel;

    public EtudiantUI(String classe){
        this.dashHelper  = new DashBordChefUI();
        this.userController = new UserController();
        this.etudiantController = new EtudiantController();
        this.ajouterEtudiantUI = new AjouterEtudiantUI(classe);
        this.modifierEtudiantUI = new ModifierEtudiantUI(classe);
        this.responsableUI = new ResponsableUI(classe);

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
    public JPanel createSideBarPanel(){
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

    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = this.dashHelper.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesomeSolid.HOME , 18));
        btnTabBord.addActionListener(this);
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnBack = this.dashHelper.btnMenuSideBar("Voir la liste des classes");
        btnBack.setIcon(FontIcon.of(FontAwesomeSolid.HAND_POINT_LEFT , 18));
        btnBack.addActionListener(this);
        panelSideBar.add(btnBack , "wrap , pushx , growx");

        return panelSideBar;
    }

    public JPanel homePanelClasses(String classe){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Classe : " + this.classe);
        labelGestionClasse.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionClasse , "pushx , growx");

        btnDeconnexion = getButton("Deconnexion" , FontAwesomeSolid.SIGN_OUT_ALT , Color.white);
        btnDeconnexion.setForeground(Color.BLACK);
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        // Composants UI - Gestion des étudiants
        JButton btnListeEtudiant = getButton("Liste des étudiants", FontAwesomeSolid.LIST, Color.white);
        btnListeEtudiant.setForeground(Color.BLACK);
        panel.add(btnListeEtudiant, "split 3");

        btnResponsable = getButton("Définir comme responsable" , FontAwesomeSolid.PENCIL_ALT , Color.white);
        btnResponsable.setForeground(Color.BLACK);
        btnResponsable.addActionListener(this);
        panel.add(btnResponsable);

        btnRetireResponsable = getButton("Retire role responsable" , FontAwesomeSolid.ERASER , Color.white);
        btnRetireResponsable.setForeground(Color.BLACK);
        btnRetireResponsable.addActionListener(this);
        panel.add(btnRetireResponsable , "wrap");

        String[] columnClasse = {"ID" , "Prenom" , "Nom" , "Email" , "Responsable"};

        tabClasseModel = new DefaultTableModel(columnClasse , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return Boolean.class;
                return String.class;
            }
        };

        tabClasse = new JTable(tabClasseModel);
        tabClasse.setRowHeight(30);
        tabClasse.setFont(new Font("Roboto" , Font.BOLD , 13));
        tabClasse.setGridColor(Color.LIGHT_GRAY);
        tabClasse.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(tabClasse);
        panel.add(scrollPane , "span , push , grow");

        btnAjouterEtudiant = getButton("Ajouter un Etudiant" , FontAwesomeSolid.PLUS_CIRCLE , new Color(46, 204, 113));
        btnAjouterEtudiant.addActionListener(this);

        btnModifierEtudiant = getButton("Modifier" , FontAwesomeSolid.EDIT , new Color(241, 196, 15));
        btnModifierEtudiant.addActionListener(this);

        btnSupprimerEtudiant = getButton("Supprimer" , FontAwesomeSolid.TRASH , new Color(231, 76, 60));
        btnSupprimerEtudiant.addActionListener(this);

        this.etudiantController.chargeListeEtudiant(tabClasseModel , classe);

        JPanel panBtn = new JPanel(new GridLayout(1 , 3 , 20 , 20));
        panBtn.add(btnAjouterEtudiant);
        panBtn.add(btnModifierEtudiant );
        panBtn.add(btnSupprimerEtudiant);
        panel.add(panBtn , "pushx , split 2 , growx , span , right" );

        return panel;
    }

    public JButton getButton(String text , FontAwesomeSolid icon , Color color){
        JButton btn = this.dashHelper.btnMenuSideBar(text);
        btn.setIcon(FontIcon.of(icon, 18));
        btn.setForeground(Color.WHITE);
        btn.setIconTextGap(5);
        btn.setBackground(color);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnTabBord){tableauBordAction();}
        if(e.getSource() == btnDeconnexion){deconnexionAction();}
        if(e.getSource() == btnBack){backAction();}
        if(e.getSource() == btnAjouterEtudiant){ajouterEtudiantAction();}
        if(e.getSource() == btnModifierEtudiant){modifierEtudiantAction();}
        if(e.getSource() == btnSupprimerEtudiant){supprimerEtudiantAction();}
        if(e.getSource() == btnRetireResponsable){retireResponsableAction();}
        if(e.getSource() == btnResponsable){responsableAction();}
    }

    // Méthodes de gestion des événements
    private void tableauBordAction() {
        new DashBordChefUI().setVisible(true);
        dispose();
    }

    private void deconnexionAction() {
        new LoginUI().setVisible(true);
        dispose();
    }

    private void backAction() {
        new GestionClasseUI().setVisible(true);
        dispose();
    }

    private void ajouterEtudiantAction() {
        this.ajouterEtudiantUI.setVisible(true);

        ajouterEtudiantUI.btnAjouter.addActionListener((ActionEvent event)->{
            String prenom = this.ajouterEtudiantUI.inputFirstName.getText();
            String nom = this.ajouterEtudiantUI.inputLastName.getText();
            String email = this.ajouterEtudiantUI.inputEmail.getText();

            if(prenom.isEmpty() || nom.isEmpty() || email.isEmpty()){
                JOptionPane.showMessageDialog(null , "Veuillez remplir tous les champs svp !" , "Erreur" , JOptionPane.WARNING_MESSAGE);
            }else{
                if (this.userController.verifUser(email)){
                    JOptionPane.showMessageDialog(null , "Cette email a été utilisé par un autre utilisateur !" , null , JOptionPane.ERROR_MESSAGE);
                }else{
                    this.etudiantController.ajouterEtudiant(prenom , nom , email , this.classe);
                    this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);

                    clearChampAjouter();
                }
            }
        });
    }

    private void clearChampAjouter(){
        ajouterEtudiantUI.inputFirstName.setText(null);
        ajouterEtudiantUI.inputLastName.setText(null);
        ajouterEtudiantUI.inputEmail.setText(null);
        ajouterEtudiantUI.dispose();
    }

    private void modifierEtudiantAction() {
        int rowSelected = tabClasse.getSelectedRow();
        if(rowSelected == -1){
            JOptionPane.showMessageDialog(null , "Veuillez choisir un étudiant svp !" , null , JOptionPane.WARNING_MESSAGE);
        }else{
            modifierEtudiantUI.setVisible(true);

            int id = (int) tabClasse.getValueAt(rowSelected , 0);
            String prenom = (String) tabClasse.getValueAt(rowSelected , 1);
            String nom = (String) tabClasse.getValueAt(rowSelected , 2);
            String email = (String) tabClasse.getValueAt(rowSelected , 3);

            modifierEtudiantUI.inputFirstName.setText(prenom);
            modifierEtudiantUI.inputLastName.setText(nom);
            modifierEtudiantUI.inputEmail.setText(email);

            modifierEtudiantUI.btnModifier.addActionListener((ActionEvent event)->{
                String nouveauPrenom = modifierEtudiantUI.inputFirstName.getText();
                String nouveauNom = modifierEtudiantUI.inputLastName.getText();
                String nouveauEmail = modifierEtudiantUI.inputEmail.getText();
                int idRes = this.etudiantController.getRespo(email);

                if(nouveauPrenom.isEmpty() || nouveauNom.isEmpty() || nouveauEmail.isEmpty()){
                    JOptionPane.showMessageDialog(null , "Veuillez remplir tous les champs svp !" , "Erreur" , JOptionPane.WARNING_MESSAGE);
                }else{
                    if(this.userController.verifUser(nouveauEmail)){ System.out.println("Cette email existe "); return;}

                    if(this.etudiantController.verifMailResponsable(email)){

                        if (this.etudiantController.verifMailResponsable(nouveauEmail) || this.etudiantController.verfMailEtudiant(nouveauEmail)){
                            JOptionPane.showMessageDialog(null, "Cette email a été utilisé par un autre etudiant !", null, JOptionPane.ERROR_MESSAGE);
                        }else{
                            this.etudiantController.modifierEtudiant(nouveauPrenom , nouveauNom , nouveauEmail , id);
                            this.etudiantController.modifRespo(nouveauPrenom , nouveauNom , nouveauEmail , idRes);
                            this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);

                            clearChampModifier();
                        }
                    }
                    else{
                        if(this.etudiantController.verfMailEtudiant(nouveauEmail)){
                            JOptionPane.showMessageDialog(null, "Cette email a été utilisé par un autre etudiant !", null, JOptionPane.ERROR_MESSAGE);
                        }else{
                            this.etudiantController.modifierEtudiant(nouveauPrenom , nouveauNom , nouveauEmail , id);
                            this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);

                            clearChampModifier();
                        }
                    }
                }
            });
        }
    }

    private void clearChampModifier(){
        modifierEtudiantUI.inputFirstName.setText(null);
        modifierEtudiantUI.inputLastName.setText(null);
        modifierEtudiantUI.inputEmail.setText(null);
        modifierEtudiantUI.dispose();
    }

    private void supprimerEtudiantAction() {
        int rowSelected = tabClasse.getSelectedRow();
        if (rowSelected == -1){
            JOptionPane.showMessageDialog(null , "Veuillez choisir un étudiant svp !", null , JOptionPane.WARNING_MESSAGE);
        }else{
            int id = (int) tabClasse.getValueAt(rowSelected , 0);
            String email = (String) tabClasse.getValueAt(rowSelected , 3);
            int idRes = this.etudiantController.getRespo(email);

            int confirm = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer cet étudiant ?", null, JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION){
                if(this.etudiantController.verifMailResponsable(email)){
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

    private void retireResponsableAction() {
        int rowSelected = tabClasse.getSelectedRow();
        if (rowSelected == -1){
            JOptionPane.showMessageDialog(null, "Veuillez choisir un étudiant svp !", null, JOptionPane.WARNING_MESSAGE);
        }else{
            int idEtudiant = (int) tabClasse.getValueAt(rowSelected , 0);
            boolean isValide = (boolean) tabClasseModel.getValueAt(rowSelected, 4);

            String emailEtudiant = (String) tabClasse.getValueAt(rowSelected , 3);
            String prenomEtudiant = (String) tabClasse.getValueAt(rowSelected , 1);
            String nomEtudiant = (String) tabClasse.getValueAt(rowSelected , 2);

            if(!isValide){
                this.etudiantController.retireResponsable(idEtudiant);
                if (this.etudiantController.verifResponsable(emailEtudiant)){
                    this.etudiantController.deleteResponsableUser(emailEtudiant);
                    JOptionPane.showMessageDialog(null, prenomEtudiant + " " + nomEtudiant + " n'est plus responsable pour cette classe !", null, JOptionPane.INFORMATION_MESSAGE);
                    this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);
                }
                else{ JOptionPane.showMessageDialog(null, prenomEtudiant + " " + nomEtudiant + " n'est pas responsable pour cette classe !", null, JOptionPane.ERROR_MESSAGE); }
            }
            else{ JOptionPane.showMessageDialog(null, "Veuillez désélectionner le chekbox\n pour retirer comme responsable svp !", null, JOptionPane.WARNING_MESSAGE); }
        }
    }

    private void responsableAction() {
        int rowSelected = tabClasse.getSelectedRow();
        if(rowSelected == -1){
            JOptionPane.showMessageDialog(null, "Veuillez choisir un étudiant svp !", null, JOptionPane.WARNING_MESSAGE);
        }else{
            int idEtudiant = (int) tabClasse.getValueAt(rowSelected , 0);
            boolean isValide = (boolean) tabClasseModel.getValueAt(rowSelected, 4);

            if(isValide){
                responsableUI.setVisible(true);

                String prenom = (String) tabClasse.getValueAt(rowSelected , 1);
                String nom = (String) tabClasse.getValueAt(rowSelected , 2);
                String email = (String) tabClasse.getValueAt(rowSelected , 3);

                responsableUI.inputFirstName.setText(prenom);
                responsableUI.inputLastName.setText(nom);
                responsableUI.inputEmail.setText(email);

                responsableUI.btnValider.addActionListener((ActionEvent event)->{
                    String nouveauPrenom = responsableUI.inputFirstName.getText();
                    String nouveauNom = responsableUI.inputLastName.getText();
                    String nouveauEmail = responsableUI.inputEmail.getText();
                    String password = new String(responsableUI.inputPassword.getPassword());

                    if(nouveauPrenom.isEmpty() || nouveauNom.isEmpty() || nouveauEmail.isEmpty() || password.isEmpty()){
                        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs svp!", null, JOptionPane.ERROR_MESSAGE);
                    }else{
                        if(this.etudiantController.verifResponsable(nouveauEmail)){
                            JOptionPane.showMessageDialog(null, "Cette etudiant est deja un responsable !", null, JOptionPane.ERROR_MESSAGE);
                        }else{
                            this.etudiantController.defResponsable(idEtudiant);
                            this.etudiantController.ajouterResponsable(nouveauPrenom,nouveauNom,nouveauEmail,password,this.classe);
                            JOptionPane.showMessageDialog(null, nouveauPrenom + " " + nouveauNom + " est nomé comme responsable pour cette classe !", null, JOptionPane.INFORMATION_MESSAGE);
                            this.etudiantController.chargeListeEtudiant(tabClasseModel , this.classe);
                        }
                       clearResponsable();
                    }
                });
            }else{
                JOptionPane.showMessageDialog(null, "Veuillez cocher le chekbox \npour definir comme responsable", null, JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void clearResponsable(){
        responsableUI.inputFirstName.setText(null);
        responsableUI.inputLastName.setText(null);
        responsableUI.inputEmail.setText(null);
        responsableUI.inputPassword.setText(null);
        responsableUI.dispose();
    }
}