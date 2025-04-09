-- Création de la base de données
CREATE DATABASE IF NOT EXISTS cahier_de_texte;
USE cahier_de_texte;

-- Table Utilisateurs
CREATE TABLE IF NOT EXISTS Utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(30) NOT NULL,
    nom VARCHAR(20) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM('Chef', 'Enseignant', 'Responsable') NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Responsables (1 par classe)
INSERT INTO Utilisateurs (prenom, nom, email, password, role)
VALUES
('Samba', 'Ndiaye', 'samba.ndiaye@univ-thies.sn', 'pass1', 'Responsable'),
('Aminata', 'Ba', 'aminata.ba@univ-thies.sn', 'pass2', 'Responsable'),
('Cheikh', 'Diop', 'cheikh.diop@univ-thies.sn', 'pass3', 'Responsable');

-- Enseignants
INSERT INTO Utilisateurs (prenom, nom, email, password, role)
VALUES
('Fatou', 'Sow', 'fatou.sow@univ-thies.sn', 'ens1', 'Enseignant'),
('Ibrahima', 'Fall', 'ibrahima.fall@univ-thies.sn', 'ens2', 'Enseignant'),
('Mariama', 'Sagna', 'mariama.sagna@univ-thies.sn', 'ens3', 'Enseignant');


-- Un chef
INSERT INTO Utilisateurs (prenom, nom, email, password, role)
VALUES ('Aliou', 'Cissé', 'aliou.cisse@univ-thies.sn', 'pass789', 'Chef');

-------------------------------------------------------------------------------------------------------

