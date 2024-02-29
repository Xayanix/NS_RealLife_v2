package net.xayanix.nssv.reallife.warp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

@Getter
public class Travel implements Runnable{

    private User user;
    private Player player;
    private Location target;
    private BukkitTask task;
    private int time;

    public Travel(User user, Location target, int time){
        this.user = user;
        this.player = this.user.getPlayer();
        this.target = target;
        this.time = time;

        if(user.getUserTempData().isTraveling())
            return;
        if(user.getCharacter().getJail() != null)
            return;

        if(this.player.hasPermission("admin")) {
            this.player.teleport(target);
            ChatUtil.sendMessage(this.player, "&8#&a Jestes adminem, wiec nie musisz czekac w busie.");
            return;
        }

        if(this.time == 0){
            this.player.teleport(target);
            return;
        }

        player.teleport(LocationList.BUS);
        user.getUserTempData().setTravelFrom(player.getLocation());
        user.getUserTempData().setTraveling(true);

        //player.playSound(player.getLocation(), Sound.RECORD_CHIRP, 1, 1);

        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 20, 20);
    }

    @Override
    public void run() {
        if(!user.isOnline()){
            this.task.cancel();
            return;
        }

        user.sendTitle("", "&cPozostaly czas podrozy:&7 " + this.time + ",&k00&7 sekund", 0, 25, 20);
        if(this.time <= 1){
            user.getUserTempData().setTraveling(false);
            user.getUserTempData().setTravelFrom(null);
            player.playEffect(player.getLocation(), Effect.RECORD_PLAY, 0);
            Bukkit.getScheduler().runTask(RealLife.getInstance(), () -> player.teleport(this.target));
            this.task.cancel();
            return;
        }

        this.time--;

    }

}
