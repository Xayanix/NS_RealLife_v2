package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.ItemUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {

    public BlockBreakListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEventLow(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(!player.hasPermission("vip") && RegionUtil.isLocationInRegion(event.getBlock().getLocation(), "kopalniavip") && user.getCharacter().getVipMine() < System.currentTimeMillis()){
            ChatUtil.sendMessage(player, "&8#&c Tylko VIP moze korzystac z tej kopalni.");
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEventHigh(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(player.getGameMode() == GameMode.CREATIVE)
            return;
        if(event.getBlock().getType() == Material.BEDROCK) {
            event.setCancelled(true);
            return;
        }

        if(event.getBlock().getType() == Material.MOB_SPAWNER && player.getWorld().getName().equalsIgnoreCase("surowcowa")){
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            event.setDropItems(false);
            return;
        }

        boolean silktouch = (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH));
        if(player.getInventory().getItemInMainHand() != null)
            ItemUtil.recalculateDurability(player, player.getInventory().getItemInMainHand());

        Material material = event.getBlock().getType();
        if(material == Material.STONE)
            player.giveExp(1);
        else if(material.toString().contains("ORE"))
            player.giveExp(3);
        else if(material.toString().contains("SHULKER"))
            return;
        else if(material == Material.QUARTZ_BLOCK) {
            event.getBlock().setType(Material.AIR);
            return;
        }


        if(silktouch) {
            player.getInventory().addItem(new ItemStack(material, 1, (short) event.getBlock().getData()));
        }
        else for(ItemStack drop : event.getBlock().getDrops())
            player.getInventory().addItem(drop);

        event.getBlock().setType(Material.AIR);


    }
}
