package Repository;

import Domain.Pacient;
import Domain.Programare;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProgramareDbRepository extends MemoryRepository<Programare> implements IDbRepository<Programare> {
    private static String JDBC_URL = "jdbc:sqlite:database.db";
    private Connection connection;

    public ProgramareDbRepository() {
        openConnection();
        createTable();
        generateAndSaveEntities();
        //initTable();
    }

    @Override
    public void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createTable() {
        try (final Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS programari(id int, id_pacient int, data varchar(400), ora varchar(400), scop varchar(400));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initTable() {
        List<Programare> programari = new ArrayList<>();
        Pacient pacient1 = new Pacient(1, "Ion", "Pop", 23);
        Pacient pacient2 = new Pacient(2, "Maria", "Ionescu", 25);
        Pacient pacient3 = new Pacient(3, "IRadu", "Verdea", 30);
        programari.add(new Programare(1, pacient1, "2023-10-10", "10:00", "scop"));
        programari.add(new Programare(2, pacient2, "2000-9-10", "11:00", "sco"));
        programari.add(new Programare(3, pacient3, "2023-8-10", "12:00", "sc"));
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO programari values (?,?,?,?,?);")) {
            for (Programare p : programari) {
                stmt.setInt(1, p.getId());
                stmt.setInt(2, p.getPacient().getId());
                stmt.setString(3, p.getData());
                stmt.setString(4, p.getOra());
                stmt.setString(5, p.getScop());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Programare> getAll() {
        ArrayList<Programare> programari = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT  * FROM programari;")) {
            try (PreparedStatement stmt1 = connection.prepareStatement("SELECT  * FROM pacienti;")) {
                ResultSet rs1 = stmt1.executeQuery();
                while (rs1.next()) {
                    Pacient pacient = new Pacient(rs1.getInt(1), rs1.getString(2), rs1.getString(3), rs1.getInt(4));
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        //TODO: try fixing this without making a new constructor. Update: IF IT RUNS IT RUNS :))))
                        if (pacient.getId() == rs.getInt(2)) {
                            Programare p = new Programare(rs.getInt(1), pacient, rs.getString(3), rs.getString(4), rs.getString(5));
                            programari.add(p);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programari;
    }

    public void add(Programare p) {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO programari values (?,?,?,?,?);")) {
            stmt.setInt(1, p.getId());
            stmt.setInt(2, p.getPacient().getId());
            stmt.setString(3, p.getData());
            stmt.setString(4, p.getOra());
            stmt.setString(5, p.getScop());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Programare p) {
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE programari SET id_pacient=?, data=?, ora=?, scop=? WHERE id=?;")) {
            stmt.setInt(1, p.getPacient().getId());
            stmt.setString(2, p.getData());
            stmt.setString(3, p.getOra());
            stmt.setString(4, p.getScop());
            stmt.setInt(5, p.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Programare p) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM programari WHERE id=?;")) {
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Programare getById(int id) {
        Programare programare = null;
        Pacient pacient = null;

        try (PreparedStatement stmt1 = connection.prepareStatement("SELECT * FROM pacienti WHERE id = ?;")) {
            try {
                stmt1.setInt(1, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (ResultSet rs1 = stmt1.executeQuery()) {
                if (rs1.next()) {
                    pacient = new Pacient(rs1.getInt("id"), rs1.getString("nume"), rs1.getString("prenume"), rs1.getInt("varsta"));
                }
            } catch (SQLException e) {
                // Handle or log the exception
                e.printStackTrace();
            }

            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM programari WHERE id = ?;")) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        programare = new Programare(rs.getInt("id"), pacient, rs.getString("data"), rs.getString("ora"), rs.getString("scop"));
                    }
                } catch (SQLException e) {
                    // Handle or log the exception
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                // Handle or log the exception
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programare;
    }

    private static void generateAndSaveEntities() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            // Generate and save programari
            generateAndSaveProgramari(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generateAndSaveProgramari(Connection connection) throws SQLException {
        String insertProgramareQuery = "INSERT INTO programari (id, id_pacient, data, ora, scop) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertProgramareQuery)) {
            // Get existing pacient IDs from the database
            Set<Integer> pacientIds = getExistingPacientIds(connection);

            for (int i = 0; i < 10; i++) {
                int id;
                do {
                    id = new Random().nextInt(1000) + 1;
                } while (id == 0);

                int pacientId = getRandomPacientId(pacientIds);

                String data = generateRandomDate();
                String ora = generateRandomTime();
                String scop = generateRandomScop();

                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, pacientId);
                preparedStatement.setString(3, data);
                preparedStatement.setString(4, ora);
                preparedStatement.setString(5, scop);

                preparedStatement.executeUpdate();
            }
        }
    }

    private static Set<Integer> getExistingPacientIds(Connection connection) throws SQLException {
        Set<Integer> pacientIds = new HashSet<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id FROM pacienti");
            while (resultSet.next()) {
                pacientIds.add(resultSet.getInt("id"));
            }
        }

        return pacientIds;
    }

    private static int getRandomPacientId(Set<Integer> pacientIds) {
        List<Integer> pacientIdsList = new ArrayList<>(pacientIds);
        Random random = new Random();
        return pacientIdsList.get(random.nextInt(pacientIdsList.size()));
    }

    private static String generateRandomDate() {
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();

        long randomDay = startEpochDay + (long) (Math.random() * (endEpochDay - startEpochDay + 1));
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return randomDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private static String generateRandomTime() {
        int randomHour = (int) (Math.random() * 24);
        int randomMinute = (int) (Math.random() * 60);

        return String.format("%02d:%02d", randomHour, randomMinute);
    }

    private static String generateRandomScop() {
        List<String> scopList = List.of("Consultatie", "Analize", "Tratament", "Vaccinare");
        Random random = new Random();
        return scopList.get(random.nextInt(scopList.size()));
    }

}


