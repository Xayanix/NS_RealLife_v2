package net.xayanix.nssv.reallife.warp;

import net.milkbowl.vault.chat.Chat;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WarpManager {

    public static int travelPrice(Player player, Location warp){
        if(UserManager.getUser(player).getCharacter().getLevel() < 3)
            return 0;
        if(player.getWorld() != warp.getWorld())
            return 50;

        int dist = (int) player.getLocation().distance(warp) / 5;
        if(dist <= 0)
            dist = 1;
        if(dist > 300)
            dist = 300;

        return dist;
    }

    public static int travelTime(Player player, Location warp){
        /*
        if(player.getWorld() != warp.getWorld())
            return 20;

        int dist = (int) player.getLocation().distance(warp) / 10;

        if(dist > 20)
            dist = 20;

        if(player.hasPermission("vip"))
            dist = dist / 5;
        */
        return 5;
    }

    public static void travel(Player player, Location warp){
        User user = UserManager.getUser(player);
        int price = travelPrice(player, warp);
        int time = travelTime(player, warp);

        if(user.getCharacter().getLevel() < 3)
            user.sendMessage("&8#&a Jestes nowy na serwerze, wiec podrozujesz busem za darmo.");

        if(user.getUserTempData().isTraveling()){
            ChatUtil.sendMessage(player, "&8#&c Przemieszczasz sie juz, poczekaj az obecny transport sie zakonczy.");
            return;
        }

        if(!EconomyManager.getInstance().takeWallet(user, price))
            return;

        new Travel(user, warp, time);

    }

}
