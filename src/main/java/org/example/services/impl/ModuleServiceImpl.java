package org.example.services.impl;

import org.example.dao.FiliereDao;
import org.example.dao.ModuleDao;
import org.example.dao.impl.FiliereDaoImpl;
import org.example.dao.impl.ModuleDaoImpl;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.services.FiliereService;
import org.example.services.ModuleService;

import java.sql.SQLException;
import java.util.List;

public class ModuleServiceImpl implements ModuleService {
    private static ModuleDao moduleDao = ModuleDaoImpl.instance;
    public static final ModuleService instance = new ModuleServiceImpl();

    private ModuleServiceImpl() {
    }

    @Override
    public Module findByName(String name) {
       return moduleDao.findByName(name);
    }

    @Override
    public Module create(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Filiere cannot be null");
        }
        if (module.getNom() == null || module.getNom().isEmpty()) {
            throw new IllegalArgumentException("Filiere name cannot be null or empty");
        }
        if (module.getCode() == null || module.getCode().isEmpty()) {
            throw new IllegalArgumentException("Filiere code cannot be null or empty");
        }
        if (module.getSemestre() == null || module.getSemestre().name().isEmpty()) {
            throw new IllegalArgumentException("Filiere semestre cannot be null or empty");
        }
        return moduleDao.create(module);
    }

    @Override
    public void update(Module module) {
        if (module == null) {
            throw new IllegalArgumentException("Filiere cannot be null");
        }
        if (module.getId() <= 0) {
            throw new IllegalArgumentException("Invalid Module  ID");
        }
        if (module.getNom() == null || module.getNom().isEmpty()) {
            throw new IllegalArgumentException("Filiere name cannot be null or empty");
        }
        if (module.getCode() == null || module.getCode().isEmpty()) {
            throw new IllegalArgumentException("Filiere code cannot be null or empty");
        }
        if (module.getSemestre() == null || module.getSemestre().name().isEmpty()) {
            throw new IllegalArgumentException("Filiere semestre cannot be null or empty");
        }
        moduleDao.update(module);

    }

    @Override
    public void delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Module ID");
        }
        moduleDao.delete(id);
    }

    @Override
    public Module findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid Module ID");
        }
        Module module = moduleDao.findById(id);
        if (module == null) {
            throw new IllegalStateException("Module not found for ID: " + id);
        }
        return module;
    }

    @Override
    public List<Module> findAll() {
        List<Module> Modules = moduleDao.findAll();
        if (Modules == null || Modules.isEmpty()) {
            throw new IllegalStateException("No Modules found in the database");
        }
        return Modules;
    }
}
