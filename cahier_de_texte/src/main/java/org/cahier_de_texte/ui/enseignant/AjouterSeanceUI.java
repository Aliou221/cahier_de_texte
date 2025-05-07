package org.cahier_de_texte.ui.enseignant;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.ui.chef.DashBordChefUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AjouterSeanceUI extends JFrame {
    DashBordChefUI dashHelper ;
    private final String classe ;

    JLabel labelContenue, labelDate  , labelDuree;
    JTextArea inputContenue;
    JTextField inputDate, inputDuree;
    JButton btnAjouter;

    public AjouterSeanceUI(String classe){
        this.dashHelper = new DashBordChefUI();
        this.classe = classe;
        initUI();
    }

    public void initUI(){
        add(formePanel());

        setTitle(this.classe + " : Ajouter une séance");
        setSize(400 , 550);
        setMinimumSize(new Dimension(400 , 550));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel formePanel(){
        JPanel formPanel = new JPanel(new MigLayout("wrap 1 , gap 10"));
        formPanel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/cours.png")));
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(90 , 90 , Image.SCALE_SMOOTH));

        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setHorizontalAlignment(JLabel.CENTER);
        formPanel.add(label , "span , wrap , pushx , growx");

        labelContenue = new JLabel("Contenue de la séance : ");
        labelContenue.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        formPanel.add(labelContenue);

        inputContenue = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(inputContenue);
        scrollPane.setPreferredSize(new Dimension(500 , 130));
        formPanel.add(scrollPane , "pushx , growx");

        labelDate = new JLabel("Date : (ex: aaaa-MM-dd HH:mm:ss)");
        labelDate.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        formPanel.add(labelDate);

        inputDate = new JTextField();
        inputDate.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputDate, "pushx , growx");

        labelDuree = new JLabel("Durée (en Heures ) :");
        labelDuree.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        formPanel.add(labelDuree);

        inputDuree = new JTextField();
        inputDuree.setPreferredSize(new Dimension(0 , 40));
        formPanel.add(inputDuree, "pushx , growx");

        JLabel l = new JLabel("");
        l.setBorder(this.dashHelper.emptyBorder(10 , 0 , 0 , 0));
        formPanel.add(l);

        btnAjouter = this.dashHelper.btnMenuSideBar("Ajouter");
        btnAjouter.setBackground(new Color(46, 204, 113));
        btnAjouter.setForeground(Color.white);
        formPanel.add(btnAjouter , "pushx , growx");

        return formPanel;
    }
}