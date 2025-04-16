package org.cahier_de_texte.vue.chef.cours;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.dao.ClassesDAO;
import org.cahier_de_texte.dao.CoursDAO;
import org.cahier_de_texte.dao.UserEnseignantDAO;
import org.cahier_de_texte.vue.chef.DashBordChefView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AssignerCoursView extends JFrame {
    ClassesDAO classesDAO = new ClassesDAO();
    CoursDAO coursDAO = new CoursDAO();
    UserEnseignantDAO userEnseignantDAO = new UserEnseignantDAO();
    DashBordChefView dash = new DashBordChefView();
    JLabel labelEnseignant, labelCours, labelClasse ;
    JComboBox<String> comboBoxEnseignant , comboBoxCours , comboBoxClasse;
    JButton btnValider;

    public AssignerCoursView(){
        initUI();
    }

    public void initUI(){

        FlatLightLaf.setup();

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
        formPanel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

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
        userEnseignantDAO.Enseignants(comboBoxEnseignant);
        formPanel.add(comboBoxEnseignant , "pushx , growx");

        labelCours = new JLabel("Cours");
        labelCours.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelCours);

        comboBoxCours = new JComboBox<>();
        comboBoxCours.setPreferredSize(new Dimension(0 , 40));
        coursDAO.chargeCours(comboBoxCours);
        formPanel.add(comboBoxCours , "pushx , growx");

        labelClasse = new JLabel("Classe");
        labelClasse.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelClasse);

        comboBoxClasse = new JComboBox<>();
        comboBoxClasse.setPreferredSize(new Dimension(0 , 40));
        classesDAO.chargeClasse(comboBoxClasse);
        formPanel.add(comboBoxClasse , "pushx , growx");


        JLabel l = new JLabel("");
        l.setBorder(dash.emptyBorder(10 , 0 , 0 , 0));
        formPanel.add(l);

        btnValider = dash.btnMenuSideBar("Enregistrer");
        btnValider.setBackground(new Color(46, 204, 113));
        btnValider.setForeground(Color.white);
        formPanel.add(btnValider , "pushx , growx");

        return formPanel;
    }
}
