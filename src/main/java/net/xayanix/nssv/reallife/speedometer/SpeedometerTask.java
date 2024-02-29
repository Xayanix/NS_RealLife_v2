package net.xayanix.nssv.reallife.speedometer;

import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpeedometerTask implements Runnable {

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);
            if(user.getUserTempData().getSpeedometer() != null){
                user.getUserTempData().getSpeedometer().calculateSpeed();
                user.getUserTempData().getSpeedometer().setLines();
            }
        }
    }

    public void start(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 5, 5);
    }

}
