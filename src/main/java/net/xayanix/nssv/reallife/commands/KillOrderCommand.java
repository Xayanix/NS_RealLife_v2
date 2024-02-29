package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.PlayerUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KillOrderCommand extends BukkitCommand {

    public KillOrderCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);
        JobType type = user.getCharacter().getJob().getType();

        if(type != JobType.LOWCA_GLOW) {
            ChatUtil.sendMessage(arg0, "&8#&c Komenda dostepna wylacznie lowcow glow.");
            return false;
        }

        if(arg3.length != 1){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /zlecenie <poszukiwany gracz>");
            return false;
        }

        Player target = Bukkit.getPlayerExact(arg3[0]);
        if(target == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
            return false;
        }

        User usertarget = UserManager.getUser(target);
        if(usertarget.getCharacter().getWantedlevel() == 0){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest poszukiwany.");
            return false;
        }

        double dist = net.xayanix.nssv.reallife.utils.PlayerUtil.distanceBetweenPlayers(player, target);
        if(!RegionUtil.isPlayerInRegion(player, "lowcyglow") && (dist > 64 || dist < 6)) {
            ChatUtil.sendMessage(arg0, "&8#&c Zeby wziac zlecenie na gracza musisz byc w biurze lowcow lub znajdowac sie od 8 do 64 kratek od gracza.");
            return false;
        }

        if(!user.isCooldownFinished("zlecenielg", TimeUnit.SECONDS.toMillis(25))){
            ChatUtil.sendMessage(arg0, "&8#&c Zlecenie mozesz brac raz na 25s.");
            return false;
        }

        usertarget.sendMessage("&8#&b Twoje listy goncze trafily do lowcow glow, lepiej sie pilnuj!");
        user.getUserTempData().setHeadHunterTarget(usertarget.getName());
        user.sendMessage("&8#&a Przyjales zlecenie na gracza &7" + target.getName() + "&a.");
        user.sendMessage("&8#&a Twój kompas bedzie caly czas wskazywac lokalizacje gracza.");

        return true;
    }
}
