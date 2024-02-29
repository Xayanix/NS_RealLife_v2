package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.region.RegionUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    public BlockPlaceListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if(block.getType() != Material.COBBLESTONE && !player.hasPermission("admin") && RegionUtil.isLocationInRegion(block.getLocation(), "kopalnia")){
            ChatUtil.sendMessage(player, "&8#&c W kopalnii i w tartaku mozesz stawiac tylko COBBLESTONE.");
            event.setCancelled(true);

            if(!player.hasPermission("vip") && RegionUtil.isLocationInRegion(block.getLocation(), "kopalniavip")){
                event.setCancelled(true);
                return;
            }

        }
    }
}
