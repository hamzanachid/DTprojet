package org.example.utils.cli.note;

import org.example.entities.ElementDeModule;
import org.example.entities.Etudiant;
import org.example.entities.ModaliteEvaluation;
import org.example.entities.Note;
import org.example.utils.cli.helpers.GlobalVars;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class HandleNotesCommand implements Command {
    private final ElementDeModule element;

    public HandleNotesCommand(ElementDeModule element) {
        this.element = element;
    }

    @Override
    public void execute() {
        List<ModaliteEvaluation> modalities = getModalities(element);
        Optional<ModaliteEvaluation> selectedModality =selectModality(modalities);
        if (selectedModality.isPresent()) {
            ModaliteEvaluation modality = selectedModality.get();
            performModalityActions(modality, element);
        } else {
            System.err.println("Modality not found.");
        }
    }
    public Optional<ModaliteEvaluation> selectModality(List<ModaliteEvaluation> modalities) {
        String modalityName = GlobalVars.prompt("Enter the name of a modality ");
        return modalities.stream()
                .filter(modality -> modality.getModaliteEvaluationType().name().equalsIgnoreCase(modalityName))
                .findFirst();
    }

    public  void performModalityActions(ModaliteEvaluation modality, ElementDeModule element) {
        while (true) {
            displayModalityOptions(modality);
            String option = GlobalVars.prompt("Choose an option ");
            if (option.equals("5")) {
                break;
            }
            switch (option) {
                case "1":
                    showNotes(modality);
                    break;
                case "2":
                    addNotes(modality, element);
                    break;
                case "3":
                    updateNotes(modality);
                    break;
                case "4":
                    validateNotes(modality);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private  void displayModalityOptions(ModaliteEvaluation modality) {
        List<Note> notes = GlobalVars.noteService.getNotesByModalite(modality.getId());
        if (notes.isEmpty()) {
            System.out.println("2. Add notes");
        } else if (notes.stream().allMatch(Note::isValidation)) {
            System.out.println("1. Show notes");
        } else {
            System.out.println("1. Show notes");
            System.out.println("2. Add notes");
            System.out.println("3. Update notes");
            System.out.println("4. Validate notes");
        }
    }

    private void showNotes(ModaliteEvaluation modality) {
        List<Note> notes = GlobalVars.noteService.getNotesByModalite(modality.getId());
        notes.forEach(note -> System.out.println("Student: " + note.getEtudiant().getLastName() + ", Note: " + note.getNote()));
    }
    public List<ModaliteEvaluation> getModalities(ElementDeModule element) {
        System.out.println("List of Modalities:");
        List<ModaliteEvaluation> modalities = GlobalVars.modaliteEvaluationService.findByElement(element);
        modalities.forEach(modality -> System.out.println(modality.getModaliteEvaluationType().name()));
        return modalities;
    }


    private void addNotes(ModaliteEvaluation modality, ElementDeModule element) {
        List<Note> notes = new ArrayList<>();
        List<Etudiant> students = GlobalVars.etudiantService.findEtudiantByElement(element.getId());

        students.forEach(student -> {
            System.out.println("Student: " + student.getLastName());
            String noteInput = GlobalVars.prompt("Enter the note  ");
            Double noteValue = Double.valueOf(noteInput);

            boolean isAbsent = false;
            if (noteValue == 0) {
                String absentInput = GlobalVars.prompt("Was the student absent? (yes/no)");
                isAbsent = absentInput.equalsIgnoreCase("yes");
            }

            Note note = Note.builder()
                    .setNote(noteValue)
                    .setAbsence(isAbsent)
                    .setValidation(false)
                    .setEtudiant(student)
                    .setModaliteEvaluation(modality)
                    .build();

            notes.add(note);
        });

        notes.forEach(GlobalVars.noteService::addNote);
    }

    private void updateNotes(ModaliteEvaluation modality) {
        List<Note> notes = GlobalVars.noteService.getNotesByModalite(modality.getId());

        notes.forEach(note -> {
            System.out.println("Student: " + note.getEtudiant().getLastName());
            String noteInput = GlobalVars.prompt("Enter the new note [" + note.getNote() + "]  ");
            System.out.println(noteInput.isEmpty());
            if (!noteInput.isEmpty()) {
                Double newNote = Double.valueOf(noteInput);
                note.setNote(newNote);
                boolean isAbsent = false;
                if (newNote == 0) {
                    String absentInput = GlobalVars.prompt("Was the student absent? (yes/no)");
                    isAbsent = absentInput.equalsIgnoreCase("yes");
                }
                note.setAbsence(isAbsent);
            }
        });

        notes.forEach(GlobalVars.noteService::updateNote);
    }

    private void validateNotes(ModaliteEvaluation modality) {
        List<Note> notes = GlobalVars.noteService.getNotesByModalite(modality.getId());
        GlobalVars.noteService.validateRange(notes);
        GlobalVars.noteService.validateEdgeNotes(notes);

        notes.forEach(note -> note.setValidation(true));
        notes.forEach(GlobalVars.noteService::updateNote);
        System.out.println("Notes validated.");
    }
}
