package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.offer.OfferType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MarryCommand extends BukkitCommand {

    public MarryCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 1){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /slub <gracz>");
            return false;
        }

        User user = UserManager.getUser(arg0.getName());

        if(arg3[0].equalsIgnoreCase("zerwij")){
            if(user.getCharacter().getMarry().equalsIgnoreCase("")) {
                ChatUtil.sendMessage(arg0, "&8#&c Nie masz z nikim slubu.");
                return false;
            }

            User target = UserManager.getUser(user.getCharacter().getMarry());
            if(target != null && target.getCharacter().getMarry().equalsIgnoreCase(arg0.getName())) {
                target.getCharacter().setMarry("");
                target.synchronize();
            }

            user.getCharacter().setMarry("");
            ChatUtil.sendMessage(arg0, "&8#&c Zerwales slub z &6" + user.getCharacter().getMarry() + "&c.");
            return false;
        }

        Player player = Bukkit.getPlayerExact(arg3[0]);


        if(player == arg0){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /slub <gracz>");
            return false;
        }

        if(player == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza o tym nicku.");
            return false;
        }

        if(PlayerUtil.distanceBetweenPlayers((Player) arg0, player) > 8){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz znajduje sie za daleko od Ciebie.");
            return false;
        }

        if(!RegionUtil.isPlayerInRegion(user.getPlayer(), "kosciol")){
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes w kosciele.");
            return false;
        }

        User usertarget = UserManager.getUser(player);
        if(!user.getCharacter().getMarry().equalsIgnoreCase("")) {
            ChatUtil.sendMessage(arg0, "&8#&c Jestes juz w zwiazku z &6" + user.getCharacter().getMarry() + "&c.");
            return false;
        }

        if(!usertarget.getCharacter().getMarry().equalsIgnoreCase("")) {
            ChatUtil.sendMessage(arg0, "&8#&c Gracz jest juz w zwiazku z &6" + usertarget.getCharacter().getMarry() + "&c.");
            return false;
        }

        if(!user.isCooldownFinished("slub", TimeUnit.MINUTES.toMillis(30))){
            ChatUtil.sendMessage(arg0, "&8#&c Slub mozesz brac raz na 30 minut.");
            return false;
        }

        usertarget.getUserTempData().addOffer(new Offer(arg0.getName(), 0, OfferType.MARRY));
        usertarget.sendMessage("&8#&e " + arg0.getName() + "&a chce wziac z Toba slub.");
        usertarget.sendMessage("&8#&a Jesli akceptujesz propozycje wpisz &e/akceptuj " + OfferType.MARRY + " " + arg0.getName() + "&a.");
        user.sendMessage("&8#&a Zaproponowales&e " + usertarget.getName() + "&a wziecie slubu.");

        return true;
    }
}
