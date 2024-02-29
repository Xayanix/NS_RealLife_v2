package net.xayanix.nssv.reallife.listeners;

import com.earth2me.essentials.IEssentials;
import eu.haelexuis.utils.xoreboard.XoreBoard;
import eu.haelexuis.utils.xoreboard.XoreBoardUtil;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.character.Character;
import net.xayanix.nssv.reallife.hide.HideManager;
import net.xayanix.nssv.reallife.job.Job;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.misc.ItemContainer;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.resourcepack.ResourceManager;
import net.xayanix.nssv.reallife.scoreboard.ScoreboardManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import us.myles.ViaVersion.api.Via;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player) == null ? new User(player.getName()) : UserManager.getUser(player);;
        user.setPlayer(player);

        XoreBoard xoreBoard = XoreBoardUtil.getNextXoreBoard();
        xoreBoard.addPlayer(player);
        user.getUserTempData().setXoreBoard(xoreBoard);

        if(user.isNew()){
            user.setCharacter(new Character(user, "BRAK", "BRAK", 25, new Job(JobType.BEZROBOTNY, 1, 0)));
            user.setNew(false);
            user.insert();
            user.getPlayer().getInventory().addItem(ItemContainer.TUTORIAL_BOOK);

            IEssentials ess = (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");
            com.earth2me.essentials.User user1 = ess.getUser(user.getName());
            if(user1 != null)
                user1.setHome("home", LocationList.SPAWN);

            player.teleport(LocationList.SPAWN);
        }

        if(user.getCharacter().getCompany() != null)
            user.getCharacter().getCompany().setLastOnline(System.currentTimeMillis());

        if(user.getCharacter().getFamily() != null)
            user.getCharacter().getFamily().setLastOnline(System.currentTimeMillis());

        user.getUserConfiguration().init();
        user.setLastActiveTime(System.currentTimeMillis());
        user.setVip(player.hasPermission("vip"));
        user.getCharacter().getStamina().setHealth(user);
        Bukkit.getScheduler().runTaskAsynchronously(RealLife.getInstance(), () ->  {ScoreboardManager.getInstance().updatePrefix(user); ScoreboardManager.getInstance().updateBelowName(user); });
        Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> ResourceManager.getInstance().set(player), 60);

        if(Via.getAPI().getPlayerVersion(player) < 107){
            Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> {
                ChatUtil.sendMessage(player, "&8#&c Grajac na starej wersji Minecraft wiele tracisz!");
                ChatUtil.sendMessage(player, "&8#&c Aby cieszyc sie pelnia rozgrywki zalecamy gre na &6Minecraft 1.12.2" + "&c z wlaczonymi serwerowymi teksturami.");
            }, 20);
        }


    }
}
