package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * A class that handles the displaying of an {@link AbstractEntity Entity} at a
 * certain orientation.
 * 
 * @author Zeale
 *
 */
public abstract class DirectionHandler implements Comparable<DirectionHandler> {
	private final float direction;

	public DirectionHandler(float direction) {
		this.direction = direction;
	}

	public float getDirection() {
		return direction;
	}

	@Override
	public int compareTo(DirectionHandler o) {
		return ((Float) direction).compareTo(o.direction);
	}

	public abstract void render(Batch batch);

}