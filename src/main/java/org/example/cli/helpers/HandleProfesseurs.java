package org.example.cli.helpers;

import org.example.entities.Professeur;
import org.example.entities.Utilisateur;
import org.example.enums.EnumRole;

import java.util.List;
import java.util.Optional;

import static org.example.cli.helpers.GlobalVars.*;

public class HandleProfesseurs {
  private static void ajouterProfesseur() {
    String firstName = prompt("Enter the professor first name");
    String lastName = prompt("Enter the professor last name");
    String speciality = prompt("Enter the professor speciality");
    String code = prompt("Enter the professor code");
    String login = prompt("Enter the professor login");
    String password = prompt("Enter the professor password");

    Utilisateur utilisateur = utilisateurService.createUtilisateur(
      login,
      password,
      EnumRole.PROFESSOR
    );

    Professeur professeur = professeurService.create(
      Professeur.builder()
        .setNom(lastName)
        .setPrenom(firstName)
        .setSpecialite(speciality)
        .setCode(code)
        .setUtilisateur(utilisateur)
        .build()
    );

    System.out.println("Professor was registered successfully");
    System.out.println(professeur.getProfessorInfo());
  }

  private static void listerProfesseurs() {
    System.out.println("Professors List:");
    List<Professeur> professeurs = professeurService.findAll();
    professeurs.forEach(professeur -> System.out.println(professeur.getProfessorInfo()));
    prompt("Press Enter to continue");
  }

  private static void modifierProfessor() {
    System.out.println("Update Professor");
    String profCode = prompt("Enter the professor code you want to update");

    Optional<Professeur> professor = professeurService.findByCode(profCode);
    if (professor.isPresent()) {
      String newFirstName = prompt("Enter the new first name");
      if (newFirstName != null && !newFirstName.isEmpty()) {
        professor.get().setPrenom(newFirstName);
      }

      String newLastName = prompt("Enter the new last name");
      if (newLastName != null && !newLastName.isEmpty()) {
        professor.get().setNom(newLastName);
      }

      String speciality = prompt("Entre the new speciality");
      if (speciality != null && !speciality.isEmpty()) {
        professor.get().setSpecialite(speciality);
      }

      String code = prompt("Enter the new code");
      if (code != null && !code.isEmpty()) {
        professor.get().setCode(code);
      }

      professeurService.update(professor.get());
      System.out.println("Professor updated successfully: " + professor.get().getProfessorInfo());
    } else {
      System.out.println("No professor has this code.");
    }

  }

  private static void supprimerProfesseur() {
    System.out.println("Delete Professor");
    String code = prompt("Enter the professor code");

    Optional<Professeur> professeur = professeurService.findByCode(code);

    if (professeur.isPresent()) {
      professeurService.delete(professeur.get().getId());
      utilisateurService.deleteUtilisateur(professeur.get().getUtilisateur().getId());
      System.out.println("Professor with code " + code + "was deleted successfully");
    } else {
      System.out.println("No professor with this code was found.");
    }
  }

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
          ajouterProfesseur();
          break;
        case "2":
          listerProfesseurs();
          break;
        case "3":
          modifierProfessor();
          break;
        case "4":
          supprimerProfesseur();
          break;
      }
    }
  }
}
