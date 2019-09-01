package pw.cinque.waypoints.command;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class ImAtCommand extends AbstractCommand {

    private static final String NAME = "imat";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + NAME;
    }

    @Override
    public List getCommandAliases() {
        return ImmutableList.of("at", "locate", "location", "4sq");
    }

    @Override
    public boolean execute(ICommandSender sender, String[] args) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        player.sendChatMessage(String.format(
            "I'm at %s, %s, %s in %s",
            str(player.posX),
            str(player.posY),
            str(player.posZ),
            player.worldObj.provider.getDimensionName()));
        return true;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    private static String str(double n) {
        return String.format("%.2f", n);
    }

}
