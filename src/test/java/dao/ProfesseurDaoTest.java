package dao;

import org.example.dao.ProfesseurDao;
import org.example.dao.impl.ProfesseurDaoImpl;
import org.example.entities.Professeur;
import org.example.enums.Role;
import org.example.config.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class ProfesseurDaoTest {

  private ProfesseurDao professeurDao;

  @BeforeEach
  public void setup() {
    // Initialize the DAO implementation before each test
    professeurDao = ProfesseurDaoImpl.instance;
  }

  @Test
  @DisplayName("Should create a professor successfully")
  public void testCreateProfesseur() {
    // Prepare a new Professeur
    Professeur professeur = new Professeur(
      null,  // id is not set yet
      "johndoe",
      "password123",
      Role.PROFESSEUR,
      "Doe",
      "John",
      "Mathematics",
      "MAT123",
      null // elementsDeModule is null for simplicity
    );

    // Create the Professeur
    Professeur createdProfesseur = professeurDao.create(professeur);

    // Assert that the created Professeur is not null and has an id
    assertNotNull(createdProfesseur);
    assertNotNull(createdProfesseur.getId());

    // Fetch the inserted Professeur from the database to verify
    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      String sql = "SELECT * FROM utilisateur WHERE id = " + createdProfesseur.getId();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      if (resultSet.next()) {
        assertEquals("johndoe", resultSet.getString("login"));
        assertEquals("password123", resultSet.getString("mot_de_passe"));
        assertEquals("PROFESSEUR", resultSet.getString("role"));
      }

      // Verify the corresponding 'professeur' data
      sql = "SELECT * FROM professeur WHERE id = " + createdProfesseur.getId();
      resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        assertEquals("Doe", resultSet.getString("nom"));
        assertEquals("John", resultSet.getString("prenom"));
        assertEquals("Mathematics", resultSet.getString("specialite"));
        assertEquals("MAT123", resultSet.getString("code"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception occurred while testing createProfesseur method: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Should find a professor by ID successfully")
  public void testFindById() {
    // Prepare the test data: Insert a professor into the database
    Long existingId = null;

    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      // Insert into the 'utilisateur' table
      String utilisateurSql = "INSERT INTO utilisateur (login, mot_de_passe, role) " +
        "VALUES ('janedoe', 'securepassword', 'PROFESSEUR') RETURNING id";
      try (Statement utilisateurStmt = connection.createStatement();
           ResultSet utilisateurRs = utilisateurStmt.executeQuery(utilisateurSql)) {
        if (utilisateurRs.next()) {
          existingId = utilisateurRs.getLong("id");
        }
      }

      // Insert into the 'professeur' table
      String professeurSql = "INSERT INTO professeur (id, nom, prenom, specialite, code) " +
        "VALUES (" + existingId + ", 'Doe', 'Jane', 'Physics', 'PHY123')";
      try (Statement professeurStmt = connection.createStatement()) {
        professeurStmt.executeUpdate(professeurSql);
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Failed to set up test data: " + e.getMessage());
    }

    // Call the findById method
    Optional<Professeur> result = professeurDao.findById(existingId);

    // Assertions
    assertTrue(result.isPresent(), "Professeur should be found in the database");
    Professeur professeur = result.get();
    assertEquals(existingId, professeur.getId(), "The ID should match the one inserted");
    assertEquals("janedoe", professeur.getLogin(), "The login should match the one inserted");
    assertEquals("securepassword", professeur.getMotDePasse(), "The password should match the one inserted");
    assertEquals(Role.PROFESSEUR, professeur.getRole(), "The role should match the one inserted");
    assertEquals("Doe", professeur.getNom(), "The name should match the one inserted");
    assertEquals("Jane", professeur.getPrenom(), "The prenom should match the one inserted");
    assertEquals("Physics", professeur.getSpecialite(), "The specialite should match the one inserted");
    assertEquals("PHY123", professeur.getCode(), "The code should match the one inserted");
  }

  @Test
  @DisplayName("Should find a professor by login successfully")
  public void testFindByLogin() {
    // Prepare the test data: Insert a professor into the database
    String login = "janedoe";

    Long existingId = null;

    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      // Insert into the 'utilisateur' table
      String utilisateurSql = "INSERT INTO utilisateur (login, mot_de_passe, role) " +
        "VALUES ('janedoe', 'securepassword', 'PROFESSEUR') RETURNING id";
      try (Statement utilisateurStmt = connection.createStatement();
           ResultSet utilisateurRs = utilisateurStmt.executeQuery(utilisateurSql)) {
        if (utilisateurRs.next()) {
          existingId = utilisateurRs.getLong("id");
        }
      }

      // Insert into the 'professeur' table
      String professeurSql = "INSERT INTO professeur (id, nom, prenom, specialite, code) " +
        "VALUES (" + existingId + ", 'Doe', 'Jane', 'Physics', 'PHY123')";
      try (Statement professeurStmt = connection.createStatement()) {
        professeurStmt.executeUpdate(professeurSql);
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Failed to set up test data: " + e.getMessage());
    }

    // Call the findByLogin method
    Optional<Professeur> result = professeurDao.findByLogin(login);

    // Assertions
    assertTrue(result.isPresent(), "Professeur should be found by login");
    Professeur professeur = result.get();
    assertEquals(existingId, professeur.getId(), "The ID should match the one inserted");
    assertEquals(login, professeur.getLogin(), "The login should match the one inserted");
    assertEquals("securepassword", professeur.getMotDePasse(), "The password should match the one inserted");
    assertEquals(Role.PROFESSEUR, professeur.getRole(), "The role should match the one inserted");
    assertEquals("Doe", professeur.getNom(), "The name should match the one inserted");
    assertEquals("Jane", professeur.getPrenom(), "The prenom should match the one inserted");
    assertEquals("Physics", professeur.getSpecialite(), "The specialite should match the one inserted");
    assertEquals("PHY123", professeur.getCode(), "The code should match the one inserted");
  }

  @Test
  @DisplayName("Should find all professors successfully")
  public void testFindAll() {
    // Prepare the test data: Insert professors into the database
    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      // Insert into 'utilisateur' table
      String utilisateurSql = "INSERT INTO utilisateur (login, mot_de_passe, role) " +
        "VALUES ('professor1', 'password1', 'PROFESSEUR'), " +
        "('professor2', 'password2', 'PROFESSEUR') RETURNING id";
      try (Statement utilisateurStmt = connection.createStatement();
           ResultSet utilisateurRs = utilisateurStmt.executeQuery(utilisateurSql)) {
        while (utilisateurRs.next()) {
          Long utilisateurId = utilisateurRs.getLong("id");

          // Insert into 'professeur' table for each user
          String professeurSql = "INSERT INTO professeur (id, nom, prenom, specialite, code) " +
            "VALUES (" + utilisateurId + ", 'Doe', 'John', 'Mathematics', 'MAT123')";
          try (Statement professeurStmt = connection.createStatement()) {
            professeurStmt.executeUpdate(professeurSql);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Failed to set up test data: " + e.getMessage());
    }

    // Call the findAll method
    List<Professeur> result = professeurDao.findAll();

    // Assertions
    assertNotNull(result, "The result should not be null");
    assertTrue(result.size() >= 2, "At least two professors should be found");
    for (Professeur professeur : result) {
      assertNotNull(professeur.getId(), "Professeur ID should not be null");
      assertNotNull(professeur.getLogin(), "Professeur login should not be null");
      assertNotNull(professeur.getNom(), "Professeur name should not be null");
      assertNotNull(professeur.getPrenom(), "Professeur first name should not be null");
    }
  }

  @Test
  @DisplayName("Should find professors by role successfully")
  public void testFindByRole() {
    // Prepare test data: Insert professors with different roles into the database
    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      // Insert two professors with role 'PROFESSEUR' and one with another role
      String utilisateurSql = "INSERT INTO utilisateur (login, mot_de_passe, role) " +
        "VALUES ('professor1', 'password1', 'PROFESSEUR'), " +
        "('professor2', 'password2', 'PROFESSEUR'), " +
        "('professor3', 'password3', 'ADMIN') RETURNING id";
      try (Statement utilisateurStmt = connection.createStatement();
           ResultSet utilisateurRs = utilisateurStmt.executeQuery(utilisateurSql)) {
        while (utilisateurRs.next()) {
          Long utilisateurId = utilisateurRs.getLong("id");

          // Insert into 'professeur' table for each user
          String professeurSql = "INSERT INTO professeur (id, nom, prenom, specialite, code) " +
            "VALUES (" + utilisateurId + ", 'Doe', 'John', 'Mathematics', 'MAT123')";
          try (Statement professeurStmt = connection.createStatement()) {
            professeurStmt.executeUpdate(professeurSql);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Failed to set up test data: " + e.getMessage());
    }

    // Call the findByRole method for 'PROFESSEUR' role
    List<Professeur> result = professeurDao.findByRole(Role.PROFESSEUR);

    // Assertions
    assertNotNull(result, "The result should not be null");
    assertTrue(result.size() >= 2, "At least two professors with 'PROFESSEUR' role should be found");
    for (Professeur professeur : result) {
      assertEquals(Role.PROFESSEUR, professeur.getRole(), "The professor role should be 'PROFESSEUR'");
    }
  }

  @Test
  @DisplayName("Should find professors by nom successfully")
  public void testFindByNom() {
    // Prepare test data: Insert professors with different noms into the database
    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      // Insert two professors with the same nom 'Doe' and one with a different nom
      String utilisateurSql = "INSERT INTO utilisateur (login, mot_de_passe, role) " +
        "VALUES ('professor1', 'password1', 'PROFESSEUR'), " +
        "('professor2', 'password2', 'PROFESSEUR'), " +
        "('professor3', 'password3', 'PROFESSEUR') RETURNING id";
      try (Statement utilisateurStmt = connection.createStatement();
           ResultSet utilisateurRs = utilisateurStmt.executeQuery(utilisateurSql)) {
        while (utilisateurRs.next()) {
          Long utilisateurId = utilisateurRs.getLong("id");

          // Insert into 'professeur' table for each user
          String professeurSql = "INSERT INTO professeur (id, nom, prenom, specialite, code) " +
            "VALUES (" + utilisateurId + ", 'Doe', 'John', 'Mathematics', 'MAT123')";
          try (Statement professeurStmt = connection.createStatement()) {
            professeurStmt.executeUpdate(professeurSql);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Failed to set up test data: " + e.getMessage());
    }

    // Call the findByNom method for 'Doe' as the nom
    List<Professeur> result = professeurDao.findByNom("Doe");

    // Assertions
    assertNotNull(result, "The result should not be null");
    assertTrue(result.size() >= 2, "At least two professors with 'Doe' as nom should be found");
    for (Professeur professeur : result) {
      assertEquals("Doe", professeur.getNom(), "The professor nom should be 'Doe'");
    }
  }

  @Test
  @DisplayName("Should update a professor successfully")
  public void testUpdateProfesseur() {
    // First, create a professor
    Professeur professeur = new Professeur(
      null,  // id will be assigned after creation
      "janedoe",
      "password456",
      Role.PROFESSEUR,
      "Doe",
      "Jane",
      "Physics",
      "PHY456",
      null // elementsDeModule is null for simplicity
    );

    // Create the professor in the database
    Professeur createdProfesseur = professeurDao.create(professeur);

    // Modify the professor details
    createdProfesseur.setLogin("janedoe_updated");
    createdProfesseur.setMotDePasse("newpassword123");
    createdProfesseur.setNom("DoeUpdated");
    createdProfesseur.setPrenom("JaneUpdated");
    createdProfesseur.setSpecialite("Advanced Physics");
    createdProfesseur.setCode("PHY789");

    // Update the professor in the database
    Professeur updatedProfesseur = professeurDao.update(createdProfesseur);

    // Assert that the update was successful
    assertNotNull(updatedProfesseur, "The updated professor should not be null");
    assertEquals("janedoe_updated", updatedProfesseur.getLogin(), "Login should be updated");
    assertEquals("newpassword123", updatedProfesseur.getMotDePasse(), "Password should be updated");
    assertEquals("DoeUpdated", updatedProfesseur.getNom(), "Nom should be updated");
    assertEquals("JaneUpdated", updatedProfesseur.getPrenom(), "Prenom should be updated");
    assertEquals("Advanced Physics", updatedProfesseur.getSpecialite(), "Specialite should be updated");
    assertEquals("PHY789", updatedProfesseur.getCode(), "Code should be updated");

    // Verify the updated data in the database
    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      String sql = "SELECT * FROM utilisateur WHERE id = " + updatedProfesseur.getId();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      if (resultSet.next()) {
        assertEquals("janedoe_updated", resultSet.getString("login"));
        assertEquals("newpassword123", resultSet.getString("mot_de_passe"));
        assertEquals("PROFESSEUR", resultSet.getString("role"));
      }

      // Verify the corresponding 'professeur' data
      sql = "SELECT * FROM professeur WHERE id = " + updatedProfesseur.getId();
      resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        assertEquals("DoeUpdated", resultSet.getString("nom"));
        assertEquals("JaneUpdated", resultSet.getString("prenom"));
        assertEquals("Advanced Physics", resultSet.getString("specialite"));
        assertEquals("PHY789", resultSet.getString("code"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception occurred while testing updateProfesseur method: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Should update password successfully")
  public void testUpdateMotDePasse() {
    // First, create a professor
    Professeur professeur = new Professeur(
      null,  // id will be assigned after creation
      "janedoe",
      "password456",
      Role.PROFESSEUR,
      "Doe",
      "Jane",
      "Physics",
      "PHY456",
      null // elementsDeModule is null for simplicity
    );

    // Create the professor in the database
    Professeur createdProfesseur = professeurDao.create(professeur);

    // Update the password for the created professor
    String newPassword = "newpassword123";
    boolean isUpdated = professeurDao.updateMotDePasse(createdProfesseur.getId(), newPassword);

    // Assert that the password update was successful
    assertTrue(isUpdated, "The password should be updated successfully");

    // Verify the updated password in the database
    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      String sql = "SELECT * FROM utilisateur WHERE id = " + createdProfesseur.getId();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      if (resultSet.next()) {
        assertEquals(newPassword, resultSet.getString("mot_de_passe"), "The password should be updated in the database");
      }

    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception occurred while testing updateMotDePasse method: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Should delete professor successfully")
  public void testDeleteProfesseur() {
    // First, create a professor to be deleted
    Professeur professeur = new Professeur(
      null,  // id will be assigned after creation
      "janedoe",
      "password123",
      Role.PROFESSEUR,
      "Doe",
      "Jane",
      "Chemistry",
      "CHM456",
      null // elementsDeModule is null for simplicity
    );

    // Create the professor in the database
    Professeur createdProfesseur = professeurDao.create(professeur);

    // Delete the created professor by id
    boolean isDeleted = professeurDao.delete(createdProfesseur.getId());

    // Assert that the professor was successfully deleted
    assertTrue(isDeleted, "The professor should be deleted successfully");

    // Verify the deletion by checking if the professor record still exists in the database
    try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
      String sql = "SELECT * FROM utilisateur WHERE id = " + createdProfesseur.getId();
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);

      // Assert that no record was found for the professor in 'utilisateur' table
      assertFalse(resultSet.next(), "The professor's record should be deleted from the utilisateur table");

      // Verify the corresponding 'professeur' record is also deleted
      sql = "SELECT * FROM professeur WHERE id = " + createdProfesseur.getId();
      resultSet = statement.executeQuery(sql);
      assertFalse(resultSet.next(), "The professor's record should be deleted from the professeur table");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception occurred while testing delete method: " + e.getMessage());
    }
  }

  @Test
  @DisplayName("Should authenticate professor successfully")
  public void testAuthenticateProfesseur() {
    // First, create a professor to authenticate
    Professeur professeur = new Professeur(
      null,  // id will be assigned after creation
      "janedoe",
      "password123",
      Role.PROFESSEUR,
      "Doe",
      "Jane",
      "Physics",
      "PHY789",
      null // elementsDeModule is null for simplicity
    );

    // Create the professor in the database
    Professeur createdProfesseur = professeurDao.create(professeur);

    // Authenticate using the created professor's login and password
    Optional<Professeur> authenticatedProfesseur = professeurDao.authenticate("janedoe", "password123");

    // Assert that the professor was authenticated successfully
    assertTrue(authenticatedProfesseur.isPresent(), "The professor should be authenticated successfully");

    // Verify the authenticated professor's details
    Professeur professeurFromDb = authenticatedProfesseur.get();
    assertEquals("janedoe", professeurFromDb.getLogin());
    assertEquals("password123", professeurFromDb.getMotDePasse());
    assertEquals("PROFESSEUR", professeurFromDb.getRole().name());
    assertEquals("Jane", professeurFromDb.getPrenom());
    assertEquals("Doe", professeurFromDb.getNom());
    assertEquals("Physics", professeurFromDb.getSpecialite());
    assertEquals("PHY789", professeurFromDb.getCode());
  }

  @Test
  @DisplayName("Should return empty when professor not found")
  public void testAuthenticateProfesseurNotFound() {
    // Try authenticating with incorrect login or password
    Optional<Professeur> authenticatedProfesseur = professeurDao.authenticate("wronglogin", "wrongpassword");

    // Assert that the result is empty
    assertFalse(authenticatedProfesseur.isPresent(), "No professor should be found with the incorrect credentials");
  }

  @Test
  @DisplayName("Should return true if the login exists")
  public void testExistsByLoginExists() {
    // Create a Professeur to ensure the login exists
    Professeur professeur = new Professeur(
      null,  // id will be assigned after creation
      "uniqueLogin123",
      "password123",
      Role.PROFESSEUR,
      "Doe",
      "John",
      "Mathematics",
      "MAT123",
      null // elementsDeModule is null for simplicity
    );

    // Create the Professeur in the database
    professeurDao.create(professeur);

    // Check if the login exists
    boolean exists = professeurDao.existsByLogin("uniqueLogin123");

    // Assert that the login exists
    assertTrue(exists, "The login should exist in the database");
  }

  @Test
  @DisplayName("Should return false if the login does not exist")
  public void testExistsByLoginNotFound() {
    // Check for a login that doesn't exist
    boolean exists = professeurDao.existsByLogin("nonExistentLogin");

    // Assert that the login does not exist
    assertFalse(exists, "The login should not exist in the database");
  }

  @Test
  @DisplayName("Should save all professors successfully")
  public void testSaveAllProfesseurs() {
    // Prepare a list of new Professeurs
    List<Professeur> professeurs = List.of(
      new Professeur(
        null,  // id will be assigned after creation
        "johndoe1",
        "password123",
        Role.PROFESSEUR,
        "Doe1",
        "John1",
        "Mathematics1",
        "MAT1231",
        null // elementsDeModule is null for simplicity
      ),
      new Professeur(
        null,  // id will be assigned after creation
        "johndoe2",
        "password123",
        Role.PROFESSEUR,
        "Doe2",
        "John2",
        "Physics2",
        "PHY1232",
        null // elementsDeModule is null for simplicity
      )
    );

    // Save the list of Professeurs
    List<Professeur> savedProfesseurs = professeurDao.saveAll(professeurs);

    // Assert that the list is not empty and that each Professeur has an id
    assertNotNull(savedProfesseurs);
    assertFalse(savedProfesseurs.isEmpty());

    // Verify the inserted Professors in the database
    try (Connection connection = DatabaseConnection.getInstance().getConnection();
         Statement statement = connection.createStatement()) {

      for (Professeur savedProfesseur : savedProfesseurs) {
        String sql = "SELECT * FROM utilisateur WHERE id = " + savedProfesseur.getId();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
          assertEquals(savedProfesseur.getLogin(), resultSet.getString("login"));
          assertEquals(savedProfesseur.getMotDePasse(), resultSet.getString("mot_de_passe"));
          assertEquals(savedProfesseur.getRole().name(), resultSet.getString("role"));
        }

        // Verify the corresponding 'professeur' data
        sql = "SELECT * FROM professeur WHERE id = " + savedProfesseur.getId();
        resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
          assertEquals(savedProfesseur.getNom(), resultSet.getString("nom"));
          assertEquals(savedProfesseur.getPrenom(), resultSet.getString("prenom"));
          assertEquals(savedProfesseur.getSpecialite(), resultSet.getString("specialite"));
          assertEquals(savedProfesseur.getCode(), resultSet.getString("code"));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception occurred while testing saveAllProfesseurs method: " + e.getMessage());
    }
  }
}
