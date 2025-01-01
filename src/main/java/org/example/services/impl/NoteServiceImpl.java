package org.example.services.impl;

import org.example.dao.ElementDeModuleDao;
import org.example.dao.NoteDao;
import org.example.dao.impl.ElementDeModuleDaoImpl;
import org.example.dao.impl.NoteDaoImpl;
import org.example.entities.ElementDeModule;
import org.example.entities.Note;
import org.example.services.NoteService;
import org.example.utils.ExcelExporter;
import java.util.ArrayList;
import java.util.List;

public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao = NoteDaoImpl.getInstance();
    private final ElementDeModuleDao elementDeModuleDao = ElementDeModuleDaoImpl.getInstance();
    private static final NoteService instance = new NoteServiceImpl();
    private List<Note> draftNotes = new ArrayList<>();

    private NoteServiceImpl() {}

    public static NoteService getInstance() {
        return instance;
    }

    @Override
    public void addNote(Note note) {
        if (note.getNote() < 0 || note.getNote() > 20) {
            throw new RuntimeException("Note must be between 0 and 20");
        }
        draftNotes.add(note);
    }

    @Override
    public void saveDraft() {
        for (Note note : draftNotes) {
            if (note.getId() ) {
                noteDao.create(note);
            } else {
                noteDao.update(note);
            }
        }
    }

    public void cancelDraft() {
        draftNotes.clear();
    }

    @Override
    public void markAbsent(Long studentId, Long modalityId) {
        Note note = new Note();
        note.setNote(0.0);
        note.setAbsence(true);
        note.getEtudiant().setId(studentId);
        note.getModaliteEvaluation().setId(modalityId);
        noteDao.create(note);
    }

    @Override
    public boolean validateElement(Long elementId, boolean confirmZeroTwenty) {
        ElementDeModule element = elementDeModuleDao.getById(elementId);
        if (element == null) return false;

        List<Note> notes = noteDao.getByElement(elementId);
        if (!checkNotes(notes, confirmZeroTwenty)) return false;

        double average = noteDao.calculateElementAverage(elementId);
        element.setMoyenne(average);
        element.setEstValide(true);
        elementDeModuleDao.update(element);

        if (checkModuleValidation(element.getModule().getId())) {
            updateModuleAverage(element.getModule().getId());
        }

        return true;
    }

    private boolean checkNotes(List<Note> notes, boolean confirmZeroTwenty) {
        for (Note note : notes) {
            if (note == null) return false;
            if (!confirmZeroTwenty && !note.isAbsence() && (note.getNote() == 0 || note.getNote() == 20)) {
                return false;
            }
        }
        return true;
    }

    private void updateModuleAverage(Long moduleId) {
        double moduleAverage = noteDao.calculateModuleAverage(moduleId);
        elementDeModuleDao.updateModuleAverage(moduleId, moduleAverage);
    }

    @Override
    public List<Note> getNotesByElement(Long elementId) {
        return noteDao.getByElement(elementId);
    }

    @Override
    public double getElementAverage(Long elementId) {
        return noteDao.calculateElementAverage(elementId);
    }

    @Override
    public double getModuleAverage(Long moduleId) {
        return noteDao.calculateModuleAverage(moduleId);
    }

    @Override
    public void exportNotes(Long elementId) {
        ElementDeModule element = elementDeModuleDao.getById(elementId);
        if (!element.isEstValide()) throw new RuntimeException("Element must be validated first");
        List<Note> notes = noteDao.getByElement(elementId);
        ExcelExporter.exportNotes(notes, element);
    }
}