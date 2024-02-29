package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PayCommand extends BukkitCommand {

    public PayCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        Player player = (Player) arg0;
        User user = UserManager.getUser(player);

        if(arg3.length != 2){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /plac <gracz> <kwota>");
            return false;
        }

        String target = arg3[0];
        if(!StringUtil.isInteger(arg3[1])){
            ChatUtil.sendMessage(arg0, "&8#&c Podana kwota jest nieprawidlowa.");
            return false;
        }
        int money = Integer.valueOf(arg3[1]);

        if(money <= 0 || money > 100000){
            ChatUtil.sendMessage(arg0, "&8#&c Minimalna kwota platnosci to 1 zl a maksymalna 100.000 zl.");
            return false;
        }

        Player player_target = Bukkit.getPlayerExact(target);
        if(player_target == player){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /plac <gracz> <kwota>");
            return false;
        }

        if(player_target == null){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz jest offline.");
            return false;
        }

        if(PlayerUtil.distanceBetweenPlayers(player, player_target) > 8) {
            ChatUtil.sendMessage(arg0, "&8#&c Jestes za daleko od tego gracza.");
            return false;
        }

        if(!user.isCooldownFinished("placcmd", TimeUnit.SECONDS.toMillis(15))){
            ChatUtil.sendMessage(arg0, "&8#&c Mozesz placic raz na 15 sekund.");
            return false;
        }

        if(!EconomyManager.getInstance().takeWallet(user, money)){
            return false;
        }

        User user_target = UserManager.getUser(player_target);
        user_target.getCharacter().getMoney().addToWallet(money);

        ChatUtil.sendMessage(arg0, "&7#&7 Przekazales&f " + money + " zlotych&7 graczowi &f" + user_target.getName() + "&7.");
        ChatUtil.sendMessage(player_target, "&7#&7 Otrzymales&f " + money + " zlotych&7 od &f" + user.getName() + "&7.");

        //new IC(ChatSeparator.ME_COLOR + arg0.getName() + " wyciaga pieniadze i podaje je " + user_target.getName(), user).me();

        return true;
    }
}
