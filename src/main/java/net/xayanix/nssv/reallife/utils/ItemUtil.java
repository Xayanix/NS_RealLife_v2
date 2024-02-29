package net.xayanix.nssv.reallife.utils;

import net.xayanix.nssv.core.utils.RandomUtil;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemUtil {

    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static void recalculateDurability(Player player, ItemStack item)
    {
        if(item == null) return;
        if (item.getType().getMaxDurability() == 0) {
            return;
        }
        int enchantLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
        short d = item.getDurability();
        if (enchantLevel > 0)
        {
            if (100 / (enchantLevel + 1) > RandomUtil.getRandom().nextInt(100)) {
                if (d == item.getType().getMaxDurability())
                {
                    player.getInventory().clear(player.getInventory().getHeldItemSlot());
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                }
                else
                {
                    item.setDurability((short)(d + 1));
                }
            }
        }
        else if (d == item.getType().getMaxDurability())
        {
            player.getInventory().clear(player.getInventory().getHeldItemSlot());
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
        }
        else
        {
            item.setDurability((short)(d + 1));
        }
    }

}
