package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.offer.OfferType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BodyGuardCommand extends BukkitCommand {

    public BodyGuardCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 2){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /ochrona <gracz> <cena>");
            return false;
        }

        Player player = Bukkit.getPlayerExact(arg3[0]);
        User user = UserManager.getUser(arg0.getName());

        if(!StringUtil.isInteger(arg3[1])){
            ChatUtil.sendMessage(arg0, "&8#&7 " + arg3[1] + "&c nie jest prawidlowa liczba.");
            return false;
        }

        int price = Integer.parseInt(arg3[1]);
        if(price < 200 || price > 800){
            ChatUtil.sendMessage(arg0, "&8#&c Cena musi zawierac sie w przedziale &e200-800 zlotych&c.");
            return false;
        }

        if(player == arg0){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /ochrona <gracz> <cena>");
            return false;
        }

        if(user.getCharacter().getJob().getType() != JobType.OCHRONA) {
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes ochroniarzem.");
            return false;
        }

        if(player == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza o tym nicku.");
            return false;
        }

        User target = UserManager.getUser(player);

        if(PlayerUtil.distanceBetweenPlayers((Player) arg0, player) > 8){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz znajduje sie za daleko od Ciebie.");
            return false;
        }

        if(!target.isCooldownFinished("ochrona", TimeUnit.SECONDS.toMillis(10))){
            ChatUtil.sendMessage(arg0, "&8#&c Oferowac ochrone mozesz co 10 sekund.");
            return false;
        }

        user.getUserTempData().setJobCaptcha(RandomUtil.generateRandomString(4));

        target.getUserTempData().addOffer(new Offer(arg0.getName(), price, OfferType.PROTECTION));
        target.sendMessage("&8#&e " + arg0.getName() + "&b proponuje Ci ochrone (przed zlodziejami) za oplata &e" + price + " zlotych&b.");
        target.sendMessage("&8#&b Jesli akceptujesz propozycje wpisz &e/akceptuj " + OfferType.PROTECTION + " " + arg0.getName() + " " + user.getUserTempData().getJobCaptcha() +  "&b.");
        user.sendMessage("&8#&a Zaproponowales&e " + target.getName() + "&a ochrone za &e" + price + " zlotych&a.");

        return true;
    }
}
