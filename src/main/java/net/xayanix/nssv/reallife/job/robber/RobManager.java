package net.xayanix.nssv.reallife.job.robber;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.pvp.DamageAllow;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import pl.xayanix.nssv.combat.combat.Combat;
import pl.xayanix.nssv.combat.user.CombatPlayer;
import pl.xayanix.nssv.combat.user.CombatPlayerManager;

public class RobManager {

    public static void rob(Player robber, Player victim){
        if(RegionUtil.isPlayerInRegion(victim, "norobzone")){
            ChatUtil.sendMessage(robber, "&8#&c Gracz znajduje sie w rejonie z monitoringiem, odpuszczasz kradziez w tym miejscu.");
            return;
        }

        robber.removePotionEffect(PotionEffectType.INVISIBILITY);

        CombatPlayer combatPlayer = CombatPlayerManager.getInstance().getCombatPlayer(robber);
        if(combatPlayer.getCombat() != null)
            combatPlayer.getCombat().setActive();
        else combatPlayer.setCombat(new Combat(robber));

        User ruser = UserManager.getUser(robber);

        ruser.sendTitle("", "&cPróbujesz okrasc&6 " + victim.getName() + "&c...", 0, 20, 0);
        WantedLevelManager.addWantedLevel(ruser, "Kradziez", 1);

        User user = UserManager.getUser(victim);

        user.getUserTempData().setRobbed(true);
        user.getUserTempData().getDamageAllows().add(new DamageAllow(robber.getName()));

        user.sendMessage("&8#&c Zlodziej &6" + robber.getName() + "&c probuje Cie okrasc!");
        user.sendMessage("&8#&c Ukucnij (klawisz SHIFT) jak najszybciej, aby sie obronic!");

        user.sendTitle("&c&lZlodziej!", "&7Ktos probuje Cie okrasc!", 0, 20, 0);

        Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> {
            if(user.getUserTempData().isRobbed()){
                if(!ruser.isOnline())
                    return;

                int money = (int) (user.getCharacter().getMoney().getWallet() * RandomUtil.getRandDouble(0.4, 1.0));
                user.getUserTempData().setLastRob(new Rob(ruser.getName(), money));


                user.getCharacter().getMoney().removeFromWallet(money);
                ruser.getCharacter().getMoney().addToWallet(money);

                ruser.getCharacter().getJob().addPoint(ruser.getPlayer(), false);

                user.sendMessage("&8#&c Zostales okradziony przez &7" + ruser.getName() + " (" + ruser.getCharacter().getFullCharacterName() + ") &ci straciles &7" + money + " zlotych&c. Zabij zlodzieja w ciagu 5 min., aby odzyskac skradzione pieniadze.");
                user.sendTitle("", "&c&lZłap zlodzieja!".toUpperCase(), 20, 30, 20);
                ruser.sendMessage("&8#&c Okradles gracza &7" + user.getName() + " (" + user.getCharacter().getFullCharacterName() + ") &ci zyskales &7" + money + " zlotych&c.");

                if(victim.isOnline()) {
                    ruser.getPlayer().openInventory(victim.getInventory());
                    Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), () -> ruser.getPlayer().closeInventory(), 20);
                }

                ruser.sendTitle("&c&lUciekaj!", "&7Zaraz tutaj beda gliny!", 10, 30, 10);
                user.getUserTempData().setRobbed(false);

            } else ruser.sendMessage("&8#&c Gracz obronil sie przed kradzieza.");
        }, 30);

    }

    public static boolean wasRobbedBy(User user, User robber){
        for(DamageAllow rob : user.getUserTempData().getDamageAllows()){
            if(rob.getTimestamp() < System.currentTimeMillis()){
                user.getUserTempData().getDamageAllows().remove(rob);
                continue;
            }

            if(rob.getRobber().equalsIgnoreCase(robber.getName()))
                return true;

        }

        return false;
    }

}
