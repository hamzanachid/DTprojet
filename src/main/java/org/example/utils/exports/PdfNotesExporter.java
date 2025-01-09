package org.example.utils.exports;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.example.entities.ElementDeModule;
import org.example.entities.Etudiant;
import org.example.entities.ModaliteEvaluation;
import org.example.entities.Note;
import org.example.utils.cli.helpers.GlobalVars;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

class PdfNotesExporter implements NotesExporter {
    @Override
    public void export(ElementDeModule element, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Notes of " + element.getNom())
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            List<ModaliteEvaluation> modalities = GlobalVars.modaliteEvaluationService.findByElement(element);
            List<Etudiant> students = GlobalVars.etudiantService.findEtudiantByElement(element.getId());

            float[] columnWidths = new float[modalities.size() + 1];
            for (int i = 0; i < columnWidths.length; i++) {
                columnWidths[i] = 1;
            }

            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("Student").setBold()));

            for (ModaliteEvaluation modality : modalities) {
                table.addCell(new Cell().add(new Paragraph(modality.getModaliteEvaluationType().name()).setBold()));
            }

            for (Etudiant student : students) {
                table.addCell(new Cell().add(new Paragraph(student.getFirstName() + " " + student.getLastName())));
                for (ModaliteEvaluation modality : modalities) {
                    List<Note> notes = GlobalVars.noteService.getNotesByModalite(modality.getId());
                    Note studentNote = notes.stream()
                            .filter(note -> note.getEtudiant().getId().equals(student.getId()))
                            .findFirst()
                            .orElse(null);
                    if (studentNote != null) {
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(studentNote.getNote()))));
                    } else {
                        table.addCell(new Cell().add(new Paragraph("N/A")));
                    }
                }
            }

            document.add(table);
            document.close();
            System.out.println("PDF generated successfully: " + filePath);
        } catch (IOException e) {
            System.err.println("Error while generating PDF: " + e.getMessage());
        }
    }
}
