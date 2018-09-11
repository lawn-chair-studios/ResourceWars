package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * A class that handles the displaying of an {@link AbstractEntity Entity} at a
 * certain orientation.
 * 
 * @author Zeale
 *
 */
public abstract class OrientationHandler implements Comparable<OrientationHandler> {
	private final float targetOrientation;

	public OrientationHandler(float targetOrientation) {
		this.targetOrientation = targetOrientation;
	}

	public float getDirection() {
		return targetOrientation;
	}

	@Override
	public int compareTo(OrientationHandler o) {
		return ((Float) targetOrientation).compareTo(o.targetOrientation);
	}

	public abstract void render(Batch batch);

}