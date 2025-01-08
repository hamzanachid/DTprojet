package org.example.dao.impl;

import org.example.dao.config.DatabaseConnection;
import org.example.dao.EtudiantDao;
import org.example.dao.NoteDao;
import org.example.entities.Etudiant;
import org.example.entities.Note;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDaoImpl implements NoteDao {
    private final DatabaseConnection connectionManager = DatabaseConnection.getInstance();
    private final EtudiantDao etudiantDao = EtudiantDaoImpl.instance;
    public final static NoteDao instance = new NoteDaoImpl();

    private NoteDaoImpl() {}

    @Override
    public Note create(Note note) {
        String sql = "INSERT INTO note (note, absence, validation, is_draft, etudiant_id, modalite_evaluation_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, note.getNote());
            stmt.setBoolean(2, note.isAbsence());
            stmt.setBoolean(3, note.isValidation());
            stmt.setBoolean(4, true);
            stmt.setLong(5, note.getEtudiant().getId());
            stmt.setLong(6, note.getModaliteEvaluation().getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                note.setId(rs.getInt(1));
                return note;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating note", e);
        }
        return null;
    }

    @Override
    public void update(Note note) {
        String sql = "UPDATE note SET note = ?, absence = ?, validation = ?, is_draft = ? WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, note.getNote());
            stmt.setBoolean(2, note.isAbsence());
            stmt.setBoolean(3, note.isValidation());
            stmt.setBoolean(4, true);
            stmt.setInt(5, note.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating note", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM note WHERE id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting note", e);
        }
    }
    @Override
    public List<Note> getByModalite(Long modaliteId) {
        String sql = "SELECT * FROM note WHERE modalite_evaluation_id = ?";
        List<Note> notes = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, modaliteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(mapResultSetToNote(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving notes by modalite", e);
        }
        return notes;
    }

    @Override
    public List<Note> getByElement(Long elementId) {
        String sql = "SELECT n.* FROM note n JOIN modalite_evaluation m ON n.modalite_evaluation_id = m.id WHERE m.element_module_id = ?";
        List<Note> notes = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, elementId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(mapResultSetToNote(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving notes by element", e);
        }
        return notes;
    }


    private Note mapResultSetToNote(ResultSet rs) throws SQLException {
        Note note = new Note();
        note.setId(rs.getInt("id"));
        note.setNote(rs.getDouble("note"));
        note.setAbsence(rs.getBoolean("absence"));
        note.setValidation(rs.getBoolean("validation"));

        Etudiant etudiant = etudiantDao.findById(rs.getLong("etudiant_id"));
        note.setEtudiant(etudiant);

        // TODO ModaliteEvaluation modaliteEvaluation = modaliteEvaluationDao.getById(rs.getLong("modalite_evaluation_id"));
        note.setModaliteEvaluation(null);
        return note;
    }
}