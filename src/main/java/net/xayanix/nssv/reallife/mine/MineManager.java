package net.xayanix.nssv.reallife.mine;

import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.region.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MineManager implements Runnable{

    @Getter
    private static MineManager instance;

    @Getter
    private List<Mine> mineList;

    @Getter
    private BukkitTask bukkitTask;

    public MineManager(){
        instance = this;
        this.mineList = new ArrayList<>();
        this.create();
        this.start(TimeUnit.MINUTES.toSeconds(3));
    }

    public void start(long delay){
        this.bukkitTask = Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), this, 20 * delay);
        ChatUtil.broadcast("&8#&a Kolejna odnowa kopalnii za &e" + StringUtil.getStringTimeFromLong(delay) + "");
    }

    public void create(){
        new Mine("kopalnia_1", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LOG, 0),
                new MineBlock(Material.LOG, 1),
                new MineBlock(Material.LOG, 2),
                new MineBlock(Material.LOG_2, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LOG, 3));

        new Mine("kopalnia_2", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LOG, 0),
                new MineBlock(Material.LOG, 1),
                new MineBlock(Material.LOG, 2),
                new MineBlock(Material.LOG_2, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LOG, 3));

        new Mine("kopalnia_3", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LOG, 0),
                new MineBlock(Material.LOG, 1),
                new MineBlock(Material.LOG, 2),
                new MineBlock(Material.LOG_2, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LOG, 3));

        new Mine("kopalnia_4", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LOG, 0),
                new MineBlock(Material.LOG, 1),
                new MineBlock(Material.LOG, 2),
                new MineBlock(Material.LOG_2, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LOG, 3));

        new Mine("kopalnia_5", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LOG, 0),
                new MineBlock(Material.LOG, 1),
                new MineBlock(Material.LOG, 2),
                new MineBlock(Material.LOG_2, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LOG, 3));

        new Mine("kopalnia_6", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LOG, 0),
                new MineBlock(Material.LOG, 1),
                new MineBlock(Material.LOG, 2),
                new MineBlock(Material.LOG_2, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LOG, 3));

        new Mine("kopalnia_7", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LOG, 0),
                new MineBlock(Material.LOG, 1),
                new MineBlock(Material.LOG, 2),
                new MineBlock(Material.LOG_2, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LEAVES, 0),
                new MineBlock(Material.LOG, 3));

        new Mine("kopalnia_8", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.DIAMOND_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalnia_9", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.IRON_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalnia_10", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.REDSTONE_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalnia_11", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.EMERALD_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalnia_12", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LAPIS_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalnia_13", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.OBSIDIAN, 0));



        new Mine("kopalniavip_2", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.DIAMOND_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalniavip_3", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.IRON_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalniavip_4", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.REDSTONE_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalniavip_1", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.EMERALD_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalniavip_5", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.LAPIS_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalniavip_7", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.GOLD_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalnia_14", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.GOLD_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalniavip_8", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.COAL_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalnia_15", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.COAL_ORE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0),
                new MineBlock(Material.STONE, 0),
                new MineBlock(Material.COBBLESTONE, 0));

        new Mine("kopalniavip_6", Bukkit.getWorld("PlotWorld"),
                new MineBlock(Material.OBSIDIAN, 0));


    }

    @Override
    public void run() {
        ChatUtil.broadcast("&8#&a Wszystkie kopalnie i tartak zostaly odnowione!");
        this.start(this.calc());

        for(Mine mine : mineList)
            mine.renew();

    }

    public void broadcast(String message){
        for(Player player : Bukkit.getOnlinePlayers())
            if(RegionUtil.isPlayerInRegion(player, "kopalnia"))
                ChatUtil.sendMessage(player, message);
    }

    public long calc(){
        int players = 0;
        for(Player player : Bukkit.getOnlinePlayers())
            if(RegionUtil.isPlayerInRegion(player, "kopalnia"))
                players++;

        int startTime = 900 - players * 20;
        if(startTime < 120)
            return 120;
        return startTime;
    }

}
