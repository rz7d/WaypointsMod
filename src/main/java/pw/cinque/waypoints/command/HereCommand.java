package pw.cinque.waypoints.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.RandomUtils;
import pw.cinque.waypoints.WaypointsMod;
import pw.cinque.waypoints.entity.Location;
import pw.cinque.waypoints.entity.Waypoint;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class HereCommand extends CommandBase {

    private static final String NAME = "here";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        if (sender instanceof EntityPlayer) {
            return "/" + NAME + " or /" + NAME + " <color> <name...>";
        }
        return "/" + NAME;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) {
            sender.addChatMessage(new ChatComponentText("You are not a player."));
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        int argc = args.length;

        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;

        String name = "X: " + x + ", Y: " + y + ", Z: " + z;
        Color color = new Color(new Random(Double.doubleToLongBits(x + y + z)).nextInt() & 0x00FFFFFF);

        if (argc >= 1) {
            color = colorize(args[0]);
            if (color == null) {
                sender.addChatMessage(new ChatComponentText(args[0] + " is invalid color"));
                color = random();
            }
        }
        if (argc >= 2) name = String.join(" ", Arrays.copyOfRange(args, 1, argc));

        WaypointsMod.addWaypoint(Waypoint.of(Location.of(x, y, z, player.worldObj.provider.getDimensionName()),
            name, color, Minecraft.getMinecraft().getCurrentServerData().serverIP));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Instance waypoint '" + name + "' created."));
    }

    private static final Map<String, Color> COLOR_NAME_MAPPING;

    static {
        COLOR_NAME_MAPPING = Arrays.stream(Color.class.getFields())
            .filter(f -> f.getType() == Color.class)
            .collect(Collectors.toMap(Field::getName, f -> {
                try {
                    return (Color) f.get(null);
                } catch (IllegalAccessException exception) {
                    throw new InternalError(exception);
                }
            }));
    }

    private static Color colorize(String descriptor) {
        if (descriptor == null) {
            return null;
        }

        if (descriptor.length() == 6) {
            try {
                return new Color(Integer.parseInt(descriptor, 16));
            } catch (NumberFormatException nfe) {
                return null;
            }
        }

        return COLOR_NAME_MAPPING.getOrDefault(descriptor, null);
    }

    private static Color random() {
        return new Color(RandomUtils.nextInt(0, 0xFFFFFF));
    }

}
