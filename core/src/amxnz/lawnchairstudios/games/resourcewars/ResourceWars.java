package amxnz.lawnchairstudios.games.resourcewars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ResourceWars extends ApplicationAdapter {
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;

	TiledMap map;

	@Override
	public void create() {

		camera = new OrthographicCamera();

		new FitViewport(20, 20, camera).apply(true);

		map = new TmxMapLoader().load("amxnz/lawnchairstudios/games/resourcewars/assets/levels/levels/Start/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
	}
}
