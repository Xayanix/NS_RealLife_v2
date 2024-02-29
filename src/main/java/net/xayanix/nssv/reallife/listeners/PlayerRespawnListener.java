package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.jail.JailUtil;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    public PlayerRespawnListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(user.isNew()){
            event.setRespawnLocation(LocationList.NEWBIE_SPAWN);
            return;
        }

        if(user.getCharacter().getJail() != null){
            event.setRespawnLocation(JailUtil.getRandomJailLocation());
            JailUtil.equipArmor(player);
            user.sendMessage("&8#&c Odsiadujesz kare, wracasz do celi!");
            return;
        }

        user.getCharacter().getStamina().reduce(70);
        user.getCharacter().getStamina().setHealth(user);

        event.setRespawnLocation(LocationList.RESPAWN);
        Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> {
            JobManager.sendActionBar(JobType.RATOWNIK_MEDYCZNY, "&6" + user.getName() + " zostal ciezko ranny i przewieziony do szpitala.");

            user.sendMessage("&8#&e Wezwij medyka, aby Cie uzdrowil. [/wezwij medyk]");
            user.sendMessage("&8#&c Poziom Twojej sily spadl o 70%.");
            player.setHealth(1.0);
            player.setFoodLevel(1);
        }, 10);

    }
}
