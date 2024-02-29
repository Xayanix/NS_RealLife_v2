package net.xayanix.nssv.reallife.job.robber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.xayanix.nssv.reallife.user.User;

import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
public class Rob {

    private String by;
    private int money;
    private final long timestamp = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3);

}
