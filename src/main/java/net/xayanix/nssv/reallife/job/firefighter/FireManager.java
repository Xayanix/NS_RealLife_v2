package net.xayanix.nssv.reallife.job.firefighter;

import lombok.Getter;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.compass.CompassUtil;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.SpaceUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class FireManager implements Runnable {

    @Getter
    private static FireManager instance;

    @Getter
    private List<Fire> fireLocations;

    public FireManager(){
        instance = this;
        this.fireLocations = new CopyOnWriteArrayList<>();

        Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), this, 15, 15);
        Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), () -> {
            if(RandomUtil.getChance(45))
                this.fire();
        }, 20 * TimeUnit.MINUTES.toSeconds(2), 20 * TimeUnit.MINUTES.toSeconds(2));
    }

    public FireLocations getRandomFireLocation(){
        return FireLocations.values()[RandomUtil.getRandom().nextInt(FireLocations.values().length)];
    }

    public Location fire(){
        FireLocations location = getRandomFireLocation();
        this.fireLocations.add(new Fire(location.getLocation(), 0));

        for(User user : UserManager.getUsersOnline())
            if(user.getCharacter().getJob().getType() == JobType.STRAZAK)
                CompassUtil.setTarget(user.getPlayer(), location.getLocation());

        JobManager.broadcast(JobType.STRAZAK, "&8#&c W poblizu " + location.getName() + " wybuchl pozar na lokalizacji X: " + location.getLocation().getX() + " Y: " + location.getLocation().getY() + " Z: " + location.getLocation().getZ() + ", jedz go ugasic aby zgarnac &e" + JobType.STRAZAK.getReward() + " zlotych&c.");
        return location.getLocation();
    }

    public Fire getFire(Location location){
        for(Fire fire : this.fireLocations){
            if(fire.getLocation().getWorld() != location.getWorld())
                continue;
            if(fire.getLocation().distance(location) < 5)
                return fire;
        }

        return null;
    }

    public void extenguish(User user, Location location){
        Fire fire = getFire(location);
        if(fire == null)
            return;

        if(fire.getExtenguish() >= 100){
            user.getCharacter().getJob().addPoint(user.getPlayer(), true);
            user.sendMessage("&8#&b Gratulacje, zgasiles pozar.");
            this.fireLocations.remove(fire);
            return;
        }

        fire.extenguish();
        user.sendTitle("&aGasisz pozar...", "&7" + fire.getExtenguish() + "/100%", 0, 20, 0);


    }

    @Override
    public void run(){
        for(Fire fire : fireLocations){
            if(fire.getExpire() < System.currentTimeMillis()){
                fireLocations.remove(fire);
                continue;
            }

            for(Player player : Bukkit.getOnlinePlayers()) {
                for(Location location : SpaceUtil.sphere(fire.getLocation(), 3, 3, false, true, 0))
                    player.spigot().playEffect(location, Effect.MOBSPAWNER_FLAMES, 0, 0, 0, 0, 0, 1, 2, 64);
                if(RandomUtil.getChance(75) && SpaceUtil.distanceBetweenLocations(player.getLocation(), fire.getLocation()) <= 4){
                    User user = UserManager.getUser(player);
                    if(user.getCharacter().getJob().getType() != JobType.STRAZAK) {
                        player.damage(2);
                        player.setFireTicks(60);
                    }
                }
            }
        }

    }

}
