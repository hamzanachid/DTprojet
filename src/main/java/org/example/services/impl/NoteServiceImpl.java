package org.example.services.impl;

import org.example.dao.ElementDeModuleDAO;
import org.example.dao.NoteDao;
import org.example.dao.impl.ElementDeModuleDaoImpl;
import org.example.dao.impl.NoteDaoImpl;
import org.example.entities.ModaliteEvaluation;
import org.example.entities.Note;
import org.example.services.NoteService;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.cli.helpers.GlobalVars.prompt;

public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao = NoteDaoImpl.instance;
    private final ElementDeModuleDAO elementDeModuleDao = ElementDeModuleDaoImpl.instance;
    private static final NoteService instance = new NoteServiceImpl();
    private List<Note> draftNotes = new ArrayList<>();

    private NoteServiceImpl() {}

    public static NoteService getInstance() {
        return instance;
    }

    @Override
    public void addNote(Note note) {
        noteDao.create(note);
    }
    @Override
    public void updateNote(Note note) {
        noteDao.update(note);
    }
    @Override
    public List<Note> getNotesByElement(Long elementId) {
        return noteDao.getByElement(elementId);
    }

    @Override
    public List<Note> getNotesByModalite(Long modliteId) {
        return noteDao.getByModalite(modliteId);
    }

    @Override
    public double getElementAverage(List<ModaliteEvaluation> modaliteEvaluations) {
        double avg = 0.0;
        double coef = 0.0;

        for (ModaliteEvaluation modalite : modaliteEvaluations) {
            List<Note> notes = this.getNotesByModalite(modalite.getId());

            boolean validNotes = notes.stream()
                    .anyMatch(note -> !note.isValidation());

            if (!validNotes) {
                double totalNote = notes.stream()
                        .mapToDouble(Note::getNote)
                        .sum();

                double avgTotal = totalNote / notes.size();

                avg += avgTotal * modalite.getCoefficient();
                coef += modalite.getCoefficient();
            }
            else {
                return -1;
            }
        }



        double weightedAverage = avg / coef;

        return weightedAverage;
    }

    @Override
    public  void validateRange(List<Note> notes) throws IllegalArgumentException {
        boolean areAllNotesValid = notes.stream().allMatch(note -> note.getNote() >= 0 && note.getNote() <= 20);
        if (!areAllNotesValid) {
            throw new IllegalArgumentException("Error: All notes must be between 0 and 20.");
        }
    }
    @Override

    public  void validateEdgeNotes(List<Note> notes) throws IllegalStateException {
        boolean hasEdgeNotes = notes.stream().anyMatch(note -> note.getNote() == 0 || note.getNote() == 20);
        if (hasEdgeNotes) {
            String confirmation = prompt("Some notes are 0 or 20. Do you confirm validation? (yes/no)");
            if (!confirmation.equalsIgnoreCase("yes")) {
                throw new IllegalStateException("Validation canceled by the user.");
            }
        }
    }

}