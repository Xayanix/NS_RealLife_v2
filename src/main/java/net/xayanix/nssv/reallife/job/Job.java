package net.xayanix.nssv.reallife.job;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.sound.SoundKey;
import net.xayanix.nssv.reallife.sound.SoundManager;
import net.xayanix.nssv.reallife.title.TitleManager;
import org.bukkit.entity.Player;

public class Job {

    @Getter
    private JobType type;

    @Getter
    @Setter
    private int level;

    @Getter
    private int points;

    @Getter
    @Setter
    private int earned;

    public Job(JobType type, int level, int points){
        this.type = type;
        this.level = level;
        this.points = points;
    }

    public void addPoint(Player player, boolean addReward){
        if(addReward)
            this.addReward(player.hasPermission("vip") ? type.getReward()*2 : type.getReward());

        if(RandomUtil.getChance(type.getPointChance())) {
            int toNextLevel = this.getPointsToNextLevel();
            if(this.points >= toNextLevel) {
                this.level++;
                this.points = 1;
                if(player != null) {
                    ChatUtil.sendMessage(player, "&8#&a Osiagnales nowy poziom pracy (&e" + this.level + " Lv&a).");
                    TitleManager.sendTitle(player, "&a&lNOWY POZIOM PRACY!", "&7Twój nowy poziom pracy: &f" + this.level + "Lv", 20, 40, 20);
                    SoundManager.play(player, SoundKey.JOB_LEVEL_UP);
                    return;
                }
            }

            this.points++;
            ChatUtil.sendMessage(player, "&8#&b Gratulacje! Zdobyles punkt pracy, oby tak dalej!");
            SoundManager.play(player, SoundKey.JOB_POINT_ACQUIRED);
        }

    }

    public int getPointsToNextLevel(){
        return (this.level * 15);
    }

    public void addReward(int money){
        this.earned+= money;
    }

}
