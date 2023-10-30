package Service;

import Domain.Pacient;
import Domain.Programare;
import Repository.Repository;
import Utils.DuplicateObjectException;
import Utils.ObjectNotFoundException;
import Utils.Validators;

import java.util.ArrayList;

public class Service {
    private Repository<Pacient> pacientRepository = new Repository<Pacient>();
    private Repository<Programare> programareRepository = new Repository<Programare>();

    Validators validators = new Validators();

    public Service(Repository<Pacient> pacientRepository, Repository<Programare> programareRepository) {
        this.pacientRepository = pacientRepository;
        this.programareRepository = programareRepository;
    }

    public Pacient makePacient(int id, String nume, String prenume, int varsta) {
        return new Pacient(id, nume, prenume, varsta);
    }

    public Programare makeProgramare(int id, Pacient pacient, String data, String ora, String scop) {
        return new Programare(id, pacient, data, ora, scop);
    }

    public void addPacient(int id, String nume, String prenume, int varsta) throws DuplicateObjectException {
        validators.validatePacientId(id, getAllPacienti());
        Pacient pacient = makePacient(id, nume, prenume, varsta);
        pacientRepository.add(pacient);
    }

    public void addProgramare(int id, int id_pacient, String data, String ora, String scop) throws DuplicateObjectException, ObjectNotFoundException {
        validators.validateProgramareId(id, getAllProgramari());
        validators.checkIfPacientExists(id_pacient, getAllPacienti());
        Pacient pacient = pacientRepository.getById(id_pacient);
        Programare programare = makeProgramare(id, pacient, data, ora, scop);
        programareRepository.add(programare);
    }

    public void deletePacient(int id) throws ObjectNotFoundException {
        validators.checkIfPacientExists(id, getAllPacienti());
        Pacient pacient = pacientRepository.getById(id);
        ArrayList<Integer> id_programari = new ArrayList<>();

        for (Programare programare : programareRepository.getAll()) {
            if (programare.getPacient().getId() == pacient.getId()) {
                id_programari.add(programare.getId());
            }
        }

        for (Integer idp : id_programari) {
            Programare programare = programareRepository.getById(idp);
            programareRepository.delete(programare);
        }
        pacientRepository.delete(pacient);
    }

    public void deleteProgramare(int id) throws ObjectNotFoundException {
        validators.checkIfProgramareExists(id, getAllProgramari());
        Programare programare = programareRepository.getById(id);
        programareRepository.delete(programare);
    }

    public void updatePacient(int id, String nume, String prenume, int varsta) throws ObjectNotFoundException {
        validators.checkIfPacientExists(id , getAllPacienti());
        pacientRepository.getById(id).setNume(nume);
        pacientRepository.getById(id).setPrenume(prenume);
        pacientRepository.getById(id).setVarsta(varsta);
    }

    public void updateProgramare(int id, int id_pacient, String data, String ora, String scop) throws ObjectNotFoundException {
        validators.checkIfProgramareExists(id, getAllProgramari());
        Pacient pacient = pacientRepository.getById(id_pacient);
        programareRepository.getById(id).setPacient(pacient);
        programareRepository.getById(id).setData(data);
        programareRepository.getById(id).setOra(ora);
        programareRepository.getById(id).setScop(scop);
    }

    public ArrayList<Pacient> getAllPacienti() {
        return pacientRepository.getAll();
    }

    public ArrayList<Programare> getAllProgramari() {
        return programareRepository.getAll();
    }
}
