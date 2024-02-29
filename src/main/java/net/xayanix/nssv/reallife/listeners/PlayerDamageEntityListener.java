package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.ball.Ball;
import net.xayanix.nssv.reallife.ball.BallManager;
import net.xayanix.nssv.reallife.jail.JailUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.nspd.PoliceManager;
import net.xayanix.nssv.reallife.job.robber.RobInventory;
import net.xayanix.nssv.reallife.job.robber.RobManager;
import net.xayanix.nssv.reallife.pvp.DamageManager;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.concurrent.TimeUnit;

public class PlayerDamageEntityListener implements Listener {

    public PlayerDamageEntityListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEvent(EntityDamageByEntityEvent event) {

        /*if(event.getEntity().getType() == EntityType.ARMOR_STAND && event.getDamager().getType() == EntityType.PLAYER && BallManager.getInstance().getBallList().size() > 0){
            Entity entity = event.getEntity();
            Player attacker = (Player) event.getDamager();
            User user = UserManager.getUser(attacker);

            if(entity.getType() == EntityType.ARMOR_STAND) {
                Ball ball = BallManager.getInstance().getBall(entity);
                if(ball != null){
                    event.setCancelled(true);
                    ball.setLastUse(System.currentTimeMillis());
                    if(user.isCooldownFinished("ballthrow", 70))
                        ball.getArmorStand().setVelocity(event.getDamager().getLocation().getDirection().multiply(attacker.isSprinting() ? 2.5 : 1.5));

                }
            }
        }*/

        if(event.getDamager().getType() == EntityType.PLAYER){
            if(event.getEntity().getType() == EntityType.PLAYER){
                Player robber = (Player) event.getDamager();
                Player victim = (Player) event.getEntity();

                if(robber == victim) {
                    event.setCancelled(true);
                    return;
                }

                User user = UserManager.getUser(robber);
                User uservictim = UserManager.getUser(victim);

                if(!robber.isOnline() || !victim.isOnline())
                    return;

                if(user == null)
                    return;

                if(PlayerUtil.distanceBetweenPlayers(robber, victim) >= 6)
                    return;

                if(robber.isSneaking() && user.getCharacter().getJob().getType() == JobType.ZLODZIEJ) {

                    if(RegionUtil.isPlayerInRegion(victim, "safezone")) {
                        user.sendMessage("&8#&c Gracz znajduje sie w bezpiecznej strefie.");
                        user.sendMessage("&8#&c Nie mozesz go okradac w tym miejscu.");
                        return;
                    }

                    if(uservictim.getUserTempData().getThiefProtect() > System.currentTimeMillis()){
                        user.sendMessage("&8#&c Gracz posiada ochrone przed zlodziejami do:&e " + TimeUtil.formatDate(uservictim.getUserTempData().getThiefProtect()));
                        return;
                    }

                    if(user.getCharacter().getJail() != null){
                        user.sendMessage("&8#&c Nie mozesz okradac siedzac w pace.");
                        return;
                    }

                    if(user.getUserTempData().isTraveling()){
                        return;
                    }

                    if(!user.isCooldownFinished("rob", TimeUnit.SECONDS.toMillis(30))) {
                        user.sendMessage("&8#&7 Mozesz okradac raz na 30 sekund.");
                        return;
                    }

                    if(!user.isCooldownFinished("rob." + victim.getName(), TimeUnit.MINUTES.toMillis(10))){
                        user.sendMessage("&8#&c Tego samego gracza mozesz okradac raz na 10 minut.");
                        return;
                    }

                    if(uservictim.getUserTempData().isTraveling())
                        return;
                    if(uservictim.getCharacter().getJail() != null)
                        return;

                    if(robber.hasPermission("fastrob")) {
                        RobManager.rob(robber, victim);
                        return;
                    }

                    RobInventory.display(robber, victim);
                }
                else if(user.getCharacter().getJob().getType() == JobType.POLICJANT){
                    User vuser = UserManager.getUser(victim);
                    if(vuser.getCharacter().getWantedlevel() > 0) {
                        PoliceManager.catchPlayer(user, vuser);
                    }
                }





            }


        }

    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEventHigh(EntityDamageByEntityEvent event) {

        Player attacker = null;
        Player victim = null;

        if(event.getEntity().getType() != EntityType.PLAYER)
            return;

        victim = (Player) event.getEntity();
        if(!victim.isOnline())
            return;

        if(event.getDamager() instanceof Player)
            attacker = (Player) event.getDamager();
        else if(event.getDamager() instanceof Projectile){
            Projectile projectile = (Projectile) event.getDamager();
            if(projectile.getShooter() instanceof Player)
                attacker = (Player) projectile.getShooter();
        }

        if(attacker != null) {
            boolean cancel = new DamageManager(attacker, victim).isCancelled();
            User user = UserManager.getUser(attacker);
            User userv = UserManager.getUser(victim);

            event.setDamage(event.getFinalDamage() * 2.2);

            if(!cancel && user.getCharacter().getJob().getType() != JobType.LOWCA_GLOW && user.isCooldownFinished("pobicie." + victim.getName(), TimeUnit.MINUTES.toMillis(5))){
                switch (userv.getCharacter().getJob().getType()){
                    case POLICJANT:
                        WantedLevelManager.addWantedLevel(user, "Pobicie policjanta" + victim.getName(), 3);
                        break;
                    default:
                        WantedLevelManager.addWantedLevel(user, "Pobicie " + victim.getName(), 1);
                        break;
                }
            }


            event.setCancelled(cancel);
        }

    }

}
