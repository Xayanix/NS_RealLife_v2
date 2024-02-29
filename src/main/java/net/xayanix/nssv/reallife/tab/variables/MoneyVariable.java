package net.xayanix.nssv.reallife.tab.variables;

import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import pl.xayanix.nssv.tab.variables.IVariable;

public class MoneyVariable extends IVariable {

    public MoneyVariable() {
        super("MONEY");
    }

    @Override
    public String getReplacement(Player player) {
        User user = UserManager.getUser(player);
        if (user == null)
            return "BRAK";

        return "" + user.getCharacter().getMoney().getWallet();
    }
}
