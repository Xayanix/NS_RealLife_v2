package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.chat.IC;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.scoreboard.ScoreboardManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WantedCommand extends BukkitCommand {

    public WantedCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);
        JobType type = user.getCharacter().getJob().getType();

        if(type != JobType.PRAWNIK && type != JobType.POLICJANT && type != JobType.LOWCA_GLOW) {
            ChatUtil.sendMessage(arg0, "&8#&c Komenda dostepna wylacznie dla prawnikow, policjantow i lowcow glow.");
            return false;
        }


        List<User> wanted = WantedLevelManager.getWantedUsers();
        if(wanted.size() == 0){
            user.sendMessage("&8#&c Nikt nie jest poszukiwany.");
            return false;
        }

        String wantedstr = "";
        for(User user1 : wanted)
            wantedstr = wantedstr + ", " + user1.getName() + " (WL: " + user1.getCharacter().getWantedlevel() + ")";
        wantedstr = wantedstr.replaceFirst(", ", "");

        user.sendMessage("&8#&c Najbardziej poszukiwani przestepcy:&7 " + wantedstr);

        ScoreboardManager.ScoreboardLine[] scoreboardLines = new ScoreboardManager.ScoreboardLine[wanted.size()];
        for(int i = 0; i < wanted.size(); i++) {
            User u = wanted.get(i);
            scoreboardLines[i] = new ScoreboardManager.ScoreboardLine(u.getUserConfiguration().getNicknameColor() + u.getName() + " &7(" + u.getCharacter().getWantedlevel() + " WL)", u.getCharacter().getWantedlevel());
        }

        ScoreboardManager.getInstance().display(user, 10, "&cPoszukiwani przestepcy", scoreboardLines);

        return true;
    }
}
