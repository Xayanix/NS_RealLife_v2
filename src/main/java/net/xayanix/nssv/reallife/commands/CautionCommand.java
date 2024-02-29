package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.character.Character;
import net.xayanix.nssv.reallife.economy.EconomyManager;
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

public class CautionCommand extends BukkitCommand {

    public CautionCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        User user = UserManager.getUser(arg0.getName());
        if(user.getCharacter().getJail() == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes w wiezieniu.");
            return false;
        }

        if(!user.getCharacter().getJail().isCaution()) {
            ChatUtil.sendMessage(arg0, "&8#&c Nie przewidziano mozliwosci wplacenia kaucji dla Ciebie.");
            return false;
        }

        if(EconomyManager.getInstance().takeWallet(user, user.getCharacter().getJail().getCautionPrice())) {
            user.getCharacter().setJail(null);
            user.getPlayer().teleport(LocationList.WIEZIENIE_WEJSCIE);
            ChatUtil.sendMessage(arg0, "&8#&a Wplaciles kaucje i wychodzisz z wiezienia.");
        }

        return true;
    }
}
