package org.cahier_de_texte.ui;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;
import org.cahier_de_texte.model.Users;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.cahier_de_texte.ui.enseignant.DashBordEnseignantUI;
import org.cahier_de_texte.ui.responsable.DashBordResponsableUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginUI extends JFrame implements ActionListener {
    JLabel labelUser , labelPassword , textTitle;
    JTextField inputUser ;
    JPasswordField inputPassword;
    JButton btnConnect , btnClear;

    UserController userController;

    public LoginUI(){
        FlatLightLaf.setup();
        this.userController = new UserController();
        initUI();
    }

    public void initUI(){
        add(createPanel());

        setTitle("Connexion");
        setSize(400 , 500);
        setResizable(false);
        setMinimumSize(new Dimension(350 , 450));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JLabel passForget;
    public JPanel createPanel(){
        ImageIcon imageUser = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profile.png")));
        Icon resizeImageUser = new ImageIcon(imageUser.getImage().getScaledInstance(80 , 80 , Image.SCALE_SMOOTH));

        JPanel panelUser , panelPassword , mainPanelLogin;

        mainPanelLogin = new JPanel(new MigLayout());
        mainPanelLogin.setBorder(BorderFactory.createEmptyBorder(15 , 25 , 20 , 25));

        textTitle = new JLabel("Connexion");
        textTitle.setFont(new Font("Roboto" , Font.BOLD , 23));
        textTitle.setIcon(resizeImageUser);
        textTitle.setHorizontalTextPosition(JLabel.CENTER);
        textTitle.setVerticalTextPosition(JLabel.BOTTOM);
        textTitle.setVerticalAlignment(JLabel.CENTER);
        textTitle.setHorizontalAlignment(JLabel.CENTER);
        mainPanelLogin.add(textTitle , "pushx , growx , wrap");

        //Panel de champs utilisateur et mot de passe
        panelUser = new JPanel(new GridLayout(2, 1));
        labelUser = new JLabel("Email");
        panelUser.add(labelUser);

        inputUser = new JTextField();
        inputUser.setPreferredSize(new Dimension(0 , 40));
        panelUser.add(inputUser);
        mainPanelLogin.add(panelUser , "pushx , growx , wrap");

        panelPassword = new JPanel(new GridLayout(2, 1));
        panelPassword.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 10 , 0));

        labelPassword = new JLabel("Mot de passe ");
        panelPassword.add(labelPassword);

        inputPassword = new JPasswordField();
        inputPassword.setPreferredSize(new Dimension(0 , 40));
        panelPassword.add(inputPassword);
        mainPanelLogin.add(panelPassword , "pushx , growx , wrap");

        passForget = new JLabel("Mot de passe oublier ?");
        passForget.setForeground(new Color(0x4d4429));
        passForget.setFont(new Font("" , Font.BOLD , 13));
        passForget.setHorizontalTextPosition(JLabel.CENTER);
        passForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        passForget.setBorder(BorderFactory.createEmptyBorder(10 , 3 , 15 , 0));
        mainPanelLogin.add(passForget , "wrap , center");

        mainPanelLogin.add(panelButton() , "center , pushx , growx");

        return mainPanelLogin;
    }

    public JPanel panelButton(){
        JPanel panbtn = new JPanel(new GridLayout(1 , 2 , 10 , 10));

        btnConnect = myButton(0x4caf50 , "Se connecter");
        btnConnect.addActionListener(this);
        panbtn.add(btnConnect);

        btnClear = myButton(0x8f5151 , "Annuler");
        btnClear.addActionListener(this);
        panbtn.add(btnClear);

        return panbtn;
    }

    public JButton myButton(int color , String title){
        JButton btn = new JButton(title);

        btn.setPreferredSize(new Dimension(0, 40));
        btn.setBackground(new Color(color));
        btn.setForeground(Color.white);
        btn.setFont(new Font("Roboto" , Font.PLAIN , 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == passForget){
            JOptionPane.showMessageDialog(
                    null,
                    "Modifier votre mot de passe"
            );
        }
        if (e.getSource() == btnConnect) {
            connect();
        }

        if (e.getSource() == btnClear) {
            inputUser.setText(null);
            inputPassword.setText(null);
        }
    }

    public void connect(){
        String email = inputUser.getText();
        String password = new String(inputPassword.getPassword());

        Users user = this.userController.login(email , password);
        String role = String.valueOf(user.getRole());

        switch (role) {
            case "CHEF" -> {
                new DashBordChefUI().setVisible(true);
                dispose();
            }
            case "ENSEIGNANT" -> {
                new DashBordEnseignantUI(user.getFirstName(), user.getLastName() , user.getId()
                ).setVisible(true);
                dispose();
            }
            case "RESPONSABLE" -> {
                new DashBordResponsableUI(user.getFirstName() + " " + user.getLastName() , user.getId()).setVisible(true);
                dispose();
            }
            default -> JOptionPane.showMessageDialog(
                    null,
                    "Une erreur est survenue ! ",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}