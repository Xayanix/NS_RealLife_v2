package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.jail.JailUtil;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.SpaceUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.concurrent.TimeUnit;

public class PlayerTeleportListener implements Listener {

    public PlayerTeleportListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHighEvent(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(event.getFrom().getWorld() == LocationList.SPAWN.getWorld() && event.getTo().getWorld() == LocationList.SUROWCOWA.getWorld()){
            user.getUserTempData().setTeleportationGrace(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
            user.sendMessage("&8#&a Otrzymales niesmiertelnosc na 60 sekund.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);

        if(user == null)
            return;

        if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL){
            user.sendMessage("&8#&c Perly sa zablokowane, gdyz sa zbyt nierealistyczne. :/");
            event.setCancelled(true);
            return;
        }

        if(user.isNew() && event.getTo().getWorld() != LocationList.NEWBIE_SPAWN.getWorld()){
            event.setTo(LocationList.NEWBIE_SPAWN);
            user.sendMessage("&8#&a Utwórz konto zanim opuscisz to miejsce.");
            return;
        }

        if(user.getCharacter().getJail() != null && !player.hasPermission("admin") && SpaceUtil.distanceBetweenLocations(event.getFrom(), event.getTo()) > 2){
            event.setTo(JailUtil.getRandomJailLocation());
            return;
        }

        if(SpaceUtil.distanceBetweenLocations(event.getFrom(), event.getTo()) > 5){
            if(user.getUserTempData().getSelfThiefCatch() != null && user.getUserTempData().getSelfThiefCatch().getLastTry() > System.currentTimeMillis()){
                event.setCancelled(true);
                user.sendMessage("&8#&b W poblizu jest policjant, ktory usiluje Cie schwytac. Nie mozesz sie teleportowac.");
                return;
            }

            if(user.getUserTempData().isTraveling()){
                event.setTo(LocationList.BUS);
                user.sendMessage("&8#&c Nie mozesz teleportowac sie podczas jazdy autobusem.");
                return;
            }

            if(user.getUserTempData().getPackTarget() != null){
                user.sendMessage("&8#&c Teleportowales sie, wiec Twoje zlecenie dostarczenia paczki do sklepu " + user.getUserTempData().getPackTarget().getDesc() + " zostalo przerwane.");
                user.getUserTempData().setPackTarget(null);
                return;
            }
        }



    }
}
