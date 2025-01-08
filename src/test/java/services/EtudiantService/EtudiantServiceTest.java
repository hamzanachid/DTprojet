package services.EtudiantService;

import org.example.dao.EtudiantDao;
import org.example.entities.Etudiant;
import org.example.entities.Filiere;
import org.example.services.EtudiantService;
import org.example.services.impl.EtudiantServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class EtudiantServiceTest {
    static Etudiant etudiant;
    static List<Etudiant> etudiantList;
    static EtudiantDao mockDao;
    static EtudiantService etudiantService;
    static Filiere filiere;

    @BeforeAll
    public static void initEtudiant() {
        filiere = Filiere.builder().setId(1L).setNom("IID").setCode("123").build();
        etudiant = Etudiant.builder()
                .setId(1L)
                .setFirstName("Marouane")
                .setLastName("Test")
                .setFiliere(filiere)
                .setMatricule("123456")
                .build();
        mockDao = mock(EtudiantDao.class);
        etudiantService = EtudiantServiceImpl.getInstance(mockDao);
        etudiantList = new ArrayList<>();
    }

    @BeforeEach
    public void setUp() {
        reset(mockDao);
        etudiantList.clear();
    }

    @Test
    public void testCreate() {
        Etudiant newEtudiant = Etudiant.builder()
                .setId(2L)
                .setFirstName("Marouane")
                .setLastName("Boufarouj")
                .setFiliere(filiere)
                .setMatricule("654321")
                .build();
        when(mockDao.create(any(Etudiant.class))).thenReturn(newEtudiant);
        Etudiant createdEtudiant = etudiantService.create(newEtudiant);
        verify(mockDao, times(1)).create(newEtudiant);
        Assertions.assertNotNull(createdEtudiant);
        Assertions.assertEquals("Marouane", createdEtudiant.getFirstName());
        Assertions.assertEquals(2L, createdEtudiant.getId());
    }

    @Test
    public void testCreateWithNullEtudiant() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.create(null);
        });
    }

    @Test
    public void testCreateWithInvalidData() {
        Etudiant invalidEtudiant = Etudiant.builder()
                .setId(2L)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            etudiantService.create(invalidEtudiant);
        });
    }

    @Test
    public void testFindAll() {
        etudiantList.add(etudiant);
        when(mockDao.findAll()).thenReturn(etudiantList);
        List<Etudiant> result = etudiantService.findAll();
        verify(mockDao, times(1)).findAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(etudiant, result.get(0));
    }

    @Test
    public void testFindById() {
        when(mockDao.findById(1L)).thenReturn(etudiant);
        Etudiant result = etudiantService.getById(1L);
        verify(mockDao, times(1)).findById(1L);
        Assertions.assertEquals(etudiant, result);
    }

    @Test
    public void testFindByIdNotFound() {
        when(mockDao.findById(99L)).thenReturn(null);
        Etudiant result = etudiantService.getById(99L);
        verify(mockDao, times(1)).findById(99L);
        Assertions.assertNull(result);
    }

    @Test
    public void testDeleteById() {
        when(mockDao.delete(1L)).thenReturn(true);
        boolean result = etudiantService.delete(1L);
        verify(mockDao, times(1)).delete(1L);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(mockDao.delete(99L)).thenReturn(false);
        boolean result = etudiantService.delete(99L);
        verify(mockDao, times(1)).delete(99L);
        Assertions.assertFalse(result);
    }
}
