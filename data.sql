-- Database: wufu
CREATE TYPE role_enum AS ENUM ('ADMIN', 'PROFESSOR');
CREATE TYPE semestre_enum AS ENUM ('S1', 'S2', 'S3', 'S4', 'S5');
CREATE TYPE modalite_evaluation_type AS ENUM ('CC', 'TP', 'PROJET', 'PRESENTATION');

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
    is_validated BOOLEAN DEFAULT false,
    FOREIGN KEY (module_id) REFERENCES Module(id) ON DELETE CASCADE
);

CREATE TABLE Professeur (
    id SERIAL PRIMARY KEY,
    specialite VARCHAR(255) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    utilisateur_id INTEGER UNIQUE NOT NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

CREATE TABLE Etudiant (
    id SERIAL PRIMARY KEY,
    matricule VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    filiere_id INTEGER NOT NULL,
    FOREIGN KEY (filiere_id) REFERENCES Filiere(id) ON DELETE CASCADE
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

CREATE TABLE Professeur_ElementDeModule (
    professeur_id INTEGER NOT NULL,
    element_module_id INTEGER NOT NULL,
    PRIMARY KEY (professeur_id, element_module_id),
    FOREIGN KEY (professeur_id) REFERENCES Professeur(id) ON DELETE CASCADE,
    FOREIGN KEY (element_module_id) REFERENCES ElementDeModule(id) ON DELETE CASCADE
);
