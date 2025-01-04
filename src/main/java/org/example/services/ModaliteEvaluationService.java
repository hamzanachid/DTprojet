package org.example.services;

import org.example.entities.ElementDeModule;
import org.example.entities.ModaliteEvaluation;
import java.util.List;

public interface ModaliteEvaluationService {
    ModaliteEvaluation findById(Long id);
    ModaliteEvaluation create(ModaliteEvaluation Modalite, ElementDeModule elementDeModule);
    List<ModaliteEvaluation> findAll();
    List<ModaliteEvaluation> findByElement(ElementDeModule elementDeModule);
    void delete(Long id);
    void update(Long id, ModaliteEvaluation modaliteEvaluation);
}
