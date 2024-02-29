package net.xayanix.nssv.reallife.transport;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public enum TransportLocation {

    URZAD("Urzad Miasta", Material.TRAP_DOOR, 0, 0, 0, "world");

    @Getter
    private String name;

    @Getter
    private Material icon;

    @Getter
    private Location destination;

    TransportLocation(String name, Material icon, int x, int y, int z, String world){
        this.name = name;
        this.icon = icon;
        this.destination = new Location(Bukkit.getWorld(world), x, y, z);
    }

}
