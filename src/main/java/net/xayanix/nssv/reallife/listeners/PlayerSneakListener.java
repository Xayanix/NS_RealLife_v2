package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneakListener implements Listener {

    public PlayerSneakListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(user.getUserTempData().isRobbed()){
            user.getUserTempData().setRobbed(false);
            ChatUtil.sendMessage(player, "&8#&a Obroniles sie przed próba kradziezy.");
        }

    }
}
