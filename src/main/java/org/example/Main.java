package org.example;

import org.example.config.DatabaseConnection;
import org.example.dao.UtilisateurDAO;
import org.example.dao.impl.UtilisateurDAOImpl;
import org.example.entities.Utilisateur;
import org.example.enums.Role;
import org.example.services.UtilisateurService;
import org.example.services.impl.UtilisateurServiceImpl;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection databaseConnection=DatabaseConnection.getInstance();
        UtilisateurDAO utilisateurDAO = UtilisateurDAOImpl.getInstance(databaseConnection);
        UtilisateurService utilisateurService = new UtilisateurServiceImpl(utilisateurDAO);

        Utilisateur utilisateur1 = utilisateurService.createUtilisateur("John", "Doe", "johndoe", "password123", Role.ADMIN);
        System.out.println("Created utilisateur: " + utilisateur1.getNom() + " " + utilisateur1.getPrenom());

        Utilisateur utilisateur2 = utilisateurService.signUpUtilisateur("Jane", "Smith", "janesmith", "securePassword", Role.ADMIN);
        System.out.println("Signed up utilisateur: " + utilisateur2.getNom() + " " + utilisateur2.getPrenom());

        System.out.println("\nAll utilisateurs:");
        for (Utilisateur utilisateur : utilisateurService.getAllUtilisateurs()) {
            System.out.println(utilisateur.getNom() + " " + utilisateur.getPrenom());
        }

        // Test finding utilisateur by login
        String loginToSearch = "johndoe";
        utilisateurService.getUtilisateurByLogin(loginToSearch).ifPresentOrElse(
                utilisateur -> System.out.println("\nFound utilisateur by login (" + loginToSearch + "): " + utilisateur.getNom() + " " + utilisateur.getPrenom()),
                () -> System.out.println("\nUtilisateur with login " + loginToSearch + " not found.")
        );

        // Test updating utilisateur (simulating password change)
        boolean passwordUpdated = utilisateurService.updateMotDePasse(utilisateur1.getId(), "newPassword123");
        System.out.println("\nPassword updated for " + utilisateur1.getNom() + ": " + passwordUpdated);

        boolean utilisateurDeleted = utilisateurService.deleteUtilisateur(utilisateur1.getId());
        System.out.println("\nUtilisateur deleted: " + utilisateurDeleted);
    }
}
