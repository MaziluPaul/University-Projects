package Tests;

import Domain.Pacient;
import Domain.PacientFactory;
import Repository.TextFileRepository;
import Utils.RepositoryExceptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextFileRepositoryTest {
    private static final String TEST_FILE_PATH = "test.txt";
    private TextFileRepository<Pacient> repository;

    @BeforeEach
    void setUp() throws RepositoryExceptions {
        // Initialize the repository and create an initial file for testing
        repository = new TextFileRepository<Pacient>(TEST_FILE_PATH, new PacientFactory());
        repository.add(new Pacient(1, "one", "one", 1));
        repository.add(new Pacient(2, "two", "two", 2));
    }

    @AfterEach
    void tearDown() throws IOException {
        // Delete the test file after each test
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    public void testAdd() throws RepositoryExceptions {
        Pacient pacient = new Pacient(3, "three", "three", 3);
        repository.add(pacient);
        assert 3 == repository.getAll().size();

        Pacient pacient1 = new Pacient(3, "three", "three", 3);
        assertThrows(RepositoryExceptions.class, () -> repository.add(pacient1));
    }

    @Test
    public void testDelete() throws RepositoryExceptions {
        repository.delete(new Pacient(2, "two", "two", 2));
        assert 1 == repository.getAll().size();
        assert repository.getAll().get(0).equals(new Pacient(1, "one", "one", 1));
    }

    @Test
    public void testUpdate() throws RepositoryExceptions {
        Pacient pacient3 = new Pacient(2, "four", "four", 4);
        repository.update(pacient3);
        assert repository.getAll().get(1).equals(pacient3);

    }

    @Test
    void testLoadFileFromExistingFile() throws IOException {
        // Create a test file with some content
        createTestFile("1,John,Doe,1\n2,Jane,Doe,2\n3,Bob,Smith,3");

        repository = new TextFileRepository<>(TEST_FILE_PATH, new PacientFactory());

        // Ensure that entities are loaded from the existing file
        List<Pacient> entities = repository.getAll();
        assertEquals(3, entities.size(), "Incorrect number of entities loaded from file");
    }

    @Test
    void testLoadFileFromNonExistingFile() throws IOException {
        // Ensure the test file does not exist initially
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }

        repository = new TextFileRepository<>(TEST_FILE_PATH, new PacientFactory());

        // Ensure that no entities are loaded from the non-existing file
        List<Pacient> entities = repository.getAll();
        assertTrue(entities.isEmpty(), "Entities should be empty for a non-existing file");
    }

    // Helper method to create a test file with the given content
    private void createTestFile(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.write(content);
        }
    }
}
