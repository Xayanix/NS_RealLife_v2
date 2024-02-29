package net.xayanix.nssv.reallife.ball;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.core.objects.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

@Getter
public class Ball {

    private ArmorStand armorStand;

    @Setter
    private long lastUse;

    public Ball(Location location){
        this.lastUse = System.currentTimeMillis();
        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        //this.armorStand.setSmall(true);
        this.armorStand.setItemInHand(new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
            .setSkullOwner("Ball")
            .toItemStack());
        this.armorStand.setCustomName("BALL");
        this.armorStand.setCustomNameVisible(false);
        //this.armorStand.setVisible(false);
        this.armorStand.setRightArmPose(armorStand.getRightArmPose().setZ(4.7).setX(0.7));

        BallManager.getInstance().getBallList().add(this);
    }

    public void destroy(){
        this.armorStand.remove();
        BallManager.getInstance().getBallList().remove(this);
    }

}
