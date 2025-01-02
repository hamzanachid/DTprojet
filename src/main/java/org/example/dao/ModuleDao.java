package org.example.dao;


import org.example.entities.Filiere;
import org.example.entities.Module;

import java.sql.SQLException;
import java.util.List;

public interface ModuleDao {
    Module findByName(String name);
    Module create(Module module);
    void update(Module module);
    void delete(Long id);
    Module findById(Long id);
    List<Module> findAll();
}
