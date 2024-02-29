package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.lotto.LottoManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class LottoCommand extends BukkitCommand {

    public LottoCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 1){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /lotto <numerek 1-99>");
            return false;
        }

        if(!StringUtil.isInteger(arg3[0])){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /lotto <numerek 1-99>");
            return false;
        }

        Player player = (Player) arg0;
        if(LottoManager.getInstance().getRewardedIps().contains(player.getAddress().getAddress().getHostAddress())){
            ChatUtil.sendMessage(arg0, "&8#&c Juz bierzesz udzial w losowaniu, poczekaj do kolejnej wyplaty.");
            return false;
        }

        User user = UserManager.getUser(arg0.getName());
        int lotto = Integer.parseInt(arg3[0]);

        if(lotto < 1 || lotto > 99){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /lotto <numerek 1-99>");
            return false;
        }

        if(EconomyManager.getInstance().takeWallet(user, 100)){
            user.getCharacter().setLotto(lotto);
            user.sendMessage("&8#&b Zakupiles kupon lotto z numerem " + lotto + ".");
            LottoManager.getInstance().getRewardedIps().add(player.getAddress().getAddress().getHostAddress());
        }

        return true;
    }
}
