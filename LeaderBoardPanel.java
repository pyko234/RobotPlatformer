package RobotPlatformer;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.ArrayList;

public class LeaderBoardPanel extends JPanel {
    Font font = new Font("Comic Sans MS", Font.BOLD, 50); // This might be different on windows

    private JLabel leaderBoardLabel = new JLabel("Leader Board");
    private JButton menuButton = new JButton("Main Menu");

    private JPanel itemPanel;
    private JLabel numberLabel;
    private JLabel nameLabel;
    private JLabel scoreLabel;

    private Border border;

    public LeaderBoardPanel(int width, int height, ArrayList<HighScore> highScores) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        border = BorderFactory.createEmptyBorder(height / 20, 0, 0, 0);

        leaderBoardLabel.setFont(font);
        leaderBoardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderBoardLabel.setForeground(Color.YELLOW);
        leaderBoardLabel.setBorder(border);

        add(leaderBoardLabel);

        for (int i = 0; i < 10; i++) {
            itemPanel = new JPanel();
            itemPanel.setPreferredSize(new Dimension(width, height / 50));
            itemPanel.setBackground(Color.BLACK);
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
            itemPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, width / 2));
            itemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(itemPanel);

            if (i < highScores.size()) {
                numberLabel = new JLabel(String.valueOf(i + 1));
                numberLabel.setFont(font);
                numberLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                numberLabel.setForeground(Color.YELLOW);
                numberLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 4, 0, 0));

                nameLabel = new JLabel(highScores.get(i).getName());
                nameLabel.setFont(font);
                nameLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                nameLabel.setForeground(Color.YELLOW);
                nameLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 4, 0, 0));

                scoreLabel = new JLabel(String.valueOf(highScores.get(i).getScore()));
                scoreLabel.setFont(font);
                scoreLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
                scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                scoreLabel.setForeground(Color.YELLOW);
                scoreLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 4, 0, 0));

                itemPanel.add(numberLabel);
                itemPanel.add(nameLabel);
                itemPanel.add(scoreLabel);
            }
            
        }

        menuButton.addActionListener(e -> {
            RobotPlatformer.mainMenu();
        });

        menuButton.setFont(font);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createRigidArea(new Dimension(0, height / 20)));
        
        add(menuButton);
    }


}
