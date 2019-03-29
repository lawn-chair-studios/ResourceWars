package amxnz.lawnchairstudios.games.resourcewars.api;

public final class Utilities {
	private Utilities() {
	}

	public final static float distance(float ax, float ay, float bx, float by) {
		float abx = ax + bx, aby = ay + by;
		return (float) Math.sqrt(abx * abx + aby * aby);
	}
}
