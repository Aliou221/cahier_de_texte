package org.cahier_de_texte.ui.chef.seances;

import com.formdev.flatlaf.FlatLightLaf;
import com.itextpdf.text.*;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.miginfocom.swing.MigLayout;
import org.cahier_de_texte.controller.chef.SeanceController;
import org.cahier_de_texte.ui.LoginUI;
import org.cahier_de_texte.ui.chef.DashBordChefUI;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SeanceClasseUI extends JFrame implements ActionListener {
    DashBordChefUI dashHelper;
    SeanceController seanceController;
    private final String classe;
    private final String responsable;

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

    //Creation de notre Sidebar
    public  JPanel createSideBarPanel(){

        JPanel getPanelSideBar ,  sideBarPanel ;

        sideBarPanel = new JPanel(new MigLayout("gap 8"));
        sideBarPanel.setBackground(new Color(0xFFFFFFFF));
        sideBarPanel.setPreferredSize(new Dimension(250 , 0));

        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png")));
        Icon logo = new ImageIcon(image.getImage().getScaledInstance(100 , 100 , Image.SCALE_SMOOTH));

        JLabel labelUidt = new JLabel("UIDT");
        labelUidt.setFont(new Font("Roboto",Font.BOLD , 25));
        labelUidt.setIcon(logo);
        labelUidt.setHorizontalTextPosition(JLabel.CENTER);
        labelUidt.setVerticalTextPosition(JLabel.BOTTOM);
        labelUidt.setHorizontalAlignment(JLabel.CENTER);

        getPanelSideBar = getPanelSidebar();

        sideBarPanel.add(labelUidt , "wrap , pushx , growx");
        sideBarPanel.add(getPanelSideBar , "pushx , growx");

        return sideBarPanel;
    }

    JButton btnTabBord , btnBack;

    public JPanel getPanelSidebar(){

        JPanel panelSideBar = new JPanel(new MigLayout());
        panelSideBar.setBackground(Color.getColor(null));

        btnTabBord = this.dashHelper.btnMenuSideBar("Tableau de bord");
        btnTabBord.setIcon(FontIcon.of(FontAwesome.HOME , 18));
        btnTabBord.addActionListener(this);
        panelSideBar.add(btnTabBord , "wrap , pushx , growx");

        btnBack = this.dashHelper.btnMenuSideBar("Voir la liste des classes");
        btnBack.setIcon(FontIcon.of(FontAwesome.HAND_O_LEFT , 18));
        btnBack.addActionListener(this);
        panelSideBar.add(btnBack , "wrap , pushx , growx");

        return panelSideBar;
    }

    JButton btnDeconnexion , btnGenerePDF;
    JTable tabClasse;
    DefaultTableModel tabClasseModel;

    public JPanel homePanelClasses(String classe){

        JPanel panel = new JPanel(new MigLayout());
        panel.setBorder(this.dashHelper.emptyBorder(20 , 20 , 20 , 20));

        JLabel labelGestionClasse = new JLabel("Gestion des séances");
        labelGestionClasse.setBorder(this.dashHelper.emptyBorder(10 , 0 ,15 , 0));
        labelGestionClasse.setFont(new Font("Roboto" , Font.BOLD , 23));
        panel.add(labelGestionClasse , "pushx , growx");


        btnDeconnexion = this.dashHelper.btnMenuSideBar("Deconnexion");
        btnDeconnexion.setIcon(FontIcon.of(FontAwesome.SIGN_OUT , 18));
        btnDeconnexion.addActionListener(this);
        panel.add(btnDeconnexion ,"wrap , split 2");

        JButton btnListeSeance = this.dashHelper.btnMenuSideBar("Liste des séances validées");
        btnListeSeance.setIcon(FontIcon.of(FontAwesome.LIST , 18));
        btnListeSeance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnListeSeance , "split 2 , wrap");

        btnGenerePDF = this.dashHelper.btnMenuSideBar("Générer un PDF");
        btnGenerePDF.setIcon(FontIcon.of(FontAwesome.FILE_PDF_O , 18));
        btnGenerePDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerePDF.addActionListener(this);
        panel.add(btnGenerePDF , "split 2 , wrap");

        String[] columnClasse = {"Date Séance" , "Code" , "Cours" , "Contenue" , "Duree" , "Enseignant"};

        tabClasseModel = new DefaultTableModel(columnClasse , 0){
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
        
        this.seanceController.chargeListeSeancesClasse(tabClasseModel , classe);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTabBord){
            new DashBordChefUI().setVisible(true);
            dispose();
        }

        if(e.getSource() == btnDeconnexion){
            new LoginUI().setVisible(true);
            dispose();
        }

        if (e.getSource() == btnGenerePDF ){
            genererPDF();
        }

        if(e.getSource() == btnBack){
            new GestionSeancesUI().setVisible(true);
            dispose();
        }
    }

    private void genererPDF() {
        Document document = new Document(); // Crée un nouveau document PDF vide

        try {
            // Préparer le nom du fichier (nom de la classe + date et heure précises)
            LocalDateTime dateTime = LocalDateTime.now(); // Récupère la date et l'heure actuelles
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"); // Définit le format
            String dateString = dateTime.format(formatter); // Formate la date et l'heure
            String nomFichier = this.classe + "_" + dateString + ".pdf"; // Nom du fichier

            // Préparer l'écriture du fichier PDF
            PdfWriter.getInstance(document, new FileOutputStream(nomFichier)); // Crée le PDF sur disque
            document.open(); // Ouvre le document pour écrire dedans

            // Ajouter le logo centré en haut
            ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/logo-uidt.png"))); // Charge le logo
            com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance(logoIcon.getImage(), null); // Convertit en Image PDF
            logo.scaleAbsolute(80, 80); // Redimensionne l'image à 80x80 pixels
            logo.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER); // Centre le logo
            document.add(logo); // Ajoute le logo au document

            //Ajouter le texte "Université Iba Der Thiam de Thiès" sous le logo
            com.itextpdf.text.Font universiteFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, Font.BOLD); // Définit une police pour le texte
            Paragraph universite = new Paragraph("Université Iba Der Thiam de Thiès", universiteFont); // Crée le paragraphe
            universite.setAlignment(Element.ALIGN_CENTER); // Centre le texte
            universite.setSpacingAfter(10f); // Ajoute un espace après le texte
            document.add(universite); // Ajoute au document

            //Créer un tableau pour l'infos
            PdfPTable headerTable = new PdfPTable(1); // Crée un tableau
            headerTable.setWidthPercentage(100); // Le tableau prend toute la largeur du document
            headerTable.setSpacingBefore(10f); // Espace avant le tableau
            headerTable.setSpacingAfter(10f); // Espace après le tableau
            float[] headerWidths = {4f}; // Largeur du colonne
            headerTable.setWidths(headerWidths); // Applique les largeurs

            // Ajouter les informations à droite
            com.itextpdf.text.Font infoFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);

            Paragraph infos = new Paragraph(); // Nouveau paragraphe pour les informations
            infos.add(new Phrase("Département Informatique\n", infoFont)); // Ajoute "Département Informatique"
            infos.add(new Phrase(this.classe + "\n", infoFont));
            infos.add(new Phrase("Nom du Responsable : " + this.responsable + "\n", infoFont)); // Ajoute nom du responsable
            infos.add(new Phrase("Année universitaire : 2024-2025\n", infoFont)); // Ajoute année universitaire

            PdfPCell infosCell = new PdfPCell(infos); // Crée une cellule avec le texte
            infosCell.setBorder(Rectangle.NO_BORDER); // Pas de bordure
            infosCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Aligne à gauche
            infosCell.setVerticalAlignment(Element.ALIGN_MIDDLE); // Centre verticalement
            headerTable.addCell(infosCell); // Ajoute la cellule d'infos au tableau

            document.add(headerTable); // Ajoute le tableau au document

            //Ajouter le titre principal du tableau
            com.itextpdf.text.Font titreFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY); // Police pour le titre
            Paragraph titre = new Paragraph("Liste des Séances de " + classe, titreFont); // Texte avec nom de la classe
            titre.setAlignment(Element.ALIGN_CENTER); // Centre le titre
            titre.setSpacingBefore(10f); // Espace avant
            titre.setSpacingAfter(20f); // Espace après
            document.add(titre); // Ajoute le titre au document

            //Créer le tableau pour afficher la liste des séances
            PdfPTable table = new PdfPTable(6); // Crée un tableau à 6 colonnes
            table.setWidthPercentage(100); // Largeur totale
            table.setSpacingBefore(10f); // Espace avant
            table.setSpacingAfter(10f); // Espace après
            float[] columnWidths = {2f, 2f, 3f, 4f, 1f, 2f}; // Largeur de chaque colonne
            table.setWidths(columnWidths); // Applique la largeur

            // Définir le style de l'entête (en-tête des colonnes)
            com.itextpdf.text.Font fontHeader = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.WHITE); // Police blanche
            BaseColor headerColor = BaseColor.GRAY; // Couleur de fond grise

            String[] columnClasse = {"Date Séance", "Code", "Cours", "Contenu", "Durée", "Enseignant"}; // Titres des colonnes
            for (String entete : columnClasse) {
                PdfPCell cell = new PdfPCell(new Phrase(entete, fontHeader)); // Crée une cellule pour chaque titre
                cell.setBackgroundColor(headerColor); // Fond gris
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Centre le texte
                cell.setPadding(4); // Marge intérieure
                table.addCell(cell); // Ajoute la cellule au tableau
            }

            // Remplir le tableau avec les données de tabClasseModel
            int rowCount = tabClasseModel.getRowCount(); // Nombre de lignes dans le modèle
            int columnCount = tabClasseModel.getColumnCount(); // Nombre de colonnes

            for (int row = 0; row < rowCount; row++) { // Parcourt chaque ligne
                for (int col = 0; col < columnCount; col++) { // Parcourt chaque colonne
                    Object value = tabClasseModel.getValueAt(row, col); // Récupère la valeur de la cellule
                    PdfPCell cell = new PdfPCell(new Phrase(value != null ? value.toString() : "")); // Met la valeur (ou vide si null)
                    cell.setPadding(3); // Marge intérieure
                    table.addCell(cell); // Ajoute la cellule au tableau
                }
            }

            document.add(table); // Ajoute le tableau rempli au document
            document.close(); // Ferme le document (finalise le PDF)

            JOptionPane.showMessageDialog(this, "PDF généré avec succès !"); // Message de succès

        } catch (Exception ex) {
           System.out.println("Erreur ! " + ex.getMessage());// Affiche l'erreur dans la console
            JOptionPane.showMessageDialog(this, "Erreur lors de la génération du PDF !"); // Message d'erreur
        }
    }



}