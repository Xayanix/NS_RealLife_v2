package net.xayanix.nssv.reallife.user;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserManager {

    @Getter
    private static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public static User getUser(String name){
        return users.get(name.toLowerCase());
    }

    public static User getUser(Player player){
        return getUser(player.getName());
    }

    public static List<User> getUsersOnline(){
        List<User> users = new CopyOnWriteArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers())
            users.add(UserManager.getUser(player));

        return users;
    }

}
