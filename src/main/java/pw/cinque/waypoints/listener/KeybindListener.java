package pw.cinque.waypoints.listener;

import net.minecraft.client.Minecraft;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.gui.screen.GuiCreateWaypoint;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeybindListener {

	private Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (WaypointsMod.bindWaypointCreate.isPressed()) {
			mc.displayGuiScreen(new GuiCreateWaypoint());
		} else if (WaypointsMod.bindWaypointMenu.isPressed()) {
			// TODO: Finish the menu screen
		}
	}
	
}
