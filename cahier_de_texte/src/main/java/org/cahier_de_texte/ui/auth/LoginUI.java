package org.cahier_de_texte.ui.auth;

import com.formdev.flatlaf.FlatClientProperties;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        setSize(400 , 550);
        setResizable(false);
        setMinimumSize(new Dimension(400 , 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JLabel passForget;
    public JPanel createPanel(){
        ImageIcon imageUser = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/profile.png")));
        Icon resizeImageUser = new ImageIcon(imageUser.getImage().getScaledInstance(80 , 80 , Image.SCALE_SMOOTH));

        JPanel panelUser , panelPassword , mainPanelLogin;

        mainPanelLogin = new JPanel(new MigLayout());
        mainPanelLogin.setBorder(BorderFactory.createEmptyBorder(15 , 25 , 0 , 25));

        textTitle = new JLabel("Connexion");
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
        labelUser.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Poppins");
        panelUser.add(labelUser);

        inputUser = new JTextField();
        inputUser.setPreferredSize(new Dimension(0 , 40));
        panelUser.add(inputUser);
        mainPanelLogin.add(panelUser , "pushx , growx , wrap");

        panelPassword = new JPanel(new GridLayout(2, 1));

        labelPassword = new JLabel("Mot de passe ");
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

        passForget = new JLabel("Mot de passe oublier ?");
        passForget.setForeground(new Color(0x4d4429));
        passForget.putClientProperty(FlatClientProperties.STYLE, "font: plain 15 Poppins");
        passForget.setHorizontalTextPosition(JLabel.CENTER);
        passForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        passForget.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 25 , 0));
        mainPanelLogin.add(passForget , "wrap , center");

        passForget.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChangePasswordUI().setVisible(true);
                dispose();
            }
        });

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
        btn.putClientProperty(FlatClientProperties.STYLE, "font: plain 16 Poppins");
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConnect) { connect(); }
        if (e.getSource() == btnClear) {
            inputUser.setText(null);
            inputPassword.setText(null);
        }
    }

    private void connect(){
        String email = inputUser.getText();
        String password = new String(inputPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(null , "Veuillez remplir tous les champs svp !" , null , JOptionPane.WARNING_MESSAGE);
        }else{
            Users user = this.userController.login(email , password);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect !", null, JOptionPane.ERROR_MESSAGE);
                return;
            }

            String role = String.valueOf(user.getRole());
            switch (role) {
                case "CHEF" -> { new DashBordChefUI().setVisible(true); dispose(); }
                case "ENSEIGNANT" -> { new DashBordEnseignantUI(user.getFirstName(), user.getLastName() , user.getId()).setVisible(true);dispose();}
                case "RESPONSABLE" -> {new DashBordResponsableUI(user.getFirstName() + " " + user.getLastName() , user.getId()).setVisible(true);dispose();}
                default -> {System.out.println("Role non reconnue");}
            }

        }
    }
}