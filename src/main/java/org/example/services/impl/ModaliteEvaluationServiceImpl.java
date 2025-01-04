package org.example.services.impl;

import org.example.dao.ModaliteEvaluationDao;
import org.example.dao.impl.ModaliteEvaluationDaoImpl;
import org.example.entities.ElementDeModule;
import org.example.entities.ModaliteEvaluation;
import org.example.services.ModaliteEvaluationService;
import java.util.List;

public class ModaliteEvaluationServiceImpl implements ModaliteEvaluationService {
    private static ModaliteEvaluationDao modaliteEvaluationDao = ModaliteEvaluationDaoImpl.instance;
    public static final ModaliteEvaluationService instance = new ModaliteEvaluationServiceImpl();

    private ModaliteEvaluationServiceImpl() {
    }

    @Override
    public ModaliteEvaluation findById(Long id) {
        return modaliteEvaluationDao.findById(id);
    }

    @Override
    public ModaliteEvaluation create(ModaliteEvaluation Modalite, ElementDeModule elementDeModule) {
        return modaliteEvaluationDao.create(Modalite, elementDeModule);
    }

    @Override
    public List<ModaliteEvaluation> findAll() {
        return modaliteEvaluationDao.findAll();
    }

    @Override
    public List<ModaliteEvaluation> findByElement(ElementDeModule elementDeModule) {
        return modaliteEvaluationDao.findByElement(elementDeModule);
    }

    @Override
    public void delete(Long id) {
        modaliteEvaluationDao.delete(id);
    }

    @Override
    public void update(Long id, ModaliteEvaluation modaliteEvaluation) {
        modaliteEvaluationDao.update(id, modaliteEvaluation);
    }
}
