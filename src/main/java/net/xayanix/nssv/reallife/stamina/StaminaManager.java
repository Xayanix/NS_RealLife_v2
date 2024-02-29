package net.xayanix.nssv.reallife.stamina;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.logger.Logger;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.DoubleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import java.util.concurrent.TimeUnit;

public class StaminaManager extends PacketHandler {

    @Getter
    private static StaminaManager instance;

    public StaminaManager() {
        instance = this;
        PacketListenerAPI.addPacketHandler(this);
    }

    @Override
    public void onSend(SentPacket sentPacket) {

    }

    @Override
    public void onReceive(ReceivedPacket receivedPacket) {
        Player player = receivedPacket.getPlayer();
        if(player == null)
            return;

        if(receivedPacket.getPacket() instanceof PacketPlayInSteerVehicle) {
            User user = UserManager.getUser(player);
            if(user.getCharacter().getStamina().getStaminaWorker() == null)
                return;



            PacketPlayInSteerVehicle packetPlayInSteerVehicle = (PacketPlayInSteerVehicle) receivedPacket.getPacket();
            if(packetPlayInSteerVehicle.b() > 0){
                if(!user.isCooldownFinished("staminapacketspam", 100))
                    return;
                if(user.isCooldownFinished("stamina", (user.isVip() ? 50 : TimeUnit.SECONDS.toMillis(15)))) {
                    this.addWorkPoint(user);
                    for(Player players : Bukkit.getOnlinePlayers())
                        players.spigot().playEffect(player.getLocation(), Effect.CLOUD, 0, 0, 0, 0, 0, 1, 2, 16);
                }
            } else user.cancelCooldown("stamina");
        }
    }

    public ArmorStand getNewStaminaWorker(Location location){
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        armorStand.setCustomNameVisible(false);
        armorStand.setCustomName("STAMINAWORKER");
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);

        return armorStand;
    }

    public void work(User user){
        Player player = user.getPlayer();
        ArmorStand armorStand = getNewStaminaWorker(player.getLocation().add(0.0, -1, 0.0));

        user.getCharacter().getStamina().setStaminaWorker(armorStand);
        armorStand.addPassenger(player);

    }

    public void addWorkPoint(User user){
        Stamina stamina = user.getCharacter().getStamina();
        if(stamina.getCurrentStamina() >= 10 && stamina.getProgress() >= 100){
            user.sendActionBar("&cPosiadasz juz maksymalna mozliwa sile postaci.");
            return;
        }

        stamina.setProgress(stamina.getProgress() + 0.25);

        int green = (int) Math.round(stamina.getProgress());
        int red = 100 - green;

        String actionBar = "&a";

        for(int i = 0; i < green; i++)
            actionBar = actionBar + "|";
        actionBar = actionBar + "&8";
        for(int i = 0; i < red; i++)
            actionBar = actionBar + "|";

        if(stamina.getProgress() >= 100) {
            stamina.setProgress(0);
            stamina.setCurrentStamina(stamina.getCurrentStamina() + 1);
            stamina.setHealth(user);
        }

        user.sendActionBar("&9Postep cwiczen: " + actionBar + "&9. [" + stamina.getCurrentStamina() + "/10 poziom, " + DoubleUtil.format(stamina.getProgress()) + "%]");
    }

    public void destroyAll(){
        int remove = 0;
        for(World world : Bukkit.getWorlds()){
            for(Entity entity : world.getEntities()){
                if(entity.getType() == EntityType.ARMOR_STAND){
                    ArmorStand armorStand = (ArmorStand) entity;
                    if(armorStand.getCustomName() != null && armorStand.getCustomName().equalsIgnoreCase("STAMINAWORKER")) {
                        armorStand.remove();
                        remove++;
                    }
                }
            }
        }

        if(remove > 0)
            Logger.info("Usunieto " + remove + " stamina workerow.");

    }

    public void destroy(User user){
        if(user.getCharacter().getStamina().getStaminaWorker() != null){
            user.getCharacter().getStamina().getStaminaWorker().remove();
            user.getCharacter().getStamina().setStaminaWorker(null);
        }
    }

}
