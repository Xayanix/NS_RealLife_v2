package net.xayanix.nssv.reallife.listeners;

import com.cnaude.chairs.api.PlayerChairSitEvent;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;

public class PlayerChairSitListener implements Listener {

    public PlayerChairSitListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerChairSitEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(user.getCharacter().getJail() != null)
            event.setCancelled(true);

    }
}
