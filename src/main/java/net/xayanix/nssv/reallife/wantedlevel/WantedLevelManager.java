package net.xayanix.nssv.reallife.wantedlevel;

import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;

import java.util.ArrayList;
import java.util.List;

public class WantedLevelManager {

    public static void addWantedLevel(User user, String reason, int points){
        if(user.getCharacter().getJob().getType() == JobType.POLICJANT)
            return;
        if(user.getCharacter().getJail() != null)
            return;

        user.getCharacter().setWantedlevel(user.getCharacter().getWantedlevel() + points);
        if(user.getCharacter().getWantedlevel() > 10)
            user.getCharacter().setWantedlevel(10);

        user.sendMessage("&8#&c Popelniles przestepstwo. ( " + reason + " ). Zglosil: Monitoring NSPD");
        user.sendMessage("&8#&e Posiadany Wanted Level: " + user.getCharacter().getWantedlevel());

        user.sendTitle("&e&l" + user.getCharacter().getWantedlevel() + " WANTED LEVEL", "&c" + reason + ", lepiej sie pilnuj!", 10, 20, 7);

        JobManager.sendActionBar(JobType.POLICJANT, "&c[List Gonczy: " + reason + "] &cGracz: &e" + user.getName() + "&c, WL: &e" + user.getCharacter().getWantedlevel() + "/10&c, nagroda za schwytanie:&e " + reward(user.getCharacter().getWantedlevel()) + " zlotych");
        JobManager.sendActionBar(JobType.LOWCA_GLOW, "&c[List Gonczy: " + reason + "] &cGracz: &e" + user.getName() + "&c, WL: &e" + user.getCharacter().getWantedlevel() + "/10&c, nagroda za schwytanie:&e " + JobType.LOWCA_GLOW.getReward() + " zlotych");

    }

    public static int reward(int wantedlevel){
        return JobType.POLICJANT.getReward() * wantedlevel;
    }

    public static List<User> getWantedUsers(){
        List<User> users = new ArrayList<>();
        for(User user : UserManager.getUsersOnline())
            if(user.getCharacter().getWantedlevel() > 0)
                users.add(user);

        return users;
    }

}
