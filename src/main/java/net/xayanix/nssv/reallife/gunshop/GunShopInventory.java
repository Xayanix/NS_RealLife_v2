package net.xayanix.nssv.reallife.gunshop;

import com.shampaggon.crackshot.CSUtility;
import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.reallife.company.gui.CompanyWarpInventory;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.logger.Logger;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GunShopInventory implements CustomInventoryAction {

    private static CSUtility csUtility = new CSUtility();

    public static void setPrices(){

    }

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        if(inventoryClickEvent.getSlot() == 44){
            CompanyWarpInventory.display(player);
            return;
        }

        int i = 0;
        for(Guns gun : Guns.values()) {
            if(gun.getConfigName() != null){
                if(inventoryClickEvent.getSlot() == i){
                    if(EconomyManager.getInstance().takeWallet(user, gun.getPrice())){
                        player.getInventory().addItem(csUtility.generateWeapon(gun.getConfigName()));
                        player.closeInventory();
                    }
                }
                i++;
                continue;
            }

            if(inventoryClickEvent.getSlot() == i){
                if(EconomyManager.getInstance().takeWallet(user, gun.getPrice())){
                    player.getInventory().addItem(gun.getAmmo());
                    player.closeInventory();
                }
            }

            i++;
        }
    }

    public static void display(Player player){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&cSklep z broniami", 1, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15)
                .setName(" ")
                .toItemStack(),
                new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7)
                        .setName(" ")
                        .toItemStack());
        inventory.setAction(new GunShopInventory());

        int i = 0;
        for(Guns gun : Guns.values()) {
            if(gun.getConfigName() != null){
                ItemStack weapon = csUtility.generateWeapon(gun.getConfigName());
                if(weapon == null){
                    Logger.info("Weapon " + gun.getConfigName() + " not found.");
                    i++;
                    continue;
                }

                inventory.setItem(i, new ItemBuilder(weapon)
                        .setName("&7Bron:&c " + gun.getName())
                        .setLore("&7Cena:&c " + gun.getPrice() + " zlotych")
                        .toItemStack());
                i++;
                continue;
            }

            inventory.setItem(i, new ItemBuilder(gun.getAmmo())
                    .setName("&6Amunicja do broni")
                    .setLore("&7Cena:&c " + gun.getPrice() + " zlotych")
                    .toItemStack());

            i++;
        }

        inventory.display(player);

    }

}
