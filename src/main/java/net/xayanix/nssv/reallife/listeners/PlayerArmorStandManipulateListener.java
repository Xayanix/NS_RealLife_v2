package net.xayanix.nssv.reallife.listeners;

import com.intellectualcrafters.plot.object.Plot;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.ball.Ball;
import net.xayanix.nssv.reallife.ball.BallManager;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class PlayerArmorStandManipulateListener implements Listener {

    public PlayerArmorStandManipulateListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerArmorStandManipulateEvent event) {
        Ball ball = BallManager.getInstance().getBall(event.getRightClicked());
        if(ball != null)
            event.setCancelled(true);

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        Plot plot = PlotManager.getApi().getPlot(entity.getLocation());

        if(!player.hasPermission("admin"))
            if(plot != null && (!plot.isOwner(player.getUniqueId()) && !plot.isAdded(player.getUniqueId())))
                event.setCancelled(true);
    }
}
