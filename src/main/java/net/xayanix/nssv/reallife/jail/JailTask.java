package net.xayanix.nssv.reallife.jail;

import lombok.AllArgsConstructor;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class JailTask implements Runnable {
    @Override
    public void run() {

        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);
            if(user.getCharacter() == null || user.isNew() || user.getCharacter().getJail() == null) {
                continue;
            }

            if(user.getCharacter().getJail().getSeconds() <= 1) {
                user.getCharacter().setJail(null);
                Bukkit.getScheduler().runTask(RealLife.getInstance(), () -> user.getPlayer().teleport(LocationList.WIEZIENIE_WEJSCIE));
                user.sendTitle("&a&lWolnosc!", "&7Odsiadka zakonczona, naucz sie byc lepszym obywatelem.", 20, 30, 20);
                continue;
            }

            user.getCharacter().getJail().setSeconds(user.getCharacter().getJail().getSeconds() - 1);
            user.sendActionBar("&cPozostaly czas odsiadki:&6 " + StringUtil.getStringTimeFromLong((long) user.getCharacter().getJail().getSeconds()));
        }



    }

    public void start(){
       Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 20, 20);
    }

}
