package org.example.services.impl;

import org.example.dao.ElementDeModuleDAO;
import org.example.dao.NoteDao;
import org.example.dao.impl.ElementDeModuleDaoImpl;
import org.example.dao.impl.NoteDaoImpl;
import org.example.entities.ElementDeModule;
import org.example.entities.Note;
import org.example.services.NoteService;
import java.util.ArrayList;
import java.util.List;

public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao = NoteDaoImpl.instance;
    private final ElementDeModuleDAO elementDeModuleDao = ElementDeModuleDaoImpl.instance;
    private static final NoteService instance = new NoteServiceImpl();
    private List<Note> draftNotes = new ArrayList<>();

    private NoteServiceImpl() {}

    public static NoteService getInstance() {
        return instance;
    }

    public boolean  isValidNote(Note note) {
        if (note.getNote() < 0 || note.getNote() > 20) {
            return false;
        }
        return true;
    }

    public boolean validateDraft(List<Note> draftNotes) {
        // true if all true
        return draftNotes.stream().allMatch(c -> isValidNote(c));
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
        if(!validateDraft(draftNotes)){
            return;
        }
        for (Note note : draftNotes) {
            noteDao.create(note);
        }
        draftNotes.clear();
    }

    @Override
    public void markAbsent(Long studentId, Long modalityId) {

    }

    @Override
    public boolean validateElement(Long elementId, boolean confirmZeroTwenty) {
        return false;
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
    public List<Note> getNotesByModalite(Long modliteId) {
        return noteDao.getByModalite(modliteId);
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

    }

}