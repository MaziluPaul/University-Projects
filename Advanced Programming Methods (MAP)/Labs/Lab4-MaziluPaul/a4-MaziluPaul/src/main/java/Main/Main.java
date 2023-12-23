package Main;

import Domain.*;
import Repository.*;
import Service.Service;
import UI.UI;
import Utils.RepositoryExceptions;

import java.util.Objects;

public class Main {
    public static void main(String[] args) throws RepositoryExceptions {

        IRepository<Pacient> pacientRepository = null;
        IRepository<Programare> programareRepository = null;
        Settings setari = Settings.getInstance();

        //IRepository<Pacient> pacientRepository = new BinaryFileRepository<>("pacienti.bin");
        //IRepository<Pacient> pacientRepository = new TextFileRepository<>("pacienti.txt", new PacientFactory());

//        Pacient pacient1 = new Pacient(1, "Mazilu", "Paul", 19);
//        pacientRepository.add(pacient1);
//        Pacient pacient2 = new Pacient(2, "Nicolaescu", "Raoul", 20);
//        pacientRepository.add(pacient2);
//        Pacient pacient3 = new Pacient(3, "Paraschiv", "Eduard", 19);
//        pacientRepository.add(pacient3);
//        Pacient pacient4 = new Pacient(4, "Olteanu", "Ana", 20);
//        pacientRepository.add(pacient4);
//        Pacient pacient5 = new Pacient(5, "Ceva", "Razvan", 21);
//        pacientRepository.add(pacient5);

        //IRepository<Programare> programareRepository = new BinaryFileRepository<>("programari.bin");
        //IRepository<Programare> programareRepository = new TextFileRepository<>("programari.txt", new ProgramareFactory());

//        programareRepository.add(new Programare(1, pacient1, "10/10/2023", "10:00", "Migrene"));
//        programareRepository.add(new Programare(2, pacient2, "20/07/2023", "10:00", "EKG"));
//        programareRepository.add(new Programare(3, pacient3, "5/02/2023", "10:00", "Depresie"));
//        programareRepository.add(new Programare(4, pacient4, "18/05/2023", "10:00", "Evaluare fat"));
//        programareRepository.add(new Programare(5, pacient5, "3/12/2023", "10:00", "Ghips picior drept"));

        if (Objects.equals(setari.getRepoType(), "memory")) {
            pacientRepository = new MemoryRepository<>();
            programareRepository = new MemoryRepository<>();
        }

        if (Objects.equals(setari.getRepoType(), "text")) {
            pacientRepository = new TextFileRepository<>(setari.getRepoFile1(), new PacientFactory());
            programareRepository = new TextFileRepository<>(setari.getRepoFile2(), new ProgramareFactory());
        }

        if (Objects.equals(setari.getRepoType(), "binary")) {
            pacientRepository = new BinaryFileRepository<>(setari.getRepoFile1());
            programareRepository = new BinaryFileRepository<>(setari.getRepoFile2());
        }

        if(Objects.equals(setari.getRepoType(), "database")){
            pacientRepository = new PacientDbRepository();
            programareRepository = new ProgramareDbRepository();
        }

        Service service = new Service(pacientRepository, programareRepository);
        UI ui = new UI(service);
        ui.run();

    }
}
