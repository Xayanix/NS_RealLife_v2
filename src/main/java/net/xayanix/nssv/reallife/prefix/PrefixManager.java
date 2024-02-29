package net.xayanix.nssv.reallife.prefix;

import eu.haelexuis.utils.xoreboard.XoreBoardUtil;
import net.xayanix.nssv.core.utils.ChatUtil;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PrefixManager {

    public static String getPrefix(Player player){
        return ChatUtil.fixColors(PermissionsEx.getUser(player).getPrefix());
    }

    public static String getPrefixColor(Player player)
    {
        String prefix = getPrefix(player);
        if(prefix.length() > 2)
            return getPrefix(player).substring(0, 2);

        return prefix;
    }

}
