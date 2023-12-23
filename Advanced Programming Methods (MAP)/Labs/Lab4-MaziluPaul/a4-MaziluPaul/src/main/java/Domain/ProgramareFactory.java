package Domain;

public class ProgramareFactory implements IEntityFactory<Programare> {
    @Override
    public String toString(Programare object) {
        Pacient pacient = object.getPacient();
        return object.getId() + "," + pacient.getId() + "," + pacient.getNume() + "," + pacient.getPrenume() + "," + pacient.getVarsta() + "," + object.getData() + "," + object.getOra() + "," + object.getScop();
    }

    @Override
    public Programare createEntity(String line) {
        int id = Integer.parseInt(line.split(",")[0]);
        int id_pacient = Integer.parseInt(line.split(",")[1]);
        String nume = line.split(",")[2];
        String prenume = line.split(",")[3];
        int varsta = Integer.parseInt(line.split(",")[4]);
        Pacient pacient = new Pacient(id_pacient,nume,prenume,varsta);
        String data = line.split(",")[5];
        String ora = line.split(",")[6];
        String scop = line.split(",")[7];

        return new Programare(id, pacient, data, ora, scop);
    }
}
