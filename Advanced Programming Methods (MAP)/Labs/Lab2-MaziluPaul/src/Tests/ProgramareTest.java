package Tests;

import Domain.Pacient;
import Domain.Programare;
import org.junit.jupiter.api.Test;

public class ProgramareTest {
    @Test
    public void testProgramare(){
        Pacient pacient = new Pacient(1,"one", "one", 1);
        Programare programare = new Programare(1, pacient, "one", "one", "one");

        assert programare.getId() == 1;
        assert programare.getPacient() == pacient;
        assert "one".equals(programare.getData());
        assert "one".equals(programare.getOra());
        assert "one".equals(programare.getScop());

        Pacient pacient1 = new Pacient(2, "two", "two", 2);
        programare.setPacient(pacient1);
        programare.setData("two");
        programare.setOra("two");
        programare.setScop("two");

        assert programare.getPacient() == pacient1;
        assert "two".equals(programare.getData());
        assert "two".equals(programare.getOra());
        assert "two".equals(programare.getScop());

        assert "Programare{id=1, pacient=Pacient{id=2, nume='two', prenume='two', varsta=2}, data='two', ora='two', scop='two'}".equals(programare.toString());
    }
}
