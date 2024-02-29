package net.xayanix.nssv.reallife.jail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.reallife.user.User;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class Jail {

    private transient User jailed;
    @Setter
    private int seconds;
    private boolean caution;
    private int cautionPrice;

}
