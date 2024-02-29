package net.xayanix.nssv.reallife.character;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.inventory.actions.PanelAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.DoubleUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CharacterInfoInventory implements CustomInventoryAction{

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
    }

    public static void display(User user, Player display){
        CustomInventory inventory = new CustomInventory("&7Postac:&f " + user.getName(), 6, display);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack(), new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        inventory.setAction(new CharacterInfoInventory());

        inventory.setItem(18, new ItemBuilder(Material.SKULL_ITEM, 1, (short) 3)
                .setName("&7Imie postaci:&f " + user.getCharacter().getName())
                .setLore("&7Nazwisko postaci:&f " + user.getCharacter().getSurname(),
                        "&7Wiek postaci:&f " + user.getCharacter().getAge() + " lat")
                .setSkullOwner(user.getName())
                .toItemStack());

        ItemBuilder ib = new ItemBuilder(Material.WOOD_PICKAXE, 1)
                .setName("&7Praca:&f " + user.getCharacter().getJob().getType().getFullName())
                .setLore("&7Poziom pracy:&f " + user.getCharacter().getJob().getLevel() + " Lv (" + user.getCharacter().getJob().getPoints() + "/" + user.getCharacter().getJob().getPointsToNextLevel() + ")");

        if(user.getCharacter().getJobLevelMap().size() > 0){
            ib.addLore("", "&7Poziomy w innych pracach:");
            for(JobType jobType : user.getCharacter().getJobLevelMap().keySet())
                ib.addLore("&f" + jobType.getShortName() + ": &7" + user.getCharacter().getJobLevelMap().get(jobType) + " Lv");
        }

        inventory.setItem(19, ib.toItemStack());

        inventory.setItem(20, new ItemBuilder(Material.PAPER, 1)
                .setName("&7Dokumenty")
                .setLore("&f" + user.getCharacter().getLicenses().toString().replace("[", "").replace("]", ""))
                .toItemStack());

        inventory.setItem(21, new ItemBuilder(Material.EMERALD, 1)
                .setName("&7Stan konta")
                .setLore("&7W portfelu: &2" + user.getCharacter().getMoney().getWallet() + " zlotych",
                        "&7W banku:&2 " + user.getCharacter().getMoney().getBank() + " zlotych")
                .toItemStack());

        inventory.setItem(22, new ItemBuilder(Material.REDSTONE_BLOCK, 1)
                .setName("&7Poziom poszukiwania")
                .setLore("&f" + user.getCharacter().getWantedlevel() + " Wanted Level")
                .toItemStack());

        inventory.setItem(23, new ItemBuilder(Material.IRON_INGOT, 1)
                .setName("&7Firma")
                .setLore("&f" + (user.getCharacter().getCompany() == null ? "Nie masz firmy." : user.getCharacter().getCompany().getName()))
                .toItemStack());

        inventory.setItem(24, new ItemBuilder(Material.DIAMOND, 1)
                .setName("&7Poziom postaci")
                .setLore("&f" + user.getCharacter().getLevel() + " Lv")
                .toItemStack());

        inventory.setItem(25, new ItemBuilder(Material.REDSTONE, 1)
                .setName("&7Respekt (do kolejnego poziomu)")
                .setLore("&f" + user.getCharacter().getRespect() + "/" + user.getCharacter().getRespectToNextLevel())
                .toItemStack());

        inventory.setItem(26, new ItemBuilder(Material.GOLD_NUGGET, 1)
                .setName("&7Slub")
                .setLore("&f" + (user.getCharacter().getMarry().equalsIgnoreCase("") ? "BRAK" : user.getCharacter().getMarry()))
                .toItemStack());

        inventory.setItem(27, new ItemBuilder(Material.BOOK, 1)
                .setName("&7Rodzina")
                .setLore("&f" + (user.getCharacter().getFamily() == null ? "Nie masz rodziny." : user.getCharacter().getFamily().getName()))
                .toItemStack());

        inventory.setItem(28, new ItemBuilder(Material.COOKED_CHICKEN, 1)
                .setName("&7Sila postaci")
                .setLore("&f" + user.getCharacter().getStamina().getCurrentStamina() + " poziom (" + DoubleUtil.format(user.getCharacter().getStamina().getProgress()) + "% do nastepnego)")
                .toItemStack());

        inventory.setItem(29, new ItemBuilder(Material.WOOL, 1)
                .setName("&7Serwerowe tekstury")
                .setLore(user.getUserTempData().isServerTextures() ? "&aAKTYWNE" : "&cNIEAKTYWNE")
                .toItemStack());


        inventory.display(display);

    }

}
