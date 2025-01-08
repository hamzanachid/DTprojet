package org.example.utils.cli.note;

import org.example.entities.*;
import org.example.utils.cli.helpers.GlobalVars;

import java.util.*;

public class HandleNotes {

    public static void handleNotes(Professeur professor) {
        while (true) {
            System.out.println("Manage Elements");
            List<ElementDeModule> elements = getElements(professor);
            Optional<ElementDeModule> selectedElement = selectElement(elements);
            if (selectedElement.isPresent()) {
                ElementDeModule element = selectedElement.get();

                CommandManager commandManager = new CommandManager();
                commandManager.add("1", new HandleNotesCommand(element));
                commandManager.add("2", new CalculateAverageCommand(element));
                commandManager.add("3", new ExportNotesCommand());

                System.out.println("1. Manage notes");
                System.out.println("2. Calculate average");
                System.out.println("3. Export notes");
                System.out.println("4. Go back");

                String option = GlobalVars.prompt("Enter your choice:");
                if (option.equals("4")) {
                    break;
                }

                commandManager.execute(option);
            } else {
                System.err.println("Element not found.");
            }
        }
    }

    public static List<ElementDeModule> getElements(Professeur professor) {
        System.out.println("List of Elements : ");
        List<ElementDeModule> elements = GlobalVars.elementDeModuleService.findByProfId(professor.getId());
        elements.forEach(element -> System.out.println(element.getNom()));
        return elements;
    }

    public static Optional<ElementDeModule> selectElement(List<ElementDeModule> elements) {
        String elementName = GlobalVars.prompt("Enter the name of an element ");
        return elements.stream()
                .filter(element -> element.getNom().equalsIgnoreCase(elementName))
                .findFirst();
    }
}
