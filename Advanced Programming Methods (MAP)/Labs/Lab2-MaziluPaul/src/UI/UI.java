package UI;

import Domain.Pacient;
import Domain.Programare;
import Service.Service;
import Utils.DuplicateObjectException;
import Utils.ObjectNotFoundException;
import Utils.RepositoryExceptions;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class UI {
    private final Service service;

    public UI(Service service) {
        this.service = service;
    }

    private static void printOptions() {
        System.out.println("""
                1. Add\s
                2. Delete\s
                3. Update\s
                4. ShowAll\s
                x. Exit""");
    }

    private void addPacient() throws RepositoryExceptions {
        Scanner scn = new Scanner(System.in);

        System.out.print("ID Pacient: ");
        int id = scn.nextInt();

        System.out.print("Nume: ");
        String nume = scn.next();

        System.out.print("Prenume: ");
        String prenume = scn.next();

        System.out.print("Varsta: ");
        int varsta = scn.nextInt();

        service.addPacient(id, nume, prenume, varsta);
    }

    private void addProgramare() throws RepositoryExceptions {

        Scanner scn = new Scanner(System.in);
        System.out.print("ID Programare: ");
        int id = scn.nextInt();

        System.out.print("ID Pacient: ");
        int id_pacient = scn.nextInt();

        System.out.print("Data: ");
        String data = scn.next();

        System.out.print("Ora: ");
        String ora = scn.next();

        System.out.print("Scopul: ");
        String scopul = scn.next();

        service.addProgramare(id, id_pacient, data, ora, scopul);
    }

    private int readId() {
        Scanner scn = new Scanner(System.in);

        System.out.print("ID: ");
        return scn.nextInt();
    }

    private void deletePacient() throws RepositoryExceptions {
        int id = readId();
        service.deletePacient(id);
    }

    private void deleteProgramare() throws RepositoryExceptions {
        int id = readId();
        service.deleteProgramare(id);
    }

    private void updatePacient() throws RepositoryExceptions {
        Scanner scn = new Scanner(System.in);

        System.out.print("ID Pacient: ");
        int id = scn.nextInt();

        System.out.print("Nume: ");
        String nume = scn.next();

        System.out.print("Prenume: ");
        String prenume = scn.next();

        System.out.print("Varsta: ");
        int varsta = scn.nextInt();

        service.updatePacient(id, nume, prenume, varsta);
    }

    private void updateProgramare() throws RepositoryExceptions {
        Scanner scn = new Scanner(System.in);
        System.out.print("ID Programare: ");
        int id = scn.nextInt();

        System.out.print("ID Pacient: ");
        int id_pacient = scn.nextInt();

        System.out.print("Data: ");
        String data = scn.next();

        System.out.print("Ora: ");
        String ora = scn.next();

        System.out.print("Scopul: ");
        String scopul = scn.next();

        service.updateProgramare(id, id_pacient, data, ora, scopul);
    }

    private void getAllPacients() {
        ArrayList<Pacient> all = this.service.getAllPacienti();
        for (Pacient pacient : all) {
            System.out.println(pacient.toString());
        }
    }

    private void getAllProgramari() {
        ArrayList<Programare> all = this.service.getAllProgramari();
        for (Programare programare : all) {
            System.out.println(programare.toString());
        }
    }

    private void add() throws RepositoryExceptions {
        Scanner scn = new Scanner(System.in);
        System.out.print("""
                1. Pacient \s
                2. Programare \n""");

        System.out.print("Option: ");
        String option = scn.nextLine();

        if (Objects.equals(option, "1"))
            addPacient();
        if (Objects.equals(option, "2"))
            addProgramare();

    }

    private void delete() throws RepositoryExceptions {
        Scanner scn = new Scanner(System.in);
        System.out.print("""
                1. Pacient \s
                2. Programare \n""");
        System.out.print("Option: ");
        String option = scn.nextLine();

        if (Objects.equals(option, "1"))
            deletePacient();
        if (Objects.equals(option, "2"))
            deleteProgramare();

    }

    private void update() throws RepositoryExceptions {
        Scanner scn = new Scanner(System.in);
        System.out.print("""
                1. Pacient \s
                2. Programare \n""");

        System.out.print("Option: ");
        String option = scn.nextLine();

        if (Objects.equals(option, "1"))
            updatePacient();
        if (Objects.equals(option, "2"))
            updateProgramare();
    }

    private void getAll() {
        Scanner scn = new Scanner(System.in);
        System.out.print("""
                1. Pacienti \s
                2. Programari \n""");

        System.out.print("Option: ");
        String option = scn.nextLine();

        if (Objects.equals(option, "1"))
            getAllPacients();
        if (Objects.equals(option, "2"))
            getAllProgramari();

    }

    public void run() {
        while (true) {
            try {
                printOptions();
                Scanner scn = new Scanner(System.in);
                System.out.print("Choose option: ");
                String option = scn.nextLine();

                switch (option) {
                    case "1":
                        add();
                        break;

                    case "2":
                        delete();
                        break;

                    case "3":
                        update();
                        break;

                    case "4":
                        getAll();
                        break;

                    case "x":
                        System.exit(0);
                        break;
                }
            }catch(RepositoryExceptions e){
                System.out.println(e);
            }
        }
    }
}
