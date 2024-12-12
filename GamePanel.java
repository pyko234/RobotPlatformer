package RobotPlatformer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {

	// Custom objects
	private Robot robot;
	private coin coin;
	private cat cat;

	private int width, jumpHeight, robotScale, coinScale, catScale, groundY;

	private int numCoins = 0;

	// Custom inner class
	private static UIPanel ui;

	// The Vairable for the background of the game panel
	private Image backgroundImage;

	// The game timer
	private Timer timer = new Timer(33, e -> {gameLoop();}); // e-> is a lambda function

	// Default Constructor
	public GamePanel(int width, int height) {
		this.width = width;

		/*
			This math handles the scaling. The scaling is not perfect as the image is not properly scaled. If I had created all the imagery used, this would need to be fixed
			but as this image was not made with this foresight in mind, I don't know of a way to perfectly scale the image.
		*/ 
		groundY = height - (int)(height / 4.0);
		robotScale = (int)(height / 6.0);
		coinScale = (int)(height / 9.0);
		catScale = (int)(height / 9.0);
		jumpHeight = (int)(height / 35.0);

		// Create the custom objects
		robot = new Robot(width / 2, groundY, robotScale, jumpHeight);
		cat = new cat(width, groundY, catScale);
		coin = new coin(new Random().nextInt(0, width - coinScale), new Random().nextInt(groundY - jumpHeight, groundY), coinScale);

		ui = new UIPanel(width, height);
		
		// Extra setup for this panel
		addKeyListener(this);
		setFocusable(true);
		add(ui);

		// Read the background image
		try {
			backgroundImage = ImageIO.read(new File("Designer.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Finally start the game
		startGame();
	}

	/*
	 * This method handles the gameloop. It applys gravity to the robot, moves the cat, checks the collision, and repaints the panel
	 *   The final if statements generates a new cat if the current cat has excedded the bounds of the screen.
	 */
	private void gameLoop() {
		robot.applyGravity();
		cat.moveCat();
		checkCollision();
		repaint();

		// The improper scaling prevents the cat from being perfectly off the screen when this triggers in both directions. Further proving the scaling needs to be revisited.
		if (cat.getX() >= getWidth() || cat.getX() <= 0) {
			cat = new cat(width, groundY, catScale);
		} 
	}

	/*
	 * The method starts the game. It first has to grab the focus of the keyboard to trigger the keyboard.
	 */
	public void startGame() {
		grabFocus();
		timer.start();
	}

	/*
	 * This paints the game panel.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Paint the background
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
		
		/*
		  This generates a new coin once the current one is collected. The Y was acting up and kept drawing the coins outside of the jump height of
		  the robot. The current math tends to only draw the coin at the same height as the robot on level ground, so this is a pleasant workaround. 
		*/
		if (coin.getCollected()) {
			coin = new coin(new Random().nextInt(0, width - coinScale), new Random().nextInt(groundY - jumpHeight, groundY), coinScale);
		}

		// Draw all of the objects
		coin.draw(g);
		robot.draw(g);
		cat.draw(g);
	}

	/*
	 * This method handles all of the key presses. Having the movement here allows for immediate feedback. If the movement was handled in the other required methods for the 
	 *   KeyListener, it would require more methods on the robot. One to increase the speed while the key is pressed (to a set amount), one to decrease the speed when the key
	 *   is released. Interestingly, the jump function could still remain here, unless you wanted a mario like jump mechanic where the longer you hold the key the higher the 
	 *   character jumps.
	 * 	
	 * One improvement that could be made here is from the scaling again, as the robots bounds are not perfectly align with its imagery.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			// Double the keys to allow for multiple input keys
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_W:
				robot.jump();
				break;

			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				// Ensure the robot never goes off the screen
				if (robot.getX() + robot.getBoundingBox().width < getWidth()) {
					robot.moveRight();
				}

				break;

			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				// Ensure the robot never goes off screen
				if (robot.getX() > 0) {
					robot.moveLeft();
				}

				break;
		}
		repaint();
	}

	// These methods are required but never used in this context.
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	/*
	 * This method handles when the objects collide with one another on the screen. Specific logic for each will be descrived above the logic itself.
	 */
	private void checkCollision() {
		/*
		 *  If the robot's bounding box intersects the coin's bounding box, add coin to the int holding the number of coins collected,
		 *    set the coins collected boolean to true to prevent mulitple collectoins from the same coin and as the flag to make a new coin,
		 *    and finally update the UI with the proper numCoins.
		 */
		if (robot.getBoundingBox().intersects(coin.getBoundingBox())) {
			numCoins++;
			coin.setCollected(true);
			ui.updateLabel(numCoins);
		}

		/*
		 *  If the robot's bounding box intersects the cat's bounding box AND the cat hasn't damaged the player yet, then the robot should take 1 point of damage,
		 *    update the health bar in the UI, and set the cat's DamagedPlayer to true so the cat can't hurt the robot more than once per collision.
		 */
		if (robot.getBoundingBox().intersects(cat.getBoundingBox()) && !cat.getDamagedPlayer()) {
			robot.takeDamage(1);
			ui.updateHealthBar(robot.getHealth());
			cat.setDamagedPlayer(true);
		}

		/*
		 *  If the robot's bounding box leaves the intersection of the cat's bounding box AND the cat has damaged the player, reset the cat's ability to hurt
		 *    the robot. 
		 */
		if (!robot.getBoundingBox().intersects(cat.getBoundingBox()) && cat.getDamagedPlayer()) {
			cat.setDamagedPlayer(false);
		}

		/*
		 *  If after the robot runs out of health, stop the timer and call the game over function from the main class.
		 */
		if (robot.getHealth() == 0) {
			timer.stop();
			RobotPlatformer.gameOver(numCoins);
		}
	}
}
