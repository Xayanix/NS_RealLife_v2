package net.xayanix.nssv.reallife.job.firefighter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@AllArgsConstructor
@Getter
public enum FireLocations {

    LOC_1("Tesco", new Location(Bukkit.getWorld("PlotWorld"), -525.532, 66, 37.486)),
    LOC_2("schroniska dla psow", new Location(Bukkit.getWorld("PlotWorld"), -420.300, 65,160.700)),
    LOC_3("parku", new Location(Bukkit.getWorld("PlotWorld"), -288.180, 65,100.568)),
    LOC_4("sklepu z roslinami", new Location(Bukkit.getWorld("PlotWorld"), -211.300, 72, 29.522)),
    LOC_5("cukierni", new Location(Bukkit.getWorld("PlotWorld"), -147.673, 75 ,153.296)),
    LOC_6("apteki", new Location(Bukkit.getWorld("PlotWorld"), -146.345, 61,181.300)),
    LOC_7("szpitala", new Location(Bukkit.getWorld("PlotWorld"), -145.300, 64, 231.662)),
    LOC_8("cmentarza", new Location(Bukkit.getWorld("PlotWorld"), -231.300, 65 , 217.537)),
    LOC_9("KFC", new Location(Bukkit.getWorld("PlotWorld"), -186.300, 65 , -101.700)),
    LOC_10("stacji benzynowej", new Location(Bukkit.getWorld("PlotWorld"), -150.674, 65 , -106.700)),
    LOC_11("parku", new Location(Bukkit.getWorld("PlotWorld"), -44.443, 82 , -90.584)),
    LOC_12("kopalnii", new Location(Bukkit.getWorld("PlotWorld"), -23.513, 20 , -179.300)),
    LOC_13("dzwigu", new Location(Bukkit.getWorld("PlotWorld"), -85.146, 97 , -224.649)),
    LOC_14("komisariatu policji", new Location(Bukkit.getWorld("PlotWorld"), 45.700, 69 ,-183.630)),
    LOC_15("lotniska", new Location(Bukkit.getWorld("PlotWorld"), 135.586, 65,22.368)),
    LOC_16("budowy", new Location(Bukkit.getWorld("PlotWorld"), 86.481, 101.5,-152.624));

    private String name;
    private Location location;

}
