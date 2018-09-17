package amxnz.lawnchairstudios.games.resourcewars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.game.Game;

/**
 * The main class for {@code ResourceWars}. This class extends
 * {@link ApplicationAdapter} and owns a {@link Game} instance which is used to
 * handle the entire game.
 * 
 * @author Zeale
 *
 */
public class ResourceWars extends ApplicationAdapter {

	private Game game;

	@Override
	public void create() {
		game = new Game(Game.ViewportType.FIT);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.render();
	}

	@Override
	public void dispose() {
		game.dispose();
	}

	@Override
	public void resize(int width, int height) {
		game.resize(width, height);
	}

}
