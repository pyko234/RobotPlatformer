package RobotPlatformer;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    Font font = new Font("Comic Sans MS", Font.BOLD, 50); // This might be different on windows

    JLabel nameLabel = new JLabel("Robot Platformer");
    JButton startButton = new JButton("Start Game");
    JButton leaderBoardButton = new JButton("Show Leader Board");

    public StartPanel(int width, int height) {
        setOpaque(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderBoardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        nameLabel.setForeground(Color.YELLOW);
        
        nameLabel.setFont(font);
        startButton.setFont(font);
        leaderBoardButton.setFont(font);

        nameLabel.setBorder(BorderFactory.createEmptyBorder(height / 20, 0, 0, 0));

        add(nameLabel);
        add(Box.createRigidArea(new Dimension(0, height / 5)));

        startButton.addActionListener(e -> {
            RobotPlatformer.startGame();
        });

        leaderBoardButton.addActionListener(e -> {
            System.out.println("Show LeaderBoard");
        });

        add(startButton);
        add(Box.createRigidArea(new Dimension(0, height / 5)));
        add(leaderBoardButton);
    }

    
}
