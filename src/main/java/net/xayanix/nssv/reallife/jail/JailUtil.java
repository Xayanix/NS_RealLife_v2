package net.xayanix.nssv.reallife.jail;

import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JailUtil {

    public static List<Location> jails = Arrays.asList(
            new Location(Bukkit.getWorld("PlotWorld"), 33, 66, -87),
            new Location(Bukkit.getWorld("PlotWorld"), 43, 66, -87),
            new Location(Bukkit.getWorld("PlotWorld"), 52, 66, -87),
            new Location(Bukkit.getWorld("PlotWorld"), 33, 66, -109),
            new Location(Bukkit.getWorld("PlotWorld"), 42, 66, -109),
            new Location(Bukkit.getWorld("PlotWorld"), 52, 66, -109),
            new Location(Bukkit.getWorld("PlotWorld"), 51, 70, -87),
            new Location(Bukkit.getWorld("PlotWorld"), 41, 70, -87),
            new Location(Bukkit.getWorld("PlotWorld"), 51, 70, -109),
            new Location(Bukkit.getWorld("PlotWorld"), 33, 70, -110),
            new Location(Bukkit.getWorld("PlotWorld"), 42, 70, -109),
            new Location(Bukkit.getWorld("PlotWorld"), 51, 75, -87),
            new Location(Bukkit.getWorld("PlotWorld"), 33, 75, -87),
            new Location(Bukkit.getWorld("PlotWorld"), 33, 75, -109),
            new Location(Bukkit.getWorld("PlotWorld"), 42, 75, -109),
            new Location(Bukkit.getWorld("PlotWorld"), 51, 75, -109)
    );

    public static Location getRandomJailLocation(){
        Random random = RandomUtil.getRandom();
        return jails.get(random.nextInt(jails.size()));
    }

    public static void jail(Player victim, boolean caution, int timemultiple){
        User user = UserManager.getUser(victim);
        if(user.getCharacter().getWantedlevel() == 0)
            return;
        if(user.getUserTempData().isTraveling())
            return;
        if(user.getCharacter().getJail() != null)
            return;

        int time = user.getCharacter().getWantedlevel() * 40 * timemultiple;

        if(user.isVip()){
            time = time / 2;
            user.sendMessage("&8#&6 Jestes VIP, wiec skrocono Twoj pobyt w wiezieniu do polowy.");
        }

        victim.teleport(getRandomJailLocation());
        user.getCharacter().setJail(new Jail(user, time, caution, time * 100));
        user.getCharacter().setWantedlevel(0);
        equipArmor(victim);

        user.sendTitle("&4&lWiezienie.", "&7Zostales uwieziony za przestepstwa na &4" + time + " sekund &7.", 20, 20, 20);
        user.sendMessage("&8#&a Potrzebujesz pomocy prawnika? [&e/wezwij prawnik&a]");
        if(caution) {
            user.sendMessage("&8#&a Mozesz wplacic kaucje i wyjsc wczesniej:&e /kaucja&a.");
            user.sendMessage("&8#&a Cena za wyjscie z wiezienia:&e " + time * 100 + " zlotych");
        }
    }

    public static void jail(Player victim, int time){
        User user = UserManager.getUser(victim);
        if(user.getCharacter().getJail() != null)
            return;

        victim.teleport(getRandomJailLocation());
        user.getCharacter().setJail(new Jail(user, time, false, time * 100));
        equipArmor(victim);

        user.sendTitle("&4&lWiezienie.", "&7Zostales uwieziony przez Administratora na &4" + time + " sekund &7.", 20, 20, 20);
    }

    public static void equipArmor(Player player){
        if(player.getInventory().getHelmet() == null)
            player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).dyeLeatherArmor(Color.BLACK).toItemStack());
        if(player.getInventory().getChestplate() == null)
            player.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).dyeLeatherArmor(Color.WHITE).toItemStack());
        if(player.getInventory().getLeggings() == null)
            player.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).dyeLeatherArmor(Color.BLACK).toItemStack());
        if(player.getInventory().getBoots() == null)
            player.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).dyeLeatherArmor(Color.WHITE).toItemStack());
    }

}
