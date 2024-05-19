package Database;

import org.apache.log4j.Logger;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Strategy.DogSearchStrategy;

public class DogRepo {
    private static Logger logger = Logger.getLogger("Database.DogRepo logger");
    private static JFrame frame = new JFrame();
    private Connection dbConnection;
    private DogSearchStrategy searchStrategy;

    public DogRepo(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void setSearchStrategy(DogSearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<String[]> getAllDogs() {
        List<String[]> dogs = new ArrayList<>();
        try {
            String query = "SELECT id, name, breed, age, description FROM dogs WHERE status = 'elerheto'";
            try (Statement stmt = dbConnection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String[] dog = new String[5];
                    dog[0] = rs.getString("id");
                    dog[1] = rs.getString("name");
                    dog[2] = rs.getString("breed");
                    dog[3] = rs.getString("age");
                    dog[4] = rs.getString("description");
                    dogs.add(dog);
                }
                logger.info("Successfully fetched all available dogs.");
            }
        } catch (SQLException e) {
            logger.error("Error fetching all available dogs: " + e.getMessage(), e);
            e.printStackTrace();
        }
        return dogs;
    }

    public List<String[]> getAdoptedDogs(int userId) {
        List<String[]> adoptedDogs = new ArrayList<>();
        try {
            String query = "SELECT d.id, d.name FROM dogs d JOIN adoptions a ON d.id = a.dog_id WHERE a.user_id = ?";
            try (PreparedStatement pstmt = dbConnection.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String[] dog = new String[2];
                        dog[0] = rs.getString("id");
                        dog[1] = rs.getString("name");
                        adoptedDogs.add(dog);
                    }
                    logger.info("Successfully fetched adopted dogs for user id: " + userId);
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching adopted dogs for user id " + userId + ": " + e.getMessage(), e);
            e.printStackTrace();
        }
        return adoptedDogs;
    }

    public boolean adoptDog(int userId, int dogId) {
        try {
            String updateQuery = "UPDATE dogs SET status = 'adopted' WHERE id = ?";
            PreparedStatement updateStmt = dbConnection.prepareStatement(updateQuery);
            updateStmt.setInt(1, dogId);
            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
                String insertQuery = "INSERT INTO adoptions (user_id, dog_id) VALUES (?, ?)";
                PreparedStatement insertStmt = dbConnection.prepareStatement(insertQuery);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, dogId);
                insertStmt.executeUpdate();

                logger.info("Successfully adopted dog with id " + dogId + " for user id " + userId);
                return true;
            } else {
                logger.warn("No dog found with id " + dogId + " to adopt.");
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error adopting dog with id " + dogId + " for user id " + userId + ": " + e.getMessage(), e);
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean addDog(String name, String breed, int age, String description) {
        try {
            String query = "INSERT INTO dogs (name, breed, age, description, status) VALUES (?, ?, ?, ?, 'elerheto')";
            PreparedStatement pstmt = dbConnection.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, breed);
            pstmt.setInt(3, age);
            pstmt.setString(4, description);
            pstmt.executeUpdate();
            logger.info("Successfully added dog: " + name + ", " + breed + ", age " + age);
            return true;
        } catch (SQLException e) {
            logger.error("Error adding dog: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Adatbázis hiba", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<String[]> searchDogs(String query) {
        List<String[]> dogs = getAllDogs();
        if (searchStrategy != null) {
            List<String[]> result = searchStrategy.search(dogs, query);
            logger.info("Search completed with query: " + query);
            return result;
        }
        logger.warn("Search strategy is not set.");
        return new ArrayList<>();
    }
}
