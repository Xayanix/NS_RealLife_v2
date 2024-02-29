package net.xayanix.nssv.reallife.listeners;

import com.intellectualcrafters.plot.object.Plot;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.ball.Ball;
import net.xayanix.nssv.reallife.ball.BallManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.hycel.HycelManager;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {

    public PlayerInteractEntityListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        User user = UserManager.getUser(player);

        if(entity.getType() == EntityType.ITEM_FRAME || entity.getType() == EntityType.ARMOR_STAND){
            Plot plot = PlotManager.getApi().getPlot(entity.getLocation());
            if(!player.hasPermission("admin") && (plot == null || (!plot.isOwner(player.getUniqueId()) && !plot.isAdded(player.getUniqueId()))))
                event.setCancelled(true);
        }

        if(entity.getType() == EntityType.WOLF && user.getCharacter().getJob().getType() == JobType.HYCEL && user.isCooldownFinished("hyceljob", 2500)) {
            Wolf wolf = (Wolf) entity;

            if(wolf.getOwner() instanceof Player){
                Player player1 = (Player) wolf.getOwner();
                if(player == player1)
                    return;

                if(!wolf.isSitting())
                    return;

                if(!player1.isOnline()){
                    HycelManager.takePet(user, entity);
                    return;
                }

                if(player1.getWorld() != entity.getWorld() || player1.getLocation().distance(entity.getLocation()) > 64)
                    HycelManager.takePet(user, entity);

            } else HycelManager.takePet(user, entity);
        } else if(entity.getType() == EntityType.LLAMA) {
            event.setCancelled(true);
        }

    }
}
