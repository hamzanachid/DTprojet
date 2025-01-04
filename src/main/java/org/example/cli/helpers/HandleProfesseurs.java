package org.example.cli.helpers;

import static org.example.cli.helpers.GlobalVars.prompt;
import static org.example.cli.helpers.GlobalVars.showMessage;

public class HandleProfesseurs {
    public static void handleProfessors() {
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
}
