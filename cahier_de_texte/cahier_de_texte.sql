-- Active: 1737733566034@@127.0.0.1@3306@cahier_de_texte

DROP DATABASE cahier_de_texte;

CREATE DATABASE cahier_de_texte;
USE cahier_de_texte;

CREATE TABLE IF NOT EXISTS Utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(30) NOT NULL,
    nom VARCHAR(20) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role ENUM('CHEF', 'ENSEIGNANT', 'RESPONSABLE') NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    id_responsable INT ,

    FOREIGN KEY (id_responsable) REFERENCES Utilisateurs(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Etudiants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(100) NOT NULL,
    nom VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    classe_id INT NOT NULL,
    responsable BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (classe_id) REFERENCES Classes(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Cours (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    intitule VARCHAR(200) NOT NULL,
    credits INT NOT NULL,
    enseignant_id INT ,
    
    FOREIGN KEY (enseignant_id) REFERENCES Utilisateurs(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS ClasseCours (
    id_cours INT NOT NULL ,
    id_classe INT NOT NULL,
    PRIMARY KEY(id_cours , id_classe),

    FOREIGN KEY (id_cours) REFERENCES Cours(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (id_classe) REFERENCES  Classes(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Seances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cours_id INT ,
    date_seance DATETIME NOT NULL,
    contenu TEXT NOT NULL,
    duree INT NOT NULL,
    Valide BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (cours_id) REFERENCES Cours(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS Validations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    responsable_id INT,
    seance_id INT ,
    date_validation DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,

    FOREIGN KEY (responsable_id) REFERENCES Utilisateurs(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
    FOREIGN KEY (seance_id) REFERENCES Seances(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- 1. CHEF DE DEPARTEMENT
INSERT INTO Utilisateurs (prenom, nom, email, password, role)
VALUES ('Aliou', 'Cissé', 'chef@univ.sn', 'pass123', 'CHEF');

-- 2. ENSEIGNANTS
INSERT INTO Utilisateurs (prenom, nom, email, password, role) VALUES
('Fatou', 'Diop', 'fatou.diop@univ.sn', 'pass123', 'ENSEIGNANT'),
('Moussa', 'Ba', 'moussa.ba@univ.sn', 'pass123', 'ENSEIGNANT'),
('Aminata', 'Sy', 'aminata.sy@univ.sn', 'pass123', 'ENSEIGNANT'),
('Cheikh', 'Ndiaye', 'cheikh.ndiaye@univ.sn', 'pass123', 'ENSEIGNANT'),
('Ousmane', 'Fall', 'ousmane.fall@univ.sn', 'pass123', 'ENSEIGNANT');

-- 3. RESPONSABLES
INSERT INTO Utilisateurs (prenom, nom, email, password, role) VALUES
('Awa', 'Sow', 'awa.sow@univ.sn', 'pass123', 'RESPONSABLE'),
('Ibou', 'Sarr', 'ibou.sarr@univ.sn', 'pass123', 'RESPONSABLE'),
('Mame', 'Diouf', 'mame.diouf@univ.sn', 'pass123', 'RESPONSABLE'),
('Papa', 'Camara', 'papa.camara@univ.sn', 'pass123', 'RESPONSABLE'),
('Mouhamed', 'Dieng', 'mouhamed.dieng@univ.sn', 'pass123', 'RESPONSABLE');

-- 4. CLASSES (associées aux responsables id 7 à 11)
INSERT INTO Classes (nom, id_responsable) VALUES
('L1 Informatique', 7),
('L2 Informatique', 8),
('L3 Informatique', 9),
('M1 Informatique', 10),
('M2 Informatique', 11);

-- 6. COURS (5 enseignants id 2 à 6)
INSERT INTO Cours (code, intitule, credits, enseignant_id) VALUES
('CS101', 'Algorithmique 1', 4, 2),
('CS102', 'Programmation Java', 4, 3),
('CS103', 'Base de données', 3, 4),
('CS104', 'Réseaux Informatiques', 3, 5),
('CS105', 'Systèmes d’exploitation', 3, 6);

-- 7. CLASSE-COURS : Associe chaque cours à une ou plusieurs classes
INSERT INTO ClasseCours (id_cours, id_classe) VALUES
(1, 1), (1, 2),
(2, 2), (2, 3),
(3, 3), (3, 4),
(4, 4), (4, 5),
(5, 1), (5, 5);

-- 8. SEANCES (on crée une séance pour chaque cours)
INSERT INTO Seances (cours_id, date_seance, contenu, duree) VALUES
(1, '2025-04-01 08:00:00', 'Introduction à l’algorithmique', 2),
(2, '2025-04-02 10:00:00', 'Classes et objets en Java', 3),
(3, '2025-04-03 09:00:00', 'Modèle relationnel', 2),
(4, '2025-04-04 11:00:00', 'Adressage IP', 2),
(5, '2025-04-05 13:00:00', 'Gestion de processus', 2);

-- 9. VALIDATIONS (chaque séance validée par un responsable)
-- Par exemple, les responsables id 7 à 11 valident les séances 1 à 5 :
INSERT INTO Validations (responsable_id, seance_id) VALUES
(7, 1),
(8, 2),
(9, 3),
(10, 4),
(11, 5);


-- Responsable aussi Étudiant
INSERT INTO Etudiants (prenom, nom, email, classe_id) VALUES
('Awa', 'Sow', 'awa.sow@univ.sn', 1);

-- 9 autres étudiants
INSERT INTO Etudiants (prenom, nom, email, classe_id) VALUES
('Abdou', 'Kane', 'abdou.kane@univ.sn', 1),
('Mariama', 'Faye', 'mariama.faye@univ.sn', 1),
('Oumar', 'Thiam', 'oumar.thiam@univ.sn', 1),
('Aissatou', 'Lo', 'aissatou.lo@univ.sn', 1),
('Modou', 'Gueye', 'modou.gueye@univ.sn', 1),
('Ndeye', 'Fall', 'ndeye.fall@univ.sn', 1),
('Babacar', 'Ndiaye', 'babacar.ndiaye@univ.sn', 1),
('Sokhna', 'Ba', 'sokhna.ba@univ.sn', 1),
('Moussa', 'Diouf', 'moussa.diouf@univ.sn', 1);

INSERT INTO Etudiants (prenom, nom, email, classe_id) VALUES
('Ibou', 'Sarr', 'ibou.sarr@univ.sn', 2),
('Fatou', 'Diallo', 'fatou.diallo@univ.sn', 2),
('Mamadou', 'Barry', 'mamadou.barry@univ.sn', 2),
('Khady', 'Dieng', 'khady.dieng@univ.sn', 2),
('Aliou', 'Camara', 'aliou.camara@univ.sn', 2),
('Bineta', 'Mbaye', 'bineta.mbaye@univ.sn', 2),
('Serigne', 'Gaye', 'serigne.gaye@univ.sn', 2),
('Astou', 'Sane', 'astou.sane@univ.sn', 2),
('Tidiane', 'Fall', 'tidiane.fall@univ.sn', 2),
('Penda', 'Ndao', 'penda.ndao@univ.sn', 2);

INSERT INTO Etudiants (prenom, nom, email, classe_id) VALUES
('Mame', 'Diouf', 'mame.diouf@univ.sn', 3),
('Amadou', 'Ba', 'amadou.ba@univ.sn', 3),
('Seynabou', 'Seck', 'seynabou.seck@univ.sn', 3),
('Cheikh', 'Sow', 'cheikh.sow@univ.sn', 3),
('Rokhaya', 'Niang', 'rokhaya.niang@univ.sn', 3),
('Youssou', 'Ndoye', 'youssou.ndoye@univ.sn', 3),
('Dieynaba', 'Mbaye', 'dieynaba.mbaye@univ.sn', 3),
('Samba', 'Gueye', 'samba.gueye@univ.sn', 3),
('Maty', 'Sarr', 'maty.sarr@univ.sn', 3),
('Abdoul', 'Diagne', 'abdoul.diagne@univ.sn', 3);

INSERT INTO Etudiants (prenom, nom, email, classe_id) VALUES
('Papa', 'Camara', 'papa.camara@univ.sn', 4),
('Thierno', 'Diallo', 'thierno.diallo@univ.sn', 4),
('Awa', 'Fall', 'awa.fall@univ.sn', 4),
('Mohamed', 'Ndiaye', 'mohamed.ndiaye@univ.sn', 4),
('Amy', 'Sow', 'amy.sow@univ.sn', 4),
('Karim', 'Diop', 'karim.diop@univ.sn', 4),
('Mbayang', 'Cissé', 'mbayang.cisse@univ.sn', 4),
('Ismaila', 'Diagne', 'ismaila.diagne@univ.sn', 4),
('Fatimata', 'Ka', 'fatimata.ka@univ.sn', 4),
('Yacine', 'Sy', 'yacine.sy@univ.sn', 4);

INSERT INTO Etudiants (prenom, nom, email, classe_id) VALUES
('Mouhamed', 'Dieng', 'mouhamed.dieng@univ.sn', 5),
('Aminata', 'Gaye', 'aminata.gaye@univ.sn', 5),
('Bocar', 'Fall', 'bocar.fall@univ.sn', 5),
('Khadija', 'Thiam', 'khadija.thiam@univ.sn', 5),
('Mouhamadou', 'Ly', 'mouhamadou.ly@univ.sn', 5),
('Ndeye Awa', 'Ndao', 'ndeyeawa.ndao@univ.sn', 5),
('El Hadji', 'Dabo', 'elhadji.dabo@univ.sn', 5),
('Aissatou', 'Balde', 'aissatou.balde@univ.sn', 5),
('Fallou', 'Diouf', 'fallou.diouf@univ.sn', 5),
('Fatoumata', 'Ndoye', 'fatoumata.ndoye@univ.sn', 5);



SELECT Seances.date_seance, Cours.code AS code_cours, Cours.intitule AS intitule_cours, Seances.contenu,
        Seances.duree,
        CONCAT(Enseignant.prenom , " ", Enseignant.nom) AS enseignant
FROM `Seances`
INNER JOIN Cours ON Cours.id = Seances.cours_id
INNER JOIN `ClasseCours` ON `ClasseCours`.id_cours = `Cours`.id
INNER JOIN `Classes` ON `Classes`.id = `ClasseCours`.id_classe
INNER JOIN Utilisateurs AS Enseignant ON Enseignant.id = Cours.enseignant_id
WHERE  `Seances`.`Valide` = false AND `Classes`.nom = "L1 Informatique";