package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class CasinoCommand extends BukkitCommand {

    public CasinoCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 2){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /kasyno <gracz> <kwota>");
            return false;
        }

        Player player = Bukkit.getPlayerExact(arg3[0]);
        if(player == null){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz jest offline.");
            return false;
        }

        User userplayer = UserManager.getUser(player);
        User userarg0 = UserManager.getUser(arg0.getName());

        if(!RegionUtil.isPlayerInRegion((Player) arg0, "kasyno")){
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes w kasynie.");
            return false;
        }

        if(!RegionUtil.isPlayerInRegion(player, "kasyno")){
            ChatUtil.sendMessage(arg0, "&8#&c Podany gracz nie jest w kasynie.");
            return false;
        }

        if(!StringUtil.isInteger(arg3[1])){
            ChatUtil.sendMessage(arg0, "&8#&7 " + arg3[1] + "&c nie jest prawidlowa liczba.");
            return false;
        }

        int price = Integer.valueOf(arg3[1]);
        if(price <= 0){
            ChatUtil.sendMessage(arg0, "&8#&c Minimalna kwota gry to &e1 zlotych&c.");
            return false;
        }

        if(player == arg0){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /kasyno <gracz> <kwota>");
            return false;
        }

        if(userarg0.getCharacter().getMoney().getWallet() < price){
            ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz &e" + price + " zlotych&c na gre o ta kwote.");
            return false;
        }

        if(userplayer.getCharacter().getMoney().getWallet() < price){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz &e" + userplayer.getName() + "&c nie posiada &e" + price + " zlotych&c na gre o ta kwote.");
            return false;
        }

        if(!userarg0.isCooldownFinished("kasyno", TimeUnit.SECONDS.toMillis(15))){
            userarg0.sendMessage("&8#&a Oferty w kasynie mozesz skladac raz na 15 sekund.");
            return false;
        }


        userplayer.sendMessage("&8#&e " + userarg0.getName() + "&a zaprasza Cie do gry w kasynie o &e" + price + " zlotych&a.");
        userplayer.sendMessage("&8#&a Wpisz &e/akceptuj " + OfferType.CASINO + " " + arg0.getName() + "&a, aby zaakceptowac.");
        userarg0.sendMessage("&8#&a Wyslales &e" + userplayer.getName() + "&a zaproszenie do gry o &e" + price + " zlotych&a.");

        userplayer.getUserTempData().addOffer(new Offer(arg0.getName(), price, OfferType.CASINO));

        return true;
    }
}
