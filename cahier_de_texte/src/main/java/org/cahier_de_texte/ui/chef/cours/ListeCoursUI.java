package org.cahier_de_texte.ui.chef.cours;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.CoursController;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ListeCoursUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper;
    CoursController coursController;

    JButton btnModifierCours;
    JButton btnSupprimerCours;
    JTable tabCours;

    DefaultTableModel modelCours;

    public  ListeCoursUI(){
        this.coursController = new CoursController();
        this.dashHelper  = new DashBordChefUI();
        initUI();
    }

    public void initUI(){
        add(formePanel());

        setTitle("Liste des cours");
        setSize(600 , 500);
        setMinimumSize(new Dimension(600 , 500));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel formePanel(){
        JPanel tabPanel = new JPanel(new MigLayout());

        btnModifierCours = dashHelper.btnMenuSideBar("Modifier un cours");
        btnModifierCours.setIcon(FontIcon.of(FontAwesomeSolid.EDIT, 18));
        btnModifierCours.setForeground(Color.white);
        btnModifierCours.setIconTextGap(5);
        btnModifierCours.setBackground(new Color(241, 196, 15));
        btnModifierCours.setPreferredSize(new Dimension(getWidth() ,45));
        btnModifierCours.addActionListener(this);
        tabPanel.add(btnModifierCours , "split 2");

        btnSupprimerCours = dashHelper.btnMenuSideBar("Supprimer un cours");
        btnSupprimerCours.setIcon(FontIcon.of(FontAwesomeSolid.TRASH, 18));
        btnSupprimerCours.setForeground(Color.white);
        btnSupprimerCours.setIconTextGap(5);
        btnSupprimerCours.setBackground(new Color(231, 76, 60));
        btnSupprimerCours.setPreferredSize(new Dimension(getWidth() ,45));
        btnSupprimerCours.addActionListener(this);
        tabPanel.add(btnSupprimerCours , "wrap");

        String[] columnCours = {"Code" , "Intitulé" , "Credit"};

        modelCours = new DefaultTableModel(columnCours , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabCours = new JTable(modelCours);
        tabCours.setRowHeight(30);
        tabCours.setGridColor(Color.LIGHT_GRAY);
        tabCours.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabCours);
        tabPanel.add(scrollPane , "span , push , grow");

        this.coursController.listeDesCours(modelCours);

        return tabPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnModifierCours){
            int rowSelected = tabCours.getSelectedRow();
            if (rowSelected == -1){
                JOptionPane.showMessageDialog(null, "Veuillez choisir un cour svp !", null, JOptionPane.WARNING_MESSAGE);
            }else{
                String code = tabCours.getValueAt(rowSelected , 0).toString();
                String nomCours = tabCours.getValueAt(rowSelected , 1).toString();
                String credit = tabCours.getValueAt(rowSelected , 2).toString();

                ModifierCoursUI modifierCoursUI = new ModifierCoursUI();
                modifierCoursUI.setVisible(true);

                modifierCoursUI.inputCode.setText(code);
                modifierCoursUI.inputNomCours.setText(nomCours);
                modifierCoursUI.inputCredit.setText(credit);

                modifierCoursUI.btnValider.addActionListener(event->{
                    String newCode = modifierCoursUI.inputCode.getText();
                    String newIntitule = modifierCoursUI.inputNomCours.getText();
                    int newCredit = Integer.parseInt(modifierCoursUI.inputCredit.getText());

                    if (newCode.isEmpty() || newIntitule.isEmpty() || modifierCoursUI.inputCredit.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs svp !", null, JOptionPane.WARNING_MESSAGE);
                    }else{
                        this.coursController.modifierCours(newCode , newIntitule , newCredit , code);
                        JOptionPane.showMessageDialog(null, "Modification du cour avec succée !", null, JOptionPane.INFORMATION_MESSAGE);
                        this.coursController.listeDesCours(modelCours);

                        modifierCoursUI.inputCode.setText(null);
                        modifierCoursUI.inputNomCours.setText(null);
                        modifierCoursUI.inputCredit.setText(null);

                    }
                });
            }
        }

        if (e.getSource() == btnSupprimerCours){
            int selectedRow = tabCours.getSelectedRow();
            if (selectedRow == -1){
                JOptionPane.showMessageDialog(null, "Veuillez choisir un cour svp !", null,    JOptionPane.WARNING_MESSAGE);
            }else{
                int opt = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer ce cours ?\n" + tabCours.getValueAt(selectedRow , 1), null, JOptionPane.YES_NO_OPTION);

                if (opt == JOptionPane.YES_OPTION){
                    this.coursController.supprimerCours((String) tabCours.getValueAt(selectedRow , 0));
                    JOptionPane.showMessageDialog(null, "La suppression a été effectué avec succées !", null, JOptionPane.INFORMATION_MESSAGE);
                    this.coursController.listeDesCours(modelCours);
                }
            }
        }
    }
}