package net.xayanix.nssv.reallife.job.medic;

import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.compass.CompassUtil;
import net.xayanix.nssv.reallife.job.firefighter.Fire;
import net.xayanix.nssv.reallife.job.firefighter.FireLocations;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.SpaceUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class MedicManager implements Runnable {

    @Getter
    private static MedicManager instance;

    public MedicManager(){
        instance = this;
        Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), this, 20 * 90, 20 * 90);
    }

    @Override
    public void run(){
        int medics = JobManager.getPlayersArmountWithJob(JobType.RATOWNIK_MEDYCZNY);
        if(medics == 0)
            return;

        for(User user : UserManager.getUsersOnline()){
            if(RandomUtil.getChance(2) && user.isCooldownFinished("choroba", TimeUnit.MINUTES.toMillis(10))) {
                ChatUtil.sendMessage(user.getPlayer(), "&8#&c Ciezko zachorowales i potrzebujesz pomocy medyka. &7[/wezwij medyk]");
                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 6000, 1));
                return;
            }
        }

    }

}
