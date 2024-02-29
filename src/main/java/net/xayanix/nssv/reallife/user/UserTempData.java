package net.xayanix.nssv.reallife.user;

import eu.haelexuis.utils.xoreboard.XoreBoard;
import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.dhl.PackTarget;
import net.xayanix.nssv.reallife.job.nspd.ThiefCatch;
import net.xayanix.nssv.reallife.job.robber.Rob;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.pvp.DamageAllow;
import net.xayanix.nssv.reallife.speedometer.Speedometer;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class UserTempData {

    private boolean isRobbed;
    private Rob lastRob;
    private List<String> regionsAtPlayerLocation = new CopyOnWriteArrayList<>();
    private Location travelFrom;
    private boolean isTraveling = false;
    private ConcurrentHashMap<String, Offer> offerMap = new ConcurrentHashMap<>();
    private Speedometer speedometer;
    private List<DamageAllow> damageAllows = new CopyOnWriteArrayList<>();
    private String headHunterTarget = "";
    private PackTarget packTarget;
    private boolean serverTextures = false;
    private ThiefCatch thiefCatch;
    private ThiefCatch selfThiefCatch;
    private long thiefProtect = 0;
    private long teleportationGrace = 0;
    private XoreBoard xoreBoard;
    private int xoreBoardKillTaskId;
    private long lastOrePlace;
    private long teleportAllow;
    private Location lastJobLocation;
    private String jobCaptcha;
    private JobType teleportAllowJob;

    public void addOffer(Offer offer){
        this.offerMap.put(offer.getOfferer().toLowerCase(), offer);
    }

    public Offer getOffer(String player){
        return this.offerMap.get(player.toLowerCase());
    }

    public void removeOffer(String player){
        this.offerMap.remove(player.toLowerCase());
    }

    public void addFightTag(String player){
        for(DamageAllow rob : damageAllows) {
            if (rob.getRobber().equalsIgnoreCase(player))
                damageAllows.remove(rob);
        }

        this.getDamageAllows().add(new DamageAllow(player));
    }

    public void removeFightTag(String player){
        for(DamageAllow rob : damageAllows)
            if(rob.getRobber().equalsIgnoreCase(player))
                damageAllows.remove(rob);
    }

    public boolean isAcceptingAllTeleportations(){
        return (this.teleportAllow > System.currentTimeMillis());
    }

    public void setTeleportationsAllowed(){
        this.teleportAllow = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30);
    }

}
