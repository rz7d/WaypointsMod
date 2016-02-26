package pw.cinque.waypoints;

public class Waypoint {

	private final String name;
	private final int x, y, z;
	private final int color;

	public Waypoint(String name, int x, int y, int z, int color) {
		this.name = name;
		this.x = x;
		this.z = z;
		this.y = y;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getColor() {
		return color;
	}

}
