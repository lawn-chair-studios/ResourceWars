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

	private static class Push {

		private static final Push NONE = new Push(0, 0);

		private float amount, direction;

		public Push(float amount, float direction) {
			this.amount = amount;
			this.direction = direction;
		}

	}

	private Push currentPush = Push.NONE;

	/**
	 * Sets the {@link #currentPush} value to nothing, (<code>0</code> push towards
	 * direction <code>0</code>). This can be used in the case that a rounding error
	 * or related issue is causing movement, when it shouldn't be.
	 */
	public void clearMovement() {
		currentPush = Push.NONE;
	}

	public Push getCurrentPush() {
		return currentPush;
	}

	public void setCurrentPush(float amount, float direction) {
		clearMovement();
		push(amount, direction);
	}

	public void push(float amount, float direction) {
		//The entity will always travel at 
	}

	public void stopPushing(float amount, float direction) {
		push(-amount, direction);
	}

	public void calculate(float delta) {
		// Total delta over the span of a second, will be one. That means, by using the
		// raw amount from the push of a keyboard key, the entity will move at 1 tile
		// per second.
	}

}
