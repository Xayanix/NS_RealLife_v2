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
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class HealCommand extends BukkitCommand {

    public HealCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 1){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /ulecz <gracz>");
            return false;
        }

        Player player = Bukkit.getPlayerExact(arg3[0]);
        User user = UserManager.getUser(arg0.getName());

        if(player == arg0){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /ulecz <gracz>");
            return false;
        }

        if(user.getCharacter().getJob().getType() != JobType.RATOWNIK_MEDYCZNY) {
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes ratownikiem medycznym.");
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

        boolean illnes = (/*player.getHealth() != player.getMaxHealth() ||*/ player.hasPotionEffect(PotionEffectType.CONFUSION) || player.hasPotionEffect(PotionEffectType.SLOW) || player.hasPotionEffect(PotionEffectType.WEAKNESS));
        if(!illnes){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest chory ani ranny.");
            return false;
        }

        if(!user.isCooldownFinished("ulecz", TimeUnit.SECONDS.toMillis(10))) {
            ChatUtil.sendMessage(arg0, "&8#&c Mozesz uleczac raz na 10 sekund.");
            return false;
        }

        if(!user.isCooldownFinished("ulecz." + player.getName(), TimeUnit.MINUTES.toMillis(5), false)){
            ChatUtil.sendMessage(arg0, "&8#&c Mozesz uleczac tego samego gracza raz na 5 minut.");
            return false;
        }

        User target = UserManager.getUser(player);
        int price = 0;

        user.getUserTempData().setJobCaptcha(RandomUtil.generateRandomString(4));

        target.getUserTempData().addOffer(new Offer(arg0.getName(), price, OfferType.HEAL));
        target.sendMessage("&8#&e " + arg0.getName() + "&b proponuje Ci uleczenie za oplata &e" + price + " zlotych&b.");
        target.sendMessage("&8#&b Jesli akceptujesz propozycje wpisz &e/akceptuj " + OfferType.HEAL + " " + arg0.getName() + " " + user.getUserTempData().getJobCaptcha() +  "&b.");
        user.sendMessage("&8#&a Zaproponowales&e " + target.getName() + "&a uleczenie za &e" + price + " zlotych&a.");

        return true;
    }
}
