package net.xayanix.nssv.reallife.resourcepack;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.actionbar.ActionBarManager;
import net.xayanix.nssv.reallife.stamina.Stamina;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.DoubleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import java.util.concurrent.TimeUnit;

public class ResourceManager extends PacketHandler {

    @Getter
    private static ResourceManager instance;

    public ResourceManager() {
        instance = this;
        PacketListenerAPI.addPacketHandler(this);
    }

    public void set(Player player){
        ActionBarManager.set(player, "&aWysyłamy Ci tekstury, zaakceptuj je jesli chcesz w pelni cieszyc sie rozgrywka.");
        ChatUtil.sendMessage(player, "&8#&a Wysylamy tekstury do Twojej gry, zaakceptuj je, jesli chcesz w pelni cieszyc sie rozgrywka.");
        ChatUtil.sendMessage(player, "&8#&7 Jesli nie widzisz tekstur to mozesz je pobrac manualnie:&f https://www.nssv.pl/resourcepacks/rlv2.zip");
        player.setResourcePack("https://www.nssv.pl/resourcepacks/rlv2.zip");
    }

    @Override
    public void onSend(SentPacket sentPacket) {

    }

    @Override
    public void onReceive(ReceivedPacket receivedPacket) {
        Player player = receivedPacket.getPlayer();
        if(player == null)
            return;

        if(receivedPacket.getPacket() instanceof PacketPlayInResourcePackStatus) {
            User user = UserManager.getUser(player);
            PacketPlayInResourcePackStatus resourcePackStatus = (PacketPlayInResourcePackStatus) receivedPacket.getPacket();

            switch (resourcePackStatus.status){
                case ACCEPTED:
                    user.sendMessage("&8#&a Pomyslnie zaakceptowales serwerowe tekstury.");
                    return;
                case SUCCESSFULLY_LOADED:
                    user.getUserTempData().setServerTextures(true);
                    user.sendMessage("&8#&a Serwerowe tekstury zostaly zaladowane.");
                    break;
                case DECLINED:
                    user.sendMessage("&8#&c Bez serwerowych tekstur nie bedziesz mogl sie cieszyc w pelni rozgrywka.");
                    user.sendMessage("&8#&c Tekstury serwerowe sa wymagane do poprawnego wyswietlania pojazdow i broni.");
                    user.getUserTempData().setServerTextures(false);
                    break;
                case FAILED_DOWNLOAD:
                    user.getUserTempData().setServerTextures(false);
                    user.sendMessage("&8#&c Wystapil problem podczas pobierania tekstur, sprobuj pobrac je manualnie ze strony:&6 https://www.nssv.pl/resourcepacks/rlv2.zip");
                    break;
            }

        }
    }

}
