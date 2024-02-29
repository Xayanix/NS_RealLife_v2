package net.xayanix.nssv.reallife.glow;

import eu.haelexuis.utils.xoreboard.XoreBoardUtil;
import lombok.Getter;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.inventivetalent.glow.GlowAPI;

public class GlowManager implements Listener {

    @Getter
    private static GlowManager instance;

    public GlowManager() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);


        /*
        Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), () -> {
            GlowAPI.Color glow = user.getUserConfiguration().getGlowColor(user);
            if(glow != null) {
                player.setGlowing(true);
                Team team = XoreBoardUtil.getTeam(player.getName().toLowerCase());
                team.setColor(ChatColor.BLUE);
                team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

                player.sendMessage("e");
            } else player.setGlowing(false);


        }, 20);
        */

    }

}
