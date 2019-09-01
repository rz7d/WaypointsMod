package pw.cinque.waypoints.gui.screen;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.entity.Location;
import pw.cinque.waypoints.entity.Waypoint;
import pw.cinque.waypoints.gui.GuiColorPicker;

import javax.annotation.Nullable;
import java.awt.*;

public class GuiScreenComposeWaypoint extends GuiScreen {

    private GuiTextField name;
    private GuiTextField coordsX;
    private GuiTextField coordsY;
    private GuiTextField coordsZ;
    private GuiColorPicker colorPicker;
    private GuiButton create;
    private GuiButton cancel;

    /**
     * only available in edit mode
     **/
    private final Waypoint target;

    public GuiScreenComposeWaypoint() {
        this(null);
    }

    public GuiScreenComposeWaypoint(@Nullable Waypoint target) {
        this.target = target;
    }

    @Override
    public void initGui() {
        this.name = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 48, 200, 20);
        this.name.setFocused(true);

        this.coordsX = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 10, 64, 20);
        this.coordsY = new GuiTextField(this.fontRendererObj, this.width / 2 - 32, this.height / 2 - 10, 63, 20);
        this.coordsZ = new GuiTextField(this.fontRendererObj, this.width / 2 + 35, this.height / 2 - 10, 64, 20);

        this.buttonList.add(colorPicker = new GuiColorPicker(0, this.width / 2 - 101, this.height / 2 + 25, 202, 20));
        this.buttonList.add(create = new GuiButton(1, this.width / 2 - 101, this.height / 2 + 50, 100, 20, "Create"));
        this.buttonList.add(cancel = new GuiButton(2, this.width / 2 + 1, this.height / 2 + 50, 100, 20, "Cancel"));

        if (target == null) {
            this.coordsX.setText(String.valueOf((int) mc.thePlayer.posX));
            this.coordsY.setText(String.valueOf((int) mc.thePlayer.posY));
            this.coordsZ.setText(String.valueOf((int) mc.thePlayer.posZ));
        } else {
            Location location = target.location();
            name.setText(target.name());
            coordsX.setText(String.valueOf(location.x()));
            coordsY.setText(String.valueOf(location.y()));
            coordsZ.setText(String.valueOf(location.z()));
            colorPicker.setColor(target.color().getRGB());
        }
        this.create.enabled = false;
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        this.drawDefaultBackground();

        this.drawCenteredString(this.fontRendererObj, target == null ? "Create Waypoint" : "Edit Waypoint", this.width / 2, 10, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "Name:", this.width / 2, this.height / 2 - 60, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "Coordinates (X/Y/Z):", this.width / 2, this.height / 2 - 22, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, "Color:", this.width / 2, this.height / 2 + 14, 0xFFFFFF);

        this.name.drawTextBox();
        this.coordsX.drawTextBox();
        this.coordsY.drawTextBox();
        this.coordsZ.drawTextBox();

        super.drawScreen(x, y, partialTicks);
    }

    @Override
    protected void keyTyped(char character, int index) {
        if (index == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        } else if (name.isFocused()) {
            this.name.textboxKeyTyped(character, index);
        } else if (coordsX.isFocused()) {
            this.coordsX.textboxKeyTyped(character, index);
        } else if (coordsY.isFocused()) {
            this.coordsY.textboxKeyTyped(character, index);
        } else if (coordsZ.isFocused()) {
            this.coordsZ.textboxKeyTyped(character, index);
        }

        for (Waypoint waypoint : WaypointsMod.getWaypoints()) {
            if (waypoint.name().equalsIgnoreCase(name.getText())
                && waypoint.address().equalsIgnoreCase(mc.getCurrentServerData().serverIP)) {
                this.create.enabled = false;
                return;
            }
        }

        testInputs();
    }

    private void testInputs() {
        this.create.enabled = name.getText().length() > 0
            && NumberUtils.isDigits(coordsX.getText().replace("-", ""))
            && NumberUtils.isDigits(coordsY.getText().replace("-", ""))
            && NumberUtils.isDigits(coordsZ.getText().replace("-", ""));
    }

    @Override
    public void updateScreen() {
        this.name.updateCursorCounter();
        this.coordsX.updateCursorCounter();
        this.coordsY.updateCursorCounter();
        this.coordsZ.updateCursorCounter();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                colorPicker.nextColor();
                testInputs();
                return;

            case 1:
                String name = this.name.getText();
                String world = target == null
                    ? mc.theWorld.provider.getDimensionName()
                    : target.location().world();
                String server = target == null
                    ? mc.getCurrentServerData().serverIP
                    : target.address();
                int x = Integer.parseInt(coordsX.getText());
                int y = Integer.parseInt(coordsY.getText());
                int z = Integer.parseInt(coordsZ.getText());
                int color = colorPicker.getColor();

                WaypointsMod.addWaypoint(Waypoint.of(Location.of(x, y, z, world), name, new Color(color), server));
                // replace waypoints
                if (target != null)
                    WaypointsMod.removeWaypoint(target);

                mc.displayGuiScreen(null);
                mc.thePlayer.addChatMessage(new ChatComponentText(
                    EnumChatFormatting.GREEN + "Waypoint '" + name + (target == null ? "' created!" : "' edited!")));
                return;

            case 2:
                mc.displayGuiScreen(null);
                return;
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int key) {
        super.mouseClicked(x, y, key);
        this.name.mouseClicked(x, y, key);
        this.coordsX.mouseClicked(x, y, key);
        this.coordsY.mouseClicked(x, y, key);
        this.coordsZ.mouseClicked(x, y, key);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
