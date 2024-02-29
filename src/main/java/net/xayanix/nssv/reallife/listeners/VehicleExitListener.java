package net.xayanix.nssv.reallife.listeners;

import es.pollitoyeye.vehicles.events.VehicleExitEvent;
import me.zombie_striker.qg.exp.cars.api.events.QAVVehicleExitEvent;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;

public class VehicleExitListener implements Listener {

    public VehicleExitListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(VehicleExitEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(user.getUserTempData().getSpeedometer() != null)
            user.getUserTempData().getSpeedometer().clear();
    }

    @EventHandler
    public void onEvent(QAVVehicleExitEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(user.getUserTempData().getSpeedometer() != null)
            user.getUserTempData().getSpeedometer().clear();
    }


}
