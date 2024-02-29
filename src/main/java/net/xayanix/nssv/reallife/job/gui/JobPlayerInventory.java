package net.xayanix.nssv.reallife.job.gui;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.reallife.job.Job;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class JobPlayerInventory implements CustomInventoryAction {

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
    }

    public static void display(Player player, JobType jobType){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&cGracze z praca: &e" + jobType.getShortName(), 6, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack(), new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        inventory.setAction(new JobPlayerInventory());

        int i = 0;
        for(User user1 : UserManager.getUsersOnline()) {
            if(i > 53)
                break;
            if(user1.getCharacter().getJob().getType() == jobType && user1.getUserConfiguration().isDisplayJobOnPrefix()) {
                inventory.setItem(i, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                        .setName("&fGracz: " + user1.getUserConfiguration().getNicknameColor() + user1.getName())
                        .setLore("&7Pracuje jako &f" + jobType.getShortName(),
                                "&f" + user1.getCharacter().getJob().getLevel() + " poziom pracy")
                        .setSkullOwner(user1.getName())
                        .toItemStack());
                i++;
            }
        }

        inventory.display(player);

    }

}
