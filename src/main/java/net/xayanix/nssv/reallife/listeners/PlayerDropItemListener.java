package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.trash.TrashManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    public PlayerDropItemListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerDropItemEvent event) {
        TrashManager.getInstance().onEvent(event);
    }
}
