package net.xayanix.nssv.reallife.job.hycel;

import net.xayanix.nssv.reallife.user.User;
import org.bukkit.entity.Entity;

import java.util.concurrent.TimeUnit;

public class HycelManager {

    public static void takePet(User user, Entity entity){
        if(TimeUnit.SECONDS.toMinutes(entity.getTicksLived() / 20) < 10){
            user.sendMessage("&8#&c Ten pies żyje za krótko, aby go łapać. (min. 10 min)");
            return;
        }

        entity.remove();
        user.getCharacter().getJob().addPoint(user.getPlayer(), true);
        user.sendMessage("&8#&b Gratulacje, zalapales bezpanskiego psa.");
    }

}
