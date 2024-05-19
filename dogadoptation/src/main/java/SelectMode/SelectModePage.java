package SelectMode;

import Adopt.AdoptPage;
import Adoptation.AdoptationPage;
import org.apache.log4j.Logger;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectModePage {
    private final JFrame frame;
    private JPanel selectPanel;
    private JButton adoptButton;
    private JButton adoptationButton;
    private static Logger logger = Logger.getLogger("SelectModePage logger");

    public SelectModePage() {
        frame = CreateFrame();
        ConfigureJFrame(frame);
        RegisterListeners();
    }

    private JFrame CreateFrame() {
        logger.info("Creating select mode frame");
        return new JFrame();
    }

    public void ConfigureJFrame(JFrame frame) {
        try {
            frame.setContentPane(this.selectPanel);
            frame.setVisible(true);
            frame.pack();
            frame.setResizable(false);
            frame.setTitle("Örökbefogadás - Választó");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            logger.info("Configured select mode frame");
        } catch (Exception e) {
            logger.error("Error configuring select mode frame: " + e.getMessage(), e);
        }
    }

    public void RegisterListeners() {
        adoptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logger.info("Adopt button clicked");
                try {
                    AdoptPage form = new AdoptPage();
                    frame.setVisible(false);
                    frame.dispose();
                } catch (Exception ex) {
                    logger.error("Error opening AdoptPage: " + ex.getMessage(), ex);
                }
            }
        });
        adoptationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logger.info("Adoptation button clicked");
                try {
                    AdoptationPage form = new AdoptationPage();
                    frame.setVisible(false);
                    frame.dispose();
                } catch (Exception ex) {
                    logger.error("Error opening AdoptationPage: " + ex.getMessage(), ex);
                }
            }
        });
    }
}
