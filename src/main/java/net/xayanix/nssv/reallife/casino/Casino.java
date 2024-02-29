package net.xayanix.nssv.reallife.casino;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.chat.IC;
import net.xayanix.nssv.reallife.user.User;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class Casino {

    private User user1;
    private User user2;
    private int money;

    public boolean takeMoney(){
        if(user1.getCharacter().getMoney().getWallet() < money){
            this.broadcast("&8#&c " + user1.getName() + "&e nie ma " + money + " zlotych na gre.");
            return false;
        }

        if(user2.getCharacter().getMoney().getWallet() < money){
            this.broadcast("&8#&c " + user2.getName() + "&e nie ma " + money + " zlotych na gre.");
            return false;
        }

        user1.getCharacter().getMoney().removeFromWallet(money);
        user2.getCharacter().getMoney().removeFromWallet(money);

        this.broadcast("&8#&c Z twojego portfela pobrano &e" + money + " zlotych&c na gre w kasynie.");

        return true;
    }

    public void play(){

        int user1kostka = 0;
        int user2kostka = 0;
        int rzut = RandomUtil.getRandomNumber(6);

        try {
            Thread.sleep(1000);
            this.broadcast("&8#&e " + user1.getName() + "&a rzuca kostka i wypada liczba: &e" + rzut);
            user1kostka+= rzut;
            rzut = RandomUtil.getRandomNumber(6);

            Thread.sleep(2500);
            this.broadcast("&8#&e " + user2.getName() + "&a rzuca kostka i wypada liczba: &e" + rzut);
            user2kostka+= rzut;
            rzut = RandomUtil.getRandomNumber(6);

            Thread.sleep(2500);
            this.broadcast("&8#&e " + user1.getName() + "&a rzuca kostka i wypada liczba: &e" + rzut);
            user1kostka+= rzut;
            rzut = RandomUtil.getRandomNumber(6);

            Thread.sleep(2500);
            this.broadcast("&8#&e " + user2.getName() + "&a rzuca kostka i wypada liczba: &e" + rzut);
            user2kostka+= rzut;

            int reward = this.money*2;

            if(user1kostka == user2kostka){
                this.broadcast("&8#&c REMIS! Za 3 sekundy rzucacie jeszcze raz!");
                Thread.sleep(3000);
                this.play();
                return;
            } else if(user1kostka > user2kostka) {
                this.broadcast("&8#&e " + user1.getName() + "&a wygrywa z wynikiem &e" + user1kostka + ":" + user2kostka + "&a i otrzymuje &e" + reward + " zlotych&a.");
                user1.getCharacter().getMoney().addToWallet(reward);
            } else if(user2kostka > user1kostka) {
                this.broadcast("&8#&e " + user2.getName() + "&a wygrywa z wynikiem &e" + user2kostka + ":" + user1kostka + "&a i otrzymuje &e" + reward + " zlotych&a.");
                user2.getCharacter().getMoney().addToWallet(reward);
            }


        } catch (InterruptedException e) {

        }

    }

    public void broadcast(String string){
        user1.sendMessage(string);
        user2.sendMessage(string);
    }

}
