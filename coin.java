package RobotPlatformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class coin {
    private final int x, y, size;
    private boolean collected;

    public coin(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.collected = false;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    } 

    public boolean getCollected() {
        return this.collected;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, size / 2, size);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, size / 2, size);

        // Draw the bounding box
        Rectangle boundingBox = getBoundingBox();
        g.setColor(Color.RED); // Use a distinct color to highlight the bounding box
        g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
    
}
