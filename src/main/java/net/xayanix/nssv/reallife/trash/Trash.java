package net.xayanix.nssv.reallife.trash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public class Trash {

    private Location location;
    private final long expire = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1);

}
