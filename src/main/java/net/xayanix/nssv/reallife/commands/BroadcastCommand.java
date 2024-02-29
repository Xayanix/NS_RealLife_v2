package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.commands.admin.MuteCommand;
import net.xayanix.nssv.core.listeners.PlayerChatListener;
import net.xayanix.nssv.core.objects.Mute;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.company.CompanyManager;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.offer.OfferType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.Travel;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BroadcastCommand extends BukkitCommand {

    public BroadcastCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(!(arg0 instanceof Player)) return false;
        User user = UserManager.getUser(arg0.getName());

        if(PlayerChatListener.getStatus() == 2) {
            ChatUtil.sendMessage(arg0, "&8#&c Czat jest wylaczony, nie mozesz napisac ogloszenia.");
            return false;
        }

        Mute mute = net.xayanix.nssv.core.objects.User.getUser(arg0.getName()).getUserConfiguration().getMute();
        if(mute != null && mute.isActive()){
            ChatUtil.sendMessage(arg0, "&8#&c Jestes wyciszony.");
            return false;
        }

        if(user.getCharacter().getLevel() < 2){
            ChatUtil.sendMessage(arg0, "&8#&c Musisz miec co najmniej drugi poziom postaci.");
            return false;
        }

        if(arg3.length == 0){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /ogl <tresc>");
            return false;
        }

        if(!user.isCooldownFinished("ogloszenie", (user.isVip() ? TimeUnit.SECONDS.toMillis(15) : TimeUnit.MINUTES.toMillis(1)))) {
            ChatUtil.sendMessage(arg0, "&8#&c Ogloszenia mozesz pisac raz na &7" + (user.isVip() ? 15 : 60) + " sekund&c.");
            return false;
        }

        String message = StringUtil.arrayToString(arg3, 0)
                .replace("&", "");

        for(User user1 : UserManager.getUsersOnline())
            if(user1.getUserConfiguration().isReceiveAdvertisments())
                user1.sendMessage("&8#&2 Ogloszenie gracza &7"  + user.getUserConfiguration().getNicknameColor() + user.getName() + "&2: &7" + message);

        return true;
    }
}
