package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.basic.Main;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.mongodb.MongoConnection;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
public class PlayerLoginListener implements Listener {

    public PlayerLoginListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(AsyncPlayerPreLoginEvent event){
        if(MongoConnection.isLoading()){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColors("&8#&c Serwer jeszcze sie wczytuje!"));
            return;
        }
    }

}
