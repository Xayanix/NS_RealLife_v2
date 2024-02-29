package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.job.firefighter.FireManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.Travel;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;

public class FireCommand extends BukkitCommand {

    public FireCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {

        User user = UserManager.getUser(arg0.getName());

        if(user.getCharacter().getJob().getType() != JobType.STRAZAK){
            user.sendMessage("&8#&c Nie jestes strazakiem.");
            return false;
        }

        if(FireManager.getInstance().getFireLocations().size() == 0){
            user.sendMessage("&8#&c Nie ma pozarow w okolicy.");
            return false;
        }

        new Travel(user, FireManager.getInstance().getFireLocations().get(RandomUtil.getRandom().nextInt(FireManager.getInstance().getFireLocations().size())).getLocation(),3);

        return true;
    }
}
