package Utils;

import Domain.Pacient;
import Domain.Programare;

import java.util.ArrayList;

public class Validators {
    // Ne uitam daca deja exista un id in lista de pacineti si aruncam exceptie
    public void validatePacientId(int id, ArrayList<Pacient> pacienti) throws DuplicateObjectException {
        for(Pacient pacient: pacienti){
            if(pacient.getId() == id)
                throw new DuplicateObjectException("ID Pacient deja existent!\n");
        }
    }
    public void validateProgramareId(int id, ArrayList<Programare> programari) throws DuplicateObjectException {
        for(Programare programare: programari){
            if(programare.getId() == id)
                throw new DuplicateObjectException("ID Programare deja existent!\n");
        }
    }

    //Ne uitam daca exista pacientul in lista de pacienti
    public void checkIfPacientExists(int id, ArrayList<Pacient> pacienti) throws ObjectNotFoundException {
        boolean ok = false;
        for(Pacient pacient: pacienti){
            if (pacient.getId() == id) {
                ok = true;
                break;
            }
        }
        if(!ok){
            throw new ObjectNotFoundException("Pacient inexistent!\n");
        }
    }

    public void checkIfProgramareExists(int id, ArrayList<Programare> programari) throws ObjectNotFoundException {
        boolean ok = false;
        for(Programare programare: programari){
            if (programare.getId() == id) {
                ok = true;
                break;
            }
        }
        if(!ok){
            throw new ObjectNotFoundException("Programare inexistenta!\n");
        }
    }
}
