package org.cahier_de_texte.ui.chef.cours;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ModifierCoursUI extends JFrame {

    DashBordChefUI dashHelper;
    JLabel labelCode , labelNomCours , labelCredit;
    JTextField inputCode , inputNomCours , inputCredit;
    JButton btnValider;

    public  ModifierCoursUI(){
        this.dashHelper  = new DashBordChefUI();
        initUI();
    }

    public void initUI(){
        add(formePanel());

        setTitle("Modifier un cours");
        setSize(350 , 450);
        setMinimumSize(new Dimension(350 , 450));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel formePanel(){

        JPanel formPanel = new JPanel(new MigLayout("wrap 1 , gap 8"));
        formPanel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/cours-1.png")));
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(90 , 90 , Image.SCALE_SMOOTH));

        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        formPanel.add(label , "span , wrap , pushx , growx");

        labelCode = new JLabel("Code");
        labelCode.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelCode);

        inputCode = new JTextField();
        inputCode.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputCode , "pushx , growx");

        labelNomCours = new JLabel("Intitulé");
        labelNomCours.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelNomCours);

        inputNomCours = new JTextField();
        inputNomCours.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputNomCours , "pushx , growx");

        labelCredit = new JLabel("Crédits");
        labelCredit.setFont(new Font("Roboto", Font.PLAIN , 15));
        formPanel.add(labelCredit);

        inputCredit = new JTextField();
        inputCredit.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputCredit , "pushx , growx");

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