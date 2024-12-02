package RobotPlatformer;

import javax.swing.*;

public class RobotPlatformer extends JFrame {
    private static RobotPlatformer robotPlatformer;
    
    private final int frameWidth = 1200;
    private final int frameHeight = 1000;

    private static StartPanel startPanel;
	private static GamePanel gamePanel;
    private static GameOverPanel gameOverPanel;

    public RobotPlatformer() {
        startPanel = new StartPanel(frameWidth, frameHeight);
        add(startPanel);
		setSize(frameWidth, frameHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setTitle("Robot Platformer");
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
    }

    public static void mainMenu() {
        robotPlatformer.dispose();
        robotPlatformer = new RobotPlatformer();
    }

    public static void main(String[] args) {
        robotPlatformer = new RobotPlatformer();
    }
}
