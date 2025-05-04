package org.cahier_de_texte.ui.chef.seances;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.SeanceController;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GestionSeancesUI extends JFrame implements ActionListener {
    private final DashBordChefUI dashHelper;
    private final SeanceController seanceController;

    private JButton btnTabBord;
    private JButton btnDeconnexion;
    private JButton btnPlusInfo;

    private JTable tabClasse;


    public GestionSeancesUI(){
        this.seanceController = new SeanceController();
        this.dashHelper = new DashBordChefUI();
        initUI();
    }

    public void initUI(){
        FlatLightLaf.setup();
        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelClasses() , BorderLayout.CENTER);

        setTitle("Gestion des séances");
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //Creation de notre Sidebar
    public  JPanel createSideBarPanel(){
        JPanel getPanelSideBar ,  sideBarPanel ;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xFFFFFFFF));
        sideBarPanel.setPreferredSize(new Dimension(250 , 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png")));
        Icon logo = new ImageIcon(image.getImage().getScaledInstance(100 , 100 , Image.SCALE_SMOOTH));

        JLabel labelUidt = new JLabel("UIDT");
        labelUidt.setFont(new Font("Arial",Font.BOLD , 25));
        labelUidt.setIcon(logo);
        labelUidt.setHorizontalTextPosition(JLabel.CENTER);
        labelUidt.setVerticalTextPosition(JLabel.BOTTOM);
        labelUidt.setHorizontalAlignment(JLabel.CENTER);

        getPanelSideBar = getPanelSidebar();

        sideBarPanel.add(labelUidt , "wrap , pushx , growx");
        sideBarPanel.add(getPanelSideBar , "pushx , growx");

        return sideBarPanel;
    }

    public JPanel getPanelSidebar(){
        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));
        btnTabBord = this.dashHelper.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesomeSolid.HOME , 18));
        btnTabBord.addActionListener(this);
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");
        return panelSideBar;
    }

    public JPanel homePanelClasses(){
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionSeances = new JLabel("Gestion des séances validées par classe");
        labelGestionSeances.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelGestionSeances.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionSeances , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT , 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        JButton btnListeClasses = this.dashHelper.btnMenuSideBar("Liste des Classes");
        btnListeClasses.setIcon(FontIcon.of(FontAwesomeSolid.LIST , 18));
        btnListeClasses.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnListeClasses, "split 2");

        btnPlusInfo = this.dashHelper.btnMenuSideBar("Plus informations");
        panel.add(btnPlusInfo , "wrap , split 2");
        btnPlusInfo.addActionListener(this);

        String[] columnClasse = {"Classe", "Responsable" , "Email Reponsable" , "Séances validées"};

        DefaultTableModel tabClasseModel = new DefaultTableModel(columnClasse, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabClasse = new JTable(tabClasseModel);
        tabClasse.setRowHeight(30);
        tabClasse.setFont(new Font("Roboto" , Font.BOLD , 13));
        tabClasse.setGridColor(Color.LIGHT_GRAY);
        tabClasse.setShowGrid(true);
        JScrollPane scrollPane = new JScrollPane(tabClasse);

        panel.add(scrollPane , "span , push , grow");

        this.seanceController.chargeTableClasseSeance(tabClasseModel);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnTabBord){ tableauBordAction(); }
        if(e.getSource() == btnPlusInfo){ plusInfoAction(); }
        if(e.getSource() == btnDeconnexion){ new LoginUI().setVisible(true); dispose(); }
    }

    private void tableauBordAction(){
        new DashBordChefUI().setVisible(true);
        dispose();
    }

    private void plusInfoAction(){
        int rowSelected = tabClasse.getSelectedRow();
        if(rowSelected == -1){
            JOptionPane.showMessageDialog(null , "Veuillez choisir une classe svp !", null, JOptionPane.WARNING_MESSAGE);
        }
        else{
            String classeName = (String) tabClasse.getValueAt(rowSelected , 0);
            String responsable = (String) tabClasse.getValueAt(rowSelected , 1);
            int option = JOptionPane.showConfirmDialog(null , "Voulez vous consulter la liste de : \n" + classeName , null , JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION){
                new SeanceClasseUI(classeName , responsable).setVisible(true);
                dispose();
            }
        }
    }
}