package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.stamina.StaminaManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TrainCommand extends BukkitCommand {

    public TrainCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }
    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(!(arg0 instanceof Player)) return false;
        User user = UserManager.getUser(arg0.getName());
        if(!RegionUtil.isPlayerInRegion(user.getPlayer(), "silownia")) {
            ChatUtil.sendMessage(user.getPlayer(), "&8#&c Nie jestes na silowni.");
            return false;
        }

        StaminaManager.getInstance().work(user);

        return true;
    }
}
