import java.awt.*;

abstract class Entity {
	
	private Rectangle bounds;
	private double xVel;
	private double yVel;
	private final double gravity;
	private final String imageReference;
	private int currentHealth;
	private final int maxHealth;
	
	Entity() {
		int width = 10;
		int height = 10;
		int initialX = 10;
		int initialY = 10;
		bounds = new Rectangle(initialX, initialY, width, height);
		xVel = 0;
		yVel = 0;
		gravity = .5;
		imageReference = "TEST_IMAGE";
		currentHealth = 10;
		maxHealth = 10;
	}
	
	void move(double x, double y) {
		this.setBounds(new Rectangle((int) x, (int) y, bounds.width, bounds.height));
	}
	
	boolean intersects(Entity e) {
		return this.bounds.intersects(e.bounds);
	}
	
	Rectangle getBounds() {
		return bounds;
	}
	
	int getCurrentHealth() {
		return currentHealth;
	}
	
	int getMaxHealth() {
		return maxHealth;
	}
	
	public void draw(Graphics g, Rectangle bounds) {
		g.setColor(Color.pink);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	void update() {
		yVel += gravity;
		
		double newX = this.bounds.x + xVel;
		double newY = this.bounds.y + yVel;
		
		if (bounds.y + bounds.height >= Controller.getModel().getWorldHeight() - bounds.getHeight()) {
			if (yVel <= 0) {
			} else {
				yVel = 0;
				newY = Controller.getModel().getWorldHeight() - bounds.height;
			}
		}
		
		this.move(newX, newY);
	}
}
