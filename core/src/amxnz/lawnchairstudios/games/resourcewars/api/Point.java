package amxnz.lawnchairstudios.games.resourcewars.api;

public class Point {
	public final float x, y;

	public static final Point ORIGIN = new Point(0, 0);

	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
