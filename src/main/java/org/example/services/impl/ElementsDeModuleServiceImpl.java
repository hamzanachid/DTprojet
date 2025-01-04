package org.example.services.impl;

import org.example.dao.ElementDeModuleDAO;
import org.example.dao.FiliereDao;
import org.example.dao.impl.ElementDeModuleDaoImpl;
import org.example.dao.impl.FiliereDaoImpl;
import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.services.ElementDeModuleService;
import org.example.services.FiliereService;

import java.util.List;

public class ElementsDeModuleServiceImpl implements ElementDeModuleService {
    private static ElementDeModuleDAO elementDeModuleDAO = ElementDeModuleDaoImpl.instance;
    public static final ElementsDeModuleServiceImpl instance = new ElementsDeModuleServiceImpl();

    private ElementsDeModuleServiceImpl() {
    }
    @Override
    public ElementDeModule create(ElementDeModule elementDeModule) {
        if (elementDeModule == null) {
            throw new IllegalArgumentException("element De Module cannot be null");
        }
        if (elementDeModule.getNom() == null || elementDeModule.getNom().isEmpty()) {
            throw new IllegalArgumentException("elementDeModule name cannot be null or empty");
        }
        if (elementDeModule.getCoefficient() == 0.0 ) {
            throw new IllegalArgumentException("elementDeModule coefficient cannot be null or empty");
        }
        return elementDeModuleDAO.create(elementDeModule);
    }

    @Override
    public ElementDeModule findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid element de module ID");
        }
        ElementDeModule elementDeModule = elementDeModuleDAO.findById(id);
        if (elementDeModule == null) {
            throw new IllegalStateException("element De Module not found for ID: " + id);
        }
        return elementDeModule;
    }

    @Override
    public void update(ElementDeModule elementDeModule) {
        if (elementDeModule == null || elementDeModule.getId() <= 0) {
            throw new IllegalArgumentException("Invalid Filiere or ID");
        }
        elementDeModuleDAO.update(elementDeModule);
    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid element de module ID");
        }
        elementDeModuleDAO.delete(id);
    }

    @Override
    public List<ElementDeModule> findAll() {
        List<ElementDeModule> elementDeModules = elementDeModuleDAO.findAll();
        if (elementDeModules == null || elementDeModules.isEmpty()) {
            throw new IllegalStateException("No element De Modules found in the database");
        }
        return elementDeModules;
    }

    @Override 
    public List<ElementDeModule> findByProfId(Long id) {
        List<ElementDeModule> elementDeModules = elementDeModuleDAO.findByProfId(id);
        if (elementDeModules == null || elementDeModules.isEmpty()) {
            throw new IllegalStateException("No element De Modules found in the database");
        }
        return elementDeModules; 
    public ElementDeModule findByName(String name) {
        return elementDeModuleDAO.findByName(name); 
    }
}
