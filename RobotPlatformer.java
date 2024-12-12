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
    // Create a static instace of the object with its class to allow other classes to access certian methods that alters this object
    private static RobotPlatformer robotPlatformer;
    
    // This is where the frames demensions are determined
    private final int frameWidth = 1200;
    private final int frameHeight = 1000;

    // All of the required objects for the game to run
    private static StartPanel startPanel;
	private static GamePanel gamePanel;
    private static GameOverPanel gameOverPanel;
    private static LeaderBoardPanel leaderBoardPanel;
    private static ArrayList<HighScore> highScores;

    // Default constructor
    public RobotPlatformer() {
        // Creates an empty List for the highscores, when returning to the main menu this prevents the scores from doubling in the array
        highScores = new ArrayList<>();

        // Read the leaderboard file
        readLeaderboard();

        // If there are scores present, sort the scores
        if (highScores.size() < 0) {
            sortScores();
        }

        // Finally begin basic setup of the frame
		setSize(frameWidth, frameHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        setTitle("Robot Platformer");

        // Create the inital panel
        startPanel = new StartPanel(frameWidth, frameHeight);
        add(startPanel);

        // Show the frame
		setVisible(true);
    }

    /*
     * This method removes the startPanel and adds a new gamePanel. I tried using removeAll() but it produced unexpected behaviour and I'm not sure why. A potential fix to remove
     *   so many different methods for routing from one panel to another, the previously loaded panel could be saved to a pointer and then removed with that pointer. This fix occurs to me to
     *   late to refactor without spending far to much time to troubleshoot the implemtation, so a note for future projects.
     */
    public static void startGame() {
        robotPlatformer.remove(startPanel);
 
        gamePanel = new GamePanel(robotPlatformer.frameWidth, robotPlatformer.frameHeight);        
        robotPlatformer.add(gamePanel);
        gamePanel.grabFocus(); // This focus grab may seem redundant, but without it being here and in the gamePanel class, the game does not pickup key inputs
        robotPlatformer.revalidate();
        robotPlatformer.repaint();
    }

    /*
     * This method removes the gameOverPanel and creates and adds a new gamePanel.
     */
    public static void restartGame() {
        robotPlatformer.remove(gameOverPanel);
        robotPlatformer.revalidate();
 
        gamePanel = new GamePanel(robotPlatformer.frameWidth, robotPlatformer.frameHeight);        
        robotPlatformer.add(gamePanel);
        gamePanel.grabFocus();
        robotPlatformer.revalidate();
        robotPlatformer.repaint();
    }

    /*
     *  This method removes the gamePanel and creates and adds a new gameOverPanel using the score. This also handles the high score logic.
     */
    public static void gameOver(int score) {
        robotPlatformer.remove(gamePanel);

        gameOverPanel = new GameOverPanel(robotPlatformer.frameWidth, robotPlatformer.frameHeight, score);
        robotPlatformer.add(gameOverPanel);
        robotPlatformer.revalidate();
        robotPlatformer.repaint();

        /*
         * If the highScores are less than 10 or the highScore is greater than the smallest score in the list (which is at the bottom)
         */
        if (highScores.size() < 10 || score > highScores.get(highScores.size() - 1).getScore()) {
            addScore(score); // Create and add a new HighScore
            sortScores(); // Sort the Scores
            saveLeaderboard(); // and save to the leaderBoard.txt file

            robotPlatformer.remove(gameOverPanel);
            overToLeaderBoard(); // Show the leaderBoard if the user is now on the leaderBoard
        }
    }

    /*
     * This method removes the current instace of the frame and creates a new one. This was the simplest way to allow the mainMenu to be called from anywhere, which was the idea I had when I was trying to
     * implement a pause screen, but that method got abandoned when I couldn't gracefully pause the key Listener.
     */
    public static void mainMenu() {
        robotPlatformer.dispose();
        robotPlatformer = new RobotPlatformer();
    }

    /*
     * This method removes the startPanel and shows the leaderBoardPanel. The guarded statement in the beginning prevents the leaderBoard from being shown when there is no scores to show.
     */
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

    /*
     * This removes the gameOverPanel and shows the leaderBoardPanel.
     */
    public static void overToLeaderBoard() {
        robotPlatformer.remove(gameOverPanel);

        leaderBoardPanel = new LeaderBoardPanel(robotPlatformer.frameHeight, robotPlatformer.frameHeight, highScores);
        robotPlatformer.add(leaderBoardPanel);
        robotPlatformer.revalidate();
        robotPlatformer.repaint();
    }

    /*
     * This method saves the current highScore ArrayList to the leaderBoard.txt file.
     */
    public static void saveLeaderboard() {
        File leaderBoard;
        FileWriter writer;

        // If the file cannot be created
        try {
            leaderBoard = new File("leaderBoard.txt");
            leaderBoard.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // If there is an issue creating the writer
        try {
            writer = new FileWriter("leaderBoard.txt");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // If there are no highScores in the array
        if (highScores.size() <= 0) {
            try {
                writer.close(); // Close the writer to prevent resource leaks
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        /*
         * This loop writes the data to the file in a parseable way. If there are more than ten scores, ignore all after 10
         */
        for (int i = 0; i < 10; i++) {
            
            // If there is no score at the point loction in the ArrayList, break
            if (i > highScores.size() -1) {
                break;
            }

            // Write the data
            try{
                writer.write(highScores.get(i).getName() + "," + String.valueOf(highScores.get(i).getScore()) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Finally close the writer
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method reads the data in the leaderBoard.txt file and stores it in the highScores ArrayList. An important note is that to make the data
     *   human readable as well as parsable, I seperated the scores with a new line, this the need for two delimiters in the scanner. This is denoted by the '|' in the
     *   useDelimiter call.
     */
    public static void readLeaderboard() {
        File leaderBoard = new File("leaderBoard.txt");
        Scanner reader;

        // Scanner requries try catch
        try {
            reader = new Scanner(leaderBoard);
            reader.useDelimiter(",|\\n"); // two delimiters seperated by a '|'

            /*
             *  While the file has a delimiter (',' or '\n'), read the string (the first data in the line) and the score (the rest of the data in the line)
             */
            while (reader.hasNext()) {
                String name = reader.next();
                int score = reader.nextInt();
                
                // If there is a name and a score
                if (!name.isEmpty() && score > 0) {
                    HighScore highScore = new HighScore(name, score);
                    highScores.add(highScore);
                }
            }

            // Finally close the reader
            reader.close();
        } catch (FileNotFoundException e) {
            // Not impleneted, If the file is not found a user will be added the empty list of high scores and a new list created.
        } 
    }

    /*
     * This method adds a new score to the highScore ArrayList. A popup is shown asking for the name of the scorer, the data is used to created a HighScore object, and that object
     *   if then added to the highScore ArrayList.
     */
    public static void addScore(int score) {
        String name = "";

        /*
         * While name is empty ask user for a name
         */
        while (name.isEmpty()) {
            name = JOptionPane.showInputDialog("New Highscore!\nPlease enter your name:");
            
            // If the name is empty show a popup
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Name cannot be empty!");
                continue;
            }

            HighScore highScore = new HighScore(name, score);
            highScores.add(highScore);
        }
    }

    /*
     * This method sorts the scores. I googled how to do this and the answer I choose came from:
     * https://www.bezkoder.com/java-sort-arraylist-of-objects/
     */
    public static void sortScores() {
        Collections.sort(highScores, Comparator.comparing(HighScore::getScore));
        Collections.reverse(highScores); // the comparator sorts in asending order, so a reverse is neccessary
    }

    /*
     * The main of the main class, all it does is create an instance of this Frame.
     */
    public static void main(String[] args) {
        robotPlatformer = new RobotPlatformer();
    }
}
