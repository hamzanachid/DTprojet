package org.example.cli.helpers;

import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.enums.Semestre;
import java.util.List;
import static org.example.cli.helpers.GlobalVars.*;

public class HandleModules {
    private static void addModule() {
        String name = prompt("Enter module name");
        String code = prompt("Enter module code");
        String semester = prompt("Enter semester (S1-S5)");
        String nameFiliere = prompt("Enter filiere name");
        Filiere filiere = filiereService.getByName(nameFiliere);
        moduleService.create(org.example.entities.Module.builder()
                .setNom(name)
                .setCode(code)
                .setSemestre(Semestre.valueOf(semester))
                .setFiliere(filiere)
                .build());
        System.out.println("Module added: " + code + " - " + name + " (" + semester + ")");
    }

    private static void listModules() {
        System.out.println("Module List:");
        List<org.example.entities.Module> modules = moduleService.findAll();
        if(!modules.isEmpty()) {
            modules.stream().forEach(c -> System.out.println(c.toString()));
            prompt("Press Enter to continue");
        } else {
            System.out.println("No modules found!");
            prompt("Press Enter to continue");
        }
    }

    private static void updateModule() {
        Long id = Long.valueOf(prompt("Id of the module to be updated"));
        Filiere filiere = filiereService.getById(Long.valueOf(prompt("New Filiere of the module")));
        Module newModule = Module.builder()
                .setNom(prompt("New name?"))
                .setFiliere(filiere)
                .setSemestre(Semestre.valueOf(prompt("Semestre?")))
                .build();
        moduleService.update(id, newModule);
    }

    private static void deleteModule() {
        System.out.println("Delete Module");
        String name = prompt("Enter Module name");
        Module module = moduleService.findByName(name);
        if(module != null) {
            moduleService.delete(module.getId());
            System.out.println("Module deleted: " + name);
        } else {
            System.out.println("Module does not exist");
        }
    }

    public static void handleModules() {
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
                    addModule();
                    break;
                case "2":
                    listModules();
                    break;
                case "3":
                    updateModule();
                    break;
                case "4":
                    deleteModule();
                    break;
                default:
                    showMessage("Operation not implemented");
                    break;
            }
        }
    }
}
