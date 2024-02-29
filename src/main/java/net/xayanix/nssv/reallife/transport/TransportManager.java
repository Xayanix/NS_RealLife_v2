package net.xayanix.nssv.reallife.transport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TransportManager {

    public static void transport(Player player, Location location){

    }

    public static void transport(Player player, TransportLocation location){
        transport(player, location.getDestination());
    }

}
