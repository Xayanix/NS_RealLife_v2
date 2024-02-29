package net.xayanix.nssv.reallife.lotto;

import com.google.common.collect.Lists;
import lombok.Getter;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class LottoManager {

    @Getter
    private static LottoManager instance;
    private int actualReward;
    private int winNumber;
    private List<String> rewardedIps;

    public LottoManager(){
        instance = this;
        this.rewardedIps = Lists.newArrayList();
        this.setWinNumber();
        this.resetReward();
    }

    public void increaseReward(){
        this.actualReward+= 20000 + RandomUtil.getRandomNumber(30000);
    }

    public void resetReward(){
        this.actualReward = 20000 + RandomUtil.getRandomNumber(30000);
    }

    public void setWinNumber(){
        this.winNumber = RandomUtil.getRandomNumber(99);
    }

    public List<User> getWinners(){
        List<User> users = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);
            if(user.getCharacter().getLotto() == this.winNumber)
                users.add(user);
        }

        return users;
    }

    public void resetWinners(){
        for(User user : UserManager.getUsersOnline())
            user.getCharacter().setLotto(-1);

        this.rewardedIps.clear();
    }

    public void giveRewards(){
        int reward = this.actualReward / this.getWinners().size();
        for(User user : this.getWinners())
            user.getCharacter().getMoney().addToWallet(reward);

        this.resetReward();
    }

}
