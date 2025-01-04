package org.example.dao;

import org.example.entities.Note;
import java.util.List;

public interface NoteDao {
    Note create(Note note);
    void update(Note note);
    void delete(Long id);
    Note getById(Long id);
    List<Note> getByEtudiant(Long etudiantId);
    List<Note> getByModalite(Long modaliteId);
    List<Note> getByElement(Long elementId);
    double calculateElementAverage(Long elementId);
    double calculateModuleAverage(Long moduleId);
    void setDraft(Long noteId, boolean isDraft);
}