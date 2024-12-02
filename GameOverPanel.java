package RobotPlatformer;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    Font font = new Font("Comic Sans MS", Font.BOLD, 50); // This might be different on windows

    private JLabel gameOverLabel = new JLabel("Game Over");
    private JLabel scoreLabel;
    private JButton restartButton = new JButton("Restart");
    private JButton menuButton = new JButton("Main Menu");
    
    public GameOverPanel(int width, int height, int score) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scoreLabel = new JLabel("Score: " + score);
        
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        gameOverLabel.setForeground(Color.YELLOW);
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(height / 20, 0, 0, 0));

        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(height / 20, 0, height / 20, 0));

        gameOverLabel.setFont(font);
        scoreLabel.setFont(font);
        restartButton.setFont(font);
        menuButton.setFont(font);

        add(gameOverLabel);
        add(scoreLabel);

        restartButton.addActionListener(e -> {
            RobotPlatformer.restartGame();
        });

        menuButton.addActionListener(e -> {
            RobotPlatformer.mainMenu();
        });

        add(restartButton);
        add(Box.createRigidArea(new Dimension(0, height / 5)));
        add(menuButton);
    }
}
