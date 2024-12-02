package RobotPlatformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.*;

public class RobotPlatformer extends JFrame {
    private static RobotPlatformer robotPlatformer;
    
    private final int frameWidth = 1200;
    private final int frameHeight = 1000;

    private static StartPanel startPanel;
	private static GamePanel gamePanel;
    private static GameOverPanel gameOverPanel;
    private static LeaderBoardPanel leaderBoardPanel;
    private static ArrayList<HighScore> highScores;

    public RobotPlatformer() {
        highScores = new ArrayList<>();
        readLeaderboard();
        if (highScores.size() < 0) {
            sortScores();
        }
		setSize(frameWidth, frameHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setTitle("Robot Platformer");
        startPanel = new StartPanel(frameWidth, frameHeight);
        add(startPanel);
		setVisible(true);
    }

    public static void startGame() {
        robotPlatformer.remove(startPanel);
 
        gamePanel = new GamePanel(robotPlatformer.frameWidth, robotPlatformer.frameHeight);        
        robotPlatformer.add(gamePanel);
        gamePanel.grabFocus();
        robotPlatformer.revalidate();
        robotPlatformer.repaint();
    }

    public static void restartGame() {
        robotPlatformer.remove(gameOverPanel);
        robotPlatformer.revalidate();
 
        gamePanel = new GamePanel(robotPlatformer.frameWidth, robotPlatformer.frameHeight);        
        robotPlatformer.add(gamePanel);
        gamePanel.grabFocus();
        robotPlatformer.revalidate();
        robotPlatformer.repaint();
    }

    public static void gameOver(int score) {
        robotPlatformer.remove(gamePanel);

        gameOverPanel = new GameOverPanel(robotPlatformer.frameWidth, robotPlatformer.frameHeight, score);
        robotPlatformer.add(gameOverPanel);
        robotPlatformer.revalidate();
        robotPlatformer.repaint();

        if (highScores.size() < 10 || score > highScores.get(highScores.size() - 1).getScore()) {
            addScore(score);
            sortScores();
            saveLeaderboard();

            robotPlatformer.remove(gameOverPanel);
            overToLeaderBoard();
        }
    }

    public static void mainMenu() {
        robotPlatformer.dispose();
        robotPlatformer = new RobotPlatformer();
    }

    public static void startToLeaderBoard() {
        if (highScores.size() > 0) {
            sortScores();
        } else {
            JOptionPane.showMessageDialog(null, "There are no high scores to show.");
            return;
        }

        robotPlatformer.remove(startPanel);

        leaderBoardPanel = new LeaderBoardPanel(robotPlatformer.frameHeight, robotPlatformer.frameHeight, highScores);
        robotPlatformer.add(leaderBoardPanel);
        robotPlatformer.revalidate();
        robotPlatformer.repaint();
    }

    public static void overToLeaderBoard() {
        robotPlatformer.remove(gameOverPanel);

        leaderBoardPanel = new LeaderBoardPanel(robotPlatformer.frameHeight, robotPlatformer.frameHeight, highScores);
        robotPlatformer.add(leaderBoardPanel);
        robotPlatformer.revalidate();
        robotPlatformer.repaint();
    }

    public static void saveLeaderboard() {
        File leaderBoard;
        FileWriter writer;

        try {
            leaderBoard = new File("leaderBoard.txt");
            leaderBoard.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            writer = new FileWriter("leaderBoard.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (highScores.size() <= 0) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        for (int i = 0; i < 10; i++) {
            
            if (i > highScores.size() -1) {
                break;
            }

            try{
                writer.write(highScores.get(i).getName() + "," + String.valueOf(highScores.get(i).getScore()) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readLeaderboard() {
        File leaderBoard = new File("leaderBoard.txt");
        Scanner reader;

        try {
            reader = new Scanner(leaderBoard);
            reader.useDelimiter(",|\\n");
            while (reader.hasNext()) {
                String name = reader.next();
                int score = reader.nextInt();
                
                if (!name.isEmpty() && score > 0) {
                    HighScore highScore = new HighScore(name, score);
                    highScores.add(highScore);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            
        } 
    }

    public static void addScore(int score) {
        String name = "";
        while (name.isEmpty()) {
            name = JOptionPane.showInputDialog("New Highscore!\nPlease enter your name:");
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!");
                continue;
            }

            HighScore highScore = new HighScore(name, score);
            highScores.add(highScore);
        }
    }

    public static void sortScores() {
        Collections.sort(highScores, Comparator.comparing(HighScore::getScore));
        Collections.reverse(highScores);
    }

    public static void main(String[] args) {
        robotPlatformer = new RobotPlatformer();
    }
}
