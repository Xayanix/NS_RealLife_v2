package net.xayanix.nssv.reallife.sound;

import net.xayanix.nssv.reallife.configuration.Configuration;
import net.xayanix.nssv.reallife.logger.Logger;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {

    public static void play(Player player, SoundKey key, float volume){
        Configuration configuration = Configuration.getInstance();
        Sound sound = configuration.getSoundMap().get(key);

        if(sound == null){
            Logger.info("Invaild `" + key + "` sound key.");
            return;
        }

        player.playSound(player.getLocation(), sound, volume, 1);

    }

    public static void play(Player player, SoundKey key){
        play(player, key, 100);
    }

}
