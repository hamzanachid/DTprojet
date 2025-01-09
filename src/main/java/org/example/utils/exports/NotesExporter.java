package org.example.utils.exports;

import org.example.entities.ElementDeModule;

public interface NotesExporter {
    void export(ElementDeModule element, String filePath);
}
