package net.xayanix.nssv.reallife.character;

import net.xayanix.nssv.core.interfaces.CustomInventoryAction;
import net.xayanix.nssv.core.objects.CustomInventory;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserConfiguration;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CharacterConfigurationInventory implements CustomInventoryAction{

    @Override
    public void handle(InventoryClickEvent inventoryClickEvent) {
        Player player = (Player) inventoryClickEvent.getWhoClicked();
        User user = UserManager.getUser(player);
        int slot = inventoryClickEvent.getSlot();
        UserConfiguration userConfiguration = user.getUserConfiguration();
        inventoryClickEvent.setCancelled(true);

        if(slot == 18){
            userConfiguration.setDisplayLevelOnChat(!userConfiguration.isDisplayLevelOnChat());
            display(player);
        } else if(slot == 19){
            userConfiguration.setDisplayCompanyOnChat(!userConfiguration.isDisplayCompanyOnChat());
            display(player);
        } else if(slot == 20){
            userConfiguration.setReceiveAdvertisments(!userConfiguration.isReceiveAdvertisments());
            display(player);
        } else if(slot == 21){
            userConfiguration.setNicknameColorsOnTab(!userConfiguration.isNicknameColorsOnTab());
            display(player);
        } else if(slot == 22){
            if(user.getCharacter().getLevel() < 3){
                user.sendMessage("&8&#c Musisz miec minimum 3 level postaci, aby moc wlaczyc PvP.");
                return;
            }

            userConfiguration.setPvpState(!userConfiguration.isPvpState());
            if(userConfiguration.isPvpState()){
                ChatUtil.sendMessage(player, "&8#&7 Od teraz mozesz atakowac innych graczy z tagiem &6[WŁ. PVP]&7.");
                user.sendTitle("&6[WŁĄCZONE PVP]", "&7", 20, 40, 20);
                player.closeInventory();
                return;
            }

            display(player);

        }

        if(user.isVip()) {
            if(slot == 27){
                userConfiguration.setDisplayJobOnPrefix(!userConfiguration.isDisplayJobOnPrefix());
                display(player);
            }
        }



    }

    public static void display(Player player){
        User user = UserManager.getUser(player);
        CustomInventory inventory = new CustomInventory("&7Postac:&f " + player.getName(), 6, player);
        inventory.background(new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 15).setName(" ").toItemStack(), new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (short) 7).setName(" ").toItemStack());
        inventory.setAction(new CharacterConfigurationInventory());

        inventory.setItem(18, new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                .setName("&7Wyswietlanie poziomu postaci na czacie")
                .setLore("&7Status: " + (user.getUserConfiguration().isDisplayLevelOnChat() ? "&aWLACZONE" : "&cWYLACZONE"))
                .setDurability(user.getUserConfiguration().isDisplayLevelOnChat() ? (short) 5 : (short) 14)
                .toItemStack());

        inventory.setItem(19, new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                .setName("&7Wyswietlanie nazwy firmy na czacie")
                .setLore("&7Status: " + (user.getUserConfiguration().isDisplayCompanyOnChat() ? "&aWLACZONE" : "&cWYLACZONE"))
                .setDurability(user.getUserConfiguration().isDisplayCompanyOnChat() ? (short) 5 : (short) 14)
                .toItemStack());

        inventory.setItem(20, new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                .setName("&7Wyswietlanie ogloszen")
                .setLore("&7Status: " + (user.getUserConfiguration().isReceiveAdvertisments() ? "&aWLACZONE" : "&cWYLACZONE"))
                .setDurability(user.getUserConfiguration().isReceiveAdvertisments() ? (short) 5 : (short) 14)
                .toItemStack());

        inventory.setItem(21, new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                .setName("&7Wyswietlanie koloru nickow w topce na TAB")
                .setLore("&7Status: " + (user.getUserConfiguration().isNicknameColorsOnTab() ? "&aWLACZONE" : "&cWYLACZONE"))
                .setDurability(user.getUserConfiguration().isNicknameColorsOnTab() ? (short) 5 : (short) 14)
                .toItemStack());

        inventory.setItem(22, new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                .setName("&7Status PvP (gracze beda mogli atakowac Cie bez interakcji)")
                .setLore("&7Status: " + (user.getUserConfiguration().isPvpState() ? "&aWLACZONE" : "&cWYLACZONE"))
                .setDurability(user.getUserConfiguration().isPvpState() ? (short) 5 : (short) 14)
                .toItemStack());


        inventory.setItem(27, new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                .setName("&6[VIP] &7Wyswietlanie pracy przed nickiem")
                .setLore("&7Status: " + (user.getUserConfiguration().isDisplayJobOnPrefix() ? "&aWLACZONE" : "&cWYLACZONE"))
                .setDurability(user.getUserConfiguration().isDisplayJobOnPrefix() ? (short) 5 : (short) 14)
                .toItemStack());




        inventory.display(player);

    }

}
