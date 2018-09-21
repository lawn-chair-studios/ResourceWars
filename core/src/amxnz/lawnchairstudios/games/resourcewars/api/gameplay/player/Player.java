package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.player;

import com.badlogic.gdx.graphics.Camera;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities.AbstractEntity;

/**
 * Represents the player in-game. Stores a reference to an entity.
 *
 * @author Zeale
 *
 */
public class Player {

	private AbstractEntity inGameCharacter;

	public Player(AbstractEntity inGameCharacter) {
		setInGameCharacter(inGameCharacter);
	}

	/**
	 * Binds the camera to this {@link Player}'s underlying entity.
	 *
	 * @param camera The camera that will follow the entity.
	 */
	public void bindToCamera(Camera camera) {
		getInGameCharacter().cameraBind(camera, true);
	}

	public AbstractEntity getInGameCharacter() {
		return inGameCharacter;
	}

	public void setInGameCharacter(AbstractEntity inGameCharacter) {
		if (inGameCharacter == null)
			throw new RuntimeException("A Player's entity cannot be null.");
		this.inGameCharacter = inGameCharacter;
	}

	/**
	 * Unbinds the camera from this {@link Player}'s underlying entity.
	 *
	 * @param camera The camera that will no longer follow the entity.
	 */
	public void unbindFromCamera(Camera camera) {
		getInGameCharacter().cameraBind(camera, false);
	}

}
