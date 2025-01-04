package org.example.cli;

import org.example.entities.Professeur;
import org.example.entities.Utilisateur;
import org.example.enums.EnumRole;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.*;

import static org.example.cli.helpers.GlobalVars.*;
 import static org.example.cli.helpers.HandleElementModule.handleElementModule;
import static org.example.cli.helpers.HandleEtudiants.handleEtudiants;
import static org.example.cli.helpers.HandleFilieres.handleFilieres;
import static org.example.cli.helpers.HandleModaliteEvaluation.handleModaliteEvaluation;
import static org.example.cli.helpers.HandleModules.handleModules;
import static org.example.cli.helpers.HandleNotes.handleElementNotes;
import static org.example.cli.helpers.HandleProfesseurs.handleProfessors;


@SuppressWarnings("unused")
public class Main {
    public static void run() {
        try {
            terminal = TerminalBuilder.builder().system(true).build();
            reader = LineReaderBuilder.builder().terminal(terminal).build();
            writer = terminal.writer();
            startApp();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void startApp() {
        while (true) {
            if (!isLoggedIn) {
                handleLogin();
            } else {
                String choice = showMenu();
                if (!handleMenuChoice(choice)) break;
            }
        }
    }

    private static Utilisateur ConnectUtilisateur(String login, String password) {
        Optional<Utilisateur> maybeUtilsateur = utilisateurService.authenticateUtilisateur(login, password);
        if (maybeUtilsateur.isPresent()){
            return maybeUtilsateur.get();
        }
        throw new RuntimeException("Utilisateur not found!");
    }

    private static void handleLogin() {
        System.out.println("\nLogin");
        System.out.println("-----");
        String login = prompt("Username");
        String password = prompt("Password");
        Utilisateur utilisateur = ConnectUtilisateur(login, password);
        if (utilisateur != null) {
            currentRole = utilisateur.getRole().name();
            isLoggedIn = true;
            System.out.println("Logged in as " + currentRole);
            if(currentRole.equals(EnumRole.PROFESSOR.name())){

            }
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static String showMenu() {
        List<String> options = new ArrayList<>();
        if ("ADMIN".equals(currentRole)) {
            options = Arrays.asList(
                    "1. Manage Professors",
                    "2. Manage Modules",
                    "3. Manage Etudiants",
                    "4. Manage Filieres",
                    "5. Manage Modalité d'evaluation",
                    "6. Elements de module",
                    "7. logout"
            );
        } else if("PROFESSOR".equals(currentRole)){
            options = Arrays.asList(
                    "1. Choose Elements",
                    "2. Logout"
            );
        }
        System.out.println("\nMain Menu");
        System.out.println("---------");
        options.forEach(System.out::println);
        return prompt("Select option");
    }


    private static boolean handleMenuChoice(String choice) {
        if ("ADMIN".equals(currentRole)) {
            switch (choice) {
                case "1": handleProfessors(); break;
                case "2": handleModules(); break;
                case "3": handleEtudiants(); break;
                case "4": handleFilieres(); break;
                case "5": handleModaliteEvaluation(); break;
                case "6": handleElementModule(); break;
                case "7": handleLogout(); break;
            }
        } else {
            switch (choice) { 
                case "1": handleElementNotes(new Professeur()); break;
                case "2": handleLogout(); break;
            }
        }
        return true;
    }



}