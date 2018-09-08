package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;

public class MovementManager {
	private final ObservableValue<Float> x, y, direction;

	public MovementManager(ObservableValue<Float> x, ObservableValue<Float> y, ObservableValue<Float> direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	private class Push {
		private final float amount, direction;

		public Push(float amount, float direction) {
			this.amount = amount;
			this.direction = direction;
		}

	}

	private Push currentPush = new Push(0, 0);

	public void push(float amount, float direction) {

	}

	public void stopPushing(float amount, float direction) {
		push(-amount, direction);
	}

	public void calculate(float delta) {
		// Total delta over the span of a second, will be one. That means, by using the
		// raw amount from the push of a keyboard key, the entity will move at 1 tile
		// per second.
		if (currentPush != null) {
			// TODO Calculate.
		}
	}

}
