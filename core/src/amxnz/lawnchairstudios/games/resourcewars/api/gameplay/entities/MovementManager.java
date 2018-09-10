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

	private float cx, cy;

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

	private float getXByCos(float amount, float direction) {
		return (float) (Math.cos(Math.toRadians(direction)) * amount);
	}

	@SuppressWarnings("unused")
	private float getXBySqrt(float amount, float y) {
		return (float) Math.sqrt(amount * amount - y * y);
	}

	public void pushComp(float x, float y) {
		cx += x;
		cy += y;
	}

	/**
	 * Should be called once each frame, after all {@link #push(float, float)}
	 * operations are called, to calculate and apply the pushes to {@link #x},
	 * {@link #y}, and {@link #direction}.
	 */
	public void calculate() {
		x.setValue(x.getValue() + cx * speed.getValue());
		y.setValue(y.getValue() + cy * speed.getValue());
	}

}
