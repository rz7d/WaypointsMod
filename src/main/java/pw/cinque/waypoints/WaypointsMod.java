package pw.cinque.waypoints;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;


import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.lwjgl.input.Keyboard;

import pw.cinque.waypoints.listener.DeathListener;
import pw.cinque.waypoints.listener.KeybindListener;
import pw.cinque.waypoints.listener.WorldListener;
import pw.cinque.waypoints.render.EntityWaypoints;
import pw.cinque.waypoints.render.WaypointRenderer;

@Mod(name = WaypointsMod.NAME, modid = WaypointsMod.MOD_ID, version = WaypointsMod.VERSION)
public class WaypointsMod {

	public static final String NAME = "Fyu's Waypoints";
	public static final String MOD_ID = "waypoints";
	public static final String VERSION = "1.0-Beta";

	private static final File WAYPOINTS_FILE;
	private static Minecraft mc = Minecraft.getMinecraft();

	public static KeyBinding bindWaypointCreate = new KeyBinding("Create Waypoint", Keyboard.KEY_SEMICOLON, "Fyu's Waypoints");
	public static KeyBinding bindWaypointMenu = new KeyBinding("Open Menu", Keyboard.KEY_GRAVE, "Fyu's Waypoints");

	private static Set<Waypoint> waypoints = new HashSet<Waypoint>();
	private static ArrayList<Waypoint> waypointsToRender = new ArrayList<Waypoint>();

	static {
		File root = new File(Minecraft.getMinecraft().gameDir + File.separator + "Fyu's Waypoints");
		root.mkdir();

		WAYPOINTS_FILE = new File(root, "waypoints");
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModMetadata metadata = event.getModMetadata();
		metadata.version = VERSION;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		if (WAYPOINTS_FILE.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(WAYPOINTS_FILE));
				String readLine;

				while ((readLine = reader.readLine()) != null) {
					waypoints.add(Waypoint.fromString(readLine));
				}

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ClientRegistry.registerKeyBinding(bindWaypointCreate);
		ClientRegistry.registerKeyBinding(bindWaypointMenu);

		MinecraftForge.EVENT_BUS.register(new KeybindListener());
		MinecraftForge.EVENT_BUS.register(new WorldListener());
		MinecraftForge.EVENT_BUS.register(new DeathListener());
	}

	@SubscribeEvent
	public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		event.getRegistry().register(EntityEntryBuilder.create()
			.name("waypoint")
			.id(new ResourceLocation(MOD_ID, "waypoint"), 999)
			.entity(EntityWaypoints.class)
			.tracker(1, 1, false)
			.build()
		);
        RenderingRegistry.registerEntityRenderingHandler(EntityWaypoints.class, WaypointRenderer::new);
    }

	public static void addWaypoint(Waypoint waypoint) {
		waypoints.add(waypoint);
		refreshWaypointsToRender();
		writeWaypointsToDisk();
	}

	public static void removeWaypoint(Waypoint waypoint) {
		waypoints.remove(waypoint);
		refreshWaypointsToRender();
		writeWaypointsToDisk();
	}

	private static void writeWaypointsToDisk() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(WAYPOINTS_FILE));

			for (Waypoint w : waypoints) {
				writer.write(w.toString());
				writer.newLine();
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Set<Waypoint> getWaypoints() {
		return waypoints;
	}

	public static void refreshWaypointsToRender() {
		waypointsToRender.clear();

		for (Waypoint waypoint : WaypointsMod.getWaypoints()) {
			if (waypoint.shouldRender()) {
				waypointsToRender.add(waypoint);
			}
		}
	}

	public static ArrayList<Waypoint> getWaypointsToRender() {
		return waypointsToRender;
	}

}
