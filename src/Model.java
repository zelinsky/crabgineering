import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Model implements RequestListener {
	//listeners
	RequestQueue requestQueue;

	//constants relevant to simulation's physics
	private final Bounds worldBounds;
	private final double GRAVITY = .05;
	private final double DRAG = .01;

	//objects in simulation
	private ArrayList<Entity> entities = new ArrayList<>();
	private TrashSpawner spawner;
	private Player player;
	private ArrayList<Trash> thrownTrash = new ArrayList<>();
	private ArrayList<Trash> toRemove = new ArrayList<>();

	//game variables
	private int currentPollutionLevel = 0;
	static final int MAXPOLLUTIONLEVEL = 100;
	private final int SCOREINCREMENT = 10;
	private int score = 0;

	/**
	 * Initialize the model, i.e. add any starting enemies and things that start with the world
	 */
	Model(Bounds worldBounds,
		  //Controller.AddedEntityListener addedEntityListener,
		  //Controller.RemovedEntityListener removedEntityListener,
		  RequestQueue requestQueue) {
	    this.worldBounds = worldBounds;
	    //this.addedEntityListener = addedEntityListener;
	    //this.removedEntityListener = removedEntityListener;

	    this.requestQueue = requestQueue;

	    //setup the RequestQueue Entities can use to post requests
		//for the Model
	    requestQueue.addListener(this::handleRequest);

		//Crab crabby = new Crab(10,10,100,100);
		//addEntity(crabby);
		TrashFactory t = new TrashFactory();

		//addEntity(t.createEasyTrash(400,50));
		//addEntity(t.createHardTrash(300,0));
		int crabInitialX = worldBounds.width / 2 - Crab.CRAB_WIDTH / 2;
		int crabInitialY = worldBounds.height / 2 - Crab.CRAB_HEIGHT / 2;
		player = new Crab(crabInitialX, crabInitialY, requestQueue);
		addEntity(player);

		int spawnInterval = 2 * 1000;
		int spawnHeight = 0;
		spawner = new TrashSpawner(requestQueue,
				                   spawnHeight,
				                   (int) worldBounds.getWidth(),
				                   spawnInterval);
		spawner.start();
		currentPollutionLevel = 0;
	}

	@Override
	public void handleRequest(Request request) {
		switch (request.getRequestedAction()){
			case ADD:
				if (request.getSpecifics() instanceof Entity)
					addEntity((Entity) request.getSpecifics());
				break;
			case REMOVE:
				if (request.getSpecifics() instanceof Entity)
					removeEntity((Entity) request.getSpecifics());
				break;
		}
	}

	/**
	 * Update the model, i.e. process any entities in the world for things like GRAVITY
	 */
	public void update() {
		for (Entity entity : entities) {
			entity.update(GRAVITY, DRAG);
		}

		//Check for player-trash collision and trash-trash collision
		for (Entity entity : entities) {
			if (entity instanceof Trash) {
				Trash trash = (Trash) entity;
				if (player.intersects(trash)) {
					player.touchTrash(trash);
				}

				for (Trash tt : thrownTrash) {
					if (entity.intersects(tt) && !entity.atBottom() && !trash.thrown()) {
						toRemove.add(trash);
						toRemove.add(tt);
						incrementScore(3);
					}
				}
			}
		}

		// Remove to-be-removed trash; prevents modifying ArrayList while iterating through
		for (Trash t : toRemove) {
			removeEntity(t);
			thrownTrash.remove(t);
		}
		toRemove.clear();

		// Check end game
		if (currentPollutionLevel == MAXPOLLUTIONLEVEL) {
			endGame();
		}

	}

	void endGame() {

		//TODO
		Controller.endGame();
	}

	public void incrementScore(int modifier) {
		score += SCOREINCREMENT * modifier;
		requestQueue.postRequest(new Request<>(
				score,
				Request.ActionType.UPDATE_SCORE
		));
	}

	public int getScore() {
		return score;
	}

	public void addEntity(Entity entity) {
		//add the Entity, and let it react to being added
		entity.setWorldBounds(worldBounds);
		entities.add(entity);

		//create the corresponding sprite
		Sprite sprite = new EntitySprite(entity);

		//and post a request for it to be added to the view
		requestQueue.postRequest(new Request<>(
    			sprite,
				Request.ActionType.ADD
		));
	}

    public void removeEntity(Entity entity) {
		entities.remove(entity);

		//remove any Sprites that are following the entity's movements
		for (BoundsListener listener: entity.getBounds().getListeners()) {
			if (listener instanceof Sprite)
				requestQueue.postRequest(new Request<>(
						(Sprite) listener,
						Request.ActionType.REMOVE
				));
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public ArrayList<Trash> getThrownTrash() {
		return thrownTrash;
	}
	
	public ArrayList<Trash> toRemove() {
		return toRemove;
	}
	
	// returns new pollution level
	int addToPollutionLevel(int addition) {
		this.currentPollutionLevel += addition;
		requestQueue.postRequest(new Request<>(
				currentPollutionLevel,
				Request.ActionType.UPDATE_POLLUTION));
		return this.currentPollutionLevel;
	}
	
	int getCurrentPollutionLevel() {
		return this.currentPollutionLevel;
	}
	
	int getMaxPollutionLevel() {
		return MAXPOLLUTIONLEVEL;
	}
	
	Rectangle getWorldBounds() {
		return worldBounds;
	}
}