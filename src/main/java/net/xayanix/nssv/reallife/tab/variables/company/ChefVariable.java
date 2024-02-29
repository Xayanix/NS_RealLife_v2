package net.xayanix.nssv.reallife.tab.variables.company;

import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.entity.Player;
import pl.xayanix.nssv.tab.variables.IVariable;

public class ChefVariable extends IVariable {

    public ChefVariable() {
        super("CCHEF");
    }

    @Override
    public String getReplacement(Player player) {
        User user = UserManager.getUser(player);
        if (user == null)
            return "BRAK";
        if (user.getCharacter().getCompany() == null)
            return "BRAK";

        return "" + user.getCharacter().getCompany().getBoss();
    }
}
