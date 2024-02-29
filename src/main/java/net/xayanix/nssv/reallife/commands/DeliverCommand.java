package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DeliverCommand extends BukkitCommand {

    public DeliverCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }
    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(!(arg0 instanceof Player)) return false;
        User user = UserManager.getUser(arg0.getName());

        if(user.getCharacter().getJob().getType() != JobType.KURIER){
            user.sendMessage("&8#&c Nie jestes kurierem.");
            return false;
        }

        if(user.getUserTempData().getPackTarget() == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie masz zlecenia dostarczenia paczki, wez je w hurtownii kurierow");
            return false;
        }

        if(!RegionUtil.isPlayerInRegion(user.getPlayer(), user.getUserTempData().getPackTarget().getRegion())) {
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes w odpowiednim miejscu.");
            ChatUtil.sendMessage(arg0, "&8#&c Paczke musisz dostarczyc do sklepu &7" + user.getUserTempData().getPackTarget().getDesc() + "&c.");
            return false;
        }

        user.getUserTempData().setPackTarget(null);
        user.getCharacter().getJob().addPoint(user.getPlayer(), true);
        user.sendMessage("&8#&b Pomyslnie dostarczyles paczke do celu.");

        if(user.getCharacter().getCompany() != null){
            Company company = user.getCharacter().getCompany();
            company.setEarned(company.getEarned() + JobType.KURIER.getReward()/2);
        }

        return true;
    }
}
