package Adoptation;

import javax.swing.*;
import Database.DatabaseConnection;
import Database.DogRepo;
import App.UserData;
import Strategy.AgeSearchStrategy;
import Strategy.BreedSearchStrategy;
import SelectMode.SelectModePage;
import org.apache.log4j.Logger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdoptationPage {
    private final JFrame frame;
    private JPanel adoptationPanel;
    private JTable dogTable;
    private JComboBox<String> comboBox1;
    private JButton adoptButton;
    private JButton backButton;
    private DogRepo dogRepo;
    private JTextField searchField;
    private JComboBox<String> searchComboBox;
    private static Logger logger = Logger.getLogger("AdoptationPage logger");

    public AdoptationPage() {
        createUIComponents();
        frame = CreateFrame();
        ConfigureJFrame(frame);
        initDatabaseConnection();
        populateDogList();
        configureComboBox();
        configureAdoptButton();
        configureBackButton();
        configureSearchComponents();
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
        logger.info("Creating adoption page frame.");
        return new JFrame();
    }

    public void ConfigureJFrame(JFrame frame) {
        try {
            frame.setContentPane(this.adoptationPanel);
            frame.setVisible(true);
            frame.pack();
            frame.setResizable(false);
            frame.setTitle("Örökbefogadás - Listák");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            logger.info("Adoption page frame configured successfully.");
        } catch (Exception e) {
            logger.error("Error configuring adoption page frame: " + e.getMessage(), e);
        }
    }

    private void populateDogList() {
        try {
            List<String[]> dogs = dogRepo.getAllDogs();
            updateTable(dogs, new String[]{"ID", "NÉV", "FAJTA", "KOR", "LEÍRÁS"});
            logger.info("Dog list populated with available dogs.");
        } catch (Exception e) {
            logger.error("Error populating dog list: " + e.getMessage(), e);
        }
    }

    private void populateAdoptedDogList() {
        try {
            List<String[]> adoptedDogs = dogRepo.getAdoptedDogs(UserData.id);
            updateTable(adoptedDogs, new String[]{"ID", "NÉV"});
            logger.info("Dog list populated with adopted dogs.");
        } catch (Exception e) {
            logger.error("Error populating adopted dog list: " + e.getMessage(), e);
        }
    }

    private void updateTable(List<String[]> data, String[] columnNames) {
        String[][] dataArray = new String[data.size()][columnNames.length];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i);
        }
        dogTable.setModel(new javax.swing.table.DefaultTableModel(dataArray, columnNames));
        logger.info("Dog table updated.");
    }

    private void configureComboBox() {
        comboBox1.addItem("Örökbe fogadható");
        comboBox1.addItem("Örökbe fogadott");

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem().equals("Örökbe fogadható")) {
                    logger.info("Selected 'Örökbe fogadható' from combo box.");
                    populateDogList();
                    adoptButton.setVisible(true);
                } else if (comboBox1.getSelectedItem().equals("Örökbe fogadott")) {
                    logger.info("Selected 'Örökbe fogadott' from combo box.");
                    populateAdoptedDogList();
                    adoptButton.setVisible(false);
                }
            }
        });
        logger.info("Combo box configured.");
    }

    private void configureAdoptButton() {
        adoptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dogTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Válassz egy kutyát az örökbefogadáshoz!", "Figyelem", JOptionPane.WARNING_MESSAGE);
                    logger.warn("No dog selected for adoption.");
                    return;
                }

                int dogId = Integer.parseInt(dogTable.getValueAt(selectedRow, 0).toString());
                boolean success = dogRepo.adoptDog(UserData.id, dogId);

                if (success) {
                    JOptionPane.showMessageDialog(frame, "Sikeres örökbefogadás!", "Információ", JOptionPane.INFORMATION_MESSAGE);
                    logger.info("Dog with ID " + dogId + " adopted successfully.");
                    populateDogList();
                } else {
                    JOptionPane.showMessageDialog(frame, "Hiba történt az örökbefogadás során.", "Hiba", JOptionPane.ERROR_MESSAGE);
                    logger.error("Error during adoption process for dog with ID " + dogId + ".");
                }
            }
        });
        logger.info("Adopt button configured.");
    }

    private void configureBackButton() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SelectModePage();
                logger.info("Navigated back to select mode page.");
            }
        });
        logger.info("Back button configured.");
    }

    private void configureSearchComponents() {
        searchComboBox.addItem("Kor");
        searchComboBox.addItem("Fajta");

        searchComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        logger.info("Search components configured.");
    }

    private void performSearch() {
        String query = searchField.getText();
        if (searchComboBox.getSelectedItem().equals("Kor")) {
            dogRepo.setSearchStrategy(new AgeSearchStrategy());
            logger.info("Age search strategy set.");
        } else if (searchComboBox.getSelectedItem().equals("Fajta")) {
            dogRepo.setSearchStrategy(new BreedSearchStrategy());
            logger.info("Breed search strategy set.");
        }

        List<String[]> results = dogRepo.searchDogs(query);
        updateTable(results, new String[]{"ID", "NÉV", "FAJTA", "KOR", "LEÍRÁS"});
        logger.info("Search performed with query: " + query);
    }

    private void createUIComponents() {
        adoptationPanel = new JPanel(new BorderLayout());
        dogTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dogTable);
        adoptationPanel.add(scrollPane, BorderLayout.CENTER);
        comboBox1 = new JComboBox<>();
        adoptButton = new JButton("Örökbe fogadás");
        backButton = new JButton("Vissza");
        searchField = new JTextField(15);
        searchComboBox = new JComboBox<>();
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchComboBox);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(comboBox1);
        bottomPanel.add(adoptButton);
        bottomPanel.add(backButton);
        adoptationPanel.add(searchPanel, BorderLayout.NORTH);
        adoptationPanel.add(bottomPanel, BorderLayout.SOUTH);
        logger.info("UI components created.");
    }
}
