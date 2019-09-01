package pw.cinque.waypoints.gui.screen;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.entity.Waypoint;
import pw.cinque.waypoints.gui.GuiSlotWaypoints;

public class GuiScreenWaypointsMenu extends GuiScreen {

    private static final int ID_DELETE = 0;
    private static final int ID_EDIT = 1;
    private static final int ID_CANCEL = -1;

    private GuiSlotWaypoints waypointsList;
    private GuiButton delete;
    private GuiButton cancel;
    private GuiButton edit;

    @Override
    public void initGui() {
        this.buttonList.add(delete = new GuiButton(ID_DELETE, this.width / 2 - 101, this.height - 24, 100, 20, "Delete"));
        this.buttonList.add(edit = new GuiButton(ID_EDIT, this.width / 2 - 203, this.height - 24, 100, 20, "Edit"));
        this.buttonList.add(cancel = new GuiButton(ID_CANCEL, this.width / 2 + 103, this.height - 24, 100, 20, "Cancel"));

        this.delete.enabled = false;
        this.edit.enabled = false;
        this.waypointsList = new GuiSlotWaypoints(this);
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        this.drawDefaultBackground();

        this.waypointsList.drawScreen(x, y, partialTicks);
        this.drawCenteredString(this.fontRendererObj, "Waypoints Menu", this.width / 2, 18, 0xFFFFFF);

        super.drawScreen(x, y, partialTicks);
    }

    @Override
    public void updateScreen() {
        delete.enabled = waypointsList.getSelectedIndex() != -1;
        edit.enabled = waypointsList.getSelectedIndex() != -1;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        Waypoint waypoint;
        switch (button.id) {
            case ID_DELETE:
                waypoint = WaypointsMod.getWaypointsToRender().get(waypointsList.getSelectedIndex());
                mc.displayGuiScreen(new GuiScreenDeleteConfirm(this, waypoint));
                break;

            case ID_EDIT:
                waypoint = WaypointsMod.getWaypointsToRender().get(waypointsList.getSelectedIndex());
                mc.displayGuiScreen(new GuiScreenComposeWaypoint(waypoint));
                break;

            case ID_CANCEL:
                mc.displayGuiScreen(null);
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

}
