package net.xayanix.nssv.reallife.warp.gui;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.reallife.company.gui.CompanyWarpInventory;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.WarpManager;
import net.xayanix.nssv.reallife.warp.Warps;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class WarpInventory implements CustomInventoryAction {

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        if(inventoryClickEvent.getSlot() == 44){
            CompanyWarpInventory.display(player);
            return;
        }

        int i = 9;
        for(Warps warp : Warps.values()) {
            if(inventoryClickEvent.getSlot() == i){
                WarpManager.travel(player, warp.getLocation());
                player.closeInventory();
                return;
            }
            i++;
        }
    }

    public static void display(Player player){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&cWybierz przystanek docelowy", 5, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15)
                .setName(" ")
                .toItemStack(),
                new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7)
                        .setName(" ")
                        .toItemStack());
        inventory.setAction(new WarpInventory());

        int i = 9;
        for(Warps warp : Warps.values()) {
            inventory.setItem(i, new ItemBuilder(warp.getIcon())
                    .setName("&7Przystanek:&f " + warp.getName())
                    .setLore("&7Cena przejazdu:&f " + WarpManager.travelPrice(player, warp.getLocation()) + " zl",
                            "&7Czas podrozy:&f " + WarpManager.travelTime(player, warp.getLocation()) + " sekund")
                    .toItemStack());
            i++;
        }

        inventory.setItem(44, new ItemBuilder(Material.PAINTING)
            .setName("&cPrzystanki firm serwerowych")
                .setLore("&7Kliknij, aby przejsc do tej sekcji.")
            .toItemStack());

        inventory.display(player);

    }

}
