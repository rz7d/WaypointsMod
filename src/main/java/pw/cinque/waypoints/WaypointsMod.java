package pw.cinque.waypoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import pw.cinque.waypoints.listener.KeybindListener;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(name = "Fyu's Waypoints", modid = "waypoints", version = "0.1")
public class WaypointsMod {

	private static final File WAYPOINTS_FILE;
	private static Minecraft mc = Minecraft.getMinecraft();

	public static KeyBinding bindWaypointCreate = new KeyBinding("Create Waypoint", Keyboard.KEY_SEMICOLON, "Fyu's Waypoints");
	public static KeyBinding bindWaypointMenu = new KeyBinding("Open Menu", Keyboard.KEY_GRAVE, "Fyu's Waypoints");

	private static Set<Waypoint> waypoints = new HashSet<Waypoint>();

	static {
		File root = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "Fyu's Waypoints");
		root.mkdir();

		WAYPOINTS_FILE = new File(root, "waypoints");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (WAYPOINTS_FILE.exists()) {
			try {
				Properties properties = new Properties();
				FileInputStream inputStream = new FileInputStream(WAYPOINTS_FILE);

				properties.load(inputStream);
				inputStream.close();

				for (Object name : properties.keySet()) {
					waypoints.add(Waypoint.fromString(properties.getProperty((String) name)));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ClientRegistry.registerKeyBinding(bindWaypointCreate);
		ClientRegistry.registerKeyBinding(bindWaypointMenu);

		FMLCommonHandler.instance().bus().register(new KeybindListener());
	}

	public static void addWaypoint(Waypoint waypoint) {
		waypoints.add(waypoint);
		Properties properties = new Properties();

		try {
			for (Waypoint w : waypoints) {
				properties.setProperty(w.getName(), waypoint.toString());
			}

			FileOutputStream output = new FileOutputStream(WAYPOINTS_FILE);
			properties.store(output, null);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeWaypoint(Waypoint waypoint) {
		waypoints.remove(waypoint);
		Properties properties = new Properties();

		try {
			for (Waypoint w : waypoints) {
				properties.setProperty(w.getName(), waypoint.toString());
			}

			FileOutputStream output = new FileOutputStream(WAYPOINTS_FILE);
			properties.store(output, null);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Set<Waypoint> getWaypoints() {
		return waypoints;
	}

}
