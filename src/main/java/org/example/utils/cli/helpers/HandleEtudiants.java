package org.example.utils.cli.helpers;

import org.example.entities.Etudiant;
import org.example.entities.Filiere;

import java.util.List;

public class HandleEtudiants {
    private static void addEtudiant() {
        String firstName = GlobalVars.prompt("Enter student first name");
        String lastName = GlobalVars.prompt("Enter student last name");
        String matricule = GlobalVars.prompt("Enter student matricule");
        String nameFiliere = GlobalVars.prompt("Enter filiere");
        Filiere filiere = GlobalVars.filiereService.getByName(nameFiliere);
        GlobalVars.etudiantService.create(Etudiant
                .builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setMatricule(matricule)
                .setFiliere(filiere)
                .build()
        );
        System.out.println("Etudiant added: " + firstName);
    }

    private static void listEtudiants() {
        System.out.println("Etudiants List:");
        List<Etudiant> etudiants = GlobalVars.etudiantService.findAll();
        etudiants.forEach(c -> System.out.println(c.toString()));
        GlobalVars.prompt("Press Enter to continue");
    }

    private static void deleteEtudiant() {
        System.out.println("Delete Etudiant");
        String matricule = GlobalVars.prompt("Enter Matricule");
        GlobalVars.etudiantService.delete(GlobalVars.etudiantService.getByMatricule(matricule).getId());
        System.out.println("Etudiant deleted: " + matricule);
    }


    private static void updateEtudiant() {
        System.out.println("Update Etudiant");
        Long Id = Long.valueOf(GlobalVars.prompt("Enter Etudiant id to be updated"));
        String newFirstName = GlobalVars.prompt("Enter new first name");
        String lastLastName = GlobalVars.prompt("Enter new last name");
        String matricule = GlobalVars.prompt("new matriculer?");
        Etudiant oldEtudiant = GlobalVars.etudiantService.getById(Id);
        Etudiant newEtudiant = Etudiant.builder()
                .setFirstName(newFirstName)
                .setLastName(lastLastName)
                .setMatricule(matricule)
                .setFiliere(oldEtudiant.getFiliere())
                .build();
        GlobalVars.etudiantService.update(oldEtudiant, newEtudiant);
        System.out.println("Etudiant Updated: "+ newEtudiant.toString());
    }

    public static void handleEtudiants() {
        while (true) {
            System.out.println("\nEtudiant Management");
            System.out.println("-----------------");
            System.out.println("1. Add Etudiant");
            System.out.println("2. List Etudiants");
            System.out.println("3. Update Etudiant");
            System.out.println("4. Delete Etudiant");
            System.out.println("5. View Grades");
            System.out.println("6. Back");
            String choice = GlobalVars.prompt("Select option");
            if (choice.equals("6")) break;
            switch (choice) {
                case "1":
                    addEtudiant();
                    break;
                case "2":
                    listEtudiants();
                    break;
                case "3":
                    updateEtudiant();
                    break;
                case "4":
                    deleteEtudiant();
                    break;
                case "5":
                    GlobalVars.showMessage("Operation not implemented");
                    break;
            }
        }
    }
}
