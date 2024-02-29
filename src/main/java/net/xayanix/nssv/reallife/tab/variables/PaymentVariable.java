package net.xayanix.nssv.reallife.tab.variables;

import net.xayanix.nssv.reallife.payday.PaydayManager;
import org.bukkit.entity.Player;
import pl.xayanix.nssv.tab.variables.IVariable;

public class PaymentVariable extends IVariable {

    public PaymentVariable() {
        super("PAYMENT");
    }

    @Override
    public String getReplacement(Player player) {
        return String.valueOf(PaydayManager.getInstance().getPaydayMinutes() - (System.currentTimeMillis() - PaydayManager.getInstance().getLastPaydayTimestamp()) / 1000 / 60);
    }


}
