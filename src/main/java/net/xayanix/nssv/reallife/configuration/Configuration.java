package net.xayanix.nssv.reallife.configuration;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.reallife.sound.SoundKey;
import org.bukkit.Sound;

import java.util.HashMap;

@Getter
public class Configuration {

    @Getter
    private static Configuration instance;
    private HashMap<SoundKey, Sound> soundMap;

    @Setter
    private int playerTrackerDistance;

    public Configuration(){
        instance = this;
        this.playerTrackerDistance = 32;
        this.soundMap = new HashMap<>();

        this.soundMap.put(SoundKey.JOB_POINT_ACQUIRED, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        this.soundMap.put(SoundKey.JOB_LEVEL_UP, Sound.ENTITY_PLAYER_LEVELUP);

    }

}
