
package org.cahier_de_texte.ui.chef.seances;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.SeanceController;
import org.cahier_de_texte.ui.auth.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SeanceClasseUI extends JFrame implements ActionListener {
    private final DashBordChefUI dashHelper;
    private final SeanceController seanceController;
    private final String classe;
    private final String responsable;

    private JButton btnTabBord;
    private JButton btnSeanceNonValide;
    private JButton btnBack;
    private JButton btnDeconnexion;
    private JButton btnGenerePDF;
    JTable tabClasse;
    private DefaultTableModel tabClasseModel;

    public SeanceClasseUI(String classe , String responsable){
        this.seanceController = new SeanceController();
        this.dashHelper = new DashBordChefUI();
        this.classe = classe;
        this.responsable = responsable;
        initUI();
    }

    public void initUI(){
        FlatLightLaf.setup();
        add(createSideBarPanel() , BorderLayout.WEST);
        add(homePanelClasses(this.classe) , BorderLayout.CENTER);

        setTitle("Classe : " + this.classe);
        setSize(1200 , 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JPanel createSideBarPanel(){
        JPanel getPanelSideBar ,  sideBarPanel ;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xFFFFFFFF));
        sideBarPanel.setPreferredSize(new Dimension(250 , 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png")));
        Icon logo = new ImageIcon(image.getImage().getScaledInstance(100 , 100 , Image.SCALE_SMOOTH));

        JLabel labelUidt = new JLabel("UIDT");
        labelUidt.putClientProperty(FlatClientProperties.STYLE, "font: bold 25 Poppins");
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

        btnSeanceNonValide = this.dashHelper.btnMenuSideBar("Les seances non validés");
        btnSeanceNonValide.setIcon(FontIcon.of(FontAwesomeSolid.EXCLAMATION_CIRCLE , 18));
        btnSeanceNonValide.addActionListener(this);
        panelSideBar.add(btnSeanceNonValide , "wrap , pushx , growx");

        btnBack = this.dashHelper.btnMenuSideBar("Voir la liste des classes");
        btnBack.setIcon(FontIcon.of(FontAwesomeSolid.HAND_POINT_LEFT , 18));
        btnBack.addActionListener(this);
        panelSideBar.add(btnBack , "wrap , pushx , growx");

        return panelSideBar;
    }

    public JPanel homePanelClasses(String classe){
        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Seances validees par : ".toUpperCase() + this.responsable);
        labelGestionClasse.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.putClientProperty(FlatClientProperties.STYLE, "font: bold 23 Poppins");
        panel.add(labelGestionClasse , "pushx , growx");

        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesomeSolid.SIGN_OUT_ALT , 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        btnGenerePDF = this.dashHelper.btnMenuSideBar("Générer un PDF");
        btnGenerePDF.setIcon(FontIcon.of(FontAwesomeSolid.FILE_PDF , 18));
        btnGenerePDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerePDF.addActionListener(this);
        panel.add(btnGenerePDF , "wrap");

        String[] columnClasse = {"Date Séance" , "Code" , "Cours" , "Contenue" , "Duree" , "Enseignant"};

        tabClasseModel = new DefaultTableModel(columnClasse , 0){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabClasse = new JTable(tabClasseModel);
        tabClasse.setRowHeight(30);
        tabClasse.setGridColor(Color.LIGHT_GRAY);
        tabClasse.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(tabClasse);
        panel.add(scrollPane , "span , push , grow");

        this.seanceController.chargeListeSeancesClasse(tabClasseModel , classe);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnTabBord){ new DashBordChefUI().setVisible(true); dispose(); }
        if(e.getSource() == btnDeconnexion){ new LoginUI().setVisible(true); dispose();}
        if(e.getSource() == btnGenerePDF ){ genererPDF(); }
        if(e.getSource() == btnBack){ new GestionSeancesUI().setVisible(true); dispose(); }
        if(e.getSource() == btnSeanceNonValide){ new SeanceNonValideUI(this.classe , this.responsable).setVisible(true); dispose(); }
    }

    private void genererPDF() {
        Document document = new Document();

        try {
            preparerDocument(document);
            ajouterLogo(document);
            ajouterInformationsEntete(document);
            ajouterTableauSeances(document);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Une erreur est survenue lors de la génération du PDF");
        }
    }

    private void preparerDocument(Document document) throws FileNotFoundException, DocumentException {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String dateString = dateTime.format(formatter);
        String nomFichier = this.classe + "_" + dateString + ".pdf";

        PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
        document.open();
    }

    private void ajouterLogo(Document document) throws DocumentException, IOException {
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png")));
        com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(logoIcon.getImage(), null);
        logo.scaleAbsolute(80, 80);
        logo.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
        document.add(logo);
    }

    private void ajouterInformationsEntete(Document document) throws DocumentException {
        com.itextpdf.text.Font universiteFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph universite = new Paragraph("Université Iba Der Thiam de Thiès", universiteFont);
        universite.setAlignment(Element.ALIGN_CENTER);
        universite.setSpacingAfter(10f);
        document.add(universite);
    }

    private void ajouterTableauSeances(Document document) throws DocumentException {
        PdfPTable headerTable = getPdfPTable();
        document.add(headerTable);

        com.itextpdf.text.Font titreFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
        Paragraph titre = new Paragraph("Liste des Séances de la " + classe, titreFont);
        titre.setAlignment(Element.ALIGN_CENTER);
        titre.setSpacingBefore(10f);
        titre.setSpacingAfter(20f);
        document.add(titre);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(105);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        float[] columnWidths = {2.2f, 2f, 3.8f, 5f, 2f, 2.3f};
        table.setWidths(columnWidths);

        com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
        BaseColor headerColor = BaseColor.GRAY;

        String[] columnClasse = {"Date Séance", "Code", "Cours", "Contenu", "Durée", "Enseignant"};
        for (String entete : columnClasse) {
            PdfPCell cell = new PdfPCell(new Phrase(entete, fontHeader));
            cell.setBackgroundColor(headerColor);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(4);
            table.addCell(cell);
        }

        int rowCount = tabClasseModel.getRowCount();
        int columnCount = tabClasseModel.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                Object value = tabClasseModel.getValueAt(row, col);
                PdfPCell cell = new PdfPCell(new Phrase(value != null ? value.toString() : ""));
                cell.setPadding(3);
                table.addCell(cell);
            }
        }

        document.add(table);
        document.close();

        JOptionPane.showMessageDialog(this, "PDF généré avec succès !");
    }

    private PdfPTable getPdfPTable() throws DocumentException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(50);
        headerTable.setSpacingBefore(10f);
        headerTable.setSpacingAfter(10f);
        float[] headerWidths = {3f, 3f};
        headerTable.setWidths(headerWidths);

        com.itextpdf.text.Font infoFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);

        String[][] infos = {
                {"Département", "Informatique"},
                {"Classe", this.classe},
                {"Nom du Responsable", this.responsable},
                {"Année universitaire", "2024-2025"}
        };

        for (String[] row : infos) {
            PdfPCell labelCell = new PdfPCell(new Phrase(row[0], infoFont));
            labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            labelCell.setVerticalAlignment(Element.ALIGN_LEFT);
            labelCell.setPadding(5);

            PdfPCell valueCell = new PdfPCell(new Phrase(row[1], infoFont));
            valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            valueCell.setPadding(5);

            headerTable.addCell(labelCell);
            headerTable.addCell(valueCell);
        }
        return headerTable;
    }
}