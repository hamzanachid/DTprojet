-- Drop tables in reverse order of dependencies
DROP TABLE IF EXISTS Note;
DROP TABLE IF EXISTS ModaliteEvaluation;
DROP TABLE IF EXISTS EtudiantModule;
DROP TABLE IF EXISTS Etudiant;
DROP TABLE IF EXISTS ElementDeModule;
DROP TABLE IF EXISTS Module;
DROP TABLE IF EXISTS Professeur;
DROP TABLE IF EXISTS Filiere;
DROP TABLE IF EXISTS Utilisateur;
DROP TYPE IF EXISTS modalite_evaluation_type;
DROP TYPE IF EXISTS semestre_enum;
DROP TYPE IF EXISTS role_enum;

-- Create types
CREATE TYPE role_enum AS ENUM ('ADMIN', 'PROFESSOR');
CREATE TYPE semestre_enum AS ENUM ('S1', 'S2', 'S3', 'S4', 'S5');
CREATE TYPE modalite_evaluation_type AS ENUM ('CC', 'TP', 'PROJET', 'PRESENTATION');

-- Create tables
CREATE TABLE Utilisateur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    login VARCHAR(255) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    role role_enum NOT NULL
);

CREATE TABLE Filiere (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE Professeur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    specialite VARCHAR(255) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    utilisateur_id INTEGER UNIQUE NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE Module (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    nom VARCHAR(255) NOT NULL,
    semestre semestre_enum NOT NULL,
    filiere_id INTEGER NOT NULL,
    is_validated BOOLEAN DEFAULT false,
    FOREIGN KEY (filiere_id) REFERENCES Filiere(id) ON DELETE CASCADE
);

CREATE TABLE ElementDeModule (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    coefficient REAL NOT NULL CHECK (coefficient > 0 AND coefficient <= 1),
    module_id INTEGER NOT NULL,
    prof_id INTEGER NOT NULL,
    is_validated BOOLEAN DEFAULT false,
    FOREIGN KEY (module_id) REFERENCES Module(id) ON DELETE CASCADE,
    FOREIGN KEY (prof_id) REFERENCES Professeur(id) ON DELETE CASCADE
);

CREATE TABLE Etudiant (
    id SERIAL PRIMARY KEY,
    matricule VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    filiere_id INTEGER NOT NULL,
    FOREIGN KEY (filiere_id) REFERENCES Filiere(id) ON DELETE CASCADE
);

CREATE TABLE EtudiantModule (
    etudiant_id INTEGER NOT NULL,
    module_id INTEGER NOT NULL,
    PRIMARY KEY (etudiant_id, module_id),
    FOREIGN KEY (etudiant_id) REFERENCES Etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (module_id) REFERENCES Module(id) ON DELETE CASCADE
);

CREATE TABLE ModaliteEvaluation (
    id SERIAL PRIMARY KEY,
    type modalite_evaluation_type NOT NULL,
    coefficient REAL NOT NULL CHECK (coefficient > 0 AND coefficient <= 1),
    element_module_id INTEGER NOT NULL,
    FOREIGN KEY (element_module_id) REFERENCES ElementDeModule(id) ON DELETE CASCADE
);

CREATE TABLE Note (
    id SERIAL PRIMARY KEY,
    note REAL CHECK (note >= 0 AND note <= 20),
    absence BOOLEAN DEFAULT false,
    validation BOOLEAN DEFAULT false,
    is_draft BOOLEAN DEFAULT true,
    etudiant_id INTEGER NOT NULL,
    modalite_evaluation_id INTEGER NOT NULL,
    FOREIGN KEY (etudiant_id) REFERENCES Etudiant(id) ON DELETE CASCADE,
    FOREIGN KEY (modalite_evaluation_id) REFERENCES ModaliteEvaluation(id) ON DELETE CASCADE
);

-- Basic test data insertions
-- Users (admin and prof)
INSERT INTO Utilisateur (login, mot_de_passe, role) VALUES
    ('admin', 'admin', 'ADMIN'),
    ('prof', 'prof', 'PROFESSOR');

-- Professor
INSERT INTO Professeur (nom, prenom, specialite, code, utilisateur_id) VALUES
    ('prof', 'prof', 'Math', 'P001', (SELECT id FROM Utilisateur WHERE login = 'prof'));

-- Filiere
INSERT INTO Filiere (nom, code) VALUES
    ('GI', 'F1');

-- Module
INSERT INTO Module (code, nom, semestre, filiere_id) VALUES
    ('M1', 'Math', 'S1', (SELECT id FROM Filiere WHERE code = 'F1'));

-- ElementDeModule
INSERT INTO ElementDeModule (nom, coefficient, module_id, prof_id) VALUES
    ('Analyse', 0.6, 
     (SELECT id FROM Module WHERE code = 'M1'),
     (SELECT id FROM Professeur WHERE code = 'P001'));

-- Students
INSERT INTO Etudiant (matricule, first_name, last_name, filiere_id) VALUES
    ('E1', 'student', 'one', (SELECT id FROM Filiere WHERE code = 'F1')),
    ('E2', 'student', 'two', (SELECT id FROM Filiere WHERE code = 'F1')),
    ('E3', 'student', 'three', (SELECT id FROM Filiere WHERE code = 'F1'));

-- EtudiantModule associations
INSERT INTO EtudiantModule (etudiant_id, module_id) VALUES
    ((SELECT id FROM Etudiant WHERE matricule = 'E1'), (SELECT id FROM Module WHERE code = 'M1')),
    ((SELECT id FROM Etudiant WHERE matricule = 'E2'), (SELECT id FROM Module WHERE code = 'M1')),
    ((SELECT id FROM Etudiant WHERE matricule = 'E3'), (SELECT id FROM Module WHERE code = 'M1'));

-- ModaliteEvaluation - All types for the module element
INSERT INTO ModaliteEvaluation (type, coefficient, element_module_id) VALUES
    ('CC', 0.3, (SELECT id FROM ElementDeModule WHERE nom = 'Analyse')),
    ('TP', 0.2, (SELECT id FROM ElementDeModule WHERE nom = 'Analyse')),
    ('PROJET', 0.3, (SELECT id FROM ElementDeModule WHERE nom = 'Analyse')),
    ('PRESENTATION', 0.2, (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'));

-- Notes for all students and all evaluation types
INSERT INTO Note (note, absence, validation, is_draft, etudiant_id, modalite_evaluation_id) VALUES
    -- Student One notes
    (15.5, false, true, false, 
     (SELECT id FROM Etudiant WHERE matricule = 'E1'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'CC' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (14.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E1'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'TP' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (16.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E1'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'PROJET' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (17.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E1'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'PRESENTATION' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
     
    -- Student Two notes
    (13.5, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E2'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'CC' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (12.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E2'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'TP' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (14.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E2'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'PROJET' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (15.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E2'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'PRESENTATION' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
     
    -- Student Three notes (with one absence)
    (11.5, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E3'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'CC' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (0.0, true, false, false,  -- Absent for TP
     (SELECT id FROM Etudiant WHERE matricule = 'E3'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'TP' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (12.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E3'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'PROJET' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse'))),
    (13.0, false, true, false,
     (SELECT id FROM Etudiant WHERE matricule = 'E3'),
     (SELECT id FROM ModaliteEvaluation WHERE type = 'PRESENTATION' AND element_module_id = (SELECT id FROM ElementDeModule WHERE nom = 'Analyse')));
