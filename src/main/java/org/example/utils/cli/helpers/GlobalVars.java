package org.example.utils.cli.helpers;


import org.example.dao.config.DatabaseConnection;
import org.example.dao.impl.ProfesseurDaoImpl;
import org.example.entities.Professeur;
import org.example.services.*;
import org.example.services.impl.*;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.PrintWriter;

public class GlobalVars {
    public static Terminal terminal;
    public static LineReader reader;
    public static PrintWriter writer;
    public static boolean isLoggedIn = false;
    public static String currentRole = null;
    public static Professeur currentUser = null;
    public static final UtilisateurService utilisateurService = UtilisateurServiceImpl.instance;
    public static final FiliereService filiereService = FiliereServiceImpl.instance;
    public static final ModuleService moduleService = ModuleServiceImpl.instance;
    public static final EtudiantService etudiantService = EtudiantServiceImpl.instance;
    public static final ModaliteEvaluationService modaliteEvaluationService = ModaliteEvaluationServiceImpl.instance;
    public static final ElementDeModuleService elementDeModuleService = ElementsDeModuleServiceImpl.instance;
    public static final ProfesseurService professeurService = ProfesseurServiceImpl.getInstance(ProfesseurDaoImpl.getInstance(DatabaseConnection.getInstance(), utilisateurService));
    public static final NoteService noteService= NoteServiceImpl.getInstance();
    public static String prompt(String message) {
        return reader.readLine(message + ": ");
    }

    public static void handleLogout() {
        isLoggedIn = false;
        currentRole = null;
        System.out.println("Logged out successfully");
    }

    public static void showMessage(String message) {
        System.out.println("\n" + message);
        prompt("Press Enter to continue");
    }
}
