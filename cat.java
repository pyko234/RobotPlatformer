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

    private enum xDirection {
        RIGHT,
        LEFT;
    }

    private BufferedImage image;

    private int x, y, size;
    private final int movementSpeed = 5;
    private final xDirection direction;

    private boolean damagedPlayer = false;

    public cat(int width, int y, int size) {
        this.y = y;
        this.size = size;

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

    public int getX() {
        return this.x;
    }

    public boolean getDamagedPlayer() {
        return damagedPlayer;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x + size / 3, y, size - size / 3, size);
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (direction == xDirection.RIGHT) {
            g2d.scale(-1, 1);  // Flip horizontally
            g2d.translate(-2 * (x + size / 3), 0);  // Adjust translation after scaling
        }

        if (image != null) {
            g.drawImage(image, x, y, size, size, null);
            
            // Draw the bounding box
            Rectangle boundingBox = getBoundingBox();
            g.setColor(Color.RED); // Use a distinct color to highlight the bounding box
            g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, size, size);
        }

        g2d.setTransform(new AffineTransform());
    }

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

    public void setDamagedPlayer(boolean damaged) {
        this.damagedPlayer = damaged;
    }

}
