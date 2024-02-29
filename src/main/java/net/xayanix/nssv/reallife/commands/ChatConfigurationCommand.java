package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.character.Character;
import net.xayanix.nssv.reallife.character.CharacterConfigurationInventory;
import net.xayanix.nssv.reallife.character.CharacterInfoInventory;
import net.xayanix.nssv.reallife.job.Job;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.misc.ItemContainer;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.RegexUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;

public class ChatConfigurationCommand extends BukkitCommand {

    public ChatConfigurationCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {

        User user = UserManager.getUser(arg0.getName());
        CharacterConfigurationInventory.display(user.getPlayer());

        return true;
    }
}
