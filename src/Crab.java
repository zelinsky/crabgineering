public class Crab extends Player {
	
	private static final double SPEED = 10;
	private double currentSpeed = 0;
	private boolean hasTrash = false;
	private Trash heldTrash = null;
	
	private SpriteImage arrow = SpriteImage.ARROW; // Image of the trajectory arrow
	private boolean arrowVisible = false;
	private final int THROW_SPEED = -25;
	private final int ROTATE_SPEED = 10;
	private int xThrow = 0;
	private int yThrow = THROW_SPEED;
	private static final int CRAB_WIDTH = 100;
	private static final int CRAB_HEIGHT = 100;
	
	
	public Crab(int x, int y) {
		super(x, y, CRAB_WIDTH, CRAB_HEIGHT);
	}
	
	@Override
	public void processInput(String action) {
		switch (PlayerAction.valueOf(action)) {
			case MOVE_LEFT:
				currentSpeed = -SPEED;
				break;
			case MOVE_RIGHT:
				currentSpeed = SPEED;
				break;
			case STOP:
				currentSpeed = 0;
				break;
			case SPECIAL_ACTION:
				doAction();
				break;
			case ROTATE_TRASH_LEFT:
				if (hasTrash) {
					rotateArrow(-ROTATE_SPEED);
				}
				break;
			case ROTATE_TRASH_RIGHT:
				if (hasTrash) {
					rotateArrow(ROTATE_SPEED);
				}
				break;
		}
		
	}

	@Override
	public void update(double gravity, double drag){
		super.update(gravity,drag);
		translate(currentSpeed,0);
		if (hasTrash) {
			heldTrash.setLocation(bounds.getLocation().x,heldTrash.bounds.getLocation().y);
		}
	}
	
	@Override
	protected Sprite initSprite() {
		return new Sprite(SpriteImage.CRAB, getBounds());
	}
	
	public void doAction() {
		if (hasTrash) {
			// Fire trash
			heldTrash.toggleStopped();
			heldTrash.throwTrash(xThrow, yThrow);
			Controller.getModel().getThrownTrash().add(heldTrash);
			heldTrash = null;
			hasTrash = false;
			arrowVisible = false;
		}
	}
	
	public void touchTrash(Trash t) {
		if (!t.atBottom() && !t.thrown() && !hasTrash) {
			hasTrash = true;
			t.toggleStopped();
			arrowVisible = true;
			heldTrash = t;
		}
	}
	
	public void rotateArrow(int rotation) {
		// ROTATE TRAJECTORY ARROW AND CHANGE xThrow and yThrow ACCORDINGLY
	}
	
	
	public boolean arrowVisible() {
		return arrowVisible;
	}
	
}
