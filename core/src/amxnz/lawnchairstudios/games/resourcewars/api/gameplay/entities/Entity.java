package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue;
import amxnz.lawnchairstudios.games.resourcewars.api.observables.ObservableValue.Observer;

public abstract class Entity {
	private final ObservableValue<Float> x = new ObservableValue<Float>(0f), y = new ObservableValue<Float>(0f),
			direction = new ObservableValue<Float>(0f);

	protected class DirectionHandler<AGT> implements Comparable<DirectionHandler<?>> {
		private final float direction;
		private final Animation<AGT> animation;

		public void render(float direction) {
			// TODO Render - (direction can be ignored)
		}

		private Animation<AGT> getAnimation() {
			return animation;
		}

		public DirectionHandler(float direction, Animation<AGT> animation) {
			this.direction = direction;
			this.animation = animation;
		}

		public float getDirection() {
			return direction;
		}

		@Override
		public int compareTo(DirectionHandler<?> o) {
			return ((Float) direction).compareTo(o.direction);
		}
	}

	private final LinkedList<DirectionHandler<Sprite>> directionHandlers = new LinkedList<DirectionHandler<Sprite>>();
	private final ObservableValue<DirectionHandler<Sprite>> currentHandler = new ObservableValue<Entity.DirectionHandler<Sprite>>(
			null);
	private float elapsedAnimationTime;

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

					DirectionHandler<Sprite> midHandler = directionHandlers.get(mid);
					if (newValue < midHandler.getDirection())
						highBar = mid - 1;
					else if (newValue > midHandler.getDirection())
						lowBar = mid + 1;
					else {
						currentHandler.setValue(midHandler);
						return;
					}
				}
				DirectionHandler<Sprite> lowHandler = directionHandlers.get(lowBar),
						highHandler = directionHandlers.get(highBar);
				currentHandler.setValue(
						(lowHandler.getDirection() - newValue) < (newValue - highHandler.getDirection()) ? lowHandler
								: highHandler);
			}
		});

		currentHandler.addObserver(new Observer<DirectionHandler<?>>() {
			@Override
			public void espy(DirectionHandler<?> oldValue, DirectionHandler<?> newValue) {
				if (newValue == oldValue)
					return;
				elapsedAnimationTime = 0;
			}
		});
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

	public void render(Batch batch) {
		currentHandler.getValue().getAnimation().getKeyFrame(elapsedAnimationTime += Gdx.graphics.getDeltaTime(), true)
				.draw(batch);
	}
}