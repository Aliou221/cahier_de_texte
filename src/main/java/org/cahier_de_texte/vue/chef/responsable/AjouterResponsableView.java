package org.cahier_de_texte.vue.chef.responsable;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.model.Etudiants;
import org.cahier_de_texte.vue.chef.DashBordChefView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AjouterResponsableView extends JFrame{
    DashBordChefView dash = new DashBordChefView();
    JLabel labelFirstName , labelLastName , labelEmail , labelPassword ;
    JTextField inputFirstName , inputLastName , inputEmail;
    JPasswordField inputPassword;
    JButton btnValider;

    public AjouterResponsableView(){
        initUI();
    }

    public void initUI(){
        FlatLightLaf.setup();

        add(formePanel());

        setTitle("Ajouter un Responsable de classe");
        setSize(400 , 550);
        setMinimumSize(new Dimension(400 , 550));
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

        labelFirstName = new JLabel("Prénom");
        labelFirstName.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelFirstName);

        inputFirstName = new JTextField();
        inputFirstName.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputFirstName , "pushx , growx");

        labelLastName = new JLabel("Nom");
        labelLastName.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelLastName);

        inputLastName = new JTextField();
        inputLastName.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputLastName , "pushx , growx");

        labelEmail = new JLabel("Email");
        labelEmail.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelEmail);

        inputEmail = new JTextField();
        inputEmail.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputEmail , "pushx , growx");

        labelPassword = new JLabel("Mot de passe");
        labelPassword.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelPassword);

        inputPassword = new JPasswordField();
        inputPassword.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputPassword , "pushx , growx");

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
