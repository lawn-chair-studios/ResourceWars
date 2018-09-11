package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;
import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue.Observer;

public abstract class AbstractEntity {

	private final ObservableValue<Float> x = new ObservableValue<Float>(0f), y = new ObservableValue<Float>(0f),
			direction = new ObservableValue<Float>(0f) {
				@Override
				public void setValue(Float value) {
					if ((value = value % 360) < 0)
						value += 360;
					super.setValue(value);
				}
			}, speed = new ObservableValue<Float>(1f);
	// direction is in degrees

	private final MovementManager mover = new MovementManager(x, y, direction, speed);

	private final LinkedList<DirectionHandler> directionHandlers = new LinkedList<DirectionHandler>();
	private final ObservableValue<DirectionHandler> currentHandler = new ObservableValue<DirectionHandler>(null);

	public void addDirectionHandler(DirectionHandler handler) {
		int pos = Collections.binarySearch(directionHandlers, handler);
		if (!(pos < 0))
			throw new RuntimeException("DirectionHandler with same degree already exists.");
		else
			directionHandlers.add(-(pos + 1), handler);
	}

	{
		direction.addObserver(new Observer<Float>() {

			@Override
			public void espy(Float oldValue, Float newValue) {
				// TODO Binary search for value, then find the closest match and use it. The
				// picked handler will be stuffed into #currentHandler and will control the the
				// direction the player faces when he/she moves.
				if (newValue < directionHandlers.getFirst().getDirection())
					currentHandler.setValue(directionHandlers.getFirst());
				else if (newValue > directionHandlers.getLast().getDirection())
					currentHandler.setValue(directionHandlers.getLast());

				int lowBar = 0, highBar = directionHandlers.size() - 1;

				while (lowBar <= highBar) {
					int mid = (highBar + lowBar) / 2;

					DirectionHandler midHandler = directionHandlers.get(mid);
					if (newValue < midHandler.getDirection())
						highBar = mid - 1;
					else if (newValue > midHandler.getDirection())
						lowBar = mid + 1;
					else {
						currentHandler.setValue(midHandler);
						return;
					}
				}
				DirectionHandler lowHandler = directionHandlers.get(lowBar),
						highHandler = directionHandlers.get(highBar);
				currentHandler.setValue(
						(lowHandler.getDirection() - newValue) < (newValue - highHandler.getDirection()) ? lowHandler
								: highHandler);
			}
		});

		currentHandler.addObserver(new Observer<DirectionHandler>() {
			@Override
			public void espy(DirectionHandler oldValue, DirectionHandler newValue) {
				if (newValue == oldValue)
					return;
			}
		});
	}

	private final Map<Camera, Object> cameraBindingMap = new HashMap<Camera, Object>();

	public void cameraBind(final Camera camera, boolean bound) {

		class Binding {

			private final Observer<Float> x, y;

			private Binding(final Camera camera) {
				x = new Observer<Float>() {

					@Override
					public void espy(Float oldValue, Float newValue) {
						if ((float) oldValue != newValue) {
							camera.position.x = newValue;
							camera.update();
						}
					}
				};
				y = new Observer<Float>() {

					@Override
					public void espy(Float oldValue, Float newValue) {
						if ((float) oldValue != newValue) {
							camera.position.y = newValue;
							camera.update();
						}
					}
				};
				AbstractEntity.this.x.addObserver(x);
				AbstractEntity.this.y.addObserver(y);
				cameraBindingMap.put(camera, this);
			}

			public void unbind() {
				AbstractEntity.this.x.removeObserver(x);
				AbstractEntity.this.y.removeObserver(y);
				cameraBindingMap.remove(camera);
			}

		}

		if (!bound) {
			if (cameraBindingMap.containsKey(camera))
				((Binding) cameraBindingMap.get(camera)).unbind();
		} else if (!cameraBindingMap.containsKey(camera))
			new Binding(camera);

	}

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

	public MovementManager getMover() {
		return mover;
	}

	private float elapsedAnimationTime;

	private class EntityOrientationHandler extends DirectionHandler {

		private final Animation<Sprite> animation;

		public EntityOrientationHandler(float direction, Animation<Sprite> animation) {
			super(direction);
			this.animation = animation;
		}

		public Animation<Sprite> getAnimation() {
			return animation;
		}

		@Override
		public void render(Batch batch) {
			Sprite sprite = getAnimation().getKeyFrame(elapsedAnimationTime += Gdx.graphics.getDeltaTime(), true);
			sprite.setPosition(getX(), getY());
			sprite.draw(batch);
		}

	}

	public void render(Batch batch) {
		currentHandler.getValue().render(batch);
	}
}
