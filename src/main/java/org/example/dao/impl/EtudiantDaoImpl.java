package org.example.dao.impl;

import org.example.config.DatabaseConnection;
import org.example.dao.EtudiantDao;
import org.example.entities.Etudiant;
import org.example.services.FiliereService;
import org.example.services.impl.FiliereServiceImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDaoImpl implements EtudiantDao {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();
    public final static EtudiantDao instance = new EtudiantDaoImpl();
    private final FiliereService filiereService = FiliereServiceImpl.instance;
    private EtudiantDaoImpl() {
    }

    @Override
    public Etudiant create(Etudiant etudiant) {
        try (Connection conn = connectionManager.getConnection())
        {
            String sql = "INSERT INTO etudiant (first_name, last_name, matricule, filiere_id) VALUES (?, ?, ?, ?) RETURNING ID";
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
        }
        return etudiant;
    }

    // TODO : I think we need to add other updateBy_something
    @Override
    public boolean update(Etudiant etudiant, Etudiant newEtudiant) {
        try (Connection conn = connectionManager.getConnection())
        {
            String sql = "UPDATE etudiant SET first_name = ? , last_name = ?, matricule = ?, filiere_id = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newEtudiant.getFirstName());
            stmt.setString(2, newEtudiant.getLastName());
            stmt.setString(3, newEtudiant.getMatricule());
            stmt.setLong(4, newEtudiant.getFiliere().getId());
            stmt.setLong(5, etudiant.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection conn = connectionManager.getConnection())
        {
            String sql = "DELETE FROM etudiant WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    @Override
    public Etudiant findById(Long id) {
        try(Connection conn = connectionManager.getConnection())
        {
            String sql = "SELECT * FROM Etudiant WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEtudiant(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user", e);
        }
        return null;
    }

    @Override
    public List<Etudiant> findByFiliere(Long filiereId) {
        List<Etudiant> etudiantList = new ArrayList<>();
        try(Connection conn = connectionManager.getConnection())
        {
            String sql = "SELECT * FROM etudiant WHERE filiere_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, filiereId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                etudiantList.add(mapResultSetToEtudiant(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
        return etudiantList;
    }

    @Override
    public List<Etudiant> findEtudiantByElement(Long element_id) {
        String sql = "SELECT \n" +
                "    e.id  AS  id,\n" +
                "    e.nom AS  nom,\n" +
                "    e.prenom AS  prenom,\n" +
                "    e.matricule AS matricule ,\n" +
                "\te.filiere_id as filiere_id\n" +
                "FROM \n" +
                "    Etudiant e\n" +
                "JOIN \n" +
                "    EtudiantModule em ON e.id = em.etudiant_id\n" +
                "JOIN \n" +
                "    Modules m ON em.module_id = m.id\n" +
                "JOIN \n" +
                "    ElementDeModule edm ON edm.module_id = m.id\n" +
                "WHERE \n" +
                "    edm.id = ?;";
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
            stmt.setLong(1, element_id);
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

    @Override
    public List<Etudiant> findAll() {
        List<Etudiant> etudiantList = new ArrayList<>();
        try(Connection conn = connectionManager.getConnection())
        {
            String sql = "SELECT * FROM etudiant";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                etudiantList.add(mapResultSetToEtudiant(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
        return etudiantList;
    }

    @Override
    public Etudiant getByMatricule(String matricule) {
        try(Connection conn = connectionManager.getConnection())
        {
            String sql = "SELECT * FROM etudiant WHERE matricule = ?";
            Etudiant etudiant = null;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, matricule);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEtudiant(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
        return null;
    }

    private Etudiant mapResultSetToEtudiant(ResultSet rs) throws SQLException {
        return new Etudiant(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("matricule"),
                filiereService.getById(rs.getLong("filiere_id"))
        );
    }
}
