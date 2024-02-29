package net.xayanix.nssv.reallife.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IEssentials;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.character.Character;
import net.xayanix.nssv.reallife.character.CharacterInfoInventory;
import net.xayanix.nssv.reallife.hide.HideManager;
import net.xayanix.nssv.reallife.job.Job;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.logger.Logger;
import net.xayanix.nssv.reallife.misc.ItemContainer;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import net.xayanix.nssv.reallife.utils.RegexUtil;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateCharCommand extends BukkitCommand {

    public CreateCharCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {

        User user = UserManager.getUser(arg0.getName());
        if(!user.isNew()){
            if(arg3.length == 1){
                User user1 = UserManager.getUser(arg3[0]);
                if(user1 == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
                    return false;
                }

                CharacterInfoInventory.display(user1, user.getPlayer());
                return false;
            }
            CharacterInfoInventory.display(user, user.getPlayer());
            return false;
        }
/*
        if(arg3.length != 3){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /konto <imie> <nazwisko> <wiek>");
            return false;
        }

        String name = arg3[0];
        String surname = arg3[1];

        if(!StringUtil.isInteger(arg3[2])) {
            ChatUtil.sendMessage(arg0, "&8#&c Wiek&6 " + arg3[2] + "&c nie jest prawidlowa liczba.");
            return false;
        }

        int wiek = Integer.valueOf(arg3[2]);

        if(!RegexUtil.isCharName(name) || !RegexUtil.isCharName(surname)) {
            ChatUtil.sendMessage(arg0, "&8#&c Imie lub nazwisko postaci jest nieprawidlowe.");
            ChatUtil.sendMessage(arg0, "&8#&c Mozesz uzywac tylko liter od A do Z i min. 3 max. 16 znakow.");
            return false;
        }

        if(wiek > 110 || wiek <= 0){
            ChatUtil.sendMessage(arg0, "&8#&c Wiek Twojej postaci moze znajdowac sie w przedziale od 1 do 110 lat.");
            return false;
        }

        user.setCharacter(new Character(user, name, surname, wiek, new Job(JobType.BEZROBOTNY, 1, 0)));
        user.setNew(false);
        user.getPlayer().teleport(LocationList.SPAWN);
        user.sendTitle("", "&aKonto utworzone.", 0, 30, 20);
        ChatUtil.sendMessage(arg0, "&8#&a Postac pomyslnie utworzona.");

        user.insert();

        PlayerUtil.enablePlayerTracker(user.getPlayer());
        user.getPlayer().getInventory().addItem(ItemContainer.TUTORIAL_BOOK);

        IEssentials ess = (IEssentials) Bukkit.getPluginManager().getPlugin("Essentials");
        ess.getUser(user.getName()).setHome("home", LocationList.SPAWN);
        */
        return true;
    }
}
