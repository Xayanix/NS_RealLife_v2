package net.xayanix.nssv.reallife.trash;

import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TrashManager implements Runnable {

    @Getter
    private static TrashManager instance;

    @Getter
    private List<Trash> trashLocation;

    public TrashManager() {
        this.trashLocation = new CopyOnWriteArrayList<>();
        instance = this;
        Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), this, 10, 10);
    }

    public void onEvent(PlayerDropItemEvent playerDropItemEvent){

        if(this.trashLocation.size() >= 300)
            return;

        Player player = playerDropItemEvent.getPlayer();
        User user = UserManager.getUser(player);

        if(user.getCharacter().getJob().getType() == JobType.SPRZATACZ)
            return;

        Location location = playerDropItemEvent.getItemDrop().getLocation();
        if(RandomUtil.getChance(25)){
            this.trashLocation.add(new Trash(location.add(0.0, -0.9, 0.0)));
        }
    }

    public void clean(User user, Location location){
        for(Trash trash : trashLocation){
            if(trash.getLocation().getWorld() == location.getWorld()) {
                double d = trash.getLocation().distance(location);
                if(d <= 3){
                    user.sendMessage("&8#&b Posprzatales zanieczyszczenie w poblizu.");
                    user.getCharacter().getJob().addPoint(user.getPlayer(), true);
                    this.trashLocation.remove(trash);
                    return;
                }
            }
        }
    }

    @Override
    public void run() {
        for(Trash location : trashLocation){
            if(location.getExpire() < System.currentTimeMillis()){
                this.trashLocation.remove(location);
                continue;
            }

            if(location.getLocation().getChunk().isLoaded()){
                for(Player entity : Bukkit.getOnlinePlayers()) {
                    User user = UserManager.getUser(entity);
                    if(entity.getWorld() == location.getLocation().getWorld())
                        entity.spigot().playEffect(location.getLocation(), Effect.SMALL_SMOKE, 0, 0, 0, 0, 0, 1, 0, 64);
                }

            }
        }
    }
}
