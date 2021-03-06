package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.game;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities.AbstractEntity;
import amxnz.lawnchairstudios.games.resourcewars.api.gameplay.player.Player;

/**
 * The {@link Game} class is used to manage and present the game itself.
 *
 * @author Zeale
 *
 */
public class Game {

	private static final int DEFAULT_VIEWPORT_SIZE = 12;

	public class Level {

		private static final String DEFAULT_LEVEL_DIR = "amxnz/lawnchairstudios/games/resourcewars/assets/levels/levels/",
				DEFAULT_LEVELID_EXT = "/map.tmx";

		private final OrthogonalTiledMapRenderer renderer;
		private final TiledMap map;

		private final String id;

		public final int tileSize;

		protected Level(String id) {
			map = new TmxMapLoader().load(DEFAULT_LEVEL_DIR + (this.id = id) + DEFAULT_LEVELID_EXT);
			renderer = new OrthogonalTiledMapRenderer(map,
					1 / (float) (tileSize = map.getProperties().get("tilewidth", Integer.class)));
		}

		public List<MapObject> getObjects() {
			List<MapObject> objects = new LinkedList<>();
			for (MapLayer ml : map.getLayers())
				for (MapObject mo : ml.getObjects())
					objects.add(mo);
			return objects;
		}

		public void dispose() {
			renderer.dispose();
		}

		@Override
		protected void finalize() throws Throwable {
			dispose();
		}

		public String getId() {
			return id;
		}

		public void render() {
			renderer.setView(camera);
			renderer.render();
		};

		public void saveObjects() {
			// TODO Code
		}

	}

	public enum ViewportType {
		STRETCH, FIT, GROW;
	}

	private static boolean idsEqual(String id1, String id2) {
		return id1.equalsIgnoreCase(id2);
	}

	private final OrthographicCamera camera = new OrthographicCamera();

	float worldDimensions = 12;
	private Viewport viewport;

	// TODO Sort?
	private final List<WeakReference<Level>> levels = new ArrayList<>();

	private Batch renderBatch = new SpriteBatch(10);

	private final Level currentLevel = new Level("ConceptTest-1");// TODO Remove test code

	private Player player = new Player(new AbstractEntity(currentLevel) {
		{
			addOrientationHandler(new OrientationHandler(270) {

				private final Sprite texture = new Sprite(new Texture(
						"amxnz/lawnchairstudios/games/resourcewars/assets/characters/Stan/backwards/standing.png"));
				private final float width = texture.getWidth(), height = texture.getHeight();
//				{
//					// We want our sprite's biggest size (either length or width) to fit into
//					// exactly one tile. (Right now, without scaling, each pixel fits into a tile.)
//
//					texture.setScale(1 / (width < height ? height : width));
//
//				}

				{
					xCameraShift = width / height / 2;
					yCameraShift = .5f;
				}

				@Override
				public void render(Batch batch) {
					draw(texture, batch, width / height, 1);
				}
			});
		}
	});

	private GameInputProcessor gameInputProcessor = new GameInputProcessor(this);

	{
		Gdx.input.setInputProcessor(gameInputProcessor);
		player.bindToCamera(camera);
	}

	public Game(ViewportType stretch) {

		float worldDimensions = DEFAULT_VIEWPORT_SIZE;

		switch (stretch) {
		default:
		case FIT:
			viewport = new FitViewport(worldDimensions, worldDimensions, camera);
			break;
		case STRETCH:
			viewport = new StretchViewport(worldDimensions, worldDimensions, camera);
			break;
		case GROW:
			viewport = new ScreenViewport(camera);

			int width = Gdx.graphics.getWidth();
			int height = Gdx.graphics.getHeight();
			int min = width < height ? width : height;

			((ScreenViewport) viewport).setUnitsPerPixel(worldDimensions / min);
			setViewportWorldSize(worldDimensions, worldDimensions);
		}
		viewport.apply(true);
	}

	public void dispose() {
		currentLevel.dispose();
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
		levels.add(new WeakReference<>(level));
		return level;
	}

	public Player getPlayer() {
		return player;
	}

	public Batch getRenderBatch() {
		return renderBatch;
	}

	public Viewport getViewport() {
		return viewport;
	}

	protected Level loadLevel(String id) {
		return new Level(id);
	}

	public void render() {
		gameInputProcessor.render();

		currentLevel.render();

		getRenderBatch().setProjectionMatrix(camera.combined);

		getRenderBatch().begin();
		player.getInGameCharacter().render(getRenderBatch());
		getRenderBatch().end();

		camera.update();
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	public void setRenderBatch(Batch renderBatch) {
		this.renderBatch = renderBatch;
	}

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
}
