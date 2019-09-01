package pw.cinque.waypoints.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.entity.Location;
import pw.cinque.waypoints.entity.Waypoint;

public final class WaypointRenderer extends Render {

    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * Renders all waypoints, most of the code in this method is based on VoxelMap's renderer.
     */
    @Override
    public void doRender(Entity entity, double d1, double d2, double d3, float f1, float f2) {
        final RenderManager renderManager = this.renderManager;

        if (!WaypointsMod.enabled) {
            return;
        }

        for (Waypoint waypoint : WaypointsMod.getWaypointsToRender()) {
            Location location = waypoint.location();
            double x = location.x() - RenderManager.renderPosX;
            double y = location.y() - RenderManager.renderPosY;
            double z = location.z() - RenderManager.renderPosZ;

            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

            double distance = waypoint.distanceTo(renderManager.livingPlayer);
            String name = waypoint.name() + " [" + (int) distance + "m]";

            double maxDistance = mc.gameSettings.getOptionFloatValue(GameSettings.Options.RENDER_DISTANCE) * 12D;

            if (distance > maxDistance) {
                x = x / distance * maxDistance;
                y = y / distance * maxDistance;
                z = z / distance * maxDistance;
                distance = maxDistance;
            }

            FontRenderer fontRenderer = renderManager.getFontRenderer();
            float size = ((float) distance * 0.1F + 1.0F) * 0.0266F;
            int width = fontRenderer.getStringWidth(name) >> 1;

            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.3F, (float) z + 0.5F);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glRotated(180, 0, 0, 0);
            GL11.glScalef(size, size, size);

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDepthMask(false);
            GL11.glBlendFunc(770, 771);

            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);

            if (distance < maxDistance) {
                GL11.glDepthMask(true);
            }

            int color = waypoint.color().getRGB();
            int r = color >>> 16 & 255;
            int g = color >>> 8 & 255;
            int b = color & 255;

            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, 3.0F);

            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(r, g, b, 150);
            tessellator.addVertex(-width - 2, -2, 0.0D);
            tessellator.addVertex(-width - 2, 9, 0.0D);
            tessellator.addVertex(width + 2, 9, 0.0D);
            tessellator.addVertex(width + 2, -2, 0.0D);
            tessellator.draw();

            GL11.glPolygonOffset(1.0F, 1.0F);

            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.15F);
            tessellator.addVertex(-width - 1, -1, 0.0D);
            tessellator.addVertex(-width - 1, 8, 0.0D);
            tessellator.addVertex(width + 1, 8, 0.0D);
            tessellator.addVertex(width + 1, -1, 0.0D);
            tessellator.draw();

            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glPolygonOffset(1.0F, 7.0F);

            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(r, g, b, 40);
            tessellator.addVertex(-width - 2, -2, 0.0D);
            tessellator.addVertex(-width - 2, 9, 0.0D);
            tessellator.addVertex(width + 2, 9, 0.0D);
            tessellator.addVertex(width + 2, -2, 0.0D);
            tessellator.draw();

            GL11.glPolygonOffset(1.0F, 5.0F);

            tessellator.startDrawingQuads();
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.15F);
            tessellator.addVertex(-width - 1, -1, 0.0D);
            tessellator.addVertex(-width - 1, 8, 0.0D);
            tessellator.addVertex(width + 1, 8, 0.0D);
            tessellator.addVertex(width + 1, -1, 0.0D);
            tessellator.draw();

            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            fontRenderer.drawString(name, -width, 0, -3355444);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            fontRenderer.drawString(name, -width, 0, -1);

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_FOG);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

}
