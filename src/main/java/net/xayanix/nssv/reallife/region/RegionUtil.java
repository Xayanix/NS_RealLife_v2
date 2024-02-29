package net.xayanix.nssv.reallife.region;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;

public class RegionUtil {

    public static boolean isPlayerInRegion(Player player, String regions){
        return isLocationInRegion(player.getLocation(), regions);
    }

    public static boolean isLocationInRegion(Location location, String regions){
        ApplicableRegionSet ar = WorldGuardManager.getInstance().getWg().getRegionManager(location.getWorld()).getApplicableRegions(location);
        for(ProtectedRegion pr : ar.getRegions())
            if (pr.getId().startsWith(regions))
                return true;

        return false;
    }

    public static boolean isInAnyRegion(Location location){
        return WorldGuardManager.getInstance().getWg().getRegionManager(location.getWorld()).getApplicableRegions(location).getRegions().size() > 0;
    }

    public static Iterator<ProtectedRegion> getRegionsAtPlayerLoc(Player player){
        ApplicableRegionSet ar = WorldGuardManager.getInstance().getWg().getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
        Iterator<ProtectedRegion> prs = ar.iterator();
        return prs;
    }

    public static void entered(Player player, String region){
        if(region.startsWith("przystanek_"))
            ChatUtil.sendMessage(player, "&8#&a Znajdujesz sie na przystanku autobusowym.");
    }

    public static void setPvpFlags(){
        Collection<ProtectedRegion> regions = WorldGuardManager.getInstance().getWg().getRegionManager(Bukkit.getWorld("PlotWorld")).getRegions().values();
        for(ProtectedRegion region : regions){
            region.setFlag(new StateFlag("pvp", true), StateFlag.State.ALLOW);
            region.setFlag(new StateFlag("use", true), StateFlag.State.ALLOW);
            region.setFlag(new StateFlag("ride", true), StateFlag.State.ALLOW);
            region.setFlag(new StateFlag("mob-damage", true), StateFlag.State.ALLOW);
            region.setFlag(new StateFlag("damage-animals", true), StateFlag.State.ALLOW);
            Logger.info("Ustawiono flage PVP TRUE dla " + region.getId());
        }
    }

}
