package pw.cinque.waypoints.command;

import com.google.common.collect.ImmutableList;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import pw.cinque.waypoints.WaypointsMod;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;

public class WaypointCommand extends AbstractCommand {

    private static final String NAME = "waypoint";

    private static final Collection<String> POSITIVE_SIGNS;
    private static final Collection<String> NEGATIVE_SIGNS;

    static {
        POSITIVE_SIGNS = ImmutableList.of(
            "1",
            "on",
            "y",
            "yes",
            "t",
            "true",
            "e",
            "enable",
            "s",
            "show",
            "activate",
            "allow"
        );
        NEGATIVE_SIGNS = ImmutableList.of(
            "0",
            "off",
            "n",
            "no",
            "f",
            "false",
            "d",
            "disable",
            "h",
            "hide",
            "deactivate",
            "disallow"
        );
        assert POSITIVE_SIGNS.size() == NEGATIVE_SIGNS.size();
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
    public List getCommandAliases() {
        return ImmutableList.of("waypoints");
    }

    @Override
    public boolean execute(ICommandSender sender, String[] args) {
        sender.addChatMessage(new ChatComponentText("You are not a player."));
        return true;
    }

    @Override
    public boolean execute(EntityPlayer player, String[] args) {
        int argc = args.length;
        if (argc < 1) {
            player.addChatMessage(new ChatComponentText(
                "Waypoints were " + (WaypointsMod.enabled ? GREEN + "enabled" : RED + "disabled")));
            return true;
        }

        String flag = args[0].toLowerCase();

        boolean positive = POSITIVE_SIGNS.contains(flag);
        boolean negative = NEGATIVE_SIGNS.contains(flag);
        if (!(positive || negative)) {
            player.addChatMessage(new ChatComponentText(flag + " is not a valid flag!"));
            player.addChatMessage(new ChatComponentText("Available flags:"));
            pickup(POSITIVE_SIGNS, 3).stream().map(e -> "- " + GREEN + e).map(ChatComponentText::new).forEach(player::addChatMessage);
            pickup(NEGATIVE_SIGNS, 3).stream().map(e -> "- " + RED + e).map(ChatComponentText::new).forEach(player::addChatMessage);
            return true;
        }

        boolean enabled;
        enabled = positive;
        enabled &= !negative;

        player.addChatMessage(new ChatComponentText(String.format(
            "Waypoints have been %s%s.",
            enabled ? GREEN : RED,
            enabled ? "enabled" : "disabled"
        )));

        WaypointsMod.enabled = enabled;
        return true;
    }

    private static <T> Collection<T> pickup(Collection<T> from, int count) {
        return new Random().ints(0, from.size())
            .distinct()
            .limit(count)
            .mapToObj(n -> {
                if (from instanceof List) {
                    return ((List<T>) from).get(n);
                }
                Iterator<T> iterator = from.iterator();
                for (int i = 0; i < n - 1; ++i) {
                    iterator.next();
                }
                return iterator.next();
            })
            .collect(Collectors.toList());
    }

}
