package Domain;

import java.util.Objects;

public class Programare extends Entity {
    private static final long serialVersionUID = 1000L;
    private Pacient pacient;
    private String data;
    private String ora;
    private String scop;
    public Programare(int id, Pacient pacient, String data, String ora, String scop) {
        super(id);
        this.pacient = pacient;
        this.data = data;
        this.ora = ora;
        this.scop = scop;
    }

    public Pacient getPacient() {
        return pacient;
    }
    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getOra() {
        return ora;
    }
    public void setOra(String ora) {
        this.ora = ora;
    }
    public String getScop() {
        return scop;
    }
    public void setScop(String scop) {
        this.scop = scop;
    }
    @Override
    public String toString() {
        return "Programare{" +
                "id=" + id +
                ", pacient=" + pacient +
                ", data='" + data + '\'' +
                ", ora='" + ora + '\'' +
                ", scop='" + scop + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Programare programare = (Programare) o;
        return this.getId() == programare.getId() && Objects.equals(this.pacient, programare.getPacient()) && Objects.equals(this.getData(), programare.getData()) && Objects.equals(this.getOra(), programare.getOra()) && Objects.equals(this.getScop(), programare.getScop());
    }
}
