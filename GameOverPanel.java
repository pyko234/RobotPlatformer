package RobotPlatformer;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    Font font = new Font("Comic Sans MS", Font.BOLD, 50); // This might be different on windows

    // The required components for the panel
    private JLabel gameOverLabel = new JLabel("Game Over");
    private JLabel scoreLabel;
    private JButton restartButton = new JButton("Restart");
    private JButton menuButton = new JButton("Main Menu");
    
    // Default Constructor
    public GameOverPanel(int width, int height, int score) {
        // Basic setup of panel
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // To handle uniformity and scaling

        // Create label for the score
        scoreLabel = new JLabel("Score: " + score);
        
        // Center the components
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Setup labels
        gameOverLabel.setForeground(Color.YELLOW);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(height / 20, 0, 0, 0));

        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(height / 20, 0, height / 20, 0));

        // Set the font for the components
        gameOverLabel.setFont(font);
        scoreLabel.setFont(font);
        restartButton.setFont(font);
        menuButton.setFont(font);

        // Add the labels
        add(gameOverLabel);
        add(scoreLabel);

        // Add action listeners to the buttons
        restartButton.addActionListener(e -> { // e-> is a lambda function. used to not have to implement action listener
            RobotPlatformer.restartGame();
        });

        menuButton.addActionListener(e -> {
            RobotPlatformer.mainMenu();
        });

        // Add the buttons, with a blank object in between for spacing.
        add(restartButton);
        add(Box.createRigidArea(new Dimension(0, height / 5))); // without this the buttons edges fill the vertical space, not wanted.
        add(menuButton);
    }
}
