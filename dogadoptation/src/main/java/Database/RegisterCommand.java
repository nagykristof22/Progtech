package Database;

import org.apache.log4j.Logger;
import javax.swing.*;
import java.sql.ResultSet;

public class RegisterCommand implements Command {
    private static Logger logger = Logger.getLogger("Database.RegisterCommand logger");
    private static JFrame frame = new JFrame();
    private DatabaseConnection databaseConnection;
    public ResultSet result;
    private String username;
    private String password;

    public RegisterCommand(DatabaseConnection databaseConnection, String username, String password) {
        this.databaseConnection = databaseConnection;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        if (username.isBlank() || password.isBlank()) {
            logger.info("Field left blank");
            JOptionPane.showMessageDialog(frame, "Kérem töltse ki a mezőket!", "Sikertelen regisztráció", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String query = "SELECT * FROM users WHERE username = '" + this.username + "'";
            this.result = this.databaseConnection.getDbConnection().createStatement().executeQuery(query);

            if (result.isBeforeFirst()) {
                logger.info(this.username + " is already registered!");
                JOptionPane.showMessageDialog(frame, "Ez a felhasználónév már foglalt!", "Sikertelen regisztráció", JOptionPane.ERROR_MESSAGE);
            } else {
                registerUser();
                JOptionPane.showMessageDialog(frame, "Most már bejelentkezhet!", "Sikeres regisztráció", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            logger.error("Error during registration process: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Register Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerUser() {
        try {
            String insertQuery = "INSERT INTO users (username, password) VALUES ('" + this.username + "', '" + this.password + "')";
            this.databaseConnection.getDbConnection().createStatement().executeUpdate(insertQuery);
            logger.info("User named " + this.username + " has been registered successfully!");
        } catch (Exception e) {
            logger.error("Error during user registration: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Register Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
