package net.xayanix.nssv.reallife.job.nspd;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.reallife.jail.JailUtil;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class ThiefCatch {

    private String name;
    @Setter
    private String cop;
    private int progress;
    private long lastTry;

    public ThiefCatch(String name, String cop){
        this.name = name;
        this.cop = cop;
        this.nextTry(null);
    }

    public void nextTry(User cop){
        this.progress = this.progress + (cop == null ? 10 : (Math.min(cop.getCharacter().getJob().getLevel() * 10, 40)));
        this.lastTry = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(20);
    }

    public void catched(){
        if(this.lastTry > System.currentTimeMillis()){
            User user = UserManager.getUser(this.name);
            User cop = UserManager.getUser(this.cop);

            if(user.getCharacter().getWantedlevel() > 0){
                int wl = user.getCharacter().getWantedlevel();

                JailUtil.jail(user.getPlayer(), true, (user.getPlayer().isSneaking() ? 1 : 2));
                cop.sendTitle("", "&bGracz aresztowany.", 0, 20, 0);
                JobManager.broadcast(JobType.PRAWNIK, "&8#&c " + user.getName() + " wpadl do wiezienia i moze potrzebowac pomocy prawnika.");
                JobManager.broadcast(JobType.POLICJANT, "&8#&c Policjant " + cop.getName() + " aresztowal podejrzanego " + user.getName() + ".");
                cop.getCharacter().getJob().setEarned(cop.getCharacter().getJob().getEarned() + WantedLevelManager.reward(wl));
                cop.getCharacter().getJob().addPoint(cop.getPlayer(), false);
            }
        }
    }

}
