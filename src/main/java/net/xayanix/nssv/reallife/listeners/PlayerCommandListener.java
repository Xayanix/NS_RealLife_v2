package net.xayanix.nssv.reallife.listeners;

import com.intellectualcrafters.plot.commands.Merge;
import com.intellectualcrafters.plot.object.Plot;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {

    public PlayerCommandListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage();

        if(player.hasPermission("admin"))
            return;

        User user = UserManager.getUser(player);
        if(user.getCharacter().getJail() != null && !player.hasPermission("admin")){
            if(cmd.toLowerCase().startsWith("/kaucja") || cmd.toLowerCase().startsWith("/akceptuj") || cmd.toLowerCase().startsWith("/wezwij"))
                return;
            ChatUtil.sendMessage(player, "&8#&c Odsiadujesz kare, nie mozesz uzywac komend.");
            event.setCancelled(true);
        }

        if(cmd.toLowerCase().startsWith("/spawner") || cmd.toLowerCase().startsWith("/espawner") || cmd.toLowerCase().startsWith("/mobspawner") || cmd.toLowerCase().startsWith("/emobspawner")){
            Plot plot = PlotManager.getApi().getPlot(player.getLocation());
            if(plot == null){
                ChatUtil.sendMessage(player, "&8#&c Ta komende mozesz uzywac tylko na dzialce, ktorej jestes wlascicielem.");
                event.setCancelled(true);
                return;
            }

            if(!plot.isOwner(player.getUniqueId())){
                ChatUtil.sendMessage(player, "&8#&c Ta komende mozesz uzywac tylko na dzialce, ktorej jestes wlascicielem.");
                event.setCancelled(true);
                return;
            }

        }


    }
}
