package org.example;

import org.example.config.DatabaseConnection;
import org.example.dao.EtudiantDao;
import org.example.dao.UtilisateurDAO;
import org.example.dao.impl.EtudiantDaoImpl;
import org.example.dao.impl.UtilisateurDAOImpl;
import org.example.entities.Etudiant;
import org.example.entities.Filiere;
import org.example.entities.Utilisateur;
import org.example.enums.Role;
import org.example.services.EtudiantService;
import org.example.services.UtilisateurService;
import org.example.services.impl.EtudiantServiceImpl;
import org.example.services.impl.UtilisateurServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        EtudiantDao etudiantService = EtudiantDaoImpl.instance;
        Filiere filiere = new Filiere(1, "IID", new ArrayList<>());
        Etudiant etudiant = new Etudiant(1L, "Marouane", "Boufarouj", "k333", filiere, new ArrayList<>(), new ArrayList<>());
        Etudiant etudiant_returned = etudiantService.create(etudiant);
        System.out.println("Created utilisateur: " + etudiant_returned.getFirstName() + " " + etudiant_returned.getLastName());
    }
}
