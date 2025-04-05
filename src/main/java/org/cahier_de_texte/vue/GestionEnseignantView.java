package org.cahier_de_texte.vue;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.model.UserDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GestionEnseignantView extends JFrame implements ActionListener {

    DashBordChefView dash = new DashBordChefView();
    UserDAO userDAO = new UserDAO();
    UserController userController = new UserController(null);

    public GestionEnseignantView(){

        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelEnseignants() , BorderLayout.CENTER);

        setTitle("Gestion des Enseignants");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JButton btnDeconnexion , btnAjouterEnseignants ,
            btnListeEnseignants , btnModifierEnseignants,
            btnSupprimerEnseignants;
    JTable tabEnseignant;
    DefaultTableModel modelTabEnseignant;

    public JPanel homePanelEnseignants(){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionEnseignant = new JLabel("Gestion des Enseignants");
        labelGestionEnseignant.setBorder(dash.emptyBorder(10 , 0 ,15 , 0));
        labelGestionEnseignant.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionEnseignant , "pushx , growx");

        btnDeconnexion = dash.btnMenuSideBar("Deconnexion", "/img/logout.png");
        btnDeconnexion.addActionListener((ActionEvent e)->{
            new UserLoginView().setVisible(true);
            dispose();
        });

        panel.add(btnDeconnexion ,"wrap , split 2");

        btnAjouterEnseignants = dash.btnMenuSideBar("Ajouter un Enseignant" , "");
        btnAjouterEnseignants.setBackground(new Color(46, 204, 113));
        btnAjouterEnseignants.addActionListener(this);

        btnListeEnseignants = dash.btnMenuSideBar("Liste des Enseignants" , "");

        btnModifierEnseignants = dash.btnMenuSideBar("Modifier" , "");
        btnModifierEnseignants.setBackground(new Color(241, 196, 15));
        btnModifierEnseignants.addActionListener(this);

        btnSupprimerEnseignants = dash.btnMenuSideBar("Supprimer" , "");
        btnSupprimerEnseignants.setBackground(new Color(231, 76, 60));
        btnSupprimerEnseignants.addActionListener(this);

        String[] columnEnseignant = {"ID","Prénom", "Nom", "Email" , "Date de création"};

        modelTabEnseignant = new DefaultTableModel(columnEnseignant , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabEnseignant = new JTable(modelTabEnseignant);
        tabEnseignant.setRowHeight(30);
        tabEnseignant.setFont(new Font("Roboto" , Font.BOLD , 13));

        tabEnseignant.setGridColor(Color.LIGHT_GRAY);
        tabEnseignant.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabEnseignant);

        userDAO.chargerTabEnseignant(modelTabEnseignant);

        panel.add(btnListeEnseignants , "wrap , split 2");
        panel.add(scrollPane , "span , push , grow");
        panel.add(btnAjouterEnseignants , "pushx , growx");
        panel.add(btnModifierEnseignants , "pushx , growx");
        panel.add(btnSupprimerEnseignants , "pushx , growx");

        return panel;
    }


    //Creation de notre Sidebar
    public  JPanel createSideBarPanel(){

        JPanel getPanelSideBar ,  sideBarPanel ;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xE2E9C0));
        sideBarPanel.setPreferredSize(new Dimension(250 , 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/log1.png")));
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

        btnTabBord = dash.btnMenuSideBar("Tableau de bord", "/img/home1.png");
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnTabBord.addActionListener((ActionEvent e)->{
            new DashBordChefView().setVisible(true);
            dispose();
        });

        return panelSideBar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAjouterEnseignants){
            ajouterEnseignant();
        }
        if(e.getSource() == btnModifierEnseignants) {
            modifierEnseignants();
        }
        if(e.getSource() == btnSupprimerEnseignants){
            supprimerEnseignants();
        }
    }

    public void ajouterEnseignant(){
        AjouterEnseignantView fenetre = new AjouterEnseignantView();
        fenetre.setVisible(true);

        fenetre.btnValider.addActionListener((ActionEvent event)->{

            String firstname = fenetre.inputFirstName.getText() ,
                    lastname = fenetre.inputLastName.getText(),
                    email = fenetre.inputEmail.getText(),
                    password = new String(fenetre.inputPassword.getPassword());

            userController.enregistreUser(firstname , lastname , email , password);
            fenetre.dispose();
            userDAO.chargerTabEnseignant(modelTabEnseignant);
        });
    }

    //Methode pour modifier un enseignant
    public void modifierEnseignants(){
        int rowSelected = tabEnseignant.getSelectedRow();

        if (rowSelected == -1){
            JOptionPane.showMessageDialog(
                    null ,
                    "Veuillez selectioner la ligne\n que vous voulez modifier !",
                    null,
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int id = (int) modelTabEnseignant.getValueAt(rowSelected , 0);
        String prenom = (String) modelTabEnseignant.getValueAt(rowSelected , 1);
        String nom = (String) modelTabEnseignant.getValueAt(rowSelected , 2);
        String email = (String) modelTabEnseignant.getValueAt(rowSelected , 3);

        ModifierEnseignantView fenetre = new ModifierEnseignantView();
        fenetre.setVisible(true);

        fenetre.inputFirstName.setText(prenom);
        fenetre.inputLastName.setText(nom);
        fenetre.inputEmail.setText(email);

        fenetre.btnModifier.addActionListener((ActionEvent even)->{

            String newFirstName = fenetre.inputFirstName.getText();
            String newLastname = fenetre.inputLastName.getText();
            String newEmail = fenetre.inputEmail.getText();

            userController.modifierUser(id , newFirstName , newLastname , newEmail);

            userDAO.chargerTabEnseignant(modelTabEnseignant);
            fenetre.dispose();

        });
    }


    public void supprimerEnseignants(){

        int rowSelected = tabEnseignant.getSelectedRow();
        if(rowSelected == -1){
            JOptionPane.showMessageDialog(null , "Veuillez selectionez la ligne\n que vous voulez supprimer !" , "Conseil" , JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modelTabEnseignant.getValueAt(rowSelected , 0);
        userController.supprimerUser(id);
        userDAO.chargerTabEnseignant(modelTabEnseignant);
    }

}
