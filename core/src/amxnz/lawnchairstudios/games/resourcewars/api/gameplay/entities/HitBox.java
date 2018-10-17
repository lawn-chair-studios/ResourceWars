package amxnz.lawnchairstudios.games.resourcewars.api.gameplay.entities;

import java.util.LinkedList;
import java.util.List;

public class HitBox {

	private final List<Point> points = new LinkedList<>();

	public HitBox(Point... points) {
		for (Point p : points)
			this.points.add(p);
	}

	public final static class Point {
		public final float x, y;

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	public Point getPoint(int position) {
		return points.get(position);
	}

	public void addPoint(Point point) {
		points.add(point);
	}

	public void addPoint(float x, float y) {
		addPoint(new Point(x, y));
	}

	public void removePoint(int position) {
		points.remove(position);
	}

	public int size() {
		return points.size();
	}

	public void setPoint(int position, float px, float py) {
		points.set(position, new Point(px, py));
	}

}
