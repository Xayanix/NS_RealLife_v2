package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerDamageListener implements Listener {

    public PlayerDamageListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    public void onEvent(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause damageCause = event.getCause();
        if(event.getEntityType() == EntityType.PLAYER) {

            Player player = (Player) event.getEntity();
            User user = UserManager.getUser(player);

            if(user.getUserTempData().getTeleportationGrace() > System.currentTimeMillis() && player.getWorld() == LocationList.SUROWCOWA.getWorld()){
                event.setCancelled(true);
                return;
            }

            switch (damageCause) {
                case HOT_FLOOR:
                    event.setCancelled(true);
                    break;
                case SUFFOCATION:
                    if(RegionUtil.isPlayerInRegion(player, "kopalnia")) {
                        player.getLocation().getBlock().setType(Material.AIR);
                        Location upper = player.getLocation().add(0.0, 1.0, 0.0);
                        if(RegionUtil.isLocationInRegion(upper, "kopalnia"))
                            upper.getBlock().setType(Material.AIR);
                        event.setCancelled(true);
                    }
                    break;
                case FALL:
                    double chance = player.getFallDistance() * 10 - 10 - user.getCharacter().getStamina().getCurrentStamina()*3;
                    if(RandomUtil.getChance(chance)){
                        ChatUtil.sendMessage(player, "&8#&c Zle upadles i zalamales noge. Potrzebujesz pomocy medyka. Wezwij medyka komenda &7/wezwij medyk&c.");
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 6000, 4));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 6000, 3));
                    }

                    break;
            }
        }
    }

}
