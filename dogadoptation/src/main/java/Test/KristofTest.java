package Test;

import App.UserData;
import Database.DatabaseConnection;
import Database.DogRepo;

import Database.LoginCommand;
import Strategy.BreedSearchStrategy;
import Strategy.DogSearchStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KristofTest {

    @Test
    public void DatabaseConnectionTest() {
        DatabaseConnection db = new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                "");
        assertNotNull(db);
    }

    @Test
    void DatabaseConnectionInstanceTest() throws SQLException {
        DatabaseConnection db = new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                ""
        );

        assertEquals(
                new DatabaseConnection(
                        "jdbc:mysql://localhost:3306/dogadoptation",
                        "root",
                        ""
                ).getDbConnection().getMetaData().getURL(),
                db.getDbConnection().getMetaData().getURL()
        );

        assertEquals(
                new DatabaseConnection(
                        "jdbc:mysql://localhost:3306/dogadoptation",
                        "root",
                        ""
                ).getDbConnection().getMetaData().getUserName(),
                db.getDbConnection().getMetaData().getUserName()
        );
    }

    @ParameterizedTest
    @CsvSource("asd, asd")
    public void LoginTest(String username, String password) {
        LoginCommand command = new LoginCommand(new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                ""),
                username,
                password);
        command.execute();
        assertNotNull(UserData.username);
    }

    @Test
    public void getAllDogsTest() {
        DatabaseConnection dbConnection = new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                ""
        );
        Connection connection = dbConnection.getDbConnection();
        DogRepo dogRepo = new DogRepo(connection);

        List<String[]> dogs = dogRepo.getAllDogs();

        assertNotNull(dogs);
        assertTrue(dogs.size() > 0); // Assuming there are dogs in the database
    }

    @Test
    public void addDogTest() {
        DatabaseConnection dbConnection = new DatabaseConnection(
                "jdbc:mysql://localhost:3306/dogadoptation",
                "root",
                ""
        );
        Connection connection = dbConnection.getDbConnection();
        DogRepo dogRepo = new DogRepo(connection);

        String name = "Buddy";
        String breed = "Golden Retriever";
        int age = 3;
        String description = "Friendly and playful.";

        boolean result = dogRepo.addDog(name, breed, age, description);

        assertTrue(result);
    }

    @Test
    public void breedSearchStrategyTest() {
        List<String[]> dogs = new ArrayList<>();
        dogs.add(new String[]{"1", "Buddy", "Golden Retriever", "3", "Friendly and playful."});
        dogs.add(new String[]{"2", "Max", "Labrador", "5", "Energetic and loving."});
        dogs.add(new String[]{"3", "Bella", "Beagle", "3", "Curious and friendly."});

        DogSearchStrategy breedSearchStrategy = new BreedSearchStrategy();
        List<String[]> result = breedSearchStrategy.search(dogs, "Labrador");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Max", result.get(0)[1]);
    }

}
