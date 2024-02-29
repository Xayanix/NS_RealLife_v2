package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.reallife.misc.ChatFields;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class JobCommand extends BukkitCommand {

    public JobCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);

        user.sendMessage(ChatFields.SEPARATOR);
        user.sendMessage("&8#&7 Pracujesz jako &f" + user.getCharacter().getJob().getType().getFullName() + "&7.");
        user.sendMessage("&8#&7 Twoje aktualne postepy w pracy:");
        user.sendMessage("&8#&7 Poziom pracy: &f" + user.getCharacter().getJob().getLevel() + " Lv (" + user.getCharacter().getJob().getPoints() + "/" + user.getCharacter().getJob().getPointsToNextLevel() + ")");
        user.sendMessage("&8#&7 Aktualny zarobek do nastepnej wyplaty &2" + user.getCharacter().getJob().getEarned() +  " zlotych&7.");
        user.sendMessage("&8#&7 Czat pracy:&f !wiadomosc");
        user.sendMessage(ChatFields.SEPARATOR);

        return true;
    }
}
