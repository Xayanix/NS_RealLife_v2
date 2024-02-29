package net.xayanix.nssv.reallife.utils;

import org.bukkit.Location;

public class LocationUtil {

    public static String nice(Location location){
        return "X: " + Math.round(location.getX()) + ", Y: " + Math.round(location.getY()) + ", Z: " + Math.round(location.getZ());
    }

}
