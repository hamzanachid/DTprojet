package org.example.dao;

import org.example.entities.Note;
import java.util.List;

public interface NoteDao {
    Note create(Note note);
    void update(Note note);
    void delete(Long id);
    List<Note> getByModalite(Long modaliteId);
    List<Note> getByElement(Long elementId);
}