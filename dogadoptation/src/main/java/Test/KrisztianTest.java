package Test;

import Database.DatabaseConnection;
import Database.DogRepo;

import Database.RegisterCommand;
import Strategy.AgeSearchStrategy;
import Strategy.DogSearchStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KrisztianTest {

    @Test
    public void getAdoptedDogsTest() {
        DatabaseConnection dbConnection = new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                ""
        );
        Connection connection = dbConnection.getDbConnection();
        DogRepo dogRepo = new DogRepo(connection);

        int testUserId = 4; // Use a valid user ID from your database
        List<String[]> adoptedDogs = dogRepo.getAdoptedDogs(testUserId);

        assertNotNull(adoptedDogs);
        // Assuming the user with ID 1 has adopted at least one dog
        assertTrue(adoptedDogs.size() > 0);
    }

    @Test
    public void adoptDogTest() {
        DatabaseConnection dbConnection = new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                ""
        );
        Connection connection = dbConnection.getDbConnection();
        DogRepo dogRepo = new DogRepo(connection);

        int testUserId = 4; // Use a valid user ID from your database
        int testDogId = 9; // Use a valid dog ID that is available for adoption

        boolean result = dogRepo.adoptDog(testUserId, testDogId);

        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource("Kristof, 1234asd")
    public void RegisterTest(String username, String password) throws SQLException {
        RegisterCommand registerCommand = new RegisterCommand(new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                ""),
                username,
                password);
        registerCommand.execute();
        assertTrue(!registerCommand.result.isBeforeFirst());
    }

    @Test
    public void ageSearchStrategyTest() {
        List<String[]> dogs = new ArrayList<>();
        dogs.add(new String[]{"1", "Buddy", "Golden Retriever", "3", "Friendly and playful."});
        dogs.add(new String[]{"2", "Max", "Labrador", "5", "Energetic and loving."});
        dogs.add(new String[]{"3", "Bella", "Beagle", "3", "Curious and friendly."});

        DogSearchStrategy ageSearchStrategy = new AgeSearchStrategy();
        List<String[]> result = ageSearchStrategy.search(dogs, "3");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Buddy", result.get(0)[1]);
        assertEquals("Bella", result.get(1)[1]);
    }
}