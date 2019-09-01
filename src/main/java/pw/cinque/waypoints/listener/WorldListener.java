package pw.cinque.waypoints.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.render.EntityWaypoints;

public class WorldListener {

    private Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onSwitchWorld(EntityJoinWorldEvent event) {
        if (event.entity == mc.thePlayer) {
            EntityWaypoints entity = new EntityWaypoints(mc.theWorld);
            WaypointsMod.refreshWaypointsToRender();
            mc.theWorld.spawnEntityInWorld(entity);
        }
    }

}
