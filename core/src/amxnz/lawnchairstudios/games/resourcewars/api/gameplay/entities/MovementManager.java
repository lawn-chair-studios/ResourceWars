package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;

/**
 * A class that handles movement (usually for an entity).
 *
 * @author Zeale
 *
 */
public class MovementManager {

	// Note that with this API, holding W and D (to move upwards and to the right),
	// will make the character run faster (at 1.414 m/s) than if they were to go
	// upwards and to the right with a controller (in which case they'd travel at 1
	// m/s).

	public static int NORTH = 90, SOUTH = 270, EAST = 0, WEST = 180, NORTH_EAST = 45, NORTH_WEST = 135,
			SOUTH_WEST = 225, SOUTH_EAST = 315;

	private final ObservableValue<Float> x, y, orientation, speed;

	private float cx, cy;

	public MovementManager(ObservableValue<Float> x, ObservableValue<Float> y, ObservableValue<Float> orientation,
			ObservableValue<Float> speed) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.speed = speed;
	}

	/**
	 * Should be called once each frame, after all {@link #push(float, float)}
	 * operations are called, to calculate and apply the pushes to {@link #x},
	 * {@link #y}, and {@link #orientation}.
	 */
	public void calculate() {
		cx *= speed.getValue();
		cy *= speed.getValue();

		move(x.getValue(), y.getValue(), x.getValue() + cx, y.getValue() + cy);
		orient(cx, cy);
		cx = cy = 0;
	}

	protected void move(float fx, float fy, float tx, float ty) {
		x.setValue(tx);
		y.setValue(ty);
	}

	protected final void setX(float x) {
		this.x.setValue(x);
	}

	protected final void setY(float y) {
		this.y.setValue(y);
	}

	protected final void orient(float cx, float cy) {
		float dir = (float) Math.toDegrees(Math.atan(cy / cx));
		if (cx < 0)
			dir += 180;
		orientation.setValue(dir);
	}

	private float getXByCos(float amount, float direction) {
		return (float) (Math.cos(Math.toRadians(direction)) * amount);
	}

	public void pushComp(float x, float y) {
		cx += x;
		cy += y;
	}

	/**
	 * Should be called with the amount to be moved. This method can be called
	 * multiple times each frame (perhaps if two inputs are receiving input, or
	 * something similar).
	 *
	 * @param amount    The amount to be moved.
	 * @param direction The direction to be moved.
	 */
	public void pushVec(float amount, float direction) {
		pushComp(getXByCos(amount, direction), (float) (Math.sin(Math.toRadians(direction)) * amount));
	}

}
