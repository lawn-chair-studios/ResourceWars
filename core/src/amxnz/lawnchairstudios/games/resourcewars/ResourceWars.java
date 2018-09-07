package amxnz.lawnchairstudios.games.resourcewars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ResourceWars extends ApplicationAdapter {
	SpriteBatch batch;
	TiledMap map;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;

	@Override
	public void create() {
		batch = new SpriteBatch();
		map = new TmxMapLoader().load("/amxnz/lawnchairstudios/games/resourcewars/assets/levels/levels/Start/map.tmx");
		camera = new OrthographicCamera();
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
