package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.license.License;
import net.xayanix.nssv.reallife.license.LicenseManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishListener implements Listener {

    public PlayerFishListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerFishEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(!LicenseManager.hasLicense(user, License.LICENCJA_RYBAKA)){
            ChatUtil.sendMessage(player, "&8#&c Nie posiadasz licencji rybaka, wyrob ja w urzedzie.");
            event.setCancelled(true);
            return;
        }

        if(user.getCharacter().getJob().getType() != JobType.RYBAK)
            return;

        switch (event.getState()){
            case CAUGHT_FISH:
                user.getCharacter().getJob().addPoint(player, true);
                if(user.getCharacter().getCompany() != null)
                    user.getCharacter().getCompany().setEarned(user.getCharacter().getCompany().getEarned() + JobType.RYBAK.getReward());

                user.sendMessage("&8#&b Udalo Ci sie zlapac rybe!");
                return;
            case FAILED_ATTEMPT:
                user.sendMessage("&8#&c Nie udalo Ci sie nic zlapac, sprobuj ponownie.");
                return;
        }


    }
}
