package RobotPlatformer;

/* 
 * This is a small class not really requiring commenting. It's all pretty self explanatory.
 */
public class HighScore {

    private String name;
    private int score;

    // Constructing the HighScore with the name and int as arguments prevents the need for setters.
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
    
}
