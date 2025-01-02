package services.filiereService;

import org.example.entities.Filiere;
import org.example.services.impl.FiliereServiceImpl;
import org.junit.jupiter.api.Test;
import org.example.services.FiliereService;
import org.mockito.Mock;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FiliereServiceTest {


    @Test
    public void testCreate() {
        FiliereService filiereServiceMock = mock(FiliereService.class);
        when(filiereServiceMock.getByName("")).thenReturn(Filiere.builder().setNom("IID").setCode("1233").build());
        Filiere filiere = filiereServiceMock.getByName("");
        Assertions.assertEquals("1233", filiere.getCode());
    }
}