-- Table enseignants
CREATE TABLE IF NOT EXISTS Enseignants(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_utilisateur INT NOT NULL,

    FOREIGN KEY (id_utilisateur) REFERENCES Utilisateurs(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Enseignants : ID utilisateurs 4, 5, 6
INSERT INTO Enseignants (id_utilisateur) VALUES (4), (5), (6);


-------------------------------------------------------------------------------------------------------

-- Table responsable
CREATE TABLE IF NOT EXISTS Responsables(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_utilisateur INT NOT NULL ,

    FOREIGN KEY(id_utilisateur) REFERENCES Utilisateurs(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
-- Responsables : ID utilisateurs 1, 2, 3
INSERT INTO Responsables (id_utilisateur) VALUES (1), (2), (3);


-------------------------------------------------------------------------------------------------------



-- Table Classes
CREATE TABLE IF NOT EXISTS Classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    id_responsable INT NOT NULL ,

    FOREIGN KEY (id_responsable) REFERENCES  Responsables(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

INSERT INTO Classes (nom, id_responsable)
VALUES
('L1 Informatique', 1),  -- Responsable ID 1
('L2 Informatique', 2),  -- Responsable ID 2
('L3 Informatique', 3);  -- Responsable ID 3


-------------------------------------------------------------------------------------------------------


-- Table Etudiants
CREATE TABLE IF NOT EXISTS Etudiants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(100) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    classe_id INT NOT NULL,
    FOREIGN KEY (classe_id) REFERENCES Classes(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- Étudiants L1 (classe_id = 1)
INSERT INTO Etudiants (prenom, nom, email, classe_id)
VALUES
('Aliou', 'Mbaye','aliou.mbaye@univ-thies.sn', 1),
('Fatou', 'Diagne', 'fatou.diagne@univ-thies.sn', 1),
('Modou', 'Gueye', 'modou.gueye@univ-thies.sn', 1),
('Aissatou', 'Kane', 'aissatou.kane@univ-thies.sn', 1),
('Moussa', 'Sow', 'moussa.sow@univ-thies.sn', 1)
('Samba', 'Ndiaye', 'samba.ndiaye@univ-thies.sn', 1);


-- Étudiants L2 (classe_id = 2)
INSERT INTO Etudiants (prenom, nom, email, classe_id)
VALUES
('Khadija', 'Thiam', 'khadija.thiam@univ-thies.sn', 2),
('Cheikh', 'Ba', 'cheikh.ba@univ-thies.sn', 2),
('Abdou', 'Fall', 'abdou.fall@univ-thies.sn', 2),
('Seynabou', 'Diop', 'seynabou.diop@univ-thies.sn', 2),
('Mamadou', 'Sy', 'mamadou.sy@univ-thies.sn', 2),
('Aminata', 'Ba', 'aminata.ba@univ-thies.sn', 2);


-- Étudiants L3 (classe_id = 3)
INSERT INTO Etudiants (prenom, nom, email, classe_id)
VALUES
('Ousmane', 'Ndoye', 'ousmane.ndoye@univ-thies.sn', 3),
('Fatimata', 'Diallo', 'fatimata.diallo@univ-thies.sn', 3),
('Ibra', 'Sarr', 'ibra.sarr@univ-thies.sn', 3),
('Mame', 'Ndiaye', 'mame.ndiaye@univ-thies.sn', 3),
('Binta', 'Gueye', 'binta.gueye@univ-thies.sn', 3),
('Cheikh', 'Diop', 'cheikh.diop@univ-thies.sn', 3);


-------------------------------------------------------------------------------------------------------



-- Table Cours
CREATE TABLE IF NOT EXISTS Cours (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    intitule VARCHAR(200) NOT NULL,
    credits INT NOT NULL,
    enseignant_id INT NOT NULL ,
    FOREIGN KEY (enseignant_id) REFERENCES Enseignants(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

INSERT INTO Cours (code, intitule, credits, enseignant_id)
VALUES
('INF101', 'Algorithmique', 6, 1),
('INF202', 'Programmation Orientée Objet', 6, 2),
('INF303', 'Base de Données', 6, 3);


-------------------------------------------------------------------------------------------------------


-- Table classe Cours
CREATE TABLE IF NOT EXISTS ClasseCours (
    id_cours INT NOT NULL ,
    id_classe INT NOT NULL,
    PRIMARY KEY(id_cours , id_classe),

    FOREIGN KEY (id_cours) REFERENCES  Cours(id) ON DELETE CASCADE,
    FOREIGN KEY (id_classe) REFERENCES  Classes(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- Algorithmique pour L1
INSERT INTO ClasseCours (id_cours, id_classe) VALUES (1, 1);

-- POO pour L2
INSERT INTO ClasseCours (id_cours, id_classe) VALUES (2, 2);

-- BDD pour L3
INSERT INTO ClasseCours (id_cours, id_classe) VALUES (3, 3);



-------------------------------------------------------------------------------------------------------


-- Table Seances
CREATE TABLE IF NOT EXISTS Seances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cours_id INT NOT NULL ,
    date_seance DATETIME NOT NULL,
    contenu TEXT NOT NULL,
    duree INT NOT NULL,

    FOREIGN KEY (cours_id) REFERENCES Cours(id)
    ON DELETE CASCADE

);


-- Séance Algorithmique (cours_id = 1)
INSERT INTO Seances (cours_id, date_seance, contenu, duree)
VALUES (1, '2025-04-02 08:00:00', 'Notions de base sur les algorithmes et les structures de contrôle', 2);

-- Séance POO (cours_id = 2)
INSERT INTO Seances (cours_id, date_seance, contenu, duree)
VALUES (2, '2025-04-02 10:00:00', 'Classes, objets et encapsulation en Java', 2);

-- Séance BDD (cours_id = 3)
INSERT INTO Seances (cours_id, date_seance, contenu, duree)
VALUES (3, '2025-04-02 14:00:00', 'Introduction au modèle relationnel et au langage SQL', 2);


-------------------------------------------------------------------------------------------------------


-- Table Validations
CREATE TABLE IF NOT EXISTS Validations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    responsable_id INT NOT NULL ,
    seance_id INT NOT NULL ,
    date_validation DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,

    FOREIGN KEY (responsable_id) REFERENCES Responsables(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (seance_id) REFERENCES Seances(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- Validation par Samba Ndiaye (responsable_id = 1) pour Algorithmique
INSERT INTO Validations (responsable_id, seance_id)
VALUES (1, 1);

-- Validation par Aminata Ba (responsable_id = 2) pour POO
INSERT INTO Validations (responsable_id, seance_id)
VALUES (2, 2);

-- Validation par Cheikh Diop (responsable_id = 3) pour BDD
INSERT INTO Validations (responsable_id, seance_id)
VALUES (3, 3);

-------------------------------------------------------------------------------------------------------