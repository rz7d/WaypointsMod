package pw.cinque.waypoints.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.entity.Waypoint;
import pw.cinque.waypoints.gui.screen.GuiScreenCreateWaypoint;
import pw.cinque.waypoints.gui.screen.GuiScreenWaypointsMenu;

public class KeybindListener {

    private Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (WaypointsMod.bindWaypointCreate.isPressed()) {
            if (mc.isSingleplayer()) {
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "You can only create waypoints in multiplayer!"));
            } else {
                mc.displayGuiScreen(new GuiScreenCreateWaypoint());
            }
        }
        if (WaypointsMod.bindWaypointMenu.isPressed()) {
            for (Waypoint waypoint : WaypointsMod.getWaypoints()) {
                if (waypoint.shouldRender()) {
                    mc.displayGuiScreen(new GuiScreenWaypointsMenu());
                    return;
                }
            }
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "No waypoints found for this server/world!"));
        }
    }

}
