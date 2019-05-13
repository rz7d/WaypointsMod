package pw.cinque.waypoints.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import pw.cinque.waypoints.Waypoint;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.gui.screen.GuiScreenCreateWaypoint;
import pw.cinque.waypoints.gui.screen.GuiScreenWaypointsMenu;

public class KeybindListener {

	private Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (WaypointsMod.bindWaypointCreate.isPressed()) {
			if (mc.isSingleplayer()) {
				mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "You can only create waypoints in multiplayer!"));
			} else {
				mc.displayGuiScreen(new GuiScreenCreateWaypoint());
			}
		} else if (WaypointsMod.bindWaypointMenu.isPressed()) {
			for (Waypoint waypoint : WaypointsMod.getWaypoints()) {
				if (waypoint.shouldRender()) {
					mc.displayGuiScreen(new GuiScreenWaypointsMenu());
					return;
				}
			}
			
			mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "No waypoints found for this server/world!"));
		}
	}

}
