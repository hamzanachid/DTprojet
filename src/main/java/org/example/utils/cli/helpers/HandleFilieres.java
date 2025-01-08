package org.example.utils.cli.helpers;

import org.example.entities.Filiere;
import java.util.List;

public class HandleFilieres {
    private static void addFiliere() {
        String name = GlobalVars.prompt("Enter filiere name");
        String code = GlobalVars.prompt("Enter filiere code");
        GlobalVars.filiereService.create(Filiere.builder().setNom(name).setCode(code).build());
        System.out.println("filiere added: " + name + " (" + code + ")");
    }

    private static void listFilieres() {
        System.out.println("List Filieres:");
        List<Filiere> filieres = GlobalVars.filiereService.getAll();
        filieres.forEach(c -> System.out.println(c.toString()));
        GlobalVars.prompt("Press Enter to continue");
    }

    private static void deleteFiliere() {
        System.out.println("Delete Filiere");
        String name = GlobalVars.prompt("Enter filiere name");
        GlobalVars.filiereService.delete(GlobalVars.filiereService.getByName(name).getId());
        System.out.println("filiere deleted: " + name);
    }

    public static void handleFilieres() {
        while (true) {
            System.out.println("\nFilieres Management");
            System.out.println("-----------------");
            System.out.println("1. Add Filieres");
            System.out.println("2. List Filieres");
            System.out.println("3. Delete Filieres");
            System.out.println("4. Back");
            String choice = GlobalVars.prompt("Select option");
            if (choice.equals("5")) break;
            switch (choice) {
                case "1":
                    addFiliere();
                    break;
                case "2":
                    listFilieres();
                    break;
                case "3":
                    deleteFiliere();
                    break;
                case "4":
                    GlobalVars.showMessage("Operation not available");
                    break;
            }
        }
    }
}
