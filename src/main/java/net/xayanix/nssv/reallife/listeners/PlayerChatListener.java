package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.prefix.PrefixManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    public PlayerChatListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String prefix = PrefixManager.getPrefix(player);
        String message = "%2$s";
        User user = UserManager.getUser(player);

        if(user.isNew()){
            ChatUtil.sendMessage(player, "&8#&a Utworz najpierw konto: &e/konto <imie> <nazwisko> <wiek>");
            event.setCancelled(true);
            return;
        }

        String mainColor = PrefixManager.getPrefixColor(player);
        String rawmessage = event.getMessage().replace("&", "");

        if(rawmessage.startsWith("!!")){
            rawmessage = rawmessage.replaceFirst("!!", "");
            if(rawmessage.length() == 0){
                event.setCancelled(true);
                return;
            }

            if(user.getCharacter().getCompany() == null){
                event.setCancelled(true);
                ChatUtil.sendMessage(player, "&8#&c Nie masz firmy, wiec nie mozesz pisac na jej czacie.");
                return;
            }

            user.getCharacter().getCompany().broadcast("&e(Czat firmy:&7 " + user.getCharacter().getCompany().getName() + "&e) (&7" + (user.getCharacter().getCompany().isOwner(user) ? "Szef" : "Pracownik") + "&e) &7" + user.getUserConfiguration().getNicknameColor() + player.getName() + " &8»&7 " + rawmessage);
            event.setCancelled(true);
        } else if(rawmessage.startsWith("!")){
            rawmessage = rawmessage.replaceFirst("!", "");
            if(rawmessage.length() == 0){
                event.setCancelled(true);
                return;
            }

            JobManager.broadcast(user.getCharacter().getJob().getType(), "&a(Czat:&7 " + user.getCharacter().getJob().getType().getShortName() + " -ów&a) (&7" + user.getCharacter().getJob().getLevel() + " Lv pracy&a) &7" + user.getUserConfiguration().getNicknameColor() + player.getName() + " &8»&7 " + rawmessage);
            event.setCancelled(true);
        } else if(rawmessage.startsWith("@")){
            rawmessage = rawmessage.replaceFirst("@", "");
            if(rawmessage.length() == 0){
                event.setCancelled(true);
                return;
            }

            if(user.getCharacter().getFamily() == null){
                event.setCancelled(true);
                ChatUtil.sendMessage(player, "&8#&c Nie masz rodziny, wiec nie mozesz pisac na jej czacie.");
                return;
            }

            user.getCharacter().getFamily().broadcast("&5(Czat rodziny:&7 " + user.getCharacter().getFamily().getName() + "&5) (&7" + user.getCharacter().getFamilyRank().getColor() + user.getCharacter().getFamilyRank() + "&5) &7" + user.getUserConfiguration().getNicknameColor() + player.getName() + " &8»&7 " + rawmessage);
            event.setCancelled(true);
        } else if(rawmessage.startsWith("#")){
            rawmessage = rawmessage.replaceFirst("#", "");
            if(rawmessage.length() == 0){
                event.setCancelled(true);
                return;
            }

            if(user.getUserTempData().getSpeedometer() == null){
                event.setCancelled(true);
                ChatUtil.sendMessage(player, "&8#&c Nie jestes w pojezdzie.");
                return;
            }

            for(User user1 : UserManager.getUsersOnline())
                if(user1.getUserTempData().getSpeedometer() != null)
                    user1.sendMessage("&9(&7CB-Radio&9) (&7" + user.getCharacter().getFamilyRank().getColor() + user.getCharacter().getFamilyRank() + "&9) &7" + user.getUserConfiguration().getNicknameColor() + player.getName() + " &8»&7 " + rawmessage);

            event.setCancelled(true);
        }

        /*
        if(rawmessage.startsWith("!k ")) {
            rawmessage = rawmessage.replaceFirst("!k ", "");
            new IC(user.getCharacter().getFullCharacterName() + " (( " + player.getName() + " )) krzyczy: " + rawmessage, player);
        } else if(rawmessage.startsWith("!s ")) {
            rawmessage = rawmessage.replaceFirst("!s ", "");
            new IC("&7" + user.getCharacter().getFullCharacterName() + " (( " + player.getName() + " )) szepcze: " + rawmessage, player);
        }  else if(rawmessage.startsWith("!l ")) {
            rawmessage = rawmessage.replaceFirst("!l ", "");
            new IC("&7" + user.getCharacter().getFullCharacterName() + " (( " + player.getName() + " )) mowi: " + rawmessage, player);
        } else if(rawmessage.startsWith("!me ")) {
            Bukkit.getScheduler().runTask(RealLife.getInstance(), () -> new IC(ChatSeparator.ME_COLOR + player.getName() + " " + event.getMessage().replaceFirst("!me ", ""), user).me());
        }
        */

        if(player.hasPermission("helper")) {
            event.setFormat(ChatUtil.fixColors("&7\n" + prefix + user.getUserConfiguration().getNicknameColor() + player.getName() + " &8»&7 " + mainColor + message + "\n&7 "));
            return;
        }


        String level = user.getUserConfiguration().isDisplayLevelOnChat() ? "&8" + mainColor + user.getCharacter().getLevel() + " Lv&8 | " : "";
        String job = user.getUserConfiguration().isDisplayJobOnPrefix() ? "&8" + mainColor + user.getCharacter().getJob().getType().getShortName() + "&8 | " : "";
        String company = user.getCharacter().getCompany() == null || !user.getUserConfiguration().isDisplayCompanyOnChat() ? "" : "&8(&7Firma: " + mainColor + user.getCharacter().getCompany().getName() + "&8) ";

        event.setFormat(ChatUtil.fixColors(level + company + job + prefix + user.getUserConfiguration().getNicknameColor() + player.getName() + " &8»&7 " + mainColor) + message);

    }
}
