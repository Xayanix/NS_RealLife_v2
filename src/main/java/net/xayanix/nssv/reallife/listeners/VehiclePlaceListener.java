package net.xayanix.nssv.reallife.listeners;

import com.intellectualcrafters.plot.object.Plot;
import es.pollitoyeye.vehicles.events.VehiclePlaceEvent;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VehiclePlaceListener implements Listener {

    public VehiclePlaceListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(VehiclePlaceEvent event) {
        Player player = event.getOwner();
        User user = UserManager.getUser(player);

        if(user.getCharacter().getJail() != null){
            event.setCancelled(true);
            return;
        }

        if(player.getNearbyEntities(6.0, 6.0, 6.0).stream().anyMatch(entity -> entity.getType() == EntityType.ARMOR_STAND && entity.getCustomName() != null && entity.getCustomName().contains("Part"))){
            ChatUtil.sendMessage(player, "&8#&c Nie ma miejsca w poblizu na postawienie tego pojazdu, oddal sie od innych pojazdow i armor standow.");
            event.setCancelled(true);
            return;
        }

        if(user.getUserTempData().isTraveling()){
            event.setCancelled(true);
            return;
        }

        if(RegionUtil.isLocationInRegion(event.getLocation(), "novehicles")){
            event.setCancelled(true);
            ChatUtil.sendMessage(player, "&8#&c W tym miejscu nie wolno stawiac pojazdow.");
            return;
        }

        Plot plot = PlotManager.getApi().getPlot(event.getLocation());
        if(plot != null && !plot.isOwner(player.getUniqueId()) && !RegionUtil.isLocationInRegion(event.getLocation(), "novehbypass")){
            event.setCancelled(true);
            ChatUtil.sendMessage(player, "&8#&c Ten pojazd mozesz stawiac na swojej dzialce lub na drodze.");
            return;
        }




    }
}
