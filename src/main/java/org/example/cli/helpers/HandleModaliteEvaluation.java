package org.example.cli.helpers;

import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.entities.ModaliteEvaluation;
import org.example.enums.ModaliteEvaluationType;
import org.example.enums.Semestre;

import static org.example.cli.helpers.GlobalVars.*;
import static org.example.cli.helpers.GlobalVars.moduleService;

public class HandleModaliteEvaluation {
    private static void addModalite() {
        String type = prompt("Enter modalite type");
        String coefficient = prompt("what about the coefficient?");
        String element = prompt("Element name?");
        ElementDeModule elementDeModule = elementDeModuleService.findByName(element);
        ModaliteEvaluation modaliteEvaluation = ModaliteEvaluation.builder()
                .setModaliteEvaluationType(ModaliteEvaluationType.valueOf(type))
                .setCoefficient(Double.parseDouble(coefficient))
                .setElementDeModule(elementDeModule)
                .build();
        modaliteEvaluationService.create(modaliteEvaluation, elementDeModule);
        System.out.println("Modalite added: " + type + " - " + element);
    }

    private static void listModalite() {
        // TODO
    }

    private static void deleteModalite() {
        // TODO
    }

    private static void updateModalite() {
       // TODO
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
            String choice = prompt("Select option");
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
                    showMessage("Operation not available");
                    break;
            }
        }
    }
}
