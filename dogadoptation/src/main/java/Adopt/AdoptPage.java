package Adopt;

import Database.DatabaseConnection;
import SelectMode.SelectModePage;
import org.apache.log4j.Logger;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Database.DogRepo;

public class AdoptPage {
    private final JFrame frame;
    private JPanel addDogPanel;
    private JTextField nameField;
    private JTextField breedField;
    private JTextField ageField;
    private JTextArea descriptionField;
    private JButton addButton;
    private JButton backButton;
    private JLabel desLabel;
    private JLabel ageLabel;
    private JLabel breedLabel;
    private JLabel nameLabel;
    private DogRepo dogRepo;
    private static Logger logger = Logger.getLogger("AdoptPage logger");

    public AdoptPage() {
        frame = CreateFrame();
        ConfigureJFrame(frame);
        initDatabaseConnection();
        addListeners();
    }

    private void initDatabaseConnection() {
        try {
            DatabaseConnection db = new DatabaseConnection(
                    "jdbc:mysql://localhost:3306/dogadoptation",
                    "root",
                    "");
            this.dogRepo = new DogRepo(db.getDbConnection());
            logger.info("Database connection initialized successfully.");
        } catch (Exception e) {
            logger.error("Error initializing database connection: " + e.getMessage(), e);
        }
    }

    private JFrame CreateFrame() {
        logger.info("Creating adopt page frame.");
        return new JFrame();
    }

    public void ConfigureJFrame(JFrame frame) {
        try {
            frame.setContentPane(this.addDogPanel);
            frame.setVisible(true);
            frame.pack();
            frame.setResizable(false);
            frame.setTitle("Örökbeadás");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            logger.info("Adopt page frame configured successfully.");
        } catch (Exception e) {
            logger.error("Error configuring adopt page frame: " + e.getMessage(), e);
        }
    }

    private void addListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Add button clicked.");
                addDog();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Back button clicked.");
                backToSelectMode();
            }
        });
        logger.info("Listeners added.");
    }

    private void addDog() {
        String name = nameField.getText();
        String breed = breedField.getText();
        int age;
        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            logger.warn("Invalid age input: " + ageField.getText());
            JOptionPane.showMessageDialog(frame, "Kérem adjon meg egy érvényes kort!", "Érvénytelen kor", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String description = descriptionField.getText();

        if (dogRepo.addDog(name, breed, age, description)) {
            logger.info("Dog named " + name + " added successfully.");
            JOptionPane.showMessageDialog(frame, "A kutya sikeresen örökbeadva!", "Siker", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logger.error("Error adding dog named " + name + ".");
            JOptionPane.showMessageDialog(frame, "Hiba történt a kutya hozzáadása során.", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToSelectMode() {
        frame.setVisible(false);
        frame.dispose();
        new SelectModePage();
        logger.info("Navigated back to select mode page.");
    }
}
