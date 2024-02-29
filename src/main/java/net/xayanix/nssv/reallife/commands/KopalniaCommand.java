package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.compass.CompassUtil;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.Travel;
import net.xayanix.nssv.reallife.warp.WarpManager;
import net.xayanix.nssv.reallife.warp.Warps;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class KopalniaCommand extends BukkitCommand {

    public KopalniaCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);

        int price = (user.getCharacter().getLevel() > 3 ? 150 : 0);

        if(arg3.length == 1 && arg3[0].equalsIgnoreCase("wykup")) {
            if(user.isVip()){
                user.sendMessage("&8#&c VIP nie musi kupowac dostepu do tej kopalni.");
                return false;
            }

            if(user.getCharacter().getVipMine() > System.currentTimeMillis()){
                user.sendMessage("&8#&a Posiadasz juz dostep do kopalni VIP do:&e " + TimeUtil.formatDate(user.getCharacter().getVipMine()));
                return false;
            }

            price = Bukkit.getOnlinePlayers().size() * 100;

            if(!user.isCooldownFinished("kupvipmine", TimeUnit.SECONDS.toMillis(15))) {
                if(EconomyManager.getInstance().takeWallet(user, price)) {
                    user.sendTitle("&6&lKopalnia VIP", "&7Zakupiles dostep na 2 godziny.", 20, 20, 20);
                    user.getCharacter().setVipMine(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2));
                }
            }

            user.sendMessage("&8#&c Dostep do kopalni VIP kosztuje w tej chwili okolo &6" + price + " zlotych&c.");
            user.sendMessage("&8#&c Kliknij na tego NPC jeszcze raz, aby potwierdzic zakup.");
            return true;
        }

        if(user.getCharacter().getLevel() < 5 && price > 0 && user.isCooldownFinished("kopalniatp", TimeUnit.SECONDS.toMillis(10))){
            user.sendMessage("&8#&c Koszt transportu na KOPALNIA wyniesie Cie:&7 " + price + " zlotych");
            user.sendMessage("&8#&c Jesli chcesz kontynuuowac, wpisz ta komende jeszcze raz.");
            return true;
        }

        if(EconomyManager.getInstance().takeWallet(user, price)) {
            new Travel(user, (user.isVip() ? LocationList.KOPALNIA_VIP : Warps.TARTAK_KOPALNIA.getLocation()), (user.isVip() ? 0 : WarpManager.travelTime(player, LocationList.SPAWN)));
            return true;
        }

        user.sendMessage("&8#&c Nie miales odpowiedniej ilosci srodkow w kieszeni.");
        CompassUtil.setTarget(player, Warps.TARTAK_KOPALNIA.getLocation());

        return true;
    }
}
