package net.xayanix.nssv.reallife.end;

import es.pollitoyeye.vehicles.interfaces.Vehicle;
import lombok.Getter;
import me.zombie_striker.qg.exp.cars.VehicleEntity;
import me.zombie_striker.qg.exp.cars.baseclasses.AbstractHelicopter;
import me.zombie_striker.qg.exp.cars.baseclasses.AbstractPlane;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.speedometer.SpeedometerType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EndManager implements Runnable {

    @Getter
    private static EndManager instance;

    public EndManager() {
        instance = this;
        Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), this, 20, 20);
    }

    public void enter(User user){
        user.getPlayer().teleport(LocationList.END);
        user.sendTitle("&e&lKsiezyc!", "&cWyladowales na ksiezycu", 20, 60, 20);
    }

    public void leave(User user){
        user.getPlayer().teleport(LocationList.SPAWN);
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);
            if(player.getLocation().getY() >= 250 && player.getWorld().getName().equalsIgnoreCase("PlotWorld") && user.getUserTempData().getSpeedometer() != null) {

                if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.OLD){
                    Vehicle vehicle = user.getUserTempData().getSpeedometer().getVehicleOld();
                    switch (vehicle.getType()) {
                        case HELICOPTER:
                        case PLANE:
                            this.enter(UserManager.getUser(player));
                            break;
                    }
                } else if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.NEW){
                    VehicleEntity vehicleEntity = user.getUserTempData().getSpeedometer().getVehicleNew();
                    if(vehicleEntity.getType() instanceof AbstractPlane || vehicleEntity.getType() instanceof AbstractHelicopter){
                        this.enter(UserManager.getUser(player));
                    }
                }



            } else if(player.getLocation().getY() < 0 && player.getWorld().getName().equalsIgnoreCase("the_end"))
                this.leave(UserManager.getUser(player));
        }
    }
}
