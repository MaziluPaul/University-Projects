import Domain.Pacient;
import Domain.Programare;
import Repository.Repository;
import Service.Service;
import UI.UI;

public class Main {
    public static void main(String[] args) {
        Repository<Pacient> pacientRepository = new Repository<Pacient>();
        Pacient pacient1 = new Pacient(1,"Mazilu","Paul",19);
        pacientRepository.add(pacient1);
        Pacient pacient2 = new Pacient(2,"Nicolaescu","Raoul",20);
        pacientRepository.add(pacient2);
        Pacient pacient3 = new Pacient(3,"Paraschiv","Eduard",19);
        pacientRepository.add(pacient3);
        Pacient pacient4 = new Pacient(4,"Olteanu","Ana",20);
        pacientRepository.add(pacient4);
        Pacient pacient5 = new Pacient(5,"Ceva","Razvan",21);
        pacientRepository.add(pacient5);

        Repository<Programare> programareRepository= new Repository<Programare>();
        programareRepository.add(new Programare(1,pacient1,"10/10/2023", "10:00", "Migrene"));
        programareRepository.add(new Programare(2,pacient2,"20/07/2023", "10:00", "EKG"));
        programareRepository.add(new Programare(3,pacient3,"5/02/2023", "10:00", "Depresie"));
        programareRepository.add(new Programare(4,pacient4,"18/05/2023", "10:00", "Evaluare fat"));
        programareRepository.add(new Programare(5,pacient5,"3/12/2023", "10:00", "Ghips picior drept"));

        Service service = new Service(pacientRepository, programareRepository);

        UI ui = new UI(service);
        ui.run();
    }
}
