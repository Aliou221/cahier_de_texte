package org.cahier_de_texte.ui.chef.classe.responsable;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ResponsableUI extends JFrame {
    DashBordChefUI dashHelper;
    private final String classe;
    JLabel labelFirstName , labelLastName , labelEmail , labelPassword ;
    public JTextField inputFirstName;
    public JTextField inputLastName;
    public JTextField inputEmail;
    public JPasswordField inputPassword;
    public JButton btnValider;


    public ResponsableUI(String classe){
        this.dashHelper = new DashBordChefUI();
        this.classe = classe;
        initUI();
    }

    public void initUI(){
        add(formePanel());

        setTitle(this.classe + " : Définir comme reponsable");
        setSize(350 , 550);
        setMinimumSize(new Dimension(350 , 550));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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
        labelFirstName.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Roboto");
        formPanel.add(labelFirstName);

        inputFirstName = new JTextField();
        inputFirstName.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputFirstName , "pushx , growx");

        labelLastName = new JLabel("Nom");
        labelLastName.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Roboto");
        formPanel.add(labelLastName);

        inputLastName = new JTextField();
        inputLastName.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputLastName , "pushx , growx");

        labelEmail = new JLabel("Email");
        labelEmail.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Roboto");
        formPanel.add(labelEmail);

        inputEmail = new JTextField();
        inputEmail.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputEmail , "pushx , growx");

        labelPassword = new JLabel("Mot de passe");
        labelPassword.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Roboto");
        formPanel.add(labelPassword);

        inputPassword = new JPasswordField();
        inputPassword.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputPassword , "pushx , growx");

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