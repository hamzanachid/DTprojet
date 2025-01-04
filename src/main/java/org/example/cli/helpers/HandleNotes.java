package org.example.cli.helpers;

import org.example.entities.*;
import org.example.services.ElementDeModuleService;
import org.example.services.EtudiantService;
import org.example.services.NoteService;
import org.example.services.impl.ElementsDeModuleServiceImpl;
import org.example.services.impl.EtudiantServiceImpl;
import org.example.services.impl.NoteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.cli.Main.prompt;

public class HandleNotes {
    private static final ElementDeModuleService elementDeModuleService= ElementsDeModuleServiceImpl.instance;
    private static final ModaliteEvaluationService modaliteEvaliationService= ModaliteEvaluationServiceImpl.instance;
    private static final NoteService noteService= NoteServiceImpl.getInstance();
    private static final EtudiantService etudiantService= EtudiantServiceImpl.instance;

    public static void handleElementNotes(Professeur professeur) {
        while (true) {
            System.out.println("handle elements model");
            List<ElementDeModule> elementDeModuleList = displayAndFetchElements(professeur);
            Optional<ElementDeModule> foundElement = selectElement(elementDeModuleList);



            if (foundElement.isPresent()) {
                System.out.println("1. handle element note");
                System.out.println("2. calculate average");
                System.out.println("3. export notes");
                System.out.println("4. go Back");
                String choice = prompt("Enter an element:");
                if (choice.equals("4") )
                    break;

                switch (choice) {
                    case "1":{
                        ElementDeModule selectedElement = foundElement.get();
                        List<ModaliteEvaluation> modaliteEvaluations = displayAndFetchModalites(selectedElement);
                        Optional<ModaliteEvaluation> foundModalite = selectModalite(modaliteEvaluations);

                        if (foundModalite.isPresent()) {
                            ModaliteEvaluation modaliteEvaluation = foundModalite.get();
                            handleModaliteActions(modaliteEvaluation, selectedElement);
                        }
                    }
                    case "2" :{

                    }
                    case "3":{

                    }

                }

            }else {
                System.err.println("not exsits in the list");
            }
        }
    }

    private static List<ElementDeModule> displayAndFetchElements(Professeur professeur) {
        System.out.println("Elements:");
        List<ElementDeModule> elementDeModuleList = elementDeModuleService.findByProfId(professeur.getId());
        elementDeModuleList.forEach(c -> System.out.println(c.getNom()));
        return elementDeModuleList;
    }

    private static Optional<ElementDeModule> selectElement(List<ElementDeModule> elementDeModuleList) {
        String element = prompt("Enter an element:");
        return elementDeModuleList.stream()
                .filter(c -> c.getNom().equalsIgnoreCase(element))
                .findFirst();
    }

    private static List<ModaliteEvaluation> displayAndFetchModalites(ElementDeModule element) {
        System.out.println("Modalities:");
        List<ModaliteEvaluation> modaliteEvaluations = modaliteEvaliationService.findByElementId(element.getId());
        modaliteEvaluations.forEach(c -> System.out.println(c.getModaliteEvaluationType().name()));
        return modaliteEvaluations;
    }

    private static Optional<ModaliteEvaluation> selectModalite(List<ModaliteEvaluation> modaliteEvaluations) {
        String modalite = prompt("Enter a modality:");
        return modaliteEvaluations.stream()
                .filter(c -> c.getModaliteEvaluationType().name().equalsIgnoreCase(modalite))
                .findFirst();
    }

    private static void handleModaliteActions(ModaliteEvaluation modaliteEvaluation, ElementDeModule element) {
        while (true) {
            displayModaliteOptions(modaliteEvaluation);
            String choice = prompt("Select option:");
            if (choice.equals("5"))
               break;
            switch (choice) {
                case "1":
                    displayNotes(modaliteEvaluation);
                    break;
                case "2":
                    addNotes(modaliteEvaluation, element);
                    break;
                case "3":
                    modifyNotes(modaliteEvaluation);
                    break;
                case "4":
                    validateNotes(modaliteEvaluation);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void displayModaliteOptions(ModaliteEvaluation modaliteEvaluation) {
        List<Note> notes = noteService.getNotesByModalite(modaliteEvaluation.getId());
        if (notes.isEmpty()) {
            System.out.println("2. Add notes");
        } else if (notes.stream().allMatch(Note::isValidation)) {
            System.out.println("1. Display notes");
            System.out.println("4. Validate notes");
        } else {
            System.out.println("1. Display notes");
            System.out.println("2. Add notes");
            System.out.println("3. Modify notes");
            System.out.println("4. Validate notes");
        }
    }

    private static void displayNotes(ModaliteEvaluation modaliteEvaluation) {
        List<Note> notes = noteService.getNotesByModalite(modaliteEvaluation.getId());
        notes.forEach(c -> System.out.println("Student: " + c.getEtudiant().getLastName() + ", Note: " + c.getNote()));
    }

    private static void addNotes(ModaliteEvaluation modaliteEvaluation, ElementDeModule element) {
        List<Note> notes = new ArrayList<>();
        List<Etudiant> students = etudiantService.findEtudiantByElement(element.getId());

        students.forEach(student -> {
            System.out.println("Student: " + student.getLastName());
            String noteInput = prompt("Enter the note: ");
            Double Rnote = Double.valueOf(noteInput);

            boolean absence = false;
            if (Rnote == 0) {
                String isAbsent = prompt("Was the student absent? (yes/no)");
                absence = isAbsent.equalsIgnoreCase("yes");
            }

            Note note = Note.builder()
                    .setNote(Rnote)
                    .setAbsence(absence)
                    .setValidation(false)
                    .setEtudiant(student)
                    .setModaliteEvaluation(modaliteEvaluation)
                    .build();

            notes.add(note);
        });

        notes.forEach(noteService::addNote);
    }

    private static void modifyNotes(ModaliteEvaluation modaliteEvaluation) {
        List<Note> notes = noteService.getNotesByModalite(modaliteEvaluation.getId());

        notes.forEach(note -> {
            System.out.println("Student: " + note.getEtudiant().getLastName());
            String noteInput = prompt("Enter the note [" + note.getNote() + "]: ");
            if (noteInput != null) {
                Double Rnote = Double.valueOf(noteInput);
                note.setNote(Rnote);

                boolean absence = false;
                if (Rnote == 0) {
                    String isAbsent = prompt("Was the student absent? (yes/no)");
                    absence = isAbsent.equalsIgnoreCase("yes");
                }
                note.setAbsence(absence);
            }
        });

        notes.forEach(noteService::updateNote);
    }

    private static void validateNotes(ModaliteEvaluation modaliteEvaluation) {
        List<Note> notes = noteService.getNotesByModalite(modaliteEvaluation.getId());

        notes.forEach(note -> note.setValidation(true));
        notes.forEach(noteService::updateNote);
    }



}
