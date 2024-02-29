package net.xayanix.nssv.reallife.listeners;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.xayanix.nssv.reallife.region.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class PistonListener implements Listener {

    private Cache<Block, Long> running;

    public PistonListener(JavaPlugin instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
        this.running = CacheBuilder.newBuilder()
                .maximumSize(3000)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .build();
    }

    @EventHandler
    public void onEvent(BlockPistonExtendEvent event) {
        if(this.running.getIfPresent(event.getBlock()) != null) {
            event.setCancelled(true);
            return;
        }

        this.running.put(event.getBlock(), System.currentTimeMillis());

        if(RegionUtil.isLocationInRegion(event.getBlock().getLocation(), "lokal"))
            event.setCancelled(true);


    }

    /*@EventHandler
    public void onEvent(BlockPistonRetractEvent event) {

    }*/

}
