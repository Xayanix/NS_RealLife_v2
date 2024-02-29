package net.xayanix.nssv.reallife.backpack;

import lombok.Getter;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class BackPack {

    @Getter
    private transient Inventory backPackInventory;
    private int size;
    private String serializedInventory;

    public BackPack(User user){
        this.size = (!user.isVip() ? 27 : 54);
        this.backPackInventory = Bukkit.createInventory(null, this.size);
    }

    public void display(User user){
        user.getPlayer().openInventory(this.backPackInventory);
    }

    public void deserialize(){
        if(this.serializedInventory == null)
            return;

        try {
            ItemStack[] array = ItemUtil.itemStackArrayFromBase64(this.serializedInventory);
            this.backPackInventory = Bukkit.createInventory(null, this.size);
            this.backPackInventory.setContents(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serialize(){
        this.serializedInventory = ItemUtil.itemStackArrayToBase64(this.backPackInventory.getContents());
    }

}
