package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class PrepareAnvilListener implements Listener {

    public PrepareAnvilListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PrepareAnvilEvent event) {
        if(event.getInventory().getItem(0) != null && event.getResult() != null){
            if(event.getResult().getDurability() != event.getInventory().getItem(0).getDurability()) {
                User user = UserManager.getUser((Player) event.getView().getPlayer());

                if(user != null && user.getCharacter().getJob().getType() != JobType.KOWAL){
                    int kowal = JobManager.getPlayersArmountWithJob(JobType.KOWAL);

                    if(kowal == 0){
                        if(user.isCooldownFinished("anvil", 200)){
                            user.sendMessage("&8#&c Nie ma zadnego kowala na serwerze, wiec mozesz uzyc kowadlo.");
                            Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> user.getPlayer().updateInventory(), 1);
                        }

                        return;
                    }

                    event.setResult(null);

                    if(user.isCooldownFinished("anvil", 200)){
                        user.sendMessage("&8#&c Nie mozna naprawiac przedmiotow w kowadlach, skorzystaj z pomocy kowala.");
                        Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> user.getPlayer().updateInventory(), 1);
                    }
                }




            }
        }
}

}
