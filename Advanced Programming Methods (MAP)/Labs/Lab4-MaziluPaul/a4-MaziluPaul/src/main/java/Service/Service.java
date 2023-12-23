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

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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

    // Problema 1: Numarul de programari pentru fiecare pacient în parte
    public void raportNumarProgramari() {
        List<Programare> programari = getAllProgramari();
        // Gruparea programarilor dupa pacienți
        Map<Pacient, Long> numarProgramariPePacient = programari.stream()
                .collect(Collectors.groupingBy(Programare::getPacient, Collectors.counting()));

        // Sortarea rezultatelor in ordine descrescatoare
        numarProgramariPePacient.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> {
                    Pacient pacient = entry.getKey();
                    Long numarProgramari = entry.getValue();
                    System.out.println("Pacient: " + pacient.getNume() + " " + pacient.getPrenume() +
                            ", Numar de programari: " + numarProgramari);
                });
    }

    // Problema 2: Numarul total de programari pentru fiecare luna a anului
    public void raportNumarProgramariPeLuna() {
        List<Programare> programari = getAllProgramari();
        // Definirea unui format pentru parsarea datelor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        // Gruparea programarilor dupa luna
        Map<Month, Long> numarProgramariPeLuna = programari.stream()
                .collect(Collectors.groupingBy(programare -> {
                    LocalDate dataProgramare = LocalDate.parse(programare.getData(), formatter);
                    return dataProgramare.getMonth();
                }, Collectors.counting()));

        // Sortarea rezultatelor în ordine descrescatoare
        numarProgramariPeLuna.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> {
                    Month luna = entry.getKey();
                    Long numarProgramari = entry.getValue();
                    String numeLuna = luna.getDisplayName(TextStyle.FULL, Locale.getDefault());
                    System.out.println("Luna: " + numeLuna + ", Numar total de programari: " + numarProgramari);
                });
    }

    // Problema 3: Numarul de zile trecute de la ultima programare a fiecarui pacient
    public void raportZileTrecuteDeLaUltimaProgramare() {
        List<Programare> programari = getAllProgramari();
        // Definirea unui format pentru parsarea datelor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        // Gruparea programarilor dupa pacient
        Map<Pacient, LocalDate> ultimaProgramarePentruPacient = programari.stream()
                .collect(Collectors.groupingBy(Programare::getPacient,
                        Collectors.collectingAndThen(
                                Collectors.maxBy((p1, p2) -> {
                                    LocalDate d1 = LocalDate.parse(p1.getData(), formatter);
                                    LocalDate d2 = LocalDate.parse(p2.getData(), formatter);
                                    return d1.compareTo(d2);
                                }),
                                opt -> opt.map(programare -> LocalDate.parse(programare.getData(), formatter))
                                        .orElse(LocalDate.of(2000, 1, 1))
                        )
                ));

        // Calcularea numarului de zile trecute de la ultima programare si sortarea rezultatelor
        ultimaProgramarePentruPacient.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> {
                    Pacient pacient = entry.getKey();
                    LocalDate ultimaProgramare = entry.getValue();
                    long zileTrecute = ultimaProgramare.until(LocalDate.now()).getDays();
                    System.out.println("Pacient: " + pacient.getNume() + " " + pacient.getPrenume() +
                            ", Ultima programare: " + ultimaProgramare.format(formatter) +
                            ", Zile trecute: " + zileTrecute);
                });
    }

    // Problema 4: Cele mai aglomerate luni ale anului
    public void raportCeleMaiAglomerateLuni() {
        List<Programare> programari = getAllProgramari();
        // Definirea unui format pentru parsarea datelor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        // Gruparea programarilor după lună si numararea programarilor pentru fiecare luna
        Map<Month, Long> numarProgramariPeLuna = programari.stream()
                .collect(Collectors.groupingBy(programare -> {
                    LocalDate dataProgramare = LocalDate.parse(programare.getData(), formatter);
                    return dataProgramare.getMonth();
                }, Collectors.counting()));

        // Sortarea rezultatelorin ordine descrescatoare
        numarProgramariPeLuna.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> {
                    Month luna = entry.getKey();
                    Long numarProgramari = entry.getValue();
                    String numeLuna = luna.getDisplayName(TextStyle.FULL, Locale.getDefault());
                    System.out.println("Luna: " + numeLuna + ", Numar total de programari: " + numarProgramari);
                });
    }
}
