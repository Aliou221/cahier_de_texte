package org.cahier_de_texte.vue.chef.classe;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.vue.chef.DashBordChefView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AjouterEtudiantView extends JFrame {
    DashBordChefView dash = new DashBordChefView();

    JLabel labelFirstName , labelLastName , labelEmail ;
    JTextField inputFirstName , inputLastName , inputEmail;
    JButton btnAjouter;

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    private String classe ;

    public AjouterEtudiantView(String classe){
        this.classe = classe;
        initUI();
    }


    public void initUI(){
        FlatLightLaf.setup();

        add(formePanel());

        setTitle(this.classe + " : Ajouter un étudiant");
        setSize(400 , 500);
        setMinimumSize(new Dimension(400 , 500));
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

        JLabel l = new JLabel("");
        l.setBorder(dash.emptyBorder(10 , 0 , 0 , 0));
        formPanel.add(l);

        btnAjouter = dash.btnMenuSideBar("Ajouter");
        btnAjouter.setBackground(new Color(46, 204, 113));
        btnAjouter.setForeground(Color.white);
        formPanel.add(btnAjouter , "pushx , growx");

        return formPanel;
    }

}

