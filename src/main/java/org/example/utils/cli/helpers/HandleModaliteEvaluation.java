package org.example.utils.cli.helpers;

import org.example.entities.ElementDeModule;
import org.example.entities.ModaliteEvaluation;
import org.example.utils.ModaliteEvaluationType;

public class HandleModaliteEvaluation {
    private static void addModalite() {
        String type = GlobalVars.prompt("Enter modalite type");
        String coefficient = GlobalVars.prompt("what about the coefficient?");
        String element = GlobalVars.prompt("Element name?");
        ElementDeModule elementDeModule = GlobalVars.elementDeModuleService.findByName(element);
        ModaliteEvaluation modaliteEvaluation = ModaliteEvaluation.builder()
                .setModaliteEvaluationType(ModaliteEvaluationType.valueOf(type))
                .setCoefficient(Double.parseDouble(coefficient))
                .setElementDeModule(elementDeModule)
                .build();
        GlobalVars.modaliteEvaluationService.create(modaliteEvaluation, elementDeModule);
        System.out.println("Modalite added: " + type + " - " + element);
    }

    private static void listModalite() {
        GlobalVars.modaliteEvaluationService.findAll().forEach(c -> System.out.println(c.toString()));
    }

    private static void deleteModalite() {
        Long id = Long.valueOf(GlobalVars.prompt("Type the Modalite id"));
        GlobalVars.modaliteEvaluationService.delete(id);
        System.out.println("Modalite with " + id + " is deleted");
    }

    private static void updateModalite() {
        Long id = Long.valueOf(GlobalVars.prompt("what Modalite to change, type id?"));
        Double coefficient = Double.valueOf(GlobalVars.prompt("Type the new coeffecient"));
        ElementDeModule element = GlobalVars.elementDeModuleService.findByName(GlobalVars.prompt("Element name"));
        ModaliteEvaluation oldModaliteEvaluation = GlobalVars.modaliteEvaluationService.findById(id);
        ModaliteEvaluation modaliteEvaluation = ModaliteEvaluation.builder()
                .setModaliteEvaluationType(oldModaliteEvaluation.getModaliteEvaluationType())
                .setCoefficient(coefficient)
                .setElementDeModule(element)
                .build();
        GlobalVars.modaliteEvaluationService.update(id, modaliteEvaluation);
    }

    public static void handleModaliteEvaluation() {
        while (true) {
            System.out.println("\nModalites Management");
            System.out.println("-----------------");
            System.out.println("1. Add Modalites");
            System.out.println("2. List Modalites");
            System.out.println("3. Delete Modalites");
            System.out.println("4. Update Modalites");
            System.out.println("5. Back");
            String choice = GlobalVars.prompt("Select option");
            if (choice.equals("5")) break;
            switch (choice) {
                case "1":
                    addModalite();
                    break;
                case "2":
                    listModalite();
                    break;
                case "3":
                    deleteModalite();
                    break;
                case "4":
                    updateModalite();
                case "5":
                    GlobalVars.showMessage("Operation not available");
                    break;
            }
        }
    }
}
