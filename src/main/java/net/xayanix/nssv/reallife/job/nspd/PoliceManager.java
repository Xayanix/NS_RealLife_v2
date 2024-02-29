package net.xayanix.nssv.reallife.job.nspd;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.jail.JailUtil;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.pvp.DamageAllow;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;

public class PoliceManager {

    public static void catchPlayer(User user, User victim){
        if(user.getCharacter().getJail() != null)
            return;
        if(user.getUserTempData().isTraveling())
            return;
        if(victim.getUserTempData().isTraveling())
            return;
        if(victim.getCharacter().getJail() != null)
            return;

        /*
        if(RegionUtil.isPlayerInRegion(victim.getPlayer(), "safezone")) {
            user.sendMessage("&8#&c Gracz znajduje sie w bezpiecznej strefie.");
            user.sendMessage("&8#&c Nie mozesz go lapac w tym miejscu.");
            return;
        }
        */

        int wl = victim.getCharacter().getWantedlevel();

        if(user.getUserTempData().getThiefCatch() == null || !user.getUserTempData().getThiefCatch().getName().equalsIgnoreCase(victim.getName()) || user.getUserTempData().getThiefCatch().getLastTry() < System.currentTimeMillis()){
            user.getUserTempData().setThiefCatch(new ThiefCatch(victim.getName(), user.getName()));
            victim.getUserTempData().setSelfThiefCatch(user.getUserTempData().getThiefCatch());
            victim.sendTitle("&3&lPolicjant &7" + user.getName(), "&7probuje Cie schwytac. Ukucnij, aby sie poddac lub uciekaj.", 20, 60, 20);
            victim.getUserTempData().addFightTag(user.getName());
            user.sendTitle("", "&bPróbujesz schwytac &e" + victim.getName() + "&b...", 0, 20, 0);
            return;
        }

        if(!user.isCooldownFinished("catchthief", 50)){
            return;
        }

        victim.getUserTempData().addFightTag(user.getName());

        ThiefCatch thiefCatch = user.getUserTempData().getThiefCatch();
        thiefCatch.setCop(user.getName());
        thiefCatch.nextTry(user);

        victim.getPlayer().setSprinting(false);

        victim.sendTitle("", "&bPolicjant &e" + user.getName() + "&b probuje Cie schwytac. " + (thiefCatch.getProgress() > 100 ? 100 : thiefCatch.getProgress()) + "%/100%", 0, 20, 0);
        user.sendTitle("", "&bPróbujesz schwytac &e" + victim.getName() + "&b. " + (thiefCatch.getProgress() > 100 ? 100 : thiefCatch.getProgress()) + "%/100%", 0, 20, 0);

        if(thiefCatch.getProgress() >= 100){
            victim.getUserTempData().setSelfThiefCatch(null);
            thiefCatch.catched();
            return;
        }

        if(victim.getPlayer().isSneaking()){
            victim.getUserTempData().setSelfThiefCatch(null);
            thiefCatch.catched();
        }

    }

}
