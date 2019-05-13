package pw.cinque.waypoints.gui.screen;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;

import pw.cinque.waypoints.Waypoint;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.gui.GuiColorPicker;

import java.io.IOException;

public class GuiScreenCreateWaypoint extends GuiScreen {

	private GuiTextField name;
	private GuiTextField coordsX;
	private GuiTextField coordsY;
	private GuiTextField coordsZ;
	private GuiColorPicker colorPicker;
	private GuiButton create;
	private GuiButton cancel;

	@Override
	public void initGui() {
		this.name = new GuiTextField(3, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 48, 200, 20);
		this.name.setFocused(true);

		this.coordsX = new GuiTextField(4, this.fontRenderer, this.width / 2 - 100, this.height / 2 - 10, 64, 20);
		this.coordsY = new GuiTextField(5, this.fontRenderer, this.width / 2 - 32, this.height / 2 - 10, 63, 20);
		this.coordsZ = new GuiTextField(6, this.fontRenderer, this.width / 2 + 35, this.height / 2 - 10, 64, 20);

		this.buttonList.add(colorPicker = new GuiColorPicker(0, this.width / 2 - 101, this.height / 2 + 25, 202, 20));
		this.buttonList.add(create = new GuiButton(1, this.width / 2 - 101, this.height / 2 + 50, 100, 20, "Create"));
		this.buttonList.add(cancel = new GuiButton(2, this.width / 2 + 1, this.height / 2 + 50, 100, 20, "Cancel"));
		
		this.coordsX.setText(String.valueOf((int) mc.player.posX));
		this.coordsY.setText(String.valueOf((int) mc.player.posY));
		this.coordsZ.setText(String.valueOf((int) mc.player.posZ));
		this.create.enabled = false;
	}

	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		this.drawDefaultBackground();

		this.drawCenteredString(this.fontRenderer, "Create Waypoint", this.width / 2, 10, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, "Name:", this.width / 2, this.height / 2 - 60, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, "Coordinates (X/Y/Z):", this.width / 2, this.height / 2 - 22, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, "Color:", this.width / 2, this.height / 2 + 14, 0xFFFFFF);

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
			if (waypoint.getName().equalsIgnoreCase(name.getText()) && waypoint.getServer().equalsIgnoreCase(mc.getCurrentServerData().serverIP)) {
				this.create.enabled = false;
				return;
			}
		}
		
		this.create.enabled = name.getText().length() > 0 && NumberUtils.isDigits(coordsX.getText().replace("-", "")) && NumberUtils.isDigits(coordsY.getText().replace("-", "")) && NumberUtils.isDigits(coordsZ.getText().replace("-", ""));
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
			return;
			
		case 1:
			String name = this.name.getText();
			String world = mc.world.provider.getDimensionType().toString();
			String server = mc.getCurrentServerData().serverIP;
			int x = Integer.valueOf(coordsX.getText());
			int y = Integer.valueOf(coordsY.getText());
			int z = Integer.valueOf(coordsZ.getText());
			int color = colorPicker.getSelectedColor();
			
			WaypointsMod.addWaypoint(new Waypoint(name, world, server, x, y, z, color));
			mc.displayGuiScreen(null);
			mc.player.sendMessage(new TextComponentString(TextFormatting.GREEN + "Waypoint '" + name + "' created!"));
			return;
			
		case 2:
			mc.displayGuiScreen(null);
			return;
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int key) throws IOException {
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
