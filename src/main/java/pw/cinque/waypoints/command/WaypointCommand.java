package pw.cinque.waypoints.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import pw.cinque.waypoints.WaypointsMod;

public class WaypointCommand extends CommandBase {

    private static final String NAME = "waypoint";

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + NAME + " <enable|disable>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText("You are not a player."));
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        int argc = args.length;
        if (argc < 1) {
            sender.addChatMessage(new ChatComponentText("Waypoints: "
                + (WaypointsMod.enable ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.RED + "disabled")));
            return;
        }

        boolean enabled;
        String flag = args[0].toLowerCase();
        switch (flag) {
            case "1":
            case "y":
            case "t":
            case "on":
            case "yes":
            case "true":
            case "enable":
            case "enables":
            case "enabled":
                enabled = true;
                break;
            case "0":
            case "n":
            case "f":
            case "off":
            case "no":
            case "false":
            case "disable":
            case "disables":
            case "disabled":
                enabled = false;
                break;
            default:
                sender.addChatMessage(new ChatComponentText(flag + " is not valid value!"));
                return;
        }

        sender.addChatMessage(new ChatComponentText("Waypoints set to "
            + (enabled ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.RED + "disabled")));
        WaypointsMod.enable = enabled;
    }

}
