package net.xayanix.nssv.reallife.gunshop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.xayanix.nssv.core.objects.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public enum Guns {

    DEAGLE("2x Desert Eagle", "Deagle", 10000, null),
    SNIPE("Sniperka", "Hunting", 30000, null),
    SHOTGUN("Shotgun", "Gauss", 15000, null),
    AMMO("Amunicja", null, 300, new ItemBuilder(Material.GHAST_TEAR, 32)
            .setName("&6Amunicja")
            .toItemStack()),
    AMMO_64("Amunicja x128", null, 1200, new ItemBuilder(Material.GHAST_TEAR, 128)
            .setName("&6Amunicja")
            .toItemStack());

    private String name;
    private String configName;
    private int price;
    private ItemStack ammo;

}
