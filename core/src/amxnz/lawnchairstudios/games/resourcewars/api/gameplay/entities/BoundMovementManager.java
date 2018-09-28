package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Polyline;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.game.Game.Level;
import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;

public class BoundMovementManager extends MovementManager {
	private final ObservableValue<Level> level = new ObservableValue<>(null);

	private List<MapObject> collisionEnabledObjects;

	{
		level.addObserver((oldValue, newValue) -> {
			if (oldValue != newValue) {
				collisionEnabledObjects = new LinkedList<>();
				for (MapObject mo : newValue.getObjects()) {
					Object collisionProp = mo.getProperties().get("collision");
					if (collisionProp == null)
						continue;
					if (!(collisionProp instanceof Boolean))
						throw new RuntimeException(
								"Collision property on object in level is not a boolean. 'collision' should be either 'true' or 'false.'");
					if ((boolean) collisionProp)
						collisionEnabledObjects.add(mo);
				}
			}
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
		Level level = this.level.getValue();
		for (MapObject mo : collisionEnabledObjects)
			if (mo instanceof PolylineMapObject) {
				Polyline poly = ((PolylineMapObject) mo).getPolyline();
				float[] verts = poly.getVertices();
				for (int i = 0; i < verts.length - 2; i += 2) {
					float cx = (verts[i] + poly.getX()) / level.tileSize,
							cy = (verts[i + 1] + poly.getY()) / level.tileSize,
							dx = (verts[i + 2] + poly.getX()) / level.tileSize,
							dy = (verts[i + 3] + poly.getY()) / level.tileSize;
					// TODO Find out where they collide
					if ((fx < dx && cx < tx || fx > dx && cx > tx) && (fy < dy && cy < ty || fy > dy && cy > ty)) {
						System.out.println("Collision detected between: A(" + fx + "," + fy + ") -> B(" + tx + "," + ty
								+ ") and C(" + cx + "," + cy + ") -> D(" + dx + "," + dy + ")");
						return;
					}

				}
			}

		setX(tx);
		setY(ty);

	}

}
