package org.example.cli;

import java.io.IOException;
import java.util.*;
import org.example.entities.Etudiant;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.entities.Utilisateur;
import org.example.enums.Semestre;
import org.jline.reader.*;
import org.jline.terminal.*;
import static org.example.cli.helpers.GlobalVars.*;

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
        // TODO - maybe throws smtg
        return null;
    }

    private static void handleLogin() {
        System.out.println("\nLogin");
        System.out.println("-----");
        String login = prompt("Username");
        String password = prompt("Password");

        // TODO : check whether it's a Prof / Admin / not signed up!
        Utilisateur utilisateur = ConnectUtilisateur(login, password);
        if (utilisateur != null) {
            currentRole = utilisateur.getRole().name();
            isLoggedIn = true;
            System.out.println("Logged in as " + currentRole);
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
        } else if("PROF".equals(currentRole)){
            options = Arrays.asList(
                    "1. Enter Grades",
                    "2. View Statistics",
                    "3. Export Reports",
                    "4. Change Password",
                    "5. Logout"
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
                case "5": handlePasswordChange(); break;
                case "6": handleLogout(); break;
                case "7": handleLogout(); break;
            }
        } else {
            switch (choice) {
                case "1": handleGrades(); break;
                case "2": showMessage("View Statistics"); break;
                case "3": showMessage("Export Reports"); break;
                case "4": handlePasswordChange(); break;
                case "5": handleLogout(); break;
            }
        }
        return true;
    }

    private static void handleProfessors() {
        while (true) {
            System.out.println("\nProfessor Management");
            System.out.println("-------------------");
            System.out.println("1. Add Professor");
            System.out.println("2. List Professors");
            System.out.println("3. Update Professor");
            System.out.println("4. Delete Professor");
            System.out.println("5. Back");

            String choice = prompt("Select option");
            if (choice.equals("5")) break;

            switch (choice) {
                case "1":
                    String name = prompt("Enter professor name");
                    String specialty = prompt("Enter specialty");
                    System.out.println("Professor added: " + name + " - " + specialty);
                    break;
                case "2":
                    System.out.println("Professor List:");
                    System.out.println("- Pr. Hamza (Mathematics)");
                    prompt("Press Enter to continue");
                    break;
                case "3":
                case "4":
                    showMessage("Operation not implemented");
                    break;
            }
        }
    }

    private static void handleModules() {
        while (true) {
            System.out.println("\nModule Management");
            System.out.println("----------------");
            System.out.println("1. Add Module");
            System.out.println("2. List Modules");
            System.out.println("3. Update Module");
            System.out.println("4. Delete Module");
            System.out.println("5. Back");

            String choice = prompt("Select option");
            if (choice.equals("5")) break;

            switch (choice) {
                case "1":
                    String name = prompt("Enter module name");
                    String code = prompt("Enter module code");
                    String semester = prompt("Enter semester (S1-S5)");
                    String nameFiliere = prompt("Enter filiere name");
                    Filiere filiere = filiereService.getByName(nameFiliere);
                    moduleService.create(Module.builder()
                            .setNom(name)
                            .setCode(code)
                            .setSemestre(Semestre.valueOf(semester))
                            .setFiliere(filiere)
                            .build());
                    System.out.println("Module added: " + code + " - " + name + " (" + semester + ")");
                    break;
                case "2":
                    System.out.println("Module List:");
                    List<Module> modules = moduleService.findAll();
                    if(!modules.isEmpty()) {
                        modules.forEach(c -> System.out.println(c.toString()));
                        prompt("Press Enter to continue");
                    } else {
                        System.out.println("No modules found!");
                        prompt("Press Enter to continue");
                    }
                    break;
                case "3":
                case "4":
                    System.out.println("Delete Module");
                    name = prompt("Enter Module name");
                    Module module = moduleService.findByName(name);
                    if(module != null) {
                        moduleService.delete(module.getId());
                        System.out.println("Module deleted: " + name);
                    } else {
                        System.out.println("Module does not exist");
                    }
                default:
                    showMessage("Operation not implemented");
                    break;
            }
        }
    }

    private static void handleGrades() {
        System.out.println("\nGrade Entry");
        System.out.println("-----------");

        // Select Module
        System.out.println("\nAvailable Modules:");
        System.out.println("1. Programming Fundamentals");
        System.out.println("2. Database Systems");
        String moduleChoice = prompt("Select module");

        // Select Element
        System.out.println("\nModule Elements:");
        System.out.println("1. Theory");
        System.out.println("2. Practice");
        String elementChoice = prompt("Select element");

        // Enter grades
        System.out.println("\nEnter grades (0-20):");
        System.out.println("Student 1: " + prompt("Grade"));
        System.out.println("Student 2: " + prompt("Grade"));
        System.out.println("Student 3: " + prompt("Grade"));

        System.out.println("Grades saved successfully!");
        prompt("Press Enter to continue");
    }

    private static void handleEtudiants() {
        while (true) {
            System.out.println("\nStudent Management");
            System.out.println("-----------------");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View Grades");
            System.out.println("6. Back");

            String choice = prompt("Select option");
            if (choice.equals("6")) break;

            switch (choice) {
                case "1":
                    String firstName = prompt("Enter student first name");
                    String lastName = prompt("Enter student last name");
                    String matricule = prompt("Enter student matricule");
                    String nameFiliere = prompt("Enter filiere");
                    Filiere filiere = filiereService.getByName(nameFiliere);
                    etudiantService.create(Etudiant
                            .builder()
                            .setFirstName(firstName)
                            .setLastName(lastName)
                            .setMatricule(matricule)
                            .setFiliere(filiere)
                            .build()
                    );
                    System.out.println("Student added: " + firstName);
                    break;
                case "2":
                    System.out.println("Etudiants List:");
                    List<Etudiant> etudiants = etudiantService.findAll();
                    etudiants.forEach(c -> System.out.println(c.toString()));
                    prompt("Press Enter to continue");
                    break;
                case "3":
                case "4":
                    System.out.println("Delete Filiere");
                    matricule = prompt("Enter Matricule");
                    etudiantService.delete(etudiantService.getByMatricule(matricule).getId());
                    System.out.println("Etudiant deleted: " + matricule);
                    break;
                case "5":
                    showMessage("Operation not implemented");
                    break;
            }
        }
    }

    private static void handlePasswordChange() {
        String oldPass = prompt("Enter current password");
        String newPass = prompt("Enter new password");
        String confirmPass = prompt("Confirm new password");

        if (newPass.equals(confirmPass)) {
            System.out.println("Password changed successfully");
        } else {
            System.out.println("Passwords do not match!");
        }
    }

    private static void handleLogout() {
        isLoggedIn = false;
        currentRole = null;
        System.out.println("Logged out successfully");
    }

    private static String prompt(String message) {
        return reader.readLine(message + ": ");
    }

    private static void showMessage(String message) {
        System.out.println("\n" + message);
        prompt("Press Enter to continue");
    }

    private static void handleFilieres() {
        while (true) {
            System.out.println("\nFilieres Management");
            System.out.println("-----------------");
            System.out.println("1. Add Filieres");
            System.out.println("2. List Filieres");
            System.out.println("3. Delete Filieres");
            System.out.println("4. Back");

            String choice = prompt("Select option");
            if (choice.equals("5")) break;

            switch (choice) {
                case "1":
                    String name = prompt("Enter filiere name");
                    String code = prompt("Enter filiere code");
                    filiereService.create(Filiere.builder().setNom(name).setCode(code).build());
                    System.out.println("filiere added: " + name + " (" + code + ")");
                    break;
                case "2":
                    System.out.println("List Filieres:");
                    List<Filiere> filieres = filiereService.getAll();
                    filieres.forEach(c -> System.out.println(c.toString()));
                    prompt("Press Enter to continue");
                    break;
                case "3":
                    System.out.println("Delete Filiere");
                    name = prompt("Enter filiere name");
                    filiereService.delete(filiereService.getByName(name).getId());
                    System.out.println("filiere deleted: " + name);
                    break;
                case "4":
                    showMessage("Operation not implemented");
                    break;
            }
        }
    }
}