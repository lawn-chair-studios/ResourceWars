package amxnz.lawnchairstudios.games.resourcewars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import amxnz.lawnchairstudios.games.resourcewars.api.Game;

public class ResourceWars extends ApplicationAdapter {

	private Game game;

	@Override
	public void create() {
		game = new Game(false);
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
}
