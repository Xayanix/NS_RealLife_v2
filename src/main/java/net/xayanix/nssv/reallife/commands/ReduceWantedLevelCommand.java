package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
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

public class ReduceWantedLevelCommand extends BukkitCommand {

    public ReduceWantedLevelCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 2){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /zbijwl <gracz> <kwota>");
            return false;
        }

        Player player = Bukkit.getPlayerExact(arg3[0]);
        User user = UserManager.getUser(arg0.getName());

        if(player == arg0){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /zbijwl <gracz>");
            return false;
        }

        if(user.getCharacter().getJob().getType() != JobType.PRAWNIK) {
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes prawnikiem");
            return false;
        }

        int price = Integer.valueOf(arg3[1]);
        if(price < 150 || price > 10000){
            ChatUtil.sendMessage(arg0, "&8#&c Cena musi zawierac sie w przedziale &e150-10000 zlotych&c.");
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

        if(target.getCharacter().getWantedlevel() == 0){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest poszukiwany.");
            return false;
        }

        for(Player player1 : PlayerUtil.getNearbyPlayers(player, 32)) {
            if(UserManager.getUser(player1).getCharacter().getJob().getType() == JobType.POLICJANT) {
                ChatUtil.sendMessage(arg0, "&8#&c Nie mozesz redukowac poziomu poszukiwania gdyz policja jest w poblizu.");
                return true;
            }
        }

        if(!target.isCooldownFinished("zbijwl", TimeUnit.SECONDS.toMillis(15))){
            ChatUtil.sendMessage(arg0, "&8#&c WL mozesz redukowac co 15 sekund.");
            return false;
        }

        user.getUserTempData().setJobCaptcha(RandomUtil.generateRandomString(4));
        target.getUserTempData().addOffer(new Offer(arg0.getName(), price, OfferType.REDUCE_WANTED_LEVEL));
        target.sendMessage("&8#&e " + arg0.getName() + "&b proponuje Ci zredukowanie poziomu poszukiwania za oplata &e" + price + " zlotych&b.");
        target.sendMessage("&8#&b Jesli akceptujesz propozycje wpisz &e/akceptuj " + OfferType.REDUCE_WANTED_LEVEL+ " " + arg0.getName() + " " + user.getUserTempData().getJobCaptcha() +  "&b.");
        user.sendMessage("&8#&a Zaproponowales&e " + target.getName() + "&a zredukowanie poziomu poszukiwania za &e" + price + " zlotych&a.");

        return true;
    }
}
