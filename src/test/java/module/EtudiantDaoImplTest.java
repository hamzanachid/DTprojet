package module;

import org.example.dao.EtudiantDao;
import org.example.dao.FiliereDao;
import org.example.dao.impl.EtudiantDaoImpl;
import org.example.dao.impl.FiliereDaoImpl;
import org.example.entities.Etudiant;
import org.example.entities.Filiere;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EtudiantDaoImplTest {
    private final EtudiantDao etudiantDao = EtudiantDaoImpl.instance;
    private final FiliereDao filiereDao = FiliereDaoImpl.instance;
    private Filiere filiere;

    @BeforeEach
    public void createFiliere() {
        this.filiere = Filiere
                .builder()
                .setNom("IID3")
                .setCode("just a code")
                .build();
        this.filiere = filiereDao.create(filiere);
    }

    @AfterEach
    public void deleteFiliere() {
        filiereDao.delete(this.filiere.getId());
    }

    @Test
    void testCreate() {
        Etudiant etudiant = Etudiant.builder()
                .setFirstName("Nachid")
                .setLastName("Hamza")
                .setMatricule("mat123")
                .setFiliere(filiere)
                .build();
        etudiant = etudiantDao.create(etudiant);

        assertNotNull(etudiant.getId());
        assertEquals("Nachid", etudiant.getFirstName());
        assertEquals("Hamza", etudiant.getLastName());
        assertEquals("mat123", etudiant.getMatricule());
        assertEquals("IID3", etudiant.getFiliere().getNom());
    }

    @Test
    void testUpdate() {
        Etudiant etudiant = Etudiant.builder()
                .setFirstName("Nachid")
                .setLastName("Hamza")
                .setMatricule("CHM456")
                .setFiliere(filiere)
                .build();
        etudiant = etudiantDao.create(etudiant);

        Etudiant updatedEtudiant = Etudiant.builder()
                .setFirstName("ilyas")
                .setLastName("blidi")
                .setMatricule("mat123")
                .setFiliere(filiere)
                .build();

        boolean isUpdated = etudiantDao.update(etudiant, updatedEtudiant);
        assertTrue(isUpdated);

        Etudiant resultEtudiant = etudiantDao.findById(etudiant.getId());
        assertEquals("ilyas", resultEtudiant.getFirstName());
        assertEquals("blidi", resultEtudiant.getLastName());
        assertEquals("mat123", resultEtudiant.getMatricule());
    }

    @Test
    void testDelete() {
        Etudiant etudiant = Etudiant.builder()
                .setFirstName("Nachid")
                .setLastName("Hamza")
                .setMatricule("mat123")
                .setFiliere(filiere)
                .build();
        etudiant = etudiantDao.create(etudiant);

        boolean isDeleted = etudiantDao.delete(etudiant.getId());
        assertTrue(isDeleted);

        Etudiant deletedEtudiant = etudiantDao.findById(etudiant.getId());
        assertNull(deletedEtudiant);
    }


    @Test
    void testFindAll() {
        Etudiant etudiant1 = Etudiant.builder()
                .setFirstName("hamza")
                .setLastName("nachid")
                .setMatricule("mat123")
                .setFiliere(filiere)
                .build();
        Etudiant etudiant2 = Etudiant.builder()
                .setFirstName("ilyas")
                .setLastName("blidi")
                .setMatricule("mat456")
                .setFiliere(filiere)
                .build();
        etudiantDao.create(etudiant1);
        etudiantDao.create(etudiant2);

        List<Etudiant> etudiants = etudiantDao.findAll();

        assertNotNull(etudiants);
        assertTrue(etudiants.size() >= 2);
    }

}
