package net.xayanix.nssv.reallife.commands;

import me.zombie_striker.qg.exp.cars.ExpansionHandler;
import me.zombie_striker.qg.exp.cars.VehicleEntity;
import me.zombie_striker.qg.exp.cars.api.QualityArmoryVehicles;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GetInCarCommand extends BukkitCommand {

    public GetInCarCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }
    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(!(arg0 instanceof Player)) return false;

        Player player = (Player) arg0;

        Entity entity = null;
        double distanceSquared = Double.MAX_VALUE;
        for (final Entity entity2 : player.getNearbyEntities(6, 6, 6)) {
            if (entity2.getCustomName() != null && QualityArmoryVehicles.isVehicle(entity2) && distanceSquared > entity2.getLocation().distanceSquared(player.getLocation())) {
                entity = entity2;
                distanceSquared = entity2.getLocation().distanceSquared(player.getLocation());
            }
        }

        VehicleEntity vehicleEntity = QualityArmoryVehicles.getVehicleEntity((ArmorStand) entity);

        if (entity != null) {
            QualityArmoryVehicles.setAddPassager(entity, ExpansionHandler.carTypes.get(QualityArmoryVehicles.getCarIDFromEntity(entity)), player, vehicleEntity.getFirstOpenSeat(), vehicleEntity);
        } else ChatUtil.sendMessage(arg0, "&8#&c Brak pojazdow w poblizu do ktorych mozesz wsiasc.");

        return true;
    }
}
