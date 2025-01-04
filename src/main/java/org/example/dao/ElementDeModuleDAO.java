package org.example.dao;

import org.example.entities.ElementDeModule;

import java.util.List;

public interface ElementDeModuleDAO {
    ElementDeModule create(ElementDeModule elementDeModule);
    ElementDeModule findById(Long id);
    void update(ElementDeModule elementDeModule);
    void delete(Long id);
    List<ElementDeModule> findAll();
    List<ElementDeModule> findByFiliereId(Long filiereId);
    List<ElementDeModule> findByModuleId(Long moduleId);
    ElementDeModule findByName(String name);
    void updateModuleAverage(Long moduleId, double moduleAverage);
}
