package RobotPlatformer;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    Font font = new Font("Comic Sans MS", Font.BOLD, 50); // This might be different on windows

    JLabel nameLabel = new JLabel("Robot Platformer");
    JButton startButton = new JButton("Start Game");
    JButton leaderBoardButton = new JButton("Show Leader Board");

    // Default Constructor
    public StartPanel(int width, int height) {
        //Basic setup of panel
        setOpaque(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Set alignment of all components
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderBoardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Set the color of the label text
        nameLabel.setForeground(Color.YELLOW);
        
        // Set the font of all the components
        nameLabel.setFont(font);
        startButton.setFont(font);
        leaderBoardButton.setFont(font);

        // Set the border of the nameLabel
        nameLabel.setBorder(BorderFactory.createEmptyBorder(height / 20, 0, 0, 0));

        // Add the label and an empty box for spacing.
        add(nameLabel);
        add(Box.createRigidArea(new Dimension(0, height / 5)));

        // Create action listeners for both buttons
        startButton.addActionListener(e -> { // e -> is a lambda function
            RobotPlatformer.startGame();
        });

        leaderBoardButton.addActionListener(e -> {
            RobotPlatformer.startToLeaderBoard();
        });

        // Add the buttons with an empty component inbetween for spacing
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, height / 5)));
        add(leaderBoardButton);
    }

    
}
