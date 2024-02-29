package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.compass.CompassUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class LocalizeCommand extends BukkitCommand {

    public LocalizeCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);
        JobType type = user.getCharacter().getJob().getType();

        if(type != JobType.POLICJANT) {
            ChatUtil.sendMessage(arg0, "&8#&c Komenda dostepna wylacznie dla policjantow.");
            return false;
        }

        if(arg3.length != 1){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /namierz <poszukiwany gracz>");
            return false;
        }

        Player target = Bukkit.getPlayerExact(arg3[0]);
        if(target == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
            return false;
        }

        User usertarget = UserManager.getUser(target);
        if(target.getWorld() != player.getWorld()){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz jest w innym swiecie niz Ty.");
            return false;
        }

        if(!user.isCooldownFinished("namierzlg", TimeUnit.SECONDS.toMillis(15))){
            ChatUtil.sendMessage(arg0, "&8#&c Namierzac mozesz brac raz na 15s.");
            return false;
        }

        if(usertarget.getCharacter().getWantedlevel() == 0){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest poszukiwany.");
            return false;
        }

        user.sendMessage("&8#&a Lokalizacja gracza &e" + usertarget.getName() + "&a to:&e X: " + Math.round(target.getLocation().getX()) + " Y: " + Math.round(target.getLocation().getY()) + "Z: "+ Math.round(target.getLocation().getZ()));
        CompassUtil.setTarget(player, target.getLocation());

        return true;
    }
}
