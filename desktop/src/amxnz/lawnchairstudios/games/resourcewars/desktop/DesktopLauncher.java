package amxnz.lawnchairstudios.games.resourcewars.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import amxnz.lawnchairstudios.games.resourcewars.ResourceWars;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 600;
		new LwjglApplication(new ResourceWars(), config);
	}
}
