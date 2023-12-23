package Tests;

import Domain.Pacient;
import Domain.Programare;
import Repository.MemoryRepository;
import Service.Service;
import Utils.RepositoryExceptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {
    MemoryRepository<Pacient> pacientMemoryRepository = new MemoryRepository<>();
    MemoryRepository<Programare> programareMemoryRepository = new MemoryRepository<>();
    Service service = new Service(pacientMemoryRepository, programareMemoryRepository);
    @Test
    public void testAdd() throws RepositoryExceptions {
        service.addPacient(1,"one","one", 1);
        assert 1 == service.getAllPacienti().size();

        service.addProgramare(1,1,"10/10/2010", "10:00", "scop");
        assert 1 == service.getAllProgramari().size();
    }
    @Test
    public void testDelete() throws RepositoryExceptions {
        service.addPacient(1,"one","one", 1);
        service.addPacient(2,"two","two", 2);
        service.deletePacient(1);

        assert 1 == service.getAllPacienti().size();

        service.addPacient(1,"one","one", 1);
        service.addProgramare(1,1, "1","1","1");
        service.addProgramare(2,2, "2","2","2");
        service.deleteProgramare(2);

        assert 1 == service.getAllProgramari().size();

        service.deletePacient(1);

        assert service.getAllProgramari().isEmpty();
    }

    @Test
    public void testUpdate() throws RepositoryExceptions {
        service.addPacient(1,"one","one", 1);
        service.updatePacient(1,"1","1",1);

        assertEquals(service.getAllPacienti().get(0), new Pacient(1, "1", "1", 1));

        service.addPacient(2,"2","2",2);
        service.addProgramare(1,1,"10/10/2010", "10:00", "scop");
        service.updateProgramare(1,2, "1","1","1");

        assertEquals(service.getAllProgramari().get(0), new Programare(1, new Pacient(2, "2", "2", 2), "1", "1", "1"));

        service.updatePacient(2,"3","3",3);

        assertEquals(service.getAllProgramari().get(0).getPacient(), new Pacient(2,"3","3",3));
    }
}
