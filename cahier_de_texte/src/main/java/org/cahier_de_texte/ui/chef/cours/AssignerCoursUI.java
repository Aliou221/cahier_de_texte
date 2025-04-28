package org.cahier_de_texte.ui.chef.cours;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.ChefController;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AssignerCoursUI extends JFrame {
    DashBordChefUI dashHelper;
    ChefController chefController ;

    JLabel labelEnseignant, labelCours, labelClasse ;
    JComboBox<String> comboBoxEnseignant , comboBoxCours , comboBoxClasse;
    JButton btnValider;

    public AssignerCoursUI(){
        this.chefController = new ChefController();
        this.dashHelper = new DashBordChefUI();
        initUI();
    }

    public void initUI(){
        add(formePanel());

        setTitle("Attribution des cours");
        setSize(450 , 500);
        setMinimumSize(new Dimension(450 , 500));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(90 , 90);
    }

    public JPanel formePanel(){
        JPanel formPanel = new JPanel(new MigLayout("wrap 1 , gap 8"));
        formPanel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profil.png")));
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(90 , 90 , Image.SCALE_SMOOTH));

        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        formPanel.add(label , "span , wrap , pushx , growx");

        labelEnseignant = new JLabel("Enseignant");
        labelEnseignant.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelEnseignant);

        comboBoxEnseignant = new JComboBox<>();
        comboBoxEnseignant.setPreferredSize(new Dimension(0 , 40));
        this.chefController.enseignant(comboBoxEnseignant);
        formPanel.add(comboBoxEnseignant , "pushx , growx");

        labelCours = new JLabel("Cours");
        labelCours.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelCours);

        comboBoxCours = new JComboBox<>();
        comboBoxCours.setPreferredSize(new Dimension(0 , 40));
        this.chefController.cours(comboBoxCours);
        formPanel.add(comboBoxCours , "pushx , growx");

        labelClasse = new JLabel("Classe");
        labelClasse.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelClasse);

        comboBoxClasse = new JComboBox<>();
        comboBoxClasse.setPreferredSize(new Dimension(0 , 40));
        this.chefController.classe(comboBoxClasse);
        formPanel.add(comboBoxClasse , "pushx , growx");

        JLabel l = new JLabel("");
        l.setBorder(this.dashHelper.emptyBorder(10 , 0 , 0 , 0));
        formPanel.add(l);

        btnValider = this.dashHelper.btnMenuSideBar("Enregistrer");
        btnValider.setBackground(new Color(46, 204, 113));
        btnValider.setForeground(Color.white);
        formPanel.add(btnValider , "pushx , growx");

        return formPanel;
    }
}
