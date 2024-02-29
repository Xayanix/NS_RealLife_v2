package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerVelocityEvent;

public class EntityVelocityListener implements Listener {

    public EntityVelocityListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerVelocityEvent event) {
        Player player = event.getPlayer();
        if(!player.isOnline())
            event.setCancelled(true);
    }
}
