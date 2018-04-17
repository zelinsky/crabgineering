import java.awt.*;

abstract class Entity {

	//note: x counts pixels left of the left-hand side of the window
	//      y counts pixels down from the top of the window
	private Rectangle bounds;
	private double dx;
	private double dy;
	
    private final String imageReference;
	private int currentHealth;
	private final int maxHealth;

	//double trashRate = 1;
	
	Entity(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		dx = 0;
		dy = 0;
		imageReference = "TEST_IMAGE";
		currentHealth = 10;
		maxHealth = 10;
	}
	
	void setLocation(int x, int y) {
		bounds.setLocation(x, y);
	}

	void translate(int dx, int dy) {
		bounds.translate(dx, dy);
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
	
	public void draw(Graphics g) {
		g.setColor(Color.pink);
		g.fillRect((int) bounds.getX(), 
				   (int) bounds.getY(), 
				   (int) bounds.getWidth(), 
				   (int) bounds.getHeight());
	}
	
	void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	void update(Rectangle worldBounds, double gravity) {
		//apply gravity
		dy += gravity;
		
		double x = this.bounds.getX() + dx;
		double y = this.bounds.getY() + dy;
		
		if (dy > 0
		    && bounds.getY() + bounds.getHeight() >= worldBounds.getHeight() - bounds.getHeight()) {
				dy = 0;
				y = worldBounds.getHeight() - bounds.getHeight();
		}
		
		if (dx > 0
		    && bounds.getX() + bounds.getWidth() >= worldBounds.getWidth() - bounds.getWidth()) {
				dx = 0;
				x = worldBounds.getWidth() - bounds.getWidth();
		}
		
		setLocation((int) x, (int) y);
	}
}
