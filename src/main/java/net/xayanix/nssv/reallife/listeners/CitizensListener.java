package net.xayanix.nssv.reallife.listeners;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CitizensListener implements Listener {

    public CitizensListener(JavaPlugin instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEvent(NPCRightClickEvent event) {
        User user = UserManager.getUser(event.getClicker());
        user.isCooldownFinished("guiCommandAllow", System.currentTimeMillis() + 10000);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEventMonitor(NPCRightClickEvent event) {
        User user = UserManager.getUser(event.getClicker());
        user.cancelCooldown("guiCommandAllow");
    }

}
