package net.xayanix.nssv.reallife.pvp;

import lombok.Getter;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.robber.RobManager;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;

@Getter
public class DamageManager {

    private Player attacker;
    private Player victim;

    private User userattacker;
    private User uservictim;

    public DamageManager(Player attacker, Player victim){
        this.attacker = attacker;
        this.victim = victim;

        this.userattacker = UserManager.getUser(this.attacker);
        this.uservictim = UserManager.getUser(this.victim);
    }

    public boolean isCancelled(){
        if(userattacker.getPlayer().hasPermission("wlasciciel"))
            return false;

        if(userattacker.getUserConfiguration().isPvpState() && uservictim.getUserConfiguration().isPvpState())
            return false;

        if(uservictim.getPlayer().getWorld() == LocationList.SUROWCOWA.getWorld())
            return false;

        if(userattacker.getCharacter().getJail() != null && null != uservictim.getCharacter().getJail())
            return false;

        if(uservictim.getUserTempData().isTraveling())
            return true;

        if(RobManager.wasRobbedBy(userattacker, uservictim)) {
            if(!RobManager.wasRobbedBy(uservictim, userattacker)) {
                uservictim.getUserTempData().getDamageAllows().add(new DamageAllow(userattacker.getName()));
            }
            return false;
        }

        if(userattacker.getCharacter().getJob().getType() == JobType.LOWCA_GLOW && uservictim.getCharacter().getWantedlevel() > 0 && userattacker.getUserTempData().getHeadHunterTarget().equalsIgnoreCase(uservictim.getName()))
            return false;

        if(uservictim.getUserTempData().getHeadHunterTarget().equalsIgnoreCase(userattacker.getName()) && userattacker.getCharacter().getWantedlevel() > 0)
            return false;

        userattacker.sendActionBar("&cNie mozesz zaatakowac tego gracza.");
        return true;
    }



}
