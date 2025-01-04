package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.ElementDeModuleDAO;
import org.example.dao.FiliereDao;
import org.example.dao.ModuleDao;
import org.example.entities.ElementDeModule;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.enums.Semestre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDaoImpl implements ModuleDao {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();
    public static final ModuleDao instance = new ModuleDaoImpl();
    private ModuleDaoImpl() {
    }

    @Override
    public Module findByName(String name) {
        String sql = "SELECT * FROM module WHERE nom = ?";
        Module module = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                module = mapResultSetToModule(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public Module create(Module module) {
        System.err.println(module);
        String sql = "INSERT INTO module (code, nom, semestre, filiere_id) VALUES (?, ?, CAST(? AS semestre_enum), ?) RETURNING id;";
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, module.getCode());
            statement.setString(2, module.getNom());
            statement.setString(3, module.getSemestre().name());
            statement.setLong(4, module.getFiliere().getId());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    module.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public void update(Long id, Module module) {
        String sql = "UPDATE module SET code = ?, nom = ? , semestre = CAST(? AS semestre_enum), filiere_id = ? WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, module.getCode());
            statement.setString(2, module.getNom());
            statement.setString(3, module.getSemestre().name());
            statement.setLong(4, module.getFiliere().getId());
            statement.setLong(5, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM module WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Module findById(Long id) {
        String sql = "SELECT * FROM module WHERE id = ?";
        Module module = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                module = mapResultSetToModule(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return module;
    }

    @Override
    public List<Module> findAll() {
        String sql = "SELECT * FROM module";
        List<Module> module = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                module.add(mapResultSetToModule(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return module;
    }

    private Module mapResultSetToModule(ResultSet resultSet) throws SQLException {
        Module module = new Module();
        module.setId(resultSet.getLong("id"));
        module.setCode(resultSet.getString("code"));
        module.setNom(resultSet.getString("nom"));

        module.setSemestre(Semestre.valueOf(resultSet.getString("semestre")));

        FiliereDao filiereDao=FiliereDaoImpl.instance;
        Filiere filiere=filiereDao.findById(resultSet.getLong("filiere_id"));
        module.setFiliere(filiere);

        return module;
    }
}
