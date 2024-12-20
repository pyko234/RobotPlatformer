package RobotPlatformer;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Robot {

  	// New Variable type to denote which direction the robot is facing
  	private enum Direction {
		LEFT,
		RIGHT;
  	}

  	private int health = 10;

  	private int x, y, size; // size exists in case I need to adjust the size of the robot
  	private final int movementSpeed = 20;
  	private Direction directionFacing;

  	// Handle Gravity
  	private final int jumpHeight;
  	private final int gravity = 1;
  	private int groundY; // This will be replaced once a ground and collision detection have been created
  	public int verticalVelocity = 0;
  	private boolean isJumping = false;

  	// Default Constructor
  	public Robot(int x, int y, int size, int jumpHeight) {
		this.x = x;
		this.y = y;
		this.groundY = y;
		this.size = size;
		this.directionFacing = Direction.LEFT;
		this.jumpHeight = jumpHeight;
  	}

  	// Getters

  	// Get the bounding box used for collision detection
  	public Rectangle getBoundingBox() {
		return new Rectangle(x, y, size - size / 4, size - size / 4);
  	}

  	public int getHealth() {
		return this.health;
  	}
  
  	public int getX() {
		return this.x;
  	}

  	public void takeDamage(int damage) {
		this.health -= damage;
  	}

  	// When moving right, ensure the direction is RIGHT
  	public void moveRight() {
		x += movementSpeed;
		directionFacing = Direction.RIGHT;
  	}

  	// When moving left, ensure the direction is LEFT
  	public void moveLeft() {
		x -= movementSpeed;
		directionFacing = Direction.LEFT;
  	}

  	// Handle jumping
  	public void jump() {
		if (!isJumping){
	  		verticalVelocity = -jumpHeight; // Negative velocity is up
	  		isJumping = true; // Set jumping to true to prevent double or inifinte jumping
		}
  	}

  	/*
  	 * This method handles applying gravity to the robots verticalVelocity. This fires on every pulse of the game clock.
  	 */
  	public void applyGravity() {
		y += verticalVelocity; // Add the current vertical Velocity to the y
		verticalVelocity += gravity; // Add gravity to vertical Velocity (which is negative until the robot starts falling)

		// If the robot is on the ground
		if (y >= groundY) {
		  	y = groundY; // Set the robot to the ground
		  	verticalVelocity = 0;  // Reset the vertical Velocity
		  	isJumping = false;  // Reset the jumping so the robot can jump again
		}
  	}

  	/*
  	 * This calls the drawRobot function after determining which direction the robot is facing.  
  	 */
  	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Only flip for RIGHT direction
		if (directionFacing == Direction.RIGHT) {
			g2d.scale(-1, 1);  // Flip horizontally
			g2d.translate(-2 * (x + size / 3), 0);  // Adjust translation after scaling
		}
	
		drawRobot(g);  // Draw the robot

		// Reset transformations for further drawing
		g2d.setTransform(new AffineTransform());
	}

  	/*
  	 * This method actually draws the robot. The commented out lines of code are to draw the bounding box of the robot to
  	 *   visualize the robot's bounding box.
  	 */  
  	private void drawRobot(Graphics g) {
		// Determines the fractional sizes of the different robots proportions.
		int headSize = size / 4;
		int bodyWidth = size / 2;
		int bodyHeight = size / 3;

		// Draw the bounding box
		//Rectangle boundingBox = getBoundingBox();
		//g.setColor(Color.RED); // Use a distinct color to highlight the bounding box
		//g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);

		// Head
		g.setColor(Color.GRAY);
		g.fillRect(x + size / 4, y, headSize, headSize);  // Head rectangle

		// Eyes
		g.setColor(Color.BLACK);
		g.fillOval(x + size / 4 + headSize / 6, y + headSize / 4, headSize / 6, headSize / 4);  // Left eye
		g.fillOval(x + size / 4 + headSize / 2, y + headSize / 4, headSize / 6, headSize / 4);  // Right eye

		// Antenna
		g.setColor(Color.DARK_GRAY);
		int[] antennaX = {x + size / 4 + headSize / 2, x + size / 4 + headSize / 2 - headSize / 5, x + size / 4 + headSize / 2 + headSize / 4};
		int[] antennaY = {y - headSize / 5, y, y};
		g.fillPolygon(antennaX, antennaY, 3);

		// Body
		g.fillRect(x + size / 4 - bodyWidth / 4, y + headSize, bodyWidth, bodyHeight);  // Body rectangle

		// Arms
		g.setColor(Color.GRAY);
		g.fillRect(x + size / 4 - bodyWidth / 4 - size / 10, y + headSize + bodyHeight / 6, size / 10, bodyHeight / 2);  // Left arm
		g.fillRect(x + bodyWidth + bodyWidth / 4, y + headSize + bodyHeight / 6, size / 10, bodyHeight / 2);  // Right arm

		// Legs
		g.setColor(Color.GRAY);
		g.fillRect(x + size / 4 - bodyWidth / 4, y + headSize + bodyHeight, bodyWidth / 4, size / 6);  // Left leg
		g.fillRect(x + size / 4 + bodyWidth / 2, y + headSize + bodyHeight, bodyWidth / 4, size / 6);  // Right leg
	}
}
