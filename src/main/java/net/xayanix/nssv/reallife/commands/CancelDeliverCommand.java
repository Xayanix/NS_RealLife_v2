package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CancelDeliverCommand extends BukkitCommand {

    public CancelDeliverCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }
    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(!(arg0 instanceof Player)) return false;
        User user = UserManager.getUser(arg0.getName());

        if(user.getUserTempData().getPackTarget() == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie masz zlecenia dostarczenia paczki.");
            return false;
        }

        user.getUserTempData().setPackTarget(null);
        user.sendMessage("&8#&c Wyrzuciles paczke na ziemie.");

        return true;
    }
}
