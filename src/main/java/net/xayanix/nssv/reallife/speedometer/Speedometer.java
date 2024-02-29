package net.xayanix.nssv.reallife.speedometer;

import es.pollitoyeye.vehicles.interfaces.Vehicle;
import eu.haelexuis.utils.xoreboard.XoreBoard;
import eu.haelexuis.utils.xoreboard.XoreBoardPlayerSidebar;
import lombok.Getter;
import me.zombie_striker.qg.exp.cars.VehicleEntity;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.utils.DoubleUtil;
import org.bukkit.Location;

import java.util.HashMap;

@Getter
public class Speedometer {

    private User user;
    private Vehicle vehicleOld;
    private VehicleEntity vehicleNew;
    private XoreBoardPlayerSidebar sidebar;
    private SpeedometerType type;

    private Location lastLocation;
    private double speed;

    public Speedometer(User user, Vehicle vehicle){
        if(vehicle == null)
            return;

        this.user = user;
        this.vehicleOld = vehicle;
        this.lastLocation = vehicle.getMainStand().getLocation();
        this.speed = 0;
        this.type = SpeedometerType.OLD;

        XoreBoard xoreBoard = user.getUserTempData().getXoreBoard();
        sidebar = xoreBoard.getSidebar(user.getPlayer());
        sidebar.setDisplayName("&e&lLICZNIK POJAZDU");

        this.setLines();

        this.user.getUserTempData().setSpeedometer(this);
    }

    public Speedometer(User user, VehicleEntity vehicle){
        if(vehicle == null)
            return;

        this.user = user;
        this.vehicleNew = vehicle;
        this.lastLocation = vehicle.getDrivingArmorstand().getLocation();
        this.speed = 0;
        this.type = SpeedometerType.NEW;

        XoreBoard xoreBoard = user.getUserTempData().getXoreBoard();
        sidebar = xoreBoard.getSidebar(user.getPlayer());
        sidebar.setDisplayName("&e&lLICZNIK POJAZDU");

        this.setLines();

        this.user.getUserTempData().setSpeedometer(this);
    }

    public void calculateSpeed(){
        if(type == SpeedometerType.OLD) {
            if(this.lastLocation.getWorld() != vehicleOld.getMainStand().getWorld()){
                this.lastLocation = vehicleOld.getMainStand().getLocation();
                return;
            }

            double dist = this.lastLocation.distance(vehicleOld.getMainStand().getLocation()) * 4 * 60 * 60;
            this.speed = dist / 1000;

            this.lastLocation = vehicleOld.getMainStand().getLocation();
        } else if(type == SpeedometerType.NEW) {
            if(this.lastLocation.getWorld() != vehicleNew.getDrivingArmorstand().getWorld()){
                this.lastLocation = vehicleNew.getDrivingArmorstand().getLocation();
                return;
            }

            double dist = this.lastLocation.distance(vehicleNew.getDrivingArmorstand().getLocation()) * 4 * 60 * 60;
            this.speed = dist / 1000;

            this.lastLocation = vehicleNew.getDrivingArmorstand().getLocation();
        }

    }

    public void setLines(){
        HashMap<String, Integer> lines = new HashMap<>();
        lines.put("&ePredkosc:&f " + DoubleUtil.format(this.speed) + " km/h", 4);
        switch (this.type){
            case OLD:
                lines.put("&ePaliwo:&f " + DoubleUtil.format(this.vehicleOld.getFuel()/10) + "%", 3);
                break;
            case NEW:
                lines.put("&ePaliwo:&f " + DoubleUtil.format(this.vehicleNew.getFuel()) + "%", 3);
                break;
        }
        lines.put("&eCB-Radio:&f #wiadomosc", 2);
        sidebar.rewriteLines(lines);
    }

    public void clear(){
        this.sidebar.clearLines();
        this.user.getUserTempData().setSpeedometer(null);
    }

}
