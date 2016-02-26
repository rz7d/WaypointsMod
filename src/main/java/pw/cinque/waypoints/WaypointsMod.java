package pw.cinque.waypoints;

import java.util.HashSet;
import java.util.Set;

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

	public static KeyBinding bindWaypointCreate = new KeyBinding("Create Waypoint", Keyboard.KEY_SEMICOLON, "Fyu's Waypoints");
	public static KeyBinding bindWaypointMenu = new KeyBinding("Open Menu", Keyboard.KEY_GRAVE, "Fyu's Waypoints");
	private static Set<Waypoint> waypoints = new HashSet<Waypoint>();
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(bindWaypointCreate);
		ClientRegistry.registerKeyBinding(bindWaypointMenu);
		
		FMLCommonHandler.instance().bus().register(new KeybindListener());
	}
	
	public static Set<Waypoint> getWaypoints() {
		return waypoints;
	}
	
}
