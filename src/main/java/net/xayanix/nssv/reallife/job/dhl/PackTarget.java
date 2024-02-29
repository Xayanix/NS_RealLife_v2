package net.xayanix.nssv.reallife.job.dhl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
public enum PackTarget {

    TARGET_1("zoologicznego", "kurierzoologiczny", new Location(Bukkit.getWorld("PlotWorld"), -139, 65, 79)),
    TARGET_2("ogrodniczego", "kurierogrodniczy", new Location(Bukkit.getWorld("PlotWorld"), -194, 65, 14)),
    TARGET_3("KFC", "kurierkfc", new Location(Bukkit.getWorld("PlotWorld"), -207, 65, -100)),
    TARGET_5("z bronia", "kuriergunshop", new Location(Bukkit.getWorld("PlotWorld"), -205, 65, 71)),
    TARGET_4("TESCO", "kuriertesco", new Location(Bukkit.getWorld("PlotWorld"), -503, 66, 40));

    private String desc;
    private String region;
    private Location location;

}
