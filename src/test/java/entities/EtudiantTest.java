package entities;


import org.example.entities.Etudiant;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.entities.Note;
import org.example.entities.builders.EtudiantBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    @Test
    void testConstructorAndGetter() {
        Filiere filiere = new Filiere();
        filiere.setNom("Informatique");
        filiere.setCode("code");

        Etudiant etudiant = new Etudiant(1L, "Hamza", "Nachid", "MAT123", filiere);

        assertNotNull(etudiant);
        assertEquals(1L, etudiant.getId());
        assertEquals("Hamza", etudiant.getFirstName());
        assertEquals("Nachid", etudiant.getLastName());
        assertEquals("MAT123", etudiant.getMatricule());
        assertEquals("Informatique", etudiant.getFiliere().getNom());
    }

    @Test
    void testSetters() {
        Filiere filiere = new Filiere();
        filiere.setNom("Mathematiques");

        List<Module> modules = new ArrayList<>();
        List<Note> notes = new ArrayList<>();

        Etudiant etudiant = new Etudiant();

        etudiant.setId(2L);
        etudiant.setFirstName("Ilyas");
        etudiant.setLastName("Blidi");
        etudiant.setMatricule("HN");
        etudiant.setFiliere(filiere);
        etudiant.setModules(modules);
        etudiant.setNotes(notes);
        assertNotNull(etudiant);

        assertEquals(2L, etudiant.getId());
        assertEquals("Ilyas", etudiant.getFirstName());
        assertEquals("Blidi", etudiant.getLastName());
        assertEquals("HN", etudiant.getMatricule());
        assertEquals("Mathematiques", etudiant.getFiliere().getNom());
        assertEquals(modules, etudiant.getModules());
        assertEquals(notes, etudiant.getNotes());
    }

    @Test
    void testToString() {
        Filiere filiere = new Filiere();
        filiere.setNom("Physique");

        Etudiant etudiant = new Etudiant(3L, "Nachid", "Hamza", "PHY789", filiere);

        String result = etudiant.toString();

        // Assert
        assertTrue(result.contains("id=3"));
        assertTrue(result.contains("firstName='Nachid'"));
        assertTrue(result.contains("lastName='Hamza'"));
        assertTrue(result.contains("matricule='PHY789'"));
    }

    @Test
    void testBuilder() {
        Filiere filiere = new Filiere();
        filiere.setNom("java");

        Etudiant etudiant = Etudiant.builder()
                .setId(4L)
                .setFirstName("Nachid")
                .setLastName("Hamza")
                .setMatricule("CHM456")
                .setFiliere(filiere)
                .build();

        assertEquals(4L, etudiant.getId());
        assertEquals("Nachid", etudiant.getFirstName());
        assertEquals("Hamza", etudiant.getLastName());
        assertEquals("CHM456", etudiant.getMatricule());
        assertEquals("java", etudiant.getFiliere().getNom());
    }
}
