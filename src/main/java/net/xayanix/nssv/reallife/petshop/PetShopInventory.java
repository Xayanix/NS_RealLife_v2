package net.xayanix.nssv.reallife.petshop;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class PetShopInventory implements CustomInventoryAction {

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        int i = 0;
        for(Pet pet : PetManager.getInstance().getPetList()) {
           if(i == inventoryClickEvent.getSlot()){
               if(EconomyManager.getInstance().takeWallet(user, pet.getPrice())) {
                   user.getPlayer().getInventory().addItem(pet.getNewItemStack());
               }
               
               return;
           }

            i++;
        }
    }

    public static void display(Player player){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&cSklep z mobami", 3, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15)
                .setName(" ")
                .toItemStack(),
                new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7)
                        .setName(" ")
                        .toItemStack());
        inventory.setAction(new PetShopInventory());

        int i = 0;
        for(Pet pet : PetManager.getInstance().getPetList()) {
            inventory.setItem(i, new ItemBuilder(pet.getNewItemStack())
                    .setName("&7Jajko spawnu:&f " + pet.getName())
                    .setLore("&7Cena:&f " + pet.getPrice() + " zlotych",
                            "&aKliknij, aby kupic.")
                    .toItemStack());
            i++;
        }

        inventory.display(player);

    }

}
