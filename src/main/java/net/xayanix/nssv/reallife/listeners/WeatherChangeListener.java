package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.LocationList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.concurrent.TimeUnit;

public class WeatherChangeListener implements Listener {

    public WeatherChangeListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    public void onEvent(WeatherChangeEvent event) {
        if(event.toWeatherState() && event.getWorld() != LocationList.SPAWN.getWorld()){
            event.setCancelled(true);
            return;
        }

        if(event.toWeatherState() && RandomUtil.getChance(25)) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> {
                event.getWorld().setWeatherDuration(20 * 60 * 3);
                event.getWorld().setThundering(true);
                event.getWorld().setThunderDuration(20 * 60 * 3);
                ChatUtil.broadcast("&8#&6 W miescie rozpoczela sie burza, lepiej schowaj sie pod dach...");
            }, 1);
        }
    }
}
