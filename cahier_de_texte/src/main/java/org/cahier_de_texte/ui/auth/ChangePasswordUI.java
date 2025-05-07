package org.cahier_de_texte.ui.auth;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class ChangePasswordUI extends JFrame implements ActionListener {
    JLabel labelUser , labelPassword , textTitle;
    JTextField inputUser ;
    JPasswordField inputPassword;
    JButton btnConnect , btnClear;

    UserController userController;

    public ChangePasswordUI(){
        FlatLightLaf.setup();
        this.userController = new UserController();
        initUI();
    }

    public void initUI(){
        add(createPanel());

        setTitle("Connexion");
        setSize(400 , 550);
        setResizable(false);
        setMinimumSize(new Dimension(400 , 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JLabel connecter;
    public JPanel createPanel(){
        ImageIcon imageUser = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profile.png")));
        Icon resizeImageUser = new ImageIcon(imageUser.getImage().getScaledInstance(80 , 80 , Image.SCALE_SMOOTH));

        JPanel panelUser , panelPassword , mainPanelLogin;

        mainPanelLogin = new JPanel(new MigLayout());
        mainPanelLogin.setBorder(BorderFactory.createEmptyBorder(15 , 25 , 20 , 25));

        textTitle = new JLabel("Modifier mot de passe");
        textTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold 25 Poppins");
        textTitle.setIcon(resizeImageUser);
        textTitle.setHorizontalTextPosition(JLabel.CENTER);
        textTitle.setVerticalTextPosition(JLabel.BOTTOM);
        textTitle.setVerticalAlignment(JLabel.CENTER);
        textTitle.setHorizontalAlignment(JLabel.CENTER);
        textTitle.setIconTextGap(15);
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

        labelPassword = new JLabel("Nouveau mot de passe ");
        panelPassword.add(labelPassword);

        inputPassword = new JPasswordField();
        inputPassword.setPreferredSize(new Dimension(0 , 40));
        panelPassword.add(inputPassword);
        mainPanelLogin.add(panelPassword , "pushx , growx , wrap");

        JCheckBox Box = new JCheckBox("Afficher le mot de passe");
        Box.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Poppins");
        Box.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Box.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 10 , 0));
        mainPanelLogin.add(Box , "wrap , pushx , growx");
        inputPassword.setEchoChar('●');
        Box.setSelected(false);
        Box.addActionListener( ev->{
            if (Box.isSelected()){
                inputPassword.setEchoChar((char)0);
            }else{
                inputPassword.setEchoChar('●');
            }
        });

        connecter = new JLabel("Connecter avec un compte existant ?");
        connecter.setForeground(new Color(0x4d4429));
        connecter.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Poppins");
        connecter.setHorizontalTextPosition(JLabel.CENTER);
        connecter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        connecter.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 25 , 0));
        mainPanelLogin.add(connecter, "wrap , center");

        connecter.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { new LoginUI().setVisible(true); dispose();
            }
        });

        mainPanelLogin.add(panelButton() , "center , pushx , growx");

        return mainPanelLogin;
    }

    public JPanel panelButton(){
        JPanel panbtn = new JPanel(new GridLayout(1 , 2 , 10 , 10));
        btnConnect = myButton(0x4caf50 , "Confirmer");
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
        btn.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConnect) {
           confirm();
        }

        if (e.getSource() == btnClear) {
            inputUser.setText(null);
            inputPassword.setText(null);
        }
    }

    private void confirm(){
        String email = inputUser.getText();
        String password = new String(inputPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
        }else{
            if (this.userController.verifUser(email)){
                this.userController.updatePassword(email , password);
                inputUser.setText(null);
                inputPassword.setText(null);
            }else{
                JOptionPane.showMessageDialog(null, "L'email n'existe pas ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}