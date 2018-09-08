package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;

public abstract class Entity {
	private final ObservableValue<Float> x = new ObservableValue<Float>(0f), y = new ObservableValue<Float>(0f),
			direction = new ObservableValue<Float>(0f);

	public float getX() {
		return x.getValue();
	}

	public float getY() {
		return y.getValue();
	}

	public float getDirection() {
		return direction.getValue();
	}

	public void setX(float value) {
		x.setValue(value);
	}

	public void setY(float value) {
		y.setValue(value);
	}

	public void setDirection(float value) {
		direction.setValue(value);
	}

	public void render() {

	}
}
