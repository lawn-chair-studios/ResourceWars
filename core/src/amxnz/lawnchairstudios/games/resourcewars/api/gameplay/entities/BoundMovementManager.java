package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import java.util.List;

import com.badlogic.gdx.maps.MapObject;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.game.Game.Level;
import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;

public class BoundMovementManager extends MovementManager {
	private final ObservableValue<Level> level = new ObservableValue<>(null);

	private List<MapObject> collisionEnabledObjects;

	{
		level.addObserver((oldValue, newValue) -> {
			if (oldValue != newValue)
				collisionEnabledObjects = newValue.getObjects();
		});
	}

	public void setLevel(Level level) {
		if (level == null)
			throw null;
		this.level.setValue(level);
	}

	public BoundMovementManager(ObservableValue<Float> x, ObservableValue<Float> y, ObservableValue<Float> orientation,
			ObservableValue<Float> speed, Level level) {
		super(x, y, orientation, speed);
		setLevel(level);
	}

	@Override
	public void calculate() {
		super.calculate();
	}

	@Override
	protected void move(float fx, float fy, float tx, float ty) {

	}

}
