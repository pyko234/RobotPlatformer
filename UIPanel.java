package RobotPlatformer;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel {
    private Font font;
    private JLabel coinsLabel = new JLabel("Coins: 0");
    private JLabel healthLabel = new JLabel("Health: ");
    private HealthBar bar = new HealthBar(10); // Custom inner class
    private Color color = Color.YELLOW;

    // Default Constructor
    public UIPanel(int width, int height) {
        // Basic setup for the panel
        font = new Font("Comic Sans MS", Font.PLAIN, height / 25); // This might be different on windows
        setOpaque(false); // This is to allow the panel to be translucent
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Set the Y alignment of the components
        coinsLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        healthLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Add a border for the coinsLabel (this border seperate both the other label and the health bar)
        coinsLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 10, 0, width / 4));

        // Set the componenets fonts and colors
        coinsLabel.setForeground(color);
        healthLabel.setForeground(color);
        coinsLabel.setFont(font);
        healthLabel.setFont(font);
    
        // Add the two components 
        add(coinsLabel);
        add(healthLabel);
    }

    /*
     * This method draws the panel and the health bar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bar.draw(g);
    }

    /*
     * This method updates the coin label.
     */
    public void updateLabel(int numCoins) {
        coinsLabel.setText("Coins: " + numCoins);
    }

    /*
     * This method calls the inner method to set the health in the health bar object.
     */
    public void updateHealthBar(int health) {
        bar.setHealth(health);
    }

    /*
     * Inner class for the health bar object. A future note, this assumes a static health of 10, it could be refactored to allow for more than 10 health.
     */
    private class HealthBar {
        private int health;

        // Default Constructor
        public HealthBar(int health) {
            this.health = health;
        }

        // Setter
        public void setHealth(int health) {
            this.health = health;
        }

        // Draw the health bar
        public void draw(Graphics g) {
            Rectangle healthRectangle = healthLabel.getBounds();
            healthRectangle.x += healthRectangle.width;
            healthRectangle.width = getWidth() / 3;

            g.setColor(Color.RED);
            g.fillRect(healthRectangle.x, healthRectangle.y, healthRectangle.width, healthRectangle.height);

            g.setColor(Color.GREEN);
            g.fillRect(healthRectangle.x, healthRectangle.y, (int)(getWidth() / 3 * health / 10.0), healthRectangle.height); // I hate scaling so much....
        }
    }
}
