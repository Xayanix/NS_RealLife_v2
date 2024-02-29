package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.reallife.misc.ChatFields;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MoneyCommand extends BukkitCommand {

    public MoneyCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);

        user.sendMessage(ChatFields.SEPARATOR);
        user.sendMessage("&8#&a W portfelu masz &e" + user.getCharacter().getMoney().getWallet() + " zlotych&a.");
        user.sendMessage("&8#&a W banku masz &e" + user.getCharacter().getMoney().getBank() + " zlotych&a.");
        user.sendMessage(ChatFields.SEPARATOR);

        return true;
    }
}
