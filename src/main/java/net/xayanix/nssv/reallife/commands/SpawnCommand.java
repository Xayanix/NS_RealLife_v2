package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.compass.CompassUtil;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.Travel;
import net.xayanix.nssv.reallife.warp.WarpManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SpawnCommand extends BukkitCommand {

    public SpawnCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);

        int price = (user.getCharacter().getLevel() > 3 ? 50 : 0);

        if(user.getCharacter().getLevel() < 5 && price > 0 && user.isCooldownFinished("spawntp", TimeUnit.SECONDS.toMillis(10))){
            user.sendMessage("&8#&c Koszt transportu na SPAWN wyniesie Cie:&7 " + price + " zlotych");
            user.sendMessage("&8#&c Jesli chcesz kontynuuowac, wpisz ta komende jeszcze raz.");
            return true;
        }

        if(price > user.getCharacter().getMoney().getWallet())
            price = 0;

        if(EconomyManager.getInstance().takeWallet(user, price)) {
            new Travel(user, LocationList.SPAWN, WarpManager.travelTime(player, LocationList.SPAWN));
            return true;
        }

        user.sendMessage("&8#&c Nie miales odpowiedniej ilosci srodkow w kieszeni.");
        CompassUtil.setTarget(player, LocationList.SPAWN);

        return true;
    }
}
