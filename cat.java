package RobotPlatformer;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.geom.AffineTransform;

public class cat {

    // privtae variable to hold the direction the cat is facing and moving
    private enum xDirection {
        RIGHT,
        LEFT;
    }

    // to hold the image of the cat
    private BufferedImage image;

    private int x, y, size;
    private final int movementSpeed = 5;
    private final xDirection direction;

    // this is to handle I-frames when the player is hit
    private boolean damagedPlayer = false;

    // Only constructor
    public cat(int width, int y, int size) {
        this.y = y;
        this.size = size;

        // Randomly select direction
        Random random = new Random();
        int randomDirection = random.nextInt(2);

        if (randomDirection == 1) {
            direction = xDirection.LEFT;
            x = width;
        } else {
            direction = xDirection.RIGHT;
            x = 0;
        }
        
        try {
            image = ImageIO.read(new File("KnifeCat.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters

    // The current X value of the cat
    public int getX() {
        return this.x;
    }

    // Wither or not the player has been damaged since the 
    public boolean getDamagedPlayer() {
        return damagedPlayer;
    }

    // this is to check wither or not the cat is intersecting with any other bounds
    public Rectangle getBoundingBox() {
        return new Rectangle(x + size / 3, y, size - size / 3, size);
    }

    /*
     * This method draws the cat based on the image. It also handles flipping the image if
     *   the cat should be facing right. There is a commented out section of the code to be used
     *   should I need to revisit collision detection, this helps visualize the bounds of the 
     *   cat.
     */
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (direction == xDirection.RIGHT) {
            g2d.scale(-1, 1);  // Flip horizontally
            g2d.translate(-2 * (x + size / 3), 0);  // Adjust translation after scaling
        }

        if (image != null) {
            g.drawImage(image, x, y, size, size, null);
            
            // Draw the bounding box
            //Rectangle boundingBox = getBoundingBox();
            //g.setColor(Color.RED); // Use a distinct color to highlight the bounding box
            //g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, size, size);
        }

        g2d.setTransform(new AffineTransform()); // reset the transform otherwise anything else drawn will be ties to the cats movement.
    }

    /*
     * This method handles the cats movements. As the cat doesn't jump, gravity need not be applied.
     */
    public void moveCat() {
        switch (direction) {
            case LEFT:
                this.x -= movementSpeed;
                break;
            case RIGHT:
                this.x += movementSpeed;
                break;
        }
    }

    // Setters
    
    public void setDamagedPlayer(boolean damaged) {
        this.damagedPlayer = damaged;
    }

}
