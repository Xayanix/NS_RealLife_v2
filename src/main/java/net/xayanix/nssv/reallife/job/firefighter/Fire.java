package net.xayanix.nssv.reallife.job.firefighter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public class Fire {

    private Location location;
    private double extenguish;
    private final long expire = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);


    public void extenguish(){
        this.extenguish+= 0.5;
    }

}
