package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import com.badlogic.gdx.graphics.g2d.Animation;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities.Entity.DirectionHandler;

public class DirectionHandler<AGT> implements Comparable<DirectionHandler<?>> {
	private final float direction;
	private final Animation<AGT> animation;

	public void render(float direction) {
		// TODO Render - (direction can be ignored)
	}

	public Animation<AGT> getAnimation() {
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