package pw.cinque.waypoints.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.entity.Location;
import pw.cinque.waypoints.entity.Waypoint;
import pw.cinque.waypoints.gui.screen.GuiScreenDeleteConfirm;
import pw.cinque.waypoints.gui.screen.GuiScreenWaypointsMenu;

public class GuiSlotWaypoints extends GuiSlot {

    private Minecraft mc = Minecraft.getMinecraft();
    private GuiScreenWaypointsMenu parent;
    private int selectedIndex = -1;

    public GuiSlotWaypoints(GuiScreenWaypointsMenu parent) {
        super(Minecraft.getMinecraft(), parent.width, parent.height, 48, parent.height - 48, 24);
        this.parent = parent;
    }

    protected int getSize() {
        return WaypointsMod.getWaypointsToRender().size();
    }

    protected boolean isSelected(int index) {
        return selectedIndex == index;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    protected int getContentHeight() {
        return this.getSize() * 24;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void elementClicked(int index, boolean doubleClicked, int p_148144_3_, int p_148144_4_) {
        this.selectedIndex = index;

        if (doubleClicked) {
            Waypoint waypoint = WaypointsMod.getWaypointsToRender().get(selectedIndex);
            mc.displayGuiScreen(new GuiScreenDeleteConfirm(parent, waypoint));
        }
    }

    @Override
    protected void drawSlot(int index, int x, int y, int p_148126_4_, Tessellator tessellator, int p_148126_6_, int p_148126_7_) {
        Waypoint waypoint = WaypointsMod.getWaypointsToRender().get(index);
        parent.drawString(parent.getFontRenderer(), waypoint.name(), x + 2, y, 0xFFFFFF);
        Location location = waypoint.location();
        parent.drawString(parent.getFontRenderer(), location.world() + " - " + location.x() + " / " + location.y() + " / " + location.z(), x + 2, y + 12, 0x777777);
    }

}
