package net.xayanix.nssv.reallife.tab.variables.company;

import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import pl.xayanix.nssv.tab.variables.IVariable;

public class NameVariable extends IVariable {

    public NameVariable() {
        super("CNAME");
    }

    @Override
    public String getReplacement(Player player) {
        User user = UserManager.getUser(player);
        if (user == null)
            return "BRAK";
        if (user.getCharacter().getCompany() == null)
            return "BRAK";

        return "" + user.getCharacter().getCompany().getName();
    }
}
