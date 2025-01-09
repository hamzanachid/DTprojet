package org.example.utils.exports;

import org.example.utils.exports.ExcelNotesExporter;
import org.example.utils.exports.NotesExporter;
import org.example.utils.exports.PdfNotesExporter;

public class NotesExporterFactory {
    public static NotesExporter getExporter(String type) {
        switch (type.toLowerCase()) {
            case "pdf":
                return new PdfNotesExporter();
            case "xls":
                return new ExcelNotesExporter();
            default:
                throw new IllegalArgumentException("Unknown exporter type: " + type);
        }
    }
}