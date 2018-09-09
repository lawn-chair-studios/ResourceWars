package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;

public class MovementManager {
	private final ObservableValue<Float> x, y, direction, speed;

	public MovementManager(ObservableValue<Float> x, ObservableValue<Float> y, ObservableValue<Float> direction,
			ObservableValue<Float> speed) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = speed;
	}

	// TODO Batching pushes may be faster.

	/**
	 * Should be called each frame with the amount to be moved. This method can be
	 * called multiple times each frame (perhaps if two inputs are receiving input).
	 * 
	 * @param amount    The amount to be moved.
	 * @param direction The direction to be moved.
	 */
	public void push(float amount, float direction) {
		// TODO Calculate
	}

	public void stopPushing(float amount, float direction) {
		push(-amount, direction);
	}

}
