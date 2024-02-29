package net.xayanix.nssv.reallife.listeners;

import eu.haelexuis.utils.xoreboard.XoreBoard;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.nspd.ThiefCatch;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class PlayerQuitListener implements Listener {

    public PlayerQuitListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        player.getInventory().getViewers().forEach(HumanEntity::closeInventory);

        if(user.getUserTempData().isTraveling()){
            player.teleport(user.getUserTempData().getTravelFrom());
            user.getUserTempData().setTraveling(false);
            user.getUserTempData().setTravelFrom(null);
        }

        if(user.getUserTempData().getSelfThiefCatch() != null){
            ThiefCatch thiefCatch = user.getUserTempData().getSelfThiefCatch();
            user.getUserTempData().setSelfThiefCatch(null);
            thiefCatch.catched();
        }

        if(!user.isNew()) {
            user.setLastActiveTime(System.currentTimeMillis());
            user.synchronize();
        }

        user.setPlayer(null);

        XoreBoard xoreBoard = user.getUserTempData().getXoreBoard();
        if(xoreBoard != null){
            xoreBoard.destroy();
            user.getUserTempData().setXoreBoard(null);
        }

        new ArrayList<>(event.getPlayer().getInventory().getViewers()).forEach(HumanEntity::closeInventory);

    }
}
