package net.xayanix.nssv.reallife.listeners;

import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import me.zombie_striker.qg.guns.QAVShotEvent;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.license.License;
import net.xayanix.nssv.reallife.license.LicenseManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;

public class WeaponPreShootListener implements Listener {

    public WeaponPreShootListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(WeaponPreShootEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(!LicenseManager.hasLicense(user, License.LICENCJA_NA_BRON)) {
            user.sendMessage("&8#&c Nie posiadasz licencji na bron.");
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onEvent(QAVShotEvent qavShotEvent){
        Player player = qavShotEvent.getPlayer();
        User user = UserManager.getUser(player);

        if(!LicenseManager.hasLicense(user, License.LICENCJA_NA_BRON)) {
            user.sendMessage("&8#&c Nie posiadasz licencji na bron.");
            qavShotEvent.setCancel(true);
        }
    }

}
