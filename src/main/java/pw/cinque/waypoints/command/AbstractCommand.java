package pw.cinque.waypoints.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractCommand extends CommandBase {

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public final void processCommand(ICommandSender sender, String[] args) {
        final boolean success = sender instanceof EntityPlayer
            ? execute((EntityPlayer) sender, args)
            : execute(sender, args);
        if (!success) {
            throw new WrongUsageException(getCommandUsage(sender));
        }
    }

    /**
     * A generic way to performing this command.
     *
     * @param sender the sender
     * @return true if succeed, else false.
     */
    public abstract boolean execute(ICommandSender sender, String[] args);

    /**
     * Performing this command by specified EntityPlayer
     *
     * @param player the sender
     * @return true if succeed, else false.
     */
    public boolean execute(EntityPlayer player, String[] args) {
        return execute((ICommandSender) player, args);
    }

}
