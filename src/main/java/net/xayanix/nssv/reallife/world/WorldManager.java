package net.xayanix.nssv.reallife.world;

import lombok.Getter;
import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Calendar;

@Getter
public class WorldManager implements Runnable {

    private static WorldManager instance;

    public WorldManager(){
        instance = this;
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 20, 20);
    }

    public void setGameTime(){
        Calendar calendar = Calendar.getInstance();
        long ticks = getTimeInTicks(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        for(World world : Bukkit.getWorlds())
            world.setTime(ticks);

    }

    public long getTimeInTicks(int hours, int minutes){
        long ret = 18000L;
        ret += hours * 1000;
        ret += (long)(minutes / 60.0 * 1000.0);
        ret %= 24000L;
        return ret;
    }

    public void setGameRules(){
        for(World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("announceAdvancements", "false");
            world.setGameRuleValue("doDaylightCycle", "false");
        }
    }

    @Override
    public void run() {
        this.setGameTime();
    }
}
