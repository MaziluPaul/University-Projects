package Domain;

public class PacientFactory implements IEntityFactory<Pacient>{

    @Override
    public String toString(Pacient pacient) {
        return pacient.getId() + "," + pacient.getNume() + "," + pacient.getPrenume() + "," + pacient.getVarsta();
    }

    @Override
    public Pacient createEntity(String line) {
        int id = Integer.parseInt(line.split(",")[0]);
        String nume = line.split(",")[1];
        String prenume = line.split(",")[2];
        int varsta = Integer.parseInt(line.split(",")[3]);

        return new Pacient(id, nume, prenume, varsta);
    }
}
