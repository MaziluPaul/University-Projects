package Tests;

import Domain.Pacient;
import Domain.PacientFactory;
import org.junit.jupiter.api.Test;

public class PacientFactoryTest {
    @Test
    public void testPacientFactory(){
        PacientFactory  pacientFactory= new PacientFactory();
        Pacient pacient = new Pacient(1,"one","one",1);
        Pacient pacient1 = pacientFactory.createEntity("1,one,one,1");
        assert pacient.equals(pacient1);

        assert (pacientFactory.toString(pacient)).equals("1,one,one,1");
    }
}
