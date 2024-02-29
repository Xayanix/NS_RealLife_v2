package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.block.SignChangeEvent;

public class PlayerSignEditListener implements Listener {

    public PlayerSignEditListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(SignChangeEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("admin")){
            if(event.getLine(0).equalsIgnoreCase("[B]")) {
                event.setLine(0, ChatUtil.fixColors("&9[Bankomat]"));
                event.setLine(1, ChatUtil.fixColors(" "));
                event.setLine(2, ChatUtil.fixColors(" "));
                event.setLine(3, ChatUtil.fixColors("&7(kliknij PPM)"));
            }
        }

    }
}
