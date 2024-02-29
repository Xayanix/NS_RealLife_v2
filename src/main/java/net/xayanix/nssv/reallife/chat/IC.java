package net.xayanix.nssv.reallife.chat;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.misc.ChatFields;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class IC implements Runnable{

    @Getter
    private String[] message;

    @Getter
    private User user;

    @Getter
    private Hologram hologram;

    private static int split = 16;

    public IC(String message, User user){
        double add = 0;
        if(message.length() <= split)
            this.message = new String[]{message};
        else {
            List<String> list = Lists.newArrayList(Splitter.fixedLength(split).split(message));

            this.message = list.toArray(new String[list.size()]);

            for(int i = 0; i < this.message.length - 1; i++)
                this.message[i] = this.message[i] + " [...]";

            add = this.message.length * 0.1;

        }



        this.user = user;
        if(PlayerUtil.getNearbyPlayers(user.getPlayer(), 32).size() > 16){
            return;
        }

        this.hologram = HologramsAPI.createHologram(RealLife.getInstance(), user.getPlayer().getLocation().add(0.0, 2.5 + add, 0.0));

        Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), this, 60);





    }

    public IC(String message, Player sender){
        for(Player player : PlayerUtil.getNearbyPlayers(sender))
            ChatUtil.sendMessage(player, message);
    }

    public void me(){
        for(String message : this.message)
            this.hologram.appendTextLine(ChatUtil.fixColors(ChatFields.ME_COLOR + message));

    }

    @Override
    public void run() {
        this.hologram.delete();
    }
}
