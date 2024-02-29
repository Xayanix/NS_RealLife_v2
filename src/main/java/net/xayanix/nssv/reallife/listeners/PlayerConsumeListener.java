package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.region.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerConsumeListener implements Listener {

    public PlayerConsumeListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEvent(PlayerItemConsumeEvent event) {
        Material material = event.getItem().getType();
        Player player = event.getPlayer();

        if(material == Material.MILK_BUCKET) {
            if(player.hasPotionEffect(PotionEffectType.CONFUSION) || player.hasPotionEffect(PotionEffectType.WEAKNESS)) {
                event.setCancelled(true);
            }

        }
    }
}
