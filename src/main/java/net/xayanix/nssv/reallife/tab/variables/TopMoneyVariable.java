package net.xayanix.nssv.reallife.tab.variables;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.tab.TabManager;
import net.xayanix.nssv.reallife.tab.TabMoney;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.xayanix.nssv.tab.variables.IVariable;

public class TopMoneyVariable extends IVariable {

    private int pos;

    public TopMoneyVariable(int pos) {
        super("GUSER-" + pos);
        this.pos = pos;
    }


    @Override
    public String getReplacement(Player player) {
        User user = UserManager.getUser(player);
        int count = 1;
        for (TabMoney money : TabManager.getMoneyList()) {
            if (count == this.pos) {
                long m = money.getMoney() / 1000;
                Player target = Bukkit.getPlayerExact(money.getName());
                return ChatUtil.fixColors((target != null ? "&a✔ " : "") + (user.getUserConfiguration().isNicknameColorsOnTab() ? money.getColor() : "") + (money.getName().equalsIgnoreCase(player.getName()) ? "&l" + money.getName() : money.getName()) + "&8 (" + m + "K)");
            }

            count++;
        }


        return "";
    }
}
