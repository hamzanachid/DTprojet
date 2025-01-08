package org.example.utils.cli.note;

import org.example.entities.ElementDeModule;
import org.example.entities.ModaliteEvaluation;
import org.example.utils.cli.helpers.GlobalVars;

import java.util.List;

class CalculateAverageCommand implements Command {
    private final ElementDeModule element;

    public CalculateAverageCommand(ElementDeModule element) {
        this.element = element;
    }

    @Override
    public void execute() {
        List<ModaliteEvaluation> modalities = getModalities(element);
        Double average = GlobalVars.noteService.getElementAverage(modalities);
        if (average == -1) {
            System.err.println("Please validate the notes first.");
        } else {
            System.out.println("Average: " + average);
        }
    }
    public List<ModaliteEvaluation> getModalities(ElementDeModule element) {
        System.out.println("List of Modalities:");
        List<ModaliteEvaluation> modalities = GlobalVars.modaliteEvaluationService.findByElement(element);
        return modalities;
    }
}
