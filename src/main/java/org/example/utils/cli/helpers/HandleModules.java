package org.example.utils.cli.helpers;

import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.utils.Semestre;

import java.util.List;

public class HandleModules {
    private static void addModule() {
        String name = GlobalVars.prompt("Enter module name");
        String code = GlobalVars.prompt("Enter module code");
        String semester = GlobalVars.prompt("Enter semester (S1-S5)");
        String nameFiliere = GlobalVars.prompt("Enter filiere name");
        Filiere filiere = GlobalVars.filiereService.getByName(nameFiliere);
        GlobalVars.moduleService.create(Module.builder()
                .setNom(name)
                .setCode(code)
                .setSemestre(Semestre.valueOf(semester))
                .setFiliere(filiere)
                .build());
        System.out.println("Module added: " + code + " - " + name + " (" + semester + ")");
    }

    private static void listModules() {
        System.out.println("Module List:");
        List<org.example.entities.Module> modules = GlobalVars.moduleService.findAll();
        if(!modules.isEmpty()) {
            modules.stream().forEach(c -> System.out.println(c.toString()));
            GlobalVars.prompt("Press Enter to continue");
        } else {
            System.out.println("No modules found!");
            GlobalVars.prompt("Press Enter to continue");
        }
    }

    private static void updateModule() {
        Long id = Long.valueOf(GlobalVars.prompt("Id of the module to be updated"));
        Filiere filiere = GlobalVars.filiereService.getById(Long.valueOf(GlobalVars.prompt("New Filiere of the module, type ID")));
        Module module = GlobalVars.moduleService.findById(id);
        Module newModule = Module.builder()
                .setId(id)
                .setCode(module.getCode())
                .setNom(GlobalVars.prompt("New name?"))
                .setFiliere(filiere)
                .setSemestre(Semestre.valueOf(GlobalVars.prompt("Semestre?")))
                .build();
        GlobalVars.moduleService.update(id, newModule);
    }

    private static void deleteModule() {
        System.out.println("Delete Module");
        String name = GlobalVars.prompt("Enter Module name");
        Module module = GlobalVars.moduleService.findByName(name);
        if(module != null) {
            GlobalVars.moduleService.delete(module.getId());
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

            String choice = GlobalVars.prompt("Select option");
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
                    GlobalVars.showMessage("Operation not implemented");
                    break;
            }
        }
    }
}
