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

/**
 * Represents an entity in game.
 * 
 * @author Zeale
 *
 */
public abstract class AbstractEntity {

	private final ObservableValue<Float> x = new ObservableValue<Float>(0f), y = new ObservableValue<Float>(0f),
			orientation = new ObservableValue<Float>(0f) {
				@Override
				public void setValue(Float value) {
					if ((value = value % 360) < 0)
						value += 360;
					super.setValue(value);
				}
			}, speed = new ObservableValue<Float>(1f);

	// direction is in degrees

	private final MovementManager mover = new MovementManager(x, y, orientation, speed);

	private final LinkedList<OrientationHandler> orientationHandlers = new LinkedList<OrientationHandler>();
	private final ObservableValue<OrientationHandler> currentHandler = new ObservableValue<OrientationHandler>(null);

	public void addOrientationHandler(OrientationHandler handler) {
		int pos = Collections.binarySearch(orientationHandlers, handler);
		if (!(pos < 0))
			throw new RuntimeException("OrientationHandler with same degree already exists.");
		else
			orientationHandlers.add(-(pos + 1), handler);
	}

	{
		orientation.addObserver(new Observer<Float>() {

			@Override
			public void espy(Float oldValue, Float newValue) {
				// TODO Binary search for value, then find the closest match and use it. The
				// picked handler will be stuffed into #currentHandler and will control the the
				// direction the player faces when he/she moves.
				if (orientationHandlers.size() < 1)
					return;
				else if (orientationHandlers.size() == 1 || newValue < orientationHandlers.getFirst().getDirection()) {
					currentHandler.setValue(orientationHandlers.getFirst());
					return;
				} else if (newValue > orientationHandlers.getLast().getDirection()) {
					currentHandler.setValue(orientationHandlers.getLast());
					return;
				}

				int lowBar = 0, highBar = orientationHandlers.size() - 1;

				while (lowBar <= highBar) {
					int mid = (highBar + lowBar) / 2;

					OrientationHandler midHandler = orientationHandlers.get(mid);
					if (newValue < midHandler.getDirection())
						highBar = mid - 1;
					else if (newValue > midHandler.getDirection())
						lowBar = mid + 1;
					else {
						currentHandler.setValue(midHandler);
						return;
					}
				}
				OrientationHandler lowHandler = orientationHandlers.get(lowBar),
						highHandler = orientationHandlers.get(highBar);
				currentHandler.setValue(
						(lowHandler.getDirection() - newValue) < (newValue - highHandler.getDirection()) ? lowHandler
								: highHandler);
			}
		});

		currentHandler.addObserver(new Observer<OrientationHandler>() {
			@Override
			public void espy(OrientationHandler oldValue, OrientationHandler newValue) {
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
		} else if (!cameraBindingMap.containsKey(camera)) {
			new Binding(camera);
			camera.update();
		}

	}

	public float getX() {
		return x.getValue();
	}

	public float getY() {
		return y.getValue();
	}

	public float getOrientation() {
		return orientation.getValue();
	}

	public void setX(float value) {
		x.setValue(value);
	}

	public void setY(float value) {
		y.setValue(value);
	}

	public void setOrientation(float value) {
		orientation.setValue(value);
	}

	public float getXCameraShift() {
		return currentHandler.getValue().xCameraShift;
	}

	public float getYCameraShift() {
		return currentHandler.getValue().yCameraShift;
	}

	public MovementManager getMover() {
		return mover;
	}

	private float elapsedAnimationTime;

	/**
	 * A class that handles the displaying of an {@link AbstractEntity Entity} at a
	 * certain orientation.
	 * 
	 * @author Zeale
	 *
	 */
	public abstract class OrientationHandler implements Comparable<OrientationHandler> {
		private final float targetOrientation;
		protected float xCameraShift, yCameraShift;

		public OrientationHandler(float targetOrientation) {
			this.targetOrientation = targetOrientation;
		}

		public float getDirection() {
			return targetOrientation;
		}

		protected final void draw(Sprite sprite, Batch batch) {
//			sprite.setX(getX());
//			sprite.setY(getY());
//			sprite.draw(batch);
			// The above code draws the sprite in some weird position.
			draw(sprite, batch, 1, 1);
		}

		protected final void draw(Sprite sprite, Batch batch, float widthInPixels, float heightInPixels) {
			batch.draw(sprite, getX(), getY(), widthInPixels, heightInPixels);
		}

		protected final float getX() {
			return AbstractEntity.this.getX();
		}

		protected final float getY() {
			return AbstractEntity.this.getY();
		}

		protected final float getSpeed() {
			return speed.getValue();
		}

		protected final float getOrientation() {
			return orientation.getValue();
		}

		@Override
		public int compareTo(OrientationHandler o) {
			return ((Float) targetOrientation).compareTo(o.targetOrientation);
		}

		public abstract void render(Batch batch);

	}

	private class EntityOrientationHandler extends OrientationHandler {

		private final Animation<Sprite> animation;

		public EntityOrientationHandler(float targetOrientation, Animation<Sprite> animation) {
			super(targetOrientation);
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
