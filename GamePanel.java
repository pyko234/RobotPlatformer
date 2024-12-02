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

	private Robot robot;
	private coin coin;
	private cat cat;

	private int width, jumpHeight, robotScale, coinScale, catScale, groundY;

	private int numCoins = 0;

	private static UIPanel ui;

	private Image backgroundImage;

	private Timer timer = new Timer(33, e -> {gameLoop();});

	public GamePanel(int width, int height) {
		this.width = width;

		groundY = height - (int)(height / 4.0);
		robotScale = (int)(height / 6.0);
		coinScale = (int)(height / 9.0);
		catScale = (int)(height / 9.0);
		jumpHeight = (int)(height / 35.0);

		robot = new Robot(width / 2, groundY, robotScale, jumpHeight);
		cat = new cat(width, groundY, catScale);
		coin = new coin(new Random().nextInt(0, width - coinScale), new Random().nextInt(groundY - jumpHeight, groundY), coinScale);

		ui = new UIPanel(width, height);
		
		addKeyListener(this);
		setFocusable(true);
		add(ui);

		try {
			backgroundImage = ImageIO.read(new File("Designer.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		startGame();
	}

	private void gameLoop() {
		robot.applyGravity();
		cat.moveCat();
		checkCollision();
		repaint();

		if (cat.getX() >= getWidth() || cat.getX() <= 0) {
			cat = new cat(width, groundY, catScale);
		} 
	}

	public void startGame() {
		grabFocus();
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
		
		if (coin.getCollected()) {
			coin = new coin(new Random().nextInt(0, width - coinScale), new Random().nextInt(groundY - jumpHeight, groundY), coinScale);
		}

		coin.draw(g);
		robot.draw(g);
		cat.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
			case KeyEvent.VK_W:
				robot.jump();
				break;

			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				if (robot.getX() + robot.getBoundingBox().width < getWidth()) {
					robot.moveRight();
				}

				break;

			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
			
				if (robot.getX() > 0) {
					robot.moveLeft();
				}

				break;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	private void checkCollision() {
		if (robot.getBoundingBox().intersects(coin.getBoundingBox())) {
			numCoins++;
			coin.setCollected(true);
			ui.updateLabel(numCoins);
		}

		if (robot.getBoundingBox().intersects(cat.getBoundingBox()) && !cat.getDamagedPlayer()) {
			robot.takeDamage(1);
			ui.updateHealthBar(robot.getHealth());
			cat.setDamagedPlayer(true);
		}

		if (!robot.getBoundingBox().intersects(cat.getBoundingBox()) && cat.getDamagedPlayer()) {
			cat.setDamagedPlayer(false);
		}

		if (robot.getHealth() == 0) {
			timer.stop();
			RobotPlatformer.gameOver(numCoins);
		}
	}
}
