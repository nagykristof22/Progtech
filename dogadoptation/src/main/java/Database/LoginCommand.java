package Database;

import App.UserData;
import org.apache.log4j.Logger;
import javax.swing.*;
import java.sql.ResultSet;

public class LoginCommand implements Command {
    private static Logger logger = Logger.getLogger("Database.LoginCommand logger");
    private static JFrame frame = new JFrame();
    private DatabaseConnection databaseConnection;
    private ResultSet result;
    private String username;
    private String password;

    public LoginCommand(DatabaseConnection databaseConnection, String username, String password) {
        this.databaseConnection = databaseConnection;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        try {
            String query = "SELECT * FROM users WHERE username = '" + this.username + "' AND password = '" + this.password + "'";
            this.result = this.databaseConnection.getDbConnection().createStatement().executeQuery(query);

            if (result.isBeforeFirst()) {
                logger.info("User named: " + this.username + " logged in successfully!");
                JOptionPane.showMessageDialog(frame, "Üdvözöljük alkalmazásunkban!", "Sikeres bejelentkezés", JOptionPane.INFORMATION_MESSAGE);
                logLoginAttempt("Success");
            } else {
                logger.info("User named: " + this.username + " tried to login and failed!");
                JOptionPane.showMessageDialog(frame, "Hibás felhasználónév vagy jelszó", "Sikertelen bejelentkezés", JOptionPane.ERROR_MESSAGE);
                logLoginAttempt("Failed");
                return;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            while (this.result.next()) {
                UserData.id = this.result.getInt("id");
                UserData.username = this.result.getString("username");
                UserData.password = this.result.getString("password");
            }
        } catch (Exception e) {
            logger.error("An error has happened while getting the user's data.", e);
        }
    }

    private void logLoginAttempt(String result) {
        logger.info("Login attempt by user: " + this.username + " was " + result);
    }
}
