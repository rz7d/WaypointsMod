package pw.cinque.waypoints.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.render.EntityWaypoints;

public class WorldListener {

    private Minecraft mc = Minecraft.getMinecraft();

    private Entity entity;

    @SubscribeEvent
    public void onSwitchWorld(EntityJoinWorldEvent event) {
        final EntityPlayer player = mc.player;
        if (event.getEntity() == player) {
            EntityWaypoints entity = new EntityWaypoints(mc.world);
            entity.posX = player.posX;
            entity.posY = player.posY;
            entity.posZ = player.posZ;
            WaypointsMod.refreshWaypointsToRender();
            mc.world.spawnEntity(entity);
            this.entity = entity;
        }
    }

    @SubscribeEvent
    public void onPlayerMove(LivingEvent.LivingUpdateEvent event) {
        final Entity player = mc.player;
        if (player != null && event.getEntity() == player) {
            final Entity entity = this.entity;
            if (entity != null) {
                entity.posX = player.posX;
                entity.posY = player.posY;
                entity.posZ = player.posZ;
                entity.world.updateEntity(entity);
            }
        }
    }

}
