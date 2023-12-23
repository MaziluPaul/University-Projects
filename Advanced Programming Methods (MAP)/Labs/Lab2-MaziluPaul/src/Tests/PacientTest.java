package Tests;

import Domain.Pacient;
import org.junit.jupiter.api.Test;

public class PacientTest {
    @Test
    public void testPacient(){
        Pacient pacient = new Pacient(1,"one","one",1);
        assert pacient.getId() == 1;
        assert "one".equals(pacient.getNume());
        assert "one".equals(pacient.getPrenume());
        assert pacient.getVarsta() == 1;

        pacient.setNume("two");
        pacient.setPrenume("two");
        pacient.setVarsta(2);

        assert "two".equals(pacient.getNume());
        assert "two".equals(pacient.getPrenume());
        assert pacient.getVarsta() == 2;

        assert "Pacient{id=1, nume='two', prenume='two', varsta=2}".equals(pacient.toString());
    }
}
