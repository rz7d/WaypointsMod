package pw.cinque.waypoints;

import net.minecraft.client.Minecraft;

public class Waypoint {

	private static Minecraft mc = Minecraft.getMinecraft();
	
	private final String name;
	private final String world;
	private final String server;
	private final int x, y, z;
	private final int color;

	public Waypoint(String name, String world, String server, int x, int y, int z, int color) {
		this.name = name;
		this.world = world;
		this.server = server;
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
	
	public String getServer() {
		return server;
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
	
	public boolean shouldRender() {
		return !mc.isSingleplayer() && mc.theWorld.provider.getDimensionName().equals(world) && mc.func_147104_D().serverIP.equals(server);
	}
	
	@Override
	public String toString() {
		return name + ";" + world + ";" + server.replace(":", "-") + ";" + x + ";" + y + ";" + z + ";" + color;
	}
	
	public static Waypoint fromString(String string) {
		String[] parts = string.split(";");
		return new Waypoint(parts[0], parts[1], parts[2].replace("-", ":"), Integer.valueOf(parts[3]), Integer.valueOf(parts[4]), Integer.valueOf(parts[5]), Integer.valueOf(parts[6]));
	}

}
