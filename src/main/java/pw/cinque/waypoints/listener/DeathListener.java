package pw.cinque.waypoints.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import pw.cinque.waypoints.Waypoint;
import pw.cinque.waypoints.WaypointsMod;

public class DeathListener {

    private final Minecraft mc = Minecraft.getMinecraft();

    private volatile Vec3d prevPos;

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (mc.getCurrentServerData() == null)
            return;

        Gui screen = mc.currentScreen;
        if (screen instanceof GuiGameOver) {
            final EntityPlayer player = mc.player;
            if (prevPos == null ||
                (
                    Double.compare(prevPos.x, player.posX) != 0
                        && Double.compare(prevPos.y, player.posY) != 0
                        && Double.compare(prevPos.z, player.posZ) != 0
                )
            ) {
                WaypointsMod.addWaypoint(
                    new Waypoint(
                        "Death Point",
                        player.world.provider.getDimensionType().toString(),
                        mc.getCurrentServerData().serverIP,
                        (int) player.posX, (int) player.posY, (int) player.posZ,
                        -3211249
                    )
                );
                prevPos = new Vec3d(player.posX, player.posY, player.posZ);
            }
        }
    }

}
