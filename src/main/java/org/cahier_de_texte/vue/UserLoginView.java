package org.cahier_de_texte.vue;

import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UserLoginView extends JFrame implements ActionListener {
    UserController userController = new UserController(this);

    JLabel labelUser , labelPassword , textTitle;
    JTextField inputUser ;
    JPasswordField inputPassword;
    JButton btnConnect , btnClear;

    public UserLoginView(){
        loginPage();
    }

    public void loginPage(){
        add(createPanel());

        setTitle("Connexion");
        setSize(400 , 500);
        setResizable(false);
        setMinimumSize(new Dimension(350 , 450));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel createPanel(){

        ImageIcon imageUser = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profile.png")));
        Icon resizeImageUser = new ImageIcon(imageUser.getImage().getScaledInstance(80 , 80 , Image.SCALE_SMOOTH));

        JPanel panelUser , panelPassword , mainPanelLogin;

        //Panel pricipale
        mainPanelLogin = new JPanel(new MigLayout());
        mainPanelLogin.setBorder(BorderFactory.createEmptyBorder(15 , 25 , 20 , 25));

        //Titre du fenetre
        textTitle = new JLabel("Connexion");
        textTitle.setFont(new Font("Arial" , Font.BOLD , 23));
        textTitle.setIcon(resizeImageUser);
        textTitle.setHorizontalTextPosition(JLabel.CENTER);
        textTitle.setVerticalTextPosition(JLabel.BOTTOM);
        textTitle.setVerticalAlignment(JLabel.CENTER);
        textTitle.setHorizontalAlignment(JLabel.CENTER);

        //Panel de champs utilisateur et mot de passe
        panelUser = new JPanel(new GridLayout(2, 1));

        labelUser = new JLabel("Email");
        panelUser.add(labelUser);

        inputUser = new JTextField();
        inputUser.setPreferredSize(new Dimension(0 , 40));
        panelUser.add(inputUser);

        panelPassword = new JPanel(new GridLayout(2, 1));
        panelPassword.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 10 , 0));

        labelPassword = new JLabel("Mot de passe ");
        panelPassword.add(labelPassword);

        inputPassword = new JPasswordField();
        inputPassword.setPreferredSize(new Dimension(0 , 40));
        panelPassword.add(inputPassword);

        JLabel passForget = new JLabel("Mot de passe oublier ?");
        passForget.setForeground(new Color(0x4d4429));
        passForget.setFont(new Font("" , Font.BOLD , 13));
        passForget.setHorizontalTextPosition(JLabel.CENTER);
        passForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        passForget.setBorder(BorderFactory.createEmptyBorder(0 , 3 , 10 , 0));

        btnConnect = myButton(0x4caf50 , "Se connecter");
        btnConnect.addActionListener(this);

        btnClear = myButton(0x8f5151 , "Annuler");
        btnClear.addActionListener(this);

        mainPanelLogin.add(textTitle , "pushx , growx , wrap");
        mainPanelLogin.add(panelUser , "pushx , growx , wrap");
        mainPanelLogin.add(panelPassword , "pushx , growx , wrap");
        mainPanelLogin.add(passForget , "pushx , growx , wrap");
        mainPanelLogin.add(btnConnect , "split2");
        mainPanelLogin.add(btnClear , "pushx , growx");

        return mainPanelLogin;
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

        if (e.getSource() == btnConnect) {
            String userEmail = inputUser.getText();
            char[] userPass = inputPassword.getPassword();
            String pass = new String(userPass);
            userController.login(userEmail , pass);
        }

        if (e.getSource() == btnClear) {
            inputUser.setText("");
            inputPassword.setText("");
        }
    }
}
