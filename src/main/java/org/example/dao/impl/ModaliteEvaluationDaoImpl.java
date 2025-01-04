package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.ElementDeModuleDAO;
import org.example.dao.ModaliteEvaluationDao;
import org.example.entities.ElementDeModule;
import org.example.entities.ModaliteEvaluation;
import org.example.enums.ModaliteEvaluationType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModaliteEvaluationDaoImpl implements ModaliteEvaluationDao {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();
    public static final ModaliteEvaluationDao instance = new ModaliteEvaluationDaoImpl();
    private ModaliteEvaluationDaoImpl() {}

    @Override
    public ModaliteEvaluation create(ModaliteEvaluation modalite, ElementDeModule elementDeModule) {
        String sql = "INSERT INTO ModaliteEvaluation (type, coefficient, element_module_id) " +
                "VALUES (CAST(? AS modalite_evaluation_type), ?, ?) RETURNING ID";
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, modalite.getModaliteEvaluationType().name());
            statement.setDouble(2, modalite.getCoefficient());
            statement.setLong(3, elementDeModule.getId());
            ResultSet res = statement.executeQuery();
            if(res.next()) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next()) {
                    modalite.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return modalite;
    }

    @Override
    public List<ModaliteEvaluation> findAll() {
        List<ModaliteEvaluation> modaliteEvaluations = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()) {
            String sql = "SELECT * FROM ModaliteEvaluation";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                modaliteEvaluations.add(mapResultSetToModalite(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modaliteEvaluations;
    }

    @Override
    public ModaliteEvaluation findById(Long id) {
        ModaliteEvaluation modaliteEvaluations = null;
        try(Connection connection = connectionManager.getConnection()) {
            String sql = "SELECT * FROM ModaliteEvaluation WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                modaliteEvaluations = mapResultSetToModalite(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modaliteEvaluations;
    }

    @Override
    public List<ModaliteEvaluation> findByElement(ElementDeModule elementDeModule) {
        List<ModaliteEvaluation> modaliteEvaluations = new ArrayList<>();
        try(Connection connection = connectionManager.getConnection()) {
            String sql = "SELECT * FROM ModaliteEvaluation WHERE element_module_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, elementDeModule.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                modaliteEvaluations.add(mapResultSetToModalite(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modaliteEvaluations;
    }

    @Override
    public void delete(Long id) {
        try(Connection connection = connectionManager.getConnection()) {
            String sql = "DELETE FROM ModaliteEvaluation WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, ModaliteEvaluation modaliteEvaluation) {
        String sql = "UPDATE ModaliteEvaluation SET coefficient = ?, element_module_id = ?  WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, modaliteEvaluation.getCoefficient());
            statement.setLong(2, modaliteEvaluation.getElementDeModule().getId());
            statement.setLong(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ModaliteEvaluation mapResultSetToModalite(ResultSet resultSet) throws SQLException {
        ModaliteEvaluation modaliteEvaluation = new ModaliteEvaluation();
        modaliteEvaluation.setId(resultSet.getLong("id"));
        modaliteEvaluation.setModaliteEvaluationType(ModaliteEvaluationType.valueOf(resultSet.getString("modalite_evaluation_type")));
        modaliteEvaluation.setCoefficient(resultSet.getDouble("coefficient"));
        ElementDeModuleDAO elementDeModuleDAO = ElementDeModuleDaoImpl.instance;
        ElementDeModule elementDeModule = elementDeModuleDAO.findById((long) resultSet.getInt("element_module_id"));
        modaliteEvaluation.setElementDeModule(elementDeModule);
        return modaliteEvaluation;
    }
}
