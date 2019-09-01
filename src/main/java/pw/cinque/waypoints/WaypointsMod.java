package pw.cinque.waypoints;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import pw.cinque.waypoints.command.HereCommand;
import pw.cinque.waypoints.command.ImAtCommand;
import pw.cinque.waypoints.command.WaypointCommand;
import pw.cinque.waypoints.entity.Waypoint;
import pw.cinque.waypoints.listener.KeybindListener;
import pw.cinque.waypoints.listener.WorldListener;
import pw.cinque.waypoints.render.EntityWaypoints;
import pw.cinque.waypoints.render.WaypointRenderer;
import pw.cinque.waypoints.storage.WaypointsStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;

@Mod(name = WaypointsMod.NAME, modid = WaypointsMod.MOD_ID, version = WaypointsMod.VERSION)
public class WaypointsMod {

    public static final String NAME = "Fyu's Waypoints";
    public static final String MOD_ID = "waypoints";
    public static final String VERSION = "1.0-Beta";

    private static final Path WAYPOINTS_FILE;
    public static boolean enabled = true;
    private static Minecraft mc = Minecraft.getMinecraft();

    public static KeyBinding bindWaypointCreate = new KeyBinding("Create Waypoint", Keyboard.KEY_SEMICOLON, "Fyu's Waypoints");
    public static KeyBinding bindWaypointMenu = new KeyBinding("Open Menu", Keyboard.KEY_GRAVE, "Fyu's Waypoints");

    private static ArrayList<Waypoint> waypointsToRender = new ArrayList<>();

    static {
        final Path dir = mc.mcDataDir.toPath().resolve("waypoints");
        try {
            Files.createDirectories(dir);
        } catch (IOException ignored) {
        }

        WAYPOINTS_FILE = dir.resolve("waypoints.json");
    }

    private static final WaypointsStorage storage = new WaypointsStorage(WAYPOINTS_FILE);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModMetadata metadata = event.getModMetadata();
        metadata.version = VERSION;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            storage.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        ClientRegistry.registerKeyBinding(bindWaypointCreate);
        ClientRegistry.registerKeyBinding(bindWaypointMenu);

        EntityRegistry.registerModEntity(EntityWaypoints.class, "Waypoint", 999, this, 1, 1, false);
        RenderingRegistry.registerEntityRenderingHandler(EntityWaypoints.class, new WaypointRenderer());

        FMLCommonHandler.instance().bus().register(new KeybindListener());
        MinecraftForge.EVENT_BUS.register(new WorldListener());
        ClientCommandHandler.instance.registerCommand(new HereCommand());
        ClientCommandHandler.instance.registerCommand(new ImAtCommand());
        ClientCommandHandler.instance.registerCommand(new WaypointCommand());
    }

    public static void addWaypoint(Waypoint waypoint) {
        storage.waypoints().add(waypoint);
        refreshWaypointsToRender();
        try {
            storage.save();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void removeWaypoint(Waypoint waypoint) {
        storage.waypoints().remove(waypoint);
        refreshWaypointsToRender();
        try {
            storage.save();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Set<Waypoint> getWaypoints() {
        return storage.waypoints();
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
