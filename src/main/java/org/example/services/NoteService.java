package org.example.services;

import org.example.entities.ModaliteEvaluation;
import org.example.entities.Note;
import java.util.List;

public interface NoteService {

    void addNote(Note note);
    void updateNote(Note note);

    List<Note> getNotesByElement(Long elementId);

    List<Note> getNotesByModalite(Long modliteId);

    double getElementAverage(List<ModaliteEvaluation> modaliteEvaluations);
    public  void validateRange(List<Note> notes);
    public  void validateEdgeNotes(List<Note> notes);

}
