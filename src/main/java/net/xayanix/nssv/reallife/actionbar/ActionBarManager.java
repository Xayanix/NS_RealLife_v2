package net.xayanix.nssv.reallife.actionbar;

import org.bukkit.entity.Player;

public class ActionBarManager {

    public static void set(Player player, String actionbar){
        new ActionBar(actionbar).sendActionBar(player);
    }

}
