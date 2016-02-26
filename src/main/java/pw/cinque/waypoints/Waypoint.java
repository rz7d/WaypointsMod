package pw.cinque.waypoints;

public class Waypoint {

	private final String name;
	private final String world;
	private final int x, y, z;
	private final int color;

	public Waypoint(String name, String world, int x, int y, int z, int color) {
		this.name = name;
		this.world = world;
		this.x = x;
		this.z = z;
		this.y = y;
		this.color = color;
	}

	public String getName() {
		return name;
	}
	
	public String getWorld() {
		return world;
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
	
	@Override
	public String toString() {
		return name + ";" + world + ";" + x + ";" + y + ";" + z + ";" + color;
	}
	
	public static Waypoint fromString(String string) {
		String[] parts = string.split(";");
		return new Waypoint(parts[0], parts[1], Integer.valueOf(parts[2]), Integer.valueOf(parts[3]), Integer.valueOf(parts[4]), Integer.valueOf(parts[5]));
	}

}
