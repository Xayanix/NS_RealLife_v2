package net.xayanix.nssv.reallife.petshop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

@Getter
@AllArgsConstructor
public class Pet {

    private String name;
    private int price;
    private EntityType type;

    public ItemStack getNewItemStack(){
        ItemStack spawnegg = new ItemStack(Material.MONSTER_EGG, 1);
        SpawnEggMeta meta = (SpawnEggMeta) spawnegg.getItemMeta();
        meta.setSpawnedType(this.getType());
        spawnegg.setItemMeta(meta);

        return spawnegg;
    }


}
