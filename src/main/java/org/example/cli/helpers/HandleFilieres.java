package org.example.cli.helpers;

import org.example.entities.Filiere;
import java.util.List;
import static org.example.cli.helpers.GlobalVars.*;
import static org.example.cli.helpers.GlobalVars.prompt;

public class HandleFilieres {
    private static void addFiliere() {
        String name = prompt("Enter filiere name");
        String code = prompt("Enter filiere code");
        filiereService.create(Filiere.builder().setNom(name).setCode(code).build());
        System.out.println("filiere added: " + name + " (" + code + ")");
    }

    private static void listFilieres() {
        System.out.println("List Filieres:");
        List<Filiere> filieres = filiereService.getAll();
        filieres.forEach(c -> System.out.println(c.toString()));
        prompt("Press Enter to continue");
    }

    private static void deleteFiliere() {
        System.out.println("Delete Filiere");
        String name = prompt("Enter filiere name");
        filiereService.delete(filiereService.getByName(name).getId());
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
            String choice = prompt("Select option");
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
                    showMessage("Operation not available");
                    break;
            }
        }
    }
}
