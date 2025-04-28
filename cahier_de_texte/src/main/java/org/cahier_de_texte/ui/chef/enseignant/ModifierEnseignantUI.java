package org.cahier_de_texte.ui.chef.enseignant;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ModifierEnseignantUI extends JFrame {
    DashBordChefUI dashHelper;

    JLabel labelFirstName , labelLastName , labelEmail ;
    JTextField inputFirstName , inputLastName , inputEmail;
    JButton btnModifier;

    public ModifierEnseignantUI(){
        this.dashHelper = new DashBordChefUI();
        initUI();
    }

    public void initUI(){
        FlatLightLaf.setup();

        add(formePanel());

        setTitle("Modifier un enseignant");
        setSize(350 , 500);
        setMinimumSize(new Dimension(350 , 500));
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
        l.setBorder(this.dashHelper.emptyBorder(10 , 0 , 0 , 0));
        formPanel.add(l);

        btnModifier = this.dashHelper.btnMenuSideBar("Modifier");
        btnModifier.setBackground(new Color(46, 204, 113));
        btnModifier.setForeground(Color.white);
        formPanel.add(btnModifier , "pushx , growx");

        return formPanel;
    }
}
