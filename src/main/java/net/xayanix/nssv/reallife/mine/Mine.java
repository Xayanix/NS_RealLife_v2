package net.xayanix.nssv.reallife.mine;

import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.region.WorldGuardManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
public class Mine {

    private String region;

    private World world;
    private List<MineBlock> setBlocks;

    private ProtectedCuboidRegion wgRegion;
    private Polygonal2DRegion weRegion;

    public Mine(String region, World world, MineBlock... m){
        this.region = region;
        this.setBlocks = Arrays.asList(m);
        this.world = world;

        wgRegion = (ProtectedCuboidRegion) WorldGuardManager.getInstance().getWg().getRegionManager(this.world).getRegion(this.region);
        weRegion = new Polygonal2DRegion(BukkitUtil.getLocalWorld(this.getWorld()), wgRegion.getPoints(), wgRegion.getMinimumPoint().getBlockY(), wgRegion.getMaximumPoint().getBlockY());

        MineManager.getInstance().getMineList().add(this);
    }

    public MineBlock getRandomMaterial(){
        return this.getSetBlocks().get(RandomUtil.getRandom().nextInt(this.getSetBlocks().size()));
    }

    public void renew(){
        EditSession editSession = new EditSessionBuilder(this.world.getName()).fastmode(true).build();

        try {
            for (BlockVector block : weRegion) {
                Block bukkitBlock = BukkitUtil.toBlock(new BlockWorldVector(BukkitUtil.getLocalWorld(this.getWorld()), block));
                MineBlock random = this.getRandomMaterial();
                editSession.setBlock(new Vector(bukkitBlock.getX(), bukkitBlock.getY(), bukkitBlock.getZ()), new BaseBlock(random.getMaterial().getId(), random.getData()));
            }


        } catch (MaxChangedBlocksException e) {
            e.printStackTrace();
        }

        editSession.flushQueue();




    }

}
