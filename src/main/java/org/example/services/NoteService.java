package org.example.services;

import org.example.entities.Note;
import java.util.List;

public interface NoteService {

    void addNote(Note note);

    void saveDraft();

    void markAbsent(Long studentId, Long modalityId);

    boolean validateElement(Long elementId, boolean confirmZeroTwenty);

    List<Note> getNotesByElement(Long elementId);

    double getElementAverage(Long elementId);

    double getModuleAverage(Long moduleId);

    void exportNotes(Long elementId);
}
