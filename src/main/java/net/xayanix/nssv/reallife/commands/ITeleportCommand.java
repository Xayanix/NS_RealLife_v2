package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.casino.Casino;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.family.Family;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.misc.Repairables;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.Travel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ITeleportCommand extends BukkitCommand {

    public ITeleportCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 1){
            return false;
        }


        String name = arg3[0];
        User user = UserManager.getUser(name);
        User sender = UserManager.getUser(arg0.getName());

        if(user == sender)
            return false;

        if(user != null && user.getPlayer() != null && user.getUserTempData().isAcceptingAllTeleportations() && user.getUserTempData().getTeleportAllowJob() == sender.getCharacter().getJob().getType()) {
            sender.sendMessage("&8#&a Wsiadles w autobus i podrozujesz do gracza...");
            user.sendMessage("&8#&e " + sender.getName() + "&b zareagowal na Twoje wezwanie i juz jest w drodze...");

            JobManager.broadcast(user.getUserTempData().getTeleportAllowJob(), "&8#&b " + user.getUserTempData().getTeleportAllowJob().getShortName() + " &e" + sender.getName() + " &bzareagowal na wezwanie gracza &e" + user.getName() + "&b.");

            user.getUserTempData().setTeleportAllowJob(null);
            user.getUserTempData().setTeleportAllow(0);

            new Travel(sender, user.getPlayer().getLocation(), 5);
            return true;
        }

        sender.sendMessage("&8#&c Wezwanie wygaslo lub jest nieprawidlowe.");

        return true;
    }
}
