package net.xayanix.nssv.reallife.listeners;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.plotsquared.bukkit.events.PlotCancelActionEvent;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.region.WorldGuardManager;
import net.xayanix.nssv.reallife.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlotCancelActionListener implements Listener {

    public PlotCancelActionListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlotCancelActionEvent event) {
        Player player = event.getPlayer();
        Location location = event.getLocation();

        if(player.hasPermission("admin")) {
            event.setCancelled(true);
            return;
        }

        if(event.getEvent() instanceof PlayerInteractEvent){
            PlayerInteractEvent event1 = (PlayerInteractEvent) event.getEvent();
            if(event1.getClickedBlock() != null){
                Location location1 = event1.getClickedBlock().getLocation();
                if(event1.getBlockFace() != null && event1.getBlockFace() == BlockFace.UP)
                    location1 = location1.add(0.0, 1, 0.0);


                Material material = event1.getClickedBlock().getType();
                if(material == Material.CHEST /*|| material.toString().contains("DOOR")*/ || material == Material.TRAPPED_CHEST || material == Material.ANVIL || material == Material.FURNACE || material == Material.HOPPER || material == Material.BURNING_FURNACE || material.toString().contains("SHULKER")) {
                    Plot plot = PlotManager.getApi().getPlot(location1);
                    if(plot == null){
                        event.setCancelled(true);
                        return;
                    }

                    if(!plot.isOwner(player.getUniqueId()) && !plot.isAdded(player.getUniqueId())) {
                        if(RegionUtil.isInAnyRegion(location1) && WorldGuardManager.getInstance().getWg().canBuild(player, location1)) {
                            event.setCancelled(true);
                            return;
                        }

                        event.setCancelled(false);
                        event1.setCancelled(true);

                        return;
                    }

                }

            }

            event.setCancelled(true);
            return;
        }

        if(!RegionUtil.isInAnyRegion(location))
            return;

        boolean canbuild = WorldGuardManager.getInstance().getWg().canBuild(player, location);

        if(canbuild)
            event.setCancelled(true);

    }
}
