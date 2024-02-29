package net.xayanix.nssv.reallife.listeners;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerMoveListener implements Listener {

    public PlayerMoveListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(player.getAllowFlight()){
            if(!player.hasPermission("moderator"))
                player.setAllowFlight(false);
        }

    }
}
