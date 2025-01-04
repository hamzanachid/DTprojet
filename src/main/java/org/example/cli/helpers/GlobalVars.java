package org.example.cli.helpers;

import org.example.services.EtudiantService;
import org.example.services.FiliereService;
import org.example.services.ModuleService;
import org.example.services.UtilisateurService;
import org.example.services.impl.EtudiantServiceImpl;
import org.example.services.impl.FiliereServiceImpl;
import org.example.services.impl.ModuleServiceImpl;
import org.example.services.impl.UtilisateurServiceImpl;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import java.io.PrintWriter;

public class GlobalVars {
    public static Terminal terminal;
    public static LineReader reader;
    public static PrintWriter writer;
    public static boolean isLoggedIn = false;
    public static String currentRole = null;
    public static final UtilisateurService utilisateurService = UtilisateurServiceImpl.instance;
    public static final FiliereService filiereService = FiliereServiceImpl.instance;
    public static final ModuleService moduleService = ModuleServiceImpl.instance;
    public static final EtudiantService etudiantService = EtudiantServiceImpl.instance;
}
