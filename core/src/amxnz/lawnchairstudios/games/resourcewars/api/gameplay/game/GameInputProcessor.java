package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.game;

import com.badlogic.gdx.InputProcessor;

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

	public GameInputProcessor(Game game) {
		this.game = game;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
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

}
