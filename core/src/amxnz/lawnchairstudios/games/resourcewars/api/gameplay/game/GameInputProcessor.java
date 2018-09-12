package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.game;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.W;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities.MovementManager;

/**
 * A helper class for {@link Game}, this class handles and processes input,
 * using its reference to {@link Game} as needed. This class may be moved into
 * {@link Game} later on.
 * 
 * @author Zeale
 *
 */
class GameInputProcessor implements InputProcessor {

	// This class processes input and handles it using the Game object as needed.

	private final Game game;

	private final List<Integer> keys = new ArrayList<Integer>(15);

	public GameInputProcessor(Game game) {
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		keys.add(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		for (Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();)
			if (iterator.next() == keycode)
				iterator.remove();
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT)
			System.out.println("x=" + screenX + " y=" + screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void movePlayer(float amount, float direction) {
		game.getPlayer().getInGameCharacter().getMover().pushVec(amount, direction);
	}

	private void movePlayer(float direction) {
		movePlayer(1 * Gdx.graphics.getDeltaTime(), direction);
	}

	public void render() {
		if (keys.contains(A))
			movePlayer(MovementManager.WEST);
		if (keys.contains(S))
			movePlayer(MovementManager.SOUTH);
		if (keys.contains(D))
			movePlayer(MovementManager.EAST);
		if (keys.contains(W))
			movePlayer(MovementManager.NORTH);
		game.getPlayer().getInGameCharacter().getMover().calculate();
	}

}
