package pw.cinque.waypoints.listener;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.render.EntityWaypoints;

public class WorldListener {

	private Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onSwitchWorld(EntityJoinWorldEvent event) {
		if (event.getEntity() == mc.player) {
			EntityWaypoints entity = new EntityWaypoints(mc.world);
			WaypointsMod.refreshWaypointsToRender();
			mc.world.spawnEntity(entity);
		}
	}

}
