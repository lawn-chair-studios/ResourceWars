package amxnz.lawnchairstudios.games.resourcewars.api.gameplay;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.player.Player;

public class Game {

	public void resize(int width, int height) {
		viewport.update(width, height);
	}

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

	// TODO Sort?
	private final List<WeakReference<Level>> levels = new ArrayList<WeakReference<Level>>();

	protected Level loadLevel(String id) {
		return new Level(id);
	}

	protected Level getLevel(String id) {
		for (Iterator<WeakReference<Level>> iterator = levels.iterator(); iterator.hasNext();) {
			WeakReference<Level> wr = iterator.next();
			if (wr.get() == null)
				iterator.remove();
			else if (idsEqual(wr.get().getId(), id))
				return wr.get();
		}
		Level level = loadLevel(id);
		levels.add(new WeakReference<Game.Level>(level));
		return level;
	}

	private static boolean idsEqual(String id1, String id2) {
		return id1.equalsIgnoreCase(id2);
	}

	public class Level {

		private static final String DEFAULT_LEVEL_DIR = "amxnz/lawnchairstudios/games/resourcewars/assets/levels/levels/",
				DEFAULT_LEVELID_EXT = "/map.tmx";

		private final OrthogonalTiledMapRenderer renderer;
		private final TiledMap map;

		private final String id;

		protected Level(String id) {
			map = new TmxMapLoader().load(DEFAULT_LEVEL_DIR + (this.id = id) + DEFAULT_LEVELID_EXT);
			renderer = new OrthogonalTiledMapRenderer(map,
					1 / (float) map.getProperties().get("tilewidth", Integer.class));
		}

		public String getId() {
			return id;
		}

		public void render() {
			renderer.setView(camera);
			renderer.render();
		}

		public void saveObjects() {
			// TODO Code
		}

		protected void finalize() throws Throwable {
			dispose();
		};

		public void dispose() {
			renderer.dispose();
		}

	}

	public enum ViewportType {
		STRETCH, FIT, GROW;
	}

	private final Level currentLevel = new Level("Start");// TODO Remove test code

	public Game(ViewportType stretch) {
		switch (stretch) {
		default:
		case FIT:
			viewport = new FitViewport(5, 5, camera);
			break;
		case STRETCH:
			viewport = new StretchViewport(5, 5, camera);
			break;
		case GROW:
			viewport = new ScreenViewport(camera);

			// 1/32 = 1 unit every 32 pixels.
			// We want five units every (minimum screen size).

			int width = Gdx.graphics.getWidth();
			int height = Gdx.graphics.getHeight();
			int min = width < height ? width : height;

			((ScreenViewport) viewport).setUnitsPerPixel(5f / min);
		}
		setViewportWorldSize(5, 5);
		viewport.apply(true);
	}

	private Player mainCharacter;

	public void render() {
		camera.update();
		currentLevel.render();
	}

	public void dispose() {
		currentLevel.dispose();
	}
}
