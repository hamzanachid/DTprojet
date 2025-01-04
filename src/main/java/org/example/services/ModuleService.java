package org.example.services;

import org.example.entities.Module;

import java.sql.SQLException;
import java.util.List;

public interface ModuleService {
    Module findByName(String name);
    Module create(Module module) ;
    void update(Long id, Module module);
    void delete(Long id);
    Module findById(Long id);
    List<Module> findAll();
}
