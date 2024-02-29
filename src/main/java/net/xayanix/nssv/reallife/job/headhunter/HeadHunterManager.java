package net.xayanix.nssv.reallife.job.headhunter;

import lombok.Getter;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.scoreboard.ScoreboardManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.LocationUtil;
import org.bukkit.Bukkit;

public class HeadHunterManager implements Runnable {

    @Getter
    private static HeadHunterManager instance;

    public HeadHunterManager() {
        instance = this;
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 40, 40);
    }

    @Override
    public void run() {
        for(User user : UserManager.getUsersOnline()) {
            if(user.getCharacter().getJob().getType() == JobType.LOWCA_GLOW) {
                if(user.getUserTempData().getHeadHunterTarget() != null){
                    User target = UserManager.getUser(user.getUserTempData().getHeadHunterTarget());
                    if(target == null || !target.isOnline()){
                        continue;
                    }

                    if(target.getPlayer().getWorld() != target.getPlayer().getWorld())
                        continue;

                    user.getPlayer().setCompassTarget(target.getPlayer().getLocation());
                    ScoreboardManager.getInstance().display(user, 3, "&c&lNadajnik GPS",
                            "&7Twój cel:",
                            "&f" + user.getUserTempData().getHeadHunterTarget(),
                            "&a",
                            "&7Lokalizacja:",
                            "&f" + LocationUtil.nice(target.getPlayer().getLocation()),
                            "&b",
                            "&7Poziom poszukiwania:",
                            "&f" + target.getCharacter().getWantedlevel() + " WL");

                }
            }
        }
    }
}
