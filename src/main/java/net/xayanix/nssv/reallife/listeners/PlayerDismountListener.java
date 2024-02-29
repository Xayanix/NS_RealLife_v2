package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.stamina.StaminaManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.spigotmc.event.entity.EntityDismountEvent;

public class PlayerDismountListener implements Listener {

    public PlayerDismountListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(EntityDismountEvent event) {
        if(event.getEntity().getType() == EntityType.PLAYER){
            Player player = (Player) event.getEntity();
            User user = UserManager.getUser(player);

            if(user.getCharacter().getStamina().getStaminaWorker() != null) {
                user.sendMessage("&8#&a Zakonczyles cwiczenia.");
                StaminaManager.getInstance().destroy(user);
            }

        }
    }
}
