package net.xayanix.nssv.reallife.world;

import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WeatherManager implements Runnable {

    @Getter
    private static WeatherManager instance;

    public WeatherManager() {
        instance = this;
        //Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), this, 20 * 15, 20 * 15);
    }

    @Override
    public void run() {
        if(!LocationList.SPAWN.getWorld().isThundering())
            return;
        for(Player player : LocationList.SPAWN.getWorld().getPlayers()){
            if(player.getGameMode() == GameMode.CREATIVE)
                continue;
            User user = UserManager.getUser(player);
            if(user.getUserTempData().getSpeedometer() != null)
                continue;

            if(RandomUtil.getChance(10)){
                if(player.getWorld().getHighestBlockAt(player.getLocation()).getY() < player.getLocation().add(0.0, 1.0, 0.0).getY()){
                    player.getWorld().strikeLightningEffect(player.getLocation());
                    player.damage(999);
                    return;

                }


            }

        }
    }
}
