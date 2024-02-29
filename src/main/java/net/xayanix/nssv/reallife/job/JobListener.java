package net.xayanix.nssv.reallife.job;

import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.character.Character;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.sound.SoundKey;
import net.xayanix.nssv.reallife.sound.SoundManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.concurrent.TimeUnit;

public class JobListener implements Listener {

    public JobListener(){
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);
        Character character = user.getCharacter();

        if(character == null)
            return;

        Job job = character.getJob();
        Material material = event.getBlock().getType();

        if(user.getCharacter().getCompany() != null && user.isCooldownFinished("companyzarobek", 3000)) {
            if(material == Material.LOG || material == Material.LOG_2 || material == Material.WOOD)
                user.getCharacter().getCompany().setEarned(user.getCharacter().getCompany().getEarned() + JobType.DRWAL.getReward());
            else if(material == Material.STONE || material == Material.COBBLESTONE || material.toString().contains("ORE"))
                user.getCharacter().getCompany().setEarned(user.getCharacter().getCompany().getEarned() + JobType.GORNIK.getReward());
        }

        if(job != null){

            if(job.getType() == JobType.DRWAL && RegionUtil.isLocationInRegion(event.getBlock().getLocation(), "kopalnia")){
                if(material == Material.LOG || material == Material.LOG_2 || material == Material.WOOD) {
                    if(user.isCooldownFinished("drewaldrewno", 1500))
                        job.addPoint(player, true);
                } else if(material == Material.LEAVES || material == Material.LEAVES_2) {
                    if(user.isCooldownFinished("drewalliscie", 1000)) {
                        job.addPoint(player, false);
                        job.addReward(RandomUtil.getRandomNumber(3));
                    }

                }

            } else if(job.getType() == JobType.GORNIK && RegionUtil.isLocationInRegion(event.getBlock().getLocation(), "kopalnia")) {
                if(material.toString().contains("ORE") && user.getUserTempData().getLastOrePlace() < System.currentTimeMillis())
                    job.addPoint(player, true);
                else if(material == Material.STONE || material == Material.COBBLESTONE) {
                    job.addPoint(player, false);
                    job.addReward(RandomUtil.getRandomNumber(3));
                }

            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);
        Character character = user.getCharacter();

        if(character == null)
            return;

        Job job = character.getJob();

        //if(job.getType() == JobType.GORNIK && event.getBlock().getType().toString().contains("ORE"))
        //    user.getUserTempData().setLastOrePlace(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));

        if(BlockUtil.isSolid(event.getBlock()) && user.isCooldownFinished("budowniczyplace", TimeUnit.SECONDS.toMillis(3))) {
            if(user.getCharacter().getCompany() != null)
                user.getCharacter().getCompany().setEarned(user.getCharacter().getCompany().getEarned() + JobType.BUDOWNICZY.getReward());
            if(job != null && job.getType() == JobType.BUDOWNICZY)
                job.addPoint(player,true);

        }

    }

}
