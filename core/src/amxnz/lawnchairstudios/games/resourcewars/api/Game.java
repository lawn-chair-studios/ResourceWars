package amxnz.lawnchairstudios.games.resourcewars.api;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game {

	private final OrthographicCamera camera = new OrthographicCamera();
	private Viewport viewport;

	/**
	 * <p>
	 * Sets this {@link Game}'s {@link Game#viewport viewport} to be the specified
	 * viewport, and then applies the viewport.
	 * </p>
	 * <p>
	 * 
	 * <pre>
	 * <code>(this.viewport = newViewport).apply(true);</code>
	 * </pre>
	 * </p>
	 * 
	 * @param newViewport The new {@link Viewport}
	 */
	public void setViewport(Viewport newViewport) {
		(viewport = newViewport).apply(true);
	}

	/**
	 * <p>
	 * Sets this Game's Viewport's world size.
	 * </p>
	 * 
	 * @param width  The new viewport's world width (in game units).
	 * @param height The new viewport's world height (also in game units).
	 */
	public void setViewportWorldSize(float width, float height) {
		viewport.setWorldSize(width, height);
	}

	public Viewport getViewport() {
		return viewport;
	}

	private final OrthogonalTiledMapRenderer renderer;
	private final TiledMap map;

	{
		map = new TmxMapLoader().load("amxnz/lawnchairstudios/games/resourcewars/assets/levels/levels/Start/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);
	}

	public Game(boolean stretch) {
		viewport = stretch ? new FitViewport(20, 20, camera) : new ScreenViewport(camera);
		setViewportWorldSize(20, 20);
		viewport.apply(true);
	}

	public void render() {
		camera.update();
		renderer.setView(camera);
		renderer.render();
	}

	public void dispose() {
		renderer.dispose();
	}
}
