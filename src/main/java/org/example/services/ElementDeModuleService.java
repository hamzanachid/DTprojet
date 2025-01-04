package org.example.services;

import org.example.entities.ElementDeModule;

import java.util.List;

public interface ElementDeModuleService {
    ElementDeModule create(ElementDeModule elementDeModule);
    ElementDeModule findById(Long id);
    void update(ElementDeModule elementDeModule);
    void delete(Long id);
    List<ElementDeModule> findAll();
    List<ElementDeModule> findByProfId(Long id);
}
