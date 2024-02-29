package net.xayanix.nssv.reallife.title;

import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.xayanix.nssv.core.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TitleManager {

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(ChatUtil.fixColors(title), ChatUtil.fixColors(subtitle), fadeIn, stay, fadeOut);
    }

}
