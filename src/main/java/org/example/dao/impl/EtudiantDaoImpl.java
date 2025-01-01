package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.EtudiantDao;
import org.example.entities.Etudiant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDaoImpl implements EtudiantDao {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();
    public final static EtudiantDao instance = new EtudiantDaoImpl();

    private EtudiantDaoImpl() {
    }

    @Override
    public Etudiant create(Etudiant etudiant) {
        String sql = "INSERT INTO etudiant (first_name, last_name, matricule, filiere_id) VALUES (?, ?, ?, ?) RETURNING ID";
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, etudiant.getFirstName());
            stmt.setString(2, etudiant.getLastName());
            stmt.setString(3, etudiant.getMatricule());
            stmt.setLong(4, etudiant.getFiliere().getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                etudiant.setId(rs.getLong(1));
                return etudiant;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return etudiant;
    }

    // TODO : I think we need to add other updateBy_something
    @Override
    public boolean update(Etudiant etudiant, Etudiant newEtudiant) {
        String sql = "UPDATE etudiant SET first_name = ? , last_name = ?, matricule = ?, filiere_id = ? WHERE id = ?";
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newEtudiant.getFirstName());
            stmt.setString(2, newEtudiant.getLastName());
            stmt.setString(3, newEtudiant.getMatricule());
            stmt.setLong(4, newEtudiant.getFiliere().getId());

            ResultSet rs = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM etudiant WHERE id = ?";
        Connection conn;
        try {
            conn = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public Etudiant findById(Long id) {
        String sql = "SELECT * etudiant WHERE id = ?";
        Connection conn;
        Etudiant etudiant = null;
        try {
            conn = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                etudiant = mapResultSetToEtudiant(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return etudiant;
    }

    @Override
    public List<Etudiant> findByFiliere(Long filiereId) {
        String sql = "SELECT * FROM etudiant WHERE filiere_id = ?";
        Connection conn;
        List<Etudiant> etudiantList = new ArrayList<>();
        try {
            conn = connectionManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user", e);
        }
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, filiereId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                etudiantList.add(mapResultSetToEtudiant(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return etudiantList;
    }

    private Etudiant mapResultSetToEtudiant(ResultSet rs) throws SQLException {
        // TODO : I need to replace "null" by filiere.getById
        return new Etudiant(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("matricule"),
                null
        );
    }
}
