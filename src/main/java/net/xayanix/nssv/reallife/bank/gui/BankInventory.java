package net.xayanix.nssv.reallife.bank.gui;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.reallife.bank.BankAction;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.WarpManager;
import net.xayanix.nssv.reallife.warp.Warps;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BankInventory implements CustomInventoryAction {

    private BankAction action;

    public BankInventory(BankAction action){
        this.action = action;
    }

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {

        inventoryClickEvent.setCancelled(true);
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);

        int slot = inventoryClickEvent.getSlot();
        player.closeInventory();

        int money = 100;
        for(int i = 0; i < 9; i++){
            if(slot == i){
                if(this.action == BankAction.WITHDRAW)
                    EconomyManager.getInstance().withdrawToWallet(user, money);
                else EconomyManager.getInstance().depositToBank(user, money);
                return;
            }
            money+= 100;
        }

        money = 250;
        for(int i = 9; i < 18; i++){
            if(slot == i){
                if(this.action == BankAction.WITHDRAW)
                    EconomyManager.getInstance().withdrawToWallet(user, money);
                else EconomyManager.getInstance().depositToBank(user, money);
                return;
            }
            money+= 250;
        }

        money = 500;
        for(int i = 18; i < 27; i++){
            if(slot == i){
                if(this.action == BankAction.WITHDRAW)
                    EconomyManager.getInstance().withdrawToWallet(user, money);
                else EconomyManager.getInstance().depositToBank(user, money);
                return;
            }
            money+= 500;
        }

        money = 1000;
        for(int i = 27; i < 36; i++){
            if(slot == i){
                if(this.action == BankAction.WITHDRAW)
                    EconomyManager.getInstance().withdrawToWallet(user, money);
                else EconomyManager.getInstance().depositToBank(user, money);
                return;
            }
            money+= 1000;
        }

        money = 5000;
        for(int i = 36; i < 45; i++){
            if(slot == i){
                if(this.action == BankAction.WITHDRAW)
                    EconomyManager.getInstance().withdrawToWallet(user, money);
                else EconomyManager.getInstance().depositToBank(user, money);
                return;
            }
            money+= 5000;
        }

        money = 125000;
        for(int i = 45; i < 54; i++){
            if(slot == i){
                if(this.action == BankAction.WITHDRAW)
                    EconomyManager.getInstance().withdrawToWallet(user, money);
                else EconomyManager.getInstance().depositToBank(user, money);
                return;
            }
            money+= 125000;
        }
    }

    public static void display(Player player, BankAction action){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&a" + action.getDesc() + " GOTOWKI", 6, player);
        inventory.setAction(new BankInventory(action));

        long umoney = (action == BankAction.WITHDRAW ? user.getCharacter().getMoney().getBank() : user.getCharacter().getMoney().getWallet());

        int money = 100;
        for(int i = 0; i < 9; i++){
            if(umoney >= money) {
                inventory.setItem(i, new ItemBuilder(Material.COAL)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&aKliknij, aby zrealizowac transakcje.")
                        .toItemStack());
            } else
                inventory.setItem(i, new ItemBuilder(Material.BARRIER)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&cBrak srodkow do sfinalizowania tej transakcji.")
                        .toItemStack());
            money+= 100;
        }

        money = 250;
        for(int i = 9; i < 18; i++){
            if(umoney >= money) {
                inventory.setItem(i, new ItemBuilder(Material.COAL)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&aKliknij, aby zrealizowac transakcje.")
                        .toItemStack());
            } else
                inventory.setItem(i, new ItemBuilder(Material.BARRIER)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&cBrak srodkow do sfinalizowania tej transakcji.")
                        .toItemStack());
            money+= 250;
        }

        money = 500;
        for(int i = 18; i < 27; i++){
            if(umoney >= money) {
                inventory.setItem(i, new ItemBuilder(Material.COAL)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&aKliknij, aby zrealizowac transakcje.")
                        .toItemStack());
            } else
                inventory.setItem(i, new ItemBuilder(Material.BARRIER)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&cBrak srodkow do sfinalizowania tej transakcji.")
                        .toItemStack());
            money+= 500;
        }

        money = 1000;
        for(int i = 27; i < 36; i++){
            if(umoney >= money) {
                inventory.setItem(i, new ItemBuilder(Material.COAL)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&aKliknij, aby zrealizowac transakcje.")
                        .toItemStack());
            } else
                inventory.setItem(i, new ItemBuilder(Material.BARRIER)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&cBrak srodkow do sfinalizowania tej transakcji.")
                        .toItemStack());
            money+= 1000;
        }

        money = 5000;
        for(int i = 36; i < 45; i++){
            if(umoney >= money) {
                inventory.setItem(i, new ItemBuilder(Material.COAL)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&aKliknij, aby zrealizowac transakcje.")
                        .toItemStack());
            } else
                inventory.setItem(i, new ItemBuilder(Material.BARRIER)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&cBrak srodkow do sfinalizowania tej transakcji.")
                        .toItemStack());
            money+= 5000;
        }

        money = 125000;
        for(int i = 45; i < 54; i++){
            if(umoney >= money) {
                inventory.setItem(i, new ItemBuilder(Material.COAL)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&aKliknij, aby zrealizowac transakcje.")
                        .toItemStack());
            } else
                inventory.setItem(i, new ItemBuilder(Material.BARRIER)
                        .setName("&7" + action.getDesc() + ":&f " + money + " zlotych")
                        .setLore("&cBrak srodkow do sfinalizowania tej transakcji.")
                        .toItemStack());
            money+= 125000;
        }

        inventory.display(player);

    }

}
