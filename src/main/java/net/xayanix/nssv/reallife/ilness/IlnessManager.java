package net.xayanix.nssv.reallife.ilness;

import lombok.Getter;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class IlnessManager implements Runnable{

    @Getter
    private static IlnessManager instance;

    public IlnessManager() {
        instance = this;
        Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), this, 20 * TimeUnit.MINUTES.toMillis(5), 20 * TimeUnit.MINUTES.toMillis(5));
    }

    @Override
    public void run() {
        for(User user : UserManager.getUsersOnline()){
            if(RandomUtil.getChance(4)){
                user.sendMessage("&8#&c Ciezko zachorowales i potrzebujesz pomocy medyka. [/wezwij medyk]");
                user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (int) (20 * TimeUnit.MINUTES.toMillis(10)), 1));
            }
        }
    }
}
