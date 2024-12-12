package RobotPlatformer;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.ArrayList;

public class LeaderBoardPanel extends JPanel {
    Font font = new Font("Comic Sans MS", Font.BOLD, 50); // This might be different on windows

    // Required components for the panel
    private JLabel leaderBoardLabel = new JLabel("Leader Board");
    private JButton menuButton = new JButton("Main Menu");
    
    // These are used when creating the list of scores
    private JPanel itemPanel;
    private JLabel numberLabel;
    private JLabel nameLabel;
    private JLabel scoreLabel;
    
    // This is to keep consistent borders around the components that need it
    private Border border;

    public LeaderBoardPanel(int width, int height, ArrayList<HighScore> highScores) {
        // Basic setup of panel
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Uniform Border
        border = BorderFactory.createEmptyBorder(height / 20, 0, 0, 0);

        // Setup the top label
        leaderBoardLabel.setFont(font);
        leaderBoardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderBoardLabel.setForeground(Color.YELLOW);
        leaderBoardLabel.setBorder(border);

        // Add the top panel
        add(leaderBoardLabel);

        /*
         *  For every HighScore in highScores up until the 10th (arbitrary number that is common in arcade games), create a JPanel and attach the
         *    label that contains the name of the scorer and another for the score.
         */
        for (int i = 0; i < 10; i++) {
            itemPanel = new JPanel();
            itemPanel.setPreferredSize(new Dimension(width, height / 50));
            itemPanel.setBackground(Color.BLACK);
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS)); // This layout allow uniformity across scaling
            itemPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, width / 2));
            itemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add the item panel to the LeaderBoardPanel
            add(itemPanel);

            // If there is a highScore in position i of the highScores ArrayList
            if (i < highScores.size()) {
                // The index of the scorer
                numberLabel = new JLabel(String.valueOf(i + 1));
                numberLabel.setFont(font);
                numberLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                numberLabel.setForeground(Color.YELLOW);
                numberLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 4, 0, 0));

                // The name of the scorer
                nameLabel = new JLabel(highScores.get(i).getName());
                nameLabel.setFont(font);
                nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                nameLabel.setForeground(Color.YELLOW);
                nameLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 4, 0, 0));

                // The score label
                scoreLabel = new JLabel(String.valueOf(highScores.get(i).getScore()));
                scoreLabel.setFont(font);
                scoreLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                scoreLabel.setForeground(Color.YELLOW);
                scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 4, 0, 0));

                // Add all the created components to the itemPanel
                itemPanel.add(numberLabel);
                itemPanel.add(nameLabel);
                itemPanel.add(scoreLabel);
            }
            
        }

        // Add action listener to the button
        menuButton.addActionListener(e -> { // e -> is the lambda function for the button
            RobotPlatformer.mainMenu();
        });

        // Setup the button
        menuButton.setFont(font);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add blank object to allow for spacing that doesn't fill with the button
        add(Box.createRigidArea(new Dimension(0, height / 20)));
        
        // Add the button
        add(menuButton);
    }


}
