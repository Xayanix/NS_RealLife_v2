package net.xayanix.nssv.reallife.compass;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CompassUtil {

    public static void setTarget(Player player, Location location){
        player.setCompassTarget(location);
        if(player.getInventory().contains(Material.COMPASS))
            ChatUtil.sendMessage(player, "&8#&a Twój kompas wskazuje nowy cel [&7" + LocationUtil.nice(location) + "&a].");
    }

}
