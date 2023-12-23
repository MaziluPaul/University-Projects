package Tests;

import Domain.Pacient;
import Domain.Programare;
import Domain.ProgramareFactory;
import org.junit.jupiter.api.Test;

public class ProgramareFactoryTest {
    @Test
    public void testProgramareFactory(){
        ProgramareFactory programareFactory = new ProgramareFactory();
        Pacient pacient = new Pacient(1,"one","one", 1);
        Programare programare = new Programare(1, pacient, "one","one","one");
        Programare programare1 = programareFactory.createEntity("1,1,one,one,1,one,one,one");
        assert programare1.equals(programare);

        assert programareFactory.toString(programare).equals("1,1,one,one,1,one,one,one");
    }
}
