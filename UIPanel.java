package RobotPlatformer;

import javax.swing.*;
import java.awt.*;

public class UIPanel extends JPanel {
    private Font font;
    private JLabel coinsLabel = new JLabel("Coins: 0");
    private JLabel healthLabel = new JLabel("Health: ");
    private HealthBar bar = new HealthBar(10);
    private Color color = Color.YELLOW;

    public UIPanel(int width, int height) {
        font = new Font("Comic Sans MS", Font.PLAIN, height / 25); // This might be different on windows
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        coinsLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        healthLabel.setAlignmentY(Component.TOP_ALIGNMENT);

        coinsLabel.setBorder(BorderFactory.createEmptyBorder(0, width / 10, 0, width / 4));

        coinsLabel.setForeground(color);
        healthLabel.setForeground(color);
        coinsLabel.setFont(font);
        healthLabel.setFont(font);
    
        add(coinsLabel);
        add(healthLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bar.draw(g);
    }

    public void updateLabel(int numCoins) {
        coinsLabel.setText("Coins: " + numCoins);
    }

    public void updateHealthBar(int health) {
        bar.setHealth(health);
    }

    private class HealthBar {
        private int health;

        public HealthBar(int health) {
            this.health = health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public void draw(Graphics g) {
            Rectangle healthRectangle = healthLabel.getBounds();
            healthRectangle.x += healthRectangle.width;
            healthRectangle.width = getWidth() / 3;

            g.setColor(Color.RED);
            g.fillRect(healthRectangle.x, healthRectangle.y, healthRectangle.width, healthRectangle.height);

            g.setColor(Color.GREEN);
            g.fillRect(healthRectangle.x, healthRectangle.y, (int)(getWidth() / 3 * health / 10.0), healthRectangle.height);
        }
    }
}
