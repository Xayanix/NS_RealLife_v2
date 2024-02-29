package net.xayanix.nssv.reallife.job.robber;

import lombok.AllArgsConstructor;
import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@AllArgsConstructor
public class RobInventory implements CustomInventoryAction {

    private Player victim;
    private int slot;
    private final long time = System.currentTimeMillis() + 1000;

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        player.closeInventory();

        if(inventoryClickEvent.getSlot() == this.slot){
            RobManager.rob(player, victim);
            return;
        }

        user.sendMessage("&8#&c Nie udalo Ci sie okrasc tego gracza.");
    }

    public static void display(Player player, Player victim){
        CustomInventory inventory = new CustomInventory("&cZlodziej: " + player.getName(), 6, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack(), new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        int slot = RandomUtil.getRandom().nextInt(54);
        inventory.setAction(new RobInventory(victim, slot));

        inventory.setItem(slot, new ItemBuilder(Material.LEASH, 1)
                .setName("&aKliknij tutaj, aby okrasc!")
                .toItemStack());


        inventory.display(player);

        Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), player::closeInventory, 40);

    }

}
