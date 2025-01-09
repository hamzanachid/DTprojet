package org.example.utils.cli.note;


import org.example.entities.ElementDeModule;
import org.example.utils.exports.NotesExporter;
import org.example.utils.exports.NotesExporterFactory;

import static org.example.utils.cli.helpers.GlobalVars.*;

class ExportNotesCommand implements Command {
    private final ElementDeModule element;

    ExportNotesCommand(ElementDeModule element) {
        this.element = element;
    }

    @Override
    public void execute() {
       System.out.println("chose type of file : ");
       System.out.println("1. excel");
       System.out.println("2. pdf ");
       String chose=prompt("enter a number : ");
       if(chose.equals("1")){
           System.out.println("excel is generating ... ");

           String fileType = "xls";
           String filePath = "notes." + fileType;

           NotesExporter exporter = NotesExporterFactory.getExporter(fileType);
           exporter.export(element, filePath);
       }else if(chose.equals("2")){
           System.out.println("pdf is generating ... ");
           String fileType = "pdf";
           String filePath = "notes." + fileType;

           NotesExporter exporter = NotesExporterFactory.getExporter(fileType);
           exporter.export(element, filePath);
       }else{
           System.out.println("number not valid");
       }

    }
}
