package pw.cinque.waypoints.command;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.math.BigDecimal;
import java.util.List;

public class LocCommand extends CommandBase {

    private static final String NAME = "loc";

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
        return ImmutableList.of("location", "lochere", "at", "imat", "pos", "posat", "posme");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
//        player.sendChatMessage(String.format(
//            "%sI'm at %s%s%s, %s%s%s, %s%s%s in %s%s", GRAY,
//            RED, str(player.posX), GRAY,
//            RED, str(player.posY), GRAY,
//            RED, str(player.posZ), GRAY,
//            GOLD, player.worldObj.provider.getDimensionName()));
        player.sendChatMessage(String.format(
            "I'm at %s, %s, %s in %s",
            str(player.posX),
            str(player.posY),
            str(player.posZ),
            player.worldObj.provider.getDimensionName()));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    private static String str(double n) {
        return BigDecimal.valueOf(n).toPlainString();
    }

}
