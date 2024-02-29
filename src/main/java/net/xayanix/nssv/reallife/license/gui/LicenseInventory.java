package net.xayanix.nssv.reallife.license.gui;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.license.License;
import net.xayanix.nssv.reallife.license.LicenseManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LicenseInventory implements CustomInventoryAction {

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        int i = 18;
        for(License license : License.values()) {
            if(inventoryClickEvent.getSlot() == i){
                player.closeInventory();

                if(LicenseManager.hasLicense(user, license)){
                    user.sendMessage("&8#&a Posiadasz juz ta licencje.");
                    return;
                }

                if(EconomyManager.getInstance().takeWallet(user, license.getPrice())){
                    LicenseManager.giveLicense(user, license);
                    ChatUtil.sendMessage(player, "&8#&a Zakupiles:&7 " + license.getName());
                }

                return;
            }
            i++;
        }
    }

    public static void display(Player player){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&aLicencje", 6, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack(), new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        inventory.setAction(new LicenseInventory());

        int i = 18;
        for(License license : License.values()) {
            inventory.setItem(i, new ItemBuilder(license.getIcon())
                    .setName("&7Typ licencji:&f " + license.getName())
                    .setLore("&7Cena:&f " + license.getPrice() + " zlotych")
                    .toItemStack());
            i++;
        }

        inventory.display(player);

    }

}
