package net.xayanix.nssv.reallife.pvp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public class DamageAllow {

    private String robber;
    private final long timestamp = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);

}
