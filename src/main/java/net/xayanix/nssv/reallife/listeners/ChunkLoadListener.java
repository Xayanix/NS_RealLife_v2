package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadListener implements Listener {

    public ChunkLoadListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(ChunkLoadEvent event) {
        Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), () -> {
            for(Entity entity : event.getChunk().getEntities()){
                if(entity.getType() == EntityType.ARMOR_STAND){
                    ArmorStand armorStand = (ArmorStand) entity;
                    if(armorStand.getCustomName() != null && armorStand.getCustomName().equalsIgnoreCase("STAMINAWORKER"))
                        armorStand.remove();
                }
            }
        }, 1);
    }
}
