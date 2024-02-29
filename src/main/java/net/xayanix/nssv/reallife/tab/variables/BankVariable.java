package net.xayanix.nssv.reallife.tab.variables;

import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import pl.xayanix.nssv.tab.variables.IVariable;

public class BankVariable extends IVariable {

    public BankVariable() {
        super("BANK");
    }

    @Override
    public String getReplacement(Player player) {
        User user = UserManager.getUser(player);
        if (user == null)
            return "BRAK";

        return "" + user.getCharacter().getMoney().getBank();
    }
}
