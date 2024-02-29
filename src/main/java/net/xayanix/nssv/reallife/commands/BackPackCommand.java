package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BackPackCommand extends BukkitCommand {

    public BackPackCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }
    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg0.hasPermission("admin") && arg3.length > 0){
            User user = UserManager.getUser(arg3[0]);
            if(user == null || user.getCharacter().getBackPack() == null){
                ChatUtil.sendMessage(arg0, "&8#&c Brak plecaka.");
                return false;
            }

            user.getCharacter().getBackPack().display(UserManager.getUser(arg0.getName()));
            return true;
        }
        if(!(arg0 instanceof Player)) return false;
        User user = UserManager.getUser(arg0.getName());

        if(user.getCharacter().getBackPack() == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie masz plecaka.");
            ChatUtil.sendMessage(arg0, "&8#&c Zakup plecak w sklepie z bronia.");
            return false;
        }

        user.getCharacter().getBackPack().display(user);

        return true;
    }
}
