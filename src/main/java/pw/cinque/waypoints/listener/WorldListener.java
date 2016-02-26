package pw.cinque.waypoints.listener;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import pw.cinque.waypoints.render.EntityWaypoint;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldListener {

	private Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onSwitchWorld(EntityJoinWorldEvent event) {
		if (event.entity == mc.thePlayer) {
			EntityWaypoint entity = new EntityWaypoint(mc.theWorld);
			mc.theWorld.spawnEntityInWorld(entity);
		}
	}

}
