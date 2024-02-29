package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityDeathListener implements Listener {

    public EntityDeathListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event){
        if(event.getEntityType() == EntityType.WITHER)
            event.setCancelled(true);
    }

    @EventHandler
    public void onEvent(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if(entity.getLastDamageCause() != null && entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) entity.getLastDamageCause();
            if(e.getDamager().getType() == EntityType.PLAYER && e.getEntityType() != EntityType.ARMOR_STAND){
                Player attacker = (Player) e.getDamager();
                User user = UserManager.getUser(attacker);

                if(user.getUserTempData().getLastJobLocation() != null && user.getUserTempData().getLastJobLocation().getWorld() == attacker.getWorld() && user.getUserTempData().getLastJobLocation().distance(attacker.getLocation()) < 8){
                    return;
                }

                if(user.isCooldownFinished("klusownik", 8000)) {
                    user.getUserTempData().setLastJobLocation(attacker.getLocation());
                    if(user.getCharacter().getCompany() != null)
                        user.getCharacter().getCompany().setEarned(user.getCharacter().getCompany().getEarned() + JobType.KLUSOWNIK.getReward());

                    if(user.getCharacter().getJob().getType() == JobType.KLUSOWNIK)
                        user.getCharacter().getJob().addPoint(attacker, true);
                }

            }
        }
    }
}
