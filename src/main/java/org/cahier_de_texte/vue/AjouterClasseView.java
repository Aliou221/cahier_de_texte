package org.cahier_de_texte.vue;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class AjouterClasseView extends JFrame {
    DashBordChefView dash = new DashBordChefView();

    public AjouterClasseView(){
        initUI();
    }

    public void initUI(){
        add(createPanel());
        setTitle("Ajouter une classes");

        setSize(300 , 300);
        setMinimumSize(new Dimension(300 , 300));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(90 , 90);
    }

    JLabel labelNiveau;
    JTextField inputNiveau;
    JButton btnValider;

    public JPanel createPanel(){

        JPanel formPanel = new JPanel(new MigLayout("wrap 1 , gap 8"));
        formPanel.setBorder(dash.emptyBorder(20 , 20 , 20 , 20));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profil.png")));
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(90 , 90 , Image.SCALE_SMOOTH));

        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setHorizontalAlignment(JLabel.CENTER);

        formPanel.add(label , "span , wrap , pushx , growx");

        labelNiveau = new JLabel("Nom de la classe");
        labelNiveau.setFont(new Font("Roboto", Font.PLAIN , 15));

        inputNiveau = new JTextField();
        inputNiveau.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(labelNiveau);
        formPanel.add(inputNiveau , "pushx , growx");

        JLabel l = new JLabel("");
        l.setBorder(dash.emptyBorder(10 , 0 , 0 , 0));
        formPanel.add(l);

        btnValider = dash.btnMenuSideBar("Valider" , "");
        btnValider.setBackground(new Color(46, 204, 113));
        btnValider.setForeground(Color.white);
        formPanel.add(btnValider , "pushx , growx");

        return formPanel;
    }

}
