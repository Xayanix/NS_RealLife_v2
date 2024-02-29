package net.xayanix.nssv.reallife.listeners;

import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.jail.JailUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.robber.Rob;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    public PlayerDeathListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        User user = UserManager.getUser(player);

        if(user.getUserTempData().getSelfThiefCatch() != null){
            user.getUserTempData().getSelfThiefCatch().catched();
            user.getUserTempData().setSelfThiefCatch(null);
        }

        event.setDeathMessage(null);
        if(LocationList.SUROWCOWA.getWorld() != player.getWorld()){
            event.setKeepLevel(true);
            event.setKeepInventory(true);
        }

        if(player.getKiller() != null && player.getKiller() != player){
            User userkiller = UserManager.getUser(player.getKiller());
            userkiller.getUserTempData().removeFightTag(player.getName());

            if(user.getCharacter().getWantedlevel() > 0 && userkiller.getCharacter().getJob().getType() == JobType.LOWCA_GLOW && userkiller.getUserTempData().getHeadHunterTarget().equalsIgnoreCase(player.getName())) {
                JailUtil.jail(player, false, 4);
                userkiller.getCharacter().getJob().addPoint(userkiller.getPlayer(), true);
            } else WantedLevelManager.addWantedLevel(userkiller, "Morderstwo", 1);

            if(userkiller.getUserTempData().getLastRob() != null){
                Rob rob = userkiller.getUserTempData().getLastRob();
                if(rob.getBy().equalsIgnoreCase(player.getName())) {
                    userkiller.getUserTempData().setLastRob(null);

                    if(rob.getMoney() > user.getCharacter().getMoney().getWallet()) {
                        int money = user.getCharacter().getMoney().getWallet();
                        user.getCharacter().getMoney().removeFromWallet(money);
                        userkiller.getCharacter().getMoney().addToWallet(money);

                        userkiller.sendMessage("&8#&a Odzyskales czesc z skradzionych pieniedzy (&e" + money + " zlotych&a).");
                        return;
                    }

                    user.getCharacter().getMoney().removeFromWallet(rob.getMoney());
                    userkiller.getCharacter().getMoney().addToWallet(rob.getMoney());

                    userkiller.sendMessage("&8#&a Odzyskales wszystkie skradzione pieniadze (&e" + rob.getMoney() + " zlotych&a).");

                }



            }

        }

        user.getUserTempData().getDamageAllows().clear();
        user.getUserTempData().setHeadHunterTarget("");

        Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), () -> player.spigot().respawn(), 1);

    }
}
