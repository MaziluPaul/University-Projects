package Service;

import Domain.Pacient;
import Domain.Programare;
import Repository.BinaryFileRepository;
import Repository.IRepository;
import Repository.MemoryRepository;
import Utils.DuplicateObjectException;
import Utils.ObjectNotFoundException;
import Utils.RepositoryExceptions;
import Utils.Validators;

import java.util.ArrayList;

public class Service {
    Validators validators = new Validators();

    IRepository<Pacient> pacientRepo;
    IRepository<Programare> programareRepo;

    public Service(IRepository<Pacient> pacientRepo, IRepository<Programare> programareRepo) {
        this.pacientRepo = pacientRepo;
        this.programareRepo = programareRepo;
    }

    public Pacient makePacient(int id, String nume, String prenume, int varsta) {
        return new Pacient(id, nume, prenume, varsta);
    }

    public Programare makeProgramare(int id, Pacient pacient, String data, String ora, String scop) {
        return new Programare(id, pacient, data, ora, scop);
    }

    public void addPacient(int id, String nume, String prenume, int varsta) throws RepositoryExceptions {
        validators.validatePacientId(id, getAllPacienti());
        Pacient pacient = makePacient(id, nume, prenume, varsta);
        pacientRepo.add(pacient);
    }

    public void addProgramare(int id, int id_pacient, String data, String ora, String scop) throws RepositoryExceptions {
        validators.validateProgramareId(id, getAllProgramari());
        validators.checkIfPacientExists(id_pacient, getAllPacienti());
        Pacient pacient = (Pacient) pacientRepo.getById(id_pacient);
        Programare programare = makeProgramare(id, pacient, data, ora, scop);
        programareRepo.add(programare);
    }

    public void deletePacient(int id) throws RepositoryExceptions {
        validators.checkIfPacientExists(id, getAllPacienti());
        Pacient pacient = (Pacient) pacientRepo.getById(id);
        ArrayList<Integer> id_programari = new ArrayList<>();

        for (Programare programare : programareRepo.getAll()) {
            if (programare.getPacient().getId() == pacient.getId()) {
                id_programari.add(programare.getId());
            }
        }

        for (Integer idp : id_programari) {
            Programare programare = (Programare) programareRepo.getById(idp);
            programareRepo.delete(programare);
        }
        pacientRepo.delete(pacient);
    }

    public void deleteProgramare(int id) throws RepositoryExceptions {
        validators.checkIfProgramareExists(id, getAllProgramari());
        Programare programare = (Programare) programareRepo.getById(id);
        programareRepo.delete(programare);
    }

    public void updatePacient(int id, String nume, String prenume, int varsta) throws RepositoryExceptions {
        validators.checkIfPacientExists(id, getAllPacienti());
        Pacient pacient = (Pacient) pacientRepo.getById(id);
        pacient.setNume(nume);
        pacient.setPrenume(prenume);
        pacient.setVarsta(varsta);
        pacientRepo.update(pacient);

        ArrayList<Integer> id_programari = new ArrayList<>();
        for (Programare programare : programareRepo.getAll()) {
            if (programare.getPacient().getId() == pacient.getId()) {
                id_programari.add(programare.getId());
            }
        }
        for (Integer idp : id_programari) {
            Programare programare = (Programare) programareRepo.getById(idp);
            programare.setPacient(pacient);
        }

    }

    public void updateProgramare(int id, int id_pacient, String data, String ora, String scop) throws RepositoryExceptions {
        validators.checkIfProgramareExists(id, getAllProgramari());
        Pacient pacient = (Pacient) pacientRepo.getById(id_pacient);
        Programare programare = (Programare) programareRepo.getById(id);
        programare.setPacient(pacient);
        programare.setData(data);
        programare.setOra(ora);
        programare.setScop(scop);
        programareRepo.update(programare);
    }

    public ArrayList<Pacient> getAllPacienti() {
        return pacientRepo.getAll();
    }

    public ArrayList<Programare> getAllProgramari() {
        return programareRepo.getAll();
    }
}
