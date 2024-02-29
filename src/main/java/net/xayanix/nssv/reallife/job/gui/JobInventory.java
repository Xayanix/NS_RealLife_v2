package net.xayanix.nssv.reallife.job.gui;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.reallife.character.CharacterInfoInventory;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class JobInventory implements CustomInventoryAction {

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        int i = 18;
        for(JobType jobType : JobType.values()){
            if(inventoryClickEvent.getSlot() == i){
                if(inventoryClickEvent.getClick() == ClickType.LEFT) {
                    JobManager.setJob(user, jobType);
                    player.closeInventory();
                    return;
                }

                if(JobManager.getPlayersArmountWithJob(jobType) > 0)
                    JobPlayerInventory.display(player, jobType);
                return;
            }
            i++;
        }
    }

    public static void display(Player player){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&cWybierz prace", 6, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack(), new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        inventory.setAction(new JobInventory());

        int i = 18;
        for(JobType jobType : JobType.values()) {
            ItemBuilder ib = new ItemBuilder(jobType.getIcon())
                    .setName("&7Praca:&f " + jobType.getFullName())
                    .setLore((jobType.getReward() > 0 ? "&7Zarobek:&f " + jobType.getReward() + " zlotych&7 za &f" + jobType.getRewardFor() : "&7Ta praca nie otrzymuje wyplat."),
                            "&7Szansa na zdobycie punktu pracy:&f " + jobType.getPointChance() + "%",
                            "&7Opis pracy:&f " + jobType.getDescription());

            if(jobType.getJobCommands().size() > 0) {
                ib.addLore(" ");
                ib.addLore("&7Komendy pracy: ");
                for(String command : jobType.getJobCommands()){
                    ib.addLore("  &8»&f " + command);
                }

            }

            if(JobManager.getPlayersArmountWithJob(jobType) > 0)
                ib.addLore("", "&aKliknij PPM, aby wyswietlic liste graczy z ta praca.");

            inventory.setItem(i, ib.toItemStack());
            i++;
        }

        inventory.display(player);

    }

}
