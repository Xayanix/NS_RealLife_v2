package net.xayanix.nssv.reallife.listeners;

import es.pollitoyeye.vehicles.VehiclesMain;
import es.pollitoyeye.vehicles.enums.VehicleType;
import es.pollitoyeye.vehicles.events.VehicleEnterEvent;
import me.zombie_striker.qg.exp.cars.VehicleEntity;
import me.zombie_striker.qg.exp.cars.api.events.QAVVehicleEnterEvent;
import me.zombie_striker.qg.exp.cars.baseclasses.*;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.license.License;
import net.xayanix.nssv.reallife.license.LicenseManager;
import net.xayanix.nssv.reallife.speedometer.Speedometer;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VehicleEnterListener implements Listener {

    public VehicleEnterListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(QAVVehicleEnterEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);
        VehicleEntity vehicleEntity = event.getVe();
        AbstractVehicle abstractVehicle = vehicleEntity.getType();

        if(user.getCharacter().getJail() != null){
            event.setCancel(true);
            ChatUtil.sendMessage(player, "&8#&c Siedzisz w pace!");
            return;
        }

        if(event.getSeat() != QAVVehicleEnterEvent.VehicleSeat.DRIVER)
            return;

        if(abstractVehicle instanceof AbstractHelicopter || abstractVehicle instanceof AbstractPlane) {
            if(!LicenseManager.hasLicense(user, License.LICENCJA_PILOTA)){
                ChatUtil.sendMessage(player, "&8#&c Nie posiadasz licencji pilota, aby moc prowadzic ten pojazd.");
                event.setCancel(true);
                return;
            }

            Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> new Speedometer(user, vehicleEntity), 1);
            return;
        }

        if(abstractVehicle instanceof AbstractBoat) {
            if(!LicenseManager.hasLicense(user, License.LICENCJA_RYBAKA)){
                ChatUtil.sendMessage(player, "&8#&c Nie posiadasz licencji rybaka, aby moc prowadzic ten pojazd.");
                event.setCancel(true);
                return;
            }

            Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> new Speedometer(user, vehicleEntity), 1);
            return;
        }

        if(abstractVehicle instanceof AbstractCar){
            if(!LicenseManager.hasLicense(user, License.PRAWO_JAZDY_KAT_B)){
                ChatUtil.sendMessage(player, "&8#&c Nie posiadasz prawa jazdy kat. B, aby moc prowadzic ten pojazd.");
                event.setCancel(true);
                return;
            }

            Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> new Speedometer(user, vehicleEntity), 1);
            return;
        }

    }

    @EventHandler
    public void onEvent(VehicleEnterEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);
        VehicleType vehicleType = event.getVehicleType();

        if(user.getCharacter().getJail() != null){
            event.setCancelled(true);
            ChatUtil.sendMessage(player, "&8#&c Siedzisz w pace!");
            return;
        }

        if(user.getUserTempData().isTraveling()) {
            event.setCancelled(true);
            return;
        }

        switch (vehicleType){
            case CAR:
            case TANK:
            case HOVER_BIKE:
                if(!LicenseManager.hasLicense(user, License.PRAWO_JAZDY_KAT_B)){
                    ChatUtil.sendMessage(player, "&8#&c Nie posiadasz prawa jazdy kat. B, aby moc prowadzic ten pojazd.");
                    event.setCancelled(true);
                    return;
                }

                Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> new Speedometer(user, VehiclesMain.getPlugin().getPlayerVehicle(player)), 1);
                break;
            case BIKE:
                if(!LicenseManager.hasLicense(user, License.PRAWO_JAZDY_KAT_A)){
                    ChatUtil.sendMessage(player, "&8#&c Nie posiadasz prawa jazdy kat. A, aby moc prowadzic ten pojazd.");
                    event.setCancelled(true);
                    return;
                }

                Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> new Speedometer(user, VehiclesMain.getPlugin().getPlayerVehicle(player)), 1);
                break;
            case HELICOPTER:
            case PLANE:
                if(!LicenseManager.hasLicense(user, License.LICENCJA_PILOTA)){
                    ChatUtil.sendMessage(player, "&8#&c Nie posiadasz licencji pilota, aby moc prowadzic ten pojazd.");
                    event.setCancelled(true);
                    return;
                }

                Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> new Speedometer(user, VehiclesMain.getPlugin().getPlayerVehicle(player)), 1);
                break;

            case RAFT:
                if(!LicenseManager.hasLicense(user, License.LICENCJA_RYBAKA)){
                    ChatUtil.sendMessage(player, "&8#&c Nie posiadasz licencji rybaka, aby moc prowadzic ten pojazd.");
                    event.setCancelled(true);
                    return;
                }
                break;

            case BROOM:
                ChatUtil.sendMessage(player, "&8#&c Nie baw sie...");
                event.setCancelled(true);
                return;

            case SUBMARINE:
                if(!LicenseManager.hasLicense(user, License.LICENCJA_RYBAKA)){
                    ChatUtil.sendMessage(player, "&8#&c Nie posiadasz licencji rybaka, aby moc prowadzic ten pojazd.");
                    event.setCancelled(true);
                    return;
                }

                Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> new Speedometer(user, VehiclesMain.getPlugin().getPlayerVehicle(player)), 1);
                break;
            default:
                break;
        }


    }
}
