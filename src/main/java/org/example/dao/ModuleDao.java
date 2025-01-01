package org.example.dao;


import org.example.entities.Module;

import java.sql.SQLException;
import java.util.List;

public interface ModuleDao {
    Module create(Module module) throws SQLException;
    void update(Module module);
    void delete(Long id);
    Module findById(Long id);
    List<Module> findAll();
}
