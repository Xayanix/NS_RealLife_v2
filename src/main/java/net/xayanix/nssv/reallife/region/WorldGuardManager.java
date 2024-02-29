package net.xayanix.nssv.reallife.region;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;

public class WorldGuardManager {

    @Getter
    private static WorldGuardManager instance;

    @Getter
    private WorldGuardPlugin wg;

    public WorldGuardManager(){
        instance = this;
        this.wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

}
