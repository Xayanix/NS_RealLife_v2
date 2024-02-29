package net.xayanix.nssv.reallife.listeners;

import com.Acrobot.ChestShop.Events.PreTransactionEvent;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.ChatFields;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Calendar;

public class PreTransactionListener implements Listener {

    public PreTransactionListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PreTransactionEvent event) {
        if(event.getOwnerAccount() == null || event.getOwnerAccount().getName() == null)
            return;

        if(event.getClient().hasPermission("shopbypass"))
            return;

        if(event.getOwnerAccount().getName().equalsIgnoreCase("NSShop")) {
            final Calendar c = Calendar.getInstance();
            final int hr = c.get(11);
            final int dayOfWeek = c.get(7);

            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    if(hr >= 9 && hr <= 19)
                        return;

                    event.setCancelled(PreTransactionEvent.TransactionOutcome.OTHER);
                    sendMessage(event.getClient());
                    break;
                case Calendar.SATURDAY:
                    if(hr >= 6 && hr <= 21)
                        return;

                    event.setCancelled(PreTransactionEvent.TransactionOutcome.OTHER);
                    sendMessage(event.getClient());
                    break;
                default:
                    if(hr >= 6 && hr <= 22)
                        return;

                    event.setCancelled(PreTransactionEvent.TransactionOutcome.OTHER);
                    sendMessage(event.getClient());
                    break;
            }

        }

    }

    private void sendMessage(Player player){
        ChatUtil.sendMessage(player, ChatFields.SEPARATOR);
        ChatUtil.sendMessage(player, " ");
        ChatUtil.sendMessage(player, "  &7Godziny otwarcia sklepów:");
        ChatUtil.sendMessage(player, "  &fPoniedzialek - Piatek: 6:00 - 22:00");
        ChatUtil.sendMessage(player, "  &fSobota: 6:00 - 21:00");
        ChatUtil.sendMessage(player, "  &fNiedziela: 9:00 - 19:00");
        ChatUtil.sendMessage(player, "  &cSklep zamkniety? Handluj z graczami!");
        ChatUtil.sendMessage(player, " ");
        ChatUtil.sendMessage(player, ChatFields.SEPARATOR);
    }

}
