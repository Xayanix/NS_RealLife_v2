package net.xayanix.nssv.reallife.utils;

import net.xayanix.nssv.reallife.configuration.Configuration;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerUtil {

    public static List<Player> getNearbyPlayers(Player player){
        return getNearbyPlayers(player, ((CraftPlayer) player).getHandle().world.spigotConfig.playerTrackingRange);
    }

    public static List<Player> getNearbyPlayers(Player player, int dist){
        List<Entity> entityList = player.getNearbyEntities(dist, dist, dist);

        List<Player> players = new ArrayList<>();
        players.add(player);

        for(Entity entity : entityList){
            if(entity.getType() == EntityType.PLAYER){
                Player nplayer = (Player) entity;
                if(nplayer.isOnline())
                    players.add(nplayer);
            }
        }

        return players;

    }

    public static void disablePlayerTracker(Player player){
        ((CraftPlayer) player).getHandle().world.spigotConfig.playerTrackingRange = 0;
    }

    public static void enablePlayerTracker(Player player){
        ((CraftPlayer) player).getHandle().world.spigotConfig.playerTrackingRange = Configuration.getInstance().getPlayerTrackerDistance();
    }

    public static double distanceBetweenPlayers(Player player1, Player player2){
        if(player1.getLocation().getWorld() != player2.getLocation().getWorld())
            return Double.MAX_VALUE;

        return player1.getLocation().distance(player2.getLocation());
    }

}
