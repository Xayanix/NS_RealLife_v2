package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.misc.ChatFields;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class CallJobCommand extends BukkitCommand {

    public CallJobCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setPermission(null);
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length < 1){
            String jobs = "";
            for(JobType jobType : JobType.values())
                jobs = jobs + ", " + jobType.getShortName();
            jobs = jobs.replaceFirst(", ", "");

            ChatUtil.sendMessage(arg0, "&8#&c Dostepne wezwania:&7 " + jobs);
            return false;
        }

        String type = StringUtil.arrayToString(arg3, 0);
        User user = UserManager.getUser(arg0.getName());
        JobType jobType = null;

        for(JobType jt : JobType.values())
            if(jt.getShortName().equalsIgnoreCase(type))
                jobType = jt;

        if(jobType == null){
            String jobs = "";
            for(JobType jobTypee : JobType.values())
                jobs = jobs + ", " + jobTypee.getShortName();
            jobs = jobs.replaceFirst(", ", "");

            ChatUtil.sendMessage(arg0, "&8#&c Dostepne wezwania:&7 " + jobs);
            return false;
        }

        if(!user.isCooldownFinished("wezwij", TimeUnit.SECONDS.toMillis(30))){
            ChatUtil.sendMessage(arg0, "&8#&c Mozesz wzywac graczy raz na 30 sekund.");
            return false;
        }

        JobManager.broadcast(jobType, "&8#&b " + arg0.getName() + " potrzebuje " + jobType.getShortName().toLowerCase() + "a, udaj sie do niego i zaoferuj mu swoje uslugi.");
        JobManager.broadcast(jobType, ChatFields.getTeleportMessage(user.getName()));

        user.sendMessage("&8#&a Wezwales " + jobType.getShortName().toLowerCase() + "a, wkrótce powinien sie ktos zjawic.");
        user.getUserTempData().setTeleportationsAllowed();
        user.getUserTempData().setTeleportAllowJob(jobType);

        return true;
    }
}
