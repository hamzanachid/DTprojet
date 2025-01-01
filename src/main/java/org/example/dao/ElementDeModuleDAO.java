package org.example.dao;

import org.example.entities.ElementDeModule;

import java.util.List;

public interface ElementDeModuleDAO {
    ElementDeModule create(ElementDeModule ElementDeModule);
    ElementDeModule findById(Long id);
    void update(ElementDeModule ElementDeModule);
    void delete(Long id);
    List<ElementDeModule> findAll();
}
