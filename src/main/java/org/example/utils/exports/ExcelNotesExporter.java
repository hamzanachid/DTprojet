package org.example.utils.exports;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.example.entities.ElementDeModule;
import org.example.entities.Etudiant;
import org.example.entities.ModaliteEvaluation;
import org.example.entities.Note;
import org.example.utils.cli.helpers.GlobalVars;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

class ExcelNotesExporter implements NotesExporter {
    @Override
    public void export(ElementDeModule element, String filePath) {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Notes");
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Notes of " + element.getNom());

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleStyle.setFont(titleFont);
            titleCell.setCellStyle(titleStyle);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            List<ModaliteEvaluation> modalities = GlobalVars.modaliteEvaluationService.findByElement(element);
            Row headerRow = sheet.createRow(1);
            Cell studentHeader = headerRow.createCell(0);
            studentHeader.setCellValue("Student");
            studentHeader.setCellStyle(createBoldCellStyle(workbook));

            for (int i = 0; i < modalities.size(); i++) {
                Cell modalityHeader = headerRow.createCell(i + 1);
                modalityHeader.setCellValue(modalities.get(i).getModaliteEvaluationType().name());
                modalityHeader.setCellStyle(createBoldCellStyle(workbook));
            }

            List<Etudiant> students = GlobalVars.etudiantService.findEtudiantByElement(element.getId());
            int rowNum = 2;

            for (Etudiant student : students) {
                Row row = sheet.createRow(rowNum++);
                Cell studentCell = row.createCell(0);
                studentCell.setCellValue(student.getFirstName() + " " + student.getLastName());

                for (int i = 0; i < modalities.size(); i++) {
                    ModaliteEvaluation modality = modalities.get(i);
                    List<Note> notes = GlobalVars.noteService.getNotesByModalite(modality.getId());
                    Note studentNote = notes.stream()
                            .filter(note -> note.getEtudiant().getId().equals(student.getId()))
                            .findFirst()
                            .orElse(null);

                    Cell noteCell = row.createCell(i + 1);
                    if (studentNote != null) {
                        noteCell.setCellValue(studentNote.getNote());
                    } else {
                        noteCell.setCellValue("N/A");
                    }
                }
            }

            for (int i = 0; i <= modalities.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                System.out.println("Excel file generated successfully: " + filePath);
            }

        } catch (IOException e) {
            System.err.println("Error while generating Excel file: " + e.getMessage());
        }
    }

    private CellStyle createBoldCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
