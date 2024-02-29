package net.xayanix.nssv.reallife.plotdev;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.plotsquared.bukkit.util.BukkitUtil;
import lombok.Getter;
import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.entity.Player;

public class PlotManager {

    @Getter
    private static PlotAPI api = new PlotAPI(RealLife.getInstance());

    public static void changeOwner(Player player){
        for(Plot plot : api.getAllPlots()){
            if(!plot.getOwners().contains(player)) {
                plot.setOwner(player.getUniqueId());
                player.sendMessage("Zostales wlascicielem jakiejs dzialki.");
            }
        }
    }

    public static void removePlots(Player player){
        for(Plot plot : api.getAllPlots()){
            if(!plot.isOwner(player.getUniqueId())){
                plot.setOwner(null);
            }
        }
    }

    public static void checkAndClaim(Player player){
        Plot plot = api.getPlot(player.getLocation());
        PlotPlayer plotPlayer = BukkitUtil.getPlayer(player);
        if(plot != null){
            if(plot.claim(plotPlayer, false, null)){
                player.sendMessage("Zajales dzialke na ktorej stoisz.");
            }
        }
    }

}
