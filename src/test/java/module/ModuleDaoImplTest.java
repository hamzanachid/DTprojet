package module;

import org.example.dao.FiliereDao;
import org.example.dao.ModuleDao;
import org.example.dao.impl.FiliereDaoImpl;
import org.example.dao.impl.ModuleDaoImpl;
import org.example.entities.Filiere;
import org.example.entities.Module;
import org.example.utils.Semestre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleDaoImplTest {
    private final ModuleDao moduleDao = ModuleDaoImpl.instance;
    private final FiliereDao filiereDao = FiliereDaoImpl.instance;
    private  Filiere filiere;



    @BeforeEach
    public void createFiliere(){
        this.filiere = Filiere
                .builder()
                .setNom("IID3")
                .setCode("just a code")
                .build();
        this.filiere = filiereDao.create(filiere);
    }

    @AfterEach
    public void deleteFiliere(){
        filiereDao.delete(this.filiere.getId());
    }

    @Test
    public void createTest() {
        Module module = Module
                .builder()
                .setCode("again test")
                .setFiliere(filiere)
                .setNom("java")
                .setSemestre(Semestre.S5)
                .build();
        module = moduleDao.create(module);

        assertEquals("again test", module.getCode());
        assertEquals("java", module.getNom());
        assertEquals(Semestre.S5, module.getSemestre());
        assertEquals(filiere.toString(), module.getFiliere().toString());
    }

    @Test
    public void updateTest() {
        Module module = Module
                .builder()
                .setCode("test1")
                .setFiliere(filiere)
                .setNom("java")
                .setSemestre(Semestre.S4)
                .build();
        module = moduleDao.create(module);

        module.setNom("Advanced Java");
        module.setSemestre(Semestre.S4);
        moduleDao.update(module.getId(), module);

        Module updatedModule = moduleDao.findById(module.getId());
        assertEquals("test1", updatedModule.getCode());
        assertEquals("Advanced Java", updatedModule.getNom());
        assertEquals(Semestre.S4, updatedModule.getSemestre());
        assertEquals(filiere.toString(), updatedModule.getFiliere().toString());
    }

    @Test
    public void deleteTest() {

        Module module = Module
                .builder()
                .setCode("test2")
                .setFiliere(filiere)
                .setNom("java jee")
                .setSemestre(Semestre.S5)
                .build();
        module = moduleDao.create(module);

        moduleDao.delete(module.getId());

        Module deletedModule = moduleDao.findById(module.getId());
        assertNull(deletedModule);

    }

    @Test
    public void findByIdTest() {

        Module module = Module
                .builder()
                .setCode("test3")
                .setFiliere(filiere)
                .setNom("oracle")
                .setSemestre(Semestre.S5)
                .build();
        module = moduleDao.create(module);

        Module fetchedModule = moduleDao.findById(module.getId());

        assertNotNull(fetchedModule);
        assertEquals("test3", fetchedModule.getCode());
        assertEquals("oracle", fetchedModule.getNom());
        assertEquals(Semestre.S5, fetchedModule.getSemestre());
        assertEquals(filiere.toString(), fetchedModule.getFiliere().toString());
    }

    @Test
    public void findAllTest() {

        Module module1 = Module
                .builder()
                .setCode("test11")
                .setFiliere(filiere)
                .setNom("java1")
                .setSemestre(Semestre.S5)
                .build();
        module1 = moduleDao.create(module1);

        Module module2 = Module
                .builder()
                .setCode("test22")
                .setFiliere(filiere)
                .setNom("python1")
                .setSemestre(Semestre.S5)
                .build();
        module2 = moduleDao.create(module2);


        List<Module> modules = moduleDao.findAll();

        assertNotNull(modules);
        assertTrue(modules.size() >= 2);
        assertTrue(modules.stream().anyMatch(m -> m.getCode().equals("test11")));
        assertTrue(modules.stream().anyMatch(m -> m.getCode().equals("test22")));
    }
}
