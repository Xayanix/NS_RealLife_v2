package net.xayanix.nssv.reallife.company.gui;

import lombok.AllArgsConstructor;
import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.company.CompanyManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.WarpManager;
import net.xayanix.nssv.reallife.warp.Warps;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CompanyWarpInventory implements CustomInventoryAction {

    private List<Company> companyList;

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        int i = 0;
        for(Company company : this.companyList) {
            if(inventoryClickEvent.getSlot() == i){
                WarpManager.travel(player, company.getHome());
                player.closeInventory();
                return;
            }
            i++;
        }
    }

    public static void display(Player player){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&cWybierz firme", 6, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack(), new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());

        int i = 0;
        List<Company> companyList = new ArrayList<>();
        for(Company company : CompanyManager.getCompany()) {
            if(company.getMembersOnline().size() > 0) {
                inventory.setItem(i, new ItemBuilder(Material.PAINTING)
                        .setName("&7Przystanek:&f Firma " + company.getName())
                        .setLore("&7Cena przejazdu:&f " + WarpManager.travelPrice(player, company.getHome()) + " zl",
                                "&7Czas podrozy:&f " + WarpManager.travelTime(player, company.getHome()) + " sekund")
                        .toItemStack());
                companyList.add(company);
                i++;
            }
        }

        inventory.setAction(new CompanyWarpInventory(companyList));
        inventory.display(player);

    }

}
